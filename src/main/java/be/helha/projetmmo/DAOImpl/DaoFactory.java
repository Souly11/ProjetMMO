package be.helha.projetmmo.DAOImpl;

import be.helha.projetmmo.DAO.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {
    private static final String FICHIER_CONFIGURATION = "src/main/resources/config.json";
    private static final DaoFactory INSTANCE = new DaoFactory();

    private Persistance persistance;

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    private DaoFactory() {
        try {
            this.persistance = ParserConfig.lireConfiguration(FICHIER_CONFIGURATION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // renvoie une connexion créée en utilisant les données du fichier de configuration
    public Connection getConnexion() {
        Connection connexion = null;
        try {
            connexion = DriverManager.getConnection(persistance.getUrl(), persistance.getUser(),
                    persistance.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connexion;
    }

    // renvoie l'instance du dao dont on spécifie l'interface
    public Dao getDaoImpl(Class<? extends Dao> interfaceDao) {
        return this.persistance.getDaoImpl(interfaceDao);
    }
}
