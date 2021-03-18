package de.melonigemelone.trader;

import de.melonigemelone.trader.commands.TraderOpenCommand;
import de.melonigemelone.trader.handler.TraderConfigHandler;
import de.melonigemelone.trader.handler.TraderHandler;
import de.melonigemelone.trader.listener.TraderListener;
import org.bukkit.plugin.java.JavaPlugin;

public class MelonTrader extends JavaPlugin {

    private static MelonTrader instance;

    private static TraderConfigHandler traderConfigHandler;
    private static TraderHandler traderHandler;

    @Override
    public void onEnable() {
        instance = this;

        traderConfigHandler = new TraderConfigHandler();
        traderHandler = new TraderHandler(traderConfigHandler);

        getCommand("melonTrader").setExecutor(new TraderOpenCommand());
        getServer().getPluginManager().registerEvents(new TraderListener(), this);
    }

    @Override
    public void onDisable() {

    }

    public static MelonTrader getInstance() {
        return instance;
    }

    public static TraderConfigHandler getTraderConfigHandler() {
        return traderConfigHandler;
    }

    public static TraderHandler getTraderHandler() {
        return traderHandler;
    }
}
