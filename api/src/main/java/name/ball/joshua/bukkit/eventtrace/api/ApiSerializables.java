package name.ball.joshua.bukkit.eventtrace.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ApiSerializables {
    public static final String RMI_NAME = "eventtrace";

    public static enum Evaluation implements Serializable {
        TO_STRING, IDENTITY_HASH_CODE;
        private static final long serialVersionUID = 0l;

        public Value evaluate(Object value) {
            Value result = new Value();
            switch (this) {
                case TO_STRING:
                    result.string = value.toString(); // todo: catch exception and set error when exception occurs
                    break;
                case IDENTITY_HASH_CODE:
                    result.integer = System.identityHashCode(value);
                    break;
            }
            return result;
        }
    }

    public static class Events implements Serializable {
        private static final long serialVersionUID = 0l;
        public List<EventInfo> events = new ArrayList<EventInfo>();
    }

    public static class EventInfo implements Serializable {
        private static final long serialVersionUID = 0l;
        public int id; // first event has id 0
        public int when; // milliseconds since recording started
        public String klass; // only returned if requested
        public List<Value> values; // ordering matches ordering of properties given in projection
    }

    public static class Value implements Serializable {
        private static final long serialVersionUID = 0l;
        public String string;
        public Integer integer;
        public String error;
    }

    public static class Query implements Serializable {
        private static final long serialVersionUID = 0l;
        public Filter filter;
        public Projection projection;
    }

    public static class Filter implements Serializable {
        private static final long serialVersionUID = 0l;
        public List<Integer> ids; // where id in this set; skipped if null
        public List<String> classes; // where class in this set; skipped if null

        /*
        filter:
        each clause is conjunction
        disjunctions can be done by making multiple requests
        where id in (3, 4, 5)
        where class in (move, break)

        projection:
          id and when always come back. Everything else must be explicitly requested:

          String class
          List<(List<String>,evaluation)> propertyChains
         */
    }

    public static class Projection implements Serializable {
        private static final long serialVersionUID = 0l;
        public boolean klass;
        public List<Property> properties;
    }

    public static class Property implements Serializable {
        private static final long serialVersionUID = 0l;
        public Chain chain;
        public Evaluation evaluation;
    }

    public static class Chain implements Serializable {
        private static final long serialVersionUID = 0l;
        public List<String> fields;
    }
}
