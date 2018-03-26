package ru.hpp.fly;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class TimerTask implements Runnable
{
  fly plugin = null;
  org.bukkit.Server server = null;
  
  public TimerTask(fly instance)
  {
    plugin = instance;
    server = plugin.getServer();
  }
  

  @Override
  public void run()
  {
    double M = fly.hoverMultiplier;
    
    World world = null;
    Player player = null;
    
    for (int W = 0; W < server.getWorlds().size(); W++)
    {
      world = (World)server.getWorlds().get(W);
      
      for (int P = 0; P < world.getPlayers().size(); P++)
      {
        player = (Player)world.getPlayers().get(P);
        
        if (fly.isFlying(player).intValue() == 2)
        {
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
      }
    }
  }
}