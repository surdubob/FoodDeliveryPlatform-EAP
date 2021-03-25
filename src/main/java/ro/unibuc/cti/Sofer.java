package ro.unibuc.cti;

public class Sofer extends Cont {
    private String nrInmatriculare;
    private String vehicul;
    private String oras;

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

    public void setVehicul(String vehicul) {
        this.vehicul = vehicul;
    }
}
