package be.helha.projetmmo.DAO;

import be.helha.projetmmo.Model.Bourse;
import be.helha.projetmmo.Model.Dragmes;

import java.util.List;

public interface BourseDAO {

    boolean ajouterBourse(Bourse bourse);
    boolean supprimerBourseParJoueur(int joueurId);

    List<Dragmes> recupererContenuBourseParJoueur(int idJoueur);

    int calculerTotalBourseParJoueur(int idJoueur);
}
