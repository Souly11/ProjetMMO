package be.helha.projetmmo.UseCase;

import be.helha.projetmmo.Model.Dragmes;
import be.helha.projetmmo.Model.Joueur;

import java.util.List;

public interface GestionJoueur {

    List<Joueur> listJoueur();

    boolean ajouterJoueur(String nom, String email);

    void supprimerJoueur(int id);

    boolean existeJoueur(String nom, String email);

    boolean updatePseudo(int joueurId, String nouveauPseudo);

    boolean updateStatut(int joueurId, boolean newStatut);

    void ajouterDragmeBourse(int joueurId, Dragmes dragme);

    int calculerTotalBourseParJoueur(int idJoueur);

    List<Dragmes> recupererContenuBourseParJoueur(int idJoueur);

    void supprimerDragmeBourse(int joueurId, Dragmes dragme);

}