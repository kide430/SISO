# **Verschlüsselung in Java**

Diese README beschreibt zwei wichtige Verschlüsselungstechniken: **Symmetrische Verschlüsselung mit AES** und **Asymmetrische Verschlüsselung mit RSA**. Es werden die Grundlagen, die Funktionsweise und Codebeispiele erläutert.

---

## **1. Symmetrische Verschlüsselung**

### **Was ist symmetrische Verschlüsselung?**
- Symmetrische Verschlüsselung verwendet einen einzigen **Schlüssel**, um Daten zu **verschlüsseln** und **entschlüsseln**.
- Beispiele:
  - **AES (Advanced Encryption Standard)**
  - DES (veraltet und unsicher).

---

### **AES (Advanced Encryption Standard)**

#### **Eigenschaften von AES**
- **Blockbasierter Algorithmus**: Verarbeitet Daten in Blöcken von 128 Bit (16 Bytes).
- **Schlüsselgrößen**: 128, 192, oder 256 Bit.
- **Anwendung**: In WPA2, TLS, VPNs und anderen sicherheitskritischen Anwendungen.

#### **CTR-Modus (Counter Mode)**
- AES wird im **CTR-Modus** verwendet:
  - Verwendet einen **Zähler (Counter)** und einen **Initialisierungsvektor (IV)**, um Daten sicher zu verschlüsseln.
  - Bietet eine Art Stromverschlüsselung.

---

### **Begriffe**

#### **1. Schlüssel**
- Ein geheimer Wert, der für Verschlüsselung und Entschlüsselung benötigt wird.
- Im Code: Ein hexadezimaler String (32 Zeichen für 256 Bit).

#### **2. Initialisierungsvektor (IV)**
- Eine zufällige Byte-Sequenz, die für den ersten Verschlüsselungsschritt genutzt wird.
- Verhindert, dass gleiche Eingabedaten zu gleichen verschlüsselten Ergebnissen führen.

#### **3. Cipher**
- `Cipher` ist die zentrale Klasse in Java für Verschlüsselungsoperationen:
  - Initialisierung im Modus **ENCRYPT_MODE** oder **DECRYPT_MODE**.
  - Unterstützt Algorithmen wie AES oder RSA.

---

### **Funktionsweise von AES**

1. **Verschlüsselung**:
   - Der Schlüssel und der IV werden in Byte-Arrays konvertiert.
   - `Cipher` wird im **ENCRYPT_MODE** initialisiert.
   - Daten werden verschlüsselt und in einer Datei gespeichert.

2. **Entschlüsselung**:
   - Verschlüsselte Datei wird gelesen.
   - `Cipher` wird im **DECRYPT_MODE** initialisiert.
   - Daten werden entschlüsselt und zurückgegeben.

---

### **Codebeispiel**

#### **Verschlüsselung**
```java
EncryptedFile encryptedFile = new EncryptedFile("1234567890abcdef1234567890abcdef", 
                                                "abcdef1234567890abcdef1234567890");

String filename = "encryptedData.aes";
byte[] data = "Sensitive Information".getBytes();
encryptedFile.saveEncrypted(filename, data);





