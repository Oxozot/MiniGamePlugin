package fr.palapika.minigame.commands;

import fr.palapika.minigame.MiniGame;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ColorGameInitCmd implements CommandExecutor {

    private MiniGame main;
    public ColorGameInitCmd(MiniGame main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Material[] colors = {Material.LIGHT_BLUE_WOOL, Material.YELLOW_WOOL, Material.RED_WOOL, Material.GREEN_WOOL, Material.PURPLE_WOOL, Material.PINK_WOOL, Material.BLACK_WOOL, Material.ORANGE_WOOL};
        for (Material color: colors){
            main.getColorsWool().add(color);
        }

        if (cmd.getName().equalsIgnoreCase("colorgameinit")){
            //        coordonnee du terrain de jeu
            int x1 = 99;
            int z1 = 99;
            int x2 = 131;
            int z2 = 131;
            int y1 = 200;
            int y2 = 210;

            for (int i=x1; i<=x2; i++){
                for (int j=z1; j<=z2; j++){
                    Location blockLoc = new Location(main.world, i, 200, j);
                    Bukkit.getServer().getWorld("world").getBlockAt(blockLoc).setType(Material.WHITE_WOOL);
                    if (i == x1 || i == x2 || j == z1 || j == z2){
                        for (int k=y1; k<=y2; k++){
                            Location blockLoc2 = new Location(main.world, i, k, j);
                            Bukkit.getServer().getWorld("world").getBlockAt(blockLoc2).setType(Material.BARRIER);
                        }
                    }
                }
            }

        }

        return true;
    }
}
