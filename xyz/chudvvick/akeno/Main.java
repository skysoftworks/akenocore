package xyz.chudvvick.akeno;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayInCustomPayload;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.projectiles.ProjectileSource;
import xyz.chudvvick.akeno.utils.ColorUtil;
import xyz.chudvvick.akeno.utils.DeathScreen;
import xyz.chudvvick.akeno.utils.LoginGUI;
import xyz.chudvvick.akeno.utils.PayloadSend;

public class Main extends JavaPlugin implements Listener {
   private String machineIP;
   private String license;
   private static List<Player> whitelist = new ArrayList();
   private static final Set<Material> interactables;
   private int potionCooldown;
   private int enderCooldown;
   private boolean sojoin;
   private boolean cooldowns;
   private boolean logingui;

   static {
      interactables = new HashSet(Arrays.asList(Material.ANVIL, Material.COMMAND, Material.BED, Material.BEACON, Material.BED_BLOCK, Material.BREWING_STAND, Material.BURNING_FURNACE, Material.CAKE_BLOCK, Material.CHEST, Material.DIODE, Material.DIODE_BLOCK_OFF, Material.DIODE_BLOCK_ON, Material.DISPENSER, Material.DROPPER, Material.ENCHANTMENT_TABLE, Material.ENDER_CHEST, Material.FENCE_GATE, Material.FENCE_GATE, Material.FURNACE, Material.HOPPER, Material.IRON_DOOR, Material.IRON_DOOR_BLOCK, Material.ITEM_FRAME, Material.LEVER, Material.REDSTONE_COMPARATOR, Material.REDSTONE_COMPARATOR_OFF, Material.REDSTONE_COMPARATOR_ON, Material.STONE_BUTTON, Material.TRAP_DOOR, Material.TRAPPED_CHEST, Material.WOODEN_DOOR, Material.WOOD_BUTTON, Material.WOOD_DOOR, Material.WORKBENCH));
   }

   public void onEnable() {
      this.saveDefaultConfig();
      if (true) {
         Bukkit.getServer().getPluginManager().registerEvents(this, this);
         Bukkit.getServer().getPluginCommand("kaptan").setExecutor(new CommandKaptan());
      }

      this.potionCooldown = this.getConfig().getInt("potion-cooldown");
      this.enderCooldown = this.getConfig().getInt("ender-cooldown");
      this.cooldowns = this.getConfig().getBoolean("cooldowns");
      this.sojoin = this.getConfig().getBoolean("sojoin");
      this.logingui = this.getConfig().getBoolean("logingui");
      super.onEnable();
   }

