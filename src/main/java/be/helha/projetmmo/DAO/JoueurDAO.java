package be.helha.projetmmo.DAO;

import be.helha.projetmmo.Model.Joueur;

import java.sql.SQLException;
import java.util.List;

public interface JoueurDAO {

    boolean ajouterJoueur(String nom, String email)throws SQLException;

    boolean supprimerJoueur(int joueurId);

    List<Joueur> getAllPersonnages()throws SQLException;

    boolean existeJoueur(String pseudo, String email) throws SQLException;

    boolean updatePseudo(int joueurId, String nouveauPseudo) throws SQLException;

    boolean updateStatut(int joueurId, boolean newStatut) throws SQLException;
}
