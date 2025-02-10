package Proxy;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Proxy-Klasse: ArticleServiceValidator
 * --------------------------------------
 * - Implementiert ebenfalls das ArticleService-Interface.
 * - Agiert als Proxy und überprüft die Eingabewerte vor der Weiterleitung an die tatsächliche Implementierung (ArticleServiceImpl).
 */
class ArticleServiceValidator implements ArticleService {

    // Referenz zur tatsächlichen Implementierung (delegiertes Objekt)
    private final ArticleService impl;

    // Reguläre Ausdrücke zur Validierung von Name und Beschreibung
    private final Pattern namePattern = Pattern.compile("^[a-zA-Z0-9 ]{4,64}$");
    private final Pattern descriptionPattern = Pattern.compile("^[a-zA-Z0-9,:.\\-)( ]{0,256}$");

    /*
     * Konstruktor
     * -----------
     * - Nimmt eine Implementierung des ArticleService-Interfaces entgegen.
     * - Verwendet Polymorphismus: Kann mit jeder Klasse arbeiten, die ArticleService implementiert.
     */
    public ArticleServiceValidator(ArticleService impl) {
        if (impl == null) {
            throw new IllegalArgumentException("ArticleService implementation cannot be null");
        }
        this.impl = impl;
    }

    /*
     * Methode: createArticle
     * -----------------------
     * - Validiert die Eingaben (Name und Beschreibung) vor der Weiterleitung an die tatsächliche Implementierung.
     */
    @Override
    public void createArticle(String name, String description) {
        // Null-Prüfung für den Namen
        if (name == null) {
            throw new IllegalArgumentException("Invalid name (null)!");
        }

        // Validierung des Namens mit Regulärem Ausdruck
        Matcher nameMatcher = namePattern.matcher(Normalizer.normalize(name, Normalizer.Form.NFKC));
        if (!nameMatcher.matches()) {
            throw new IllegalArgumentException("Invalid name!");
        }

        // Null-Prüfung und Validierung der Beschreibung
        if (description != null) { // Beschreibung ist optional
            Matcher descriptionMatcher = descriptionPattern.matcher(Normalizer.normalize(description, Normalizer.Form.NFKC));
            if (!descriptionMatcher.matches()) {
                throw new IllegalArgumentException("Invalid description!");
            }
        }

        // Delegiert die Verarbeitung an die tatsächliche Implementierung
        impl.createArticle(name, description);
    }
}
