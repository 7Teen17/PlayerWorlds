package com.github.seventeen.playerworld.cca.impl;

import com.github.seventeen.playerworld.cca.generic.StringComponent;
import net.minecraft.nbt.NbtCompound;

public class NameComponent implements StringComponent {
    String value = "";
    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.value = tag.getString("Name");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        assert this.value != null;
        tag.putString("Name", this.value);
    }
}
