package xyz.chudvvick.akeno.utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;

public class LoginGUI {
   private final Player player;

   public LoginGUI(Player player) {
      this.player = player;
   }

   public void send() {
      String x = "{\"action\":\"displayGUI\",\"screenType\":\"login\",\"username\":\"%player%\",\"chatLoginFormat\":\"/login <password>\"}".replaceAll("%player%", this.player.getName());
      IChatBaseComponent component = CraftChatMessage.fromString(x)[0];
      PacketPlayOutChat pay = new PacketPlayOutChat(component, (byte)10);
      ((CraftPlayer)this.player).getHandle().playerConnection.sendPacket(pay);
   }
}
