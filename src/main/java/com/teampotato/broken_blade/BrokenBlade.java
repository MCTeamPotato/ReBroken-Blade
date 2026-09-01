package com.teampotato.broken_blade;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod("broken_blade")
public class BrokenBlade {
    public static ForgeConfigSpec CLIENT_CONFIG;
    public static ForgeConfigSpec.ConfigValue<String> ITEM;

    public static ItemStack BROKEN_BLADE = null;
    private static final Logger LOGGER = LogUtils.getLogger();
    private static String cachedDefinition;

    static {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        BUILDER.push("Mowzie's Mobs: The Broken Blade");
        ITEM = BUILDER.comment("Item ID, optionally followed by item NBT using /give syntax.",
                "Example: minecraft:diamond_sword{CustomModelData:123,Damage:10}")
                .define("WroughtnautBackSword", "minecraft:diamond_sword");
        BUILDER.pop();
        CLIENT_CONFIG = BUILDER.build();
    }

    public BrokenBlade() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG);
    }

    public static ItemStack parseItem(String definition) throws CommandSyntaxException {
        StringReader reader = new StringReader(definition.trim());
        ItemParser.ItemResult result = ItemParser.parseForItem(BuiltInRegistries.ITEM.asLookup(), reader);
        reader.skipWhitespace();
        if (reader.canRead()) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().createWithContext(reader);
        }
        ItemStack stack = new ItemStack(result.item());
        if (result.nbt() != null) stack.setTag(result.nbt().copy());
        return stack;
    }

    public static ItemStack getConfiguredItem() {
        String definition = ITEM.get();
        if (BROKEN_BLADE == null || !definition.equals(cachedDefinition)) {
            cachedDefinition = definition;
            try {
                BROKEN_BLADE = parseItem(definition);
            } catch (CommandSyntaxException | IllegalArgumentException exception) {
                LOGGER.warn("Invalid WroughtnautBackSword '{}'; using diamond sword: {}", definition, exception.getMessage());
                BROKEN_BLADE = new ItemStack(Items.DIAMOND_SWORD);
            }
        }
        return BROKEN_BLADE.copy();
    }
}
