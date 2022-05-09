package com.github.seventeen.playerworld.cca.generic;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

import java.util.UUID;

public interface UUIDComponent extends ComponentV3 {
    UUID getValue();
    void setValue(UUID value);
}
