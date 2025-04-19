package fr.palapika.minigame.tasks;

import fr.palapika.minigame.MiniGame;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class TerritoryGameStartTask extends BukkitRunnable {

    private Location gameSpawn;

    private MiniGame main;

    public TerritoryGameStartTask(MiniGame main) {
        this.main = main;
        this.gameSpawn = new Location(main.world, 3, 223, 3);
    }

    private int countReadyPlayer = 0;
    private int timer = 3;

    @Override
    public void run() {

        for (int i=0; i < main.getTerritoryGamePlayers().size(); i++){
            main.getConfig().set("territoryGame.player." + main.getTerritoryGamePlayers().get(i).getUniqueId().toString() + ".gold", main.gold);
            main.getConfig().set("territoryGame.player." + main.getTerritoryGamePlayers().get(i).getUniqueId().toString() + ".troops", main.troops);
            main.getConfig().set("territoryGame.player." + main.getTerritoryGamePlayers().get(i).getUniqueId().toString() + ".attaquePourcent", main.attaquePourcent);

            // createScoreboardTerritoryManager(Player player, int gold, int troops, int attaquePourcent, String team)

            main.createScoreboardTerritoryManager(main.getTerritoryGamePlayers().get(i), main.gold, main.troops, main.attaquePourcent, main.getColorsWool().get(i).toString());
        }

        for (Player player: main.getTerritoryGamePlayers()){
            ItemStack territorySelector = main.getItem(Material.BLAZE_ROD, "§5Territory Selector", true, 1);
            player.getInventory().addItem(territorySelector);


            if (main.getConfig().contains("player." + player.getUniqueId().toString() + ".territory")){
                countReadyPlayer++;
            }
        }

        if (countReadyPlayer == main.getTerritoryGamePlayers().size()){

            for (Player player: main.getTerritoryGamePlayers()){
                player.setLevel(timer);
                if (timer == 0){
                    player.sendTitle(ChatColor.GREEN + "Lancement du Jeu !", null,0, 20, 0);
                    player.teleport(gameSpawn);
                }
                // creer la boucle du jeu et la lancer
                TerritoryGameCycle cycle = new TerritoryGameCycle(main);
                cycle.runTaskTimer(main, 0, 20);
                cancel();
            }

            timer--;

        }

    }
}
