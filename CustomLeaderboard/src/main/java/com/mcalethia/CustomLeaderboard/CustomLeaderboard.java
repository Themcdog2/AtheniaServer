package com.mcalethia.CustomLeaderboard;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcalethia.CustomLeaderboard.SQL.MySQL;

public final class CustomLeaderboard extends JavaPlugin {

	Connection c;
	MySQL MySQL = new MySQL(getPlugin(null), "host.name", "port", "database", "user", "pass");

	@Override
	public void onEnable() {
		getLogger().info("Custom leaderboard has been invoked");

		// ///// SQL BEGIN ///////

		getLogger().info("Custom leaderboard is invoking mySQL..");

		try {
			c = MySQL.openConnection();
		} catch (ClassNotFoundException | SQLException e) {
			getLogger().info("Error while invoking SQL");
			e.printStackTrace();
		};

		// //// SQL END ///////
	}

	@Override
	public void onDisable() {
		// LOGIC
	}

	public void PlayerLoginEvent(Player p) throws SQLException {
		getLogger().info("Player joining....");
		if (!databaseFindPlayer(p.getName())) {
			getLogger().info("Player does not have data file.. Generating one now");
			databaseCreatePlayer(p.getName());
		}
	}

	public void databaseCreatePlayer(String name) throws SQLException {
		Statement statement = c.createStatement();
		;
		statement.executeUpdate("INSERT INTO players (`name`, `points`) VALUES ('" + name + "', '0');");
		getLogger().info("Generated database file for " + name);

	}

	public boolean databaseFindPlayer(String name) throws SQLException {
		Statement statement = c.createStatement();

		ResultSet res = statement.executeQuery("SELECT * FROM players WHERE PlayerName = '" + name + "';");
		res.next();

		if (res.getString("PlayerName") == null) {
			return false;
		} else

		{

			return true;

		}
	}

}
