@echo off
echo Building Grid Conflict Engine...

cd /d "%~dp0src"

if not exist "..\build" (
    mkdir "..\build"
)

g++ -std=c++20 -O2 -static -static-libgcc -static-libstdc++ gcae.cpp main.cpp -o "..\build\grid_conflict.exe"

if %errorlevel% neq 0 (
    echo Build failed.
    pause
    exit /b %errorlevel%
)

echo Build successful.
pause