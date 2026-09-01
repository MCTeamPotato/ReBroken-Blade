package com.teampotato.broken_blade.regression;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.teampotato.broken_blade.BrokenBlade;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

@Mod("broken_blade_tests")
@GameTestHolder("broken_blade")
@PrefixGameTestTemplate(false)
public class RegressionTests {
    @GameTest(template = "empty")
    public static void oldItemIdsAndAirRemainSupported(GameTestHelper helper) throws CommandSyntaxException {
        helper.assertTrue(BrokenBlade.parseItem(" minecraft:diamond_sword ").is(Items.DIAMOND_SWORD), "Existing ID-only configuration must work");
        helper.assertTrue(BrokenBlade.parseItem("minecraft:air").isEmpty(), "Air must hide the display item");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void itemNbtReachesTheRenderedStack(GameTestHelper helper) throws CommandSyntaxException {
        var stack = BrokenBlade.parseItem("minecraft:diamond_sword{CustomModelData:123,Damage:10,Enchantments:[{id:\"minecraft:unbreaking\",lvl:3s}],display:{Name:'{\"text\":\"Test Blade\"}'}}");
        helper.assertTrue(stack.is(Items.DIAMOND_SWORD), "The item type must be retained");
        helper.assertTrue(stack.getTag().getInt("CustomModelData") == 123, "Custom model NBT must be retained");
        helper.assertTrue(stack.getDamageValue() == 10 && stack.isEnchanted(), "Damage and enchantments must be retained");
        helper.assertTrue(stack.getHoverName().getString().equals("Test Blade"), "Custom name must be retained");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void invalidDefinitionsAreRejected(GameTestHelper helper) {
        for (String definition : new String[]{"missing:item", "minecraft:diamond_sword{broken", "minecraft:diamond_sword trailing"}) {
            boolean rejected = false;
            try {
                BrokenBlade.parseItem(definition);
            } catch (CommandSyntaxException | IllegalArgumentException expected) {
                rejected = true;
            }
            helper.assertTrue(rejected, "Invalid definition must not be accepted: " + definition);
        }
        helper.succeed();
    }
}
