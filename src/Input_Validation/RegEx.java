package Input_Validation;


import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegEx {

    public static void main(String[] args) {

        /*
         * 1. Eingabestring definieren
         * ---------------------------
         * Der zu prüfende String (z. B. eine E-Mail-Adresse).
         */
        String input = "test@example.com";

        /*
         * 2. Regulären Ausdruck (RegEx) definieren
         * ---------------------------------------
         * Das RegEx-Muster beschreibt das Format einer gültigen E-Mail-Adresse.
         */
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        /*
         * 3. RegEx-Muster kompilieren
         * ---------------------------
         * Das Muster wird mit Pattern.compile() in ein Pattern-Objekt umgewandelt.
         */
        Pattern pattern = Pattern.compile(regex);

        /*
         * 4. Unicode-Normalisierung
         * -------------------------
         * Die Eingabe wird mit Normalizer.normalize() in die kanonische Form NFKC umgewandelt,
         * um sicherzustellen, dass Zeichen wie Akzente oder ähnliche Unicode-Probleme standardisiert werden.
         */
        String normalizedInput = Normalizer.normalize(input, Normalizer.Form.NFKC);

        /*
         * 5. RegEx-Prüfung
         * ----------------
         * Das normalisierte Eingabeformat wird mit dem RegEx-Muster abgeglichen.
         */
        Matcher matcher = pattern.matcher(normalizedInput);

        /*
         * 6. Ausgabe
         * ----------
         * Gibt aus, ob die E-Mail-Adresse gültig oder ungültig ist.
         */
        if (matcher.matches()) {
            System.out.println("Valid email address");
        } else {
            System.out.println("Invalid email address");
        }
    }
}

