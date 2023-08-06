package be.helha.projetmmo.UseCase;

import be.helha.projetmmo.Model.Joueur;

import java.util.List;

public interface GestionJoueur {

    List<Joueur> listJoueur();

    boolean ajouterJoueur(String nom, String email);

    void supprimerJoueur(int id);

    boolean existeJoueur(String nom, String email);

}