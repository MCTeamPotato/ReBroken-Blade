package com.teampotato.broken_blade.mixin;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelWroughtnaut;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.ItemLayer;
import com.bobmowzie.mowziesmobs.server.entity.wroughtnaut.EntityWroughtnaut;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.teampotato.broken_blade.BrokenBlade;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemLayer.class, remap = false)
public abstract class MixinItemLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    protected MixinItemLayer(RenderLayerParent<T, M> parent) { super(parent); }
    @Shadow public abstract void setItemstack(ItemStack stack);
    @Shadow public abstract AdvancedModelRenderer getModelRenderer();

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = @At("HEAD"), remap = false)
    private void brokenBlade$updateWroughtnautItem(PoseStack poseStack, MultiBufferSource buffers, int light,
            T entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
            float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (entity instanceof EntityWroughtnaut && getParentModel() instanceof ModelWroughtnaut<?> model
                && getModelRenderer() == model.sword) {
            setItemstack(BrokenBlade.getConfiguredItem(entity.registryAccess()));
        }
    }
}
