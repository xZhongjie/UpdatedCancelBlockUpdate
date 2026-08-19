package phoupraw.mcmod.cancelblockupdate.registry;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import org.jetbrains.annotations.ApiStatus;
import phoupraw.mcmod.cancelblockupdate.CancelBlockUpdate;

@ApiStatus.Internal
@Environment(EnvType.CLIENT)
public final class CBUClientModInitializer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> sender.sendPacket(new CBUPayloads.RequestSyncPayload()));
        ClientPlayNetworking.registerGlobalReceiver(CBUPayloads.SyncPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                var key = CBURegistries.BOOL_RULE.get(payload.ruleId());
                var world = context.client().world;
                if (world == null) return;
                CBUGameRules.CACHES.get(key).put(world, payload.value());
                CancelBlockUpdate.LOGGER.debug((Object) (world + "?" + key + "???" + payload.value()));
            });
        });
    }

}


