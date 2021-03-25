package ro.unibuc.cti;

public class Utilizator extends Cont {
    private String adresa;

    public Utilizator(String username, String password, String nume, String prenume, String nrTel, String adresa) {
        super(username, password, nume, prenume, nrTel);
        this.adresa = adresa;
    }

    public Utilizator(int id, String username, String password, String nume, String prenume, String nrTel, String adresa) {
        super(id, username, password, nume, prenume, nrTel);
        this.adresa = adresa;
    }
}
