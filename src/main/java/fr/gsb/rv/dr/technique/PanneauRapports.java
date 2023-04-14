package fr.gsb.rv.dr.technique;

import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.entites.RapportVisite;
import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PanneauRapports extends Parent {

    private ComboBox<Visiteur> cbVisiteurs = new ComboBox<Visiteur>();
    private ComboBox<Mois> cbMois = new ComboBox<Mois>();
    private ComboBox<Integer> cbAnnee = new ComboBox<Integer>();

    ObservableList<RapportVisite> rapportsVisites = FXCollections.observableArrayList();

    public PanneauRapports(){
        super();

        try {
            cbVisiteurs.getItems().setAll(ModeleGsbRv.getVisiteurs());
        } catch (ConnexionException e) {
            throw new RuntimeException(e);
        }

        cbMois.getItems().setAll(Mois.values());

        LocalDate aujourdhui = LocalDate.now();
        int anneeCourante = aujourdhui.getYear();
        List<Integer> annees = new ArrayList<Integer>();
        annees.add(anneeCourante);
        int i = 0;
        while(i != 4){
            i = i + 1;
            annees.add(aujourdhui.minusYears(i).getYear());
        }
        cbAnnee.getItems().setAll(annees);
        Button OK_DONE = new Button("Valider");

        TableView<RapportVisite> Table = new TableView<RapportVisite>();
        TableColumn<RapportVisite,Integer> colNumero = new TableColumn<RapportVisite, Integer>("numero");
        TableColumn<RapportVisite,Date> colVisite = new TableColumn<RapportVisite, Date>("dateVisite");
        TableColumn<RapportVisite, Date> colRedaction= new TableColumn<RapportVisite, Date>("dateRedaction");
        TableColumn<RapportVisite, String> colNom = new TableColumn<RapportVisite, java.lang.String>("nom");
        TableColumn<RapportVisite, String> colVille = new TableColumn<RapportVisite, java.lang.String>("ville");
        colNumero.setCellValueFactory(new PropertyValueFactory<RapportVisite, Integer>("numero"));
        colNom.setCellValueFactory(
                param -> {
                    String nom = param.getValue().getLepraticien().getNom();
                    return new SimpleStringProperty(nom);
                }
        );
        colVille.setCellValueFactory(
                param -> {
                    String ville = param.getValue().getLepraticien().getVille();
                    return new SimpleStringProperty(ville);
                }
        );
        colVisite.setCellValueFactory(new PropertyValueFactory<RapportVisite, Date>("dateVisite"));
        colRedaction.setCellValueFactory(new PropertyValueFactory<RapportVisite, Date>("dateRedaction"));

        colVisite.setCellFactory(
                colonne -> {
                    return new TableCell<RapportVisite, Date>() {
                        @Override
                        protected void updateItem(Date item, boolean empty) {
                            super.updateItem(item, empty);

                            if (empty) {
                                setText("");
                            } else {
                                SimpleDateFormat formateur = new SimpleDateFormat("dd/MM/yyyy");
                                setText(formateur.format(item));
                            }
                        }
                    };
                }
        );

        colRedaction.setCellFactory(
                colonne -> {
                    return new TableCell<RapportVisite, Date>() {
                        @Override
                        protected void updateItem(Date item, boolean empty) {
                            super.updateItem(item, empty);

                            if (empty) {
                                setText("");
                            } else {
                                SimpleDateFormat formateur = new SimpleDateFormat("dd/MM/yyyy");
                                setText(formateur.format(item));
                            }
                        }
                    };
                }
        );

        Table.getColumns().add(colNumero);
        Table.getColumns().add(colVisite);
        Table.getColumns().add(colRedaction);
        Table.getColumns().add(colNom);
        Table.getColumns().add(colVille);


        Table.setOnMouseClicked(
                (MouseEvent event) -> {
                    if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                        int indiceRapporrt = Table.getSelectionModel().getSelectedIndex();
                        if(Table.getSelectionModel().getSelectedItem().isLu() != true) {
                            try {
                                ModeleGsbRv.setRapportVisiteLu(Table.getSelectionModel().getSelectedItem().getNumero());
                                System.out.println(Table.getSelectionModel().getSelectedItem().isLu());
                            } catch (ConnexionException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        rafraichir();
                        VueRapport vueRapport = new VueRapport(Table.getSelectionModel().getSelectedItem());
                        Optional<Pair<String, String>> reponse = vueRapport.showAndWait();
                        Table.setItems(rapportsVisites);
                    }
                }
        );

        Table.setRowFactory(
                ligne -> {
                    return new TableRow<RapportVisite>(){
                        @Override
                        protected void updateItem(RapportVisite item, boolean empty){
                            super.updateItem(item, empty);
                            if(item != null){
                                if(item.isLu() == true){
                                    setStyle("-fx-background-color: gold");
                                }
                                else{
                                    setStyle("-fx-background-color: cyan");
                                }
                            }
                        }
                    };
                }
        );

        VBox rapports = new VBox(30);
        rapports.getChildren().add(new Label("Rapports"));
        rapports.getChildren().add(cbVisiteurs);
        rapports.getChildren().add(cbMois);
        rapports.getChildren().add(cbAnnee);
        rapports.getChildren().add(Table);
        rapports.getChildren().add(OK_DONE);
        rapports.setStyle("-fx-background-color : white");
        this.getChildren().add(rapports);

        OK_DONE.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        rafraichir();
                        Table.setItems(rapportsVisites);
                    }
                }
        );

    }

    public void rafraichir(){
        //System.out.println(cbVisiteurs.getValue().toString() + cbMois.getValue().toString() + cbAnnee.getValue());
        try {
            rapportsVisites = ModeleGsbRv.getRapportsVisites( cbVisiteurs.getValue().getMatricule() , cbMois.getValue().getText() , cbAnnee.getValue());
        } catch (ConnexionException e) {
            throw new RuntimeException(e);
        }
    }

}
