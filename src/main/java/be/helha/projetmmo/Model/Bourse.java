package be.helha.projetmmo.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente la bourse d'un joueur contenant des Dragmes.
 */
public class Bourse {
    private int id;
    private Joueur joueur;
    private List<Dragmes> dragmes;
    private int Limit;

    /**
     * Constructeur par défaut de la classe Bourse.
     * Initialise la liste de Dragmes avec une liste vide et la limite de la bourse par défaut.
     */
    public Bourse() {
        this.dragmes = new ArrayList<>(); // Initialiser la liste de Dragmes avec une liste vide
        this.Limit = 10; // Valeur par défaut pour la limite de la bourse (10 dans cet exemple)
    }

    public List<Dragmes> getDragmes() {
        return dragmes;
    }

    public void setDragmes(List<Dragmes> dragmes) {
        this.dragmes = dragmes;
    }

    public int getLimit() {
        return Limit;
    }

    public void setLimit(int limit) {
        Limit = limit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }
}
