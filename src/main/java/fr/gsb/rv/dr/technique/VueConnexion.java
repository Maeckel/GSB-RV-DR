package fr.gsb.rv.dr.technique;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Pair;

import static javafx.scene.control.ButtonBar.ButtonData.OK_DONE;

public class VueConnexion extends Dialog<Pair<String,String>> {

    public VueConnexion() {
       super();

       this.setTitle("Authentification");
       this.setHeaderText("Saisir vos données de connexion");

       TextField Matricule = new TextField();
       TextField Mdp = new PasswordField();
       Label matricule = new Label("Matricule :");
       Label mdp = new Label("Mot de passe :");

       VBox saisie = new VBox();
       saisie.getChildren().add(matricule);
       saisie.getChildren().add( Matricule );
       saisie.getChildren().add(mdp);
       saisie.getChildren().add( Mdp );

       this.getDialogPane().setContent(saisie);

       ButtonType OK_DONE = new ButtonType("Se connecter");
       ButtonType CANCEL_CLOSE = new ButtonType("Annuler");
       this.getDialogPane().getButtonTypes().addAll(OK_DONE , CANCEL_CLOSE);

       setResultConverter(
               new Callback<ButtonType, Pair<String, String>>() {
                   @Override
                   public Pair<String, String> call(ButtonType typeBouton) {
                       if (typeBouton == OK_DONE) {
                           return new Pair<String,String>( Matricule.getText() , Mdp.getText() );
                       }
                       Platform.exit();
                       return null;
                   }
               }
       );

    }

}
