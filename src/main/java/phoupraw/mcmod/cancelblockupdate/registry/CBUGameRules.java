/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Modified from CancelBlockUpdate (https://github.com/Phoupraw/CancelBlockUpdate)
 * by xZhongjie (https://github.com/xZhongjie/UpdatedCancelBlockUpdate)
 */

package phoupraw.mcmod.cancelblockupdate.registry;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.*;
import net.minecraft.world.rule.GameRule;
import net.minecraft.world.rule.GameRuleCategory;
import phoupraw.mcmod.cancelblockupdate.CancelBlockUpdate;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 1.21.11 ? Minecraft ??????????{@link GameRule} / {@link GameRuleCategory}???
 * {@code net.minecraft.world.rule}??Fabric API ??? {@link GameRuleBuilder} + {@link GameRuleEvents}?
 * ???????????????? API?????????????
 */
public final class CBUGameRules {

    /**
     ??????????????????????????????????????????????????????????????????
     <br/>
     ????????????????{@link World}?{@link WorldAccess}?{@link WorldView}??????????????????
     */
    public static final Map<GameRule<Boolean>, Map<WorldView, Boolean>> CACHES;
    public static final GameRule<Boolean> OFF;
    public static final GameRule<Boolean> REPLACE;

    static {
        OFF = GameRuleBuilder.forBoolean(false)
          .category(GameRuleCategory.UPDATES)
          .buildAndRegister(CBUIdentifiers.OFF);
        REPLACE = GameRuleBuilder.forBoolean(false)
          .category(GameRuleCategory.UPDATES)
          .buildAndRegister(CBUIdentifiers.REPLACE);
        Registry.register(CBURegistries.BOOL_RULE, RegistryKey.of(CBURegistries.BOOL_RULE_KEY, CBUIdentifiers.OFF), OFF);
        Registry.register(CBURegistries.BOOL_RULE, RegistryKey.of(CBURegistries.BOOL_RULE_KEY, CBUIdentifiers.REPLACE), REPLACE);
        Map<GameRule<Boolean>, Map<WorldView, Boolean>> map = new HashMap<>();
        for (var key : CBURegistries.BOOL_RULE) map.put(key, new WeakHashMap<>());
        CACHES = map;
        GameRuleEvents.changeCallback(OFF).register(CBUGameRules::onOffChanged);
        GameRuleEvents.changeCallback(REPLACE).register(CBUGameRules::onReplaceChanged);
    }

    private static void onOffChanged(Boolean value, MinecraftServer server) {
        onChange(OFF, value, server);
    }

    private static void onReplaceChanged(Boolean value, MinecraftServer server) {
        onChange(REPLACE, value, server);
    }

    private static void onChange(GameRule<Boolean> rule, boolean newValue, MinecraftServer server) {
        for (ServerWorld world : server.getWorlds()) CACHES.get(rule).put(world, newValue);
        int ruleId = CBURegistries.BOOL_RULE.getRawId(rule);
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            ServerPlayNetworking.send(player, new CBUPayloads.SyncPayload((byte) ruleId, newValue));
        }
    }

    /**
     @see #get
     */
    public static boolean getOff(WorldView world) {
        return get(OFF, world);
    }

    /**
     ?{@link #}?????????????{@code null}????{@link ServerWorldAccess}???????????????????{@code false}??????????
     @param key ?
     @return ??????
     */
    public static boolean get(GameRule<Boolean> key, WorldView world) {
        var cache = CACHES.get(key);
        Boolean value = cache.get(world);
        if (value != null) return value;
        if (world instanceof ServerWorldAccess serverWorldAccess) {
            value = (Boolean) serverWorldAccess.toServerWorld().getGameRules().getValue(key);
        } else {
            value = false;
            StringWriter writer = new StringWriter();
            new Throwable().printStackTrace(new PrintWriter(writer));
            CancelBlockUpdate.LOGGER.error("????" + key + "?CACHE?????false?" + world + System.lineSeparator() + writer);
            //CancelBlockUpdate.LOGGER.catching(new IllegalStateException());
        }
        cache.put(world, value);
        return value;
    }

    private CBUGameRules() {
    }

}

