/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Modified from CancelBlockUpdate (https://github.com/Phoupraw/CancelBlockUpdate)
 * by xZhongjie (https://github.com/xZhongjie/UpdatedCancelBlockUpdate)
 */

package phoupraw.mcmod.cancelblockupdate.mixin;

import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import phoupraw.mcmod.cancelblockupdate.registry.CBUGameRules;

@Mixin(FluidState.class)
abstract class MFluidState {

    //以下是取消计划刻
    @Inject(method = "onScheduledTick", at = @At("HEAD"), cancellable = true)
    private void cancelScheduledTick(World world, BlockPos pos, CallbackInfo ci) {
        if (!CBUGameRules.getOff(world)) {
            ci.cancel();
        }
    }

}
