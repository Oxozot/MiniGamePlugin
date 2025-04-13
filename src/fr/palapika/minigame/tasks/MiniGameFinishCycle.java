package fr.palapika.minigame.tasks;

import fr.palapika.minigame.GameStates;
import fr.palapika.minigame.MiniGame;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MiniGameFinishCycle extends BukkitRunnable {

    private MiniGame main;

    public MiniGameFinishCycle(MiniGame main) {
        this.main = main;
    }

    @Override
    public void run() {

        if (main.isState(GameStates.FINISH)){
            main.getServer().dispatchCommand(main.getServer().getConsoleSender(), "kill @e[type=!minecraft:player]");
            for (Player deadPlayer: main.getDeadPlayers()){
                deadPlayer.teleport(main.spawnLocation);
                deadPlayer.setGameMode(GameMode.ADVENTURE);
                deadPlayer.getInventory().clear();
                deadPlayer.setLevel(0);
                deadPlayer.setHealth(20d);
                deadPlayer.setFoodLevel(20);
                deadPlayer.getInventory().addItem(main.getItem(Material.COMPASS, "§dGameSelector", true));
            }
            main.setState(GameStates.WAITING);
        }

    }
}
