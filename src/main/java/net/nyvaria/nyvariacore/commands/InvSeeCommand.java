/**
 * Copyright (c) 2013-2014
 * Paul Thompson <captbunzo@gmail.com> / Nyvaria <geeks@nyvaria.net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 *
 */
package net.nyvaria.nyvariacore.commands;

import net.nyvaria.nyvariacore.NyvariaCore;
import net.nyvaria.nyvariacore.NyvariaCoreCommand;
import net.nyvaria.nyvariacore.coreplayer.CorePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Paul Thompson
 */
public class InvSeeCommand extends NyvariaCoreCommand implements CommandExecutor, TabCompleter {
    public static String CMD = "invsee";

    public InvSeeCommand(NyvariaCore plugin) {
        super(plugin);
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> completions = new ArrayList<String>();

        // If we have one argument, the first is a partial player name
        if (args.length == 1) {
            if ((sender instanceof Player) && sender.hasPermission(NyvariaCore.PERM_INVSEE)) {
                String partialPlayerName = args[0];

                for (Player nextMatchingPlayer : plugin.getServer().matchPlayer(partialPlayerName)) {
                    completions.add(nextMatchingPlayer.getPlayerListName());
                }
            }
        }

        return completions;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Make sure we have a Player
        if (!NyvariaCoreCommand.isPlayer(sender, InvSeeCommand.CMD)) {
            return true;
        }

        // Get the core player that has called this command
        CorePlayer corePlayer = this.plugin.getCorePlayerList().get(sender);

        // Check the command permission (though bukkit should have already stopped players without permission)
        if (!NyvariaCoreCommand.hasCommandPermission(corePlayer, NyvariaCore.PERM_INVSEE)) {
            return true;
        }

        // Check if we have enough arguments
        if (args.length != 1) {
            return false;
        }

        // Get the target player whose inventory we are peeking at
        String invPlayerName = args[0];
        Player invPlayer = this.getOnlinePlayer(invPlayerName, corePlayer);
        if (invPlayer == null) {
            return true;
        }

        // Grab the inventory and open it up
        Inventory inv = invPlayer.getInventory();
        corePlayer.getPlayer().closeInventory();
        corePlayer.getPlayer().openInventory(inv);
        corePlayer.setIsInvseeing(true);

        // Return a happy result
        return true;
    }

}
