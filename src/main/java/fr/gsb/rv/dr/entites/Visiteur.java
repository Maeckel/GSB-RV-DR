package fr.gsb.rv.dr.entites;

import java.lang.reflect.Constructor;

public class Visiteur {

    private String matricule;
    private String nom;
    private String prenom;

    public Visiteur(){

    }
    public Visiteur( String matricule , String nom , String prenom){
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public String toString() {
        return "Visiteur{" + "matricule='" + matricule + '\'' + ", nom='" + nom + '\'' + ", prenom='" + prenom + '\'' + '}';
    }
}
