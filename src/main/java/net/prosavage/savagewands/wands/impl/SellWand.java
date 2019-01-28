package net.prosavage.savagewands.wands.impl;

import net.prosavage.savagewands.SavageWands;
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

public class SellWand extends Wand {

   public SellWand(ItemStack wandItemStack, Player player, Chest chest) {
      this.wandItemStack = wandItemStack;
      this.player = player;
      this.chest = chest;
   }

   public static ItemStack buildItem(Integer uses, Integer tier) {
      ItemStack itemStack = Wand.buildItem();
      NBTItem nbtItem = new NBTItem(itemStack);
      nbtItem.setBoolean("Sell", true);
      if (uses == null) {
         uses = SavageWands.getInstance().getConfig().getInt("wands.sell.uses");
         nbtItem.setInteger("Uses", uses);

      } else {
         nbtItem.setInteger("Uses", uses);
      }
      if (tier == null) {
         tier = 1;
         nbtItem.setInteger("Tier", 1);
      } else {
         nbtItem.setInteger("Tier", tier);
      }
      itemStack = nbtItem.getItem();
      return new ItemBuilder(itemStack)
              .name(SavageWands.getInstance().getConfig().getString("wands.sell.item.name"))
              .lore(Util.colorWithPlaceholders(SavageWands.getInstance().getConfig().getStringList("wands.sell.item.lore")
                      , new Placeholder("{uses}", uses + "")
                      , new Placeholder("{multiplier}", SavageWands.getInstance().getConfig().getDouble("wands.sell.tiers." + tier) + "x")))
              .glowing(SavageWands.getInstance().getConfig().getBoolean("wands.sell.item.glowing"))
              .build();
   }


   public static boolean isSellWand(ItemStack itemStack) {
      if (!Wand.isWand(itemStack)) {
         return false;
      }
      NBTItem nbtItem = new NBTItem(itemStack);
      if (nbtItem.hasKey("Sell")) {
         return true;
      }
      return false;
   }

   protected int getTier() {
      NBTItem nbtItem = new NBTItem(wandItemStack);
      if (!nbtItem.hasKey("Tier")) {
         return 1;
      }
      return nbtItem.getInteger("Tier");
   }

   public void run() {
      Bukkit.getScheduler().runTaskAsynchronously(SavageWands.getInstance(), ()-> {
         double multiplier = SavageWands.getInstance().getConfig().getDouble("wands.sell.tiers." + getTier());
         int itemsSold = 0;
         double moneyEarned = 0;
         HashMap<Material, Double> costMap = SavageWands.getCostMap();
         chest.update();
         if (chest.getBlockInventory() == null) {
            updateWand();
         }
         Inventory inventory = chest.getBlockInventory();
         ArrayList<ItemStack> itemStacks = new ArrayList<>();
         for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack == null) {
               continue;
            }
            if (!costMap.containsKey(itemStack.getType())) {
               itemStacks.add(itemStack);
               continue;
            }
            moneyEarned += costMap.get(itemStack.getType()) * itemStack.getAmount();
            itemsSold += itemStack.getAmount();
            itemStacks.add(new ItemStack(Material.AIR));
         }
         inventory.setContents(itemStacks.toArray(new ItemStack[itemStacks.size()]));
         chest.update();
         if (moneyEarned > 0) { wandUsed = true; }
         if (wandUsed) {
            SavageWands.deposit(player, itemsSold, moneyEarned, multiplier);
         }
         updateWand();

      });
   }



}
