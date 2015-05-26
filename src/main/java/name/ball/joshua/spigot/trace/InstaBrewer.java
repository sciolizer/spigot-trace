package name.ball.joshua.spigot.trace;

import org.bukkit.block.BrewingStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InstaBrewer implements Listener {

    @EventHandler
    public void onBrewStart(InventoryClickEvent event) {
        if (InventoryType.BREWING.equals(event.getInventory().getType())) {
            BrewingStand brewingStand = (BrewingStand) event.getInventory().getHolder();
            brewingStand.setBrewingTime(1);
        }
    }
}
