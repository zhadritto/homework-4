package com.narxoz.rpg.battle;

import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;
import java.util.Random;

public class RaidEngine {
    private Random random;

    public RaidEngine() {
        this.random = new Random();
    }

    public RaidEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public RaidResult runRaid(CombatNode teamA, CombatNode teamB, Skill skillA, Skill skillB) {
        RaidResult result = new RaidResult();
        int round = 1;

        result.addLine("Starting raid: " + teamA.getName() + " VS " + teamB.getName());

        while (teamA.isAlive() && teamB.isAlive()) {
            result.addLine("\n--- Round " + round + " ---");


            if (teamA.isAlive()) {
                int damageA = skillA.getBasePower();
                result.addLine(teamA.getName() + " casts " + skillA.getSkillName() + " (" + skillA.getEffectName() + ")!");
                skillA.cast(teamB);
                result.addLine(teamB.getName() + " remaining HP: " + teamB.getHealth());
            }
            if (teamB.isAlive()) {
                result.addLine(teamB.getName() + " retaliates with " + skillB.getSkillName() + " (" + skillB.getEffectName() + ")!");
                skillB.cast(teamA);
                result.addLine(teamA.getName() + " remaining HP: " + teamA.getHealth());
            }

            round++;
            if (round > 100) {
                result.addLine("Raid timed out after 100 rounds.");
                break;
            }
        }

        result.setRounds(round - 1);

        if (teamA.isAlive() && !teamB.isAlive()) {
            result.setWinner(teamA.getName());
        } else if (teamB.isAlive() && !teamA.isAlive()) {
            result.setWinner(teamB.getName());
        } else {
            result.setWinner("Draw");
        }

        return result;
    }
}