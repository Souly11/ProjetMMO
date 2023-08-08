package be.helha.projetmmo.Controllers;

import be.helha.projetmmo.DAO.BourseDAO;
import be.helha.projetmmo.DAO.JoueurDAO;
import be.helha.projetmmo.DAOImpl.BourseDAOImpl;
import be.helha.projetmmo.DAOImpl.JoueurDAOImpl;
import be.helha.projetmmo.Model.Dragmes;
import be.helha.projetmmo.Model.Joueur;
import be.helha.projetmmo.UseCaseImpl.GestionJoueurImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
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

    GestionJoueurImpl gestionJoueur = new GestionJoueurImpl();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialisationDesDonnes();
        pseudoTF.setPromptText("Entrez votre pseudo");
        emailTF.setPromptText("Entrez votre e-mail (exemple@exemple.be)");

    }

    @FXML
    private void initialisationDesDonnes() {
        // Configurez les cellules des colonnes pour afficher les données correctement
        pseudo.setCellValueFactory(new PropertyValueFactory<>("pseudo"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        statut.setCellValueFactory(cellData -> {
            boolean status = cellData.getValue().isStatus();
            String statutValue = status ? "Premium" : "Free";
            return new SimpleStringProperty(statutValue);
        });

        // Initialisez les données en appelant la méthode getAllPersonnages de la DAO
        JoueurDAO joueurDAO = new JoueurDAOImpl();
        List<Joueur> joueurs = gestionJoueur.listJoueur();

        // Ajoutez les joueurs à l'ObservableList tvObservableList
        tvObservableList.addAll(joueurs);

        // Affichez les joueurs dans la TableView
        joueur.setItems(tvObservableList);
    }

    @FXML
    private void SelectJoueur(MouseEvent mouseEvent) throws SQLException {
        if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2) {
            Joueur selectedJoueur = joueur.getSelectionModel().getSelectedItem();
            // Récupérer le contenu de la bourse et le montant total
            BourseDAO bourseDAO = new BourseDAOImpl();
            List<Dragmes> contenuBourse = gestionJoueur.recupererContenuBourseParJoueur(selectedJoueur.getId());
            int montantTotalBourse = gestionJoueur.calculerTotalBourseParJoueur(selectedJoueur.getId());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/GestionJoueur.fxml"));
            Parent userDetailsParent;
            try {
                userDetailsParent = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            GestionJoueurController joueurDetailsController = loader.getController();
            joueurDetailsController.setMainController(this);
            joueurDetailsController.initData(selectedJoueur, contenuBourse, montantTotalBourse);

            // Créer une nouvelle scène et une nouvelle fenêtre pour la nouvelle vue
            Scene userDetailsScene = new Scene(userDetailsParent);
            Stage nouvelleFenetreStage = new Stage();
            nouvelleFenetreStage.setScene(userDetailsScene);

            // Afficher la nouvelle fenêtre en mode modal (empêche l'interaction avec la fenêtre principale)
            nouvelleFenetreStage.initModality(Modality.APPLICATION_MODAL);
            nouvelleFenetreStage.showAndWait();

            refreshTableView();
        }
    }


    @FXML
    private void ajouterJoueur() {
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
                // Afficher un message d'erreur, car le pseudo ou l'email existe déjà en base de données
                Alert confirmationDialog = new Alert(Alert.AlertType.ERROR);
                confirmationDialog.setTitle("Pseudo ou email déjà pris");
                confirmationDialog.setHeaderText(null);
                confirmationDialog.setContentText("Le pseudo ou l'email que vous avez saisi est déjà pris.");

                ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                confirmationDialog.getButtonTypes().setAll(buttonTypeOk);
                confirmationDialog.showAndWait();
            } else {
                // Ajouter le joueur, car le pseudo et l'email ne sont pas déjà pris
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
            // Afficher un message d'erreur, car le pseudo ou l'email est vide
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

    // Méthode pour rafraîchir la TableView après les modifications dans la deuxième fenêtre
    public void refreshTableView() {
        tvObservableList.clear();
        JoueurDAO joueurDAO = new JoueurDAOImpl();
        List<Joueur> joueurs = gestionJoueur.listJoueur();
        tvObservableList.addAll(joueurs);
        joueur.setItems(tvObservableList);
    }

    // Méthode pour mettre à jour le pseudo dans le premier contrôleur à partir du deuxième contrôleur
    public void updatePseudoInMainController(int joueurId, String nouveauPseudo) {
        for (Joueur joueur : tvObservableList) {
            if (joueur.getId() == joueurId) {
                joueur.setPseudo(nouveauPseudo);
                break;
            }
        }
    }

    // Méthode pour mettre à jour le statut dans le premier contrôleur à partir du deuxième contrôleur
    public void updateStatusInMainController(int joueurId, boolean newStatus) {
        for (Joueur joueur : tvObservableList) {
            if (joueur.getId() == joueurId) {
                joueur.setStatus(newStatus);
                break;
            }
        }
    }


}
