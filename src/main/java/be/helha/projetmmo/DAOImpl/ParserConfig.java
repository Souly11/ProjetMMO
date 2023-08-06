package be.helha.projetmmo.DAOImpl;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class ParserConfig {

    // Méthode chargée de construire un objet de type Persistance.
    // Elle initialise ses attributs avec les valeurs extraites du fichier json.
    // Elle reçoit en paramètre le nom du fichier json de configuration.
    // Elle lance une exception si le fichier de configuration est incorrect.
    public static Persistance lireConfiguration(String fichierConfiguration) throws Exception {
        FileReader fr = new FileReader(fichierConfiguration, StandardCharsets.UTF_8);
        JsonReader reader = new JsonReader(fr);
        Gson gson = new Gson();
        Persistance persistance=gson.fromJson(reader, Persistance.class);
        reader.close();
        fr.close();
        validation(persistance);
        return persistance;
    }

    private static void validation(Persistance persistance) throws Exception {
        if (persistance.getNbDaos() == 0)
            throw new Exception("Il manque le champ <dao>");
        if (persistance.getType() == null)
            throw new Exception("Il manque le champ <type>");
        if (!persistance.getType().equals(Persistance.DB))
            throw new Exception("Type de persistance est incorrect");
        if (persistance.getUrl() == null)
            throw new Exception("Il manque le champ <url>");
        if (persistance.getUser() == null)
            throw new Exception("Il manque le champ <user>");
        if (persistance.getPassword() == null)
            throw new Exception("Il manque le champ <password>");
    }
}
