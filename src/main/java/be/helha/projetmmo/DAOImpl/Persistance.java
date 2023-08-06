package be.helha.projetmmo.DAOImpl;

import be.helha.projetmmo.DAO.Dao;

import java.util.HashSet;
import java.util.Set;

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
