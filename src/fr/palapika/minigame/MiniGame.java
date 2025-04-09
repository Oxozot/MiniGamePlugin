package fr.palapika.minigame;

import fr.palapika.minigame.commands.JoinGameCommand;
import fr.palapika.minigame.commands.StartCommand;
import fr.palapika.minigame.manager.DamageListeners;
import fr.palapika.minigame.manager.MiniGameListeners;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MiniGame extends JavaPlugin {

    public World world = Bukkit.getWorld("world");
    public Location spawnLocation = new Location(world, 0.5, world.getHighestBlockYAt(0, 0)+1d, 0.5);

    public List<Player> players = new ArrayList<>();
    public List<Player> deadPlayers = new ArrayList<>();

    public List<Cow> cowTerrorist = new ArrayList<>();

    private GameStates state;

    @Override
    public void onEnable(){
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "le serv va bien");
        setState(GameStates.WAITING);
        getCommand("joingame").setExecutor(new JoinGameCommand(this));
        getCommand("start").setExecutor(new StartCommand(this));

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents( new MiniGameListeners(this), this);
        pm.registerEvents( new DamageListeners(this), this);

    }


    @Override
    public void onDisable(){
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "le serv est en pls");
    }

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

    public List<Cow> getCows(){
        return cowTerrorist;
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

    public void killAndClearCows() {
        Iterator<Cow> iterator = cowTerrorist.iterator(); // ou main.getCows().iterator() si c'est ailleurs
        while (iterator.hasNext()) {
            Cow cow = iterator.next();
            cow.setHealth(0);
            iterator.remove();
        }
    }

}
