package ARL.tesi.modelobject;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


//todo: si creano due report nel controller uno lo uso tutto dell altro prendo i totali
public class Reports {

    //da modelObject
    private List<Date> dates;
    private List<String> shifftsName;
    private List<LocalTime> shifftsStart;
    private List<LocalTime> shifftsEnd;
    private List<Integer> durata;
    private List<Integer> valore;
    private List<Integer> totaleMinuti;

    //derivati

    //indennita
    //speciali
    List<Double> cantone;
    List<Double> fuori;
    List<Double> colazione;
    List<Double> pranzi;
    List<Double> notturno;

    List<Double> festivo;
    List<Double> kmAuto;
    List<Double> cedole;

    //liberi
    List<Double> vacanze;
    List<Double> feriali;
    List<Double> sabati;
    List<Double> domenicheFestivi;
    List<Double> malattia;

    List<Double> infortunio;
    List<Double> pCivile;
    List<Double> militare;
    List<Double> congedo;
    List<Double> picchetto;


    public Reports() {
    }

    public Reports(List<Date> dates, List<String> shifftsName, List<LocalTime> shifftsStart, List<LocalTime> shifftsEnd, List<Integer> durata, List<Integer> valore, List<Integer> totaleMinuti) {
        this.dates = dates;
        this.shifftsName = shifftsName;
        this.shifftsStart = shifftsStart;
        this.shifftsEnd = shifftsEnd;
        this.durata = durata;
        this.valore = valore;
        this.totaleMinuti = totaleMinuti;

        this.vacanze = createVacanze();
        this.feriali = createFeriali();
        this.sabati = createSabati();
        this.domenicheFestivi = createDomeniche();
        this.malattia = createMalattia();


    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    public List<String> getShifftsName() {
        return shifftsName;
    }

    public void setShifftsName(List<String> shifftsName) {
        this.shifftsName = shifftsName;
    }

    public List<LocalTime> getShifftsStart() {
        return shifftsStart;
    }

    public void setShifftsStart(List<LocalTime> shifftsStart) {
        this.shifftsStart = shifftsStart;
    }

    public List<LocalTime> getShifftsEnd() {
        return shifftsEnd;
    }

    public void setShifftsEnd(List<LocalTime> shifftsEnd) {
        this.shifftsEnd = shifftsEnd;
    }

    public List<Integer> getDurata() {
        return durata;
    }

    public void setDurata(List<Integer> durata) {
        this.durata = durata;
    }

    public List<Integer> getValore() {
        return valore;
    }

    public void setValore(List<Integer> valore) {
        this.valore = valore;
    }

    public List<Integer> getTotaleMinuti() {
        return totaleMinuti;
    }

    public void setTotaleMinuti(List<Integer> totaleMinuti) {
        this.totaleMinuti = totaleMinuti;
    }

    public List<Double> getVacanze() {
        return vacanze;
    }

    public List<Double> getFeriali() {
        return feriali;
    }


    public List<Double> getSabati() {
        return sabati;
    }


    public List<Double> getDomenicheFestivi() {
        return domenicheFestivi;
    }

    public List<Double> getMalattia() {
        return malattia;
    }


    //todo: un solo loop e vari richiami a funzioni, es. check if V
    //presenza di V nel mese
    private List<Double> createVacanze(){
        List<Double> vacanze = new ArrayList<>();
        for (int i = 0; i < this.shifftsName.size(); i++){
            if (shifftsName.get(i).equals("V")){
                vacanze.add((double) 1);
            }else {
                vacanze.add((double) 0);
            }
        }
        return vacanze;
    }

    //presenza di R nei lu-ve
    private List<Double> createFeriali(){
        List<Double> feriali = new ArrayList<>();
        for (int i = 0; i < this.shifftsName.size(); i++){
            Calendar cal = Calendar.getInstance();
            cal.setTime(dates.get(i));
            if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && cal.get(Calendar.DAY_OF_WEEK)!= Calendar.SATURDAY && shifftsName.get(i).equals("R")){
                feriali.add((double) 1);
            }else {
                feriali.add((double) 0);
            }
        }
        return feriali;
    }

    //presenza di R nei sabati
    private List<Double> createSabati(){
        List<Double> sabati = new ArrayList<>();
        for (int i = 0; i < this.shifftsName.size(); i++){
            Calendar cal = Calendar.getInstance();
            cal.setTime(dates.get(i));
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && shifftsName.get(i).equals("R")){
                sabati.add((double) 1);
            }else {
                sabati.add((double) 0);
            }
        }
        return sabati;
    }

    //presenza di R nelle domeniche
    private List<Double> createDomeniche(){
        List<Double> domeniche = new ArrayList<>();
        for (int i = 0; i < this.shifftsName.size(); i++){
            Calendar cal = Calendar.getInstance();
            cal.setTime(dates.get(i));
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && shifftsName.get(i).equals("R")){
                domeniche.add((double) 1);
            }else {
                domeniche.add((double) 0);
            }
        }
        return domeniche;
    }


    //presenza di MAL nel mese
    private List<Double> createMalattia(){
        List<Double> malattia = new ArrayList<>();
        for (int i = 0; i < this.shifftsName.size(); i++){
            if (shifftsName.get(i).equals("MAL")){
                malattia.add((double) 1);
            }else {
                malattia.add((double) 0);
            }
        }
        return malattia;
    }


}
