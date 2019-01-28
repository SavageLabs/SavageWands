package net.prosavage.savagewands.hooks.impl;

import com.massivecraft.factions.SavageFactions;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import net.prosavage.savagewands.SavageWands;
import net.prosavage.savagewands.hooks.PluginHook;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class WorldGuardHook implements PluginHook<WorldGuardHook> {

   private WorldGuardPlugin worldGuardPlugin;

   @Override
   public WorldGuardHook setup() {
      if (SavageWands.getInstance().getServer().getPluginManager().getPlugin("WorldGuard") == null) {
         this.worldGuardPlugin = null;
         return this;
      }
      this.worldGuardPlugin = WorldGuardPlugin.inst();
      return this;
   }

   public boolean canBuild(Player player, Block block) {
      if (worldGuardPlugin == null) {
         return true;
      }
      return worldGuardPlugin.canBuild(player, block);
   }

   @Override
   public String getName() {
      return "WorldGuard";
   }

}
