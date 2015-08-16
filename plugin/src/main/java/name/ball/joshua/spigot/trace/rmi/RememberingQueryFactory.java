package name.ball.joshua.spigot.trace.rmi;

import name.ball.joshua.bukkit.eventtrace.api.ApiSerializables;

public interface RememberingQueryFactory {
    RememberingQuery newRememberingQuery(ApiSerializables.Query query);
}
