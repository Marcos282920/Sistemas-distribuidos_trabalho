@echo off
echo ╔══════════════════════════════════════════════════╗
echo ║       CLIENTE TCP - LOCALHOST:5000               ║
echo ╚══════════════════════════════════════════════════╝
echo.
echo Aguarde 2 segundos para garantir que o servidor iniciou...
timeout /t 2 /nobreak > nul
echo.
echo Conectando ao servidor...
echo.

cd src
java network.ClienteTelefonia

pause
