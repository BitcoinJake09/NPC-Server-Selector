Simple GUI NPC Bungeecord server selector

uses NpcSs/config.json for the NPC xyz 
Dynamic server display
just add acending numbers to SERVER
set NAME, IP, PORT, MATERIAL

example config:


[{"config":{
"SERVER1":{"NAME":"Anarchy","IP":"xxx.xxx.xxx.xxx","PORT":"xxxxx","MATERIAL":"OBSIDIAN"},
"SERVER2":{"NAME":"EmeraldQuest","IP":"xxx.xxx.xxx.xxx","PORT":"xxxxx","MATERIAL":"EMERALD"},
"SERVER3":{"NAME":"SatoshiQuest","IP":"xxx.xxx.xxx.xxx","PORT":"xxxxx","MATERIAL":"GLOWSTONE"},
"SERVER4":{"NAME":"LbryQuest","IP":"xxx.xxx.xxx.xxx","PORT":"xxxxx","MATERIAL":"SEA_LANTERN"},
"NPC_X":"13",
"NPC_Z":"72",
"NPC_Y":"10"}}]


for self compilation:

$ git clone https://github.com/BitcoinJake09/NPC-Server-Selector

$ cd NPC-Server-Selector

$ mvn clean compile assembly:single



and jar would be found in target folder, place in servers plugins folder and then configure the config.json in NpcSs folder in plugins, if you have not ran plugin before you can create the folder and put config.json in it.
