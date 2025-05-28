@echo off
echo ========================================
echo   Train Ticket System - Quick Start
echo ========================================
echo.

echo 1. Compilation du projet...
call mvn clean package
if %ERRORLEVEL% neq 0 (
    echo ERREUR: La compilation a echoue
    pause
    exit /b 1
)

echo.
echo 2. Verification du fichier WAR...
if exist "target\train-ticket-system-1.0-SNAPSHOT.war" (
    echo ✓ Fichier WAR genere avec succes
) else (
    echo ✗ Fichier WAR non trouve
    pause
    exit /b 1
)

echo.
echo 3. Instructions de deploiement:
echo.
echo   a) Assurez-vous que MySQL est demarre
echo   b) Creez la base de donnees 'traindb' si necessaire
echo   c) Copiez le fichier WAR dans le dossier webapps de Tomcat:
echo      target\train-ticket-system-1.0-SNAPSHOT.war
echo   d) Demarrez Tomcat
echo   e) Accedez a: http://localhost:8080/train-ticket-system-1.0-SNAPSHOT/test
echo.
echo 4. Comptes par defaut:
echo   - Admin: admin / admin123
echo.
echo 5. URLs importantes:
echo   - Test: /test
echo   - Accueil: /
echo   - Recherche: /recherche
echo   - Admin: /admin/dashboard
echo.

echo Appuyez sur une touche pour ouvrir le dossier target...
pause >nul
explorer target

echo.
echo Deploiement termine ! Suivez les instructions ci-dessus.
pause
