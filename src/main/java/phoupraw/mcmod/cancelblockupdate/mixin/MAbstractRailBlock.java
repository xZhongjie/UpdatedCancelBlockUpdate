/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Modified from CancelBlockUpdate (https://github.com/Phoupraw/CancelBlockUpdate)
 * by xZhongjie (https://github.com/xZhongjie/UpdatedCancelBlockUpdate)
 */

package phoupraw.mcmod.cancelblockupdate.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import phoupraw.mcmod.cancelblockupdate.registry.CBUGameRules;

/**
 ????????????? */
@Mixin(BaseRailBlock.class)
class MAbstractRailBlock {

    @Inject(method = "updateState", at = @At("HEAD"), cancellable = true)
    private void cancelUpdateState(BlockState state, Level world, BlockPos pos, boolean notify, CallbackInfoReturnable<BlockState> cir) {
        if (!CBUGameRules.getOff(world)) {
            cir.setReturnValue(state);
        }
    }

}
