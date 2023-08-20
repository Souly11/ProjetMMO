package be.helha.projetmmo.DAO;

import be.helha.projetmmo.Model.Bourse;
import be.helha.projetmmo.Model.Dragmes;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface décrivant les opérations de gestion de la bourse.
 */

public interface BourseDAO extends Dao{

    /**
     * Ajoute une bourse pour un joueur.
     */
    boolean ajouterBourse(Bourse bourse);

    /**
     * Supprime la bourse d'un joueur en fonction de son identifiant.
     */
    boolean supprimerBourseParJoueur(int joueurId);

    /**
     * Récupère le contenu de la bourse d'un joueur.
     */
    List<Dragmes> recupererContenuBourseParJoueur(int idJoueur);

    /**
     * Calcule le montant total de la bourse d'un joueur en fonction de son identifiant.
     */
    int calculerTotalBourseParJoueur(int idJoueur) throws SQLException;

    /**
     * Ajoute un dragme à la bourse d'un joueur en fonction de son identifiant.
     */
    void ajouterDragmeBourse(int joueurId, Dragmes dragme) throws SQLException;

    /**
     * Supprime un dragme de la bourse d'un joueur en fonction de son identifiant.
     */
    void supprimerDragmeBourse(int joueurId, Dragmes dragme);
}
