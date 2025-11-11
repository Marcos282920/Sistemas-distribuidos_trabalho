@echo off
echo ========================================
echo    COMPILANDO SISTEMA DE TELEFONIA
echo ========================================
echo.

cd src

echo [1/4] Compilando model...
javac model\*.java
if %errorlevel% neq 0 (
    echo ERRO ao compilar model
    pause
    exit /b 1
)

echo [2/4] Compilando stream...
javac -cp . stream\*.java
if %errorlevel% neq 0 (
    echo ERRO ao compilar stream
    pause
    exit /b 1
)

echo [3/4] Compilando network...
javac -cp . network\*.java
if %errorlevel% neq 0 (
    echo ERRO ao compilar network
    pause
    exit /b 1
)

echo [4/4] Compilando Main...
javac -cp . Main.java
if %errorlevel% neq 0 (
    echo ERRO ao compilar Main
    pause
    exit /b 1
)

echo.
echo ========================================
echo    COMPILACAO CONCLUIDA COM SUCESSO!
echo ========================================
echo.
echo Para executar:
echo   cd src
echo   java Main
echo.
pause
