package fr.lemonadd.rankmanager.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.lemonadd.rankmanager.Main;

/*
    Code by CODEDRED
    https://www.youtube.com/channel/UC_kPUW3XPrCCRT9a4Pnf1Tg
    Edited by LemonAdd
 */

public class SQLGetter implements Listener {

    Main plugin = Main.getPlugin(Main.class);


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        createPlayer(player.getUniqueId(), player);
    }

    public boolean playerExists(UUID uuid) {
        try {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * FROM PlayerData WHERE UUID=?");
            statement.setString(1, uuid.toString());

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                // player found
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setRank(UUID uuid, int rank) {
        try {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE PlayerData SET Rank=? WHERE UUID=?");
            statement.setInt(1, rank);
            statement.setString(2,  uuid.toString());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        PreparedStatement ps;
        try {
            ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS PlayerData "
                    + "(Name VARCHAR(100),UUID VARCHAR(100),Rank INT(100),PRIMARY KEY (UUID))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void emptyTable() {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("TRUNCATE PlayerData");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void createPlayer(final UUID uuid, Player player) {
        try {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * FROM PlayerData WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();
            if (!playerExists(uuid)) {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT IGNORE INTO PlayerData (Name,UUID,Rank) VALUES (?,?,0)");
                ps.setString(1, player.getName());
                ps.setString(2, player.getUniqueId().toString());
                ps.executeUpdate();
                // plugin.getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "CREATING PLAYER");
                return;
            }
            // plugin.getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "PLAYER FOUND");
        } catch (SQLException e ) {
            e.printStackTrace();
            // plugin.getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "ERROR");
        }
    }
}