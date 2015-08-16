package name.ball.joshua.spigot.trace;

import org.bukkit.command.CommandSender;

public interface TraceCommandFactory {
    TraceCommand newTraceCommand(CommandSender commandSender);
}
