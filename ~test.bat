@echo off
copy /Y "build\libs\*.jar" C:\Users\admin\AppData\Roaming\.minecraft\mods
start /D "C:\Program Files (x86)\Minecraft" MinecraftLauncher.exe