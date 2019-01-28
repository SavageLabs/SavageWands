package net.prosavage.savagewands.util;

import net.prosavage.savagewands.SavageWands;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Util {

   public static String formattedMessage(String string) {
      return ChatColor.translateAlternateColorCodes('&', "&7(&e!&7)&7 " + string);
   }

   public static String color(String string) {
      return ChatColor.translateAlternateColorCodes('&', string);
   }

   public static List<String> color(List<String> string) {
      List<String> colored = new ArrayList<>();
      for (String line : string) {
         colored.add(color(line));
      }
      return colored;
   }

   public static List<String> colorWithPlaceholders(List<String> string, Placeholder... placeholders) {
      List<String> colored = new ArrayList<>();
      for (String line : string) {
         String coloredLine = color(line);
         for (Placeholder placeholder : placeholders) {
            coloredLine = coloredLine.replace(placeholder.getKey(), placeholder.getValue());
         }
         colored.add(coloredLine);
      }
      return colored;
   }

   public static void broadcastMessage(String string) {
      for (Player player : SavageWands.getInstance().getServer().getOnlinePlayers()) {
         player.sendMessage(string);
      }
   }

   public static boolean isEmpty(Inventory inventory) {
      for(ItemStack it : inventory.getContents())
      {
         if(it != null) return false;
      }
      return true;
   }


}
