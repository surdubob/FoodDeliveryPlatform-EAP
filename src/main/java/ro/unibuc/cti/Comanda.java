package ro.unibuc.cti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Comanda {

    private static int lastId = 0;

    private int idComanda;
    private int idUser;
    private int idLocal;
    private int idSofer;
    private Map<String, Integer> produseComandate;

    public enum StatusComanda {
        PLASATA, IN_PREPARARE, PREPARATA, IN_LIVRARE, FINALIZATA
    }

    private StatusComanda statusComanda = StatusComanda.PLASATA;

    public Comanda(int idUser, int idLocal, int idSofer) {
        this.idUser = idUser;
        this.idLocal = idLocal;
        this.idSofer = idSofer;
        produseComandate = new HashMap<>();
        this.idComanda = lastId++;
    }
    public Comanda(int idComanda, int idUser, int idLocal, int idSofer) {
        this.idUser = idUser;
        this.idLocal = idLocal;
        this.idSofer = idSofer;
        produseComandate = new HashMap<>();
        this.idComanda = idComanda;
    }

    public Comanda(int idUser, int idLocal, int idSofer, Map<String, Integer> produseComandate) {
        this.idUser = idUser;
        this.idLocal = idLocal;
        this.idSofer = idSofer;
        this.produseComandate = produseComandate;
        this.idComanda = lastId++;
    }

    public Comanda(int idComanda, int idUser, int idLocal, int idSofer, Map<String, Integer> produseComandate) {
        this.idUser = idUser;
        this.idLocal = idLocal;
        this.idSofer = idSofer;
        this.produseComandate = produseComandate;
        this.idComanda = idComanda;
    }

    public Comanda(int idUser, int idLocal, Map<String, Integer> produseComandate) {
        this.idUser = idUser;
        this.idLocal = idLocal;
        this.produseComandate = produseComandate;
        this.idComanda = lastId++;
    }

    public Comanda(int idComanda, int idUser, int idLocal, Map<String, Integer> produseComandate, Object giveNullHere) {
        this.idUser = idUser;
        this.idLocal = idLocal;
        this.produseComandate = produseComandate;
        this.idComanda = idComanda;
    }

    public void adaugaProdus(String produs, int cantitate) {
        produseComandate.put(produs, cantitate);
    }

    public void soferPreiaComanda() {
        statusComanda = StatusComanda.IN_LIVRARE;
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
}
