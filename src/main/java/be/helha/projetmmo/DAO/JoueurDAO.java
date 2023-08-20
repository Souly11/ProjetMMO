package be.helha.projetmmo.DAO;

import be.helha.projetmmo.Model.Joueur;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface décrivant les opérations de gestion des joueurs.
 */
public interface JoueurDAO extends Dao{

    /**
     * Récupère un joueur en fonction de son identifiant.
     */
    Joueur getJoueurById(int joueurId) throws SQLException;

    /**
     * Ajoute un joueur avec le nom et l'email spécifiés.
     */
    boolean ajouterJoueur(String nom, String email)throws SQLException;

    /**
     * Supprime un joueur en fonction de son identifiant.
     */
    boolean supprimerJoueur(int joueurId);

    /**
     * Récupère la liste de tous les joueurs.
     */
    List<Joueur> getAllJoueurs()throws SQLException;

    /**
     * Vérifie si un joueur existe déjà en fonction de son pseudo et de son email.
     */
    boolean existeJoueur(String pseudo, String email) throws SQLException;

    /**
     * Met à jour le pseudo d'un joueur en fonction de son identifiant.
     */
    boolean updatePseudo(int joueurId, String nouveauPseudo) throws SQLException;

    /**
     * Met à jour le statut d'un joueur (premium ou non) en fonction de son identifiant.
     */
    boolean updateStatut(int joueurId, boolean newStatut) throws SQLException;
}
