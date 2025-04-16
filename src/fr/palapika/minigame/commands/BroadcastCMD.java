package fr.palapika.minigame.commands;

import fr.palapika.minigame.MiniGame;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class BroadcastCMD implements CommandExecutor {

    private MiniGame main;

    public BroadcastCMD(MiniGame main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("broadcast")){
            Bukkit.broadcastMessage(main.getConfig().getString("message"));
            return true;
        }

        return true;
    }
}
