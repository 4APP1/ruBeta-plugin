package ru.hpp.comp;


import java.util.HashMap;
import net.minecraft.server.Packet53BlockChange;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class main extends JavaPlugin

{
  public HashMap<String, Long> cooldowns = new HashMap<String, Long>();
  public static final HashMap<Player, Double> active = new HashMap();  
  
  public static main plugin;
  public static Server server;

  @Override
  public void onDisable()
  {}
  
  @Override
  public void onEnable()
  {
    plugin = this;
    server = plugin.getServer();
    Event.Priority priority = Event.Priority.Monitor;
    
    
    PluginManager pm = server.getPluginManager();
    
    PlayerListener pListener = new PlayerListener(this);
    EntityListener eListener = new EntityListener(this);
    BlockListener blockListener = new bListener();

    
    pm.registerEvent(Event.Type.PLAYER_MOVE, pListener, priority, plugin);
    pm.registerEvent(Event.Type.PLAYER_INTERACT, pListener, priority, plugin);
    pm.registerEvent(Event.Type.ENTITY_DAMAGE, eListener, Event.Priority.Highest, plugin);
    pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Event.Priority.Normal, plugin);
    
    System.out.println("[RuBeta] Плагин работает!");
  }
    private final long serverStart = System.currentTimeMillis();
   
   
    
  @Override
  public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String commandLabel, String[] args){

    if (commandLabel.equalsIgnoreCase("colours")) {
      Player p = (Player)sender;
      //Создание игрока
      p.sendMessage("Все цветовые коды майнкрафта");
      p.sendMessage(ChatColor.BLACK + "&0, " + ChatColor.DARK_BLUE + "&1, " + ChatColor.DARK_GREEN + "&2, " + ChatColor.DARK_AQUA + "&3, " + ChatColor.DARK_RED + "&4, " + ChatColor.DARK_PURPLE + "&5, " + ChatColor.GOLD + "&6, " + ChatColor.GRAY + "&7, " + ChatColor.DARK_GRAY + "&8, " + ChatColor.BLUE + "&9, " + ChatColor.GREEN + "&a, " + ChatColor.AQUA + "&b, " + ChatColor.RED + "&c, " + ChatColor.LIGHT_PURPLE + "&d, " + ChatColor.YELLOW + "&e, " + ChatColor.WHITE + "&f");
      return true;
    }
    
    if (commandLabel.equalsIgnoreCase("pl")) {
        Player p = (Player)sender;
      
        p.kickPlayer(ChatColor.RED + "Даже не пытайся.");
        
        return true;      
    }

    if (commandLabel.equalsIgnoreCase("crash")) {
        Player p = (Player)sender;
        if (p.isOp()){

        Player player = Bukkit.getServer().getPlayer(args[0]);
                
        if (!(player instanceof Player)) return false;
        
        Packet53BlockChange deathPacket = new Packet53BlockChange();
        
                deathPacket.a = (int) player.getLocation().getX();
		deathPacket.b = (int) player.getLocation().getY();
		deathPacket.c = (int) player.getLocation().getZ();
		deathPacket.data = 0;
		deathPacket.material = 900; //Неправильный код блока

        
        ((CraftPlayer) player).getHandle().netServerHandler.sendPacket(deathPacket);
            return true;
        }
     
        else{
            p.sendMessage(ChatColor.RED + "[RuBeta] " + ChatColor.AQUA + "Нет, ты не админ.");
            return true;
        }
        
    }
       if (commandLabel.equalsIgnoreCase("uptime")) {
           Player p = (Player)sender;
           long diff = System.currentTimeMillis() - serverStart;
        String msg = " " + (int)(diff / 86400000L) + " days " + (int)(diff / 3600000L % 24L) + " hours " + (int)(diff / 60000L % 60L) + " mins " + (int)(diff / 1000L % 60L) + " seconds";
        p.sendMessage(ChatColor.RED + "[RuBeta]" + ChatColor.AQUA + msg);
        return true;
       }
       
       if (commandLabel.equalsIgnoreCase("ptime")) {
      Player p = (Player)sender;
      if (args.length == 0) {
        p.sendMessage(ChatColor.BLUE + "}---- PlayerTime помощь ----{");
        p.sendMessage(ChatColor.GREEN + "/ptime day" + ChatColor.BLUE + " - Ставит время на день.");
        p.sendMessage(ChatColor.GREEN + "/ptime night" + ChatColor.BLUE+ " - Ставит время на ночь.");
        p.sendMessage(ChatColor.GREEN + "/ptime morning" + ChatColor.BLUE + " - Ставит время на утро.");
        p.sendMessage(ChatColor.GREEN + "/ptime reset" + ChatColor.BLUE + " - Сбрасывает время на серверное.");
        return true;
      }
      if (args.length == 1) {
        if (args[0].equalsIgnoreCase("day")) {
          p.setPlayerTime(6000L, false);
          p.sendMessage(ChatColor.GREEN + "[RuBeta] " + ChatColor.RED + "Ты установил у себя день.");
          return true;
        }
        if (args[0].equalsIgnoreCase("night")) {
          p.setPlayerTime(18000L, false);
          p.sendMessage(ChatColor.GREEN + "[RuBeta] " + ChatColor.RED + "Ты установил у себя ночь.");
          return true;
        }
        if (args[0].equalsIgnoreCase("morning")) {
          p.setPlayerTime(0L, false);
          p.sendMessage(ChatColor.GREEN + "[RuBeta] " + ChatColor.RED + "Ты установил у себя утро.");
          return true;
        }
        if (args[0].equalsIgnoreCase("dawn")) {
          p.setPlayerTime(12000L, false);
          p.sendMessage(ChatColor.GREEN + "[RuBeta] " + ChatColor.RED + "Ты установил у себя рассвет.");
          return true;
        }
        if (args[0].equalsIgnoreCase("reset")) {
          p.resetPlayerTime();
          p.sendMessage(ChatColor.GREEN + "[RuBeta] " + ChatColor.RED + "Ты установил у себя серверное время.");
          return true;
        }
      }
    }
      return false;
  }
}