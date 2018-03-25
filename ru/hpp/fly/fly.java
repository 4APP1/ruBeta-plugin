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
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class fly extends JavaPlugin
{
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
  
  public fly() {}
  
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
    PlayerListener pListener = new PlayerListener(this);
    EntityListener eListener = new EntityListener(this);
    TimerTask task = new TimerTask(plugin);
    
    pm.registerEvent(Event.Type.PLAYER_QUIT, pListener, priority, plugin);
    pm.registerEvent(Event.Type.PLAYER_MOVE, pListener, priority, plugin);
    pm.registerEvent(Event.Type.PLAYER_INTERACT, pListener, priority, plugin);
    pm.registerEvent(Event.Type.ENTITY_DAMAGE, eListener, priority, plugin);
    
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
  
  @Override
  public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String commandLabel, String[] args){
    if (commandLabel.equalsIgnoreCase("colours")) {
      //Создание игрока
      Player p = (Player)sender;
      p.sendMessage("Все цветовые коды майнкрафта");
      p.sendMessage(ChatColor.BLACK + "&0, " + ChatColor.DARK_BLUE + "&1, " + ChatColor.DARK_GREEN + "&2, " + ChatColor.DARK_AQUA + "&3, " + ChatColor.DARK_RED + "&4, " + ChatColor.DARK_PURPLE + "&5, " + ChatColor.GOLD + "&6, " + ChatColor.GRAY + "&7, " + ChatColor.DARK_GRAY + "&8, " + ChatColor.BLUE + "&9, " + ChatColor.GREEN + "&a, " + ChatColor.AQUA + "&b, " + ChatColor.RED + "&c, " + ChatColor.LIGHT_PURPLE + "&d, " + ChatColor.YELLOW + "&e, " + ChatColor.WHITE + "&f");
      }
        return true;
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