/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Modified from CancelBlockUpdate (https://github.com/Phoupraw/CancelBlockUpdate)
 * by xZhongjie (https://github.com/xZhongjie/UpdatedCancelBlockUpdate)
 */

package phoupraw.mcmod.cancelblockupdate.registry;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

/**
 * 26.x ???????? {@link CustomPacketPayload} ? {@link StreamCodec}?Mojang ????
 */
public final class CBUPayloads {

    private CBUPayloads() {
    }

    /**
     * ?????????????????????????????
     */
    public record RequestSyncPayload() implements CustomPacketPayload {
        public static final CustomPacketPayload.Type<RequestSyncPayload> TYPE = new CustomPacketPayload.Type<>(CBUIdentifiers.REQUEST_SYNC);
        public static final StreamCodec<FriendlyByteBuf, RequestSyncPayload> CODEC = StreamCodec.unit(new RequestSyncPayload());

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    /**
     * ?????????????????????
     */
    public record SyncPayload(byte ruleId, boolean value) implements CustomPacketPayload {
        public static final CustomPacketPayload.Type<SyncPayload> TYPE = new CustomPacketPayload.Type<>(CBUIdentifiers.CHANNEL);
        public static final StreamCodec<FriendlyByteBuf, SyncPayload> CODEC = CustomPacketPayload.codec(SyncPayload::write, SyncPayload::read);

        public static SyncPayload read(FriendlyByteBuf buf) {
            return new SyncPayload(buf.readByte(), buf.readBoolean());
        }

        public static void write(SyncPayload payload, FriendlyByteBuf buf) {
            buf.writeByte(payload.ruleId);
            buf.writeBoolean(payload.value);
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

}

