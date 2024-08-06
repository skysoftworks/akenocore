package xyz.chudvvick.akeno.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class Yaml {
   private final String fileURL;
   private final File file;
   private FileConfiguration fileConfiguration;

   public Yaml(String fileURL, BufferedReader bufferedReader) {
      this.fileURL = fileURL;
      this.file = new File(fileURL);
      if (!this.file.exists()) {
         createFile(fileURL);
         if (bufferedReader != null) {
            try {
               BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.file)));

               String line;
               while((line = bufferedReader.readLine()) != null) {
                  bw.write(line);
                  bw.newLine();
               }

               bufferedReader.close();
               bw.close();
            } catch (IOException var5) {
               System.out.println("An internal error");
               var5.printStackTrace();
            }
         }
      }

      this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
   }

   public Yaml(String fileURL, InputStream inputStream) {
      this(fileURL, inputStream != null ? new BufferedReader(new InputStreamReader(inputStream)) : null);
   }

   public Yaml(String fileURL, String resourceName) {
      this(fileURL, Yaml.class.getResourceAsStream("/" + resourceName));
   }

   public Yaml(String fileURL) {
      this(fileURL, (InputStream)null);
   }

   public static void createFile(String url) {
      if (!(new File(url)).exists()) {
         url = url.replace("/", "\\");
         String[] sp = url.split(Pattern.quote("\\"));
         String folder = url.substring(0, url.length() - sp[sp.length - 1].length());

         try {
            (new File(folder.replace("\\", "/"))).mkdirs();
            (new File(url.replace("\\", "/"))).createNewFile();
         } catch (IOException var4) {
            var4.printStackTrace();
         }

      }
   }

   public void reload() {
      this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
   }

   public void unload() {
      this.fileConfiguration = null;
      this.file.delete();
   }

   public void save() {
      try {
         this.fileConfiguration.save(this.file);
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }

   public File getFile() {
      return this.file;
   }

   public String getFileURL() {
      return this.fileURL;
   }

   public FileConfiguration getFileConfiguration() {
      return this.fileConfiguration;
   }

   public void set(String path, Object value) {
      this.fileConfiguration.set(path, value);
   }

   public boolean isSet(String path) {
      return this.fileConfiguration.isSet(path);
   }

   public String getString(String path) {
      return ChatColor.translateAlternateColorCodes('&', this.fileConfiguration.getString(path));
   }

   public String getString(String path, String def) {
      return this.fileConfiguration.getString(path, def);
   }

   public boolean isString(String path) {
      return this.fileConfiguration.isString(path);
   }

   public int getInt(String path) {
      return this.fileConfiguration.getInt(path);
   }

   public int getInt(String path, int def) {
      return this.fileConfiguration.getInt(path, def);
   }

   public boolean isInt(String path) {
      return this.fileConfiguration.isInt(path);
   }

   public boolean getBoolean(String path) {
      return this.fileConfiguration.getBoolean(path);
   }

   public boolean getBoolean(String path, boolean def) {
      return this.fileConfiguration.getBoolean(path, def);
   }

   public boolean isBoolean(String path) {
      return this.fileConfiguration.isBoolean(path);
   }

   public double getDouble(String path) {
      return this.fileConfiguration.getDouble(path);
   }

   public double getDouble(String path, double def) {
      return this.fileConfiguration.getDouble(path, def);
   }

   public boolean isDouble(String path) {
      return this.fileConfiguration.isDouble(path);
   }

   public long getLong(String path) {
      return this.fileConfiguration.getLong(path);
   }

   public long getLong(String path, long def) {
      return this.fileConfiguration.getLong(path, def);
   }

   public boolean isLong(String path) {
      return this.fileConfiguration.isLong(path);
   }

   public List<?> getList(String path) {
      return this.fileConfiguration.getList(path);
   }

   public List<?> getList(String path, List<?> def) {
      return this.fileConfiguration.getList(path, def);
   }

   public boolean isList(String path) {
      return this.fileConfiguration.isList(path);
   }

   public List<String> getStringList(String path) {
      return this.fileConfiguration.getStringList(path);
   }

   public List<Integer> getIntegerList(String path) {
      return this.fileConfiguration.getIntegerList(path);
   }

   public List<Boolean> getBooleanList(String path) {
      return this.fileConfiguration.getBooleanList(path);
   }

   public List<Double> getDoubleList(String path) {
      return this.fileConfiguration.getDoubleList(path);
   }

   public List<Float> getFloatList(String path) {
      return this.fileConfiguration.getFloatList(path);
   }

   public List<Long> getLongList(String path) {
      return this.fileConfiguration.getLongList(path);
   }

   public List<Byte> getByteList(String path) {
      return this.fileConfiguration.getByteList(path);
   }

   public List<Character> getCharacterList(String path) {
      return this.fileConfiguration.getCharacterList(path);
   }

   public List<Short> getShortList(String path) {
      return this.fileConfiguration.getShortList(path);
   }

   public List<Map<?, ?>> getMapList(String path) {
      return this.fileConfiguration.getMapList(path);
   }

   public OfflinePlayer getOfflinePlayer(String path) {
      return this.fileConfiguration.getOfflinePlayer(path);
   }

   public OfflinePlayer getOfflinePlayer(String path, OfflinePlayer def) {
      return this.fileConfiguration.getOfflinePlayer(path, def);
   }

   public boolean isOfflinePlayer(String path) {
      return this.fileConfiguration.isOfflinePlayer(path);
   }

   public Location getLocation(String path) {
      return (Location)this.fileConfiguration.get(path);
   }

   public Location getLocation(String path, Location def) {
      Location location = this.getLocation(path);
      return location != null ? location : def;
   }

   public boolean isLocation(String path) {
      return this.getLocation(path) != null;
   }

   public List<Location> getLocationList(String path) {
      ArrayList<Location> locations = new ArrayList();
      if (!this.fileConfiguration.isList(path)) {
         return locations;
      } else {
         Iterator var4 = this.fileConfiguration.getList(path).iterator();

         while(var4.hasNext()) {
            Object locs = var4.next();
            locations.add((Location)locs);
         }

         return locations;
      }
   }

   public ItemStack getItemStack(String path) {
      return (ItemStack)this.fileConfiguration.get(path);
   }

   public ItemStack getItemStack(String path, ItemStack def) {
      ItemStack itemStack = this.getItemStack(path);
      return itemStack != null ? itemStack : def;
   }

   public boolean isItemStack(String path) {
      return this.getItemStack(path) != null;
   }

   public List<ItemStack> getItemStackList(String path) {
      ArrayList<ItemStack> itemStackList = new ArrayList();
      Iterator var4 = this.getList(path).iterator();

      while(var4.hasNext()) {
         Object items = var4.next();
         itemStackList.add((ItemStack)items);
      }

      return itemStackList;
   }

   public List<ItemStack> getItemStackList(String path, List<ItemStack> def) {
      List<ItemStack> itemStackList = this.getItemStackList(path);
      return itemStackList.size() != 0 ? itemStackList : def;
   }

   public ConfigurationSection getConfigurationSection(String path) {
      return this.fileConfiguration.getConfigurationSection(path);
   }

   public boolean isConfigurationSection(String path) {
      return this.fileConfiguration.isConfigurationSection(path);
   }
}
