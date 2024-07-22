package com.teampotato.broken_blade;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;

@Mod("broken_blade")
public class BrokenBlade {
    public static ForgeConfigSpec CLIENT_CONFIG;
    public static ForgeConfigSpec.ConfigValue<String> ITEM;

    public static ItemStack BROKEN_BLADE = null;

    static {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        BUILDER.push("Mowzie's Mobs: The Broken Blade");
        ITEM = BUILDER.define("WroughtnautBackSword", "minecraft:diamond_sword", s -> s instanceof String &&  ForgeRegistries.ITEMS.getValue(new ResourceLocation((String) s)) != null);
        BUILDER.pop();
        CLIENT_CONFIG = BUILDER.build();
    }

    public BrokenBlade() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG);
    }
}
