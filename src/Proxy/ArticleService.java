package Proxy;

/*
 * Interface: ArticleService
 * --------------------------
 * - Definiert die grundlegenden Methoden, die von Klassen wie `ArticleServiceImpl` und `ArticleServiceValidator` implementiert werden.
 * - Ermöglicht Polymorphismus, indem sowohl der Proxy (Validator) als auch die konkrete Implementierung über das gleiche Interface angesprochen werden können.
 */
interface ArticleService {
    void createArticle(String name, String description);
}
