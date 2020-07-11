package fr.lemonadd.rankmanager.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import fr.lemonadd.rankmanager.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

/*
    Code by CODEDRED
    https://www.youtube.com/channel/UC_kPUW3XPrCCRT9a4Pnf1Tg
    Edited by LemonAdd
 */

public class MySQL {

    Main plugin = Main.getPlugin(Main.class);

    // grab config
    public String host = plugin.getConfig().getString("db.host");
    public String port = plugin.getConfig().getString("db.port");
    public String database = plugin.getConfig().getString("db.name");
    public String username = plugin.getConfig().getString("db.user");
    public String password = plugin.getConfig().getString("db.pwd");
    public static Connection con;

    ConsoleCommandSender console = Bukkit.getConsoleSender();

    // connect
    public void connect() throws ClassNotFoundException {
        if (!isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", username, password);
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[RankManager]"
                        + ChatColor.WHITE + " Database connected successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // disconnect
    public void disconnect() {
        if (isConnected()) {
            try {
                con.close();
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[RankManager]"
                        + ChatColor.WHITE + " Database disconnecting!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // isConnected
    public boolean isConnected() {
        return (con != null);
    }

    // getConnection
    public static Connection getConnection() {
        return con;
    }
}
