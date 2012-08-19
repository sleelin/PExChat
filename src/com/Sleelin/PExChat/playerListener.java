package com.Sleelin.PExChat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class playerListener
  implements Listener
{
  PExChat pexchat;

  playerListener(PExChat ichat)
  {
    this.pexchat = ichat;
  }
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onPlayerChat(AsyncPlayerChatEvent event) {
    if (this.pexchat.permissions == null) return;
    if (event.isCancelled()) return;
    Player p = event.getPlayer();
    String msg = event.getMessage();

    event.setFormat(this.pexchat.parseChat(p, msg) + " ");
  }

  @EventHandler(priority=EventPriority.HIGHEST)
  public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
    if (this.pexchat.permissions == null) return;
    if (event.isCancelled()) return;
    Player p = event.getPlayer();
    String message = event.getMessage();

    if (message.toLowerCase().startsWith("/me ")) {
      String s = message.substring(message.indexOf(" ")).trim();
      String formatted = this.pexchat.parseChat(p, s, this.pexchat.meFormat);

      PExChatMeEvent meEvent = new PExChatMeEvent(p, formatted);
      Bukkit.getServer().getPluginManager().callEvent(meEvent);
      Bukkit.getServer().broadcastMessage(formatted);

      event.setCancelled(true);
    }
  }
}