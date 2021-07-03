package com.npcss.npcss;

import java.text.*;
import org.bukkit.Material;

public class bungeeServer {
    public String name, ip;
    public int port;
    public Material material;

    public bungeeServer(String Name, String Ip, Integer Port, String mat){
	Default();
	if (Ip != null) {
		this.ip=Ip;
	}
	if (Port.toString() != null) {
		this.port=Port;
	}
	if (Name != null) {
		this.name=Name;
	}
	if (mat != null) {
		this.material = Material.matchMaterial(mat);
	}
    }
    public void Default(){
	this.ip="localhost";
	this.port=25565;
	this.name="lobby";
	this.material = Material.matchMaterial("GRASS_BLOCK");
    }
}
