package net.prosavage.savagewands;

import net.milkbowl.vault.economy.Economy;
import net.prosavage.savagewands.command.CmdSavageWands;
import net.prosavage.savagewands.hooks.HookManager;
import net.prosavage.savagewands.hooks.impl.ASkyBlockHook;
import net.prosavage.savagewands.hooks.impl.FactionHook;
import net.prosavage.savagewands.hooks.impl.VaultHook;
import net.prosavage.savagewands.hooks.impl.WorldGuardHook;
import net.prosavage.savagewands.listener.WandListener;
import net.prosavage.savagewands.util.CraftKey;
import net.prosavage.savagewands.util.Util;

import net.prosavage.savagewands.util.nbt.MultiversionMaterials;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public final class SavageWands extends JavaPlugin {

   public static Logger logger;
   public static Economy econ;
   private static SavageWands instance;
   private static HashMap<Material, Double> costMap = new HashMap<>();
   private static HashMap<Material, CraftKey> craftKeys = new HashMap<>();



   @Override
   public void onEnable() {
      logger = getLogger();
      instance = this;
      getClass().isInstance(new HookManager(Arrays.asList(new FactionHook(), new VaultHook(), new WorldGuardHook(), new ASkyBlockHook())));
      getServer().getPluginManager().registerEvents(new WandListener(), this);
      getCommand("savagewands").setExecutor(new CmdSavageWands());
      if (!checkIfConfigExists()) {
         logger.info("Configuration file was not found!");
         logger.info("Creating config.yml...");
         saveResource("config.yml", true);
      } else {
         logger.info("Configuration File Found!");
      }
      populateCostMap();
      populateCraftKeys();
   }

   @Override
   public void onDisable() {
      // Plugin shutdown logic
   }


   private void populateCraftKeys() {
      ConfigurationSection craftConfig = getConfig().getConfigurationSection("craft");
      for (String key : craftConfig.getKeys(false)) {
         Material initial = MultiversionMaterials.valueOf(key).parseMaterial();
         int amount = getConfig().getInt("craft." + key + ".amount");
         Material result = MultiversionMaterials.valueOf(getConfig().getString("craft." + key + ".result")).parseMaterial();
         craftKeys.put(initial, new CraftKey(initial, amount, result));
      }
   }

   private void populateCostMap() {
      ConfigurationSection materialsConfig = getConfig().getConfigurationSection("materials");
      for (String key : materialsConfig.getKeys(true)) {
         Material material = MultiversionMaterials.valueOf(key).parseMaterial();
         if (material == null) {
            continue;
         }
         costMap.put(material, getConfig().getDouble("materials." + key));
      }
   }


   public static SavageWands getInstance() {
      return instance;
   }

   public static HashMap<Material, Double> getCostMap() {
      return costMap;
   }

   public static HashMap<Material, CraftKey> getCraftKeys() {
      return craftKeys;
   }



   public static void deposit(Player player,int items, double amount, double multiplier) {
      amount = amount * multiplier;
      econ.depositPlayer(player, amount);
      player.sendMessage(Util.color(getInstance().getConfig().getString("messages.deposit-money")
              .replace("{amount}", amount + "")
              .replace("{items}", items + "")
              .replace("{multiplier}", multiplier + "")));
   }

   private boolean checkIfConfigExists() {
      File configFile = new File(getDataFolder(), "config.yml");
      return configFile.exists();
   }
}
