package be.helha.projetmmo.UseCaseImpl;

import be.helha.projetmmo.DAO.JoueurDAO;
import be.helha.projetmmo.DAOImpl.JoueurDAOImpl;
import be.helha.projetmmo.Model.Joueur;
import be.helha.projetmmo.UseCase.GestionJoueur;

import java.sql.SQLException;
import java.util.List;

public class GestionJoueurImpl implements GestionJoueur {

    private final JoueurDAO joueurDao = new JoueurDAOImpl();

    @Override
    public List<Joueur> listJoueur(){
        try{
            return this.joueurDao.getAllPersonnages();
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

}
