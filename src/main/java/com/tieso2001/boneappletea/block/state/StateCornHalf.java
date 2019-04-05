package com.tieso2001.boneappletea.block.state;

import net.minecraft.util.IStringSerializable;

public enum StateCornHalf implements IStringSerializable
{
    LOWER("lower"),
    UPPER("upper");

    public static final StateCornHalf[] VALUES = StateCornHalf.values();

    private final String name;

    StateCornHalf(String name)
    {
        this.name = name;
    }

    @Override
    public String getName()
    {
        return name;
    }
}
