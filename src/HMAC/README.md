# **Was ist `Mac` und warum wird es verwendet?**

## **1. Einführung**
`Mac` steht für **Message Authentication Code**. Es ist eine kryptografische Funktion, die dazu dient:
- **Datenintegrität** sicherzustellen: Prüft, ob die Daten während der Übertragung unverändert geblieben sind.
- **Authentizität** der Daten zu garantieren: Verifiziert, dass die Daten von einer vertrauenswürdigen Quelle stammen.

`Mac` kombiniert **Daten** und einen **geheimen Schlüssel** mithilfe eines spezifischen Algorithmus (z. B. `HmacSHA1`), um einen einzigartigen Code zu generieren. Nur jemand, der den Schlüssel kennt, kann diesen Code korrekt erzeugen und verifizieren.

---

## **2. Warum wird ein `Mac` verwendet?**

### **Probleme ohne `Mac`**
1. **Man-in-the-Middle-Angriffe**:
   - Ein Angreifer könnte die Daten während der Übertragung verändern, ohne dass der Empfänger es bemerkt.
2. **Fälschung**:
   - Jemand könnte vorgeben, die Daten zu senden, obwohl sie von einer unautorisierten Quelle stammen.

### **Wie schützt ein `Mac`?**
- Der `Mac` generiert einen **Code**, der:
  - Basierend auf den Daten und einem geheimen Schlüssel berechnet wird.
  - Einzigartig für die spezifische Nachricht und den Schlüssel ist.
- Der Empfänger verwendet denselben geheimen Schlüssel, um den Code zu verifizieren:
  - **Daten korrekt?** Wenn der Code übereinstimmt, wurden die Daten nicht verändert.
  - **Daten manipuliert?** Wenn der Code nicht übereinstimmt, wurden die Daten verändert oder der Schlüssel ist falsch.

---

## **3. Wie funktioniert ein `Mac`?**

Ein `Mac` benötigt:

1. **Daten (die Nachricht)**:
   - Die Daten, die signiert und geschützt werden sollen, z. B. `Hello, World!`.

2. **Einen geheimen Schlüssel**:
   - Ein nur dem Sender und Empfänger bekannter Schlüssel, z. B. `mySecretKey123`.

3. **Einen Algorithmus**:
   - Der Algorithmus bestimmt, wie die Kombination aus Daten und Schlüssel verarbeitet wird, z. B. `HmacSHA1`.

**Ablauf:**
1. Der Sender erstellt einen Code (`MAC`) mit den Daten und dem geheimen Schlüssel.
2. Der Empfänger nimmt die empfangenen Daten und generiert mit dem gleichen Schlüssel einen neuen `MAC`.
3. Der empfangene `MAC` wird mit dem neu generierten `MAC` verglichen:
   - Stimmen die Codes überein, sind die Daten unverändert und authentisch.
   - Stimmen sie nicht überein, wurden die Daten manipuliert oder der Schlüssel ist falsch.

---

## **4. Warum reicht einfaches Hashing nicht aus?**

Eine einfache Hash-Funktion (wie `SHA-1`) schützt nicht vor Fälschungen:
- Ein Angreifer könnte den Hash einer gefälschten Nachricht berechnen und diesen mitsenden.
- Ohne einen geheimen Schlüssel kann der Empfänger nicht feststellen, ob der Hash manipuliert wurde.

### **Lösung:**
HMAC (Hash-based Message Authentication Code) kombiniert:
1. **Hashing** (wie SHA-1 oder SHA-256).
2. **Einen geheimen Schlüssel**.

Dadurch wird sichergestellt, dass nur jemand mit dem geheimen Schlüssel den richtigen `MAC` berechnen kann.

---

## **5. Eigenschaften eines `Mac`**

- **Sicher**:
  - Ohne den geheimen Schlüssel kann der Code nicht gefälscht werden.
- **Einzigartig**:
  - Selbst kleinste Änderungen an den Daten führen zu einem völlig anderen Code.
- **Effizient**:
  - Die Berechnung ist schnell und ressourcenschonend.

---

## **6. Wofür wird ein `Mac` verwendet?**

1. **API-Authentifizierung**:
   - Viele APIs verwenden HMAC, um Anfragen zu authentifizieren.
   - Der Client sendet den HMAC-Code mit seiner Anfrage. Der Server überprüft diesen Code, um die Anfrage zu validieren.

2. **Datenintegritätsprüfung**:
   - Sicherstellen, dass Dateien oder Datenpakete während der Übertragung nicht verändert wurden.

3. **Authentifizierung von Nachrichten**:
   - Verifizieren, dass eine Nachricht von einer vertrauenswürdigen Quelle stammt.

4. **Schlüsselbasierte Signaturen**:
   - HMAC wird oft verwendet, um Daten mit einem geheimen Schlüssel zu signieren.

---

## **7. Warum HMAC (Hash-based MAC)?**

HMAC ist eine spezielle Art von `Mac`, die Hash-Funktionen wie SHA-1 oder SHA-256 verwendet. Es ist besonders beliebt, weil:
1. **Flexibilität**:
   - Es unterstützt verschiedene Hash-Funktionen, z. B. SHA-1, SHA-256, SHA-512.
2. **Sicherheit**:
   - HMAC schützt vor bekannten Angriffen auf einfache Hash-Funktionen.
3. **Kompatibilität**:
   - Es ist in vielen kryptografischen Bibliotheken und APIs implementiert.

---

## **8. Beispiel: Sender und Empfänger**

### **Sender**
1. Nimmt die Originaldaten und einen geheimen Schlüssel.
2. Verwendet einen `Mac`, um einen Code zu generieren.
3. Sendet die Daten und den Code an den Empfänger.

### **Empfänger**
1. Nimmt die empfangenen Daten und denselben geheimen Schlüssel.
2. Verwendet einen `Mac`, um den Code neu zu generieren.
3. Vergleicht den empfangenen Code mit dem neu generierten Code:
   - Stimmen die Codes überein, sind die Daten unverändert und authentisch.
   - Stimmen sie nicht überein, wurden die Daten manipuliert.

---

## **9. Beispiel: HMAC mit Java**

```java
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacExample {
    public static void main(String[] args) throws Exception {
        // 1. Algorithmus definieren
        final String ALGORITHM = "HmacSHA1";

        // 2. Geheimer Schlüssel (z. B. aus einer Konfigurationsdatei)
        byte[] secretKeyBytes = "mySecretKey123".getBytes();

        // 3. SecretKeySpec erstellen
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, ALGORITHM);

        // 4. Mac-Instanz erstellen und initialisieren
        Mac mac = Mac.getInstance(ALGORITHM);
        mac.init(secretKeySpec);

        // 5. Daten, die signiert werden sollen
        byte[] dataBytes = "Hello, World!".getBytes();

        // 6. HMAC-Wert berechnen
        byte[] hmacResult = mac.doFinal(dataBytes);

        // 7. Ergebnis als Hex-String ausgeben
        System.out.println("HMAC: " + bytesToHex(hmacResult));
    }

    // Hilfsmethode: Byte-Array in Hex-String konvertieren
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
