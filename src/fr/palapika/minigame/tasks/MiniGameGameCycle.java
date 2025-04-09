package fr.palapika.minigame.tasks;

import fr.palapika.minigame.GameStates;
import fr.palapika.minigame.MiniGame;
import org.bukkit.*;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class MiniGameGameCycle extends BukkitRunnable {

    private MiniGame main;
    private int timer = 0;

    public MiniGameGameCycle(MiniGame main) {
        this.main = main;
    }

    @Override
    public void run() {

        int min = 13;
        int max = 59;


        Random random = new Random();
        int x = random.nextInt(max - min + 1) + min;
        int z = random.nextInt(max - min + 1) + min;

        int y = 210;

        Location cowSpawn = new Location(Bukkit.getWorld("world"), x, y, z);
        Cow cow = (Cow) Bukkit.getWorld("world").spawnEntity(cowSpawn, EntityType.COW);
        main.cowTerrorist.add(cow);


        for (Player player: main.getPlayers()){
            if (player.getHealth() != 0 && timer == 30){
                player.getInventory().setItem( 2, main.getItem(Material.FEATHER, ChatColor.GREEN + "Jump Boost", true));
                player.updateInventory();
            } else if (player.getHealth() != 0 && timer == 45){
                player.getInventory().setItem( 2, main.getItem(Material.YELLOW_DYE, ChatColor.GREEN + "Speed Boost", true));
                player.updateInventory();
            }
        }

        if (main.getPlayers().isEmpty()) {
            main.killAndClearCows();
            main.setState(GameStates.FINISH);
            MiniGameFinishCycle finish = new MiniGameFinishCycle(main);
            cancel();
        }

        timer++;
    }
}
