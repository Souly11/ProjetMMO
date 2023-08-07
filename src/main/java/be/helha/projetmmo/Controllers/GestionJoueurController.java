package be.helha.projetmmo.Controllers;

import be.helha.projetmmo.Model.Bourse;
import be.helha.projetmmo.Model.Dragmes;
import be.helha.projetmmo.Model.Joueur;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class GestionJoueurController implements Initializable {

    @FXML
    public void switchToMainController(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/hello-view.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private Label pseudo;

    @FXML
    private Label email;

    @FXML
    private Label montant;

    // Ajout du champ pour le montant total de la bourse
    private int montantTotalBourse;

    @FXML
    private Label statut;

    @FXML
    private TableView<Dragmes> bourse;

    @FXML
    private final ObservableList<Dragmes> tvObservableList = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Dragmes, String> dragme;

    private Joueur joueur;
    public void initData(Joueur joueur, List<Dragmes> contenuBourse, int montantTotalBourse) {
        this.joueur = joueur;
        pseudo.setText(joueur.getPseudo());
        email.setText(joueur.getEmail());
        statut.setText(joueur.isStatus() ? "Premium" : "Free");
        this.montantTotalBourse = montantTotalBourse; // Stocker le montant total de la bourse

        // Afficher le contenu de la bourse dans le TableView
        afficherContenuBourse(contenuBourse);

        // Afficher le montant total de la bourse
        montant.setText(String.valueOf(montantTotalBourse));

    }

    private void afficherContenuBourse(List<Dragmes> contenuBourse) {
        dragme.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().toString()));
        tvObservableList.setAll(contenuBourse);
        bourse.setItems(tvObservableList);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }




}
