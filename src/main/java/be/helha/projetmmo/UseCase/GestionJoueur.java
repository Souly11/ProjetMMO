package be.helha.projetmmo.UseCase;

import be.helha.projetmmo.Model.Dragmes;
import be.helha.projetmmo.Model.Joueur;

import java.util.List;

/**
 * Interface définissant les opérations de gestion des joueurs dans le jeu.
 */
public interface GestionJoueur {

    /**
     * Récupère la liste de tous les joueurs dans le jeu.
     */
    List<Joueur> listJoueur();

    /**
     * Ajoute un nouveau joueur avec le nom et l'email spécifiés.
     */
    boolean ajouterJoueur(String nom, String email);

    /**
     * Supprime le joueur avec l'ID spécifié.
     */
    void supprimerJoueur(int id);

    /**
     * Vérifie si un joueur avec le nom et l'email spécifiés existe déjà.
     */
    boolean existeJoueur(String nom, String email);

    /**
     * Met à jour le pseudo du joueur avec l'ID spécifié.
     */
    boolean updatePseudo(int joueurId, String nouveauPseudo);

    /**
     * Met à jour le statut du joueur avec l'ID spécifié.
     */
    boolean updateStatut(int joueurId, boolean newStatut);

    /**
     * Ajoute un dragme à la bourse du joueur avec l'ID spécifié.
     */
    void ajouterDragmeBourse(int joueurId, Dragmes dragme);

    /**
     * Calcule le total des dragmes dans la bourse du joueur avec l'ID spécifié.
     */
    int calculerTotalBourseParJoueur(int idJoueur);

    /**
     * Récupère le contenu de la bourse du joueur avec l'ID spécifié.
     */
    List<Dragmes> recupererContenuBourseParJoueur(int idJoueur);

    /**
     * Supprime un dragme de la bourse du joueur avec l'ID spécifié.
     */
    void supprimerDragmeBourse(int joueurId, Dragmes dragme);

}