package com.github.seventeen.playerworld.cca.generic;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.util.math.Vec3d;

public interface Vec3dComponent extends ComponentV3 {
    Vec3d getValue();
    void setValue(Vec3d value);
}
