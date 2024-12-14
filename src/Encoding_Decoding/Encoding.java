package Encoding_Decoding;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encoding {

    /*
     * Berechnet den SHA-256-Hash eines Eingabestrings und gibt ihn Base64-kodiert zurück.
     */
    public static String sha256AsBase64String(String message) {
        // 1. Überprüfen, ob die Eingabe null oder leer ist
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Message must not be null or empty!");
        }

        try {
            // 2. SHA-256-Algorithmus instanziieren
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");

            // 3. Eingabestring in UTF-8-Byte-Array konvertieren und dem Algorithmus übergeben
            algorithm.update(message.getBytes("UTF-8"));

            // 4. Hash berechnen (als Byte-Array)
            byte[] bytes = algorithm.digest();

            // 5. Base64-kodierten String aus dem Byte-Array erzeugen
            return Base64.getEncoder().encodeToString(bytes);

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            // 6. Falls der Algorithmus oder die Kodierung nicht unterstützt wird, Exception werfen
            throw new IllegalArgumentException("Error computing SHA-256 hash", e);
        }
    }

    /*
     * Berechnet den SHA-256-Hash des Inhalts einer Datei.
     * Der Hash wird als Base64-kodierter String zurückgegeben.
     */
    public static String sha256(File file) {
        // 1. Überprüfen, ob die Datei null ist
        if (file == null) {
            throw new IllegalArgumentException("File is null!");
        }

        // 2. Überprüfen, ob die Datei existiert
        if (!file.exists()) {
            throw new IllegalArgumentException("File " + file.getAbsolutePath() + " does not exist!");
        }

        // 3. Dateiinhalt lesen und SHA-256-Hash berechnen
        try (InputStream in = new FileInputStream(file)) {
            // Dateigröße ermitteln und Byte-Array erstellen
            int length = (int) file.length();
            byte[] content = new byte[length];

            // Dateiinhalt in das Byte-Array lesen
            in.read(content);

            // Hash berechnen (delegiert an eine andere Methode)
            return sha256(content);
        } catch (IOException e) {
            // Fehler beim Lesen der Datei behandeln
            throw new IllegalStateException("Can't read file: " + file.getAbsolutePath(), e);
        }
    }

    /*
     * Berechnet den SHA-256-Hash eines Byte-Arrays.
     * Gibt den Base64-kodierten Hash zurück.
     */
    public static String sha256(byte[] content) {
        try {
            // SHA-256-Algorithmus instanziieren
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");

            // Eingabe-Bytes dem Algorithmus übergeben
            algorithm.update(content);

            // Hash berechnen und als Byte-Array zurückgeben
            byte[] bytes = algorithm.digest();

            // Byte-Array in Base64 kodieren und zurückgeben
            return Base64.getEncoder().encodeToString(bytes);

        } catch (NoSuchAlgorithmException e) {
            // Fehler beim Zugriff auf den SHA-256-Algorithmus
            throw new IllegalArgumentException("Error computing SHA-256 hash", e);
        }
    }
}
