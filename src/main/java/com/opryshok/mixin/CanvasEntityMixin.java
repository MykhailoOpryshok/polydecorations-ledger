package com.opryshok.mixin;

import com.github.quiltservertools.ledger.callbacks.EntityKillCallback;
import com.github.quiltservertools.ledger.callbacks.EntityModifyCallback;
import com.github.quiltservertools.ledger.utility.Sources;
import eu.pb4.polydecorations.entity.CanvasEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ClickType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CanvasEntity.class)
public abstract class CanvasEntityMixin {

    @Inject(method = "onUsed", at = @At(value = "INVOKE", target = "Leu/pb4/mapcanvas/api/core/PlayerCanvas;sendUpdates()V"))
    public void onUsedLedgerLogging(ServerPlayerEntity serverPlayerEntity, ClickType clickType, int x, int y, CallbackInfo ci){
        CanvasEntity canvas = (CanvasEntity) (Object) this;
        NbtCompound nbt = new NbtCompound();
        EntityModifyCallback.EVENT.invoker().modify(serverPlayerEntity.getServerWorld(), canvas.getBlockPos(), canvas.writeNbt(nbt), canvas, ItemStack.EMPTY, serverPlayerEntity, Sources.PLAYER);
    }
    @Inject(method = "onBreak", at = @At("HEAD"))
    public void onBreakLedgerLogging(Entity entity, CallbackInfo ci){
        if (entity instanceof ServerPlayerEntity serverPlayer){
            CanvasEntity canvas = (CanvasEntity) (Object) this;
            EntityKillCallback.EVENT.invoker().kill(canvas.getWorld(), canvas.getBlockPos(), canvas, entity.getDamageSources().playerAttack(serverPlayer));
        }
    }
}
