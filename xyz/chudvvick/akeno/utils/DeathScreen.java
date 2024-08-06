package xyz.chudvvick.akeno.utils;

import io.netty.buffer.Unpooled;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class DeathScreen {
   private final Player attacker;
   private final Player victim;

   public DeathScreen(Player attacker, Player victim) {
      this.attacker = attacker;
      this.victim = victim;
   }

   public void send() {
      ByteArrayOutputStream stream = new ByteArrayOutputStream();
      DataOutputStream output = new DataOutputStream(stream);

      try {
         output.writeUTF("KillAnimation");
         output.writeUTF(this.attacker.getName());
         output.writeUTF(this.victim.getName());
      } catch (IOException var5) {
      }

      PacketDataSerializer serializer = new PacketDataSerializer(Unpooled.wrappedBuffer(stream.toByteArray()));
      PacketPlayOutCustomPayload pay = new PacketPlayOutCustomPayload("MC|AmongUs", serializer);
      ((CraftPlayer)this.victim).getHandle().playerConnection.sendPacket(pay);
   }
}
