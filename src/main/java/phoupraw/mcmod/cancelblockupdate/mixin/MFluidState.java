package phoupraw.mcmod.cancelblockupdate.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import phoupraw.mcmod.cancelblockupdate.registry.CBUGameRules;

@Mixin(FluidState.class)
abstract class MFluidState {

    //以下是取消计划刻
    @Inject(method = "onScheduledTick", at = @At("HEAD"), cancellable = true)
    private void cancelScheduledTick(ServerWorld world, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (!CBUGameRules.getOff(world)) {
            ci.cancel();
        }
    }

}
