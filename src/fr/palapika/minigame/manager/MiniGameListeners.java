package fr.palapika.minigame.manager;

import fr.palapika.minigame.GameStates;
import fr.palapika.minigame.GameStatesColorGame;
import fr.palapika.minigame.GameStatesTerritoryGame;
import fr.palapika.minigame.MiniGame;
import fr.palapika.minigame.tasks.ColorGameStartTask;
import fr.palapika.minigame.tasks.MiniGameStartTask;
import fr.palapika.minigame.tasks.TerritoryGameStartTask;
import org.bukkit.*;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;

public class MiniGameListeners implements Listener {

    private static MiniGame main;


    public MiniGameListeners(MiniGame main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();

        Location spawnLocation = new Location(main.world, main.getConfig().getDouble("spawn.x"), main.getConfig().getInt("spawn.y"), main.getConfig().getDouble("spawn.z"));
        //Location spawnLocation = new Location(main.world, 5, 202, 5);

        player.teleport(spawnLocation);
        player.getInventory().clear();
        player.setLevel(0);
        player.setHealth(20d);
        player.setFoodLevel(20);
        main.getServer().dispatchCommand(main.getServer().getConsoleSender(), "effect clear " + player.getName());
        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 999999, 0, false, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 3, false, false, false));

        player.getInventory().addItem(main.getItem(Material.COMPASS, "§dGameSelector", true,1));

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if (main.getPlayers().contains(player)){
            main.getPlayers().remove(player);
        } else if (main.getDeadPlayers().contains(player)) {
            main.getDeadPlayers().remove(player);
        } else if (main.getColorGamePlayers().contains(player)) {
            main.getColorGamePlayers().remove(player);
        } else if (main.getColorGameDeadPlayers().contains(player)) {
            main.getColorGameDeadPlayers().remove(player);
        }
        main.getTerritoryGamePlayers().remove(player);
        main.getTerritoryGameDeadPlayers().remove(player);
    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item != null && item.getType() == Material.COMPASS &&
                item.hasItemMeta() && item.getItemMeta().hasDisplayName() &&
                item.getItemMeta().getDisplayName().equals("§dGameSelector")) {

            Inventory gameSelectorInv = Bukkit.createInventory(null, 3 * 9, "§dGameSelector");
            for (int i=0; i < 27; i++){
                gameSelectorInv.setItem(i, main.getItem(Material.BLACK_STAINED_GLASS_PANE, " ", false,1));
            }
            gameSelectorInv.setItem(10, main.getItem(Material.TNT, "§5Vache explosive", false,1));
            gameSelectorInv.setItem(13, main.getItem(Material.LIGHT_BLUE_WOOL, "§6Color Game", true,1));
            gameSelectorInv.setItem(16, main.getItem(Material.BLAZE_ROD, "§5OpenFront", true,1));

            player.openInventory(gameSelectorInv);
        }

        if (item != null){
            if (item.getItemMeta().getDisplayName().equals("§aSpeed Boost") && item.getType() == Material.YELLOW_DYE){

                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5*20, 5, false, false, false));
                for (int i = 0; i < player.getInventory().getSize(); i++) {
                    ItemStack itemInSlot = player.getInventory().getItem(i);
                    if (itemInSlot != null && itemInSlot.equals(item)) {
                        player.getInventory().clear(i);
                        break;
                    }
                }


            }
            if (item.getItemMeta().getDisplayName().equals("§aJump Boost") && item.getType() == Material.FEATHER){

                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 3*20, 3, false, false, false));
                for (int i = 0; i < player.getInventory().getSize(); i++) {
                    ItemStack itemInSlot = player.getInventory().getItem(i);
                    if (itemInSlot != null && itemInSlot.equals(item)) {
                        player.getInventory().clear(i);
                        break;
                    }
                }
            }
            if (item.getItemMeta().getDisplayName().equals("§5Territory Selector") && item.getType() == Material.BLAZE_ROD){

                int xBlock = event.getClickedBlock().getX();
                int yBlock = event.getClickedBlock().getY();
                int zBlock = event.getClickedBlock().getZ();

                player.sendMessage("Territoire selectionne en: x= " + xBlock + " y= " + yBlock + " z= " + zBlock);
                main.getConfig().set("player." + player.getUniqueId().toString() + ".territory.x", xBlock);
                main.getConfig().set("player." + player.getUniqueId().toString() + ".territory.y", yBlock);
                main.getConfig().set("player." + player.getUniqueId().toString() + ".territory.z", zBlock);

                ItemMeta itemM = item.getItemMeta();
                itemM.setLore(Collections.singletonList("x: " + xBlock + " y: " + yBlock + " z:" + zBlock));
                item.setItemMeta(itemM);

                player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);

                main.saveConfig();
            }
        }

        if (event.getClickedBlock() == null)return;
        if (event.getClickedBlock().getState() instanceof Sign){
            Sign sign = (Sign) event.getClickedBlock().getState();
            if (sign.getLine(1).equalsIgnoreCase(ChatColor.DARK_BLUE + "FORCE START")){
                if (player.getLocation().getY() == 205){
                    if (main.isState(GameStates.STARTING) || main.isState(GameStates.PLAYING) || main.isState(GameStates.FINISH)){
                        player.sendMessage(ChatColor.RED + "Le jeu a deja commence! Veuillez attendre la fin de la partie");
                    }
                    MiniGameStartTask startTask = new MiniGameStartTask(main);
                    startTask.runTaskTimer(main, 0, 20);
                    main.setState(GameStates.STARTING);
                }
                if (player.getLocation().getY() == 209){
                    if (main.isColorGameState(GameStatesColorGame.STARTING) || main.isColorGameState(GameStatesColorGame.PLAYING) || main.isColorGameState(GameStatesColorGame.FINISH)){
                        player.sendMessage(ChatColor.RED + "Le jeu a deja commence! Veuillez attendre la fin de la partie");
                    }else {
                        ColorGameStartTask colorGameStartTask = new ColorGameStartTask(main);
                        colorGameStartTask.runTaskTimer(main, 0, 20);
                        main.setColorGameState(GameStatesColorGame.STARTING);
                    }

                }
                if (player.getLocation().getY() == 213){
                    if (!main.isTerritoryGameState(GameStatesTerritoryGame.WAITING)){
                        player.sendMessage(ChatColor.RED + "Le jeu a deja commence! Veuillez attendre la fin de la partie");
                    }else {
                        TerritoryGameStartTask TerritoryGameStartTask = new TerritoryGameStartTask(main);
                        TerritoryGameStartTask.runTaskTimer(main, 0, 20);
                        main.setTerritoryGameState(GameStatesTerritoryGame.STARTING);
                    }

                }
            }
        }

    }

    @EventHandler
    public static void InventoryClickEvent(InventoryClickEvent event){
        Inventory inv = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();

        if (event.getView().getTitle().equals("§dGameSelector")){
            if (current == null)return;
            if (current.getType() == Material.BLACK_STAINED_GLASS_PANE && current.getItemMeta().getDisplayName().equals(" ")){
                event.setCancelled(true);
            }
            if (current.getType() == Material.TNT && current.getItemMeta().getDisplayName().equals("§5Vache explosive")){
                if (!main.isState(GameStates.WAITING)){
                    player.sendMessage(ChatColor.RED + "Le jeu a deja commence! Veuillez attendre la fin de la partie");
                }

                if (main.getPlayers().contains(player)){
                    player.sendMessage(ChatColor.RED + "Vous etes deja parmi les joueurs!");
                    event.setCancelled(true);
                    player.closeInventory();
                } else {
                    main.getPlayers().add(player);
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(ChatColor.GREEN + "Vous avez bien rejoins le jeu");
                    player.sendTitle(ChatColor.GREEN + "Vous avez bien rejoins le jeu", null,1, 40, 1);
                    event.setCancelled(true);
                    player.getInventory().clear();
                    player.closeInventory();
                    player.teleport(main.spawnTntGameLoc);
                }

            }
            if (current.getType() == Material.LIGHT_BLUE_WOOL && current.getItemMeta().getDisplayName().equals("§6Color Game")){
                // event.setCancelled(true);
                if (!main.isColorGameState(GameStatesColorGame.WAITING)){
                    player.sendMessage(ChatColor.RED + "Le jeu a deja commence! Veuillez attendre la fin de la partie");
                }

                if (main.getColorGamePlayers().contains(player)){
                    player.sendMessage(ChatColor.RED + "Vous etes deja parmi les joueurs!");
                    event.setCancelled(true);
                    player.closeInventory();
                } else {
                    main.getColorGamePlayers().add(player);
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(ChatColor.GREEN + "Vous avez bien rejoins le jeu");
                    player.sendTitle(ChatColor.GREEN + "Vous avez bien rejoins le jeu", null,1, 40, 1);
                    event.setCancelled(true);
                    player.getInventory().clear();
                    player.closeInventory();
                    player.teleport(main.spawnColorGameLoc);
                }
            }
            if (current.getType() == Material.BLAZE_ROD && current.getItemMeta().getDisplayName().equals("§5OpenFront")){
                if (!main.isTerritoryGameState(GameStatesTerritoryGame.WAITING)){
                    player.sendMessage(ChatColor.RED + "Le jeu a deja commence! Veuillez attendre la fin de la partie");
                }

                if (main.getTerritoryGamePlayers().contains(player)){
                    player.sendMessage(ChatColor.RED + "Vous etes deja parmi les joueurs!");
                    event.setCancelled(true);
                    player.closeInventory();
                } else if (main.getTerritoryGamePlayers().size() < 8){
                    main.getTerritoryGamePlayers().add(player);
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(ChatColor.GREEN + "Vous avez bien rejoins le jeu <" + main.getTerritoryGamePlayers().size() + "/8>");
                    player.sendTitle(ChatColor.GREEN + "Vous avez bien rejoins le jeu", null,1, 40, 1);
                    event.setCancelled(true);
                    player.getInventory().clear();
                    player.closeInventory();
                    player.teleport(main.spawnTerritoryGameLoc);
                } else player.sendMessage(ChatColor.RED + "Le jeu est plein!");
            }
        }

    }

    @EventHandler
    public void onSignChange(SignChangeEvent event){
        if (event.getLine(1).equalsIgnoreCase("[FORCE START]")){
            event.setLine(1, ChatColor.DARK_BLUE + "FORCE START");
        }
    }

}
