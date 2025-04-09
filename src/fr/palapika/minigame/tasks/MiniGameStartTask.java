package fr.palapika.minigame.tasks;

import fr.palapika.minigame.GameStates;
import fr.palapika.minigame.MiniGame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Timer;

public class MiniGameStartTask extends BukkitRunnable {

    int timer = 3;

    private MiniGame main;

    public MiniGameStartTask(MiniGame main) {
        this.main = main;
    }

    World world = Bukkit.getWorld("world");

    Location gameSpawn = new Location(world, 34.5, 203d, 36.5);


    @Override
    public void run() {

        for (Player player: main.getPlayers()){
            player.setLevel(timer);
            player.sendTitle(ChatColor.GREEN + "Lancement dans: " + timer, null,0, 20, 0);
        }


        if (timer == 0){
            Bukkit.broadcastMessage(ChatColor.GREEN + "Lancement du Jeu !");
            main.setState(GameStates.PLAYING);
            for (Player player: main.getPlayers()){
                player.sendTitle(ChatColor.GREEN + "Lancement du Jeu !", null,0, 20, 0);
                player.teleport(gameSpawn);
            }
            MiniGameGameCycle cycle = new MiniGameGameCycle(main);
            cycle.runTaskTimer(main, 0, 20);
            cancel();
        }
        timer--;
    }

}
