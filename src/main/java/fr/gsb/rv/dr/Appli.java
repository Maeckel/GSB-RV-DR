package fr.gsb.rv.dr;

import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.entites.RapportVisite;
import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.*;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefConfiance;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefNotoriete;
import fr.gsb.rv.dr.utilitaires.ComparateurDateVisite;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Appli extends Application {

    @Override
    public void start(Stage primaryStage) throws ConnexionException {

        PanneauAccueil vueAccueil = new PanneauAccueil();
        PanneauRapports vueRapports = new PanneauRapports();
        PanneauPracticiens vuePracticiens = new PanneauPracticiens();
        StackPane Pile = new StackPane(vueAccueil , vueRapports , vuePracticiens);

        MenuBar Menu = new MenuBar();

        Menu Fichier = new Menu("Fichier");
        Menu Rapports = new Menu("Rapports");
        Menu Practiciens = new Menu("Practiciens");

        MenuItem seConnecter = new MenuItem("Se connecter");
        MenuItem seDeconnecter = new MenuItem("Se Deconnecter");
        MenuItem Quitter = new MenuItem("Quitter");
        MenuItem Consulter = new MenuItem("Consulter");
        MenuItem Hesitant = new MenuItem("Hésitants");

        Fichier.getItems().add(seConnecter);
        Fichier.getItems().add(seDeconnecter);
        Fichier.getItems().add(Quitter);
        Rapports.getItems().add(Consulter);
        Practiciens.getItems().add(Hesitant);

        Menu.getMenus().add(Fichier);
        Menu.getMenus().add(Rapports);
        Menu.getMenus().add(Practiciens);

        BorderPane root = new BorderPane();
        root.setTop(Menu);
        root.setCenter(vueAccueil);

        Visiteur visiteur = new Visiteur() ; //Création de l'utilisateur

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("GSB-RV-DR");
        primaryStage.setScene(scene);
        primaryStage.show();

        seConnecter.setVisible(true);
        seDeconnecter.setVisible(false);
        Rapports.setVisible(false);
        Practiciens.setVisible(false);

        List<RapportVisite> test = ModeleGsbRv.getRapportsVisites("b19", "07", 2022);

        seConnecter.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        VueConnexion vue = new VueConnexion();
                        Optional<Pair <String,String>> reponse = vue.showAndWait();
                        if(reponse.isPresent()) {
                            try {
                                ConnexionBD.getConnexion();
                                ModeleGsbRv.seConnecter(reponse.get().getKey(), reponse.get().getValue());
                                if (ModeleGsbRv.seConnecter(reponse.get().getKey(), reponse.get().getValue()) != null) {
                                    visiteur.setMatricule(ModeleGsbRv.seConnecter(reponse.get().getKey(), reponse.get().getValue()).getMatricule());
                                    visiteur.setNom(ModeleGsbRv.seConnecter(reponse.get().getKey(), reponse.get().getValue()).getNom());
                                    visiteur.setPrenom(ModeleGsbRv.seConnecter(reponse.get().getKey(), reponse.get().getValue()).getPrenom());
                                    Session.ouvrir(visiteur);
                                    primaryStage.setTitle(visiteur.getNom() + " " + visiteur.getPrenom()); //Affichage du nom et prénom de l'utilisateur
                                    seConnecter.setVisible(false);
                                    seDeconnecter.setVisible(true);
                                    Rapports.setVisible(true);
                                    Practiciens.setVisible(true);
                                }
                                else{
                                    System.out.println("[NOk] Matricule ou mot de passe incorrect !");
                                }
                            } catch (ConnexionException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        System.out.println(seConnecter.getText());
                    }
                }
        );

        seDeconnecter.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        System.out.println(seDeconnecter.getText());
                        seConnecter.setVisible(true);
                        seDeconnecter.setVisible(false);
                        Rapports.setVisible(false);
                        Practiciens.setVisible(false);
                        root.setCenter(vueAccueil);
                        if(Session.estOuverte() == true) {
                            Session.fermer();
                        }
                        primaryStage.setTitle("GSB-RV-DR"); //Fin de la session
                    }
                }
        );

        Quitter.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Alert alertQuitter = new Alert(Alert.AlertType.CONFIRMATION);
                        alertQuitter.setTitle("Quitter");
                        alertQuitter.setHeaderText("Demande de confirmation");
                        alertQuitter.setContentText("Voulez-vous quitter l'application ?");

                        ButtonType btnOui = new ButtonType("Oui");
                        ButtonType btnNon = new ButtonType("Non");
                        alertQuitter.getButtonTypes().setAll(btnOui, btnNon);
                        Optional<ButtonType> reponse = alertQuitter.showAndWait();
                        reponse.get();
                        if (reponse.get() == btnOui) {
                            Platform.exit();
                        }
                    }
                }
        );

        Consulter.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        root.setCenter(vueRapports);
                        System.out.println("[Rapports]" + " " + visiteur.getNom() + " " + visiteur.getPrenom()); //Affiche la chaine concaténée
                    }
                }
        );

        Hesitant.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        root.setCenter(vuePracticiens);
                        System.out.println("[Practiciens]" + " " + visiteur.getNom() + " " + visiteur.getPrenom()); //Affiche la chaine concaténée
                    }
                }
        );
    }
    public static void main(String[] args)  {
        launch(args) ;
    }
}
