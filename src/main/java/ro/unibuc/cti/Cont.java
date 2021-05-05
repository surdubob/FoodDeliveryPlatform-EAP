package ro.unibuc.cti;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class Cont {
    private static int lastId = 0;

    @CsvBindByName(required = true)
    private int id;

    @CsvBindByName(required = true)
    private String username;

    @CsvBindByName(required = true)
    private String password;

    @CsvBindByName(required = true)
    private String nume;

    @CsvBindByName(required = true)
    private String prenume;

    @CsvBindByName(required = true)
    private String nrTel;

    public Cont(){

    }

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

    @Override
    public String toString() {
        return "Cont{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", nrTel='" + nrTel + '\'' +
                '}';
    }
}
