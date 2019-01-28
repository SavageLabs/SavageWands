package net.prosavage.savagewands.util;

public class Placeholder {


   String key;
   String value;

   public Placeholder(String key, String value) {
      this.key = key;
      this.value = value;
   }


   public String getValue() {
      return value;
   }

   public String getKey() {
      return key;
   }
}
