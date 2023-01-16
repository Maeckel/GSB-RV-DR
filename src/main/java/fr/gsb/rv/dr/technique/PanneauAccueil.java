package fr.gsb.rv.dr.technique;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class PanneauAccueil extends Parent {

    public PanneauAccueil(){
        super();

        VBox accueil = new VBox(150);
        accueil.setPadding(new Insets(110, 125, 110, 125));
        Label label = new Label("Accueil");
        accueil.getChildren().add(label);
        label.setTextAlignment(TextAlignment.CENTER);
        accueil.setStyle("-fx-background-color : white");
        this.getChildren().add(accueil);

    }
}
