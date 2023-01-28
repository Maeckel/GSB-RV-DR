package fr.gsb.rv.dr.technique;

import fr.gsb.rv.dr.entites.RapportVisite;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Pair;

public class VueRapport extends Dialog<Pair<String, String>> {

    private RapportVisite rapportVisite;
    public VueRapport(RapportVisite rapportVisite) {
        super();

        this.setTitle("Rapport de Visite");
        this.setHeaderText("Informations concernant le rapport de visite :");

        VBox saisie = new VBox(10);
        saisie.getChildren().add(new Label("Rapport :"));
        saisie.getChildren().add(new Label(""));
        saisie.getChildren().add(new Label(" Motif : " + rapportVisite.getMotif() + " | Date visite : " + rapportVisite.getDateVisite() + " | Coef Confiance : " + rapportVisite.getCoefConfiance()  + " | Bilan : "+ rapportVisite.getBilan() + " | Date Redaction : " + rapportVisite.getDateRedaction() ));
        saisie.getChildren().add(new Label(""));
        saisie.getChildren().add(new Label("Praticien :"));
        saisie.getChildren().add(new Label(""));
        saisie.getChildren().add(new Label("Nom : " + rapportVisite.getLepraticien().getNom() + " | Prénom : " + rapportVisite.getLepraticien().getPrenom() + " | Ville : " + rapportVisite.getLepraticien().getVille() + " | Adresse : " + rapportVisite.getLepraticien().getAdresse() + " | Code Postal : " + rapportVisite.getLepraticien().getCodePostal()));
        saisie.getChildren().add(new Label(""));
        saisie.getChildren().add(new Label("Visiteur :"));
        saisie.getChildren().add(new Label(""));
        saisie.getChildren().add(new Label("Matricule : " + rapportVisite.getLevisiteur().getMatricule() + " | Nom : " + rapportVisite.getLevisiteur().getNom() + " | Prénom : " + rapportVisite.getLevisiteur().getPrenom()));

        this.getDialogPane().setContent(saisie);
        ButtonType CANCEL_CLOSE = new ButtonType("Fermer", ButtonBar.ButtonData.CANCEL_CLOSE);
        this.getDialogPane().getButtonTypes().setAll(CANCEL_CLOSE);

        setOnCloseRequest(
                new EventHandler<DialogEvent>() {
                    @Override
                    public void handle(DialogEvent dialogEvent) {
                        close();
                    }
                }
        );

    }
}