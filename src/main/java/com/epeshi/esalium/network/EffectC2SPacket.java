package com.epeshi.esalium.network;

import com.epeshi.esalium.Esalium;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record EffectC2SPacket(int effectType) implements CustomPayload {
    public static final Id<EffectC2SPacket> ID = new Id<>(Identifier.of(Esalium.MOD_ID, "effect_packet"));

    public static final PacketCodec<RegistryByteBuf, EffectC2SPacket> CODEC = CustomPayload.codecOf(
            (value, buf) -> buf.writeInt(value.effectType),
            buf -> new EffectC2SPacket(buf.readInt())
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}