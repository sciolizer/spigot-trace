package name.ball.joshua.spigot.trace;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.Set;

public class MethodPropertyMapper {

    private Set<String> skippables = new LinkedHashSet<String>();

    public MethodPropertyMapper() {
        skippables.add("Class");
        skippables.add("EventName");
        skippables.add("Handlers");
    }

    public String getPropertyName(Method method) {
        if (Modifier.isStatic(method.getModifiers())) {
            return null;
        }
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes != null && parameterTypes.length > 0) {
            return null;
        }
        String methodName = method.getName();
        if (!methodName.startsWith("get")) {
            return null;
        }
        String propertyName = methodName.substring(3);
        return skippables.contains(propertyName) ? null : propertyName;
    }

}
