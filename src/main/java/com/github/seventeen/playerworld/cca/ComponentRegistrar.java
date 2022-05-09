package com.github.seventeen.playerworld.cca;

import com.github.seventeen.playerworld.cca.generic.BooleanComponent;
import com.github.seventeen.playerworld.cca.generic.StringComponent;
import com.github.seventeen.playerworld.cca.generic.UUIDComponent;
import com.github.seventeen.playerworld.cca.generic.Vec3dComponent;
import com.github.seventeen.playerworld.cca.impl.CreatorComponent;
import com.github.seventeen.playerworld.cca.impl.IsPublicComponent;
import com.github.seventeen.playerworld.cca.impl.NameComponent;
import com.github.seventeen.playerworld.cca.impl.SpawnComponent;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import net.minecraft.util.Identifier;

public final class ComponentRegistrar implements WorldComponentInitializer {

    public static final ComponentKey<StringComponent> WORLDNAME = ComponentRegistry.getOrCreate(new Identifier("playerworld", "name"), StringComponent.class);
    public static final ComponentKey<BooleanComponent> ISPUBLIC = ComponentRegistry.getOrCreate(new Identifier("playerworld", "ispublic"), BooleanComponent.class);
    public static final ComponentKey<UUIDComponent> CREATOR = ComponentRegistry.getOrCreate(new Identifier("playerworld", "creator"), UUIDComponent.class);
    public static final ComponentKey<Vec3dComponent> SPAWN = ComponentRegistry.getOrCreate(new Identifier("playerworld", "spawn"), Vec3dComponent.class);

    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
        registry.register(WORLDNAME, e -> new NameComponent());
        registry.register(ISPUBLIC, e -> new IsPublicComponent());
        registry.register(CREATOR, e -> new CreatorComponent());
        registry.register(SPAWN, e -> new SpawnComponent());
    }
}
