package ro.unibuc.cti;

public class Cont {
    private static int lastId = 0;

    private int id;
    private String username;
    private String password;
    private String nume;
    private String prenume;
    private String nrTel;

    public Cont(String username, String password, String nume, String prenume, String nrTel) {
        this.username = username;
        this.password = password;
        this.nume = nume;
        this.prenume = prenume;
        this.nrTel = nrTel;
    }
    public Cont(int id, String username, String password, String nume, String prenume, String nrTel) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nume = nume;
        this.prenume = prenume;
        this.nrTel = nrTel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getNrTel() {
        return nrTel;
    }

    public void setNrTel(String nrTel) {
        this.nrTel = nrTel;
    }
}
