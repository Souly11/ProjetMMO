package be.helha.projetmmo.Model;

public enum Dragmes {
    piece_1(1),
    piece_2(2),
    billet_5(5),
    billet_10(10),
    billet_20(20),
    billet_50(50);

    private final int valeur;

    Dragmes(int valeur) {
        this.valeur = valeur;
    }

    public int getValeur() {
        return valeur;
    }

    @Override
    public String toString() {
        // Retourner le nom souhaité pour l'élément de l'enum
        // Par exemple, retourner "Pièce 1" pour Dragmes.piece_1, etc.
        // Vous pouvez adapter le code selon vos besoins.
        // Pour cet exemple, nous retournons simplement le nom de l'enum avec la première lettre en majuscule.
        return this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase();
    }
}
