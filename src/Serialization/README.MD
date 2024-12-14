# **Serialization and Deserialization in Java**

## **1. Einführung**

### **Was ist Serialization?**
- Serialization ist der Prozess der Konvertierung eines Objekts in einen **Bytestream**.
- Der Bytestream kann in einer Datei gespeichert, über ein Netzwerk versendet oder in einer Datenbank persistiert werden.

### **Was ist Deserialization?**
- Deserialization ist der umgekehrte Prozess: Der Bytestream wird gelesen und in das ursprüngliche Objekt zurückkonvertiert.

---

## **2. Warum Serialisierung verwenden?**

### **Anwendungsfälle:**
1. **Datenpersistenz**:
   - Objekte können in Dateien oder Datenbanken gespeichert und später wiederhergestellt werden.

2. **Datenübertragung**:
   - Objekte können über Netzwerke gesendet werden, z. B. zwischen Client und Server.

3. **Caching**:
   - Serialisierte Objekte können im Cache gespeichert werden, um Wiederholungen teurer Operationen zu vermeiden.

### **Vorteile:**
- **Portabilität**:
  - Ein serialisiertes Objekt kann zwischen verschiedenen Java-Programmen und sogar über verschiedene Plattformen hinweg verwendet werden.
- **Automatisierung**:
  - Java kümmert sich um die Konvertierung und Rekonstruktion der Objekte, ohne dass Entwickler sich um die Details des Bytestreams kümmern müssen.

---

## **3. Voraussetzungen für Serialisierung in Java**

### **1. Implementierung des Interfaces `Serializable`**
- Eine Klasse muss das Marker-Interface `java.io.Serializable` implementieren, damit ihre Objekte serialisiert werden können.
- Das Interface hat keine Methoden – es dient nur als Kennzeichnung.

### **2. `serialVersionUID`**
- Ein statisches Feld, das die Version des Objekts definiert.
- Wird verwendet, um sicherzustellen, dass die Serialisierung und Deserialisierung kompatibel sind.
- Wenn sich die Klasse ändert, aber die `serialVersionUID` gleich bleibt, können alte serialisierte Objekte weiterhin deserialisiert werden.

### **3. Einschränkungen**
- Nicht alle Objekte können serialisiert werden:
  - Transiente Felder (`transient`) und statische Felder werden nicht serialisiert.
  - Objekte, die nicht `Serializable` implementieren, verursachen Fehler.

---

## **4. Funktionsweise: Serialization**

### **Codebeispiel**

#### **1. Erstellung eines Objekts und Speicherung**
```java
try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.ser"))) {
    Person person = new Person("Alice", 25); // Objekt erstellen
    oos.writeObject(person); // Objekt serialisieren und in Datei speichern
    System.out.println("Object serialized: " + person);
} catch (IOException e) {
    e.printStackTrace();
}
