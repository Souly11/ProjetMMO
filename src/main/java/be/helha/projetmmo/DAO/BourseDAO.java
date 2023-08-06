package be.helha.projetmmo.DAO;

import be.helha.projetmmo.Model.Bourse;

public interface BourseDAO {

    public boolean ajouterBourse(Bourse bourse);

    boolean supprimerBourseParJoueur(int joueurId);
}
