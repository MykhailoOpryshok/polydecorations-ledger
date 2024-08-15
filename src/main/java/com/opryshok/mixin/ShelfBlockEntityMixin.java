package com.opryshok.mixin;

import com.opryshok.util.ShelfLedgerGui;
import eu.pb4.polydecorations.block.item.ShelfBlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShelfBlockEntity.class)
public class ShelfBlockEntityMixin {
    @Inject(method = "createGui", at = @At("HEAD"), cancellable = true)
    public void injectGui(ServerPlayerEntity playerEntity, CallbackInfo ci) {
        new ShelfLedgerGui(playerEntity, (ShelfBlockEntity) (Object) this);
        ci.cancel();
    }
}
