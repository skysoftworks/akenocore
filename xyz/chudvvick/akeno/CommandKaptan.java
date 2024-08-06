package xyz.chudvvick.akeno;

import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.chudvvick.akeno.utils.Menu;

public class CommandKaptan implements CommandExecutor {
   public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
      if (arg0 instanceof Player) {
         World pandora = Bukkit.getWorld("pandora");
         World liman = Bukkit.getWorld("world");
         World orman = Bukkit.getWorld("EjderOrmani");
         World nether = Bukkit.getWorld("nether");
         List<Player> pandoraList = (List)Bukkit.getOnlinePlayers().stream().filter((onlinePlayer) -> {
            return onlinePlayer.getWorld().equals(pandora);
         }).collect(Collectors.toList());
         List<Player> ormanList = (List)Bukkit.getOnlinePlayers().stream().filter((onlinePlayer) -> {
            return onlinePlayer.getWorld().equals(orman);
         }).collect(Collectors.toList());
         List<Player> limanList = (List)Bukkit.getOnlinePlayers().stream().filter((onlinePlayer) -> {
            return onlinePlayer.getWorld().equals(liman);
         }).collect(Collectors.toList());
         List<Player> netherList = (List)Bukkit.getOnlinePlayers().stream().filter((onlinePlayer) -> {
            return onlinePlayer.getWorld().equals(nether);
         }).collect(Collectors.toList());
         String json = "{\"Title\":\"Athena Kaptan\",\"Games\":[{\"ID\":\"orman\",\"TextureID\":\"survival\",\"DisplayName\":\"Orman\",\"Online\":\"%online_orman%\"},{\"ID\":\"pandora\",\"TextureID\":\"arenapvp\",\"DisplayName\":\"Pandora\",\"Online\":\"%online_pandora%\"},{\"ID\":\"nether\",\"TextureID\":\"thepit\",\"DisplayName\":\"Nether\",\"Online\":\"%online_nether%\"},{\"ID\":\"liman\",\"TextureID\":\"survivaltitanium\",\"DisplayName\":\"Titanya Limanı\",\"Online\":\"%online_liman%\"}]}";
         json = json.replaceAll("%online_orman%", String.valueOf(ormanList.size())).replaceAll("%online_liman%", String.valueOf(limanList.size())).replaceAll("%online_pandora%", String.valueOf(pandoraList.size())).replaceAll("%online_nether%", String.valueOf(netherList.size()));
         (new Menu((Player)arg0, "MC|GameMenu", "Open", json)).send();
      }

      return true;
   }
}
