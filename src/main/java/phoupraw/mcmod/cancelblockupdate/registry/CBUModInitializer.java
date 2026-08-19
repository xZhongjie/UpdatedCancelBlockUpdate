package phoupraw.mcmod.cancelblockupdate.registry;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents.Load;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.ApiStatus;
import phoupraw.mcmod.cancelblockupdate.CancelBlockUpdate;

import java.util.Objects;

@ApiStatus.Internal
public final class CBUModInitializer implements ModInitializer {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void loadClasses() {
        CBUGameRules.CACHES.hashCode();

    }

    /**
     ???????????????????????????????{@link CBUGameRules#getOff}???????????????????????????
     @see Load#onWorldLoad
     */
    private static void onWorldLoad(MinecraftServer server, ServerWorld world) {
        for (var key : CBURegistries.BOOL_RULE) {
            CBUGameRules.CACHES.get(key).put(world, (Boolean) world.getGameRules().getValue(key));
        }
    }

    private static void afterChangeWorld(ServerPlayerEntity player, ServerWorld origin, ServerWorld destination) {
        for (var key : CBURegistries.BOOL_RULE) {
            ServerPlayNetworking.send(player, new CBUPayloads.SyncPayload((byte) CBURegistries.BOOL_RULE.getRawId(key), (Boolean) destination.getGameRules().getValue(key)));
        }
    }

    /**
     ???????????????????????????????
     */
    private static void onRequestSync(CBUPayloads.RequestSyncPayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerPlayerEntity player = context.player();
            if (player == null) return;
            for (var key : CBURegistries.BOOL_RULE) {
                ServerPlayNetworking.send(player, new CBUPayloads.SyncPayload((byte) CBURegistries.BOOL_RULE.getRawId(key), (Boolean) player.getEntityWorld().getGameRules().getValue(key)));
            }
        });
    }

    /**
     ?????????schedule?random??????
     @see CommandRegistrationCallback#register
     */
    private static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal(CancelBlockUpdate.MOD_ID)
          .then(CommandManager.literal("schedule")
            .then(CommandManager.argument("pos", BlockPosArgumentType.blockPos())
              .executes(context -> {
                  BlockPos pos = BlockPosArgumentType.getBlockPos(context, "pos");
                  ServerWorld world = context.getSource().getWorld();
                  BlockState blockState = world.getBlockState(pos);
                  //noinspection deprecation
                  blockState.getBlock().scheduledTick(blockState, world, pos, world.getRandom());
                  return 1;
              })))
          .then(CommandManager.literal("random")
            .then(CommandManager.argument("pos", BlockPosArgumentType.blockPos())
              .executes(context -> {
                  BlockPos pos = BlockPosArgumentType.getBlockPos(context, "pos");
                  ServerWorld world = context.getSource().getWorld();
                  BlockState blockState = world.getBlockState(pos);
                  //noinspection deprecation
                  blockState.getBlock().randomTick(blockState, world, pos, world.getRandom());
                  return 1;
              }))));
    }

    @Override
    public void onInitialize() {
        loadClasses();
        PayloadTypeRegistry.playC2S().register(CBUPayloads.RequestSyncPayload.ID, CBUPayloads.RequestSyncPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(CBUPayloads.SyncPayload.ID, CBUPayloads.SyncPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(CBUPayloads.RequestSyncPayload.ID, CBUModInitializer::onRequestSync);
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(CBUModInitializer::afterChangeWorld);
        CommandRegistrationCallback.EVENT.register(CBUModInitializer::register);
        ServerWorldEvents.LOAD.register(CBUModInitializer::onWorldLoad);
    }

}

