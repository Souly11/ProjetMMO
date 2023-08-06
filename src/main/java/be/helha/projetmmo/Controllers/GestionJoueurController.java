package be.helha.projetmmo.Controllers;

import be.helha.projetmmo.DAO.JoueurDAO;
import be.helha.projetmmo.DAOImpl.JoueurDAOImpl;
import be.helha.projetmmo.Model.Joueur;
import be.helha.projetmmo.UseCaseImpl.GestionJoueurImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class GestionJoueurController implements Initializable {

    @FXML
    private final ObservableList<Joueur> tvObservableList = FXCollections.observableArrayList();
    @FXML
    private TableView<Joueur> joueur;

    @FXML
    private TableColumn<Joueur, String> pseudo;

    @FXML
    private TableColumn<Joueur, String> email;

    @FXML
    private TableColumn<Joueur, String> statut;
    @FXML
    private TextField pseudoTF;
    @FXML
    private TextField emailTF;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initialisationDesDonnes();
    }

    @FXML
    private void initialisationDesDonnes() {
        // Configurez les cellules des colonnes pour afficher les données correctement
        pseudo.setCellValueFactory(new PropertyValueFactory<>("pseudo"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        statut.setCellValueFactory(cellData -> {
            boolean status = cellData.getValue().isStatus();
            String statutValue = status ? "P" : "F";
            return new SimpleStringProperty(statutValue);
        });

        // Initialisez les données en appelant la méthode getAllPersonnages de la DAO
        try {
            JoueurDAO joueurDAO = new JoueurDAOImpl();
            List<Joueur> joueurs = joueurDAO.getAllPersonnages();

            // Créez un ObservableList à partir de la liste des joueurs pour l'afficher dans la TableView
            ObservableList<Joueur> joueursObservableList = FXCollections.observableArrayList(joueurs);

            // Affichez les joueurs dans la TableView
            joueur.setItems(joueursObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception selon vos besoins (par exemple, afficher un message d'erreur à l'utilisateur)
        }
    }

    @FXML
    private void ajouterJoueur() {
        GestionJoueurImpl gestionJoueur = new GestionJoueurImpl();
        String pseudo = pseudoTF.getText();
        String email = emailTF.getText();

        // Vérifier si le pseudo a au moins 3 caractères
        if (pseudo.length() < 3) {
            // Afficher un message d'erreur si le pseudo est trop court
            Alert errorDialog = new Alert(Alert.AlertType.ERROR);
            errorDialog.setTitle("Erreur");
            errorDialog.setHeaderText(null);
            errorDialog.setContentText("Le pseudo doit contenir au moins 3 caractères.");
            errorDialog.showAndWait();
            return;
        }

        // Vérifier si l'email est au bon format (exemple@exemple.be)
        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.be$")) {
            // Afficher un message d'erreur si l'email n'est pas au bon format
            Alert errorDialog = new Alert(Alert.AlertType.ERROR);
            errorDialog.setTitle("Erreur");
            errorDialog.setHeaderText(null);
            errorDialog.setContentText("Le format de l'email est invalide. Veuillez utiliser le format exemple@exemple.be.");
            errorDialog.showAndWait();
            return;
        }

        if (pseudo != null && !pseudo.isEmpty() && email != null && !email.isEmpty()) {
            if (gestionJoueur.existeJoueur(pseudo, email)) {
                // Afficher un message d'erreur car le pseudo ou l'email existe déjà en base de données
                Alert confirmationDialog = new Alert(Alert.AlertType.ERROR);
                confirmationDialog.setTitle("Pseudo ou email déjà pris");
                confirmationDialog.setHeaderText(null);
                confirmationDialog.setContentText("Le pseudo ou l'email que vous avez saisi est déjà pris.");

                ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                confirmationDialog.getButtonTypes().setAll(buttonTypeOk);
                confirmationDialog.showAndWait();
            } else {
                // Ajouter le joueur car le pseudo et l'email ne sont pas déjà pris
                if (!gestionJoueur.ajouterJoueur(pseudo, email)) {
                    // Afficher un message d'erreur en cas d'échec de l'ajout du joueur
                    Alert confirmationDialog = new Alert(Alert.AlertType.ERROR);
                    confirmationDialog.setTitle("Erreur");
                    confirmationDialog.setHeaderText(null);
                    confirmationDialog.setContentText("Une erreur est survenue lors de l'ajout du joueur.");

                    ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                    confirmationDialog.getButtonTypes().setAll(buttonTypeOk);
                    confirmationDialog.showAndWait();
                } else {
                    // Afficher un message de succès en cas de succès de l'ajout du joueur
                    Alert confirmationDialog = new Alert(Alert.AlertType.INFORMATION);
                    confirmationDialog.setTitle("Succès");
                    confirmationDialog.setHeaderText(null);
                    confirmationDialog.setContentText("Le joueur a été ajouté avec succès.");

                    ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                    confirmationDialog.getButtonTypes().setAll(buttonTypeOk);
                    confirmationDialog.showAndWait();

                    tvObservableList.setAll(gestionJoueur.listJoueur());
                    pseudoTF.setText("");
                    emailTF.setText("");
                    joueur.refresh();
                }
            }
        } else {
            // Afficher un message d'erreur car le pseudo ou l'email est vide
            Alert confirmationDialog = new Alert(Alert.AlertType.ERROR);
            confirmationDialog.setTitle("Champs vides");
            confirmationDialog.setHeaderText(null);
            confirmationDialog.setContentText("Veuillez saisir un pseudo et un email.");

            ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            confirmationDialog.getButtonTypes().setAll(buttonTypeOk);
            confirmationDialog.showAndWait();
        }
    }


    @FXML
    private void supprimerJoueur() {
        GestionJoueurImpl gestionJoueur = new GestionJoueurImpl();
        Joueur joueurSelectionne = joueur.getSelectionModel().getSelectedItem();
        if (joueurSelectionne != null) {
            // Afficher une boîte de dialogue de confirmation de suppression
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation de suppression");
            confirmationDialog.setHeaderText(null);
            confirmationDialog.setContentText("Voulez-vous vraiment supprimer ce joueur ?");

            ButtonType buttonTypeOk = new ButtonType("OK ", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

            confirmationDialog.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);
            confirmationDialog.showAndWait().ifPresent(result -> {
                if (result == buttonTypeOk) {
                    gestionJoueur.supprimerJoueur(joueurSelectionne.getId());
                    tvObservableList.remove(joueurSelectionne);
                }
            });
        } else {
            // Afficher un message d'erreur indiquant qu'aucun joueur n'est sélectionné
            Alert errorDialog = new Alert(Alert.AlertType.ERROR);
            errorDialog.setTitle("Aucun joueur sélectionné");
            errorDialog.setHeaderText(null);
            errorDialog.setContentText("Veuillez sélectionner un joueur à supprimer.");
            errorDialog.showAndWait();
        }
    }




}
