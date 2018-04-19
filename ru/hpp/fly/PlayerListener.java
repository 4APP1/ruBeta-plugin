package ru.hpp.fly;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
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
  public void onPlayerMove(org.bukkit.event.player.PlayerMoveEvent event)
  {
    Player player = event.getPlayer();
    
      if ((!active.containsKey(player)) || (!player.isSneaking())) return;
      player.setVelocity(player.getLocation().getDirection().multiply(((Double)active.get(player)).doubleValue()));
    
    if (fly.isFlying(player).intValue() == 1)
    {
      if (player.getLocation().getY() < 125.0D)
      {
        Vector dir = player.getLocation().getDirection();
        player.setVelocity(dir.multiply(fly.flightSpeed));
      }
    }
    
    if (fly.isFlying(player).intValue() == 2)
    {
      double M = fly.hoverMultiplier;
      
      if (player.isSneaking()) { M = fly.hoverBoostMultiplier;
      }
      double X = fly.getHoverLocation(player).getX();
      double Y = fly.getHoverLocation(player).getY();
      double Z = fly.getHoverLocation(player).getZ();
      
      Vector pVector = new Vector(X, Y, Z);
      Vector hVector = new Vector(0.0D, M, 0.0D);
      
      Vector hover = pVector.multiply(hVector);
      
      player.setVelocity(new Vector(0, 0, 0));
      player.setVelocity(hover);
    }
    
    if ((fly.flyingEatsFeathers) && (fly.isFlying(player).intValue() > 0))
    {
      Integer fAmount = fly.getFeatherPoints(player);
      
      fAmount = Integer.valueOf(fAmount.intValue() - 1);
      
      fly.setFeatherPoints(player, fAmount.intValue());
      
      if (fAmount.intValue() < 1)
      {
        try
        {
          if (((ItemStack)player.getInventory().all(Material.FEATHER).get(Integer.valueOf(0))).getAmount() < 1)
          {
            System.out.println("out of feathers (OPM)");
            fly.setFlying(player, Integer.valueOf(0));
            player.sendMessage("You are no longer flying.");
          }
          else
          {
            ItemStack F = new ItemStack(Material.FEATHER, 1);
            player.getInventory().removeItem(new ItemStack[] { F });
            
            if (((ItemStack)player.getInventory().all(Material.FEATHER).get(Integer.valueOf(0))).getAmount() < 1)
            {
              fly.setFlying(player, Integer.valueOf(0));
              player.sendMessage("You are no longer flying.");
            }
            else
            {
              fly.setFeatherPoints(player, fly.defaultFeatherAmount.intValue());
            }
          }
          
          fly.setFeatherPoints(player, fly.defaultFeatherAmount.intValue());
        }
        catch (Exception e)
        {
          fly.setFlying(player, Integer.valueOf(0));
          player.sendMessage("You are no longer flying.");
        }
      }
    }
  }
    

  
  @Override
  public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
    Player p = event.getPlayer();
    event.setJoinMessage(ChatColor.RED + "[RuBeta] " + ChatColor.AQUA + "Player " + p.getName() + " came for Nostalgia!");

      fly.cooldown.put(p, true);
  }

  @Override
  public void onPlayerInteract(PlayerInteractEvent event)
  {
    Player player = event.getPlayer();
    
    if (fly.isAllowed(player))
    {
      if (player.getItemInHand().getType() == Material.FEATHER)
      {
        if ((event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_AIR) || (event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK))
        {

          if (fly.isFlying(player).intValue() == 0)
          {

            player.sendMessage("You are now flying.");
            
            if (!fly.featherPoints.containsKey(player)) {
              fly.setFeatherPoints(player, fly.defaultFeatherAmount.intValue());
            }
            fly.setFlying(player, Integer.valueOf(1));
          }
          else if (fly.isFlying(player).intValue() == 1)
          {
            fly.setHoverLocation(player);
            player.setVelocity(new Vector(0, 0, 0));
            player.sendMessage("You are now hovering.");
            fly.setFlying(player, Integer.valueOf(2));
          }
          else
          {
            player.sendMessage("You are no longer flying.");
            fly.setFlying(player, Integer.valueOf(0));
          }
          
      
               
           
          event.setCancelled(true);
        }
      }
    }
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