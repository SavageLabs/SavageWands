package net.prosavage.savagewands.hooks.impl.factions;

import com.massivecraft.factions.engine.EnginePermBuild;

import com.massivecraft.massivecore.ps.PS;

import net.prosavage.savagewands.hooks.impl.FactionHook;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class FactionMCHook extends FactionHook {

   @Override
   public boolean canBuild(Block block, Player player) {
      return EnginePermBuild.canPlayerBuildAt(player, PS.valueOf(block), true);
   }



}
