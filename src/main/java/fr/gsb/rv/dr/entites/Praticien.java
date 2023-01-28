package fr.gsb.rv.dr.entites;

import java.time.LocalDate;
import java.util.Date;

public class Praticien {

    private String numero;
    private String nom;
    private String ville;
    private double coefNotoriete;
    private Date dateDerniereVisite;
    private int dernierCoefConfiance ;
    private String adresse;
    private String codePostal;
    private String prenom;

    public Praticien(String nom, String ville, String adresse, String codePostal, String prenom){
        this.nom = nom;
        this.ville = ville;
        this.adresse = adresse;
        this.codePostal  = codePostal;
        this.prenom = prenom;
    }

    public Praticien(String numero, String nom, String ville, double coefNotoriete, Date dateDerniereVisite, int dernierCoefConfiance){
        this.numero = numero;
        this.nom = nom;
        this.ville = ville;
        this.coefNotoriete = coefNotoriete;
        this.dateDerniereVisite = dateDerniereVisite;
        this.dernierCoefConfiance = dernierCoefConfiance;

    }

    public Praticien(String numero, String nom, String ville, double coefNotoriete, Date dateDerniereVisite, int dernierCoefConfiance, String adresse, String codePostal, String prenom) {
        this.numero = numero;
        this.nom = nom;
        this.ville = ville;
        this.coefNotoriete = coefNotoriete;
        this.dateDerniereVisite = dateDerniereVisite;
        this.dernierCoefConfiance = dernierCoefConfiance;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.prenom = prenom;
    }

    public Praticien(String numero, String nom, String ville){
        this.numero = numero;
        this.nom = nom;
        this.ville = ville;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Date getDateDerniereVisite() {
        return dateDerniereVisite;
    }

    public void setDateDerniereVisite(Date dateDerniereVisite) {
        this.dateDerniereVisite = dateDerniereVisite;
    }

    public double getCoefNotoriete() {
        return coefNotoriete;
    }

    public void setCoefNotoriete(double coefNotoriete) {
        this.coefNotoriete = coefNotoriete;
    }

    public int getDernierCoefConfiance() {
        return dernierCoefConfiance;
    }

    public void setDernierCoefConfiance(int dernierCoefConfiance) {
        this.dernierCoefConfiance = dernierCoefConfiance;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public String toString() {
        return "Praticien{" +
                "numero='" + numero + '\'' +
                ", nom='" + nom + '\'' +
                ", ville='" + ville + '\'' +
                ", coefNotoriete=" + coefNotoriete +
                ", dateDerniereVisite=" + dateDerniereVisite +
                ", dernierCoefConfiance=" + dernierCoefConfiance +
                ", adresse='" + adresse + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }
}
