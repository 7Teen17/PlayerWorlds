package com.github.seventeen.playerworld.cca.impl;

import com.github.seventeen.playerworld.cca.generic.UUIDComponent;
import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

import static net.minecraft.util.Util.NIL_UUID;

public class CreatorComponent implements UUIDComponent {

    UUID value = NIL_UUID;
    @Override
    public UUID getValue() {
        return this.value;
    }

    @Override
    public void setValue(UUID value) {
        this.value = value;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        assert this.value != null;
        this.value = tag.getUuid("Creator");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putUuid("Creator", this.value);
    }
}
