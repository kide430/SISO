package Verschlüsselung;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;

public class Symmetrisch {

    /**
     * Diese Klasse implementiert symmetrische Verschlüsselung und Entschlüsselung
     * mit dem AES-Algorithmus im CTR-Modus (Counter Mode).
     *
     * Sie enthält Methoden zum Verschlüsseln und Entschlüsseln von Dateien.
     * Der AES-Schlüssel und der Initialisierungsvektor (IV) müssen als
     * 32-stellige hexadezimale Strings übergeben werden (256 Bit).
     */

        public class EncryptedFile {

            // Verschlüsselungs-Engine, die den AES-Algorithmus implementiert
            private final Cipher cipher;

            // Geheimschlüssel für AES-Verschlüsselung
            private final SecretKey key;

            // Initialisierungsvektor, der für die erste Verschlüsselung verwendet wird
            private final IvParameterSpec iv;

            /**
             * Konstruktor: Initialisiert das Cipher-Objekt, den Schlüssel und den IV.
             *
             * @param keyString Hexadezimaler String für den AES-Schlüssel (32 Zeichen, 256 Bit)
             * @param ivString  Hexadezimaler String für den Initialisierungsvektor (32 Zeichen, 128 Bit)
             */
            public EncryptedFile(String keyString, String ivString) {
                // Validierung des Schlüssels und IV
                if (keyString == null || keyString.length() != 32)
                    throw new IllegalArgumentException("Invalid key string! Der Schlüssel muss 32 Zeichen lang sein (256 Bit).");

                if (ivString == null || ivString.length() != 32)
                    throw new IllegalArgumentException("Invalid init vector string! Der IV muss 32 Zeichen lang sein (128 Bit).");

                try {
                    // Initialisiert den Cipher mit AES im CTR-Modus ohne Padding
                    cipher = Cipher.getInstance("AES/CTR/NoPadding");

                    // Konvertiert den Schlüssel-String in ein Byte-Array und erzeugt das Schlüsselobjekt
                    byte[] keyBytes = Hex.decodeHex(keyString.toCharArray());
                    key = new SecretKeySpec(keyBytes, "AES");

                    // Konvertiert den IV-String in ein Byte-Array und erzeugt das IV-Objekt
                    byte[] ivBytes = Hex.decodeHex(ivString.toCharArray());
                    iv = new IvParameterSpec(ivBytes);

                } catch (NoSuchAlgorithmException | NoSuchPaddingException | DecoderException e) {
                    // Fehler bei der Initialisierung
                    throw new IllegalStateException("Can't initialize EncryptedFile object!", e);
                }
            }

            /**
             * Verschlüsselt Daten und speichert sie in einer Datei.
             *
             * @param filename Der Name der Datei, in der die verschlüsselten Daten gespeichert werden sollen.
             * @param data     Die zu verschlüsselnden Daten als Byte-Array.
             */
            public void saveEncrypted(String filename, byte[] data) {
                if (filename == null)
                    throw new IllegalArgumentException("Invalid filename. Der Dateiname darf nicht null sein!");

                if (data == null)
                    throw new IllegalArgumentException("Invalid data! Die zu verschlüsselnden Daten dürfen nicht null sein.");

                try {
                    // Initialisiert den Cipher im ENCRYPT_MODE
                    cipher.init(Cipher.ENCRYPT_MODE, key, iv);

                    // Verschlüsselt die Eingabedaten
                    byte[] cipherText = cipher.doFinal(data);

                    // Speichert die verschlüsselten Daten in der Datei
                    save(filename, cipherText);
                } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
                    // Fehler bei der Verschlüsselung
                    throw new IllegalStateException("Unable to encrypt data!", e);
                }
            }

            /**
             * Liest verschlüsselte Daten aus einer Datei und entschlüsselt sie.
             *
             * @param filename Der Name der Datei, aus der die verschlüsselten Daten gelesen werden sollen.
             * @return Die entschlüsselten Daten als Byte-Array.
             */
            public byte[] loadEncrypted(String filename) {
                if (filename == null)
                    throw new IllegalArgumentException("Invalid filename! Der Dateiname darf nicht null sein.");

                try {
                    // Liest die verschlüsselten Daten aus der Datei
                    byte[] data = load(filename);

                    // Initialisiert den Cipher im DECRYPT_MODE
                    cipher.init(Cipher.DECRYPT_MODE, key, iv);

                    // Entschlüsselt die geladenen Daten
                    return cipher.doFinal(data);
                } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
                    // Fehler bei der Entschlüsselung
                    throw new IllegalStateException("Unable to decrypt data!", e);
                }
            }

            /**
             * Hilfsmethode: Speichert ein Byte-Array in einer Datei.
             *
             * @param filename Der Name der Datei, in der die Daten gespeichert werden sollen.
             * @param data     Die zu speichernden Daten als Byte-Array.
             */
            private void save(String filename, byte[] data) throws IllegalStateException {
                try (FileOutputStream fos = new FileOutputStream(filename)) {
                    fos.write(data);
                } catch (IOException e) {
                    // Fehler beim Schreiben der Datei
                    throw new IllegalStateException("Unable to save data to file!", e);
                }
            }

            /**
             * Hilfsmethode: Lädt ein Byte-Array aus einer Datei.
             *
             * @param filename Der Name der Datei, aus der die Daten geladen werden sollen.
             * @return Die geladenen Daten als Byte-Array.
             */
            private byte[] load(String filename) throws IllegalStateException {
                try (FileInputStream fis = new FileInputStream(filename)) {
                    return fis.readAllBytes();
                } catch (IOException e) {
                    // Fehler beim Lesen der Datei
                    throw new IllegalStateException("Unable to load data from file!", e);
                }
            }
        }
    }
