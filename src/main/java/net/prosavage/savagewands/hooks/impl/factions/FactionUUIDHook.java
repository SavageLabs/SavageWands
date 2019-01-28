package net.prosavage.savagewands.hooks.impl.factions;


import com.massivecraft.factions.listeners.FactionsBlockListener;


import com.massivecraft.factions.listeners.FactionsPlayerListener;
import net.prosavage.savagewands.SavageWands;
import net.prosavage.savagewands.hooks.impl.FactionHook;
import net.prosavage.savagewands.util.Util;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class FactionUUIDHook extends FactionHook {

   @Override
   public boolean canBuild(Block block, Player player) {
      if (!FactionsPlayerListener.canPlayerUseBlock(player, block, true)) {
         player.sendMessage(Util.color(SavageWands.getInstance().getConfig().getString("messages.cant-use-here")));
         return false;
      }
      return true;
   }



}