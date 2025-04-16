package fr.palapika.minigame.tasks;

import fr.palapika.minigame.GameStatesColorGame;
import fr.palapika.minigame.MiniGame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class ColorGameCycle extends BukkitRunnable {

    private MiniGame main;

    public ColorGameCycle(MiniGame main) {
        this.main = main;
    }

    int timer = 0;

    int timer2 = 0;
    // coordonnee du terrain de jeu
    private int x1 = 99;
    private int z1 = 99;
    private int x2 = 131;
    private int z2 = 131;

    private Material tempMaterialColor;

    @Override
    public void run() {
        int colorBlockIndex = (int)(Math.random() * main.getColorsWool().size());

        if (timer == 30){
            ItemStack batonKnockback = main.getItem(Material.STICK, "§aBaton repousseur de Puant", true,1);
            ItemMeta bKnM = batonKnockback.getItemMeta();
            bKnM.addEnchant(Enchantment.KNOCKBACK, 3, true);
            batonKnockback.setItemMeta(bKnM);
            for (Player player: main.getColorGamePlayers()){
                player.sendTitle(ChatColor.GREEN + "AUGMENTATION DE LA DIFFICULTE", null,1, 40, 1);
                player.getInventory().addItem(batonKnockback);
                player.updateInventory();
            }
            for (int i=x1; i<=x2; i+=5){
                for (int j=z1; j<z2; j+=5){
                    Location blockLoc = new Location(main.world, i, 200, j);
                    int colorBlockIndex2 = (int)(Math.random() * main.getColorsWool().size());
                    main.create5x5Block(blockLoc, main.getColorsWool().get(colorBlockIndex2));
                }
            }
        }


        if (timer2 == 3){
            colorBlockIndex = (int)(Math.random() * main.getColorsWool().size());
        }


        for (Player player: main.getColorGamePlayers()){
            player.setLevel(timer2);

            if (player.getLocation().getY() < 201-10){
                player.getInventory().clear();
                main.getServer().dispatchCommand(main.getServer().getConsoleSender(), "kill " + player.getName());
            }


            if (timer2 == 5){

                for (int i=x1; i<=x2; i++){
                    for (int j=z1; j<z2; j++){
                        Location blockLoc = new Location(main.world, i, 200, j);
                        Material blockLocMaterial = Bukkit.getServer().getWorld("world").getBlockAt(blockLoc).getType();
                        if (blockLocMaterial == Material.AIR){
                            Bukkit.getServer().getWorld("world").getBlockAt(blockLoc).setType(tempMaterialColor);
                        }
                    }
                }
                timer2 = 0;
            }
            if (timer2 == 3){

                for (int i=x1; i<=x2; i++){
                    for (int j=z1; j<z2; j++){
                        Location blockLoc = new Location(main.world, i, 200, j);
                        Material blockLocMaterial = Bukkit.getServer().getWorld("world").getBlockAt(blockLoc).getType();
                        this.tempMaterialColor = player.getInventory().getItem(4).getType();
                        if (blockLocMaterial == player.getInventory().getItem(4).getType()){
                            Bukkit.getServer().getWorld("world").getBlockAt(blockLoc).setType(Material.AIR);
                        }
                    }
                }


                player.getInventory().setItem(4, main.getItem(main.getColorsWool().get(colorBlockIndex), "", false, 3));
            }
        }


        Bukkit.broadcastMessage(String.valueOf(timer2));


        timer++;
        timer2++;

        if (main.getColorGamePlayers().isEmpty()){
            main.setColorGameState(GameStatesColorGame.FINISH);
            ColorGameFinishCycle finishCycle = new ColorGameFinishCycle(main);
            finishCycle.runTaskTimer(main, 0, 20);
            cancel();
        }

    }

}
