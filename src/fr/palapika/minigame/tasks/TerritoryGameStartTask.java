package fr.palapika.minigame.tasks;

import fr.palapika.minigame.GameStates;
import fr.palapika.minigame.MiniGame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class TerritoryGameStartTask extends BukkitRunnable {

    private Location gameSpawn;

    private MiniGame main;

    public TerritoryGameStartTask(MiniGame main) {
        this.main = main;
        this.gameSpawn = new Location(main.world, 3, 223, 3);
    }

    private int countReadyPlayer = 0;
    private int timer = 3;
    private int nb = 0;

    private int countPlayer = 0;


    @Override
    public void run() {

        for (int i=0; i < main.getTerritoryGamePlayers().size(); i++){
            main.getConfig().set("territoryGame.player." + main.getTerritoryGamePlayers().get(i).getUniqueId().toString() + ".gold", main.gold);
            main.getConfig().set("territoryGame.player." + main.getTerritoryGamePlayers().get(i).getUniqueId().toString() + ".troops", main.troops);
            main.getConfig().set("territoryGame.player." + main.getTerritoryGamePlayers().get(i).getUniqueId().toString() + ".attaquePourcent", main.attaquePourcent);

            // createScoreboardTerritoryManager(Player player, int gold, int troops, int attaquePourcent, String team)

            main.createScoreboardTerritoryManager(main.getTerritoryGamePlayers().get(i), main.gold, main.troops, main.attaquePourcent, main.getColorsWool().get(i).toString());

            main.territoryPlayerTeam.put(main.getTerritoryGamePlayers().get(i), main.getColorsWool().get(i));

        }

        if (countPlayer < main.getTerritoryGamePlayers().size()) {
            for (Player player: main.getTerritoryGamePlayers()) {

                player.teleport(gameSpawn);
                countPlayer++;

            }
        }


        for (Player player: main.getTerritoryGamePlayers()){

            if (main.getConfig().contains("territoryGame.player." + player.getUniqueId().toString() + ".territory") &&
                    main.getConfig().getInt("territoryGame.player." + player.getUniqueId().toString() + ".territory.y") == 221){
                main.world.getBlockAt(main.getConfig().getInt("territoryGame.player." + player.getUniqueId().toString() + ".territory.x"),
                        main.getConfig().getInt("territoryGame.player." + player.getUniqueId().toString() + ".territory.y"),
                        main.getConfig().getInt("territoryGame.player." + player.getUniqueId().toString() + ".territory.z") ).setType(main.territoryPlayerTeam.get(player));
                countReadyPlayer++;
                Bukkit.broadcastMessage("Joueur pret");
            }
        }


        if (countReadyPlayer >=1 && countReadyPlayer < 8) {
            List<Material> colors = main.getColorsWool(); // ou List<Material> selon ce que c'est
            for (Player player: main.getTerritoryGamePlayers()) {

                player.setLevel(timer);
                if (timer == 0){
                    player.sendTitle(ChatColor.GREEN + "Lancement du Jeu !", null,0, 20, 0);
                    player.getInventory().clear();

                    player.getInventory().addItem(main.getItem(Material.STICK, "§5Attack Stick", true, 1));
                    player.getInventory().addItem(main.getItem(Material.CARROT_ON_A_STICK, "§5Manage Territory", true, 1));

                    TerritoryGameCycle cycle = new TerritoryGameCycle(main);
                    cycle.runTaskTimer(main, 0, 20);
                    cancel();
                }

                nb++;
                // creer la boucle du jeu et la lancer

            }

            timer--;
        }

    }
}
