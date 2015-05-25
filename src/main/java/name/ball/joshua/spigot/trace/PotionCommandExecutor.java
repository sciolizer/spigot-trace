package name.ball.joshua.spigot.trace;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PotionCommandExecutor implements CommandExecutor {
    @Override public boolean onCommand(CommandSender sender, Command command, String c, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Expected exactly one argument");
            return false;
        }
        String label = args[0];
        List<Range> ranges = new ArrayList<Range>(label.length());
        String[] unparsedRanges = label.split(",");
        for (String unparsedRange : unparsedRanges) {
            if (unparsedRange.contains("-")) {
                String[] rangeEnds = unparsedRange.split("-");
                if (rangeEnds.length != 2) {
                    sender.sendMessage("Unrecognized range: " + unparsedRange);
                    return false;
                }
                final short start;
                final short end;
                try {
                    start = Short.parseShort(rangeEnds[0]);
                    end = Short.parseShort(rangeEnds[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("begin and end of " + unparsedRange + " are not shorts");
                    return false;
                }
                ranges.add(new Range() {
                    @Override public List<Short> getRange() {
                        List<Short> result = new ArrayList<Short>();
                        for (short i = start; i < end + 1; i++) {
                            result.add(i);
                        }
                        return result;
                    }
                });
            } else {
                final short single;
                try {
                    single = Short.parseShort(unparsedRange);
                } catch (NumberFormatException e) {
                    sender.sendMessage("not a short: " + unparsedRange);
                    return false;
                }
                ranges.add(new Range() {
                    @Override public List<Short> getRange() {
                        return Collections.singletonList(single);
                    }
                });
            }
        }
        for (Range range : ranges) {
            for (Short r : range.getRange()) {
                Bukkit.getServer().broadcastMessage("r = " + r);
            }
        }
        return true;
    }

    private interface Range {
        List<Short> getRange();
    }
}
