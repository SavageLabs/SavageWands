package net.prosavage.savagewands.hooks;



import net.prosavage.savagewands.SavageWands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HookManager {

   private static Map<String, PluginHook<?>> pluginMap = new HashMap<>();

   public HookManager(List<PluginHook<?>> list) {
      for (PluginHook<?> hook : list) {
         pluginMap.put(hook.getName(), (PluginHook<?>) hook.setup());
         if (SavageWands.getInstance().getServer().getPluginManager().getPlugin(hook.getName()) != null) {
            SavageWands.logger.fine("successfully hooked ");
         } else {
            SavageWands.logger.fine("could not hook " + hook.getName());
         }
      }
   }

   public static Map<String, PluginHook<?>> getPluginMap() {
      return pluginMap;
   }

}
