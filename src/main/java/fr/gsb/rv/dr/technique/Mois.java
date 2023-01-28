package fr.gsb.rv.dr.technique;

public enum Mois {Janvier("01") , Février("02") , Mars("03") , Avril("04") , Maï("05") , Juin("06") , Juillet("07") , Août("08") , Septembre("09") , Octobre("10") , Novembre("11") , Décembre("12") ;

    private final String text;

    Mois(final String text){
        this.text = text;
    }

    public String toString(){
        return text;
    }

}
