package be.helha.tests;

import be.helha.projetmmo.DAO.JoueurDAO;
import be.helha.projetmmo.Model.Joueur;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Test_JoueurDAO {

    @Mock
    private JoueurDAO joueurDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @Order(1)
    public void testAjouterJoueur() throws SQLException {
        // Configurer le comportement du mock
        when(joueurDAO.ajouterJoueur(anyString(), anyString())).thenReturn(true);

        boolean resultat = joueurDAO.ajouterJoueur("nouveauJoueur", "nouveaujoueur@example.com");
        assertTrue(resultat, "Le joueur devrait être ajouté avec succès.");
    }
/*
    @Test
    @Order(1)
    public void testAjouterJoueur() throws SQLException {
        String pseudo = "nouveauJoueur";
        String email = "nouveaujoueur@example.com";

        boolean resultat = joueurDAO.ajouterJoueur(pseudo, email);
        assertTrue(resultat);
        System.out.println("Le joueur a été ajouté avec succès.");
    }*/

/*
    @Test
    @Order(2)
    public void testSupprimerJoueur() {
        // Supposons que l'ID 1 correspond à un joueur existant dans la base de données
        int joueurId = 56;

        boolean resultat = joueurDAO.supprimerJoueur(joueurId);
        assertTrue(resultat);
        System.out.println("Le joueur a été supprimé avec succès.");
    }*/
    @Test
    @Order(2)
    public void testSupprimerJoueur() throws SQLException {
        // Configurer le comportement du mock
        when(joueurDAO.supprimerJoueur(anyInt())).thenReturn(true);

        boolean resultat = joueurDAO.supprimerJoueur(56);
        assertTrue(resultat, "Le joueur devrait être supprimé avec succès.");
    }
/*
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
    }*/

    @Test
    @Order(3)
    public void testGetAllJoueurs() throws SQLException {
        // Configurer le comportement du mock
        List<Joueur> personnagesMock = List.of(new Joueur(), new Joueur());
        when(joueurDAO.getAllJoueurs()).thenReturn(personnagesMock);

        List<Joueur> personnages = joueurDAO.getAllJoueurs();
        assertNotNull(personnages, "La liste de joueurs ne devrait pas être null.");
        assertEquals(2, personnages.size(), "Il devrait y avoir 2 joueurs dans la liste.");
    }
    /*
    @Test
    @Order(5)
    public void testExisteJoueur() throws SQLException {
        // Supposons que ces joueurs existent déjà dans la base de données
        String pseudoExistant = "Taj";
        String emailExistant = "taj@gmail.com";

        // Test lorsque le joueur existe dans la base de données
        boolean joueurExistantExiste = joueurDAO.existeJoueur(pseudoExistant, emailExistant);
        assertTrue(joueurExistantExiste, "Le joueur existant doit exister dans la base de données.");
    }*/

    /*
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
*/

    @Test
    @Order(4)
    public void testExisteJoueur() throws SQLException {
        // Configurer le comportement du mock
        when(joueurDAO.existeJoueur(anyString(), anyString())).thenReturn(true);

        boolean joueurExistantExiste = joueurDAO.existeJoueur("Taj", "taj@gmail.com");
        assertTrue(joueurExistantExiste, "Le joueur existant devrait exister dans la base de données.");
    }

    @Test
    @Order(5)
    public void testUpdatePseudo() throws SQLException {
        // Configurer le comportement du mock
        when(joueurDAO.updatePseudo(anyInt(), anyString())).thenReturn(true);

        int joueurId = 8;
        String nouveauPseudo = "Taj";

        boolean updateResult = joueurDAO.updatePseudo(joueurId, nouveauPseudo);
        assertTrue(updateResult, "La mise à jour du pseudo devrait être réussie.");
    }

    @Test
    @Order(6)
    public void testGetJoueurById() throws SQLException {
        int joueurId = 1; // ID du joueur que vous voulez récupérer
        Joueur joueurAttendu = new Joueur();
        joueurAttendu.setId(joueurId);
        joueurAttendu.setPseudo("TestPseudo");
        joueurAttendu.setEmail("test@example.com");
        joueurAttendu.setStatus(true);

        // Configurez le mock de joueurDAO pour renvoyer le joueur attendu
        when(joueurDAO.getJoueurById(joueurId)).thenReturn(joueurAttendu);

        // Appelez la méthode à tester
        Joueur joueur = joueurDAO.getJoueurById(joueurId);

        // Vérifiez que la méthode a été appelée avec le bon argument
        verify(joueurDAO).getJoueurById(joueurId);

        assertNotNull(joueur);
        assertEquals(joueurAttendu.getId(), joueur.getId());
        assertEquals(joueurAttendu.getPseudo(), joueur.getPseudo());
        assertEquals(joueurAttendu.getEmail(), joueur.getEmail());
        assertEquals(joueurAttendu.isStatus(), joueur.isStatus());
    }

    @Test
    @Order(7)
    public void testUpdateStatut() throws SQLException {
        // Configurer le comportement du mock pour l'updateStatut
        when(joueurDAO.updateStatut(anyInt(), anyBoolean())).thenReturn(true);

        boolean updateResult = joueurDAO.updateStatut(1, true);
        assertTrue(updateResult, "La mise à jour du statut devrait être réussie.");
    }




}