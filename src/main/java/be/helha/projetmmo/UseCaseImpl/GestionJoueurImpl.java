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

public class GestionJoueurImpl implements GestionJoueur {

    private final JoueurDAO joueurDao = new JoueurDAOImpl();

    private final BourseDAO bourseDAO = new BourseDAOImpl();

    @Override
    public List<Joueur> listJoueur(){
        try{
            return this.joueurDao.getAllJoueurs();
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean ajouterJoueur(String nom, String email) {
        JoueurDAO joueurDAO = new JoueurDAOImpl();
        try {
            return joueurDAO.ajouterJoueur(nom, email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void supprimerJoueur(int id) {
        this.joueurDao.supprimerJoueur(id);
    }

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

    @Override
    public boolean updatePseudo(int joueurId, String nouveauPseudo) {
        try {
            return joueurDao.updatePseudo(joueurId, nouveauPseudo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateStatut(int joueurId, boolean newStatut) {
        try {
            return joueurDao.updateStatut(joueurId, newStatut);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void ajouterDragmeBourse(int joueurId, Dragmes dragme) {
        try {
            bourseDAO.ajouterDragmeBourse(joueurId, dragme);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int calculerTotalBourseParJoueur(int idJoueur) {
        try {
            return bourseDAO.calculerTotalBourseParJoueur(idJoueur);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Dragmes> recupererContenuBourseParJoueur(int idJoueur) {
        return bourseDAO.recupererContenuBourseParJoueur(idJoueur);
    }

    @Override
    public void supprimerDragmeBourse(int joueurId, Dragmes dragme) {
        BourseDAO bourseDAO = new BourseDAOImpl();
        bourseDAO.supprimerDragmeBourse(joueurId, dragme);
    }




}
