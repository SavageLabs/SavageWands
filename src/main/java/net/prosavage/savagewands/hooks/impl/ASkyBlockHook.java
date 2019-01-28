package net.prosavage.savagewands.hooks.impl;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import net.prosavage.savagewands.SavageWands;
import net.prosavage.savagewands.hooks.PluginHook;
import org.bukkit.Location;
import org.bukkit.entity.Player;



public class ASkyBlockHook implements PluginHook<ASkyBlockHook> {

   ASkyBlockAPI aSkyBlockAPI;


   @Override
   public ASkyBlockHook setup() {
      if (SavageWands.getInstance().getServer().getPluginManager().getPlugin("ASkyBlock") != null) {
         aSkyBlockAPI = ASkyBlockAPI.getInstance();
      }
      return this;
   }


   public boolean canDoAction(Location location, Player player) {
      if (aSkyBlockAPI == null) {
         return true;
      }
      return aSkyBlockAPI.getIslandAt(location).getMembers().contains(player.getUniqueId());
   }


   @Override
   public String getName() {
      return "ASkyblock";
   }











}
