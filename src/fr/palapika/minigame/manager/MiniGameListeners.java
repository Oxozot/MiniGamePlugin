package fr.palapika.minigame.manager;

import fr.palapika.minigame.MiniGame;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MiniGameListeners implements Listener {

    private static MiniGame main;


    public MiniGameListeners(MiniGame main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();

        player.teleport(main.spawnLocation);
        player.getInventory().clear();
        player.setHealth(20d);
        player.setFoodLevel(20);

        player.getInventory().addItem(main.getItem(Material.COMPASS, "§dGameSelector", true));

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){

    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item.getType() == Material.COMPASS && item.getItemMeta().getDisplayName().equals("§dGameSelector")) {

            Inventory gameSelectorInv = Bukkit.createInventory(null, 3*9, "§dGameSelector");

            gameSelectorInv.setItem(10 , main.getItem(Material.TNT, "§5Vache explosive", false));

            player.openInventory(gameSelectorInv);

        }

    }

    @EventHandler
    public static void InventoryClickEvent(InventoryClickEvent event){
        Inventory inv = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();

        if (event.getView().getTitle().equals("§dGameSelector")){
            if (current.getType() == Material.TNT && current.getItemMeta().getDisplayName().equals("§5Vache explosive")){
                main.getPlayers().add(player);
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(ChatColor.GREEN + "Vous avez bien rejoins le jeu");
                player.sendTitle(ChatColor.GREEN + "Vous avez bien rejoins le jeu", null,1, 40, 1);
                event.setCancelled(true);
                player.getInventory().clear();
                player.closeInventory();
            }
        }

    }

}
