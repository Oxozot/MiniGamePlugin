package fr.palapika.minigame.tasks;

import fr.palapika.minigame.GameStatesColorGame;
import fr.palapika.minigame.MiniGame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ColorGameStartTask extends BukkitRunnable {

    int timer = 3;

    private final MiniGame main;
    private final Location gameSpawn;

    // coordonnee du terrain de jeu
    private int x1 = 99;
    private int z1 = 99;
    private int x2 = 131;
    private int z2 = 131;


    public ColorGameStartTask(MiniGame main) {
        this.main = main;
        this.gameSpawn = new Location(main.world, 115.5, 203d, 115.5);
    }


    @Override
    public void run() {


        for (int i=x1; i<=x2; i++){
            for (int j=z1; j<z2; j++){
                Location blockLoc = new Location(main.world, i, 200, j);
                int colorBlockIndex = (int)(Math.random() * main.getColorsWool().size());
                Bukkit.getServer().getWorld("world").getBlockAt(blockLoc).setType(main.getColorsWool().get(colorBlockIndex));
            }
        }


        for (Player player: main.getColorGamePlayers()){
            player.setLevel(timer);
            player.sendTitle(ChatColor.GREEN + "Lancement dans: " + timer, null,0, 20, 0);
        }


        if (timer == 0){
            Bukkit.broadcastMessage(ChatColor.GREEN + "Lancement du Jeu !");
            main.setColorGameState(GameStatesColorGame.PLAYING);
            for (Player player: main.getColorGamePlayers()){
                player.sendTitle(ChatColor.GREEN + "Lancement du Jeu !", null,0, 20, 0);
                player.teleport(gameSpawn);
                int colorBlockIndex = (int)(Math.random() * main.getColorsWool().size());
                player.getInventory().setItem(4, main.getItem(main.getColorsWool().get(colorBlockIndex), "", false));
            }
            ColorGameCycle cycle = new ColorGameCycle(main);
            cycle.runTaskTimer(main, 0, 20);
            cancel();
        }
        timer--;
    }

}
