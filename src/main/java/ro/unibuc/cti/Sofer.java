package ro.unibuc.cti;

import com.opencsv.bean.CsvBindByName;

public class Sofer extends Cont {

    @CsvBindByName(required = true)
    private String nrInmatriculare;

    @CsvBindByName(required = true)
    private String vehicul;

    @CsvBindByName(required = true)
    private String oras;

    public Sofer() {

    }

    public Sofer(String username, String password, String nume, String prenume, String nrTel, String oras, String nrInmatriculare, String vehicul) {
        super(username, password, nume, prenume, nrTel);
        this.nrInmatriculare = nrInmatriculare;
        this.vehicul = vehicul;
        this.oras = oras;
    }

    public Sofer(int id, String username, String password, String nume, String prenume, String nrTel, String nrInmatriculare, String vehicul, String oras) {
        super(id, username, password, nume, prenume, nrTel);
        this.nrInmatriculare = nrInmatriculare;
        this.vehicul = vehicul;
        this.oras = oras;
    }

    public String getNrInmatriculare() {
        return nrInmatriculare;
    }

    public void setNrInmatriculare(String nrInmatriculare) {
        this.nrInmatriculare = nrInmatriculare;
    }

    public String getVehicul() {
        return vehicul;
    }

    public String getOras() {
        return oras;
    }

    public void setVehicul(String vehicul) {
        this.vehicul = vehicul;
    }
}
