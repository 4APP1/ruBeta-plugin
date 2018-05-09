package ru.hpp.comp;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener extends org.bukkit.event.player.PlayerListener
{
  public main plugin;
  public org.bukkit.Server server;
  
  public PlayerListener(main instance)
  {
    plugin = instance;
    server = plugin.getServer();
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
}