package de.melonigemelone.trader.listener;

import de.melonigemelone.trader.MelonTrader;
import de.melonigemelone.trader.model.Trader;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class TraderListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Trader trader = MelonTrader.getTraderHandler().getTraderFromTraderSession(player.getUniqueId());
        if(trader == null) {
            return;
        }
        trader.checkInventory(event, player);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        MelonTrader.getTraderHandler().removeTraderSession(player);

    }
}
