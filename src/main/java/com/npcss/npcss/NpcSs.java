// bitcoinjake09 - npcss - 06-10-2021
package com.npcss.npcss;

import com.npcss.npcss.*;
import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.text.*;
import java.util.*;
import java.util.Date;
import javax.net.ssl.HttpsURLConnection;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import org.bukkit.plugin.messaging.PluginMessageListener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.*;

public class NpcSs extends JavaPlugin implements Listener {

  public boolean maintenance_mode = false;
  ArrayList<server> serverList;
  public static ArrayList<bungeeServer> BungeeServers;
  public static double NPC_X = 0.0;
  public static double NPC_Y = 0.0;
  public static double NPC_Z = 0.0;
  public static Location Loc = new Location(Bukkit.getServer().getWorld("World"), NPC_X, NPC_Z, NPC_Y);
  
  public static final String PLUGIN_WEBSITE = "https://github.com/BitcoinJake09/NpcSs";
  public static int SERVER_COUNT = 4;

  public void onEnable() {
    log("[NpcSs] NpcSs starting");
    try {
      System.out.println("[NpcSs] Starting NpcSs");

      // loads config file. If it doesn't exist, creates it.
      getDataFolder().mkdir();
      System.out.println("[NpcSs] checking default config file");

      if (!new java.io.File(getDataFolder(), "config.yml").exists()) {
        saveDefaultConfig();
        System.out.println("[NpcSs] config file does not exist. creating default.");
      }
      BungeeServers = new ArrayList<>();
  	loadJSON();
	loadNPC();
      // creates scheduled timers (update balances, etc)
      createScheduledTimers();
      
	getServer().getPluginManager().registerEvents(new EntityEvents(this), this);
    getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    

    //this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

      System.out.println("[NpcSs] finished");

    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("[NpcSs] plugin enable fails");
      Bukkit.shutdown();
    }
  }
 
 public void sendPlayerOffToServer(Player p, String server) throws Exception {
	ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
 
        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
		System.out.println("[NpcSs] trying to send " + p.getName() + " to server: " + server);
        } catch (IOException eee) {
            // Fehler
        }
        p.sendPluginMessage(this, "BungeeCord", b.toByteArray());
 }
 
public boolean checkNPC() {
Iterator<Entity> entityIterator = Loc.getWorld().getEntities().iterator();
  while (entityIterator.hasNext()) {
   Entity e = entityIterator.next();
   
   if (e.getType() == EntityType.VILLAGER) {
   //	location.getWorld().spawnEntity(location.clone().subtract(-0.5, 2, -0.5), EntityType.SNOWMAN);
   Location tempLoc = e.getLocation();
    if (((tempLoc.getX()-Loc.getX()) <= 1.5)&&((tempLoc.getY()-Loc.getY()) <= 1.5)&&((tempLoc.getZ()-Loc.getZ()) <= 1.5)) {
       return true;
    }
   }
	}
	return false;
}

public void loadNPC() {
	if (checkNPC() == false) {
	Loc.getWorld().spawnEntity(Loc.clone(), EntityType.VILLAGER);
	}
}


  public void loadJSON() {
  JSONParser jsonParser = new JSONParser();
  System.out.println("[NpcSs] attempting to load json config files.");
          try {
		File configFile = new File(System.getProperty("user.dir") + "/plugins/NpcSs/config.json");

            FileReader reader = new FileReader(configFile);

            Object obj = jsonParser.parse(reader);
            JSONArray configData = (JSONArray) obj; 
	configData.forEach( tConfig -> parseJSON( (JSONObject) tConfig) );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
           System.out.println("[NpcSs] no config json files found in directory plugins/NpcSs/* .");
        } catch (IOException e) {
	   System.out.println("[NpcSs] error reading json files.");
            e.printStackTrace();
        } catch (ParseException e) {
        System.out.println("[NpcSs] error parsing json files.");
            e.printStackTrace();
        }
  }
 
   private static void parseJSON(JSONObject tConfig) {
   try {
   	
   	JSONObject configDataObj = (JSONObject) tConfig.get("config");
   	JSONObject serverJsons = (JSONObject) configDataObj.get("SERVER1");
	int x = 1;   	
	while (serverJsons != null) {
	String tNAME = (String) serverJsons.get("NAME") != null ? serverJsons.get("NAME").toString() : null;
	String tIP = (String) serverJsons.get("IP") != null ? serverJsons.get("IP").toString() : null;
	int tPORT = (String) serverJsons.get("PORT") != null ? Integer.parseInt(serverJsons.get("PORT").toString()) : null;
	String tMat = (String) serverJsons.get("MATERIAL") != null ? serverJsons.get("MATERIAL").toString() : null;
   	BungeeServers.add(new bungeeServer(tNAME, tIP, tPORT, tMat));
   	x = x+1;
	serverJsons = (JSONObject) configDataObj.get("SERVER" + x);
   	}
	String tNPC_X = (String) configDataObj.get("NPC_X") != null ? configDataObj.get("NPC_X").toString() : null;
	NPC_X = Double.parseDouble(tNPC_X);            
	String tNPC_Y = (String) configDataObj.get("NPC_Y") != null ? configDataObj.get("NPC_Y").toString() : null;              
	NPC_Y = Double.parseDouble(tNPC_Y);            
	String tNPC_Z = (String) configDataObj.get("NPC_Z") != null ? configDataObj.get("NPC_Z").toString() : null;  
	NPC_Z = Double.parseDouble(tNPC_Z);            
	Loc = new Location(Bukkit.getServer().getWorld("World"), NPC_X, NPC_Z, NPC_Y);
	/*
	[{"config":{
"NPC_UUID":"bceeaefc-9590-4233-a858-d3eb933121ec",
"NPC_X":"0",
"NPC_Y":"100",
"NPC_Z":"0"}}]
	*/
            } catch (Exception e) {
      e.printStackTrace();
      System.out.println("[NpcSs] [fatal] plugin enable failed to get config.json");
    }
  }
 
  public static void announce(final String message) {
    for (Player player : Bukkit.getOnlinePlayers()) {
      player.sendMessage(message);
    }
  }

  public void createScheduledTimers() {
    BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
    scheduler.scheduleSyncRepeatingTask(
        this,
        new Runnable() {
          @Override
          public void run() {
          loadNPC();
          }
        },
        0L,
        5L);
        }



  public void publish_stats() {
    try {

      if (System.getenv("ELASTICSEARCH_ENDPOINT") != null) {
        JSONParser parser = new JSONParser();

        final JSONObject jsonObject = new JSONObject();


        jsonObject.put("time", new Date().getTime());
        URL url = new URL(System.getenv("ELASTICSEARCH_ENDPOINT") + "-stats/_doc");

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        con.setDoOutput(true);
        OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
        out.write(jsonObject.toString());
        out.close();

        if (con.getResponseCode() == 200) {

          BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
          String inputLine;
          StringBuffer response = new StringBuffer();

          while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
          }
          in.close();
          // System.out.println(response.toString());
          JSONObject response_object = (JSONObject) parser.parse(response.toString());
          // System.out.println(response_object);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

 


  public void log(String msg) {
    Bukkit.getLogger().info(msg);
  }
} // EOF
