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
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import phoupraw.mcmod.cancelblockupdate.registry.CBUGameRules;

@Mixin(BlockBehaviour.BlockStateBase.class)
abstract class MAbstractBlockState {

    @Shadow
    public abstract Block getBlock();
//?????????
    @Inject(method = "updateShape", at = @At("HEAD"), cancellable = true)
    private void cancelUpdateShape(LevelReader world, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random, CallbackInfoReturnable<BlockState> cir) {
        if (!CBUGameRules.getOff(world)) {
            //noinspection ConstantConditions
            cir.setReturnValue((BlockState) (Object) this);
        }
    }

    @Inject(method = "handleNeighborChanged", at = @At("HEAD"), cancellable = true)
    private void cancelHandleNeighborChanged(Level world, BlockPos pos, Block block, Orientation orientation, boolean notify, CallbackInfo ci) {
        if (!CBUGameRules.getOff(world)) {
            ci.cancel();
        }
    }

    @Inject(method = "updateNeighbourShapes*", at = @At("HEAD"), cancellable = true)
    private void cancelUpdateNeighbourShapes(LevelAccessor world, BlockPos pos, int flags, CallbackInfo ci) {
        if (!CBUGameRules.getOff(world)) {
            ci.cancel();
        }
    }

    //????????
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void cancelTick(ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (!CBUGameRules.getOff(world)) {
            ci.cancel();
        }
    }

    //?????????
    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    private void passCanSurvive(LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (!CBUGameRules.getOff(world)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void cancelRandomTick(ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (!CBUGameRules.getOff(world)) {
            ci.cancel();
        }
    }

    @Inject(method = "canBeReplaced", at = @At("RETURN"), cancellable = true)
    private void setCanBeReplaced(BlockPlaceContext context, CallbackInfoReturnable<Boolean> cir) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        VoxelShape shape = world.getBlockState(pos).getShape(world, pos);
        if (!CBUGameRules.get(CBUGameRules.REPLACE, world) && !shape.isEmpty() && !(CBUGameRules.get(CBUGameRules.STACK_SLABS, world) && cir.getReturnValue())) {
            cir.setReturnValue(false);
        }
    }

}

