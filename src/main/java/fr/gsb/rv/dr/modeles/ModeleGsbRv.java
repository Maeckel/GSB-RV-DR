package fr.gsb.rv.dr.modeles;

import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;

public class ModeleGsbRv {

    public static Visiteur seConnecter(String matricule, String mdp) throws ConnexionException {

        // Code de test à compléter

        Connection connexion = ConnexionBD.getConnexion();

        String requete = "select vis_nom , vis_prenom "
                + "from Visiteur "
                + "where vis_matricule = ?";

        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement(requete);
            requetePreparee.setString(1, matricule);
            ResultSet resultat = requetePreparee.executeQuery();
            if (resultat.next()) {
                Visiteur visiteur = new Visiteur(matricule, resultat.getString("vis_nom"), resultat.getString("vis_prenom"));
                requetePreparee.close();
                return visiteur;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static ObservableList<Praticien> getPraticiensHesitants() throws ConnexionException {

        Connection connexion = ConnexionBD.getConnexion();

        String requete = "select Praticien.pra_num, pra_nom, pra_ville, pra_coefnotoriete, MAX(rap_date_visite), MIN(rap_coef_confiance) "
                + "from Praticien inner join RapportVisite on RapportVisite.pra_num = Praticien.pra_num inner join Visiteur on Visiteur.vis_matricule = RapportVisite.vis_matricule "
                + "where rap_coef_confiance < 5 group by Praticien.pra_num";

        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement(requete);
            ResultSet resultat = requetePreparee.executeQuery();
            if (resultat.next()) {
                ObservableList<Praticien> praticiens = FXCollections.observableArrayList();
                Praticien first = new Praticien(resultat.getString("Praticien.pra_num"), resultat.getString("pra_nom"), resultat.getString("pra_ville"), resultat.getDouble("pra_coefnotoriete"), resultat.getDate("MAX(rap_date_visite)") , resultat.getInt("MIN(rap_coef_confiance)"));
                praticiens.add(first);
                while(resultat.next()) {
                    String numero = resultat.getString(1);
                    String nom = resultat.getString(2);
                    String ville = resultat.getString(3);
                    Double coefNot = resultat.getDouble(4);
                    Date dateDerniereVisite = resultat.getDate(5);
                    int dernierCoefConfiance = resultat.getInt(6);
                    Praticien praticien = new Praticien(numero, nom, ville, coefNot, dateDerniereVisite , dernierCoefConfiance);
                    praticiens.add(praticien);
                }
                    requetePreparee.close();
                    return praticiens;
            }
            else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
