package de.melonigemelone.trader.handler;

import de.melonigemelone.trader.model.Trader;

import org.bukkit.entity.Player;

import java.util.*;

public class TraderHandler {

    private List<Trader> traders = new ArrayList<>();
    private HashMap<UUID, Trader> traderSessions = new HashMap<>();

    public TraderHandler(TraderConfigHandler traderConfigHandler) {
        traders = traderConfigHandler.loadTraders();
    }


    public void addTraderSession(Player player, Trader trader) {
        traderSessions.put(player.getUniqueId(), trader);
    }

    public void removeTraderSession(Player player) {
        traderSessions.remove(player.getUniqueId());
    }

    public Trader getTraderFromTraderSession(UUID uuid) {
        return traderSessions.get(uuid);
    }

    public Trader getTraderFromName(String name) {
        for(Trader trader : traders) {
            if(trader.getName().equalsIgnoreCase(name)) {
                return trader;
            }
        }
        return null;
    }
}
