/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Modified from CancelBlockUpdate (https://github.com/Phoupraw/CancelBlockUpdate)
 * by xZhongjie (https://github.com/xZhongjie/UpdatedCancelBlockUpdate)
 */

package phoupraw.mcmod.cancelblockupdate.registry;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import phoupraw.mcmod.cancelblockupdate.CancelBlockUpdate;

/**
 * 本模组在 1.20.5+ 使用的基于 {@link CustomPayload} 的自定义网络包。
 * <br/>
 * 旧版本（1.20.4 及以下）直接使用 Identifier 通道，见对应分支的代码。
 */
public final class CBUPayloads {

    private CBUPayloads() {
    }

    /**
     * 客户端在加入服务器时发送，请求服务器同步所有游戏规则的值。
     */
    public record RequestSyncPayload() implements CustomPayload {
        public static final CustomPayload.Id<RequestSyncPayload> ID = new CustomPayload.Id<>(CBUIdentifiers.REQUEST_SYNC);
        public static final PacketCodec<PacketByteBuf, RequestSyncPayload> CODEC = PacketCodec.of((payload, buf) -> {
        }, buf -> new RequestSyncPayload());

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }

    /**
     * 服务端发送给客户端，同步一个游戏规则的值。
     */
    public record SyncPayload(byte ruleId, boolean value) implements CustomPayload {
        public static final CustomPayload.Id<SyncPayload> ID = new CustomPayload.Id<>(CBUIdentifiers.CHANNEL);
        public static final PacketCodec<PacketByteBuf, SyncPayload> CODEC = PacketCodec.of(SyncPayload::write, SyncPayload::read);

        public static SyncPayload read(PacketByteBuf buf) {
            return new SyncPayload(buf.readByte(), buf.readBoolean());
        }

        public static void write(SyncPayload payload, PacketByteBuf buf) {
            buf.writeByte(payload.ruleId);
            buf.writeBoolean(payload.value);
        }

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }

}
