package fr.palapika.minigame.commands;

import fr.palapika.minigame.GameStates;
import fr.palapika.minigame.MiniGame;
import fr.palapika.minigame.tasks.MiniGameStartTask;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {

    private MiniGame main;
    public StartCommand(MiniGame main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You have to be a player to execute this command");
        } else {

            Player player = (Player) sender;

            if (cmd.getName().equalsIgnoreCase("start")){
                if (main.isState(GameStates.STARTING) || main.isState(GameStates.PLAYING) || main.isState(GameStates.FINISH)){
                    player.sendMessage(ChatColor.RED + "Le jeu a deja commence! Veuillez attendre la fin de la partie");
                    return true;
                }
                MiniGameStartTask startTask = new MiniGameStartTask(main);
                startTask.runTaskTimer(main, 0, 20);
                main.setState(GameStates.STARTING);
            }
        }

        return true;
    }
}
