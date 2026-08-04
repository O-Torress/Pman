package com.pacman.score;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Lee y escribe el ranking en un archivo binario mediante serialización.
 */
public final class ScoreManager {

    private static final Path SCORE_DIRECTORY = Paths.get("data");
    private static final Path SCORE_FILE = SCORE_DIRECTORY.resolve("scores.dat");
    private static final Path TEMP_FILE = SCORE_DIRECTORY.resolve("scores.tmp");
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /** Agrega una puntuación y vuelve a ordenar el ranking. */
    public synchronized void saveScore(String playerName, int score) {
        List<ScoreEntry> scores = loadScores();
        scores.add(new ScoreEntry(normalizeName(playerName), Math.max(score, 0), LocalDateTime.now()));
        sortScores(scores);
        writeScores(scores);
    }

    /** Devuelve todas las puntuaciones guardadas, ordenadas de mayor a menor. */
    public synchronized List<ScoreEntry> loadScores() {
        if (!Files.exists(SCORE_FILE)) {
            return new ArrayList<>();
        }

        try (ObjectInputStream input = new ObjectInputStream(Files.newInputStream(SCORE_FILE))) {
            Object storedObject = input.readObject();
            List<ScoreEntry> scores = new ArrayList<>();

            if (storedObject instanceof List<?>) {
                for (Object item : (List<?>) storedObject) {
                    if (item instanceof ScoreEntry) {
                        scores.add((ScoreEntry) item);
                    }
                }
            }

            sortScores(scores);
            return scores;
        } catch (IOException | ClassNotFoundException exception) {
            System.err.println("No se pudo leer el ranking: " + exception.getMessage());
            return new ArrayList<>();
        }
    }

    /** Devuelve solamente las primeras posiciones solicitadas. */
    public synchronized List<ScoreEntry> getTopScores(int limit) {
        List<ScoreEntry> scores = loadScores();
        int safeLimit = Math.max(limit, 0);
        return new ArrayList<>(scores.subList(0, Math.min(safeLimit, scores.size())));
    }

    /** Crea una tabla de texto lista para mostrar en Swing o en la consola. */
    public synchronized String formatRanking(int limit) {
        List<ScoreEntry> scores = getTopScores(limit);
        StringBuilder table = new StringBuilder();

        table.append(String.format("%-4s %-18s %8s  %-16s%n", "#", "Jugador", "Puntos", "Fecha"));
        table.append("------------------------------------------------------\n");

        if (scores.isEmpty()) {
            table.append("Todavía no hay puntuaciones guardadas.\n");
            return table.toString();
        }

        for (int index = 0; index < scores.size(); index++) {
            ScoreEntry entry = scores.get(index);
            table.append(String.format(
                    "%-4d %-18s %8d  %-16s%n",
                    index + 1,
                    shorten(entry.getPlayerName(), 18),
                    entry.getScore(),
                    entry.getPlayedAt().format(DATE_FORMAT)
            ));
        }

        return table.toString();
    }

    private void writeScores(List<ScoreEntry> scores) {
        try {
            Files.createDirectories(SCORE_DIRECTORY);

            try (ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(TEMP_FILE))) {
                output.writeObject(scores);
            }

            Files.move(TEMP_FILE, SCORE_FILE, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            System.err.println("No se pudo guardar el ranking: " + exception.getMessage());
        }
    }

    private static void sortScores(List<ScoreEntry> scores) {
        scores.sort(
                Comparator.comparingInt(ScoreEntry::getScore)
                        .reversed()
                        .thenComparing(ScoreEntry::getPlayedAt)
        );
    }

    private static String normalizeName(String playerName) {
        if (playerName == null || playerName.trim().isEmpty()) {
            return "Jugador";
        }
        return playerName.trim();
    }

    private static String shorten(String value, int maximumLength) {
        if (value.length() <= maximumLength) {
            return value;
        }
        return value.substring(0, maximumLength - 1) + "…";
    }
}
