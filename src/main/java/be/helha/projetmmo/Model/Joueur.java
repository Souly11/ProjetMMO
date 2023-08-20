package be.helha.projetmmo.Model;

/**
 * Représente un joueur du jeu.
 */
public class Joueur {

    private int id;
    private String pseudo;
    private String email;
    private boolean status;

    /**
     * Constructeur par défaut de la classe Joueur.
     * Ne définit pas de valeurs pour le pseudo, l'email et le statut.
     */
    public Joueur() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
