package ru.hpp.fly;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityListener extends org.bukkit.event.entity.EntityListener
{
    
  public static fly plugin;
  
  public EntityListener(fly instance)
  {
    plugin = instance;

      
  
  }
  @Override
  public void onEntityDamage(EntityDamageEvent event)
  {
      
    if ((event.getCause().equals(org.bukkit.event.entity.EntityDamageEvent.DamageCause.FALL)) && ((event.getEntity() instanceof Player))) {
      try
      {
          
        Player player = (Player)event.getEntity();
        
        if (fly.isFlying(player).intValue() >= 0) event.setCancelled(true);

      }
      catch (Exception localException) {}
    }

    }
}
  
 
