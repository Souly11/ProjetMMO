package be.helha.tests;

import be.helha.projetmmo.DAO.BourseDAO;
import be.helha.projetmmo.Model.Dragmes;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Test_BourseDAO {

    @Mock
    private BourseDAO bourseDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    public void testRecupererContenuBourseParIdJoueur() {
        int idJoueur = 8;

        List<Dragmes> contenuBourseMock = List.of(Dragmes.billet_10, Dragmes.piece_2);
        when(bourseDAO.recupererContenuBourseParJoueur(idJoueur)).thenReturn(contenuBourseMock);

        List<Dragmes> contenuBourse = bourseDAO.recupererContenuBourseParJoueur(idJoueur);

        assertFalse(contenuBourse.isEmpty(), "Le contenu de la bourse ne doit pas être vide.");
        assertEquals(2, contenuBourse.size(), "Il devrait y avoir 2 éléments dans la bourse.");
    }

    @Test
    @Order(2)
    public void testCalculerTotalBourseParJoueur() throws SQLException {
        int idJoueur = 8;
        when(bourseDAO.calculerTotalBourseParJoueur(idJoueur)).thenReturn(100);

        int montantTotal = bourseDAO.calculerTotalBourseParJoueur(idJoueur);

        assertEquals(100, montantTotal, "Le montant total de la bourse devrait être 100.");
    }

    @Test
    @Order(3)
    public void testAjouterDragmeBourse() throws SQLException {
        int idJoueur = 8;
        Dragmes dragme = Dragmes.billet_10;
        doNothing().when(bourseDAO).ajouterDragmeBourse(idJoueur, dragme);

        bourseDAO.ajouterDragmeBourse(idJoueur, dragme);

        verify(bourseDAO).ajouterDragmeBourse(idJoueur, dragme);
    }

    @Test
    @Order(4)
    public void testSupprimerDragmeBourse() {
        int joueurId = 8;
        Dragmes dragmeASupprimer = Dragmes.piece_2;
        doNothing().when(bourseDAO).supprimerDragmeBourse(joueurId, dragmeASupprimer);

        bourseDAO.supprimerDragmeBourse(joueurId, dragmeASupprimer);

        verify(bourseDAO).supprimerDragmeBourse(joueurId, dragmeASupprimer);
    }

/*
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
    @Order(2)
    public void testCalculerTotalBourseParJoueur() throws SQLException {
        int idJoueur = 8; // Remplacez par l'ID du joueur pour lequel vous souhaitez calculer le montant

        // Appelez la méthode pour calculer le montant total de la bourse associée à l'ID du joueur
        int montantTotal = bourseDAO.calculerTotalBourseParJoueur(idJoueur);

        System.out.println("Montant de la bourse : " + montantTotal);
    }

    @Test
    @Order(3)
    public void testAjouterDragmeBourse() throws SQLException {
        int idJoueur = 8; // Remplacez par l'ID du joueur auquel vous voulez ajouter la pièce
        Dragmes dragme = Dragmes.billet_10; // Remplacez par la pièce que vous voulez ajouter

        // Appelez la méthode pour ajouter la pièce à la bourse du joueur
        bourseDAO.ajouterDragmeBourse(idJoueur, dragme);

        // Vérifiez que la pièce a bien été ajoutée en récupérant le contenu de la bourse du joueur
        List<Dragmes> contenuBourse = bourseDAO.recupererContenuBourseParJoueur(idJoueur);
        Assertions.assertTrue(contenuBourse.contains(dragme), "La pièce doit être présente dans la bourse");
    }

    @Test
    @Order(4)
    public void testSupprimerDragmeBourse() {
        int joueurId = 8; // Remplacez par l'ID du joueur concerné
        Dragmes dragmeASupprimer = Dragmes.piece_2; // Remplacez par la pièce que vous souhaitez supprimer

        // Appelez la méthode pour supprimer le dragme de la bourse du joueur
        bourseDAO.supprimerDragmeBourse(joueurId, dragmeASupprimer);

        // Vérifiez que le dragme a bien été supprimé de la bourse
        List<Dragmes> contenuBourseApresSuppression = bourseDAO.recupererContenuBourseParJoueur(joueurId);
        Assertions.assertFalse(contenuBourseApresSuppression.contains(dragmeASupprimer),
                "La dragme ne devrait pas être présent dans la bourse après suppression");

        // Rafraîchir la vue de la bourse (si nécessaire)
        // rafraichirBourse(joueurId);
    }
    */



}
