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
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;
import phoupraw.mcmod.cancelblockupdate.CancelBlockUpdate;

import java.util.Objects;

@ApiStatus.Internal
@Environment(EnvType.CLIENT)
public final class CBUClientModInitializer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> sender.sendPacket(CBUIdentifiers.CHANNEL, PacketByteBufs.empty()));
        ClientPlayNetworking.registerGlobalReceiver(CBUIdentifiers.CHANNEL, (client, handler, buf, responseSender) -> {
            var key = CBURegistries.BOOL_RULE.get(buf.readByte());
            boolean value = buf.readBoolean();
            var player = Objects.requireNonNull(client.player, client.toString());
            World world = player.getWorld();
            CBUGameRules.CACHES.get(key).put(world, value);
            CancelBlockUpdate.LOGGER.debug((Object) (world + "@" + System.identityHashCode(world) + "的" + key + "已改为" + value));
        });
    }

}
