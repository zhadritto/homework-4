package com.narxoz.rpg.battle;

import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;
import java.util.Random;

public class RaidEngine {
    private Random random = new Random(1L);

    public RaidResult runRaid(CombatNode teamA, CombatNode teamB, Skill skillA, Skill skillB) {
        RaidResult result = new RaidResult();

        if (teamA == null || teamB == null || !teamA.isAlive() || !teamB.isAlive()) {
            result.addLine("Invalid raid setup: Teams must exist and be alive.");
            return result;
        }

        int round = 1;
        while (teamA.isAlive() && teamB.isAlive() && round <= 20) {
            result.addLine("Round " + round + ":");
            result.addLine(" > " + teamA.getName() + " uses " + skillA.getSkillName() + " (" + skillA.getEffectName() + ")");
            skillA.cast(teamB);
            result.addLine("   " + teamB.getName() + " collective HP: " + teamB.getHealth());

            if (!teamB.isAlive()) break;
            result.addLine(" > " + teamB.getName() + " uses " + skillB.getSkillName() + " (" + skillB.getEffectName() + ")");
            skillB.cast(teamA);
            result.addLine("   " + teamA.getName() + " collective HP: " + teamA.getHealth());

            round++;
        }

        result.setRounds(round - 1);
        result.setWinner(teamA.isAlive() ? teamA.getName() : (teamB.isAlive() ? teamB.getName() : "None (Draw)"));
        return result;
    }

    public RaidEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }
}