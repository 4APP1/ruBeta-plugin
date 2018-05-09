
package ru.hpp.comp;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.inventory.ItemStack;


public class bListener extends BlockListener{
    
      
   @Override
   public void onBlockBreak(BlockBreakEvent e) {
      Block block = e.getBlock();
      
      if (e.getBlock().getType() == Material.ICE) {
         
          block.setType(Material.AIR);
          block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.ICE, 1));
          e.setCancelled(true);
      }
      
      if (e.getBlock().getType() == Material.GLASS) {
          
          block.setType(Material.AIR);
          block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GLASS, 1));
          e.setCancelled(true);
      }
    }
    
}
