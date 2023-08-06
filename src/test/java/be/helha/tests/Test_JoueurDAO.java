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
        int joueurId = 1;

        boolean resultat = joueurDAO.supprimerJoueur(joueurId);
        assertTrue(resultat);
        System.out.println("Le joueur a été supprimé avec succès.");
    }

    @Test
    @Order(4)
    public void testGetAllPersonnages() {
        List<Joueur> personnages = null;
        try {
            personnages = joueurDAO.getAllPersonnages();
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

}
