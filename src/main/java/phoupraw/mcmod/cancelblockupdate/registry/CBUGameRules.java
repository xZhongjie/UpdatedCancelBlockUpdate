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
 * 1.21.11 起 Minecraft 重构了游戏规则系统（{@link GameRule} / {@link GameRuleCategory}，包为
 * {@code net.minecraft.world.rule}），Fabric API 也改为 {@link GameRuleBuilder} + {@link GameRuleEvents}。
 * 本类与旧版本的差异仅在注册与取值 API，缓存与网络同步逻辑不变。
 */
public final class CBUGameRules {

    /**
     从世界到游戏规则的值的映射。客户端仅靠此来获取游戏规则值，而服务端如果检测到没有缓存，则会尝试获取服务器来获得游戏规则值并加入缓存。
     <br/>
     本模组修改的所有方法的形参都包含{@link World}、{@link WorldAccess}、{@link WorldView}等，所以以这个作为缓存的键比较合适。
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
     从{@link #}中获取游戏规则的值，如果为{@code null}，如果为{@link ServerWorldAccess}，则会从服务器获取规则的值，否则直接为{@code false}，将其添加到缓存中。
     @param key 键
     @return 游戏规则值。
     */
    public static boolean get(GameRule<Boolean> key, WorldView world) {
        var cache = CACHES.get(key);
        Boolean value = cache.get(world);
        if (value != null) return value;
        if (world instanceof ServerWorldAccess serverWorldAccess) {
            value = (Boolean) serverWorldAccess.toServerWorld().getServer().getGameRules().getValue(key);
        } else {
            value = false;
            StringWriter writer = new StringWriter();
            new Throwable().printStackTrace(new PrintWriter(writer));
            CancelBlockUpdate.LOGGER.error("无法获取" + key + "的CACHE值！已设为false。" + world + System.lineSeparator() + writer);
            //CancelBlockUpdate.LOGGER.catching(new IllegalStateException());
        }
        cache.put(world, value);
        return value;
    }

    private CBUGameRules() {
    }

}
