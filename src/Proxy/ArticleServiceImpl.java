package Proxy;

/*
 * Klasse: ArticleServiceImpl
 * ---------------------------
 * - Konkrete Implementierung des ArticleService-Interfaces.
 * - Führt die tatsächliche Logik für das Erstellen eines Artikels aus.
 */
class ArticleServiceImpl implements ArticleService {
    @Override
    public void createArticle(String name, String description) {
        // Simuliert das Speichern eines Artikels
        System.out.println("Article created: Name = " + name + ", Description = " + description);
    }
}
