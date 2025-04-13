package fr.palapika.minigame.tasks;

import fr.palapika.minigame.GameStates;
import fr.palapika.minigame.MiniGame;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
        // tnt 1 spawn
        int x = random.nextInt(max - min + 1) + min;
        int z = random.nextInt(max - min + 1) + min;

        // tnt 2 spawn
        int x2 = random.nextInt(max - min + 1) + min;
        int z2 = random.nextInt(max - min + 1) + min;


        // tnt 3 spawn
        int x3 = random.nextInt(max - min + 1) + min;
        int z3 = random.nextInt(max - min + 1) + min;

        // tnt 4 spawn
        int x4 = random.nextInt(max - min + 1) + min;
        int z4 = random.nextInt(max - min + 1) + min;

        int y = 210;

        Location cowSpawn = new Location(Bukkit.getWorld("world"), x, y, z);
        Location tntSpawn = new Location(Bukkit.getWorld("world"), x2, y, z2);
        Location tnt3Spawn = new Location(Bukkit.getWorld("world"), x3, y, z3);
        Location tnt4Spawn = new Location(Bukkit.getWorld("world"), x4, y, z4);
        //Cow cow = (Cow) Bukkit.getWorld("world").spawnEntity(cowSpawn, EntityType.COW);
//        TNTPrimed tntPrimed = (TNTPrimed) Bukkit.getWorld("world").spawnEntity(cowSpawn, EntityType.PRIMED_TNT);

        if (timer < 15){
            TNTPrimed tntPrimed = (TNTPrimed) Bukkit.getWorld("world").spawnEntity(cowSpawn, EntityType.PRIMED_TNT);
        } else if (timer >= 15 && timer < 30) {
            TNTPrimed tntPrimed = (TNTPrimed) Bukkit.getWorld("world").spawnEntity(cowSpawn, EntityType.PRIMED_TNT);
            TNTPrimed tntPrimed2 = (TNTPrimed) Bukkit.getWorld("world").spawnEntity(tntSpawn, EntityType.PRIMED_TNT);
        } else if (timer >= 30 && timer < 45){
            TNTPrimed tntPrimed = (TNTPrimed) Bukkit.getWorld("world").spawnEntity(cowSpawn, EntityType.PRIMED_TNT);
            TNTPrimed tntPrimed2 = (TNTPrimed) Bukkit.getWorld("world").spawnEntity(tntSpawn, EntityType.PRIMED_TNT);
            TNTPrimed tntPrimed3 = (TNTPrimed) Bukkit.getWorld("world").spawnEntity(tnt3Spawn, EntityType.PRIMED_TNT);
        }else if (timer >= 45){
            TNTPrimed tntPrimed = (TNTPrimed) Bukkit.getWorld("world").spawnEntity(cowSpawn, EntityType.PRIMED_TNT);
            TNTPrimed tntPrimed2 = (TNTPrimed) Bukkit.getWorld("world").spawnEntity(tntSpawn, EntityType.PRIMED_TNT);
            TNTPrimed tntPrimed3 = (TNTPrimed) Bukkit.getWorld("world").spawnEntity(tnt3Spawn, EntityType.PRIMED_TNT);
            TNTPrimed tntPrimed4 = (TNTPrimed) Bukkit.getWorld("world").spawnEntity(tnt4Spawn, EntityType.PRIMED_TNT);
        }


        for (Player player: main.getPlayers()){
            player.setLevel(timer);
            if (timer == 15){
                player.getInventory().addItem( main.getItem(Material.YELLOW_DYE, "§aSpeed Boost", true));
                player.updateInventory();
            }
            if (timer == 30){
                player.getInventory().addItem( main.getItem(Material.FEATHER, "§aJump Boost", true));
                player.updateInventory();
            } else if (timer == 45){

                player.sendTitle(ChatColor.LIGHT_PURPLE + "Bataille Final !", null,0, 20, 0);

                player.getInventory().addItem( main.getItem(Material.YELLOW_DYE, "§aSpeed Boost", true));
                // baton knockback 5
                ItemStack batonKnockback = main.getItem(Material.STICK, "§aBaton repousseur de Puant", true);
                ItemMeta bKnM = batonKnockback.getItemMeta();
                bKnM.addEnchant(Enchantment.KNOCKBACK, 5, true);
                batonKnockback.setItemMeta(bKnM);
                player.getInventory().addItem(batonKnockback);

                player.updateInventory();
            }
        }

        if (main.getPlayers().isEmpty()) {
            main.setState(GameStates.FINISH);
            MiniGameFinishCycle finish = new MiniGameFinishCycle(main);
            finish.runTaskTimer(main, 0, 20);
            cancel();
        }

        timer++;
    }




}
