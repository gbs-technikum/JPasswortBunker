jPasswortBunker Zielvereinbarung
================================

**Projektmitglieder**
_____________________
* Michael Kopp
* Marcel Eglseder
* Günther Wagenhuber

**Zweck**
_________
Im Rahmen der Projektarbeit im Fach Programmieren wird eine Client Java-Anwendung erstellt, dokumentiert und vorgestellt. Die Anwendung mit der Bezeichnung jPasswortBunker soll eine Software zur sicheren Verwaltung von Passwörtern darstellen.

**Ziel**
________
Der Anwender soll über eine intuitive grafische Oberfläche in der Lage sein, beliebige Zugänge bzw. Anmelde/Login-Daten zu verwalten. Diese Daten werden zusammen mit begleitenden Informationen wie URLs und Beschreibungen in einer lokalen Datenbank gespeichert. 
Das Speichern der sensiblen Daten wie z.B. Passwörter soll durch kryptografische Funktionen abgesichert werden.

Dazu vergibt der Anwender selbst ein zentrales Schlüssel-Passwort, welches im Folgenden vom Programm für die Ver- und Entschlüsselung, aller mit der Anwendung verwalteten Zugänge, verwendet wird. 

Zudem soll das Programm eine Funktionalität für das automatische Generieren sicherer Passwörter zu Verfügung stellen.

Passwörter sollen mittels der Zwischenablage des Betriebssystems aus dem Passwortmanager in Anwendungen übertragen werden können (Copy/Paste). Die Zwischenablage soll sich nach einer einstellbaren Zeit automatisch bereinigen.

Bei Änderung eines Passwortes, soll mittels einer Historien-Funktion, auf das Vorgänger-Passwort zugegriffen werden können. Ebenso soll beim Löschen eines kompletten Datensatzes mittels Historien-Funktion auf den gelöschten Eintrag zugegriffen werden können.

**Strukturierte objektorientierte Programmierung**
__________________________________________________
Das Programm wird so entwickelt, dass es
*durch seine Plattformunabhängigkeit auf möglichst vielen Systemen ohne aufwendige Installation lauffähig, lediglich eine Java Laufzeitumgebung  ab Version 1.7 wird benötigt um alle Funktionen des Package Java Cryptography Extension bereitzustellen
*Passwörter werden verschlüsselt in einer lokalen SQL Datenbank abgelegt
*eine intuitive Oberfläche auf Basis von JavaFX besitzt
*modular aufgebaut ist (GUI / Logik / Datenbank)
*die in der Zielvereinbarung beschriebenen Funktionen erfüllt

**Datenbank**
_____________
* Strukturierte und organisierte Datenerfassung der Zugangsdaten mit folgenden Inhalten:
* Bezeichnung des Zugangs
* Benutzername
* Passwort
* Url für Websites
* Zusätzliche Anmerkungen

**Ausschluss**
______________
* Die Anwendung wird das direkte Übertragen von Zugangsdaten in Browser nicht unterstützen
