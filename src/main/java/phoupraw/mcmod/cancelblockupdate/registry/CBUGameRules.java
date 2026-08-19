package phoupraw.mcmod.cancelblockupdate.registry;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import phoupraw.mcmod.cancelblockupdate.CancelBlockUpdate;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 26.x??????? {@link GameRule} / {@link GameRuleCategory}?Mojang ???net.minecraft.world.level.gamerules??
 * Fabric API ?? {@link GameRuleBuilder} + {@link GameRuleEvents}?
 */
public final class CBUGameRules {

    /**
     ??????????????????????????????????????????????????????????????????
     <br/>
     ????????????????{@link LevelReader}??????????????????
     */
    public static final Map<GameRule<Boolean>, Map<LevelReader, Boolean>> CACHES;
    public static final GameRule<Boolean> OFF;
    public static final GameRule<Boolean> REPLACE;

    static {
        OFF = GameRuleBuilder.forBoolean(false)
          .category(GameRuleCategory.UPDATES)
          .buildAndRegister(CBUIdentifiers.OFF);
        REPLACE = GameRuleBuilder.forBoolean(false)
          .category(GameRuleCategory.UPDATES)
          .buildAndRegister(CBUIdentifiers.REPLACE);
        Registry.register(CBURegistries.BOOL_RULE, ResourceKey.create(CBURegistries.BOOL_RULE_KEY, CBUIdentifiers.OFF), OFF);
        Registry.register(CBURegistries.BOOL_RULE, ResourceKey.create(CBURegistries.BOOL_RULE_KEY, CBUIdentifiers.REPLACE), REPLACE);
        Map<GameRule<Boolean>, Map<LevelReader, Boolean>> map = new HashMap<>();
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
        for (ServerLevel level : server.getAllLevels()) CACHES.get(rule).put(level, newValue);
        int ruleId = CBURegistries.BOOL_RULE.getId(rule);
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            ServerPlayNetworking.send(player, new CBUPayloads.SyncPayload((byte) ruleId, newValue));
        }
    }

    /**
     @see #get
     */
    public static boolean getOff(LevelReader world) {
        return get(OFF, world);
    }

    /**
     ????????????????{@code null}????{@link ServerLevelAccessor}???????????????????{@code false}??????????
     @param key ?
     @return ??????
     */
    public static boolean get(GameRule<Boolean> key, LevelReader world) {
        var cache = CACHES.get(key);
        Boolean value = cache.get(world);
        if (value != null) return value;
        if (world instanceof ServerLevel serverLevel) {
            value = serverLevel.getServer().getGameRules().get(key);
        } else {
            value = false;
            StringWriter writer = new StringWriter();
            new Throwable().printStackTrace(new PrintWriter(writer));
            CancelBlockUpdate.LOGGER.error("????" + key + "?CACHE?????false?" + world + System.lineSeparator() + writer);
        }
        cache.put(world, value);
        return value;
    }

    private CBUGameRules() {
    }

}

