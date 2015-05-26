package name.ball.joshua.spigot.trace.md;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MaterialDataLorifier implements Listener {

//    private Map<Inventory,Map<ItemStack>> inventories = new LinkedHashSet<Inventory>();

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent inventoryOpenEvent) {
        Bukkit.getServer().broadcastMessage("lorifier triggered");
        for (ItemStack itemStack : inventoryOpenEvent.getInventory()) {
            if (itemStack == null) continue;
            ItemMeta itemMeta = itemStack.getItemMeta();
            List<String> lores = itemMeta.getLore();
            if (lores == null) lores = new ArrayList<String>(1);
            boolean missingLore = true;
            for (int i = 0; i < lores.size(); i++) {
                String lore = lores.get(i);
                if (lore.startsWith("md:")) {
                    lores.set(i, "md:" + itemStack.getData());
                    missingLore = false;
                    break;
                }
            }
            if (missingLore) {
                lores.add("md:" + itemStack.getData());
            }
            itemMeta.setLore(lores);
            itemStack.setItemMeta(itemMeta);
        }
    }

}
