package Encapsulation;

import java.util.ArrayList;
import java.util.List;

/*
 * Die Klasse User demonstriert das Konzept der Encapsulation (Kapselung)
 * und verwendet Deep Copy, um die Integrität der internen Datenstruktur zu schützen.
 */

public class DeepCOpy {

    // Private Liste von E-Mail-Objekten, die encapsuliert wird
    private List<EMail> mails;

    /*
     * Setter-Methode: E-Mails setzen
     * ------------------------------
     * - Nimmt eine Liste von E-Mail-Objekten entgegen.
     * - Überprüft die Eingabe auf null.
     * - Erstellt eine Deep Copy der Liste und ihrer Elemente, um Änderungen an der Originaldatenstruktur zu verhindern.
     */
    public final void setMails(final List<EMail> mails) {
        // Null-Prüfung: Verhindert, dass eine null-Liste gesetzt wird
        if (mails == null) {
            throw new IllegalArgumentException("E-Mail-Liste darf nicht null sein.");
        }

        // Deep Copy: Neue Liste erstellen, um Originaldaten zu schützen
        List<EMail> deepCopiedList = new ArrayList<>();

        // Kopiere jedes E-Mail-Objekt in die neue Liste
        for (EMail mail : mails) {
            deepCopiedList.add(new EMail(mail.getAddress())); // Erzeugt ein neues E-Mail-Objekt
        }

        // Speichert die tief kopierte Liste in der Klasse
        this.mails = deepCopiedList;
    }

    /*
     * Getter-Methode: E-Mails abrufen
     * -------------------------------
     * - Gibt eine kopierte Version der internen Liste zurück.
     * - Erstellt Deep Copies der E-Mail-Objekte, um Änderungen an den internen Daten zu verhindern.
     */
    public List<EMail> getMails() {
        // Neue Liste für die kopierten E-Mail-Objekte
        List<EMail> copiedList = new ArrayList<>();

        // Kopiere jedes E-Mail-Objekt aus der internen Liste
        for (EMail mail : this.mails) {
            copiedList.add(new EMail(mail.getAddress())); // Erzeugt ein neues E-Mail-Objekt
        }

        // Gibt die kopierte Liste zurück
        return copiedList;
    }

    /*
     * Innere Klasse: EMail
     * ---------------------
     * - Stellt eine einfache E-Mail-Adresse dar.
     * - Dient als Demonstration für Deep Copy.
     */
    static class EMail {
        private String address; // Die E-Mail-Adresse

        // Konstruktor: Erstellt ein E-Mail-Objekt mit einer angegebenen Adresse
        public EMail(String address) {
            this.address = address;
        }

        // Getter: Gibt die Adresse zurück
        public String getAddress() {
            return this.address;
        }

        // Setter: Setzt eine neue Adresse
        public void setAddress(String address) {
            this.address = address;
        }
    }

    /*
     * Hauptmethode: Demonstriert die Funktionsweise der Klasse
     * --------------------------------------------------------
     * - Erstellt ein User-Objekt und setzt eine Liste von E-Mails.
     * - Zeigt, wie Änderungen an der abgerufenen Liste die interne Datenstruktur nicht beeinflussen.
     */
    public static void main(String[] args) {
        DeepCOpy user = new DeepCOpy();

        // Eingabeliste erstellen
        List<EMail> emailList = new ArrayList<>();
        emailList.add(new EMail("test@example.com"));
        emailList.add(new EMail("user@example.com"));

        // Setter: E-Mails setzen
        user.setMails(emailList);

        // Getter: Kopierte Liste abrufen
        List<EMail> mails = user.getMails();
        mails.add(new EMail("hacker@example.com")); // Ändert NICHT die interne Liste

        // Interne Liste bleibt unverändert
        System.out.println("Interne Liste Größe: " + user.getMails().size()); // Ausgabe: 2
        System.out.println("Externe Liste Größe: " + mails.size()); // Ausgabe: 3
    }
}
