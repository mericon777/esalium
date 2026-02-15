package com.epeshi.esalium.event;

import com.epeshi.esalium.entity.ModEntities;
import com.epeshi.esalium.entity.custom.MertEntity;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.text.Text;

public class ModPlayerEvent {
    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            var player = handler.player;

            // HATA ÇÖZÜMÜ: getScoreboardTags yerine getCommandTags kullanıyoruz
            if (!player.getCommandTags().contains("met_mert")) {

                // Mert'i oyuncunun 2 blok önüne spawn et
                MertEntity mert = ModEntities.MERT.create(player.getWorld());
                if (mert != null) {
                    mert.refreshPositionAndAngles(player.getX() + 2, player.getY(), player.getZ(), 0, 0);
                    mert.setEndGame(false); // Başlangıç modu (Seni arıyordum diyaloğu)
                    player.getWorld().spawnEntity(mert);

                    // HATA ÇÖZÜMÜ: addScoreboardTag yerine addCommandTag
                    player.addCommandTag("met_mert"); // Tag ekle ki her girişte tekrar doğmasın

                    // Mesaj key'i esalium olarak güncellendi
                    player.sendMessage(Text.translatable("message.esalium.mert_spawn"), true);
                }
            }
        });
    }
}