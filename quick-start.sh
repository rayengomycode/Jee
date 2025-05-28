#!/bin/bash

echo "========================================"
echo "  Train Ticket System - Quick Start"
echo "========================================"
echo

echo "1. Compilation du projet..."
mvn clean package
if [ $? -ne 0 ]; then
    echo "ERREUR: La compilation a échoué"
    exit 1
fi

echo
echo "2. Vérification du fichier WAR..."
if [ -f "target/train-ticket-system-1.0-SNAPSHOT.war" ]; then
    echo "✓ Fichier WAR généré avec succès"
else
    echo "✗ Fichier WAR non trouvé"
    exit 1
fi

echo
echo "3. Instructions de déploiement:"
echo
echo "   a) Assurez-vous que MySQL est démarré"
echo "   b) Créez la base de données 'traindb' si nécessaire:"
echo "      CREATE DATABASE traindb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
echo "   c) Copiez le fichier WAR dans le dossier webapps de Tomcat:"
echo "      cp target/train-ticket-system-1.0-SNAPSHOT.war \$CATALINA_HOME/webapps/"
echo "   d) Démarrez Tomcat:"
echo "      \$CATALINA_HOME/bin/startup.sh"
echo "   e) Accédez à: http://localhost:8080/train-ticket-system-1.0-SNAPSHOT/test"
echo
echo "4. Comptes par défaut:"
echo "   - Admin: admin / admin123"
echo
echo "5. URLs importantes:"
echo "   - Test: /test"
echo "   - Accueil: /"
echo "   - Recherche: /recherche"
echo "   - Admin: /admin/dashboard"
echo

echo "Fichier WAR prêt dans: $(pwd)/target/"
ls -la target/*.war

echo
echo "Déploiement terminé ! Suivez les instructions ci-dessus."
