package be.helha.projetmmo.Model;

/**
 * Représente les différentes valeurs de dragmes disponibles dans le jeu.
 */
public enum Dragmes {
    piece_1(1),
    piece_2(2),
    billet_5(5),
    billet_10(10),
    billet_20(20),
    billet_50(50);

    private final int valeur;

    /**
     * Constructeur de l'énumération Dragmes.
     */
    Dragmes(int valeur) {
        this.valeur = valeur;
    }

    public int getValeur() {
        return valeur;
    }

    /**
     * Renvoie une représentation sous forme de texte de l'élément de l'énumération.
     */
    @Override
    public String toString() {
        return this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase();
    }
}
