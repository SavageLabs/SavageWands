package net.prosavage.savagewands.listener;

import com.massivecraft.factions.SavageFactions;
import net.prosavage.savagewands.SavageWands;
import net.prosavage.savagewands.hooks.HookManager;
import net.prosavage.savagewands.hooks.PluginHook;
import net.prosavage.savagewands.hooks.impl.ASkyBlockHook;
import net.prosavage.savagewands.util.Util;
import net.prosavage.savagewands.wands.Wand;
import net.prosavage.savagewands.wands.impl.CondenseWand;
import net.prosavage.savagewands.wands.impl.SellWand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WandListener implements Listener {


   @EventHandler
   public void onWandUse(PlayerInteractEvent event) {
      if (event.getClickedBlock() == null
              || event.getClickedBlock().getType() != Material.CHEST
              || event.getItem() == null
              || !Wand.isWand(event.getItem())) {
         return;
      }





      Player player = event.getPlayer();
      ItemStack eventItem = event.getItem();
      Chest chest = (Chest) event.getClickedBlock().getState();
      if (!((ASkyBlockHook) HookManager.getPluginMap().get("ASkyBlock")).canDoAction(event.getClickedBlock().getLocation(), event.getPlayer())) {
         player.sendMessage(Util.color(SavageWands.getInstance().getConfig().getString("messages.cannot-use-here")));
         return;
      }
      event.setCancelled(true);
      if (Util.isEmpty(chest.getBlockInventory())) {
         player.sendMessage(Util.color(SavageWands.getInstance().getConfig().getString("messages.chest-empty")));
         return;
      }




      if (SellWand.isSellWand(eventItem)) {
         SellWand sellWand = new SellWand(eventItem, player, chest);
         sellWand.takeWand();
         sellWand.run();
      } else if (CondenseWand.isCondenseWand(eventItem)) {
         CondenseWand condenseWand = new CondenseWand(eventItem, player, chest);
         condenseWand.takeWand();
         condenseWand.run();
      }

   }




}
