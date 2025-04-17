package fr.palapika.minigame;

import fr.palapika.minigame.commands.*;
import fr.palapika.minigame.manager.DamageListeners;
import fr.palapika.minigame.manager.MiniGameListeners;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class MiniGame extends JavaPlugin {


    public World world = Bukkit.getWorld("world");
    public Location spawnLocation = new Location(world, 4, 201+1d, 4);

    public Location spawnTntGameLoc = new Location(world, 4, 206, 4);
    public Location spawnColorGameLoc = new Location(world, 4, 210, 4);

// tntGame
    public List<Player> players = new ArrayList<>();
    public List<Player> deadPlayers = new ArrayList<>();
// colorGame
    public List<Player> colorGamePlayers = new ArrayList<>();
    public List<Player> colorGameDeadPlayers = new ArrayList<>();
    public List<Material> colorsWool = new ArrayList<>();


    private FileConfiguration dataConfig = null;
    private File configFile = null;



    private GameStates state;
    private GameStatesColorGame colorGameState;

    @Override
    public void onEnable(){
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "le serv va bien");
        setState(GameStates.WAITING);
        setColorGameState(GameStatesColorGame.WAITING);

        saveDefaultConfig();

        getConfig().set("message", "message de la config");
        saveConfig();

        getCommand("joingame").setExecutor(new JoinGameCommand(this));
        getCommand("start").setExecutor(new StartCommand(this));
        getCommand("colorgameinit").setExecutor(new ColorGameInitCmd(this));
        getCommand("circle").setExecutor(new CircleCmd(this));
        getCommand("broadcast").setExecutor(new BroadcastCMD(this));

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents( new MiniGameListeners(this), this);
        pm.registerEvents( new DamageListeners(this), this);
        getServer().dispatchCommand(getServer().getConsoleSender(), "colorgameinit");

    }


    @Override
    public void onDisable(){
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "le serv est en pls");
    }
// tntGame
    public void setState(GameStates state){
        this.state = state;
    }

    public boolean isState(GameStates state){
        return this.state == state;
    }

    public List<Player> getPlayers(){
        return players;
    }

    public List<Player> getDeadPlayers(){
        return deadPlayers;
    }
// colorGame
    public void setColorGameState(GameStatesColorGame state){
        this.colorGameState = state;
    }

    public boolean isColorGameState(GameStatesColorGame state){
        return this.colorGameState == state;
    }

    public List<Player> getColorGamePlayers(){
        return colorGamePlayers;
    }

    public List<Player> getColorGameDeadPlayers(){
        return colorGameDeadPlayers;
    }

    public List<Material> getColorsWool(){
        return colorsWool;
    }






    public ItemStack getItem(Material material, String name , boolean enchantEffect, int nbItem){
        ItemStack item = new ItemStack(material, nbItem);
        ItemMeta itemM = item.getItemMeta();
        itemM.setDisplayName(name);
        if (enchantEffect){
            itemM.addEnchant(Enchantment.IMPALING, 1, true);
            itemM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(itemM);
            return item;
        }else {
            item.setItemMeta(itemM);
            return item;
        }
    }


    public static void createCircle(Location center, int radius, Material block, Player player) {
        World world = center.getWorld();
        int x = center.getBlockX();
        int y = center.getBlockY();
        int z = center.getBlockZ();

        int radiusSquare = radius*radius;
        for(int blockX = -radius; blockX < radius; blockX++) {
            for(int blockZ = -radius; blockZ < radius; blockZ++) {
                if((blockX*blockX + blockZ*blockZ) <= radiusSquare) {
                    world.getBlockAt(x + blockX,y,z + blockZ).setType(block);
                }
            }
        }
    }

    public static void create3x3Block(Location center, Material block){
        World world = center.getWorld();
        int x = (int) center.getX();
        int y = (int) center.getY();
        int z = (int) center.getZ();

        for (int i=z-1; i<z+2; i++){
            world.getBlockAt(x - 1,y , i).setType(block);
            world.getBlockAt(x + 1,y , i).setType(block);
            world.getBlockAt(x, y, i).setType(block);
        }

    }

    public static void create5x5Block(Location center, Material block){
        World world = center.getWorld();
        int x = (int) center.getX();
        int y = (int) center.getY();
        int z = (int) center.getZ();

        for (int i=z-2; i<=z+2; i++){
            world.getBlockAt(x - 2,y , i).setType(block);
            world.getBlockAt(x - 1,y , i).setType(block);
            world.getBlockAt(x + 1,y , i).setType(block);
            world.getBlockAt(x + 2,y , i).setType(block);
            world.getBlockAt(x, y, i).setType(block);
        }

    }

    public void reloadConfig(){
        if (this.configFile == null)
            this.configFile = new File(this.getDataFolder(), "data.yml");

        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defaultStream = this.getResource("data.yml");
        if (defaultStream != null){
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig(){
        if (this.dataConfig == null)
            reloadConfig();

        return this.dataConfig;
    }

    public void saveConfig(){
        if (this.dataConfig ==null || this.configFile == null) return;

        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            this.getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, e);
        }
    }

    public void saveDefaultConfig(){
        if (this.configFile == null)
            this.configFile = new File(this.getDataFolder(), "data.yml");

        if (!this.configFile.exists()){
            this.saveResource("data.yml", false);
        }
    }

}
