package ru.hpp.fly;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import static ru.hpp.fly.fly.active;
import static ru.hpp.fly.fly.isFlying;

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
          if (active.containsKey(p))
          p.setHealth(20);
          if (fly.isFlying(p).intValue() == 1)
          event.setCancelled(true);
          
       
                 
      }
  }
  }