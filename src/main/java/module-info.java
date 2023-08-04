module be.helha.projetmmo {
    requires javafx.controls;
    requires javafx.fxml;


    opens be.helha.projetmmo to javafx.fxml;
    exports be.helha.projetmmo;
}