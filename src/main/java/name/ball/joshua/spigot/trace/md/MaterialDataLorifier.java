package name.ball.joshua.spigot.trace.md;

import name.ball.joshua.spigot.trace.di.Inject;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.Potion;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class MaterialDataLorifier implements Listener {

//    private Map<Inventory,Map<ItemStack>> inventories = new LinkedHashSet<Inventory>();

    @Inject private Plugin plugin;

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
                    lores.set(i, "md:" + itemStack.getData() + ";" + itemStack.getDurability());
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

    @EventHandler
    public void onBrew(BrewEvent event) {
        final BrewerInventory contents = event.getContents();
        new BukkitRunnable() {
            @Override public void run() {
                for (ItemStack itemStack : contents) {
                    if (itemStack == null || !Material.POTION.equals(itemStack.getType())) continue;
                    Potion potion = Potion.fromItemStack(itemStack);
                    String codified = "(" + itemStack.getData() + ";" + itemStack.getDurability() + ")nms;" + potion.toDamageValue() + "bukkit";
                    addLore(itemStack, codified);
                }
            }
        }.runTask(plugin);
    }

    private void addLore(ItemStack s, String l) {
        ItemMeta itemMeta = s.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore == null) lore = new ArrayList<String>();
        lore.add(l);
        itemMeta.setLore(lore);
        s.setItemMeta(itemMeta);
    }

}
