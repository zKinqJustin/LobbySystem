package de.zkinqjustin.LobbySystem.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import de.zkinqjustin.LobbySystem.LobbySystem;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScoreboardManager extends BukkitRunnable {

    private final LobbySystem plugin;

    public ScoreboardManager(LobbySystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            updateScoreboard(player);
        }
    }

    private void updateScoreboard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = board.registerNewObjective("lobby", "dummy", ChatColor.GOLD + "Lobby");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date(System.currentTimeMillis() + 3600000); // Current time + 1 hour

        Score timeScore = objective.getScore(ChatColor.YELLOW + "Time: " + ChatColor.WHITE + sdf.format(now));
        timeScore.setScore(2);

        Score playerCountScore = objective.getScore(ChatColor.YELLOW + "Players: " + ChatColor.WHITE + Bukkit.getOnlinePlayers().size());
        playerCountScore.setScore(1);

        player.setScoreboard(board);
    }
}

