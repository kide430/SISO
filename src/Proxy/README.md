# **Proxy Pattern und Validierung mit Java**

## **1. Einführung**

### **Was ist das Proxy Pattern?**
Das Proxy-Muster ist ein Strukturmuster in der Softwareentwicklung, das ein Objekt durch ein anderes ersetzt. Der Proxy agiert als Stellvertreter und kann:
- **Eingaben validieren oder verändern** (wie in diesem Beispiel).
- **Zugriffe steuern** (z. B. Authentifizierung).
- **Funktionalitäten hinzufügen** (z. B. Caching, Logging).

In diesem Beispiel dient der **Validator-Proxy** als Filter, der Eingaben überprüft, bevor sie an die tatsächliche Implementierung weitergeleitet werden.

---

## **2. Warum Proxy verwenden?**

### **Anwendungsfälle:**
1. **Eingabevalidierung**:
   - Verhindert, dass ungültige Daten in die tatsächliche Implementierung gelangen.
2. **Sicherheitskontrolle**:
   - Kann den Zugriff basierend auf Benutzerrollen oder Berechtigungen steuern.
3. **Funktionserweiterung**:
   - Fügt zusätzliche Funktionalitäten hinzu, ohne die Hauptimplementierung zu ändern.

---

## **3. Wie funktioniert das Proxy-Muster in diesem Beispiel?**

1. **Interface `ArticleService`**:
   - Definiert die Methode `createArticle`, die von mehreren Klassen implementiert wird.

2. **Tatsächliche Implementierung (`ArticleServiceImpl`)**:
   - Führt die Logik für das Erstellen eines Artikels aus (z. B. Speichern von Daten).

3. **Proxy (`ArticleServiceValidator`)**:
   - Implementiert ebenfalls `ArticleService`, überprüft jedoch die Eingaben (Name und Beschreibung) mit regulären Ausdrücken.
   - Leitet nur gültige Eingaben an die tatsächliche Implementierung weiter.

---

## **4. Beispiel: Eingabevalidierung**

### **Reguläre Ausdrücke**
1. **Name**:
   - Muss zwischen 4 und 64 Zeichen lang sein.
   - Erlaubt Buchstaben, Ziffern und Leerzeichen.
   - RegEx: `^[a-zA-Z0-9 ]{4,64}$`

2. **Beschreibung**:
   - Kann optional sein (darf `null` sein).
   - Darf bis zu 256 Zeichen lang sein.
   - Erlaubt Buchstaben, Ziffern, Leerzeichen, Kommas, Punkte und Klammern.
   - RegEx: `^[a-zA-Z0-9,:.\\-)( ]{0,256}$`

---

## **5. Polymorphismus: Warum Interfaces verwenden?**

- **Flexibilität**:
  - Durch die Verwendung eines Interfaces (`ArticleService`) kann der Proxy auf jede Implementierung zugreifen, die dieses Interface implementiert.
- **Austauschbarkeit**:
  - Der Proxy (`ArticleServiceValidator`) und die tatsächliche Implementierung (`ArticleServiceImpl`) können problemlos ausgetauscht werden.
- **Erweiterbarkeit**:
  - Neue Klassen können das Interface implementieren, ohne den Proxy ändern zu müssen.

---

## **6. Beispielaufruf**

```java
// Konkrete Implementierung des ArticleService
ArticleService realService = new ArticleServiceImpl();

// Proxy (Validator) wird mit der tatsächlichen Implementierung initialisiert
ArticleService proxyService = new ArticleServiceValidator(realService);

// Erfolgreicher Aufruf
proxyService.createArticle("Valid Name", "This is a valid description.");

// Fehlerhafter Aufruf: Ungültiger Name
proxyService.createArticle("Bad", "Valid description");

// Fehlerhafter Aufruf: Ungültige Beschreibung
proxyService.createArticle("Valid Name", "Invalid description $$$$!!!");