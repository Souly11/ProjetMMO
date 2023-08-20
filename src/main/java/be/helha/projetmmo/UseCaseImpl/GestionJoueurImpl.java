package be.helha.projetmmo.UseCaseImpl;

import be.helha.projetmmo.DAO.BourseDAO;
import be.helha.projetmmo.DAO.JoueurDAO;
import be.helha.projetmmo.DAOImpl.BourseDAOImpl;
import be.helha.projetmmo.DAOImpl.JoueurDAOImpl;
import be.helha.projetmmo.Model.Dragmes;
import be.helha.projetmmo.Model.Joueur;
import be.helha.projetmmo.UseCase.GestionJoueur;

import java.sql.SQLException;
import java.util.List;

/**
 * Implémentation de l'interface GestionJoueur pour gérer les opérations liées aux joueurs.
 */
public class GestionJoueurImpl implements GestionJoueur {

    private final JoueurDAO joueurDao = new JoueurDAOImpl();

    private final BourseDAO bourseDAO = new BourseDAOImpl();

    /**
     * Récupère la liste de tous les joueurs dans le jeu.
     */
    @Override
    public List<Joueur> listJoueur(){
        try{
            return this.joueurDao.getAllJoueurs();
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return null;
    }

    /**
     * Ajoute un nouveau joueur avec le nom et l'email spécifiés.
     */
    @Override
    public boolean ajouterJoueur(String nom, String email) {
        JoueurDAO joueurDAO = new JoueurDAOImpl();
        try {
            return joueurDAO.ajouterJoueur(nom, email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Supprime le joueur avec l'ID spécifié.
     */
    @Override
    public void supprimerJoueur(int id) {
        this.joueurDao.supprimerJoueur(id);
    }

    /**
     * Vérifie si un joueur avec le nom et l'email spécifiés existe déjà.
     */
    @Override
    public boolean existeJoueur(String nom, String email) {
        List<Joueur> joueurs = listJoueur();
        if (joueurs != null) {
            for (Joueur joueur : joueurs) {
                if (joueur.getPseudo().equalsIgnoreCase(nom) || joueur.getEmail().equalsIgnoreCase(email)) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Met à jour le pseudo du joueur avec l'ID spécifié.
     */
    @Override
    public boolean updatePseudo(int joueurId, String nouveauPseudo) {
        try {
            return joueurDao.updatePseudo(joueurId, nouveauPseudo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Met à jour le statut du joueur avec l'ID spécifié.
     */
    @Override
    public boolean updateStatut(int joueurId, boolean newStatut) {
        try {
            return joueurDao.updateStatut(joueurId, newStatut);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Ajoute un dragme à la bourse du joueur avec l'ID spécifié.
     */
    @Override
    public void ajouterDragmeBourse(int joueurId, Dragmes dragme) {
        try {
            bourseDAO.ajouterDragmeBourse(joueurId, dragme);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Calcule le total des dragmes dans la bourse du joueur avec l'ID spécifié.
     */
    @Override
    public int calculerTotalBourseParJoueur(int idJoueur) {
        try {
            return bourseDAO.calculerTotalBourseParJoueur(idJoueur);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Récupère le contenu de la bourse du joueur avec l'ID spécifié.
     */
    @Override
    public List<Dragmes> recupererContenuBourseParJoueur(int idJoueur) {
        return bourseDAO.recupererContenuBourseParJoueur(idJoueur);
    }

    /**
     * Supprime un dragme de la bourse du joueur avec l'ID spécifié.
     */
    @Override
    public void supprimerDragmeBourse(int joueurId, Dragmes dragme) {
        BourseDAO bourseDAO = new BourseDAOImpl();
        bourseDAO.supprimerDragmeBourse(joueurId, dragme);
    }




}
