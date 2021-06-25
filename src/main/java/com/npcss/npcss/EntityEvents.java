package com.npcss.npcss;

import com.npcss.npcss.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Attachable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.player.*;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.block.Sign;
import org.bukkit.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitScheduler;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import org.bukkit.plugin.messaging.PluginMessageListener;
import net.md_5.bungee.api.plugin.Plugin;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by explodi on 11/7/15.
 */
public class EntityEvents implements Listener {
    NpcSs npcSs;
    

    public EntityEvents(NpcSs plugin) {
        npcSs = plugin;
    }

  @EventHandler
  void onInteract(PlayerInteractEntityEvent event) {
    // VILLAGER
    if (event.getRightClicked().getType().equals(EntityType.VILLAGER)) {
      //event.setCancelled(true);
    npcSs.serverList = new ArrayList<>();
    npcSs.serverList.add(new server(new ItemStack(Material.OBSIDIAN, 1), "Anarchy"));
    npcSs.serverList.add(new server(new ItemStack(Material.EMERALD, 1), "EmeraldQuest"));
    npcSs.serverList.add(new server(new ItemStack(Material.GLOWSTONE, 1), "SatoshiQuest"));
    npcSs.serverList.add(new server(new ItemStack(Material.SEA_LANTERN, 1), "LbryQuest")); 
      Inventory serverInventory = Bukkit.getServer().createInventory(null, InventoryType.ENDER_CHEST, "Server List");
      for (int i = 0; i < npcSs.serverList.size(); i++) {
        int inventoryStock = npcSs.SERVER_COUNT;

        if (inventoryStock > 0) {
          ItemStack button = new ItemStack(npcSs.serverList.get(i).itemStack);
          ItemMeta meta = button.getItemMeta();
          ArrayList<String> lore = new ArrayList<String>();
          lore.add(npcSs.serverList.get(i).name);
          meta.setLore(lore);
          button.setItemMeta(meta);
          serverInventory.setItem(i, button);
        }
      }
      event.getPlayer().openInventory(serverInventory);
    }
  }
  
    @EventHandler
  void onInventoryClick(final InventoryClickEvent event) throws Exception {
    final Player player = (Player) event.getWhoClicked();
    final Inventory inventory = event.getInventory();

    // Merchant inventory
    if (inventory.getType() == InventoryType.ENDER_CHEST) {
      if (event.getRawSlot() < event.getView().getTopInventory().getSize()) {
       
        // player buys
        final ItemStack clicked = event.getCurrentItem();
        if (clicked != null && clicked.getType() != Material.AIR) {
	//System.out.println("[NpcSs Test] Test 1: ");
        ItemMeta meta = event.getCurrentItem().getItemMeta();
	ArrayList<String> sendToServerName = new ArrayList<String>(meta.getLore());
	String FinalServerName = sendToServerName.get(0);


  // If you don't care about the player
  // Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
  // Else, specify them
  npcSs.sendPlayerOffToServer(player, FinalServerName);
	//player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
	//ServerCommand(player, sendToServerName);


        }
      }
    }
  }
}
