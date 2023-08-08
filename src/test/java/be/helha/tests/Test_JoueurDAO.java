package be.helha.tests;

import be.helha.projetmmo.DAO.JoueurDAO;
import be.helha.projetmmo.DAOImpl.JoueurDAOImpl;
import be.helha.projetmmo.Model.Joueur;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Test_JoueurDAO {

    private JoueurDAO joueurDAO = new JoueurDAOImpl();

    @Test
    @Order(1)
    public void testAjouterJoueur() throws SQLException {
        String pseudo = "nouveauJoueur";
        String email = "nouveaujoueur@example.com";

        boolean resultat = joueurDAO.ajouterJoueur(pseudo, email);
        assertTrue(resultat);
        System.out.println("Le joueur a été ajouté avec succès.");
    }


    @Test
    @Order(2)
    public void testSupprimerJoueur() {
        // Supposons que l'ID 1 correspond à un joueur existant dans la base de données
        int joueurId = 56;

        boolean resultat = joueurDAO.supprimerJoueur(joueurId);
        assertTrue(resultat);
        System.out.println("Le joueur a été supprimé avec succès.");
    }

    @Test
    @Order(4)
    public void testGetAllPersonnages() {
        List<Joueur> personnages = null;
        try {
            personnages = joueurDAO.getAllJoueurs();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        assertNotNull(personnages);
        assertTrue(personnages.size() > 0);
        System.out.println("Les personnages ont ete recuperes avec succes.");
        for (Joueur personnage : personnages) {
            System.out.println(personnage.toString());
        }
    }

    @Test
    @Order(5)
    public void testExisteJoueur() throws SQLException {
        // Supposons que ces joueurs existent déjà dans la base de données
        String pseudoExistant = "Taj";
        String emailExistant = "taj@gmail.com";

        // Test lorsque le joueur existe dans la base de données
        boolean joueurExistantExiste = joueurDAO.existeJoueur(pseudoExistant, emailExistant);
        assertTrue(joueurExistantExiste, "Le joueur existant doit exister dans la base de données.");
    }

    @Test
    @Order(6)
    public void testUpdatePseudo() throws SQLException {
        // Supposons que le joueur avec l'ID 1 existe déjà dans la base de données
        int joueurId = 8;
        String nouveauPseudo = "Taj";

        boolean updateResult = joueurDAO.updatePseudo(joueurId, nouveauPseudo);
        assertTrue(updateResult, "La mise à jour du pseudo doit être réussie.");

        // Récupérer tous les joueurs depuis la base de données
        List<Joueur> joueurs = joueurDAO.getAllJoueurs();

        // Rechercher le joueur mis à jour dans la liste
        Joueur joueurMisAJour = null;
        for (Joueur joueur : joueurs) {
            if (joueur.getId() == joueurId) {
                joueurMisAJour = joueur;
                break;
            }
        }
        // Vérifier si le joueur mis à jour existe dans la liste
        assertNotNull(joueurMisAJour, "Le joueur mis à jour ne doit pas être null.");
        assertEquals(nouveauPseudo, joueurMisAJour.getPseudo(), "Le pseudo doit être mis à jour correctement.");
    }






}