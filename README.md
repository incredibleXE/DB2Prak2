# DB2Prak2
## Wichtiges vorweg
Das Projekt ist nicht fertig. Es dient lediglich dem Zweck, zu verstehen, wie man mit Datenbanken kommuniziert.

## Was ist das und welches Ziel hat das Projekt
Diese Projekt ist eine Aufgabe der Westsächsichen Hochschule Zwickau (http://fh-zwickau.de) im Rahmen der Informatikveranstaltung Datenbanken 2 im vierten Semster. 
Das Ziel ist es eine Verbindung mit einem Microsoft Datenbankserver aufzubauen.

## Installation
Das Projekt ist noch lange nicht fertig, funktioniert aber schon.
1. Projekt in Eclipse öffnen
2. Logindaten in Databean anpassen
3. Projekt starten

## Anmeldedaten ändern
### Möglichkeit 1
```javascript
Databean bean = new Databean("localhost","databasename","user","passwd",DATABASE_TYPE.MsSQL);
```

### Möglichkeit 2
```javascript
Databean bean = new Databean();
bean.setHost("host");
bean.setDatabase("database");
bean.setUser("username");
bean.setPasswd("passwd");
```

### Microsoft SQL Server
Wenn ihr versucht mit dem Microsoft SQL Datenbankserver zu reden, gibt es noch die Möglichkeit die Authentifikation über die integrierte Windowsanmeldung aufzubauen. Um das zu aktivieren, müsst ihr noch folgenden Code anpassen.
```javascript
bean.setWinlogon(true);
// braucht ihr dann nicht mehr
bean.setUser("");
bean.setPasswd("");
```

## Lizenz
Ihr könnt mein Projekt gern verwenden wie ihr möchtet.

## Fragen und Probleme
Wenn ihr fragen habt, schreibt mich einfach an:
[incrediblexe92@gmail.com](mailto:incrediblexe92@gmail.com)