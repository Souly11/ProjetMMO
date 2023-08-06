package be.helha.projetmmo.DAOImpl;

import be.helha.projetmmo.DAO.BourseDAO;
import be.helha.projetmmo.Model.Bourse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.*;

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


}
