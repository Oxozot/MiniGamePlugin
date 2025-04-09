package fr.palapika.minigame.manager;

import fr.palapika.minigame.MiniGame;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MiniGameListeners implements Listener {

    private MiniGame main;


    public MiniGameListeners(MiniGame main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();
        World world = Bukkit.getWorld("world");
        Location spawnLocation = new Location(world, 0.5, world.getHighestBlockYAt(0, 0)+1d, 0.5);
        player.teleport(spawnLocation);
        player.getInventory().clear();
        player.setHealth(20d);
        player.setFoodLevel(20);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){

    }

}
