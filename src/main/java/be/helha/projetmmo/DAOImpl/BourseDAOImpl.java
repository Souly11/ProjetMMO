package be.helha.projetmmo.DAOImpl;

import be.helha.projetmmo.DAO.BourseDAO;
import be.helha.projetmmo.Model.Bourse;
import be.helha.projetmmo.Model.Dragmes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BourseDAOImpl implements BourseDAO {

    // Méthode pour ajouter une bourse dans la base de données
    @Override
    public boolean ajouterBourse(Bourse bourse) {
        Connection con = null;
        PreparedStatement ps = null;
        int idBourse = -1; // Valeur par défaut en cas d'échec d'ajout de la bourse
        try {
            con = DaoFactory.getInstance().getConnexion();
            String query = "INSERT INTO bourse (joueur_id, contenu, limite) VALUES (?, ?, ?)";
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, bourse.getJoueur().getId());

            // Convertir la liste de dragmes en JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String contenuJson = objectMapper.writeValueAsString(bourse.getDragmes());
            ps.setObject(2, contenuJson, Types.OTHER);

            ps.setInt(3, bourse.getLimit());

            ps.executeUpdate();

            // Récupérer l'ID généré automatiquement pour la nouvelle bourse
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                idBourse = generatedKeys.getInt(1);
                bourse.setId(idBourse); // Mettre à jour l'ID de la bourse dans l'objet Bourse
            } else {
                // Échec de l'ajout de la bourse
                idBourse = -1;
                return false;
            }
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public boolean supprimerBourseParJoueur(int joueurId) {
        boolean resultat = true;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DaoFactory.getInstance().getConnexion();
            String query = "DELETE FROM bourse WHERE joueur_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, joueurId);
            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted == 0) {
                // Aucune bourse supprimée, joueurId n'a peut-être pas de bourse associée
                resultat = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultat = false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return resultat;
    }


    @Override
    public List<Dragmes> recupererContenuBourseParJoueur(int idJoueur) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs;
        List<Dragmes> contenuBourse = new ArrayList<>();

        try {
            // Établir la connexion à la base de données
            con = DaoFactory.getInstance().getConnexion();
            // Préparer la requête SQL pour récupérer le contenu de la bourse par l'ID du joueur
            String query = "SELECT contenu FROM bourse WHERE joueur_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, idJoueur);

            // Exécuter la requête
            rs = ps.executeQuery();

            // Si la bourse est trouvée, récupérer le contenu (sous forme de JSON)
            if (rs.next()) {
                String contenuJson = rs.getString("contenu");

                // Convertir le contenu JSON en une liste d'objets Dragmes
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.findAndRegisterModules(); // Enregistrez les modules pour les énumérations
                contenuBourse = objectMapper.readValue(contenuJson, new TypeReference<List<Dragmes>>() {});
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            // Gérer l'exception selon vos besoins (par exemple, afficher un message d'erreur à l'utilisateur)
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return contenuBourse;
    }
    @Override
    public int calculerTotalBourseParJoueur(int idJoueur) {
        List<Dragmes> contenuBourse = recupererContenuBourseParJoueur(idJoueur);
        return contenuBourse.stream().mapToInt(Dragmes::getValeur).sum();
    }

    @Override
    public void ajouterDragmeBourse(int joueurId, Dragmes dragme) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            // Établir la connexion à la base de données
            con = DaoFactory.getInstance().getConnexion();

            // Vérifier le nombre d'objets actuellement dans la bourse
            List<Dragmes> contenuBourse = recupererContenuBourseParJoueur(joueurId);
            if (contenuBourse.size() >= 10) {
                System.out.println("La bourse est pleine. Impossible d'ajouter une nouvelle pièce/billet.");
                return;
            }

            // Convertir l'objet Dragmes en JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String contenuJson = objectMapper.writeValueAsString(dragme);

            // Ajouter la nouvelle pièce à la liste
            contenuBourse.add(dragme);

            // Convertir la liste complète en JSON
            String contenuBourseJson = objectMapper.writeValueAsString(contenuBourse);

            // Préparer la requête SQL pour mettre à jour le contenu de la bourse du joueur
            String query = "UPDATE bourse SET contenu = ?::json WHERE joueur_id = ?";
            ps = con.prepareStatement(query);

            // Définir les paramètres de la requête
            ps.setString(1, contenuBourseJson);
            ps.setInt(2, joueurId);

            // Exécuter la requête de mise à jour
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("La dragme a été ajoutée à la bourse du joueur.");
            } else {
                System.out.println("Erreur lors de l'ajout de la pièce à la bourse du joueur.");
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            // Gérer l'exception selon vos besoins (par exemple, afficher un message d'erreur à l'utilisateur)
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void supprimerDragmeBourse(int joueurId, Dragmes dragme) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DaoFactory.getInstance().getConnexion();

            // Récupérer le contenu actuel de la bourse du joueur
            List<Dragmes> contenuBourse = recupererContenuBourseParJoueur(joueurId);

            // Supprimer le dragme de la liste du contenu
            contenuBourse.remove(dragme);

            // Convertir la liste de contenu mise à jour en JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String contenuJson = objectMapper.writeValueAsString(contenuBourse);

            // Préparer la requête SQL pour mettre à jour le contenu de la bourse du joueur
            String query = "UPDATE bourse SET contenu = ?::json WHERE joueur_id = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, contenuJson);
            ps.setInt(2, joueurId);

            // Exécuter la requête de mise à jour
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Le dragme a été supprimé de la bourse du joueur.");
            } else {
                System.out.println("Erreur lors de la suppression du dragme de la bourse du joueur.");
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            // fermetures des ressources
        }
    }







}
