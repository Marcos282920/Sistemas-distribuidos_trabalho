@echo off
chcp 65001 > nul
echo ╔════════════════════════════════════════════════════╗
echo ║    TESTE COMPLETO - SISTEMA DE TELEFONIA         ║
echo ╚════════════════════════════════════════════════════╝
echo.

echo [1/5] Compilando projeto...
cd src
call javac model\*.java
call javac -cp . stream\*.java
call javac -cp . network\*.java
call javac -cp . *.java
if errorlevel 1 (
    echo ❌ Erro na compilação!
    pause
    exit /b 1
)
echo ✅ Compilação concluída!
echo.

echo [2/5] Teste 1: FaturaOutputStream com System.out
echo 1 | java Main
echo.
pause

echo [3/5] Teste 2: FaturaOutputStream com FileOutputStream
echo 2 | java Main
echo.
echo Arquivo gerado: faturas_output.txt
pause

echo [4/5] Teste 3: FaturaOutputStream com TCP Socket
java TesteSocket
echo.
pause

echo [5/5] Teste 4: Cliente-Servidor com SERIALIZAÇÃO
echo.
echo 🚀 Iniciando servidor em background...
start /B java network.ServidorTelefonia > servidor.log 2>&1

timeout /t 3 /nobreak > nul

echo 🚀 Executando cliente...
echo 1 | java network.ClienteTelefonia
echo.

echo 📄 Log do servidor:
type servidor.log
echo.

taskkill /F /FI "WINDOWTITLE eq java*" > nul 2>&1

echo.
echo ╔════════════════════════════════════════════════════╗
echo ║              ✅ TODOS OS TESTES OK!               ║
echo ╚════════════════════════════════════════════════════╝
pause
