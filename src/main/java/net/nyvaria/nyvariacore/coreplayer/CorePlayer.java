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
package net.nyvaria.nyvariacore.coreplayer;

import net.nyvaria.component.wrapper.NyvariaGroup;
import net.nyvaria.component.wrapper.NyvariaPlayer;
import net.nyvaria.nyvariacore.NyvariaCore;
import net.nyvaria.nyvariacore.coregroup.CoreGroup;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * @author Paul Thompson
 */
public class CorePlayer extends NyvariaPlayer implements Comparable<CorePlayer> {
    private boolean isInvseeing;

    public CorePlayer(Player player) {
        super(player, CoreGroup.DEFAULT_GROUP_NAME);
        this.setIsInvseeing(false);
        this.setAfk(false);
    }

    public CorePlayer(OfflinePlayer offlinePlayer) {
        super(offlinePlayer, CoreGroup.DEFAULT_GROUP_NAME);
        this.setIsInvseeing(false);
    }

    public void sendMessage(String message) {
        if (getPlayer() != null) {
            getPlayer().sendMessage(message);
        }
    }

    public boolean hasPermission(String permission) {
        if (getPlayer() != null) {
            return getPlayer().hasPermission(permission);
        }
        return false;
    }

    /*
    public String getPrettyName() {
        String group = this.getPrimaryGroup();
        return CoreGroup.getGroupPrefix(group) + this.getName() + CoreGroup.getGroupSuffix(group);
    }
    */

    public CoreGroup getPrimaryGroup() {
        return CoreGroup.getPrimaryGroup(this);
    }

    public void setAfk(boolean afk) {
        if (getPlayer() != null) {
            getPlayer().setMetadata("afk", new FixedMetadataValue(NyvariaCore.getInstance(), afk));
        }
    }

    public boolean isAfk() {
        if ((getPlayer() != null) && getPlayer().hasMetadata("afk")) {
            return getPlayer().getMetadata("afk").get(0).asBoolean();
        }
        return false;
    }

    public void setIsInvseeing(boolean isInvSeeing) {
        this.isInvseeing = isInvSeeing;
    }

    public boolean isInvseeing() {
        return isInvseeing;
    }

    public boolean isVanished() {
        if ((getPlayer() != null) && getPlayer().hasMetadata("vanished")) {
            return getPlayer().getMetadata("vanished").get(0).asBoolean();
        }
        return false;
    }

    public int compareTo(CorePlayer other) {
        return this.getName().compareTo(other.getName());
    }
}
