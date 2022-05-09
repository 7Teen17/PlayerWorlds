package com.github.seventeen.playerworld.cca.impl;

import com.github.seventeen.playerworld.cca.generic.Vec3dComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;

public class SpawnComponent implements Vec3dComponent {

    Vec3d value = new Vec3d(0, 70, 0);
    @Override
    public Vec3d getValue() {
        return this.value;
    }

    @Override
    public void setValue(Vec3d value) {
        this.value = value;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        NbtCompound spawnTag = tag.getCompound("Spawn");
        this.value = new Vec3d(spawnTag.getDouble("X"), spawnTag.getDouble("Y"), spawnTag.getDouble("Z"));
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        assert this.value != null;
        NbtCompound spawnTag = new NbtCompound();
        spawnTag.putDouble("X", this.value.x);
        spawnTag.putDouble("Y", this.value.y);
        spawnTag.putDouble("Z", this.value.z);
        tag.put("Spawn", spawnTag);
    }
}
