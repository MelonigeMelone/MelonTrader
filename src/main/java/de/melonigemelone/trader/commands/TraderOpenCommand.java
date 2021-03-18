package de.melonigemelone.trader.commands;

import de.melonigemelone.api.server.messages.GeneralMessages;
import de.melonigemelone.trader.MelonTrader;
import de.melonigemelone.trader.model.Trader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TraderOpenCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length == 1) {
                Trader trader = MelonTrader.getTraderHandler().getTraderFromName(args[0]);
                if (trader == null) {
                    return true;
                }
                trader.openInventory(player);
            }

        } else {
            sender.sendMessage(GeneralMessages.ONLY_PLAYERS.getMessage(false));
        }
        return false;
    }
}
