package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_UserInfo;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

@CommandPermissions(level = ADMIN_LEVEL.SUPER, source = SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions = false)
public class Command_orbit extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 0)
        {
            return false;
        }

        Player p;
        try
        {
            p = getPlayer(args[0]);
        }
        catch (CantFindPlayerException ex)
        {
            sender.sendMessage(ex.getMessage());
            return true;
        }

        TFM_UserInfo playerdata = TFM_UserInfo.getPlayerData(p);

        double strength = 10.0;

        if (args.length >= 2)
        {
            if (TFM_Util.isStopCommand(args[1]))
            {
                sender.sendMessage(ChatColor.GRAY + "Stopped orbiting " + p.getName());
                playerdata.stopOrbiting();
                return true;
            }

            try
            {
                strength = Math.max(1.0, Math.min(150.0, Double.parseDouble(args[1])));
            }
            catch (NumberFormatException ex)
            {
                sender.sendMessage(ex.getMessage());
                return true;
            }
        }

        p.setGameMode(GameMode.SURVIVAL);
        playerdata.startOrbiting(strength);

        p.setVelocity(new Vector(0, strength, 0));
        TFM_Util.adminAction(sender.getName(), "Orbiting " + p.getName() + ".", false);

        return true;
    }
}
