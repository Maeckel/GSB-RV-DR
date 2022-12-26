package fr.gsb.rv.dr.technique;

import fr.gsb.rv.dr.entites.Visiteur;
import java.util.ArrayList;
import java.util.List;

public class Session {

    private static Session session = null;
    private Visiteur visiteur ;

    private Session(Visiteur visiteur){
        this.visiteur = visiteur;
    }

    public static void ouvrir( Visiteur visiteur){
       session = new Session(visiteur);
    }

    public static void fermer(){
        session = null;
    }

    public static Session getSession(){
        return session;
    }

    public Visiteur getLeVisteur(){
        return visiteur;
    }

    public static boolean estOuverte(){
        if(session != null){
            return true;
        }
        else{
            return false;
        }
    }
}
