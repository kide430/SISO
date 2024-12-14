package HMAC;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class hmac_ohne_erklärung {
    public static void main(String[] args) {
        try {


            String secretKey = readSecretKey("security.properties", "key");

            final String ALGORITHM = "HmacSHA1";

            byte[] secretKeyBytes = secretKey.getBytes();
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, ALGORITHM);

            Mac mac = Mac.getInstance(ALGORITHM);
            mac.init(secretKeySpec);


            String data = "Hello, World!";
            byte[] dataBytes = data.getBytes();


            byte[] hmacResult = mac.doFinal(dataBytes);
            System.out.println("HMAC (Hex-Format): " + bytesToHex(hmacResult));

        } catch (Exception e) {
            // Fehlerbehandlung: Gibt Probleme bei Datei-I/O, Algorithmus oder Schlüssel aus.
            e.printStackTrace();
        }
    }


    private static String readSecretKey(String filePath, String keyName) throws IOException {
        // 1. Erstellen einer Properties-Instanz.
        Properties properties = new Properties();

        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis); // Schlüssel-Wert-Paare aus der Datei laden.
        }

        String key = properties.getProperty(keyName);

        if (key == null) {
            throw new IllegalArgumentException("Key '" + keyName + "' not found in file: " + filePath);
        }

        return key;
    }


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