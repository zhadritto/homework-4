package com.narxoz.rpg.battle;

import java.util.ArrayList;
import java.util.List;

public class RaidResult {
    private String winner;
    private int rounds;
    private final List<String> log = new ArrayList<>();

    public void addLine(String line) {
        log.add(line);
    }

    public List<String> getLog() {
        return log;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public int getRounds() {
        return rounds;
    }
}