package com.bagel.mixin;

import com.bagel.HealthFixer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "setAbsorptionAmount", at = @At("HEAD"), cancellable = true)
    private void checkSetAbsorbtionEntity(float f, CallbackInfo ci){
        if (Float.isNaN(f)){
            new Throwable().printStackTrace();
            HealthFixer.LOGGER.warn("A mod tried to set NaN absorption to entity {}!", ((LivingEntity) (Object) this));
            if (((LivingEntity) (Object) this).getServer() != null) {
                ((LivingEntity) (Object) this).getServer().getPlayerList().getPlayers().forEach(player -> {
                    player.displayClientMessage(Component.literal("Something tried to set a non number absorption, this is bad! Check server logs for more info!").withStyle(ChatFormatting.RED), false);
                });
            }
            ci.cancel();
        }
    }

    @Inject(method = "setHealth", at = @At("HEAD"), cancellable = true)
    private void checkSetHealthEntity(float f, CallbackInfo ci){
        if (Float.isNaN(f)){
            new Throwable().printStackTrace();
            HealthFixer.LOGGER.warn("A mod tried to set NaN health to entity {}!", ((LivingEntity) (Object) this));
            if (((LivingEntity) (Object) this).getServer() != null) {
                ((LivingEntity) (Object) this).getServer().getPlayerList().getPlayers().forEach(player -> {
                    player.displayClientMessage(Component.literal("Something tried to set a non number health, this is bad! Check server logs for more info!").withStyle(ChatFormatting.RED), false);
                });
            }
            ci.cancel();
        }
    }
}
