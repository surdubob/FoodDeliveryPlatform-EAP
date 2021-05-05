package ro.unibuc.cti;

import com.opencsv.bean.CsvBindByName;

public class Utilizator extends Cont {

    @CsvBindByName(required = true)
    private String adresa;

    public Utilizator() {

    }

    public Utilizator(String username, String password, String nume, String prenume, String nrTel, String adresa) {
        super(username, password, nume, prenume, nrTel);
        this.adresa = adresa;
    }

    public Utilizator(int id, String username, String password, String nume, String prenume, String nrTel, String adresa) {
        super(id, username, password, nume, prenume, nrTel);
        this.adresa = adresa;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
}
