package be.helha.tests;

import be.helha.projetmmo.DAO.JoueurDAO;
import be.helha.projetmmo.DAOImpl.JoueurDAOImpl;
import be.helha.projetmmo.DAOImpl.MockJoueurDAO;
import be.helha.projetmmo.Model.Joueur;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Test_JoueurDAO {

    private JoueurDAO joueurDAO = new MockJoueurDAO();

    @Test
    @Order(1)
    public void testAjouterJoueur() throws SQLException {
        String pseudo = "nouveauJoueur";
        String email = "nouveaujoueur@example.com";

        // Vérifier que le joueur n'existe pas déjà
        assertFalse(joueurDAO.existeJoueur(pseudo, email));

        // Ajouter le joueur
        assertDoesNotThrow(() -> {
            boolean resultat = joueurDAO.ajouterJoueur(pseudo, email);
            assertTrue(resultat);
        });

        // Vérifier que le joueur a été ajouté avec succès
        assertTrue(joueurDAO.existeJoueur(pseudo, email));
    }


    @Test
    @Order(2)
    public void testSupprimerJoueur() throws SQLException {
        int joueurId = 1;

        // Vérifier que le joueur existe avant la suppression
        assertNotNull(joueurDAO.getJoueurById(joueurId), "Le joueur doit exister avant la suppression.");

        // Supprimer le joueur
        assertDoesNotThrow(() -> {
            boolean resultat = joueurDAO.supprimerJoueur(joueurId);
            assertTrue(resultat);
        });

        // Vérifier que le joueur a été supprimé avec succès
        assertNull(joueurDAO.getJoueurById(joueurId), "Le joueur doit être supprimé après la suppression.");
    }


    @Test
    @Order(3)
    public void testUpdateStatut() throws SQLException {
        int joueurId = 8;
        boolean nouveauStatut = true;

        boolean updateResult = joueurDAO.updateStatut(joueurId, nouveauStatut);
        assertTrue(updateResult, "La mise à jour du statut doit être réussie.");
    }

    @Test
    @Order(4)
    public void testGetAllPersonnages() throws SQLException {
        List<Joueur> joueurs = new ArrayList<>(); // Créer une liste fictive de joueurs
        // Ajouter des joueurs fictifs à la liste
        joueurs.add(new Joueur());
        joueurs.add(new Joueur());

        // Injecter la liste fictive dans MockJoueurDAO
        ((MockJoueurDAO) joueurDAO).setJoueurs(joueurs);

        List<Joueur> personnages = joueurDAO.getAllJoueurs();
        assertNotNull(personnages);
        assertEquals(joueurs.size(), personnages.size());
    }

    @Test
    @Order(5)
    public void testExisteJoueur() throws SQLException {
        String pseudoExistant = "Taj";
        String emailExistant = "taj@gmail.com";

        boolean joueurExistantExiste = joueurDAO.existeJoueur(pseudoExistant, emailExistant);
        assertTrue(joueurExistantExiste, "Le joueur existant doit exister dans la base de données.");
    }

    @Test
    @Order(6)
    public void testUpdatePseudo() throws SQLException {
        int joueurId = 8;
        String nouveauPseudo = "Taj";

        boolean updateResult = joueurDAO.updatePseudo(joueurId, nouveauPseudo);
        assertTrue(updateResult, "La mise à jour du pseudo doit être réussie.");
    }






}
