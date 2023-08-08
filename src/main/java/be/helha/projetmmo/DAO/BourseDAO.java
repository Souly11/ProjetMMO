package be.helha.projetmmo.DAO;

import be.helha.projetmmo.Model.Bourse;
import be.helha.projetmmo.Model.Dragmes;

import java.sql.SQLException;
import java.util.List;

public interface BourseDAO {

    boolean ajouterBourse(Bourse bourse);
    boolean supprimerBourseParJoueur(int joueurId);

    List<Dragmes> recupererContenuBourseParJoueur(int idJoueur);

    int calculerTotalBourseParJoueur(int idJoueur) throws SQLException;

    void ajouterDragmeBourse(int joueurId, Dragmes dragme) throws SQLException;

    void supprimerDragmeBourse(int joueurId, Dragmes dragme);
}
