package net.prosavage.savagewands.hooks;

public interface PluginHook<T> {

   T setup();

   String getName();

}
