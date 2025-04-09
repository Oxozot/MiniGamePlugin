package fr.palapika.minigame.manager;

import fr.palapika.minigame.MiniGame;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListeners implements Listener {

    private MiniGame main;

    public DamageListeners(MiniGame main) {
        this.main = main;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        Entity victim = event.getEntity();

        if (victim instanceof Player){
            Player player = (Player) victim;
            if (main.getPlayers().contains(player)){
                if (player.getHealth() <= event.getDamage()){
                    event.setDamage(0);
                    main.getPlayers().remove(player);
                    main.getDeadPlayers().add(player);

                    player.setGameMode(GameMode.SPECTATOR);
/*
                    pour kill toutes les entites sauf les joueurs
                    main.getServer().dispatchCommand(main.getServer().getConsoleSender(), "kill @e[type=!minecraft:player]");

 */
                }
            }

        }
    }

}
