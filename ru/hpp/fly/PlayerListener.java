package ru.hpp.fly;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
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
  public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
    Player p = event.getPlayer();
    
    event.setJoinMessage(ChatColor.RED + "[RuBeta] " + ChatColor.AQUA + "Player " + p.getName() + " came for Nostalgia!");

  }


  @Override
    	public void onPlayerInteract(PlayerInteractEvent event){            
                Player p = event.getPlayer();
                
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
                    if(event.getPlayer().getInventory().getItemInHand().getType() == Material.BREAD || event.getPlayer().getInventory().getItemInHand().getType() == Material.COOKED_FISH || event.getPlayer().getInventory().getItemInHand().getType() == Material.COOKIE || event.getPlayer().getInventory().getItemInHand().getType() == Material.CAKE || (event.getPlayer().getInventory().getItemInHand().getType() == Material.GRILLED_PORK || event.getPlayer().getInventory().getItemInHand().getType() == Material.RAW_FISH || event.getPlayer().getInventory().getItemInHand().getType() == Material.APPLE || event.getPlayer().getInventory().getItemInHand().getType() == Material.PORK)){
                       if (p.getHealth() == 20) {
                           p.setHealth(p.getHealth());
                           p.sendMessage(ChatColor.RED + "[RuBeta]" + ChatColor.AQUA + " Ты не можешь есть, когда у тебя полное здоровье!");
                           event.setCancelled(true);
                           return;
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
  
  @Override
  public void onPlayerMove(PlayerMoveEvent event){
		if (event.getTo().getBlockX() == event.getFrom().getBlockX() && event.getTo().getBlockY() == event.getFrom().getBlockY() && event.getTo().getBlockZ() == event.getFrom().getBlockZ()) return;
		for (Entity e : event.getPlayer().getNearbyEntities(0.1D, 0.1D, 0.1D)) {
            if ((e != null) && ((e instanceof Item))) {
            	if (e.getPassenger() == event.getPlayer())
            		e.remove();
            }
        }
     }
}