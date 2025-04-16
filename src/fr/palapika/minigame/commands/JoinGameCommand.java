package fr.palapika.minigame.commands;

import fr.palapika.minigame.GameStates;
import fr.palapika.minigame.MiniGame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class JoinGameCommand implements CommandExecutor {

    private MiniGame main;

    public JoinGameCommand(MiniGame main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You have to be a player to execute this command");
        } else {
            Player player = (Player) sender;
            Inventory gameSelectorInv = Bukkit.createInventory(null, 3 * 9, "§dGameSelector");
            for (int i=0; i < 27; i++){
                gameSelectorInv.setItem(i, main.getItem(Material.BLACK_STAINED_GLASS_PANE, " ", false,1));
            }
            gameSelectorInv.setItem(10, main.getItem(Material.TNT, "§5Vache explosive", false,1));
            gameSelectorInv.setItem(12, main.getItem(Material.LIGHT_BLUE_WOOL, "§6Color Game", true,1));

            player.openInventory(gameSelectorInv);

        }


        return true;
    }
}
