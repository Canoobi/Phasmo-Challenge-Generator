# Phasmophobia-Challenge-Generator
PhasmoChallengeGenerator ist ein Programm zur Generierung von zufälligen Herausforderungen für das Spiel 'Phasmophobia'.

## Beschreibung
Das Programm ermöglicht es Benutzern, ein "Chellenge-Wheel" zu drehen, um zufällige Herausforderungen zu erhalten, die sie während des Spiels erfüllen müssen. Die Herausforderungen werden aus einer JSON-Datei geladen, die die verschiedenen Aufgaben enthält

## Funktionalitäten
- Drehen des Challenge Wheels, um eine zufällige Herausforderung zu erhalten.
- Anzeige der Herausforderung in einem GUI-Fenster.
- Unterstützung für verschiedene Herausforderungen, die aus einer JSON-Datei geladen werden.

## Verwendung
1. Lade dir den letzten Release von [GitHub](https://github.com/Canoobi/Phasmo-Challenge-Generator/releases) herunter.
2. Stelle sicher, dass du Java installiert hast (siehe auch 'Java-Installation').
3. Führe die Datei ```"\Phasmo-Challenge-Generator\PhasmoChallengeGen.exe"``` aus.
4. Eine Warnung mit "Der Computer wurde durch Windows geschützt" sollte sich öffnen. Führe folgende Schritte durch:
   - Klicke auf 'Weitere Informationen'.
   - Klicke 'trotzdem ausführen'.
5. Klicke auf die Schaltfläche "Spin", um eine neue Challenge zu erhalten
6. Hab Spaß mit der Challenge!

## Eigene Version erstellen
Um eine eigene Version des Phasmo-Challenge-Generators zu erstellen, befolge die folgenden Schritte:
1. Klone dir das aktuelle Repository.
2. Öffne das lokale Repository mit IntelliJ.
3. Füge in die Datei ```src\resources\tasks.json``` deine Aufgaben nach dem folgenden Muster ein:
   - ```{"text": "%Deine Aufgabe hier einfügen%", "openSelectItemFrame": "false", "message": "%Optional eine abweichende Ausgabenachricht einfügen%" }``` für eine normale Aufgabe ohne auszuführende Funktion.
   - ```{"text": "%Deine Aufgabe hier einfügen%", "openSelectItemFrame": "true", "message": "%Optional eine abweichende Ausgabenachricht einfügen%" }``` für eine Aufgabe, bei der sich das Item-Auswahl-Fenster öffnen soll (beachte dabei den Timer und, dass jedes Item nur einmal ausgewählt werden kann).
   - ```{"text": "%Deine [items] Aufgabe hier einfügen%", "openSelectItemFrame": "false", "message": "%ZWINGEND eine Ausgabenachricht einfügen%" }``` für eine Aufgabe bei der 2 bis 12 zufällige Items an das Ende der Aufgabe angehangen werden sollen. Beachte, dass ```[items]``` in der Aufgabe vorkommen muss!
   - ```{"text": "%Deine [numb] Aufgabe einfügen%", "openSelectItemFrame": "false", "message": "%Optional eine abweichende Ausgabenachricht einfügen%" }``` für eine Aufgabe in der eine zufällige Zahl zwischen 1 und 8 vorkommen soll. Beachte, dass ```[numb]``` in der Aufgabe vorkommen muss! Soll eine abweichende Ausgabenachricht genutzt werden, muss darin ebenfalls ```[numb]``` vorkommen.
4. Erstelle eine JAR-Datei als Artifact. Beachte, dass die 'json-simple'-Library inkludiert werden muss.
5. Installiere und starte Launch4j. Wähle eine Output-Datei und die JAR-Datei aus und erstelle die EXE-Datei.
6. Herzlichen Glückwunsch, du hast deine eigene Version des Phasmo-Challenge-Generators erstellt. Zur Verwendung fahre bei 'Verwendung' ab Punkt 2 fort.

## Java-Installation
1. Lade dir Folgendes runter und installiere es:
   - [jdk](https://www.oracle.com/de/java/technologies/downloads/#jdk22-windows)
   - [jre](https://javadl.oracle.com/webapps/download/AutoDL?BundleId=249550_4d245f941845490c91360409ecffb3b4) 
2. Ändere die folgenden System-Umgebungsvariablen oder füge sie hinzu:
   - JAVA_HOME &rarr; C:\Program Files\Java\jdk-22 (Installationspfad jdk)
   - PATH &rarr; C:\Program Files (x86)\Java\jre-1.8\bin (Installationspfad jre)

## JSON-Datein
In der Datei ```src\resources\tasks.json``` sind die einzelnen Aufgaben gespeichert. Befolge die Anweisungen unter 'Eigene Version erstellen', um den Randomizer für eigene Aufgaben zu nutzen!
In der Datei ```src\resources\items.json``` sind alle im Spiel verfügbaren Items hinterlegt.

## Mitwirkende
@Josefine (Challenges)\
@Carlo (Challenge Generator)