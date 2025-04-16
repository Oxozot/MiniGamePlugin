package fr.palapika.minigame.commands;

import fr.palapika.minigame.MiniGame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CircleCmd implements CommandExecutor {

    private MiniGame main;

    public CircleCmd(MiniGame main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You have to be a player to execute this command");
            return true;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("circle")){
            if (args.length != 2){
               player.sendMessage(ChatColor.RED + "Il faut 2 arguments pour executer cette cmd.");
               return true;
            }else {

                Location loc = new Location(Bukkit.getPlayer(args[0]).getWorld(), Bukkit.getPlayer(args[0]).getLocation().getX(), Bukkit.getPlayer(args[0]).getLocation().getY()-1, Bukkit.getPlayer(args[0]).getLocation().getZ());

                main.create3x3Block(loc, Material.getMaterial(args[1]));

            }
        }

        return true;
    }
}
