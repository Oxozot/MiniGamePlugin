package fr.palapika.minigame.commands;

import fr.palapika.minigame.MiniGame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.Permission;

public class TrailCMD implements CommandExecutor {

    private MiniGame main;


    public TrailCMD(MiniGame main) {
        this.main = main;
    }



    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You have to be a player to execute this command");
            return true;
        }

        Player player = (Player) sender;

        if (label.equalsIgnoreCase("trail")){

            Inventory trailInv = Bukkit.createInventory(null, 27, "§dTrail Selector");

            for (int i=0; i<trailInv.getSize(); i++){
                trailInv.setItem(i, main.getItem(Material.RED_STAINED_GLASS_PANE, " ", false, 1));
            }

            trailInv.setItem(10, main.getItem(Material.LIGHT_BLUE_WOOL, "§3Blue", true, 1));
            trailInv.setItem(12, main.getItem(Material.RED_WOOL, "§4Red", true, 1));
            trailInv.setItem(14, main.getItem(Material.GREEN_WOOL, "§2Green", true, 1));
            trailInv.setItem(16, main.getItem(Material.PURPLE_WOOL, "§dPurple", true, 1));

            trailInv.setItem(26, main.getItem(Material.BARRIER, "§4OFF", false, 1));

            player.openInventory(trailInv);

        }

        return true;
    }
}
