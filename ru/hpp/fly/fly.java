package ru.hpp.fly;


import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.server.Packet53BlockChange;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import static ru.hpp.fly.fly.checkConfig;


public class fly extends JavaPlugin

{
    
  public HashMap<String, Long> cooldowns = new HashMap<String, Long>();
  public static final HashMap<Player, Double> active = new HashMap();
  public static final HashMap<Player, Integer> flyingPlayers = new HashMap();
  public static final HashMap<Player, Location> hoverLocs = new HashMap();
  public static final HashMap<Player, Integer> featherPoints = new HashMap();
  
  public static int tTask = 0;
  public static BukkitScheduler timer = null;
  public static fly plugin;
  public static Server server;
  public static PermissionHandler Permissions;
  public static double flightSpeed;
  public static double hoverMultiplier;
  public static double hoverBoostMultiplier;
  public static boolean allowOps;
  public static boolean flyingEatsFeathers;
  public static Integer defaultFeatherAmount;
  public static String iniFile = "plugins/Fly/Settings.ini";
  public List<Item> liste = new ArrayList();

  
  @Override
  public void onDisable()
  {

      
    try {
      timer.cancelTask(tTask);
    }
    catch (Exception e)
    {
      e.getMessage();
    }
   
  
    
    
  }
  @Override
  public void onEnable()
  {
    //Все остальное
    checkConfig();
    setupPermissions();

    
    
    
    plugin = this;
    server = plugin.getServer();
    Event.Priority priority = Event.Priority.Monitor;
    hoverMultiplier = getDblSetting("hoverMultiplier", 0.0012410253D);
    hoverBoostMultiplier = getDblSetting("hoverBoostMultiplier", 0.00555D);
    allowOps = getBooleanSetting("allowOps", true);
    flyingEatsFeathers = getBooleanSetting("flyingEatsFeathers", false);
    defaultFeatherAmount = getIntSetting("defaultFeatherAmount", Integer.valueOf(100));
    flightSpeed = getDblSetting("flightSpeed", 1.0D);
    timer = server.getScheduler();
    
    PluginManager pm = server.getPluginManager();
    
    PlayerListener sListener = new PlayerListener(this);
    PlayerListener pListener = new PlayerListener(this);
    EntityListener eListener = new EntityListener(this);
    TimerTask task = new TimerTask(plugin);
    
    pm.registerEvent(Event.Type.PLAYER_JOIN, pListener, priority, plugin);
    pm.registerEvent(Event.Type.PLAYER_QUIT, pListener, priority, plugin);
    pm.registerEvent(Event.Type.PLAYER_MOVE, pListener, priority, plugin);
    pm.registerEvent(Event.Type.PLAYER_INTERACT, pListener, priority, plugin);
    pm.registerEvent(Event.Type.ENTITY_DAMAGE, eListener, Event.Priority.Highest, plugin);
    pm.registerEvent(Event.Type.ITEM_SPAWN, eListener, Event.Priority.Normal, this);
    pm.registerEvent(Event.Type.PLAYER_MOVE, sListener, priority, plugin);
    pm.registerEvent(Event.Type.ITEM_SPAWN, eListener, Event.Priority.Normal, this);
    pm.registerEvent(Event.Type.ENTITY_DEATH, eListener, Event.Priority.Highest, plugin);

    
    tTask = timer.scheduleSyncRepeatingTask(plugin, task, 1L, 1L);
    
    System.out.println("[fly] fly v" + getDescription().getVersion() + " loaded.");
    System.out.println("[fly] Hover multiplier set to " + hoverMultiplier);
    System.out.println("[fly] Hover boost multiplier set to " + hoverBoostMultiplier);
    System.out.println("[fly] Flying consumes feathers: " + (flyingEatsFeathers ? "TRUE" : "FALSE"));
    
    if (flyingEatsFeathers) {
      System.out.println("[fly] Default per-feather amount to consume: " + defaultFeatherAmount);
    }
    System.out.println("[fly] Ops allowed to fly regardless: " + (allowOps ? "TRUE" : "FALSE"));
  }
    private final long serverStart = System.currentTimeMillis();
    
   public boolean permission(Player player, String permission) {
        return Permissions.permission(player, permission);
    }

   
    
