package be.helha.projetmmo.DAOImpl;

import be.helha.projetmmo.DAO.Dao;

import java.util.HashSet;
import java.util.Set;

/**
 * La classe Persistance représente la configuration de la persistance des données de l'application.
 * Elle contient des informations telles que le type de persistance (par exemple, "DB" pour base de données),
 * l'URL d'accès à la base de données, le nom d'utilisateur et le mot de passe.
 *
 * De plus, cette classe gère la liste des noms de classes d'implémentation des DAO (Data Access Objects)
 * associés à cette configuration. Elle permet de rechercher et d'obtenir une instance de DAO à partir
 * d'une interface de DAO donnée.
 *
 * Les méthodes principales de cette classe sont :
 * - `getDaoImpl` : Renvoie une instance de DAO pour une interface de DAO spécifiée.
 * - `getUrl` : Renvoie l'URL d'accès à la base de données.
 * - `getUser` : Renvoie le nom d'utilisateur pour la base de données.
 * - `getPassword` : Renvoie le mot de passe pour la base de données.
 * - `getType` : Renvoie le type de persistance (par exemple, "DB").
 * - `getNbDaos` : Renvoie le nombre de classes d'implémentation des DAO spécifiées dans la configuration.
 *
 * Cette classe est essentielle pour la configuration et la gestion de la persistance des données de l'application.
 * Elle permet de créer des instances de DAO en fonction des interfaces spécifiées.
 */

public class Persistance {
    public static String DB = "DB";

    private String type; // type de persistance

    private Set<String> daos=new HashSet<>();
    // ensemble des noms des classes d'implémentation des daos

    private String url; // url d'accès à la db

    private String user; // login d'un utilisateur enregistré dans la db

    private String password;

    public Persistance() {
        super();
    }

    // renvoie l'instance du dao dont on spécifie l'interface
    @SuppressWarnings("unchecked")
    public Dao getDaoImpl(Class<? extends Dao> interfaceDao) {
        Dao dao;
        Class<Dao> classeDaoImpl;
        Class<?>[] interfaces;
        try {
            for (String nomDaoImpl : daos) {
                classeDaoImpl = (Class<Dao>) Class.forName(nomDaoImpl);
                interfaces = classeDaoImpl.getInterfaces();
                for (Class<?> i : interfaces) {
                    if ( i.getName().equals(interfaceDao.getName())) {
                        dao = classeDaoImpl.getDeclaredConstructor().newInstance();
                        return dao;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return this.type;
    }

    public int getNbDaos() {
        return this.daos.size();
    }


}
