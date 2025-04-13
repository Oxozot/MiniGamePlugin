package fr.palapika.minigame;

import fr.palapika.minigame.commands.ColorGameInitCmd;
import fr.palapika.minigame.commands.JoinGameCommand;
import fr.palapika.minigame.commands.StartCommand;
import fr.palapika.minigame.manager.DamageListeners;
import fr.palapika.minigame.manager.MiniGameListeners;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

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



    private GameStates state;
    private GameStatesColorGame colorGameState;

    @Override
    public void onEnable(){
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "le serv va bien");
        setState(GameStates.WAITING);
        setColorGameState(GameStatesColorGame.WAITING);
        getCommand("joingame").setExecutor(new JoinGameCommand(this));
        getCommand("start").setExecutor(new StartCommand(this));
        getCommand("colorgameinit").setExecutor(new ColorGameInitCmd(this));

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






    public ItemStack getItem(Material material, String name , boolean enchantEffect){
        ItemStack item = new ItemStack(material, 1);
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

}
