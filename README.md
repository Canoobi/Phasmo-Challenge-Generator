# Phasmophobia-Challenge-Generator
PhasmoChallengeGenerator ist ein Programm zur Generierung von zufälligen Herausforderungen für das Spiel 'Phasmophobia'.

## Beschreibung
Das Programm ermöglicht es Benutzern, ein "Chellenge-Wheel" zu drehen, um zufällige Herausforderungen zu erhalten, die sie während des Spiels erfüllen müssen. Die Herausforderungen werden aus einer JSON-Datei geladen, die die verschiedenen Aufgaben enthält

## Funktionalitäten
- Drehen des Challenge Wheels, um eine zufällige Herausforderung zu erhalten.
- Anzeige der Herausforderung in einem GUI-Fenster.
- Unterstützung für verschiedene Herausforderungen, die aus einer JSON-Datei geladen werden.

## Verwendung
1. Stelle sicher, dass du Java installiert hast (siehe auch unter Java-Installation).
2. Führe die Datei "\PhasmoChallengeGenerator\PhasmoChallengeGen.exe" aus.
3. Eine Warnung mit "Der Computer wurde durch Windows geschützt" sollte sich öffnen. Führe folgende Schritte durch:
   - Klicke auf 'Weitere Informationen'.
   - Klicke 'trotzdem ausführen'.
4. Klicke auf die Schaltfläche "Spin", um eine neue Challenge zu erhalten
5. Hab Spaß mit der Challenge!

## Java-Installation
1. Lade dir Folgendes runter und installiere es:
   - [jdk](https://www.oracle.com/de/java/technologies/downloads/#jdk22-windows)
   - [jre](https://javadl.oracle.com/webapps/download/AutoDL?BundleId=249550_4d245f941845490c91360409ecffb3b4) 
2. Ändere die folgenden System-Umgebungsvariablen oder füge sie hinzu:
   - JAVA_HOME &rarr; C:\Program Files\Java\jdk-22 (Installationspfad jdk)
   - PATH &rarr; C:\Program Files (x86)\Java\jre-1.8\bin (Installationspfad jre)

## JSON-Datei
In der Datei 'tasks.json' sind die einzelnen Aufgaben gespeichert. Bearbeite diese Datei, um neue Aufgaben hinzuzufügen.

## Mitwirkende
@Josefine (Challenges)\
@Carlo (Challenge Generator)