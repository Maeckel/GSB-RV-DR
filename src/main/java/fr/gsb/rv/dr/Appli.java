package fr.gsb.rv.dr;

import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.technique.Session;
import fr.gsb.rv.dr.technique.VueConnexion;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Optional;

public class Appli extends Application {

    @Override
    public void start(Stage primaryStage) {

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

        Visiteur visiteur = new Visiteur() ; //Création de l'utilisateur

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("GSB-RV-DR");
        primaryStage.setScene(scene);
        primaryStage.show();

        seConnecter.setVisible(true);
        seDeconnecter.setVisible(false);
        Rapports.setVisible(false);
        Practiciens.setVisible(false);

        seConnecter.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        VueConnexion vue = new VueConnexion();
                        Optional<Pair <String,String>> reponse = vue.showAndWait();
                        try {
                            ConnexionBD.getConnexion();
                            ModeleGsbRv.seConnecter( reponse.get().getKey(), reponse.get().getValue() );
                            if(ModeleGsbRv.seConnecter( reponse.get().getKey(), reponse.get().getValue() ) != null){
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
                        } catch (ConnexionException e) {
                            throw new RuntimeException(e);
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
                        Session.fermer();
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
                        System.out.println("[Rapports]" + " " + visiteur.getNom() + " " + visiteur.getPrenom()); //Affiche la chaine concaténée
                    }
                }
        );

        Hesitant.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        System.out.println("[Practiciens]" + " " + visiteur.getNom() + " " + visiteur.getPrenom()); //Affiche la chaine concaténée
                    }
                }
        );
    }
    public static void main(String[] args)  {

        try {
            ConnexionBD.getConnexion();
            ModeleGsbRv.seConnecter( "a131" , "azerty" );
        } catch (ConnexionException e) {
            throw new RuntimeException(e);
        }
        launch(args) ;
    }
}
