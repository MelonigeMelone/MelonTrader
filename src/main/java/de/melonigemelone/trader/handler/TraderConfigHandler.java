package de.melonigemelone.trader.handler;

import de.melonigemelone.api.lib.configuration.yaml.YamlFileBuilder;
import de.melonigemelone.trader.MelonTrader;
import de.melonigemelone.trader.model.Trader;
import de.melonigemelone.trader.model.TraderItem;
import de.melonigemelone.trader.model.TraderItemType;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class TraderConfigHandler extends YamlFileBuilder {

    public TraderConfigHandler() {
        super(MelonTrader.getInstance().getDataFolder().toString(), "traders.yml");
        setDefaultValues();
    }

    public void setDefaultValues() {
        setIfNotExists("TRADERS.0.NAME", "Holzfäller");
        setIfNotExists("TRADERS.0.ITEM.0.TYPE", "MATERIAL");
        setIfNotExists("TRADERS.0.ITEM.0.MATERIAL", "STONE");
        setIfNotExists("TRADERS.0.ITEM.0.BUY_PRICE", 1);
        setIfNotExists("TRADERS.0.ITEM.0.SELL_PRICE", 1);
        setIfNotExists("TRADERS.0.ITEM.0.SLOT", 1);
        setIfNotExists("TRADERS.0.ITEM.0.DISPLAY_NAME", "STEIN");

        setIfNotExists("TRADERS.1.NAME", "Bergarbeiter");
        setIfNotExists("TRADERS.1.ITEM.0.TYPE", "EXECUTE_COMMAND");
        setIfNotExists("TRADERS.1.ITEM.0.MATERIAL", "STONE");
        setIfNotExists("TRADERS.1.ITEM.0.COMMAND", "eco add %player% 100");
        setIfNotExists("TRADERS.1.ITEM.0.BUY_PRICE", 1);
        setIfNotExists("TRADERS.1.ITEM.0.SELL_PRICE", 1);
        setIfNotExists("TRADERS.1.ITEM.0.SLOT", 1);
        setIfNotExists("TRADERS.1.ITEM.0.DISPLAY_NAME", "STEIN");

        save();

    }

    public  List<Trader> loadTraders() {
        List<Trader> traders = new ArrayList<>();
        for (String traderKey : getConfigurationSection("TRADERS").getKeys(false)) {
            String traderName = getString("TRADERS." + traderKey + ".NAME");
            List<TraderItem> traderItems = new ArrayList<>();
            for (String traderItemKey : getConfigurationSection("TRADERS." + traderKey + ".ITEM").getKeys(false)) {
                TraderItemType traderItemType = TraderItemType.valueOf(getString("TRADERS." + traderKey + ".ITEM." + traderItemKey + ".TYPE"));
                Material material = Material.getMaterial(getString("TRADERS." + traderKey + ".ITEM." + traderItemKey + ".MATERIAL"));
                String command = null;
                if(traderItemType.equals(TraderItemType.EXECUTE_COMMAND)) {
                    command = getString("TRADERS." + traderKey + ".ITEM." + traderItemKey + ".COMMAND");
                }
                double buyPrice = getDouble("TRADERS." + traderKey + ".ITEM." + traderItemKey + ".BUY_PRICE");
                double sellPrice = getDouble("TRADERS." + traderKey + ".ITEM." + traderItemKey + ".SELL_PRICE");
                int slot = getInt("TRADERS." + traderKey + ".ITEM." + traderItemKey + ".SLOT");
                String name = getString("TRADERS." + traderKey + ".ITEM." + traderItemKey + ".DISPLAY_NAME");
                traderItems.add(new TraderItem(traderItemType, material, buyPrice, sellPrice, command, name, slot));

            }
            traders.add(new Trader(traderName, traderItems));
        }
        return traders;
    }

}
