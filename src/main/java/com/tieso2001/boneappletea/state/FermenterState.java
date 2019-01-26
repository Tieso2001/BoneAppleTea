package com.tieso2001.boneappletea.state;

import net.minecraft.util.IStringSerializable;

public enum FermenterState implements IStringSerializable {

    INACTIVE("inactive"),
    ACTIVE("active");

    public static final FermenterState[] VALUES = FermenterState.values();

    private final String name;

    FermenterState(String name) { this.name = name; }

    @Override
    public String getName() { return name; }

}