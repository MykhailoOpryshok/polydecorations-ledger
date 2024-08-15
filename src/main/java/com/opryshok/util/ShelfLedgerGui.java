package com.opryshok.util;

import eu.pb4.polydecorations.block.item.ShelfBlock;
import eu.pb4.polydecorations.block.item.ShelfBlockEntity;
import eu.pb4.polydecorations.ui.GuiTextures;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.SlabType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class ShelfLedgerGui extends LedgerSimpleGui {
    public BlockState state;
    public ShelfBlockEntity parent;

    public ShelfLedgerGui(ServerPlayerEntity player, ShelfBlockEntity parent) {
        super(parent.getCachedState().get(ShelfBlock.TYPE) == SlabType.DOUBLE ? ScreenHandlerType.GENERIC_9X2 : ScreenHandlerType.GENERIC_9X1, player, false);
        this.parent = parent;
        this.state = parent.getCachedState();
        this.setTitle((parent.getCachedState().get(ShelfBlock.TYPE) == SlabType.DOUBLE ? GuiTextures.SHELF_2 : GuiTextures.SHELF).apply(state.getBlock().getName()));
        switch (state.get(ShelfBlock.TYPE)) {
            case TOP:
                this.setSlotRedirect(3, new LedgerSlot(parent.getPos(), player, parent, 3, 0, 0));
                this.setSlotRedirect(4, new LedgerSlot(parent.getPos(), player, parent, 4, 1, 0));
                this.setSlotRedirect(5, new LedgerSlot(parent.getPos(), player, parent, 5, 2, 0));
                break;
            case BOTTOM:
                this.setSlotRedirect(3, new LedgerSlot(parent.getPos(), player, parent, 0, 0, 0));
                this.setSlotRedirect(4, new LedgerSlot(parent.getPos(), player, parent, 1, 1, 0));
                this.setSlotRedirect(5, new LedgerSlot(parent.getPos(), player, parent, 2, 2, 0));
                break;
            case DOUBLE:
                this.setSlotRedirect(3, new LedgerSlot(parent.getPos(), player, parent, 3, 0, 0));
                this.setSlotRedirect(4, new LedgerSlot(parent.getPos(), player, parent, 4, 1, 0));
                this.setSlotRedirect(5, new LedgerSlot(parent.getPos(), player, parent, 5, 2, 0));
                this.setSlotRedirect(12, new LedgerSlot(parent.getPos(), player, parent, 0, 0, 0));
                this.setSlotRedirect(13, new LedgerSlot(parent.getPos(), player, parent, 1, 1, 0));
                this.setSlotRedirect(14, new LedgerSlot(parent.getPos(), player, parent, 2, 2, 0));
        }

        this.open();
    }

    public void onClose() {
        super.onClose();
    }

    public void onTick() {
        if (parent.isRemoved() || this.player.getPos().squaredDistanceTo(Vec3d.ofCenter(parent.getPos())) > 324.0) {
            this.close();
        }

        if (this.state != parent.getCachedState()) {
            if (this.state.get(ShelfBlock.TYPE) != parent.getCachedState().get(ShelfBlock.TYPE)) {
                this.close();
                return;
            }

            this.state = parent.getCachedState();
        }

        super.onTick();
    }
}
