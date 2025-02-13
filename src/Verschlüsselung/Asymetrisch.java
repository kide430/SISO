package Verschlüsselung;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Diese Klasse implementiert asymmetrische Verschlüsselung und Entschlüsselung
 * mit dem RSA-Algorithmus. Sie enthält Methoden zum Verschlüsseln und
 * Entschlüsseln von Dateien. Der öffentliche Schlüssel wird verwendet, um Daten
 * zu verschlüsseln, und der private Schlüssel wird verwendet, um diese Daten zu
 * entschlüsseln.
 */
public class Asymetrisch {

    public class EncryptedFile {

        private PrivateKey privateKey; // Privater Schlüssel zum Entschlüsseln
        private PublicKey publicKey; // Öffentlicher Schlüssel zum Verschlüsseln
        private Cipher cipher; // Verschlüsselungs-Engine

        /**
         * Konstruktor: Initialisiert die RSA-Schlüssel (öffentlich und privat) und
         * setzt die Verschlüsselungs-Engine (`Cipher`) auf RSA.
         *
         * @param publicKeyString  Der öffentliche Schlüssel als hexadezimaler String.
         * @param privateKeyString Der private Schlüssel als hexadezimaler String.
         */
        public EncryptedFile(String publicKeyString, String privateKeyString) {
            if (publicKeyString == null)
                throw new IllegalArgumentException("Invalid public key string! Der öffentliche Schlüssel darf nicht null sein.");

            if (privateKeyString == null)
                throw new IllegalArgumentException("Invalid private key string! Der private Schlüssel darf nicht null sein.");

            try {
                // Konvertiere den hexadezimalen String in ein Byte-Array
                byte[] publicKeyBytes = Hex.decodeHex(publicKeyString.toCharArray());
                byte[] privateKeyBytes = Hex.decodeHex(privateKeyString.toCharArray());

                // Erzeuge KeyFactory, um Schlüsselobjekte zu erstellen
                KeyFactory factory = KeyFactory.getInstance("RSA");

                // Erstelle private und öffentliche Schlüssel
                privateKey = factory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
                publicKey = factory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));

                // Initialisiere die Cipher-Instanz für RSA
                cipher = Cipher.getInstance("RSA");
            } catch (NoSuchAlgorithmException | InvalidKeySpecException | DecoderException | NoSuchPaddingException e) {
                throw new IllegalStateException("Can't initialize EncryptedFile object! Fehler beim Initialisieren der Schlüssel.", e);
            }
        }

        /**
         * Methode: Verschlüsselt die angegebenen Daten und speichert sie in einer Datei.
         *
         * @param filename Der Name der Datei, in der die verschlüsselten Daten gespeichert werden.
         * @param data     Die zu verschlüsselnden Daten als Byte-Array.
         */
        public void saveEncrypted(String filename, byte[] data) {
            if (filename == null || data == null)
                throw new IllegalArgumentException("Invalid filename or data! Der Dateiname oder die Daten sind ungültig.");

            try {
                // Initialisiere die Cipher-Engine im ENCRYPT_MODE mit dem öffentlichen Schlüssel
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);

                // Verschlüsselt die Eingabedaten
                byte[] cipherText = cipher.doFinal(data);

                // Speichert die verschlüsselten Daten in der angegebenen Datei
                save(filename, cipherText);
            } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
                throw new IllegalStateException("Unable to encrypt data! Fehler beim Verschlüsseln der Daten.", e);
            }
        }

        /**
         * Methode: Liest verschlüsselte Daten aus einer Datei und entschlüsselt sie.
         *
         * @param filename Der Name der Datei, aus der die verschlüsselten Daten geladen werden.
         * @return Die entschlüsselten Daten als Byte-Array.
         */
        public byte[] loadEncrypted(String filename) {
            if (filename == null)
                throw new IllegalArgumentException("Invalid filename! Der Dateiname darf nicht null sein.");

            try {
                // Lese die verschlüsselten Daten aus der Datei
                byte[] data = load(filename);

                // Initialisiere die Cipher-Engine im DECRYPT_MODE mit dem privaten Schlüssel
                cipher.init(Cipher.DECRYPT_MODE, privateKey);

                // Entschlüsselt die geladenen Daten und gibt sie zurück
                return cipher.doFinal(data);
            } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
                throw new IllegalStateException("Unable to decrypt data! Fehler beim Entschlüsseln der Daten.", e);
            }
        }}}

/**
 * Speichert ein Byte-Array in einer Datei.
 *
 * @param filename Der Name der Datei.
 * @param data     Die zu speichernden Daten.

