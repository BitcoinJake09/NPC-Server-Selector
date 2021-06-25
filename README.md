Simple GUI NPC Bungeecord server selector

uses NpcSs/config.json for the NPC xyz 

example config:
[{"config":{
"NPC_X":"13",
"NPC_Z":"72",
"NPC_Y":"10"}}]


for self compilation:

$ git clone https://github.com/BitcoinJake09/NPC-Server-Selector
$ cd NPC-Server-Selector
$ mvn clean compile assembly:single

and jar would be found in target folder, place in servers plugins folder and then configure the config.json in NpcSs folder in plugins, if you have not ran plugin before you can create the folder and put config.json in it.
