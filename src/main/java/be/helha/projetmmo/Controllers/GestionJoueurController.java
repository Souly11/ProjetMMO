package be.helha.projetmmo.Controllers;

import be.helha.projetmmo.DAO.BourseDAO;
import be.helha.projetmmo.DAOImpl.BourseDAOImpl;
import be.helha.projetmmo.Model.Dragmes;
import be.helha.projetmmo.Model.Joueur;
import be.helha.projetmmo.UseCase.GestionJoueur;
import be.helha.projetmmo.UseCaseImpl.GestionJoueurImpl;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class GestionJoueurController implements Initializable {

    @FXML
    private Label pseudo;

    @FXML
    private Label email;

    @FXML
    private Label montant;

    private int montantTotalBourse;

    @FXML
    private Label statut;

    @FXML
    private Button boutonAjouterBillet20;

    @FXML
    private Button boutonAjouterBillet50;

    @FXML
    private TableView<Dragmes> bourse;

    @FXML
    private final ObservableList<Dragmes> tvObservableList = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Dragmes, String> dragme;

    @FXML
    private TextField pseudoTF;
    private Joueur joueur;
    BourseDAOImpl bourseDAO = new BourseDAOImpl();
    GestionJoueur gestionJoueur = new GestionJoueurImpl();
    @FXML
    private RadioButton premiumRadioButton;

    @FXML
    private RadioButton freeRadioButton;

    private final ToggleGroup toggleGroup = new ToggleGroup();

    private MainController mainController;

    private int billet20Count = 0; // Compteur pour les billets de 20
    private int billet50Count = 0; // Compteur pour les billets de 50

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void initData(Joueur joueur, List<Dragmes> contenuBourse, int montantTotalBourse) {
        this.joueur = joueur;
        pseudo.setText(joueur.getPseudo());
        email.setText(joueur.getEmail());
        statut.setText(joueur.isStatus() ? "Premium" : "Free");
        this.montantTotalBourse = montantTotalBourse; // Stocker le montant total de la bourse

        // Récupérer le contenu de la bourse depuis la base de données
        contenuBourse = gestionJoueur.recupererContenuBourseParJoueur(joueur.getId());

        // Compter le nombre de billets de 20 et de 50 dans la bourse
        int billet20CountFromDB = 0;
        int billet50CountFromDB = 0;
        for (Dragmes dragmes : contenuBourse) {
            if (dragmes == Dragmes.billet_20) {
                billet20CountFromDB++;
            } else if (dragmes == Dragmes.billet_50) {
                billet50CountFromDB++;
            }
        }
        // Mettre à jour les compteurs en fonction de la base de données
        billet20Count = billet20CountFromDB;
        billet50Count = billet50CountFromDB;

        // Afficher le contenu de la bourse dans le TableView
        afficherContenuBourse(contenuBourse);
        // Afficher le montant total de la bourse
        montant.setText(String.valueOf(montantTotalBourse) + " Dragmes");
        // Associer les RadioButton au groupe de bascule
        premiumRadioButton.setToggleGroup(toggleGroup);
        freeRadioButton.setToggleGroup(toggleGroup);

        // Mettre à jour la sélection en fonction du statut initial du joueur
        if (joueur.isStatus()) {
            premiumRadioButton.setSelected(true);
        } else {
            freeRadioButton.setSelected(true);
        }
    }

    private void afficherContenuBourse(List<Dragmes> contenuBourse) {
        dragme.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().toString()));
        tvObservableList.setAll(contenuBourse);
        bourse.setItems(tvObservableList);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pseudoTF.setPromptText("Entrez un nouveau pseudo");

        // Vérifier si la liste de contenu de la bourse est vide
        if (tvObservableList.isEmpty()) {
            // Créer un Label personnalisé pour afficher le message personnalisé
            Label messageLabel = new Label("Votre bourse \nest vide.\nVeuillez ajouter \ndes dragmes.");
            messageLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");
            bourse.setPlaceholder(messageLabel);
        } else {
            // La liste de contenu de la bourse n'est pas vide, afficher le contenu dans le TableView
            afficherContenuBourse(tvObservableList);
        }


    }

    @FXML
    private void updatePseudoJoueur() {
        String nouveauPseudo = pseudoTF.getText();

        if (nouveauPseudo != null && !nouveauPseudo.isEmpty()) {
            if (nouveauPseudo.length() < 3) {
                // Afficher une alerte d'erreur si le nouveau pseudo est trop court
                Alert errorDialog = new Alert(Alert.AlertType.ERROR);
                errorDialog.setTitle("Erreur");
                errorDialog.setHeaderText(null);
                errorDialog.setContentText("Le nouveau pseudo doit contenir au moins 3 caractères.");
                errorDialog.showAndWait();
                pseudoTF.clear();
                return; // Sortir de la méthode pour éviter de poursuivre la mise à jour avec un pseudo incorrect
            }

            if (nouveauPseudo.equalsIgnoreCase(joueur.getPseudo())) {
                // Afficher une alerte d'erreur si le nouveau pseudo est identique à l'ancien
                Alert errorDialog = new Alert(Alert.AlertType.ERROR);
                errorDialog.setTitle("Erreur");
                errorDialog.setHeaderText(null);
                errorDialog.setContentText("Le nouveau pseudo est identique à l'ancien.");
                errorDialog.showAndWait();
                pseudoTF.clear();
                return; // Sortir de la méthode pour éviter de poursuivre la mise à jour avec un pseudo identique
            }

            try {
                GestionJoueur gestionJoueur = new GestionJoueurImpl();
                boolean updated = gestionJoueur.updatePseudo(joueur.getId(), nouveauPseudo);

                if (updated) {
                    joueur.setPseudo(nouveauPseudo);
                    pseudo.setText(nouveauPseudo);
                    pseudoTF.clear();
                    // Afficher une alerte de succès si la mise à jour du pseudo est réussie
                    Alert confirmationDialog = new Alert(Alert.AlertType.INFORMATION);
                    confirmationDialog.setTitle("Succès");
                    confirmationDialog.setHeaderText(null);
                    confirmationDialog.setContentText("Le pseudo a été mis à jour avec succès !");
                    confirmationDialog.showAndWait();
                } else {
                    // Afficher une alerte d'erreur si la mise à jour du pseudo échoue
                    Alert errorDialog = new Alert(Alert.AlertType.ERROR);
                    errorDialog.setTitle("Erreur");
                    errorDialog.setHeaderText(null);
                    errorDialog.setContentText("Une erreur est survenue lors de la mise à jour du pseudo.");
                    errorDialog.showAndWait();
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
                // Afficher une alerte d'erreur en cas d'exception
                Alert errorDialog = new Alert(Alert.AlertType.ERROR);
                errorDialog.setTitle("Erreur");
                errorDialog.setHeaderText(null);
                errorDialog.setContentText("Une erreur est survenue lors de la mise à jour du pseudo.");
                errorDialog.showAndWait();
            }
        } else {
            // Afficher une alerte d'erreur si le nouveau pseudo est vide
            Alert errorDialog = new Alert(Alert.AlertType.ERROR);
            errorDialog.setTitle("Erreur");
            errorDialog.setHeaderText(null);
            errorDialog.setContentText("Veuillez saisir un nouveau pseudo.");
            errorDialog.showAndWait();
        }
        // Après avoir mis à jour le pseudo, appelez la méthode "updatePseudoInMainController" dans le contrôleur "MainController"
        mainController.updatePseudoInMainController(joueur.getId(), nouveauPseudo);
    }

    @FXML
    private void updateStatut() {
        // Mettre à jour le statut du joueur en fonction du RadioButton sélectionné
        boolean newStatus = premiumRadioButton.isSelected();
        joueur.setStatus(newStatus);

        // Mettre à jour le texte du label en fonction du nouveau statut
        if (newStatus) {
            statut.setText("Premium");
            statut.setStyle("-fx-text-fill: orange;");
        } else {
            statut.setText("Free");
            statut.setStyle("-fx-text-fill: blanc;");
        }
        // Mettre à jour le statut du joueur en base de données
        GestionJoueur gestionJoueur = new GestionJoueurImpl();
        boolean updated = gestionJoueur.updateStatut(joueur.getId(), newStatus);
        if (!updated) {
            // Gérer l'erreur si la mise à jour en base de données échoue
            System.err.println("Erreur : la mise à jour du statut en base de données a échoué.");
        }
        // Après avoir mis à jour le statut, appelez la méthode "updateStatusInMainController" dans le contrôleur "MainController"
        mainController.updateStatusInMainController(joueur.getId(), newStatus);
    }

    private void rafraichirBourse(int joueurId) {
        // Mettre à jour la tableview avec le contenu de la bourse du joueur
        List<Dragmes> contenuBourse = gestionJoueur.recupererContenuBourseParJoueur(joueurId);
        bourse.getItems().clear();
        bourse.getItems().addAll(contenuBourse);
    }

    @FXML
    private void ajouterPieceOuBillet(Dragmes dragme) {
        int joueurId = joueur.getId();
        int limite = 10; // Limite de 10 objets dans la bourse

        // Récupérer le contenu actuel de la bourse
        List<Dragmes> contenuBourse = gestionJoueur.recupererContenuBourseParJoueur(joueurId);

        // Vérifier si la limite est atteinte
        if (contenuBourse.size() >= limite) {
            // Afficher une alerte pour indiquer que la limite est atteinte
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Limite atteinte");
            alert.setHeaderText(null);
            alert.setContentText("La limite de la bourse est atteinte (10 éléments maximum). Vous ne pouvez plus ajouter de dragme.");
            alert.showAndWait();
        } else {
            // Ajouter la pièce ou le billet à la bourse du joueur
            gestionJoueur.ajouterDragmeBourse(joueurId, dragme);

            // Mettre à jour le montant total en ajoutant la valeur du dragme
            montantTotalBourse += dragme.getValeur();

            // Mettre à jour l'affichage du montant total en utilisant Platform.runLater()
            Platform.runLater(() -> montant.setText(String.valueOf(montantTotalBourse) + " Dragmes"));

            // Rafraîchir la vue de la bourse
            rafraichirBourse(joueurId);
        }
    }

    @FXML
    private void ajouterPiece1() {
        ajouterPieceOuBillet(Dragmes.piece_1);
    }

    @FXML
    private void ajouterPiece2() {
        ajouterPieceOuBillet(Dragmes.piece_2);
    }

    @FXML
    private void ajouterBillet5() {
        ajouterPieceOuBillet(Dragmes.billet_5);
    }

    @FXML
    private void ajouterBillet10() {
        ajouterPieceOuBillet(Dragmes.billet_10);
    }

    @FXML
    private void ajouterBillet20() throws SQLException {
        int joueurId = joueur.getId();
        // Vérifier le statut du joueur
        boolean isPremium = joueur.isStatus();

        if (isPremium && (billet20Count < 1)) {
            // Ajouter le billet de 20 à la bourse du joueur
            BourseDAO bourseDAO = new BourseDAOImpl();
            bourseDAO.ajouterDragmeBourse(joueurId, Dragmes.billet_20);

            // Incrémenter le compteur de billets de 20
            billet20Count++;
            montantTotalBourse += Dragmes.billet_20.getValeur();
            Platform.runLater(() -> montant.setText(String.valueOf(montantTotalBourse) + " Dragmes"));
            // Rafraîchir la vue de la bourse
            rafraichirBourse(joueurId);
        } else if (!isPremium) {
            // Afficher une alerte indiquant que l'opération n'est pas autorisée pour les joueurs 'free'
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Opération non autorisée");
            alert.setHeaderText(null);
            alert.setContentText("Les joueurs de statut 'free' ne peuvent pas ajouter de billets de 20.");
            alert.showAndWait();
        } else {
            // Afficher une alerte indiquant que le joueur a déjà atteint le maximum de billets de 20
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Opération non autorisée");
            alert.setHeaderText(null);
            alert.setContentText("La limite des billets de 20 est de 1.");
            alert.showAndWait();
        }
    }

    @FXML
    private void ajouterBillet50() throws SQLException {
        int joueurId = joueur.getId();
        // Vérifier le statut du joueur
        boolean isPremium = joueur.isStatus();

        if (isPremium && (billet50Count < 1)) {
            // Ajouter le billet de 50 à la bourse du joueur
            BourseDAO bourseDAO = new BourseDAOImpl();
            bourseDAO.ajouterDragmeBourse(joueurId, Dragmes.billet_50);

            // Incrémenter le compteur de billets de 50
            billet50Count++;
            montantTotalBourse += Dragmes.billet_50.getValeur();
            Platform.runLater(() -> montant.setText(String.valueOf(montantTotalBourse) + " Dragmes"));
            // Rafraîchir la vue de la bourse
            rafraichirBourse(joueurId);
        } else if (!isPremium) {
            // Afficher une alerte indiquant que l'opération n'est pas autorisée pour les joueurs 'free'
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Opération non autorisée");
            alert.setHeaderText(null);
            alert.setContentText("Les joueurs de statut 'free' ne peuvent pas ajouter de billets de 50.");
            alert.showAndWait();
        } else {
            // Afficher une alerte indiquant que le joueur a déjà atteint le maximum de billets de 50
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Opération non autorisée");
            alert.setHeaderText(null);
            alert.setContentText("La limite des billets de 50 est de 1.");
            alert.showAndWait();
        }
    }

    @FXML
    private void supprimerDragme() {
        Dragmes dragmeSelectionne = bourse.getSelectionModel().getSelectedItem();
        if (dragmeSelectionne != null) {
            // Afficher une boîte de dialogue de confirmation de suppression
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation de suppression");
            confirmationDialog.setHeaderText(null);
            confirmationDialog.setContentText("Voulez-vous vraiment supprimer ce dragme de la bourse ?");

            ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

            confirmationDialog.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);
            confirmationDialog.showAndWait().ifPresent(result -> {
                if (result == buttonTypeOk) {
                    // Récupérer l'ID du joueur (remplacez 8 par la valeur appropriée)
                    int joueurId = joueur.getId();

                    gestionJoueur.supprimerDragmeBourse(joueurId, dragmeSelectionne);

                    // Mettre à jour le montant total en soustrayant la valeur du dragme
                    montantTotalBourse -= dragmeSelectionne.getValeur();
                    Platform.runLater(() -> montant.setText(String.valueOf(montantTotalBourse) + " Dragmes"));
                    // Rafraîchir la vue de la bourse (vous devez mettre à jour la tableview avec le contenu de la bourse)
                    rafraichirBourse(joueurId);
                }
            });
        } else {
            // Afficher un message d'erreur indiquant qu'aucun dragme n'est sélectionné
            Alert errorDialog = new Alert(Alert.AlertType.ERROR);
            errorDialog.setTitle("Aucun dragme sélectionné");
            errorDialog.setHeaderText(null);
            errorDialog.setContentText("Veuillez sélectionner une dragme à supprimer.");
            errorDialog.showAndWait();
        }
    }





}
