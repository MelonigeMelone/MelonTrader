package de.melonigemelone.trader.commands;

import de.melonigemelone.api.server.messages.GeneralMessages;
import de.melonigemelone.trader.MelonTrader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TraderReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(!player.hasPermission("melontrader.command.reload")) {
                player.sendMessage(GeneralMessages.NO_PERM.getMessage(true));
                return true;
            }

            MelonTrader.getTraderHandler().reloadTradersFromConfig();
            player.sendMessage(GeneralMessages.PREFIX.getMessage(false) + "§aDu hast die Trader erfolgreich neugeladen");


        } else {
            MelonTrader.getTraderHandler().reloadTradersFromConfig();
            System.out.println("§aDu hast die Trader erfolgreich neugeladen");
        }
        return false;
    }
}
