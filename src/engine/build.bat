:: To ensure that the bat file builds the engine, keep the projects structure as it is.
@echo off

echo Building Grid Conflict Engine...

cd /d %~dp0\src

if not exist "..\build" (
    mkdir ..\build
)

g++ -std=c++20 -O2 gcae.cpp main.cpp -o ..\build\grid_conflict.exe

if %errorlevel% neq 0 (
    echo Build failed.
    pause
    exit /b %errorlevel%
)

echo Build successful.
pause