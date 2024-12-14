# **SHA-256 Hashing und Base64-Encoding in Java**

## **1. Einführung**

### **Was ist Hashing?**
Hashing ist der Prozess, bei dem Eingabedaten (z. B. ein String oder eine Datei) durch einen Algorithmus verarbeitet werden, um einen festen, eindeutigen Wert (den Hash) zu erzeugen. Hashes haben folgende Eigenschaften:
- **Feste Länge**: Der Hash-Wert hat immer die gleiche Länge, unabhängig von der Eingabegröße.
- **Deterministisch**: Die gleiche Eingabe erzeugt immer denselben Hash.
- **Irreversibel**: Der ursprüngliche Wert kann nicht aus dem Hash berechnet werden.

### **Was ist Base64-Encoding?**
Base64-Encoding konvertiert Binärdaten (wie den Hash) in einen lesbaren String aus ASCII-Zeichen. Dies ist nützlich für:
- **Speicherung**: Einfach in Dateien oder Datenbanken zu speichern.
- **Übertragung**: Sicheres Versenden von Daten über Netzwerke.

---

## **2. Verwendete Technologien**

### **SHA-256**
- Ein kryptografischer Hash-Algorithmus, der 256-Bit-Hashes erzeugt.
- Wird häufig für Sicherheitszwecke verwendet (z. B. Datenintegrität, Passwortsicherheit).

### **Base64-Encoding**
- Konvertiert den Binär-Hash in einen String aus lesbaren Zeichen.

---

## **3. Funktionsweise des Codes**

### **1. Hash eines Strings berechnen**
- Der SHA-256-Hash eines Eingabestrings wird berechnet.
- Der Hash wird anschließend Base64-kodiert.

### **2. Dateiinhalt hashen**
- Der Inhalt einer Datei wird gelesen.
- Der SHA-256-Hash des Inhalts wird berechnet und Base64-kodiert.

---

## **4. Beispielaufrufe**

### **String-Hash**
```java
String hash = HashingExample.sha256AsBase64String("Hello, World!");
System.out.println(hash);
