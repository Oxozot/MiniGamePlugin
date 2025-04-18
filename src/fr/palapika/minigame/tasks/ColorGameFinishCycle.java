package fr.palapika.minigame.tasks;

import fr.palapika.minigame.GameStates;
import fr.palapika.minigame.GameStatesColorGame;
import fr.palapika.minigame.MiniGame;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ColorGameFinishCycle extends BukkitRunnable {

    private MiniGame main;

    public ColorGameFinishCycle(MiniGame main) {
        this.main = main;
    }

    @Override
    public void run() {

        Location spawnLocation = new Location(main.world, main.getConfig().getDouble("message.x"), main.getConfig().getDouble("message.y"), main.getConfig().getDouble("message.z"));

        if (main.isColorGameState(GameStatesColorGame.FINISH)){
            for (Player deadPlayer: main.getColorGamePlayers()){
                deadPlayer.teleport(spawnLocation);
                deadPlayer.setGameMode(GameMode.ADVENTURE);
                deadPlayer.getInventory().clear();
                deadPlayer.setLevel(0);
                deadPlayer.setHealth(20d);
                deadPlayer.setFoodLevel(20);
                deadPlayer.getInventory().addItem(main.getItem(Material.COMPASS, "§dGameSelector", true, 1));
            }
            main.setColorGameState(GameStatesColorGame.WAITING);
        }

    }
}
