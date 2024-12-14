package HMAC;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Hmac {
    public static void main(String[] args) {
        try {
            /*
             * 1. Geheimen Schlüssel aus Properties-Datei lesen
             * ------------------------------------------------
             * Wir verwenden die Java-Klasse `Properties`, um einen geheimen Schlüssel
             * aus einer Konfigurationsdatei (z. B. "security.properties") zu laden.
             *
             * Was ist `Properties`?
             * ---------------------
             * - `Properties` ist eine Unterklasse von `Hashtable` und wird in Java verwendet,
             *   um Schlüssel-Wert-Paare zu speichern.
             * - Typischer Anwendungsfall:
             *   Konfigurationswerte in Dateien wie "key=value" zu speichern.
             *
             * Wie funktioniert `Properties.load()`?
             * -------------------------------------
             * - Liest ein Schlüssel-Wert-Paar aus einer Datei, die als Eingabestream bereitgestellt wird.
             * - Es speichert diese Paare intern in einer Tabelle.
             * - Beispiel für eine Datei:
             *       key=mySecretKey123
             *       anotherKey=someValue
             * - Auf die Werte kann dann mit `getProperty(keyName)` zugegriffen werden.
             */
            String secretKey = readSecretKey("security.properties", "key");

            /*
             * 2. Algorithmus definieren
             * -------------------------
             * Der Algorithmus "HmacSHA1" gibt an, dass wir HMAC mit der Hash-Funktion SHA-1 verwenden.
             */
            final String ALGORITHM = "HmacSHA1";

            /*
             * 3. Schlüssel als Byte-Array vorbereiten
             * ---------------------------------------
             * Der gelesene Schlüssel (`secretKey`) wird mit der Methode `getBytes()` in ein Byte-Array
             * umgewandelt, da kryptografische APIs wie `Mac` mit Byte-Daten arbeiten.
             */
            byte[] secretKeyBytes = secretKey.getBytes();

            /*
             * 4. SecretKeySpec erstellen
             * --------------------------
             * SecretKeySpec wandelt das Byte-Array (`secretKeyBytes`) in ein Schlüsselobjekt um, das
             * für kryptografische Klassen wie `Mac` geeignet ist.
             */
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, ALGORITHM);

            /*
             * 5. Mac-Instanz erstellen und initialisieren
             * -------------------------------------------
             * - Mac: Führt die HMAC-Berechnung durch.
             * - Die Instanz wird mit `getInstance(ALGORITHM)` für den Algorithmus "HmacSHA1" erstellt.
             * - Mit `mac.init(secretKeySpec)` wird der Algorithmus mit dem geheimen Schlüssel verknüpft.
             */
            Mac mac = Mac.getInstance(ALGORITHM);
            mac.init(secretKeySpec);

            /*
             * 6. Eingabedaten für die HMAC-Berechnung
             * ---------------------------------------
             * Die Daten, die signiert werden sollen, werden ebenfalls als Byte-Array vorbereitet.
             */
            String data = "Hello, World!";
            byte[] dataBytes = data.getBytes();

            /*
             * 7. HMAC-Wert berechnen
             * ----------------------
             * Die Methode `doFinal(byte[] input)` berechnet den HMAC-Wert für die Eingabedaten.
             */
            byte[] hmacResult = mac.doFinal(dataBytes);

            /*
             * 8. HMAC-Wert ausgeben (als Hex-String)
             * --------------------------------------
             * Der HMAC-Wert wird als Hexadezimal-String ausgegeben, um ihn besser lesbar zu machen.
             */
            System.out.println("HMAC (Hex-Format): " + bytesToHex(hmacResult));

        } catch (Exception e) {
            // Fehlerbehandlung: Gibt Probleme bei Datei-I/O, Algorithmus oder Schlüssel aus.
            e.printStackTrace();
        }
    }

    /*
     * Liest den geheimen Schlüssel aus einer Properties-Datei.
     * --------------------------------------------------------
     * Parameter:
     * - filePath: Der Pfad zur Properties-Datei, z. B. "security.properties".
     * - keyName: Der Name des Schlüssels, der aus der Datei abgerufen werden soll, z. B. "key".
     *
     * Was passiert in dieser Methode?
     * 1. Eine Instanz der Klasse `Properties` wird erstellt.
     * 2. Mit `load(FileInputStream)` wird der Inhalt der Datei gelesen.
     * 3. Mit `getProperty(String keyName)` wird der Wert für den angegebenen Schlüssel abgerufen.
     */
    private static String readSecretKey(String filePath, String keyName) throws IOException {
        // 1. Erstellen einer Properties-Instanz.
        Properties properties = new Properties();

        /*
         * 2. Datei öffnen und laden.
         * --------------------------
         * - `FileInputStream`: Öffnet die Datei im Lesemodus.
         * - `Properties.load(InputStream)`: Liest die Datei zeilenweise und speichert die Schlüssel-Wert-Paare. schlüsselwertpaar wäre key = 123124 in der datei und ich filteere mit getproperty (keyname ) nach dem wie das in der datei benannt worden ist
         */
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis); // Schlüssel-Wert-Paare aus der Datei laden.
        }
        /*
         * 3. Schlüssel abrufen.
         * ---------------------
         * - `getProperty(String key)`: Gibt den Wert zurück, der dem angegebenen Schlüssel zugeordnet ist.
         * - Wenn der Schlüssel nicht existiert, wird `null` zurückgegeben.
         */
        String key = properties.getProperty(keyName);

        // 4. Fehlerbehandlung: Falls der Schlüssel fehlt, eine Ausnahme auslösen.
        if (key == null) {
            throw new IllegalArgumentException("Key '" + keyName + "' not found in file: " + filePath);
        }

        // 5. Den gefundenen Schlüssel zurückgeben.
        return key;
    }

    /*
     * Konvertiert ein Byte-Array in einen Hexadezimal-String.
     * -------------------------------------------------------
     * - Wandelt jedes Byte in einen lesbaren Hex-Wert um.
     * - Nützlich für die Darstellung von HMAC-Werten.
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            // Byte in Hexadezimalwert umwandeln (0xff entfernt das Vorzeichen).
            String hex = Integer.toHexString(0xff & b);
            // Führende Null hinzufügen, falls nötig.
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}