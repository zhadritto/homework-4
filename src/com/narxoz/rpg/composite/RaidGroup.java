package com.narxoz.rpg.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RaidGroup implements CombatNode {
    private final String name;
    private final List<CombatNode> children = new ArrayList<>();

    public RaidGroup(String name) {
        this.name = name;
    }

    public void add(CombatNode node) { children.add(node); }
    public void remove(CombatNode node) { children.remove(node); }

    @Override
    public int getHealth() {
        return children.stream().mapToInt(CombatNode::getHealth).sum();
    }

    @Override
    public int getAttackPower() {
        return children.stream()
                .filter(CombatNode::isAlive)
                .mapToInt(CombatNode::getAttackPower)
                .sum();
    }

    @Override
    public void takeDamage(int amount) {
        List<CombatNode> alive = getAliveChildren();
        if (alive.isEmpty()) return;

        int damagePerChild = amount / alive.size();
        for (CombatNode child : alive) {
            child.takeDamage(damagePerChild);
        }
    }

    @Override
    public boolean isAlive() {
        return children.stream().anyMatch(CombatNode::isAlive);
    }

    @Override
    public void printTree(String indent) {
        System.out.println(indent + "+ " + name + " [Raid Group HP=" + getHealth() + ", ATK=" + getAttackPower() + "]");
        for (CombatNode child : children) {
            child.printTree(indent + "  ");
        }
    }

    private List<CombatNode> getAliveChildren() {
        return children.stream()
                .filter(CombatNode::isAlive)
                .collect(Collectors.toList());
    }

    @Override
    public String getName() { return name; }

    @Override
    public List<CombatNode> getChildren() { return Collections.unmodifiableList(children); }
}