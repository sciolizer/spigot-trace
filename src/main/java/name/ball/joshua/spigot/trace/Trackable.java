package name.ball.joshua.spigot.trace;

import name.ball.joshua.spigot.trace.di.Inject;
import org.bukkit.event.Event;

import java.lang.reflect.Method;
import java.util.*;

public class Trackable {

    @Inject private MethodPropertyMapper methodPropertyMapper;

    private final Class<? extends Event> eventClass;
    private final List<String> propertyChain;

    public Trackable(Class<? extends Event> eventClass, List<String> propertyChain) throws NoSuchMethodException {
        this.eventClass = eventClass;
        this.propertyChain = Collections.unmodifiableList(propertyChain);
        getTypeUnsafe(); // to throw NoSuchMethodException
    }

    public List<Trackable> getSubTrackables() {
        try {
            return getSubTrackablesThrowing();
        } catch (NoSuchMethodException nsme) {
            throw new RuntimeException(nsme); // should never happen
        }
    }

    public List<Trackable> getSubTrackablesThrowing() throws NoSuchMethodException {
        Class<?> type = getTypeUnsafe();
        Method[] methods = type.getMethods();
        SortedMap<String,Trackable> result = new TreeMap<String,Trackable>();
        for (Method method : methods) {
            String propertyName = methodPropertyMapper.getPropertyName(method);
            if (propertyName != null) {
                result.put(propertyName, new Trackable(eventClass, extend(propertyName)));
            }
        }
        return new ArrayList<Trackable>(result.values());
    }

    private List<String> extend(String propertyName) {
        List<String> result = new ArrayList<String>(propertyChain.size() + 1);
        result.addAll(propertyChain);
        result.add(propertyName);
        return result;
    }

    private Class<?> getTypeUnsafe() throws NoSuchMethodException {
        Class<?> result = eventClass;
        for (String property : propertyChain) {
            Method method;
            try {
                method = result.getMethod("get" + property);
            } catch (NoSuchMethodException e) {
                throw (NoSuchMethodException)new NoSuchMethodException(property).initCause(e);
            }
            result = method.getReturnType();
        }
        return result;
    }

    public String getLastProperty() {
        return propertyChain.get(propertyChain.size() - 1);
    }

    public String getValue(Event event) {
        if (!eventClass.isAssignableFrom(event.getClass())) {
            throw new RuntimeException("expecting " + eventClass + ", but got " + event.getClass());
        }
        Object o = event;
        for (String property : propertyChain) {
            Method method;
            try {
                method = eventClass.getMethod("get" + property);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            try {
                o = method.invoke(o);
            } catch (Exception e) {
                return e.getClass() + ": " + e.getMessage();
            }
        }
        return String.valueOf(o);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(eventClass.getSimpleName());
        for (String property : propertyChain) {
            sb.append('.').append(property);
        }
        return sb.toString();
    }

    public Class<? extends Event> getEventClass() {
        return eventClass;
    }

    public List<String> getPropertyChain() {
        return propertyChain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trackable trackable = (Trackable) o;

        return eventClass.equals(trackable.eventClass) && propertyChain.equals(trackable.propertyChain);
    }

    @Override
    public int hashCode() {
        int result = eventClass.hashCode();
        result = 31 * result + propertyChain.hashCode();
        return result;
    }

}
