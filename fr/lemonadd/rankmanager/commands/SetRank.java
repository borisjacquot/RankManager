package fr.lemonadd.rankmanager.commands;


import fr.lemonadd.rankmanager.data.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetRank implements CommandExecutor {
    public SQLGetter SQLGetter;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        this.SQLGetter = new SQLGetter();
        if (label.equalsIgnoreCase("setrank")) {
            if (sender.hasPermission("rankmanger.setrank")) {
                if (args.length != 2) {
                    sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Usage: /setrank <pseudo> <rankID>");
                    return true;
                }
                Player cible = Bukkit.getPlayer(args[0]);
                if (!SQLGetter.playerExists(cible.getUniqueId())) {
                    sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Le joueur doit être connecté");
                    return true;
                }
                if (!isInteger(args[1])) {
                    sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Rank invalide");
                    return true;
                }
                SQLGetter.setRank(cible.getUniqueId(), Integer.parseInt(args[1]));
                sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Joueur édité avec succès!");
            }

        }
        return false;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }
}
