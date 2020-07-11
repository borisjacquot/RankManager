package fr.lemonadd.rankmanager;

import fr.lemonadd.rankmanager.commands.SetRank;
import fr.lemonadd.rankmanager.data.MySQL;
import fr.lemonadd.rankmanager.data.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public MySQL SQL;
    public SQLGetter SQLGetter;

    PluginManager pm = this.getServer().getPluginManager();
    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        config.addDefault("db.host", "localhost");
        config.addDefault("db.port", "3306");
        config.addDefault("db.name", "serveur");
        config.addDefault("db.user", "root");
        config.addDefault("db.pwd", "");
        config.options().copyDefaults(true);
        saveConfig();

        this.SQL = new MySQL();
        this.SQLGetter = new SQLGetter();

        try {
            SQL.connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        SQLGetter.createTable();
        pm.registerEvents(SQLGetter, this);

        this.getCommand("setrank").setExecutor(new SetRank());

    }

    @Override
    public void onDisable() {
        SQL.disconnect();
    }
}
