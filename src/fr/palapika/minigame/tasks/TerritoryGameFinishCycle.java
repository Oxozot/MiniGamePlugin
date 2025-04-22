package fr.palapika.minigame.tasks;

import fr.palapika.minigame.MiniGame;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TerritoryGameFinishCycle extends BukkitRunnable {

    private MiniGame main;

    public TerritoryGameFinishCycle(MiniGame main) {
        this.main = main;
    }

    Location spawnLocation = new Location(main.world, main.getConfig().getDouble("spawn.x"), main.getConfig().getInt("spawn.y"), main.getConfig().getDouble("spawn.z"));

    @Override
    public void run() {

        for (Player player: main.getTerritoryGamePlayers()){

            player.getInventory().clear();
            player.teleport(spawnLocation);

        }

    }
}
