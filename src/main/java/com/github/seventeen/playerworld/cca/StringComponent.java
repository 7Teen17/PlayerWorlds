package com.github.seventeen.playerworld.cca;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

public interface StringComponent extends ComponentV3 {
    String getValue();
    void setValue(String value);
}
