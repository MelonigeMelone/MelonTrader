package de.melonigemelone.trader.model;

import de.melonigemelone.api.lib.minecraft.ItemBuilder;
import de.melonigemelone.api.lib.minecraft.player.MelonPlayer;
import de.melonigemelone.api.server.messages.GeneralMessages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.Console;
import java.util.Iterator;

public class TraderItem {

    private final TraderItemType traderItemType;
    private final Material material;
    private final double buyPrice;
    private final double sellPrice;
    private final String command;
    private final String displayName;
    private final int slot;

    public TraderItem(TraderItemType traderItemType, Material material, double buyPrice, double sellPrice, String command, String displayName, int slot) {
        this.traderItemType = traderItemType;
        this.material = material;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.command = command;
        this.displayName = displayName;
        this.slot = slot;
    }

    public TraderItemType getTraderItemType() {
        return traderItemType;
    }

    public Material getMaterial() {
        return material;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public String getCommand() {
        return command;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getItemStack() {
        return new ItemBuilder(material, 1)
                .setName(displayName)
                .addLore("§8§l» §7Kaufen: §e" + buyPrice + "€")
                .addLore("§8§l» §Verkaufen: §e" + sellPrice + "€")
                .addLore("§7Um das Item zu verkaufen klicke auf")
                .addLore("§7das §eItem §7in deinem §eInventar")
                .build();
    }

    public void buy(Player player, boolean buyItemStack) {
        MelonPlayer melonPlayer = MelonPlayer.fromPlayer(player);

        int amount = 1;
        if(buyItemStack) {
            amount = 64;
        }

        double price = buyPrice * amount;

        if(!melonPlayer.hasMoney(price)) {
            player.sendMessage(GeneralMessages.NOT_ENOUGH_MONEY.getMessage(true));
            return;
        }

        melonPlayer.removeMoney(price);

        if(traderItemType.equals(TraderItemType.MATERIAL)) {
            player.getInventory().addItem(new ItemBuilder(material, amount).build());
            player.sendMessage(GeneralMessages.PREFIX.getMessage(false) + "§7"+ amount + "x §e" + displayName + " §7gekauft für §e" + price + "€");
        } else if(traderItemType.equals(TraderItemType.EXECUTE_COMMAND)) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%player%", player.getName()));
            player.sendMessage(GeneralMessages.PREFIX.getMessage(false) + "§7"+ amount + "x §e" + displayName + " §7gekauft für §e" + price + "€");

        }
    }

    public void sellItem(Player player, ItemStack itemStack, int slot) {
        if(traderItemType.equals(TraderItemType.EXECUTE_COMMAND)) {
            return;
        }

        MelonPlayer melonPlayer = MelonPlayer.fromPlayer(player);

        int amount = itemStack.getAmount();
        double sellPrice = this.sellPrice * amount;

        melonPlayer.addMoney(sellPrice);
        player.getInventory().setItem(slot, new ItemStack(Material.AIR));
        player.sendMessage(GeneralMessages.PREFIX.getMessage(false) + "§7Du hast §e" + sellPrice + "€ §7verdient!");


    }
}
