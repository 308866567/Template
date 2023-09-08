@echo off
setlocal enabledelayedexpansion

set "folderPath=C:\c+++\Template\graph"

for %%f in ("%folderPath%\*.cpp") do (
    echo Converting file: %%~nxf
    chcp 65001 > nul
    powershell -Command "$content = Get-Content -LiteralPath '%%f' -Encoding Default; Set-Content -LiteralPath '%%f' -Value $content -Encoding UTF8"
)

echo All CPP files in the folder have been converted to UTF-8 encoding.

pause
