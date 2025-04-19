package fr.palapika.minigame.commands;

import fr.palapika.minigame.MiniGame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.*;


public class TerritoryCMD implements CommandExecutor {


    private MiniGame main;

    public TerritoryCMD(MiniGame main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player))sender.sendMessage(ChatColor.RED + "You have to be a player to execute this command");

        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("territory")){

            ItemStack territorySelector = main.getItem(Material.BLAZE_ROD, "§5Territory Selector", true, 1);
            // createScoreboardTerritoryManager(Player player, int gold, int troops, int attaquePourcent, String team)
            main.createScoreboardTerritoryManager(player, main.gold, main.troops, main.attaquePourcent, "Blue");

            player.getInventory().addItem(territorySelector);

        }



        return true;
    }
}
