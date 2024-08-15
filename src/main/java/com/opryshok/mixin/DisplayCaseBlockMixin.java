package com.opryshok.mixin;

import com.github.quiltservertools.ledger.callbacks.ItemInsertCallback;
import com.github.quiltservertools.ledger.utility.Sources;
import eu.pb4.polydecorations.block.item.DisplayCaseBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DisplayCaseBlock.class)
public class DisplayCaseBlockMixin {
    @Inject(method = "onUseWithItem", at = @At(value = "INVOKE", target = "Leu/pb4/polydecorations/block/other/GenericSingleItemBlockEntity;setStack(Lnet/minecraft/item/ItemStack;)V"))
    public void LedgerInsertLogging(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ItemActionResult> cir){
        if (player instanceof ServerPlayerEntity serverPlayer){
            ItemInsertCallback.EVENT.invoker().insert(stack.copyWithCount(1), pos, serverPlayer.getServerWorld(), Sources.PLAYER, serverPlayer);
        }
    }
}
