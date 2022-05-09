package com.github.seventeen.playerworld.cca.impl;

import com.github.seventeen.playerworld.cca.generic.BooleanComponent;
import net.minecraft.nbt.NbtCompound;

public class IsPublicComponent implements BooleanComponent {

    Boolean value = false;
    @Override
    public Boolean getValue() {
        return this.value;
    }

    @Override
    public void setValue(Boolean value) {
        this.value = value;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.value = tag.getBoolean("IsPublic");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        assert this.value != null;
        tag.putBoolean("IsPublic", this.value);
    }
}
