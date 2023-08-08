package be.helha.projetmmo.DAOImpl;

import be.helha.projetmmo.DAO.JoueurDAO;
import be.helha.projetmmo.Model.Joueur;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MockJoueurDAO implements JoueurDAO {

    private List<Joueur> joueurs; // Liste fictive de joueurs

    public void setJoueurs(List<Joueur> joueurs) {
        this.joueurs = joueurs;
    }


    @Override
    public Joueur getJoueurById(int joueurId) {
        // Implémentation fictive pour les tests
        // Retourner le joueur correspondant à l'ID donné depuis la liste fictive
        return joueurs.stream()
                .filter(joueur -> joueur.getId() == joueurId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean existeJoueur(String pseudo, String email) {
        // Implémentation fictive pour les tests
        // Vérifier si un joueur avec le pseudo ou l'email donné existe dans la liste fictive
        return joueurs.stream()
                .anyMatch(joueur -> joueur.getPseudo().equals(pseudo) || joueur.getEmail().equals(email));
    }

    @Override
    public boolean ajouterJoueur(String pseudo, String email) {
        // Implémentation factice pour le test
        return true;
    }

    @Override
    public boolean supprimerJoueur(int joueurId) {
        // Implémentation factice pour le test
        return true;
    }

    @Override
    public List<Joueur> getAllJoueurs() throws SQLException {
        // Implémentation factice pour le test
        List<Joueur> joueurs = new ArrayList<>();
        Joueur joueur1 = new Joueur();
        joueur1.setId(1);
        joueur1.setPseudo("MockJoueur1");
        joueur1.setEmail("mock1@example.com");
        joueur1.setStatus(true);
        joueurs.add(joueur1);

        Joueur joueur2 = new Joueur();
        joueur2.setId(2);
        joueur2.setPseudo("MockJoueur2");
        joueur2.setEmail("mock2@example.com");
        joueur2.setStatus(false);
        joueurs.add(joueur2);

        return joueurs;
    }

    @Override
    public boolean updatePseudo(int joueurId, String nouveauPseudo) throws SQLException {
        // Implémentation factice pour le test
        return true;
    }

    @Override
    public boolean updateStatut(int joueurId, boolean newStatut) throws SQLException {
        // Implémentation factice pour le test
        return true;
    }



}
