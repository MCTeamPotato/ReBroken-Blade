package com.teampotato.broken_blade;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.slf4j.Logger;

@Mod("broken_blade")
public class BrokenBlade {
    public static final ModConfigSpec CLIENT_CONFIG;
    public static final ModConfigSpec.ConfigValue<String> ITEM;
    private static final Logger LOGGER = LogUtils.getLogger();
    private static String cachedDefinition;
    private static HolderLookup.Provider cachedRegistries;
    private static ItemStack cachedItem;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("Mowzie's Mobs: The Broken Blade");
        ITEM = builder.comment("Item ID, optionally followed by 1.21.1 data components using /give syntax.",
                "Example: minecraft:diamond_sword[minecraft:custom_model_data=123,minecraft:damage=10]")
                .define("WroughtnautBackSword", "minecraft:diamond_sword");
        builder.pop();
        CLIENT_CONFIG = builder.build();
    }

    public BrokenBlade(ModContainer container) {
        container.registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG);
    }

    public static ItemStack parseItem(String definition, HolderLookup.Provider registries) throws CommandSyntaxException {
        StringReader reader = new StringReader(definition.trim());
        ItemParser.ItemResult result = new ItemParser(registries).parse(reader);
        reader.skipWhitespace();
        if (reader.canRead()) throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().createWithContext(reader);
        return new ItemInput(result.item(), result.components()).createItemStack(1, false);
    }

    public static ItemStack getConfiguredItem(HolderLookup.Provider registries) {
        String definition = ITEM.get();
        if (cachedItem == null || !definition.equals(cachedDefinition) || registries != cachedRegistries) {
            cachedDefinition = definition;
            cachedRegistries = registries;
            try {
                cachedItem = parseItem(definition, registries);
            } catch (CommandSyntaxException | IllegalArgumentException exception) {
                LOGGER.warn("Invalid WroughtnautBackSword '{}'; using diamond sword: {}", definition, exception.getMessage());
                cachedItem = new ItemStack(Items.DIAMOND_SWORD);
            }
        }
        return cachedItem.copy();
    }
}
