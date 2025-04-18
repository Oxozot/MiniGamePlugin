package fr.palapika.minigame.tasks;

import fr.palapika.minigame.MiniGame;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TerritoryGameStartTask extends BukkitRunnable {

    private MiniGame main;

    public TerritoryGameStartTask(MiniGame main) {
        this.main = main;
    }

    private int countReadyPlayer = 0;
    private int timer = 3;

    private Location gameSpawn = new Location(main.world, 3, 223, 3);


    @Override
    public void run() {



        for (Player player: main.getTerritoryGamePlayers()){
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
            }

            timer--;

        }

    }
}
