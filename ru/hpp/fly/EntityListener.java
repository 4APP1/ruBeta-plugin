package ru.hpp.fly;

import java.util.ArrayList;
import java.util.Random;
import org.bukkit.DyeColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import static ru.hpp.fly.fly.active;


public class EntityListener extends org.bukkit.event.entity.EntityListener
{
    
 public static ArrayList<Sheep> spawnedSheep = new ArrayList<Sheep>();

    
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
  