package de.melonigemelone.trader.model;

import de.melonigemelone.api.lib.minecraft.ItemBuilder;
import de.melonigemelone.trader.MelonTrader;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Trader {

    private final String name;
    private final List<TraderItem> traderItems;
    private final HashMap<Material, TraderItem> traderMaterials = new  HashMap<>();

    public Trader(String name, List<TraderItem> traderItems) {
        this.name = name;
        this.traderItems = traderItems;
        for(TraderItem traderItem : traderItems) {
            traderMaterials.put(traderItem.getMaterial(), traderItem);
        }
    }

    public String getName() {
        return name;
    }


    public List<TraderItem> getTraderItems() {
        return traderItems;
    }

    public void openInventory(Player player) {
        MelonTrader.getTraderHandler().addTraderSession(player, this);
        Inventory inventory = Bukkit.createInventory(null, 27, "§e" + name);

        ItemStack background = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1).setName(" ").build();

        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, background);
        }
        for(TraderItem traderItem : traderItems) {
            inventory.setItem(traderItem.getSlot(), traderItem.getItemStack());
        }

        inventory.setItem(26, new ItemBuilder(Material.BARRIER, 1)
                .setName("§cVerkaufe alle Items in deinem Inventar!")
                .build());

        player.openInventory(inventory);
    }

    public void checkInventory(InventoryClickEvent event, Player player) {
        event.setCancelled(true);

        if (event.getCurrentItem() == null) {
            return;
        }

        ItemStack itemStack = event.getCurrentItem();

        if(!traderMaterials.containsKey(itemStack.getType())) {
            return;
        }

        if(event.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
            traderMaterials.get(itemStack.getType()).sellItem(player, itemStack, event.getSlot());
        } else {
            if(itemStack.getType().equals(Material.BARRIER)) {
                sellAll();
                return;
            }
            if (event.getClick() == ClickType.SHIFT_LEFT) {
                traderMaterials.get(itemStack.getType()).buy(player, true);
            } else if (event.getClick() == ClickType.LEFT) {
                traderMaterials.get(itemStack.getType()).buy(player, false);
            }
        }
    }

    public void sellAll() {

    }




}
