package ro.unibuc.cti;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FoodDelivery {

    private boolean esteLogat = false;
    private Cont contLogat = null;

    private List<Cont> conturi;
    private List<Comanda> comenzi;
    private FoodDeliveryDataService dataService;


    public FoodDelivery() {
        dataService = new TestDataService(); // Se va schimba in functie de provenienta datelor cu clase care implementeaza FoodDeliveryDataService
        conturi = dataService.readConturi();
        comenzi = dataService.readComenzi();
    }

    public boolean login(String userName, String parolaText) {
        // TODO: change test login

        if (userName.equals("bobica") && parolaText.equals("asdfghj")) {
            esteLogat = true;
            for (Cont c : conturi) {
                if (c.getUsername().equals(userName)) {
                    contLogat = c;
                    return true;
                }
            }
        }

        if (userName.equals("shoferubos") && parolaText.equals("shoferiepetichie")) {
            esteLogat = true;
            for (Cont c : conturi) {
                if (c.getUsername().equals(userName)) {
                    contLogat = c;
                    return true;
                }
            }
        }

        if (userName.equals("lamicutu") && parolaText.equals("shaormalamicutu123")) {
            esteLogat = true;
            for (Cont c : conturi) {
                if (c.getUsername().equals(userName)) {
                    contLogat = c;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean inregistreazaUtilizatorNou(String username, String password, String nume, String prenume, String nrTel, String adresa) {
        // TODO: de adaugat verificari pentru validitatea datelor
        conturi.add(new Utilizator(username, password, nume, prenume, nrTel, adresa));
        return true;
    }

    public boolean inregistreazaLocalNou(String username, String password, String nume, String prenume, String nrTel, String adresa) {
        // TODO: de adaugat verificari pentru validitatea datelor
        conturi.add(new Local(username, password, nume, prenume, nrTel, adresa));
        return true;
    }

    public boolean inregistreazaSoferNou(String username, String password, String nume, String prenume, String nrTel, String oras, String nrInmatriculare, String vehicul) {
        // TODO: de adaugat verificari pentru validitatea datelor
        conturi.add(new Sofer(username, password, nume, prenume, nrTel, oras, nrInmatriculare, vehicul));
        return true;
    }

    public void logout() {
        esteLogat = false;
        contLogat = null;
    }

    public boolean esteLogat() {
        return esteLogat;
    }

    public boolean utilizatorPlaseazaComanda(Map<String, Integer> produse, int idLocal) {
        if (contLogat instanceof Utilizator) {
            // TODO: de adaugat validare date
            comenzi.add(new Comanda(contLogat.getId(), idLocal, produse));
            return true;
        } else {
            System.err.println("Doar conturile de utilizator pot plasa comenzi, nu si celelalte tipuri de conturi!");
        }
        return false;
    }

    public List<Comanda> utilizatorVeziComenzi() {
        if (contLogat instanceof Utilizator) {
            List<Comanda> comenzileUtilizatorului = new ArrayList<>();
            for(Comanda c : comenzi) {
                if (c.getIdUser() == contLogat.getId()) {
                    comenzileUtilizatorului.add(c);
                }
            }
            return comenzileUtilizatorului;
        }
        return null;
    }

    public boolean soferPreiaComanda(int idComanda) {
        if (contLogat instanceof Sofer) {
            // TODO: de adaugat validare date
            for (Comanda c : comenzi) {
                if (c.getIdComanda() == idComanda && c.getIdSofer() == contLogat.getId() && c.getStatusComanda() == Comanda.StatusComanda.PREPARATA) {
                    c.soferPreiaComanda(contLogat.getId());
                    return true;
                } else if(c.getIdComanda() == idComanda) {
                    System.err.println("Comanda nu apartine acestui sofer!");
                    return false;
                } else if(c.getStatusComanda() != Comanda.StatusComanda.PREPARATA) {
                    System.err.println("Nu se pot prelua decat comenzi care au fost preparate deja.");
                }
            }
        } else {
            System.err.println("Doar conturile cu rol de sofer pot livra comenzi, nu si celelalte tipuri de conturi!");
        }
        return false;
    }

    public Cont getContLogat() {
        return contLogat;
    }

    public List<Local> getListaLocaluri() {
        List<Local> localuri = new ArrayList<>();
        for (Cont c : conturi) {
            if (c instanceof Local) {
                localuri.add((Local) c);
            }
        }
        return localuri;
    }

    public Local cautaLocalDupaId(int id) {
        for (Local l : getListaLocaluri()) {
            if(l.getId() == id) {
                return l;
            }
        }
        return null;
    }

    public Sofer cautaSoferDupaId(int id) {
        for (Sofer s : getListaSoferi()) {
            if(s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    private List<Sofer> getListaSoferi() {
        List<Sofer> soferi = new ArrayList<>();
        for (Cont c : conturi) {
            if (c instanceof Sofer) {
                soferi.add((Sofer) c);
            }
        }
        return soferi;
    }
}
