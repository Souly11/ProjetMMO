package be.helha.tests;

import be.helha.projetmmo.DAO.BourseDAO;
import be.helha.projetmmo.DAOImpl.BourseDAOImpl;
import be.helha.projetmmo.Model.Dragmes;
import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Test_BourseDAO {

    public BourseDAO bourseDAO = new BourseDAOImpl();

    @Test
    @Order(1)
    public void testRecupererContenuBourseParIdJoueur() {
        int idJoueur = 8; // Remplacez par l'ID du joueur pour lequel vous souhaitez récupérer la bourse

        // Appelez la méthode pour récupérer le contenu de la bourse associée à l'ID du joueur
        List<Dragmes> contenuBourse = bourseDAO.recupererContenuBourseParJoueur(idJoueur);

        // Assurez-vous que le contenu de la bourse n'est pas vide (si c'est le cas, cela signifie que la bourse n'a pas été trouvée)
        Assertions.assertFalse(contenuBourse.isEmpty(), "Le contenu de la bourse ne doit pas être vide");
        // Afficher le contenu de la bourse
        System.out.println("Contenu de la bourse : ");
        for (Dragmes dragmes : contenuBourse) {
            System.out.println(dragmes);
        }
    }

    @Test
    @Order(1)
    public void testCalculerTotalBourseParJoueur() {
        int idJoueur = 8; // Remplacez par l'ID du joueur pour lequel vous souhaitez calculer le montant

        // Appelez la méthode pour calculer le montant total de la bourse associée à l'ID du joueur
        int montantTotal = bourseDAO.calculerTotalBourseParJoueur(idJoueur);

        System.out.println("Montant de la bourse : " + montantTotal);
    }




}
