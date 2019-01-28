package net.prosavage.savagewands.hooks.impl;

import net.milkbowl.vault.economy.Economy;

import net.prosavage.savagewands.SavageWands;
import net.prosavage.savagewands.hooks.PluginHook;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook implements PluginHook<VaultHook> {

   @Override
   public VaultHook setup() {
      RegisteredServiceProvider<Economy> rsp = SavageWands.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
      if (rsp == null) {
         return null;
      }
     SavageWands.econ = rsp.getProvider();
      return this;
   }

   @Override
   public String getName() {
      return "Vault";
   }

}
