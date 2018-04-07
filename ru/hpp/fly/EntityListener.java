package ru.hpp.fly;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import static ru.hpp.fly.fly.active;

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
    
        if ((event.getEntity() instanceof Player)) {
          Player p = (Player)event.getEntity();
          if (active.containsKey(p)) event.setCancelled(true);
        }
      }
       
                 
      
  }
  