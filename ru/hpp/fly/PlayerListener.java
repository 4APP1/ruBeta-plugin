package ru.hpp.fly;

import static java.lang.Math.random;
import static java.lang.StrictMath.random;
import java.util.Random;
import static org.bukkit.Bukkit.getServer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import static ru.hpp.fly.fly.active;

public class PlayerListener extends org.bukkit.event.player.PlayerListener
{
  public fly plugin;
  public org.bukkit.Server server;
  
  public PlayerListener(fly instance)
  {
    plugin = instance;
    server = plugin.getServer();
  }
  
  @Override
  public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
    Player p = event.getPlayer();
    event.setJoinMessage(ChatColor.RED + "[RuBeta] " + ChatColor.AQUA + "Player " + p.getName() + " came for Nostalgia!");

  }


  

  @Override
  public void onPlayerQuit(org.bukkit.event.player.PlayerQuitEvent event)
  {
    Player p = event.getPlayer();
    event.setQuitMessage(ChatColor.RED + "[RuBeta] " + ChatColor.AQUA + p.getName() + " was bored of Nostalgia and quit!");
    
    if (fly.flyingPlayers.containsKey(event.getPlayer()))
    {
      fly.flyingPlayers.remove(event.getPlayer());
      System.out.println("[fly] Player quit; removed from list.");
    }
  }
  @Override
  public void onPlayerRespawn(PlayerRespawnEvent event)
  {
      active.remove(event.getPlayer());
    
  }
  
}