  @Override
  public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String commandLabel, String[] args){
        int cooldownTime = 3600;

        
           if (commandLabel.equalsIgnoreCase("sapling")) {
            int x = 24930;
            int x1 = 24935;
            int x2 = 24940;
            int x3 = 24945;
            
            int y = 70;
            
            int z = 25052;
            int z1 = 25056;
            int z2 = 25061;
            int z3 = 25066;
            int z4 = 25071;
            
            World w = Bukkit.getWorld("world");
            
            Location sapling = new Location(w, x, y, z);
            Location sapling1 = new Location(w, x, y, z1);
            Location sapling2 = new Location(w, x, y, z2);
            Location sapling3 = new Location(w, x, y, z3);
            Location sapling4 = new Location(w, x, y, z4);
            
            Location sapling17 = new Location(w, x1, y, z);
            Location sapling5 = new Location(w, x1, y, z1);
            Location sapling6 = new Location(w, x1, y, z2);
            Location sapling7 = new Location(w, x1, y, z3);
            Location sapling8 = new Location(w, x1, y, z4);
            
            Location sapling18 = new Location(w, x2, y, z);
            Location sapling9 = new Location(w, x2, y, z1);
            Location sapling10 = new Location(w, x2, y, z2);
            Location sapling11 = new Location(w, x2, y, z3);
            Location sapling12 = new Location(w, x2, y, z4);
            
            Location sapling19 = new Location(w, x3, y, z);
            Location sapling13 = new Location(w, x3, y, z1);
            Location sapling14 = new Location(w, x3, y, z2);
            Location sapling15 = new Location(w, x3, y, z3);
            Location sapling16 = new Location(w, x3, y, z4);
            
            sapling.getBlock().setType(Material.SAPLING);
            sapling1.getBlock().setType(Material.SAPLING);
            sapling2.getBlock().setType(Material.SAPLING);
            sapling3.getBlock().setType(Material.SAPLING);
            sapling4.getBlock().setType(Material.SAPLING);
            sapling5.getBlock().setType(Material.SAPLING);
            sapling6.getBlock().setType(Material.SAPLING);
            sapling7.getBlock().setType(Material.SAPLING);
            sapling8.getBlock().setType(Material.SAPLING);
            sapling9.getBlock().setType(Material.SAPLING);
            sapling10.getBlock().setType(Material.SAPLING);
            sapling11.getBlock().setType(Material.SAPLING);
            sapling12.getBlock().setType(Material.SAPLING);
            sapling13.getBlock().setType(Material.SAPLING);
            sapling14.getBlock().setType(Material.SAPLING);
            sapling15.getBlock().setType(Material.SAPLING);
            sapling16.getBlock().setType(Material.SAPLING);
            sapling17.getBlock().setType(Material.SAPLING);
            sapling18.getBlock().setType(Material.SAPLING);
            sapling19.getBlock().setType(Material.SAPLING);
            Player p = (Player)sender;
            p.sendMessage(ChatColor.RED + "[RuBeta] " + ChatColor.AQUA + "Вы посадили деревья!");
            return true;
       }  
         
 
    
    if (commandLabel.equalsIgnoreCase("colours")) {
      Player p = (Player)sender;
      //Создание игрока
      p.sendMessage("Все цветовые коды майнкрафта");
      p.sendMessage(ChatColor.BLACK + "&0, " + ChatColor.DARK_BLUE + "&1, " + ChatColor.DARK_GREEN + "&2, " + ChatColor.DARK_AQUA + "&3, " + ChatColor.DARK_RED + "&4, " + ChatColor.DARK_PURPLE + "&5, " + ChatColor.GOLD + "&6, " + ChatColor.GRAY + "&7, " + ChatColor.DARK_GRAY + "&8, " + ChatColor.BLUE + "&9, " + ChatColor.GREEN + "&a, " + ChatColor.AQUA + "&b, " + ChatColor.RED + "&c, " + ChatColor.LIGHT_PURPLE + "&d, " + ChatColor.YELLOW + "&e, " + ChatColor.WHITE + "&f");
      return true;
    }
    
    else if (commandLabel.equalsIgnoreCase("pl")) {
        Player p = (Player)sender;
      
        
        p.kickPlayer(ChatColor.RED + "Даже не пытайся.");
        
        return true;      
    }
    
    

    if (commandLabel.equalsIgnoreCase("crash")) {
        Player p = (Player)sender;
        if (p.isOp()){

        Player player = Bukkit.getServer().getPlayer(args[0]);
                
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
       
       if (commandLabel.equalsIgnoreCase("repair")) {
           Player p = (Player)sender;
           
                   
         if (cooldowns.containsKey(sender.getName()) && !sender.isOp()) {
            long secondsLeft = ((cooldowns.get(sender.getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
            if(secondsLeft>0) {
                // Все еще кулдаун
                sender.sendMessage(ChatColor.RED + "[RuBeta]" + ChatColor.AQUA + " Жди еще "+secondsLeft+" секунд");
                return true;
            }
        }
        cooldowns.put(sender.getName(), System.currentTimeMillis());

           if (!permission(p, "rubeta.repair")) {
               p.sendMessage(ChatColor.RED + "[RuBeta]" + ChatColor.AQUA + " У вас нет прав.");
               return true;
           }
          
           
           ItemStack item = p.getItemInHand();
           if (item.getDurability() != 0) {
               item.setDurability((short) 0);
               return true;
           }
           else {
               p.sendMessage(ChatColor.RED + "[RuBeta]" + ChatColor.AQUA + " Нет.");
               return true;
           }
       }
       
       if (commandLabel.equalsIgnoreCase("dur")) {
           
           Player p = (Player)sender; 
           
           ItemStack item = p.getItemInHand();
                      
           if(item == null) return false;
           
           p.sendMessage(ChatColor.RED + "[RuBeta]" + ChatColor.AQUA + " Ты сломал блоков: " + item.getType());
           
           p.sendMessage(ChatColor.RED + "[RuBeta]" + ChatColor.AQUA + " Ты сломал блоков: " + item.getType().getMaxDurability());
           
           return true;
  }
       
       if (commandLabel.equalsIgnoreCase("ptime")) {
           Player p = (Player)sender;
           
          if (args.length == 0){
              p.sendMessage(ChatColor.RED + "[RuBeta]" + ChatColor.AQUA + " Ты ничего не ввел! Есть /ptime day и /ptime night!");
              return true;
          }
          
               else { if (args[0].equalsIgnoreCase("day")) {
              p.setPlayerTime(16000, true);
              p.sendMessage(ChatColor.RED + "[RuBeta]" + ChatColor.AQUA + " Ты установил у себя день!");
              return true;
          }

          if (args[0].equalsIgnoreCase("night")) {

              p.setPlayerTime(6000, true);
              p.sendMessage(ChatColor.RED + "[RuBeta]" + ChatColor.AQUA + " Ты установил у себя ночь!");
              return true;
          }
        }
       }
       
       if (commandLabel.equalsIgnoreCase("record")) {
           
           for(Player p : Bukkit.getOnlinePlayers()) {
               if (p.isOp()) {
               p.playEffect(p.getLocation(), Effect.RECORD_PLAY, Material.GOLD_RECORD.getId());
               return true;
               } else p.sendMessage(ChatColor.RED + "[RuBeta]" + ChatColor.AQUA + " Хорошая попытка!");
           }
       }
       
      return false;
  }

  

  //Создание флая
  public static void checkConfig()
  {
    File iniDir = new File("plugins/Fly");
    
    if (!iniDir.exists())
    {
      if (iniDir.mkdir()) {
        System.out.println("[fly] Created Settings.ini folder: plugins/fly/");
      }
    }
    File ini = new File(iniFile);
    
    if (!ini.exists())
    {
      if (createIniFile())
      {
        System.out.println("New Settings.ini file created with default settings.");
      }
    }
  }
  
  public static Integer isFlying(Player player)
  {
    if (flyingPlayers.containsKey(player)) {
      return (Integer)flyingPlayers.get(player);
    }
    return Integer.valueOf(0);
  }
  
  public static Location getHoverLocation(Player player)
  {
    if (hoverLocs.containsKey(player)) {
      return (Location)hoverLocs.get(player);
    }
    return player.getLocation();
  }
  
  public static void setFlying(Player player, Integer value)
  {
    flyingPlayers.put(player, value);
  }
  
  public static void setHoverLocation(Player player)
  {
    hoverLocs.put(player, player.getLocation());
  }
  
  public static void setFeatherPoints(Player player, int points)
  {
    featherPoints.put(player, Integer.valueOf(points));
  }
  
  public static Integer getFeatherPoints(Player player)
  {
    if (featherPoints.containsKey(player)) {
      return (Integer)featherPoints.get(player);
    }
    
    featherPoints.put(player, defaultFeatherAmount);
    return defaultFeatherAmount;
  }
  

  public static void logOutput(String text)
  {
    System.out.println("[fly] " + text);
  }
  
  private void setupPermissions()
  {
    Plugin test = getServer().getPluginManager().getPlugin("Permissions");
    
    if (Permissions == null)
    {
      if (test != null)
      {
        Permissions = ((Permissions)test).getHandler();
      }
      else
      {
        logOutput("Permission system not detected, defaulting to OP");
      }
    }
  }
  
  public static boolean isAllowed(Player player)
  {
    if (Permissions.has(player, "fly.fly")) {
      return true;
    }
    
    if (Permissions.has(player, "rubeta.repair")) {
        return true;
    }
    
    if ((player.isOp()) && (allowOps)) {
      return true;
    }
    return false;
  }
  

  public String[] getSetting(String which, String Default)
  {
    return getSettingValue(iniFile, which, Default, "");
  }
  


  public boolean getBooleanSetting(String which, boolean dValue)
  {
    String dString = dValue ? "true" : "false";
    boolean retVal; if (getSettingValue(iniFile, which, dString, "")[0].equalsIgnoreCase("true")) {
      retVal = true;
    } else {
      retVal = false;
    }
    return retVal;
  }
  

  public String[] getSettingValue(String fileName, String optionName, String defaultValue, String splitValue)
  {
      String[] returnValue = new String[100];
    

    Boolean gotLine = Boolean.valueOf(false);
    
    if (!new File(fileName).exists())
    {
      return new String("File not found.ZQX").split("ZQX");
    }
    if (splitValue.equals("")) {
      splitValue = "afs4wfa3waawfa3dogrsijkge5ssioeguhwar3awwa3rar";
    }
    
    try
    {
      FileInputStream fstream = new FileInputStream(fileName);
      BufferedReader in = new BufferedReader(new InputStreamReader(fstream));
      
      while (in.ready())
      {
        String curLine = in.readLine().toString();
        if (curLine.split("=", 2)[0].equalsIgnoreCase(optionName)) {
          returnValue = new String[100];
          returnValue = curLine.split("=", 2)[1].split(splitValue);
          gotLine = Boolean.valueOf(true);
        }
      }
      
      in.close();
      

      if (!gotLine.booleanValue())
      {
        returnValue = defaultValue.split(splitValue);
        
        try
        {
          FileOutputStream out = new FileOutputStream(fileName, true);
          PrintStream p = new PrintStream(out);
          p.println(optionName + "=" + defaultValue);
          p.close();
        } catch (Exception e) {
          logOutput("Error writing to file");
        }
      }
      

      return returnValue;
    }
    catch (Exception e)
    {
      logOutput("-=-");
      logOutput("File input error: " + e.toString());
      logOutput("File input error: " + e.getStackTrace().toString());
      logOutput("-=-");
      return returnValue;
  }
}

  public Integer getIntSetting(String item, Integer dValue)
  {
    Integer retVal = Integer.valueOf(Integer.parseInt(getSettingValue(iniFile, item, dValue.toString(), "")[0]));
    return retVal;
  }
  
  public double getDblSetting(String item, double d)
  {
    double retVal = Double.parseDouble(getSettingValue(iniFile, item, Double.toString(d), "")[0]);
    return retVal;
  }
  
  public Float getFloatSetting(String item, Float dValue)
  {
    Float retVal = Float.valueOf(getSettingValue(iniFile, item, dValue.toString(), "")[0]);
    return retVal;
  }
  
  public static boolean createIniFile()
  {
    boolean retVal = false;
    
    try
    {
      FileWriter outFile = new FileWriter(iniFile);
      PrintWriter outP = new PrintWriter(outFile);
      
      outP.println("flightSpeed=1.1");
      outP.println("hoverMultiplier=0.0012410253");
      outP.println("hoverBoostMultiplier=0.00555");
      outP.println("allowOps=true");
      outP.println("flyingEatsFeathers=false");
      outP.println("defaultFeatherAmount=500");
      
      outP.close();
      retVal = true;
    }
    catch (IOException e)
    {
      logOutput("Error writing to ini file.");
      retVal = false;
      e.printStackTrace();
    }
    
    return retVal;
        
    
    
  }
  
  
}