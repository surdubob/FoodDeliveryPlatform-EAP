package ro.unibuc.cti;

import com.opencsv.bean.CsvBindByPosition;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Comanda {

    private static int lastId = 0;

    private final int idComanda;

    private int idUser;

    private int idLocal;

    private int idSofer;

    private Map<String, Integer> produseComandate;

    private LocalDate dataPlasarii;

    public enum StatusComanda {
        PLASATA, IN_PREPARARE, PREPARATA, IN_LIVRARE, FINALIZATA
    }

    private StatusComanda statusComanda = StatusComanda.PLASATA;

    public Comanda(int idUser, int idLocal, int idSofer, StatusComanda sc) {
        this.idUser = idUser;
        this.idLocal = idLocal;
        this.idSofer = idSofer;
        produseComandate = new HashMap<>();
        this.idComanda = lastId++;
        this.statusComanda = sc;
        this.dataPlasarii = LocalDate.now();
    }

    public Comanda(int idUser, int idLocal) {
        this.idUser = idUser;
        this.idLocal = idLocal;
        produseComandate = new HashMap<>();
        this.idComanda = lastId++;
        produseComandate = new HashMap<>();
        this.dataPlasarii = LocalDate.now();
    }


    public Comanda(int idUser, int idLocal, Map<String, Integer> produseComandate) {
        this.idUser = idUser;
        this.idLocal = idLocal;
        this.produseComandate = produseComandate;
        this.idComanda = lastId++;
        this.dataPlasarii = LocalDate.now();
    }

    public Comanda(int idComanda, int idUser, int idLocal, Map<String, Integer> produseComandate, Object giveNullHere) {
        this.idUser = idUser;
        this.idLocal = idLocal;
        this.produseComandate = produseComandate;
        this.idComanda = idComanda;
        this.dataPlasarii = LocalDate.now();
    }

    public void adaugaProdus(String produs, int cantitate) {
        produseComandate.put(produs, cantitate);
    }

    public void soferPreiaComanda(int idSofer) {
        statusComanda = StatusComanda.IN_LIVRARE;
        this.idSofer = idSofer;
    }

    public Map<String, Integer> getProduseComandate() {
        return produseComandate;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public int getIdSofer() {
        return idSofer;
    }

    public void setIdSofer(int idSofer) {
        this.idSofer = idSofer;
    }

    public int getIdComanda() {
        return idComanda;
    }

    public StatusComanda getStatusComanda() {
        return statusComanda;
    }

    public LocalDate getData() {
        return dataPlasarii;
    }

    public static void setLastId(int lastId) {
        Comanda.lastId = lastId;
    }

    public static int getLastId() {
        return lastId;
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "idComanda=" + idComanda +
                ", idUser=" + idUser +
                ", idLocal=" + idLocal +
                ", idSofer=" + idSofer +
                ", produseComandate=" + produseComandate +
                ", dataPlasarii=" + dataPlasarii +
                ", statusComanda=" + statusComanda +
                '}';
    }
}
