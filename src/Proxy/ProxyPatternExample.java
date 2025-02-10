package Proxy;

/*
 * Hauptklasse: Demonstration des Proxy-Musters
 * --------------------------------------------
 * - Zeigt, wie der Validator-Proxy verwendet wird, um Eingaben zu validieren, bevor sie an die Implementierung weitergeleitet werden.
 */
public class ProxyPatternExample {
    public static void main(String[] args) {
        // Konkrete Implementierung des ArticleService
        ArticleService realService = new ArticleServiceImpl();

        // Proxy (Validator) wird mit der tatsächlichen Implementierung initialisiert
        ArticleService proxyService = new ArticleServiceValidator(realService);

        // Erfolgreicher Aufruf: Gültige Eingaben
        proxyService.createArticle("Valid Name", "This is a valid description.");

        // Fehlerhafter Aufruf: Ungültiger Name
        try {
            proxyService.createArticle("Bad", "Valid description");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        // Fehlerhafter Aufruf: Ungültige Beschreibung
        try {
            proxyService.createArticle("Valid Name", "Invalid description with too many invalid characters $$$$!!!");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
