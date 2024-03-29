package koala.dev.freerank.commands.freerank;

import koala.dev.freerank.FreeRank;
import koala.dev.freerank.commands.FreeRankAdmin;
import koala.dev.freerank.utils.CC;
import koala.dev.freerank.utils.commands.Command;
import koala.dev.freerank.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreeRankCommand {
    @Command(name = "freerank")
    public void onCommand(CommandArgs cmd){
        CommandSender sender = cmd.getSender();
        Player player = cmd.getPlayer();
        String[] args = cmd.getArgs();
        FreeRankAdmin whitelist = FreeRank.get().getfreeRankAdmin();
        if (FreeRank.get().getMainConfig().getBoolean("CAN-CLAIM")) {
            if (!whitelist.isReclaimed(player.getName())) {
                Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), FreeRank.get().getMainConfig().getString("COMMAND")
                        .replace("%player%", player.getName())
                        .replace("%rank%", FreeRank.get().getMainConfig().getString("RANK")));
                Bukkit.broadcastMessage(CC.translate(FreeRank.get().getMainConfig().getString("MESSAGE.CLAIMED")));
                whitelist.addReclaimed(player.getName());
            } else {
                player.sendMessage(CC.translate(FreeRank.get().getMainConfig().getString("MESSAGE.ALREADY")));
            }
        } else {
            player.sendMessage(CC.translate(FreeRank.get().getMainConfig().getString("MESSAGE.DISABLED")));
        }
    }
}

