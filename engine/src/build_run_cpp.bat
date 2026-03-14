@echo off

set "SRC=%~1"
set "DIR=%~dp1"

rem presupunem ca main2.cpp si gcae2.cpp sunt in acelasi folder
set "MAIN=%DIR%main2.cpp"
set "ENGINE=%DIR%gcae2.cpp"
set "OUT=%DIR%engine.exe"

g++ -std=c++20 -O2 "%MAIN%" "%ENGINE%" -o "%OUT%"

start "" cmd /c "cd /d %DIR% && engine.exe < input.txt & pause"

