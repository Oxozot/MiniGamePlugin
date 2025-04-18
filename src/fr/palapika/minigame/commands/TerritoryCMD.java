package fr.palapika.minigame.commands;

import fr.palapika.minigame.MiniGame;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


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

            player.getInventory().addItem(territorySelector);
        }



        return true;
    }
}
