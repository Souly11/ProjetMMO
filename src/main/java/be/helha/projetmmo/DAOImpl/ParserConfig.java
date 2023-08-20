package be.helha.projetmmo.DAOImpl;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

/**
 * La classe ParserConfig est responsable de la lecture et de la validation de la configuration
 * de persistance à partir d'un fichier JSON. Elle extrait les informations de configuration
 * et les convertit en un objet de type Persistance.
 *
 * Le fichier JSON de configuration doit contenir des informations telles que l'URL de la base de données,
 * le nom d'utilisateur, le mot de passe, etc.
 *
 * La méthode `lireConfiguration` prend en paramètre le nom du fichier JSON de configuration, lit le fichier,
 * le désérialise en un objet Persistance à l'aide de la bibliothèque Gson, puis valide les champs de configuration.
 * En cas d'erreur dans le fichier de configuration, des exceptions sont levées.
 *
 * La méthode `validation` est utilisée pour valider les champs de configuration obligatoires.
 *
 * Cette classe joue un rôle essentiel dans la configuration de la persistance des données de l'application.
 *
 * @author El Kadaoui Soulyman
 */

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
