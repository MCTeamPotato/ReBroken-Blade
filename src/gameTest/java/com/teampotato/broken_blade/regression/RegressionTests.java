package com.teampotato.broken_blade.regression;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.teampotato.broken_blade.BrokenBlade;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Items;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@Mod("broken_blade_tests")
@GameTestHolder("broken_blade")
@PrefixGameTestTemplate(false)
public class RegressionTests {
    @GameTest(template = "empty")
    public static void oldItemIdsAndAirRemainSupported(GameTestHelper helper) throws CommandSyntaxException {
        var registries = helper.getLevel().registryAccess();
        helper.assertTrue(BrokenBlade.parseItem(" minecraft:diamond_sword ", registries).is(Items.DIAMOND_SWORD), "Existing ID-only configuration must work");
        helper.assertTrue(BrokenBlade.parseItem("minecraft:air", registries).isEmpty(), "Air must hide the display item");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void itemComponentsReachTheRenderedStack(GameTestHelper helper) throws CommandSyntaxException {
        var stack = BrokenBlade.parseItem("minecraft:diamond_sword[minecraft:custom_model_data=123,minecraft:damage=10,minecraft:custom_data={test_value:123}]", helper.getLevel().registryAccess());
        helper.assertTrue(stack.is(Items.DIAMOND_SWORD), "The item type must be retained");
        helper.assertTrue(stack.get(DataComponents.CUSTOM_MODEL_DATA).value() == 123, "Custom model component must be retained");
        helper.assertTrue(stack.getDamageValue() == 10, "Damage component must be retained");
        helper.assertTrue(stack.get(DataComponents.CUSTOM_DATA).copyTag().getInt("test_value") == 123, "Custom data component must be retained");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void invalidDefinitionsAreRejected(GameTestHelper helper) {
        for (String definition : new String[]{"missing:item", "minecraft:diamond_sword[minecraft:damage=", "minecraft:diamond_sword trailing"}) {
            boolean rejected = false;
            try {
                BrokenBlade.parseItem(definition, helper.getLevel().registryAccess());
            } catch (CommandSyntaxException | IllegalArgumentException expected) {
                rejected = true;
            }
            helper.assertTrue(rejected, "Invalid definition must not be accepted: " + definition);
        }
        helper.succeed();
    }
}
