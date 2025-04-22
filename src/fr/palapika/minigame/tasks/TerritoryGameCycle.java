package fr.palapika.minigame.tasks;

import fr.palapika.minigame.MiniGame;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class TerritoryGameCycle extends BukkitRunnable {

    private MiniGame main;

    public TerritoryGameCycle(MiniGame main) {
        this.main = main;
    }

    Material[] colors = {Material.LIGHT_BLUE_WOOL, Material.YELLOW_WOOL, Material.RED_WOOL, Material.GREEN_WOOL, Material.PURPLE_WOOL, Material.PINK_WOOL, Material.BLACK_WOOL, Material.ORANGE_WOOL};

    private Map<Player, Location> playerTerritory = new HashMap<>();

    private int timer = 0;

    @Override
    public void run() {
        Bukkit.broadcastMessage("jeu en cours " + timer);

        for (Player player: main.getTerritoryGamePlayers()){

            if (main.getConfig().contains("territoryGame.player." + player.getUniqueId().toString() + ".attackPoint.z")){
                int playerTerritoryX = main.getConfig().getInt("territoryGame.player." + player.getUniqueId().toString() + ".territory.x");
                int playerTerritoryZ = main.getConfig().getInt("territoryGame.player." + player.getUniqueId().toString() + ".territory.z");
                int territoryY = 221;

                int playerAttackX = main.getConfig().getInt("territoryGame.player." + player.getUniqueId().toString() + ".attackPoint.x");
                int playerAttackZ = main.getConfig().getInt("territoryGame.player." + player.getUniqueId().toString() + ".attackPoint.z");

                for (int i = playerTerritoryX; i <= playerAttackX; i++) {
                    for (int j = playerTerritoryZ; j <= playerAttackZ; j++){
                        main.world.getBlockAt(i, territoryY, j).setType(main.territoryPlayerTeam.get(player));
                    }
                }

                for (int h = playerTerritoryX; h >= playerAttackX; h--) {
                    for (int k = playerTerritoryZ; k >= playerAttackZ; k--){
                        main.world.getBlockAt(h, territoryY, k).setType(main.territoryPlayerTeam.get(player));
                    }
                }

                for (int h = playerTerritoryX; h >= playerAttackX; h--) {
                    for (int k = playerTerritoryZ; k <= playerAttackZ; k++){
                        main.world.getBlockAt(h, territoryY, k).setType(main.territoryPlayerTeam.get(player));
                    }
                }

                for (int h = playerTerritoryX; h <= playerAttackX; h++) {
                    for (int k = playerTerritoryZ; k >= playerAttackZ; k--){
                        main.world.getBlockAt(h, territoryY, k).setType(main.territoryPlayerTeam.get(player));
                    }
                }


            }

        }




        timer++;

        if (main.getTerritoryGamePlayers().isEmpty()){
            TerritoryGameFinishCycle finishCycle = new TerritoryGameFinishCycle(main);
            cancel();
        }

    }
}
