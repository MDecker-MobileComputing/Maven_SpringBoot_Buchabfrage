
@REM wartet 5 Sekunden zwischen Start von zwei Instanzen

@echo.

start "ISBN2Preis (1) auf Port 8010" cmd /c "maven_startserver_8010.bat"

timeout /t 5 >nul

start "ISBN2Preis (2) auf Port 8020" cmd /c "maven_startserver_8020.bat"

timeout /t 5 >nul

start "ISBN2Preis (3) auf Port 8030" cmd /c "maven_startserver_8030.bat"

@echo.
@echo Alle Instanzen von ISBN2Preis sollten jetzt laufen ...
@pause

