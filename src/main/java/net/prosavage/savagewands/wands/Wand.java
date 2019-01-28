package net.prosavage.savagewands.wands;

import net.prosavage.savagewands.SavageWands;
import net.prosavage.savagewands.util.ItemBuilder;
import net.prosavage.savagewands.util.Placeholder;
import net.prosavage.savagewands.util.Util;
import net.prosavage.savagewands.util.nbt.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Wand {

   protected Chest chest;
   protected Player player;
   protected ItemStack wandItemStack;
   protected boolean wandUsed = false;



   public static ItemStack buildItem() {
      ItemStack itemStack = new ItemStack(Material.STICK, 1);
      NBTItem nbtItem = new NBTItem(itemStack);
      nbtItem.setBoolean("Wand", true);
      nbtItem.setInteger("ID", ThreadLocalRandom.current().nextInt(0, 1000));
      return nbtItem.getItem();
   }

   public static boolean isWand(ItemStack itemStack) {
      NBTItem nbtItem = new NBTItem(itemStack);
      return nbtItem.hasKey("Wand");
   }

   private void useDurability() {
      Placeholder usesLeftPlaceholder;
      NBTItem nbtItem = new NBTItem(wandItemStack);
      if (nbtItem.getInteger("Uses") != Integer.MAX_VALUE) {
         nbtItem.setInteger("Uses", nbtItem.getInteger("Uses") - 1);
         if (nbtItem.getInteger("Uses") <= 0) {
            wandItemStack = new ItemStack(Material.AIR);
            return;
         } else {
            wandItemStack = nbtItem.getItem();
         }
         int usesLeft = new NBTItem(wandItemStack).getInteger("Uses");
         usesLeftPlaceholder = new Placeholder(((usesLeft + 1) + ""), usesLeft + "");
      } else {
         usesLeftPlaceholder = new Placeholder("{uses}", "Infinite");
      }

      List<String> lore = wandItemStack.getItemMeta().getLore();
      this.wandItemStack = new ItemBuilder(wandItemStack).lore(Util.colorWithPlaceholders(lore, usesLeftPlaceholder)).build();
   }

   public void takeWand() {
      player.setItemInHand(new ItemStack(Material.AIR));
   }

   protected void updateWand() {
      if (wandUsed) {
         useDurability();
      } else {
         player.sendMessage(Util.color(SavageWands.getInstance().getConfig().getString("messages.wand-not-used")));
      }
      wandItemStack.setAmount(1);
      player.getInventory().addItem(wandItemStack);
   }



   public abstract void run();


}
