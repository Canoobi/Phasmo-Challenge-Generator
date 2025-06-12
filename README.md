# Phasmophobia-Challenge-Generator (German)
(For the English version, see below)\
Phasmo-Challenge-Generator ist ein Programm zur zufälligen Auswahl von Herausforderungen für das Spiel 'Phasmophobia'.

## Beschreibung
Das Programm ermöglicht es Benutzern, ein "Challenge-Wheel" zu drehen, um zufällige Herausforderungen zu erhalten, die sie während des Spiels erfüllen müssen. Die Herausforderungen werden aus einer JSON-Datei geladen, die die verschiedenen Aufgaben enthält

## Funktionalitäten
- Drehen des Challenge Wheels, um eine zufällige Herausforderung zu erhalten.
- Anzeige der Herausforderung in einem GUI-Fenster.
- Unterstützung für verschiedene Herausforderungen, die aus einer JSON-Datei geladen werden.

## Verwendung
1. Lade dir die Datei ```PhasmoChallengeGenerator.exe.msi``` des letzten Releases von [GitHub](https://github.com/Canoobi/Phasmo-Challenge-Generator/releases) herunter.
2. Stelle sicher, dass du Java installiert hast (siehe auch 'Java-Installation').
3. Führe die heruntergeladene Datei ```PhasmoChallengeGenerator.exe.msi``` aus.
4. Eine Warnung mit "Der Computer wurde durch Windows geschützt" sollte sich öffnen. Führe folgende Schritte durch:
   - Klicke auf 'Weitere Informationen'.
   - Klicke 'trotzdem ausführen'.
5. Führe die Installation durch
6. Klicke auf die Schaltfläche "Spin", um eine neue Challenge zu erhalten
7. Hab Spaß mit der Challenge!

## Eigene Version erstellen
Um eine eigene Version des Phasmo-Challenge-Generators zu erstellen, befolge die folgenden Schritte:
1. Forke dir das aktuelle Repository.
2. Clone dir dein geforktes Repository.
3. Öffne das lokale Repository mit IntelliJ.
4. Überprüfe in der Datei ```src\main\resources\settings.json```, ob alle Einstellungen für dich passen. Falls nicht, passe sie an.
5. Füge in die Datei ```src\main\resources\tasks.json``` deine Aufgaben nach dem folgenden Muster ein:
   - ```{"text": "%Deine Aufgabe hier einfügen%", "openSelectItemFrame": "false", "message": "%Optional eine abweichende Ausgabenachricht einfügen%", "reqPlayers": [%füge die Spieleranzahlen hinzu, die diese Aufgabe bekommen können (als int; kommasepariert)%]}``` für eine normale Aufgabe ohne auszuführende Funktion.
   - ```{"text": "%Deine Aufgabe hier einfügen%", "openSelectItemFrame": "true", "message": "%Optional eine abweichende Ausgabenachricht einfügen%", "reqPlayers": [%füge die Spieleranzahlen hinzu, die diese Aufgabe bekommen können (als int; kommasepariert)%]}``` für eine Aufgabe, bei der sich das Item-Auswahl-Fenster öffnen soll (beachte dabei den Timer und, dass jedes Item nur einmal ausgewählt werden kann).
   - ```{"text": "%Deine [$items$] Aufgabe hier einfügen%", "openSelectItemFrame": "false", "message": "%ZWINGEND eine Ausgabenachricht einfügen%", "reqPlayers": [%füge die Spieleranzahlen hinzu, die diese Aufgabe bekommen können (als int; kommasepariert)%]}``` für eine Aufgabe bei der 2 bis 12 zufällige Items an das Ende der Aufgabe angehangen werden sollen. Beachte, dass ```[$items$]``` in der Aufgabe vorkommen muss!
   - ```{"text": "%Deine [$numb$] Aufgabe einfügen%", "openSelectItemFrame": "false", "message": "%Optional eine abweichende Ausgabenachricht einfügen%", "reqPlayers": [%füge die Spieleranzahlen hinzu, die diese Aufgabe bekommen können (als int; kommasepariert)%]}``` für eine Aufgabe in der eine zufällige Zahl zwischen 1 und 8 vorkommen soll. Beachte, dass ```[$numb$]``` in der Aufgabe vorkommen muss! Soll eine abweichende Ausgabenachricht genutzt werden, muss darin ebenfalls ```[$numb$]``` vorkommen.
   - ```{"text": "%Deine [$color$] Aufgabe einfügen%", "openSelectItemFrame": "false", "message": "%Optional eine abweichende Ausgabenachricht einfügen%", "reqPlayers": [%füge die Spieleranzahlen hinzu, die diese Aufgabe bekommen können (als int; kommasepariert)%]}``` für eine Aufgabe in der eine zufällige Spielerfarbe vorkommen soll. Beachte, dass ```[$color$]``` in der Aufgabe vorkommen muss! Soll eine abweichende Ausgabenachricht genutzt werden, muss darin ebenfalls ```[$color$]``` vorkommen.
6. Füge in der Datei ```src\recources\penalties.json``` die Bestrafungen nach dem Fehlschlagen einer Challenge hinzu.
7. Pushe deine Änderungen in das Remote-Repository.
8. Es wird im Ordner ```application``` automatisch eine Exe-Datei erstellt.
9. Herzlichen Glückwunsch, du hast deine eigene Version des Phasmo-Challenge-Generators erstellt. Zur Verwendung fahre bei 'Verwendung' ab Punkt 2 fort.

## Java-Installation
1. Lade dir Folgendes runter und installiere es:
   - [jdk](https://www.oracle.com/de/java/technologies/downloads/#jdk22-windows)
   - [jre](https://javadl.oracle.com/webapps/download/AutoDL?BundleId=249550_4d245f941845490c91360409ecffb3b4) 
2. Ändere die folgenden System-Umgebungsvariablen oder füge sie hinzu:
   - JAVA_HOME &rarr; C:\Program Files\Java\jdk-22 (Installationspfad jdk)
   - PATH &rarr; C:\Program Files (x86)\Java\jre-1.8\bin (Installationspfad jre)

## JSON-Datein
In der Datei ```src\main\resources\tasks.json``` sind die einzelnen Aufgaben gespeichert. Befolge die Anweisungen unter 'Eigene Version erstellen', um den Randomizer für eigene Aufgaben zu nutzen!
In der Datei ```src\main\resources\items.json``` sind alle im Spiel verfügbaren Items und die Pfade zu deren Bildern hinterlegt.
In der Datei ```src\main\resources\maps.json``` sind alle im Spiel verfügbaren Maps hinterlegt.
In der Datei ```src\main\resources\penalties.json``` sind alle Penalties hinterlegt.
In der Datei ```src\main\resources\settings.json``` sind nötigen Einstellungen hinterlegt.

## Mitwirkende
@blnpurple (Challenges)\
@canoobi (Challenge Generator)

# ---------------------------------------------

# Phasmophobia Challenge Generator (English)
Phasmo-Challenge-Generator is a program for the random selection of challenges for the game 'Phasmophobia'.

## Description
The program allows users to spin a "Challenge Wheel" to receive random challenges they must complete during the game. The challenges are loaded from a JSON file that contains various tasks.

## Features
- Spin the Challenge Wheel to receive a random challenge.
- Display the challenge in a GUI window.
- Support for various challenges loaded from a JSON file.

## Usage
1. Download the file ```PhasmoChallengeGenerator.exe.msi``` of the latest release from [GitHub](https://github.com/Canoobi/Phasmo-Challenge-Generator/releases).
2. Make sure you have Java installed (see 'Java Installation' also).
3. Run the downloaded file ```PhasmoChallengeGenerator.exe.msi```.
4. A warning with "Windows protected your PC" should open. Follow these steps:
   - Click on 'More info'.
   - Click 'Run anyway'.
5. Carry out the installation.
6. Click the "Spin" button to receive a new challenge.
7. Have fun with the challenge!

## Creating Your Own Version
To create your own version of the Phasmo Challenge Generator, follow these steps:
1. Fork the current repository.
2. Clone your forked repository.
3. Open the local repository with IntelliJ.
4. Check in the file ```src\main\resources\settings.json``` whether all settings are correct for you. If not, adjust them.
5. Add your tasks to the file ```src\main\resources\tasks.json``` as follows:
   - ```{"text": "%Insert your task here%", "openSelectItemFrame": "false", "message": "%Optionally insert a different output message%", "reqPlayers": [%add the number of players who can receive this task (as int; comma separated)%]}``` for a normal task without any functionality.
   - ```{"text": "%Insert your task here%", "openSelectItemFrame": "true", "message": "%Optionally insert a different output message%", "reqPlayers": [%add the number of players who can receive this task (as int; comma separated)%]}``` for a task where the item selection frame should open (note the timer and that each item can only be selected once).
   - ```{"text": "%Insert your [$items$] task here%", "openSelectItemFrame": "false", "message": "%MANDATORY: Insert an output message here%", "reqPlayers": [%add the number of players who can receive this task (as int; comma separated)%]}``` for a task where 2 to 12 random items should be appended to the end of the task. Note that ```[$items$]``` must be present in the task!
   - ```{"text": "%Insert your [$numb$] task here%", "openSelectItemFrame": "false", "message": "%Optionally insert a different output message%", "reqPlayers": [%add the number of players who can receive this task (as int; comma separated)%]}``` for a task where a random number between 1 and 8 should be included. Note that ```[$numb$]``` must be present in the task! If a different output message is to be used, ```[$numb$]``` must also be included in it.
   - ```{"text": "%Insert your [$color$] task here%", "openSelectItemFrame": "false", "message": "%Optionally insert a different output message%", "reqPlayers": [%add the number of players who can receive this task (as int; comma separated)%]}``` for a task where a random player color should be included. Note that ```[$color$]``` must be present in the task! If a different output message is to be used, ```[$color$]``` must also be included in it.
6. Add the penalties after the failure of a challenge in the file ```src\recources\penalties.json```.
7. Push your changes to the remote repository.
8. An exe file is automatically created in the ```application``` folder.
9. Congratulations, you have created your own version of the Phasmo Challenge Generator. To use it, continue from point 2 under 'Usage'.

## Java Installation
1. Download and install the following:
   - [jdk](https://www.oracle.com/java/technologies/downloads/#jdk22-windows)
   - [jre](https://www.oracle.com/java/technologies/downloads/#jdk22-windows)
2. Change or add the following system environment variables:
   - JAVA_HOME &rarr; C:\Program Files\Java\jdk-22 (jdk installation path)
   - PATH &rarr; C:\Program Files (x86)\Java\jre-1.8\bin (jre installation path)

## JSON Files
The file ```src\main\resources\tasks.json``` stores the individual tasks. Follow the instructions under 'Creating Your Own Version' to use the randomizer for your own tasks!
The file ```src\main\resources\items.json``` contains all the items available in the game and the paths to their images.
The file ```src\main\resources\maps.json``` contains all the maps available in the game.
The file ```src\main\resources\penalties.json``` contains all penalties.
The file ```src\main\resources\settings.json``` contains all necessary settings.

## Contributors
👩‍💻 **[blnpurple](https://github.com/blnpurple)** (Challenges)
👨‍💻 **[Canoob](https://github.com/canoobi)** (Challenge Generator)

<p align="right">(<a href="#readme-top">back to top</a>)</p>
