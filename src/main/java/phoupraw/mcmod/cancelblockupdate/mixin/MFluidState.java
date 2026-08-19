package phoupraw.mcmod.cancelblockupdate.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import phoupraw.mcmod.cancelblockupdate.registry.CBUGameRules;

@Mixin(FluidState.class)
abstract class MFluidState {

    //????????
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void cancelTick(ServerLevel world, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (!CBUGameRules.getOff(world)) {
            ci.cancel();
        }
    }

}
