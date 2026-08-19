/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Modified from CancelBlockUpdate (https://github.com/Phoupraw/CancelBlockUpdate)
 * by xZhongjie (https://github.com/xZhongjie/UpdatedCancelBlockUpdate)
 */

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
        ClientPlayNetworking.registerGlobalReceiver(CBUPayloads.SyncPayload.TYPE, (payload, context) -> {
            context.client().execute(() -> {
                var key = CBURegistries.BOOL_RULE.byId(payload.ruleId());
                var player = context.player();
                if (player == null) return;
                CBUGameRules.CACHES.get(key).put(player.level(), payload.value());
                CancelBlockUpdate.LOGGER.debug(player.level() + "?" + key + "???" + payload.value());
            });
        });
    }

}
