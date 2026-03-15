package com.narxoz.rpg.bridge;

public class PhysicalEffect implements EffectImplementor {
    @Override
    public int computeDamage(int basePower) {
        return basePower;
    }

    @Override
    public String getEffectName() {
        return "Physical";
    }
}
