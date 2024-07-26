package com.teampotato.broken_blade.mixin;

import com.bobmowzie.mowziesmobs.client.render.entity.RenderWroughtnaut;
import com.teampotato.broken_blade.BrokenBlade;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Objects;
@OnlyIn(Dist.CLIENT)
@Mixin(value = RenderWroughtnaut.class)
public abstract class MixinRenderWroughtnaut {
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;getDefaultInstance()Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack getItemStack(Item instance) {
        if (BrokenBlade.BROKEN_BLADE == null) BrokenBlade.BROKEN_BLADE = Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(BrokenBlade.ITEM.get()))).getDefaultInstance();
        return BrokenBlade.BROKEN_BLADE;
    }
}
