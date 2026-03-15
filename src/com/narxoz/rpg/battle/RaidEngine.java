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
            boolean teamAGoesFirst = random.nextBoolean();

            CombatNode first = teamAGoesFirst ? teamA : teamB;
            CombatNode second = teamAGoesFirst ? teamB : teamA;
            Skill firstSkill = teamAGoesFirst ? skillA : skillB;
            Skill secondSkill = teamAGoesFirst ? skillB : skillA;

            result.addLine(" > " + first.getName() + " uses " + firstSkill.getSkillName() + " (" + firstSkill.getEffectName() + ")");
            firstSkill.cast(second);
            result.addLine("   " + second.getName() + " collective HP: " + second.getHealth());

            if (!second.isAlive()) break;

            result.addLine(" > " + second.getName() + " uses " + secondSkill.getSkillName() + " (" + secondSkill.getEffectName() + ")");
            secondSkill.cast(first);
            result.addLine("   " + first.getName() + " collective HP: " + first.getHealth());

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