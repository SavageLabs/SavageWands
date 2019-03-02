package net.prosavage.savagewands.wands.impl;


import net.prosavage.savagewands.SavageWands;
import net.prosavage.savagewands.util.CraftKey;
import net.prosavage.savagewands.util.ItemBuilder;
import net.prosavage.savagewands.util.Placeholder;
import net.prosavage.savagewands.util.Util;
import net.prosavage.savagewands.util.nbt.NBTItem;
import net.prosavage.savagewands.wands.Wand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class CondenseWand extends Wand {


   public CondenseWand(ItemStack wandItemStack, Player player, Chest chest) {
      this.wandItemStack = wandItemStack;
      this.player = player;
      this.chest = chest;
   }

   public static ItemStack buildItem(Integer uses, boolean infinite) {
      ItemStack itemStack = Wand.buildItem();
      NBTItem nbtItem = new NBTItem(itemStack);
      nbtItem.setBoolean("Condense", true);
      if (infinite) {
         nbtItem.setInteger("Uses", Integer.MAX_VALUE);
      } else {
         if (uses == null) {
            uses = SavageWands.getInstance().getConfig().getInt("wands.condense.uses");
            nbtItem.setInteger("Uses", uses);
         } else {
            nbtItem.setInteger("Uses", uses);
         }
      }
      Placeholder usesPlaceholder = new Placeholder("{uses}", uses + "");
      if (infinite) {
         usesPlaceholder = new Placeholder("{uses}", "Infinite");
      }

      itemStack = nbtItem.getItem();
      return new ItemBuilder(itemStack)
              .name(SavageWands.getInstance().getConfig().getString("wands.condense.item.name"))
              .lore(Util.colorWithPlaceholders(SavageWands.getInstance().getConfig().getStringList("wands.condense.item.lore")
                      , usesPlaceholder))
              .glowing(SavageWands.getInstance().getConfig().getBoolean("wands.sell.item.glowing"))
              .build();
   }


   public static boolean isCondenseWand(ItemStack itemStack) {
      if (!Wand.isWand(itemStack)) {
         return false;
      }
      NBTItem nbtItem = new NBTItem(itemStack);
      return nbtItem.hasKey("Condense");
   }



   public void run() {
      Bukkit.getScheduler().runTaskAsynchronously(SavageWands.getInstance(), ()-> {
         HashMap<Material, CraftKey> craftKeys = SavageWands.getCraftKeys();
         chest.update();
         if (chest.getBlockInventory() == null) {
            updateWand();
         }
         HashMap<Material, Integer> validItems = new HashMap<>();
         Inventory inventory = chest.getBlockInventory();
         ArrayList<ItemStack> itemStacks = new ArrayList<>();
         for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack == null) {
               continue;
            }
            Material itemType = itemStack.getType();
            if (!craftKeys.containsKey(itemType)) {
               itemStacks.add(itemStack);
               continue;
            }
            if (validItems.containsKey(itemType)) {
               validItems.put(itemType, validItems.get(itemType) + itemStack.getAmount());
            } else {
               validItems.put(itemType, itemStack.getAmount());
            }
         }
         for (Material material : validItems.keySet()) {

            CraftKey craftKey = craftKeys.get(material);
            int craftedAmount = (int) Math.floor(validItems.get(material) / craftKey.getAmountToResult());
            int remainder = validItems.get(material) % craftKey.getAmountToResult();
            int fullStacks = (int) Math.floor(craftedAmount / 64);
            int remainingStacks = craftedAmount % 64;
            for (int i = 0; i < fullStacks; i++) {
               wandUsed = true;
               itemStacks.add(new ItemStack(craftKey.getResult(), 64));
            }
            if (remainingStacks != 0) {
               wandUsed = true;
               itemStacks.add(new ItemStack(craftKey.getResult(), remainingStacks));
            }
            if (remainder != 0) {
               itemStacks.add(new ItemStack(craftKey.getInitial(), remainder));
            }


         }
         inventory.setContents(itemStacks.toArray(new ItemStack[itemStacks.size()]));
         chest.update();
         super.updateWand();
         if (wandUsed) {
            player.sendMessage(Util.color(SavageWands.getInstance().getConfig().getString("messages.crafted-items")));
         }
      });
   }
}
