package Classloader;

import java.io.*;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class Classloader {

    /*
     * 1. Lädt die verschlüsselte Klasse als Byte-Array.
     *    - Liest die Datei "<className>.hex", die den Bytecode als Hex-String enthält.
     *    - Dekodiert den Hex-String in ein Byte-Array.
     *    - Entschlüsselt das Byte-Array mit einer XOR-Operation.
     */
    private byte[] findClassBytes(String className) {
        try {
            System.out.println("  load bytes from: " + className + ".hex");

            // 1. Hex-Datei lesen und als String laden
            String content = readString(className + ".hex");

            // 2. Hex-String in ein Byte-Array konvertieren
            // Jede Hex-Zeichenkombination (z. B. "4F") wird in ein Byte dekodiert
            byte[] xorBytes = Hex.decodeHex(content.toCharArray());

            // 3. Byte-Array mit XOR-Schlüssel (0xCC) entschlüsseln
            byte[] classBytes = xorBytes(xorBytes, 0xCC);

            // 4. Entschlüsseltes Byte-Array zurückgeben
            return classBytes;
        } catch (IOException | DecoderException e) {
            // Fehler beim Lesen der Datei oder Dekodieren des Hex-Strings behandeln
            throw new IllegalStateException("Can't read byte array for class " + className, e);
        }
    }

    /*
     * Liest den Hex-String aus der Datei.
     * -----------------------------------
     * - Öffnet die angegebene Datei im aktuellen Verzeichnis.
     * - Liest die Datei zeilenweise (erwartet nur eine Zeile mit Hex-String).
     * - Gibt die Datei als String zurück.
     */
    private String readString(String filename) throws IOException {
        // Erstellt einen BufferedReader, um die Datei effizient zeilenweise zu lesen.
        BufferedReader reader = new BufferedReader(new FileReader(new File(".", filename)));

        // Liest die erste Zeile (Hex-String), die die verschlüsselten Daten enthält.
        String content = reader.readLine();

        // Schließt den Reader, um Ressourcen freizugeben.
        reader.close();

        // Gibt den gelesenen Hex-String zurück.
        return content;
    }

    /*
     * Führt eine XOR-Operation auf einem Byte-Array durch.
     * ---------------------------------------------------
     * - Wendet die XOR-Operation auf jedes Byte des Arrays an.
     * - Verwendet dabei den angegebenen Schlüssel.
     * - Gibt das entschlüsselte Byte-Array zurück.
     */
    private byte[] xorBytes(byte[] bytes, int key) {
        // Erstellt ein neues Byte-Array, das die entschlüsselten Bytes speichern wird.
        byte[] result = new byte[bytes.length];

        // Iteriert über jedes Byte im Eingabe-Array.
        for (int i = 0; i < bytes.length; i++) {
            // Führt die XOR-Operation für das aktuelle Byte durch.
            result[i] = xorByte(bytes[i], key);
        }

        // Gibt das entschlüsselte Byte-Array zurück.
        return result;
    }

    /*
     * Führt eine XOR-Operation auf einem einzelnen Byte durch.
     * --------------------------------------------------------
     * - Nimmt ein Byte (`a`) und einen Schlüssel (`key`) als Eingabe.
     * - Führt die XOR-Operation durch: Ergebnis = a XOR key.
     * - Gibt das Ergebnis als Byte zurück.
     */
    private byte xorByte(byte a, int key) {
        // Konvertiert das Byte in einen Integer (für sichere Operationen).
        int ia = a;

        // Führt die XOR-Operation zwischen dem Byte und dem Schlüssel durch.
        int r = ia ^ key;

        // Konvertiert das Ergebnis zurück in ein Byte und gibt es zurück.
        return (byte) r;
    }
}
