/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Modified from CancelBlockUpdate (https://github.com/Phoupraw/CancelBlockUpdate)
 * by xZhongjie (https://github.com/xZhongjie/UpdatedCancelBlockUpdate)
 */

package phoupraw.mcmod.cancelblockupdate.registry;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityLevelChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLevelEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.ApiStatus;
import phoupraw.mcmod.cancelblockupdate.CancelBlockUpdate;

import java.util.Objects;

@ApiStatus.Internal
public final class CBUModInitializer implements ModInitializer {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void loadClasses() {
        CBUGameRules.CACHES.hashCode();
    }

    private static void onLevelLoad(MinecraftServer server, ServerLevel level) {
        for (var key : CBURegistries.BOOL_RULE) {
            CBUGameRules.CACHES.get(key).put(level, server.getGameRules().get(key));
        }
    }

    private static void afterChangeLevel(ServerPlayer player, ServerLevel origin, ServerLevel destination) {
        var server = Objects.requireNonNull(player.level().getServer(), "player=" + player);
        for (var key : CBURegistries.BOOL_RULE) {
            ServerPlayNetworking.send(player, new CBUPayloads.SyncPayload((byte) CBURegistries.BOOL_RULE.getId(key), server.getGameRules().get(key)));
        }
    }

    private static void onRequestSync(CBUPayloads.RequestSyncPayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerPlayer player = context.player();
            if (player == null) return;
            MinecraftServer server = context.server();
            for (var key : CBURegistries.BOOL_RULE) {
                ServerPlayNetworking.send(player, new CBUPayloads.SyncPayload((byte) CBURegistries.BOOL_RULE.getId(key), server.getGameRules().get(key)));
            }
        });
    }

    /**
     ?????????schedule?random??????
     */
    private static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection environment) {
        dispatcher.register(Commands.literal(CancelBlockUpdate.MOD_ID)
          .then(Commands.literal("schedule")
            .then(Commands.argument("pos", BlockPosArgument.blockPos())
              .executes(context -> {
                  BlockPos pos = BlockPosArgument.getLoadedBlockPos(context, "pos");
                  ServerLevel level = context.getSource().getLevel();
                  BlockState blockState = level.getBlockState(pos);
                  blockState.getBlock().tick(blockState, level, pos, level.getRandom());
                  return 1;
              })))
          .then(Commands.literal("random")
            .then(Commands.argument("pos", BlockPosArgument.blockPos())
              .executes(context -> {
                  BlockPos pos = BlockPosArgument.getLoadedBlockPos(context, "pos");
                  ServerLevel level = context.getSource().getLevel();
                  BlockState blockState = level.getBlockState(pos);
                  blockState.getBlock().randomTick(blockState, level, pos, level.getRandom());
                  return 1;
              }))));
    }

    @Override
    public void onInitialize() {
        loadClasses();
        PayloadTypeRegistry.serverboundPlay().register(CBUPayloads.RequestSyncPayload.TYPE, CBUPayloads.RequestSyncPayload.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(CBUPayloads.SyncPayload.TYPE, CBUPayloads.SyncPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(CBUPayloads.RequestSyncPayload.TYPE, CBUModInitializer::onRequestSync);
        ServerEntityLevelChangeEvents.AFTER_PLAYER_CHANGE_LEVEL.register(CBUModInitializer::afterChangeLevel);
        CommandRegistrationCallback.EVENT.register(CBUModInitializer::register);
        ServerLevelEvents.LOAD.register(CBUModInitializer::onLevelLoad);
    }

}

