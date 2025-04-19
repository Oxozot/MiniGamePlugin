package fr.palapika.minigame.tasks;

import fr.palapika.minigame.MiniGame;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

public class TerritoryGameCycle extends BukkitRunnable {

    private MiniGame main;

    public TerritoryGameCycle(MiniGame main) {
        this.main = main;
    }

    Material[] colors = {Material.LIGHT_BLUE_WOOL, Material.YELLOW_WOOL, Material.RED_WOOL, Material.GREEN_WOOL, Material.PURPLE_WOOL, Material.PINK_WOOL, Material.BLACK_WOOL, Material.ORANGE_WOOL};



    @Override
    public void run() {



    }
}
