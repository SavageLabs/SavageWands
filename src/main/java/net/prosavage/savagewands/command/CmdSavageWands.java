package net.prosavage.savagewands.command;

import net.prosavage.savagewands.SavageWands;
import net.prosavage.savagewands.util.Util;
import net.prosavage.savagewands.wands.impl.CondenseWand;
import net.prosavage.savagewands.wands.impl.SellWand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CmdSavageWands implements CommandExecutor {

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (!sender.hasPermission("sellwand.give")) {
         sender.sendMessage(Util.color("&cYou do not have permission to do this."));
         return true;
      }
      if (args.length == 0) {
         sendInfo(sender);
         return true;
      }
      if (args[0].equalsIgnoreCase("give")) {
         if (args.length < 3 || args.length > 5) {
            sender.sendMessage(Util.color("&cWrong arguments."));
            sender.sendMessage(Util.color("&cCorrect Format > &a/sw give %player% %wand% [%uses%] [%tier%]"));
            return true;
         }
         if (Bukkit.getPlayerExact(args[1]) == null) {
            sender.sendMessage(Util.color("&cPlayer does not exist"));
            return true;
         }
         if (args.length == 3) {
            if (args[2].toLowerCase().equalsIgnoreCase("sellwand")) {
               SavageWands.getInstance().getServer().getPlayerExact(args[1]).getInventory().addItem(SellWand.buildItem(null, null));
               return true;
            } else if ((args[2].toLowerCase().equalsIgnoreCase("craftwand")
                    || (args[2].toLowerCase().equalsIgnoreCase("condensewand")))) {
               SavageWands.getInstance().getServer().getPlayerExact(args[1]).getInventory().addItem(CondenseWand.buildItem(null));
               return true;
            }
         }

         if (args.length > 3) {
            Integer uses, tier;
            if (!isNumber(args[3])) {
               sender.sendMessage(Util.color("The uses argument has to be an integer."));
               uses = null;
            } else {
               uses = Integer.parseInt(args[3]);
            }
            if (args.length != 4) {
               if (!isNumber(args[4])) {
                  sender.sendMessage(Util.color("The tier argument has to be an integer."));
                  tier = null;
               } else {
                  tier = Integer.parseInt(args[4]);
               }
            } else {
               tier = null;
            }

            if (args[2].toLowerCase().equalsIgnoreCase("sellwand")) {
               SavageWands.getInstance().getServer().getPlayerExact(args[1]).getInventory().addItem(SellWand.buildItem(uses, tier));
               return true;
            } else if ((args[2].toLowerCase().equalsIgnoreCase("craftwand")
                    || (args[2].toLowerCase().equalsIgnoreCase("condensewand")))) {
               SavageWands.getInstance().getServer().getPlayerExact(args[1]).getInventory().addItem(CondenseWand.buildItem(uses));
               return true;
            }
         }
      }
      return true;
   }




   private void sendInfo(CommandSender sender) {
      sender.sendMessage(Util.color("&7(&c&l!&7) &cSavageWands By ProSavage."));
      sender.sendMessage(Util.color(" &c&l• &7Wand Types: SellWand, Craftwand (CondenseWand)."));
      sender.sendMessage(Util.color(" &c&l• &7Arguments with Square Brackets > '[%arg%]' around them are optional."));
      sender.sendMessage(Util.color(" &c&l• &7/sw give %player% %wand% [%uses%] [%tier%]"));
   }

   private boolean isNumber(String number) {
      try {
         Integer.parseInt(number + "");
      } catch (NumberFormatException ex) {
         return false;
      }
      return true;
   }


}
