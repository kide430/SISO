# **Asymmetrische Verschlüsselung mit RSA**

## **1. Einführung**

### **Was ist asymmetrische Verschlüsselung?**
- Asymmetrische Verschlüsselung verwendet ein **Schlüsselpaar**:
  1. **Öffentlicher Schlüssel (Public Key)**:
     - Wird verwendet, um Daten zu **verschlüsseln**.
     - Kann frei geteilt werden.
  2. **Privater Schlüssel (Private Key)**:
     - Wird verwendet, um die verschlüsselten Daten zu **entschlüsseln**.
     - Muss geheim gehalten werden.
- Diese Technik wird häufig in sicheren Kommunikationsprotokollen wie **HTTPS**, **TLS**, und **SSH** eingesetzt.

---

## **2. RSA (Rivest-Shamir-Adleman)**

### **Was ist RSA?**
- RSA ist ein weit verbreiteter asymmetrischer Verschlüsselungsalgorithmus.
- Es basiert auf der mathematischen Schwierigkeit, sehr große Zahlen zu faktorisieren.
- **Eigenschaften von RSA**:
  - Unterstützt **Schlüssellängen** von typischerweise 2048 oder 4096 Bit.
  - Wird für die Verschlüsselung kleiner Datenmengen oder den sicheren Austausch symmetrischer Schlüssel verwendet.

---

## **3. Wichtige Begriffe**

### **1. Öffentlicher Schlüssel**
- Der öffentliche Schlüssel wird verwendet, um Daten zu verschlüsseln.
- Da er keine Entschlüsselung erlaubt, kann er frei geteilt werden.

### **2. Privater Schlüssel**
- Der private Schlüssel wird verwendet, um verschlüsselte Daten zu entschlüsseln.
- **Wichtig**: Der private Schlüssel muss sicher aufbewahrt werden, da er die einzige Möglichkeit ist, die verschlüsselten Daten zu entschlüsseln.

### **3. Key Factory**
- Die Klasse `KeyFactory` wird verwendet, um Schlüssel aus codierten Daten zu erzeugen:
  - **PKCS#8**: Format für private Schlüssel.
  - **X.509**: Format für öffentliche Schlüssel.

### **4. Cipher**
- Die Klasse `Cipher` ist das zentrale Tool in Java für Verschlüsselung und Entschlüsselung.
- Funktionen:
  - Initialisieren mit **ENCRYPT_MODE** (Verschlüsselung) oder **DECRYPT_MODE** (Entschlüsselung).
  - Verarbeiten von Daten in Blöcken oder als Ganzes.

---

## **4. Funktionsweise von RSA**

### **Verschlüsselung**
1. Der öffentliche Schlüssel wird verwendet, um die Daten zu verschlüsseln.
2. Das `Cipher`-Objekt wird mit **ENCRYPT_MODE** initialisiert.
3. Die Eingabedaten werden verschlüsselt und in einer Datei gespeichert.

### **Entschlüsselung**
1. Der private Schlüssel wird verwendet, um die verschlüsselten Daten zu entschlüsseln.
2. Das `Cipher`-Objekt wird mit **DECRYPT_MODE** initialisiert.
3. Die verschlüsselten Daten werden entschlüsselt und als Klartext zurückgegeben.

---

## **5. Codebeispiel**

### **Verschlüsselung**
```java
EncryptedFile encryptedFile = new EncryptedFile(publicKey, privateKey);

String filename = "encryptedData.rsa";
byte[] data = "Sensitive Information".getBytes();
encryptedFile.saveEncrypted(filename, data);
