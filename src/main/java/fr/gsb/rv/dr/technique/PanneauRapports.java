package fr.gsb.rv.dr.technique;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class PanneauRapports extends Parent {

    public PanneauRapports(){
        super();

        VBox rapports = new VBox(150);
        rapports.setPadding(new Insets(110, 125, 110, 125));
        rapports.getChildren().add(new Label("Rapports"));
        rapports.setStyle("-fx-background-color : white");
        this.getChildren().add(rapports);


    }
}
