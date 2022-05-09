package com.github.seventeen.playerworld.cca.impl;

import com.github.seventeen.playerworld.cca.generic.ArrayComponent;
import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;

public class VisitorsComponent<T> implements ArrayComponent {

    ArrayList<T> value = new ArrayList<>();

    @Override
    public <T> ArrayList<T> getValue() {
        return null;
    }

    @Override
    public <T> void addValue(T value) {

    }

    @Override
    public <T> void removeValue(T value) {

    }

    @Override
    public <T> void setValue(T value) {

    }

    @Override
    public void readFromNbt(NbtCompound tag) {

    }

    @Override
    public void writeToNbt(NbtCompound tag) {

    }
}
