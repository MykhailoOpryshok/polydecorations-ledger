package com.opryshok.mixin;

import com.github.quiltservertools.ledger.callbacks.ItemInsertCallback;
import com.github.quiltservertools.ledger.callbacks.ItemRemoveCallback;
import com.github.quiltservertools.ledger.utility.Sources;
import com.llamalad7.mixinextras.sugar.Local;
import eu.pb4.polydecorations.block.item.ToolRackBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ToolRackBlock.class)
public class ToolRackBlockMixin {
    @Inject(method = "onUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;copyWithCount(I)Lnet/minecraft/item/ItemStack;"))
    public void LedgerInsertLogging(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir){
        if (player instanceof ServerPlayerEntity serverPlayer){
            ItemInsertCallback.EVENT.invoker().insert(serverPlayer.getMainHandStack().copyWithCount(1), pos, serverPlayer.getServerWorld(), Sources.PLAYER, serverPlayer);
        }
    }
    @Inject(method = "onUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setStackInHand(Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;)V"))
    public void LedgerRemoveLogging(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir, @Local (ordinal = 0) ItemStack currentStack){
        if (player instanceof ServerPlayerEntity serverPlayer){
            ItemRemoveCallback.EVENT.invoker().remove(currentStack, pos, serverPlayer.getServerWorld(), Sources.PLAYER, serverPlayer);
        }
    }
}