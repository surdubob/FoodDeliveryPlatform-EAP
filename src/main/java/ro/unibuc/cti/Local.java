package ro.unibuc.cti;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Local extends Cont {
    private String adresa;

    private SortedMap<String, Integer> meniu;

    public Local(String username, String password, String nume, String prenume, String nrTel, String adresa) { // fara id
        super(username, password, nume, prenume, nrTel);
        this.adresa = adresa;
        this.meniu = new TreeMap<>();
    }

    public Local(int id, String username, String password, String nume, String prenume, String nrTel, String adresa) { // cu id
        super(id, username, password, nume, prenume, nrTel);
        this.adresa = adresa;
        this.meniu = new TreeMap<>();
    }

    public Local(String username, String password, String nume, String prenume, String nrTel, String adresa, SortedMap<String, Integer> meniu) {
        super(username, password, nume, prenume, nrTel);
        this.meniu = meniu;
    }

    public Local(int id, String username, String password, String nume, String prenume, String nrTel, String adresa, SortedMap<String, Integer> meniu) {
        super(id, username, password, nume, prenume, nrTel);
        this.meniu = meniu;
    }

    public void adaugaProdusInMeniu(String produs, int pret) {
        meniu.put(produs, pret);
    }

    public void stergeProdusDinMeniu(String produs) {
        meniu.remove(produs);
    }

    public SortedMap<String, Integer> getMeniu() {
        return meniu;
    }
}
