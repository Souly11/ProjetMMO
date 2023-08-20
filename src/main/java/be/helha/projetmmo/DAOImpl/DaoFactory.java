package be.helha.projetmmo.DAOImpl;

import be.helha.projetmmo.DAO.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La classe DaoFactory est une fabrique de DAO (Data Access Objects) qui permet d'obtenir des connexions
 * à une base de données en utilisant les informations de configuration spécifiées dans un fichier JSON.
 * Elle fournit également des instances de DAO pour des interfaces données.
 *
 * La configuration de la base de données, telle que l'URL, le nom d'utilisateur et le mot de passe, est lue
 * à partir du fichier de configuration "config.json" situé dans le répertoire "src/main/resources".
 *
 * La classe DaoFactory suit le modèle Singleton, ce qui signifie qu'il existe une seule instance globale
 * de cette classe dans l'application, accessible via la méthode statique `getInstance()`.
 *
 * Pour obtenir une connexion à la base de données, vous pouvez utiliser la méthode `getConnexion()`. Cette méthode
 * renvoie une instance de `java.sql.Connection` basée sur les informations de configuration.
 *
 * Pour obtenir une instance de DAO pour une interface donnée, vous pouvez utiliser la méthode `getDaoImpl()`.
 * Elle renvoie une implémentation spécifique de DAO associée à l'interface donnée, en utilisant les informations
 * de configuration pour déterminer quelle implémentation utiliser.
 *
 * Cette classe utilise la bibliothèque Jackson pour lire les données de configuration à partir du fichier JSON.
 * En cas d'erreur lors de la lecture de la configuration, des exceptions peuvent être levées.
 *
 * @author El Kadaoui Soulyman
 */

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
