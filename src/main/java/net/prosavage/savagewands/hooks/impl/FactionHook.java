package net.prosavage.savagewands.hooks.impl;


import net.prosavage.savagewands.SavageWands;
import net.prosavage.savagewands.exceptions.NotImplementedException;
import net.prosavage.savagewands.hooks.PluginHook;
import net.prosavage.savagewands.hooks.impl.factions.FactionMCHook;
import net.prosavage.savagewands.hooks.impl.factions.FactionUUIDHook;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public class FactionHook implements PluginHook<FactionHook> {

   @Override
   public FactionHook setup() {
      if (SavageWands.getInstance().getServer().getPluginManager().getPlugin(getName()) == null) {
         SavageWands.logger.fine("Factions could not be found");
         return null;
      }
      List<String> authors = SavageWands.getInstance().getServer().getPluginManager().getPlugin(getName()).getDescription().getAuthors();
      if (!authors.contains("drtshock") && !authors.contains("Benzimmer")) {
         SavageWands.logger.fine("Server Factions type has been set to (MassiveCore)");
         return new FactionMCHook();
      } else {
         SavageWands.logger.fine("Server Factions type has been set to (FactionsUUID/SavageFactions/FactionsUltimate)");
         return new FactionUUIDHook();
      }
   }

   public boolean canBuild(Block block, Player player) {
      throw new NotImplementedException("Factions does not exist!");
   }

   public boolean hasNearbyPlayer(Player player) {
      throw new NotImplementedException("Factions does not exist!");
   }

   @Override
   public String getName() {
      return "Factions";
   }

}
