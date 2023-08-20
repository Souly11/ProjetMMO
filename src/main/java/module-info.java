module be.helha.projetmmo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;



    opens be.helha.projetmmo to javafx.fxml;
    exports be.helha.projetmmo;
    exports be.helha.projetmmo.Controllers;
    exports be.helha.projetmmo.Model;
    opens be.helha.projetmmo.DAOImpl;
    opens be.helha.projetmmo.Controllers to javafx.fxml;
    opens be.helha.projetmmo.Model;

}