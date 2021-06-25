package com.npcss.npcss;

import org.bukkit.inventory.ItemStack;

public class server {
  public String name;
  public ItemStack itemStack;

  public server(ItemStack itemStack, String n) {
    this.itemStack = itemStack;
    this.name = n;
  }
}
