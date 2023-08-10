package be.helha.projetmmo.DAOImpl;

import be.helha.projetmmo.DAO.BourseDAO;
import be.helha.projetmmo.DAO.JoueurDAO;
import be.helha.projetmmo.Model.Bourse;
import be.helha.projetmmo.Model.Joueur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JoueurDAOImpl implements JoueurDAO {

    public Joueur getJoueurById(int joueurId) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Joueur joueur = null;

        try {
            con = DaoFactory.getInstance().getConnexion();
            String selectQuery = "SELECT * FROM joueur WHERE id = ?";
            stmt = con.prepareStatement(selectQuery);
            stmt.setInt(1, joueurId);

            rs = stmt.executeQuery();
            if (rs.next()) {
                joueur = new Joueur();
                joueur.setId(rs.getInt("id"));
                joueur.setPseudo(rs.getString("pseudo"));
                joueur.setEmail(rs.getString("mail"));
                joueur.setStatus(rs.getString("statut").equals("P"));
            }

            return joueur;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    @Override
    public boolean existeJoueur(String pseudo, String email) throws SQLException {
        boolean existe = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DaoFactory.getInstance().getConnexion();
            String query = "SELECT * FROM joueur WHERE pseudo = ? OR mail = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, pseudo);
            ps.setString(2, email);
            rs = ps.executeQuery();
            existe = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception appropriée (relever ou lancer une nouvelle exception par exemple)
        } finally {
            // Fermer les ressources de manière appropriée
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return existe;
    }

    public boolean ajouterJoueur(String pseudo, String email) {
        boolean statut = false;
        Connection con = null;
        PreparedStatement ps = null;
        boolean resultat = true;
        try {
            con = DaoFactory.getInstance().getConnexion();
            String query = "INSERT INTO joueur (pseudo, mail, statut) VALUES (?, ?, ?)";
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, pseudo);
            ps.setString(2, email);
            String lettreStatut = statut ? "P" : "F";
            ps.setString(3, lettreStatut);

            ps.executeUpdate();

            // Récupérer l'ID généré automatiquement pour le nouveau joueur
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int joueurId = generatedKeys.getInt(1);

                // Ajouter une nouvelle bourse correspondante au joueur
                BourseDAO bourseDAO = new BourseDAOImpl();
                Bourse bourse = new Bourse();
                bourse.setDragmes(new ArrayList<>()); // Contenu initial vide au format JSON
                bourse.setLimit(10); // Limite de la bourse à 10

                // Créer une instance de joueur et définir son ID
                Joueur joueur = new Joueur();
                joueur.setId(joueurId);
                bourse.setJoueur(joueur); // Définir le joueur pour la bourse

                // Ajout-er la bourse à la base de données
                boolean bourseAjoutee = bourseDAO.ajouterBourse(bourse);

                // Vérifier si l'ajout de la bourse a réussi
                if (!bourseAjoutee) {
                    resultat = false;
                }
            } else {
                // Échec de l'ajout du joueur
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

    public boolean supprimerJoueur(int joueurId) {
        boolean resultat = true;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DaoFactory.getInstance().getConnexion();
            con.setAutoCommit(false); // Désactiver l'autocommit pour gérer la transaction

            // Supprimer la bourse associée au joueur
            BourseDAO bourseDAO = new BourseDAOImpl();
            boolean bourseSupprimee = bourseDAO.supprimerBourseParJoueur(joueurId);

            // Si la suppression de la bourse échoue, annuler toute la transaction
            if (!bourseSupprimee) {
                con.rollback();
                resultat = false;
            } else {
                // Supprimer le joueur lui-même
                String query = "DELETE FROM joueur WHERE id = ?";
                ps = con.prepareStatement(query);
                ps.setInt(1, joueurId);
                int rowsDeleted = ps.executeUpdate();

                if (rowsDeleted == 0) {
                    // Aucun joueur supprimé, joueurId n'existe pas
                    con.rollback();
                    resultat = false;
                } else {
                    // Valider la transaction
                    con.commit();
                }
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
                    con.setAutoCommit(true); // Rétablir l'autocommit
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return resultat;
    }

    @Override
    public List<Joueur> getAllJoueurs() throws SQLException {
        List<Joueur> joueurs = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = DaoFactory.getInstance().getConnexion();
            String query = "SELECT * FROM joueur";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Joueur joueur = new Joueur();
                joueur.setId(rs.getInt("id"));
                joueur.setPseudo(rs.getString("pseudo"));
                joueur.setEmail(rs.getString("mail"));
                joueur.setStatus(rs.getString("statut").equalsIgnoreCase("P"));
                // Vous pouvez récupérer d'autres attributs du joueur si nécessaire

                joueurs.add(joueur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception appropriée (relever ou lancer une nouvelle exception par exemple)
        } finally {
            // Fermer les ressources de manière appropriée
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return joueurs;
    }

    @Override
    public boolean updatePseudo(int joueurId, String nouveauPseudo) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = DaoFactory.getInstance().getConnexion();
            String updateQuery = "UPDATE joueur SET pseudo = ? WHERE id = ?";
            stmt = con.prepareStatement(updateQuery);
            stmt.setString(1, nouveauPseudo);
            stmt.setInt(2, joueurId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception appropriée (relever ou lancer une nouvelle exception par exemple)
            throw e;
        } finally {
            // Fermer les ressources de manière appropriée
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    // Classe GestionJoueurImpl
    @Override
    public boolean updateStatut(int joueurId, boolean newStatut) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = DaoFactory.getInstance().getConnexion();
            String updateQuery = "UPDATE joueur SET statut = ? WHERE id = ?";
            stmt = con.prepareStatement(updateQuery);

            // Convertir le statut boolean en chaîne "P" ou "F"
            String statutString = newStatut ? "P" : "F";

            stmt.setString(1, statutString);
            stmt.setInt(2, joueurId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }



}
