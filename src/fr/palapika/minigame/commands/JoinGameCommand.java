package fr.palapika.minigame.commands;

import fr.palapika.minigame.GameStates;
import fr.palapika.minigame.MiniGame;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinGameCommand implements CommandExecutor {

    private MiniGame main;

    public JoinGameCommand(MiniGame main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You have to be a player to execute this command");
        } else {
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("joingame")){
                if (!main.isState(GameStates.WAITING)){
                    player.sendMessage(ChatColor.RED + "Le jeu a deja commence! Veuillez attendre la fin de la partie");
                    return true;
                }

                if (main.getPlayers().contains(player)){
                    player.sendMessage(ChatColor.RED + "Vous etes deja parmi les joueurs!");
                } else {
                    main.getPlayers().add(player);
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(ChatColor.GREEN + "Vous avez bien rejoins le jeu");
                    player.sendTitle(ChatColor.GREEN + "Vous avez bien rejoins le jeu", null,1, 40, 1);
                }
            }

        }


        return true;
    }
}
