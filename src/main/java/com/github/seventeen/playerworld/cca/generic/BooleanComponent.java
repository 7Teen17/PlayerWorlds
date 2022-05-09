package com.github.seventeen.playerworld.cca.generic;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

public interface BooleanComponent extends ComponentV3 {
    Boolean getValue();
    void setValue(Boolean value);
}