   @EventHandler(
      priority = EventPriority.NORMAL
   )
   public void onDeath(PlayerDeathEvent event) {
      if (event.getEntity() instanceof Player) {
         Player victim = event.getEntity();
         EntityDamageEvent e = event.getEntity().getLastDamageCause();
         if (e instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent)e;
            if (nEvent.getDamager() instanceof Player) {
               Player killer = (Player)nEvent.getDamager();
               (new DeathScreen(killer, victim)).send();
            }
         }
      }
   }

   public void injectListener(final Player player) {
      ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
         public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
            if (packet instanceof PacketPlayInCustomPayload) {
               PacketPlayInCustomPayload customPayload = (PacketPlayInCustomPayload)packet;
               PacketDataSerializer data;
               String xx;
               String game;
               String cmd;
               String channel;
               byte[] bytes;
               if (Main.this.sojoin) {
                  (new PayloadSend(player, "Teyyapclntvars", "request###processList")).send();
                  if (customPayload.a().equals("Teyyapclntvars")) {
                     data = new PacketDataSerializer(Unpooled.buffer());
                     customPayload.b(data);
                     byte[] bytesx = new byte[data.readableBytes()];
                     data.readBytes(bytesx);
                     String x = new String(bytesx);
                     if (x.contains("processList")) {
                        if (x.contains("null")) {
                           player.kickPlayer(ColorUtil.fixColor("&cMuhtemelen hile kullanıyorsunuz eğer kullanmıyorsanız yetkililere bildiriniz!"));
                        } else {
                           if (x.contains("cheat") && x.contains("engine")) {
                              player.kickPlayer(ColorUtil.fixColor("&cMuhtemelen hile kullanıyorsunuz eğer kullanmıyorsanız yetkililere bildiriniz!"));
                              channelHandlerContext.close();
                           }

                           if (!Main.whitelist.contains(player)) {
                              Main.whitelist.add(player);
                           }
                        }
                     }
                  }

                  if (customPayload.a().equals("MC|GameMenu")) {
                     data = new PacketDataSerializer(Unpooled.buffer());
                     customPayload.b(data);
                     channel = data.c(20);
                     bytes = new byte[data.readableBytes()];
                     data.readBytes(bytes);
                     xx = new String(bytes);
                     if (xx.contains("SelectGame")) {
                        game = xx.split("SelectGame")[1].substring(1);
                        cmd = "warp " + game;
                        player.performCommand(cmd);
                     }
                  }
               } else if (customPayload.a().equals("MC|GameMenu")) {
                  data = new PacketDataSerializer(Unpooled.buffer());
                  customPayload.b(data);
                  channel = data.c(20);
                  bytes = new byte[data.readableBytes()];
                  data.readBytes(bytes);
                  xx = new String(bytes);
                  if (xx.contains("SelectGame")) {
                     game = xx.split("SelectGame")[1].substring(1);
                     cmd = "warp " + game;
                     player.performCommand(cmd);
                  }
               }
            }

            super.channelRead(channelHandlerContext, packet);
         }
      };
      ChannelPipeline pipeline = ((CraftPlayer)player).getHandle().playerConnection.networkManager.channel.pipeline();
      pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);
   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void onJoin(PlayerJoinEvent e) {
      this.injectListener(e.getPlayer());
      if (this.sojoin) {
         Bukkit.getScheduler().runTaskLater(this, () -> {
            if (!whitelist.contains(e.getPlayer())) {
               e.getPlayer().kickPlayer(ColorUtil.fixColor("&cBu Sunucuya sadece sonoyuncu launcher ile giriş yapabilirsin!"));
            }

         }, 300L);
      }

      if (this.logingui) {
         Bukkit.getScheduler().runTaskLater(this, () -> {
            (new LoginGUI(e.getPlayer())).send();
         }, 50L);
      }

   }

   @EventHandler(
      priority = EventPriority.NORMAL,
      ignoreCancelled = true
   )
   public void onProjectileLaunch(ProjectileLaunchEvent e) {
      if (this.cooldowns) {
         Projectile projectile = e.getEntity();
         if (projectile instanceof ThrownPotion) {
            ThrownPotion thrownPotion = (ThrownPotion)projectile;
            if (thrownPotion.getType() != null) {
               ProjectileSource shooter = thrownPotion.getShooter();
               if (shooter instanceof Player) {
                  Player player = (Player)shooter;
                  (new PayloadSend(player, "Teyyap", "itemcooldown###" + thrownPotion.getItem().getType().getId() + "###" + this.potionCooldown)).send();
               }
            }
         }
      }
   }

   @EventHandler
   public void onQuit(PlayerQuitEvent e) {
      if (whitelist.contains(e.getPlayer())) {
         whitelist.remove(e.getPlayer());
      }

   }

   @EventHandler(
      priority = EventPriority.NORMAL
   )
   public void onPlayerUseEP(PlayerInteractEvent event) {
      if (this.cooldowns) {
         if (event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK && event.getItem() != null && event.getItem().getType() == Material.ENDER_PEARL) {
            if (event.getClickedBlock() == null || event.isCancelled() || event.getPlayer().isSneaking() || !interactables.contains(event.getClickedBlock().getType())) {
               Player player = event.getPlayer();
               (new PayloadSend(player, "Teyyap", "itemcooldown###" + event.getItem().getType().getId() + "###" + this.enderCooldown)).send();
            }
         }
      }
   }

   @EventHandler(
      ignoreCancelled = true,
      priority = EventPriority.HIGHEST
   )
   public void onPlayerLogin(PlayerLoginEvent loginEvent) {
      if (loginEvent.getHostname().contains("minecraftCHC") && loginEvent.getHostname().contains("launcherCHC")) {
         try {
            String json = loginEvent.getHostname().split("###")[1];
            String suid = json.split("suid\":\"")[1].split("\",")[0];
            if (suid == null) {
               loginEvent.disallow(Result.KICK_OTHER, ColorUtil.fixColor("&cBu Sunucuya sadece sonoyuncu launcher ile giriş yapabilirsin!"));
            }
         } catch (Exception var4) {
            var4.printStackTrace();
            loginEvent.disallow(Result.KICK_OTHER, ColorUtil.fixColor("&cBu Sunucuya sadece sonoyuncu launcher ile giriş yapabilirsin!"));
         }
      } else {
         loginEvent.disallow(Result.KICK_OTHER, ColorUtil.fixColor("&cBu Sunucuya sadece sonoyuncu launcher ile giriş yapabilirsin!"));
      }

   }

   public void sendWebhook(String players) {
   }

   public boolean checkLicense() {
      return false;
   }

   // $FF: synthetic method
   private boolean lambda$2(String xd31) {
      return this.license.equals(xd31.split(":")[0]) ? xd31.split(":")[1].equals(this.machineIP) : false;
   }
}
