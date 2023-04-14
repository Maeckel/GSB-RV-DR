package fr.gsb.rv.dr.modeles;

import fr.gsb.rv.dr.entites.RapportVisite;
import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;

public class ModeleGsbRv {

    public static Visiteur seConnecter(String matricule, String mdp) throws ConnexionException {

        // Code de test à compléter

        Connection connexion = ConnexionBD.getConnexion();

        String requete = "select vis_nom , vis_prenom "
                + "from Visiteur "
                + "where vis_matricule = ? and vis_mdp = ?";

        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement(requete);
            requetePreparee.setString(1, matricule);
            requetePreparee.setString(2, mdp);
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

    public static List<Visiteur> getVisiteurs() throws ConnexionException {

        Connection connexion = ConnexionBD.getConnexion();

        String requete = "select vis_matricule, vis_nom , vis_prenom "
                + "from Visiteur;" ;

        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement(requete);
            ResultSet resultat = requetePreparee.executeQuery();
            if (resultat.next()) {
                List<Visiteur> visiteurs = new ArrayList<Visiteur>();
                Visiteur first = new Visiteur(resultat.getString("vis_matricule"), resultat.getString("vis_nom"), resultat.getString("vis_prenom"));
                visiteurs.add(first);
                while(resultat.next()) {
                    String matricule = resultat.getString(1);
                    String nom = resultat.getString(2);
                    String prenom = resultat.getString(3);
                    Visiteur visiteur = new Visiteur(matricule, nom, prenom);
                    visiteurs.add(visiteur);
                }
                requetePreparee.close();
                return visiteurs;
            }
            else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

    }

    public static ObservableList<RapportVisite> getRapportsVisites(String matricule, String mois , int annee) throws ConnexionException {

        Connection connexion = ConnexionBD.getConnexion();

        String requete = "select rap_num , rap_date_visite , rap_date_redaction , rap_bilan , mot_libelle , rap_coef_confiance , rap_lu , Visiteur.vis_matricule, vis_nom, vis_prenom, pra_nom , pra_ville, pra_adresse, pra_cp, pra_prenom "
                + "from Visiteur inner join RapportVisite on RapportVisite.vis_matricule = Visiteur.vis_matricule inner join Motif on Motif.mot_num = RapportVisite.mot_num inner join Praticien on Praticien.pra_num = RapportVisite.pra_num "
                + "where Visiteur.vis_matricule like '" + matricule + "' and rap_date_visite like '" + annee + "-" + mois + "-__' ;";
        //System.out.println(requete);

        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement(requete);
            requetePreparee.setString(1, matricule);
            requetePreparee.setInt(2, annee);
            requetePreparee.setString(3, mois);
            ResultSet resultat = requetePreparee.executeQuery();
            if (resultat.next()) {
                ObservableList<RapportVisite> rapportsVisites = FXCollections.observableArrayList();
                RapportVisite rapportVisite1 = new RapportVisite(resultat.getInt("rap_num"), resultat.getDate("rap_date_visite"), resultat.getDate("rap_date_redaction"), resultat.getString("rap_bilan"), resultat.getString("mot_libelle"), resultat.getInt("rap_coef_confiance"), resultat.getBoolean("rap_lu"), new Visiteur(resultat.getString("Visiteur.vis_matricule"), resultat.getString("vis_nom"), resultat.getString("vis_prenom") ), new Praticien(resultat.getString("pra_nom") , resultat.getString("pra_ville"), resultat.getString("pra_adresse"), resultat.getString("pra_cp"), resultat.getString("pra_prenom")));
                //System.out.println(rapportVisite1);
                rapportsVisites.add(rapportVisite1);
                    while (resultat.next()) {
                        Integer num = resultat.getInt(1);
                        Date dateVisite = resultat.getDate(2);
                        Date dateRedaction = resultat.getDate(3);
                        String bilan = resultat.getString(4);
                        String libelle = resultat.getString(5);
                        Integer coeffConfiance = resultat.getInt(6);
                        Boolean lu = resultat.getBoolean(7);
                        String mat = resultat.getString(8);
                        String vis_nom = resultat.getString(9);
                        String vis_pre = resultat.getString(10);
                        String pra_nom = resultat.getString(11);
                        String pra_ville = resultat.getString(12);
                        String pra_adresse = resultat.getString(13);
                        String pra_cp = resultat.getString(14);
                        String pra_prenom = resultat.getString(15);
                        RapportVisite rapportVisite = new RapportVisite(num, dateVisite, dateRedaction, bilan, libelle, coeffConfiance, lu, new Visiteur(mat, vis_nom , vis_pre), new Praticien(pra_nom, pra_ville, pra_adresse, pra_cp, pra_prenom));
                        rapportsVisites.add(rapportVisite);
                    }
                requetePreparee.close();
                return rapportsVisites;
            } else {
                System.out.println("Pas de Rapport trouver !");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Pas de Rapport trouver !");
            return null;
        }
    }

    public static boolean setRapportVisiteLu( int rapportVisite) throws ConnexionException {

        Connection connexion = ConnexionBD.getConnexion();

        String requete = "Update RapportVisite set rap_lu = true where rap_num = ? ;";

        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement(requete);
            requetePreparee.setInt(1, rapportVisite);
            ResultSet resultat = requetePreparee.executeQuery();
            requetePreparee.close();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    }
