@echo off
cd /d "%~dp0"
javac -encoding UTF-8 -d out game\Pman.java com\pacman\score\ScoreEntry.java com\pacman\score\ScoreManager.java
if errorlevel 1 (
    echo Compilacion fallida.
    pause
    exit /b 1
)
java -cp out Pman
