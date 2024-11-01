@echo off

REM Определение пути к java.exe из PATH
set JAVA_CMD=java

REM Проверка, доступен ли java в PATH
%JAVA_CMD% -version >nul 2>&1
if errorlevel 1 (
    echo Java не найден в PATH. Убедитесь, что Java установлена и путь прописан в переменной окружения PATH.
    pause
    exit /b 1
)

REM Проверка версии JDK
%JAVA_CMD% -version

REM Запуск вашего JAR-файла
%JAVA_CMD% -cp Simulation-1.0-SNAPSHOT.jar com.example.Main

pause