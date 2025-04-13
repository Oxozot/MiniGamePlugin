package fr.palapika.minigame.tasks;

import fr.palapika.minigame.MiniGame;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ColorGameCycle extends BukkitRunnable {

    private MiniGame main;

    public ColorGameCycle(MiniGame main) {
        this.main = main;
    }

    int timer = 0;

    // coordonnee du terrain de jeu
    private int x1 = 99;
    private int z1 = 99;
    private int x2 = 131;
    private int z2 = 131;

    @Override
    public void run() {

        for (Player player: main.getColorGamePlayers()){
            player.setLevel(timer);
            float timerModulo5 = timer % 5;
            if (timer >=5 && timerModulo5 == 0){

                for (int i=x1; i<=x2; i++){
                    for (int j=z1; j<z2; j++){
                        Location blockLoc = new Location(main.world, i, 200, j);
                        Material blockLocMaterial = Bukkit.getServer().getWorld("world").getBlockAt(blockLoc).getType();
                        if (blockLocMaterial == player.getInventory().getItem(4).getType()){
                            Bukkit.getServer().getWorld("world").getBlockAt(blockLoc).setType(Material.AIR);
                        }
                    }
                }

                //player.getInventory().getItem(4)
                // suppression des blocks d'une certaine couleur toutes les 5s
                int colorBlockIndex = (int)(Math.random() * main.getColorsWool().size());
                player.getInventory().setItem(4, main.getItem(main.getColorsWool().get(colorBlockIndex), "", false));
            }
        }


        timer++;
    }
}
