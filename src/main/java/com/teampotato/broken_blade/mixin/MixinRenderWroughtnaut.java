package com.teampotato.broken_blade.mixin;

import com.bobmowzie.mowziesmobs.client.render.entity.RenderWroughtnaut;
import com.teampotato.broken_blade.BrokenBlade;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@OnlyIn(Dist.CLIENT)
@Mixin(value = RenderWroughtnaut.class)
public abstract class MixinRenderWroughtnaut {
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;getDefaultInstance()Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack getItemStack(Item instance) {
        return BrokenBlade.getConfiguredItem();
    }
}
