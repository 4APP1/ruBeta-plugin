package ru.hpp.comp;

import org.bukkit.entity.Pig;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;


public class EntityListener extends org.bukkit.event.entity.EntityListener
{
    
  public static main plugin;
  
  public EntityListener(main instance)
  {
    plugin = instance;

      
  
  }
  
  @Override
    public void onEntityDeath(EntityDeathEvent event)
    {
    if ((event.getEntity() instanceof Pig))
    {
      if (((Pig)event.getEntity()).hasSaddle())
      {
        event.getDrops().add(new ItemStack(329, 1));
      }
    }
  }
}
  