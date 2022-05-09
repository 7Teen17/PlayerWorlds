package com.github.seventeen.playerworld.cca.generic;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

import java.util.ArrayList;

public interface ArrayComponent extends ComponentV3 {
    <T> ArrayList<T> getValue();
    <T> void addValue(T value);
    <T> void removeValue(T value);
    <T> void setValue(T value);

}
