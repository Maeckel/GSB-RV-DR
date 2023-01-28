package fr.gsb.rv.dr.technique;

import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefConfiance;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefNotoriete;
import fr.gsb.rv.dr.utilitaires.ComparateurDateVisite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.Collections;
import java.util.Date;

import static javafx.collections.FXCollections.observableArrayList;

public class PanneauPracticiens extends Parent {

    public static int CRITERE_COEF_CONFIANCE = 1;
    public static int CRITERE_COEF_NOTORIETE = 2;
    public static int CRITERE_DATE_VISITE = 3;
    private int critereTri = CRITERE_COEF_CONFIANCE;

    ObservableList<Praticien> praticiens = FXCollections.observableArrayList();

    public PanneauPracticiens(){
        super();

        ToggleGroup groupe = new ToggleGroup();
        RadioButton Confiance = new RadioButton("Confiance");
        RadioButton Notoriété = new RadioButton("Notoriété");
        RadioButton DateVisite = new RadioButton("Date Visite");
        Confiance.setToggleGroup(groupe);
        Notoriété.setToggleGroup(groupe);
        DateVisite.setToggleGroup(groupe);
        Confiance.setSelected(true);
        Confiance.isVisible();
        Notoriété.isVisible();
        DateVisite.isVisible();

        TableView<Praticien> Table = new TableView<Praticien>();
        TableColumn<Praticien, Mois> colNumero = new TableColumn<Praticien, Mois>("Numero");
        TableColumn<Praticien, Mois> colNom = new TableColumn<Praticien, Mois>("Nom");
        TableColumn<Praticien, Mois> colVille = new TableColumn<Praticien, Mois>("Ville");
        TableColumn<Praticien, Double> colCoefNoto = new TableColumn<Praticien, Double>("coefNotoriete");
        TableColumn<Praticien, Date> colDate = new TableColumn<Praticien, Date>("dateDerniereVisite");
        TableColumn<Praticien, Integer> colCoefConf = new TableColumn<Praticien, Integer>("dernierCoefConfiance");
        colNumero.setCellValueFactory(new PropertyValueFactory<Praticien, Mois>("Numero"));
        colNom.setCellValueFactory(new PropertyValueFactory<Praticien, Mois>("Nom"));
        colVille.setCellValueFactory(new PropertyValueFactory<Praticien, Mois>("Ville"));
        colCoefNoto.setCellValueFactory(new PropertyValueFactory<Praticien, Double>("coefNotoriete"));
        colDate.setCellValueFactory(new PropertyValueFactory<Praticien, Date>("dateDerniereVisite"));
        colCoefConf.setCellValueFactory(new PropertyValueFactory<Praticien, Integer>("dernierCoefConfiance"));
        Table.getColumns().add(colNumero);
        Table.getColumns().add(colNom);
        Table.getColumns().add(colVille);
        Table.getColumns().add(colCoefNoto);
        Table.getColumns().add(colDate);
        Table.getColumns().add(colCoefConf);

        try {
            praticiens = ModeleGsbRv.getPraticiensHesitants();
        } catch (ConnexionException e) {
            throw new RuntimeException(e);
        }

        Table.setItems(praticiens);


        VBox practiciens = new VBox(50);
        practiciens.getChildren().add(new Label("Selectionner un critere de tri :"));
        practiciens.getChildren().add(Confiance);
        practiciens.getChildren().add(Notoriété);
        practiciens.getChildren().add(DateVisite);
        practiciens.getChildren().add(Table);
        practiciens.setStyle("-fx-background-color : white");
        this.getChildren().add(practiciens);

        Confiance.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        critereTri = CRITERE_COEF_CONFIANCE;
                        rafraichir();
                        for(Praticien unpraticien : praticiens) {
                            System.out.println();
                            System.out.println(unpraticien);
                        }
                    }
                }
        );

        Notoriété.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        critereTri = CRITERE_COEF_NOTORIETE;
                        rafraichir();
                        for(Praticien unpraticien : praticiens) {
                            System.out.println();
                            System.out.println(unpraticien);
                        }
                    }
                }
        );

        DateVisite.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        critereTri = CRITERE_DATE_VISITE;
                        rafraichir();
                        for(Praticien unpraticien : praticiens) {
                            System.out.println();
                            System.out.println(unpraticien);
                        }
                    }
                }
        );

    }

    public void rafraichir(){
        if(critereTri == CRITERE_COEF_CONFIANCE) {
            Collections.sort(praticiens, new ComparateurCoefConfiance());
        }
        else if(critereTri == CRITERE_COEF_NOTORIETE) {
            Collections.sort( praticiens , new ComparateurCoefNotoriete());
            Collections.reverse( praticiens);
        }
        else if (critereTri == CRITERE_DATE_VISITE) {
            Collections.sort( praticiens , new ComparateurDateVisite());
            Collections.reverse( praticiens);
        }
    }
    public int getCritereTri(){
        return this.critereTri;
    }

}
