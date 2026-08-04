package com.pacman.score;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/** Representa una puntuación guardada en el archivo binario. */
public final class ScoreEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String playerName;
    private final int score;
    private final LocalDateTime playedAt;

    public ScoreEntry(String playerName, int score, LocalDateTime playedAt) {
        this.playerName = Objects.requireNonNull(playerName, "playerName");
        this.score = score;
        this.playedAt = Objects.requireNonNull(playedAt, "playedAt");
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public LocalDateTime getPlayedAt() {
        return playedAt;
    }
}
