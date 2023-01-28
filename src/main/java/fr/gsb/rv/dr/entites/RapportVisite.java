package fr.gsb.rv.dr.entites;

import javax.management.loading.PrivateClassLoader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RapportVisite {

    private int numero;
    private Date dateVisite;
    private Date dateRedaction;
    private String bilan;
    private String Motif;
    private int coefConfiance;
    private boolean lu;

    private Visiteur levisiteur ;
    private Praticien lepraticien ;

    public RapportVisite(int numero, Date dateVisite, Date dateRedaction, String bilan, String motif, int coefConfiance, boolean lu, Visiteur levisiteur, Praticien lepraticien) {
        this.numero = numero;
        this.dateVisite = dateVisite;
        this.dateRedaction = dateRedaction;
        this.bilan = bilan;
        this.Motif = motif;
        this.coefConfiance = coefConfiance;
        this.lu = lu;
        this.levisiteur = levisiteur ;
        this.lepraticien = lepraticien ;
    }


    public Visiteur getLevisiteur() {
        return levisiteur;
    }

    public void setLevisiteur(Visiteur levisiteur) {
        this.levisiteur = levisiteur;
    }

    public Praticien getLepraticien() {
        return lepraticien;
    }

    public void setLepraticien(Praticien lepraticien) {
        this.lepraticien = lepraticien;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getDateVisite() {
        return dateVisite;
    }

    public void setDateVisite(Date dateVisite) {
        this.dateVisite = dateVisite;
    }

    public Date getDateRedaction() {
        return dateRedaction;
    }

    public void setDateRedaction(Date dateRedaction) {
        this.dateRedaction = dateRedaction;
    }

    public String getBilan() {
        return bilan;
    }

    public void setBilan(String bilan) {
        this.bilan = bilan;
    }

    public String getMotif() {
        return Motif;
    }

    public void setMotif(String motif) {
        Motif = motif;
    }

    public int getCoefConfiance() {
        return coefConfiance;
    }

    public void setCoefConfiance(int coefConfiance) {
        this.coefConfiance = coefConfiance;
    }

    public boolean isLu() {
        return lu;
    }

    public void setLu(boolean lu) {
        this.lu = lu;
    }



    @Override
    public String toString() {
        return "RapportVisite{" +
                "numero=" + numero +
                ", dateVisite=" + dateVisite +
                ", dateRedaction=" + dateRedaction +
                ", bilan='" + bilan + '\'' +
                ", Motif='" + Motif + '\'' +
                ", coefConfiance=" + coefConfiance +
                ", lu=" + lu +
                ", levisiteur=" + levisiteur +
                ", lepraticien=" + lepraticien +
                '}';
    }
}
