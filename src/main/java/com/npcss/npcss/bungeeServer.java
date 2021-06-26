package com.npcss.npcss;

import java.text.*;
import org.bukkit.Material;

public class bungeeServer {
    public String name, ip;
    public int port;
    public Material material;

    public bungeeServer(String Name, String Ip, int Port, String mat){
	this.ip=Ip;
	this.port=Port;
	this.name=Name;
	this.material = Material.matchMaterial(mat);
    }
    public void Default(){
	this.ip="localhost";
	this.port=25565;
	this.name="lobby";
    }
}
