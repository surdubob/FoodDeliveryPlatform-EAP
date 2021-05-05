package ro.unibuc.cti;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FoodDelivery {

    private static final String filenameLog = "src/main/csv/log.csv";
    BufferedWriter log = null;

    private boolean esteLogat = false;
    private Cont contLogat = null;

    private List<Cont> conturi;
    private List<Comanda> comenzi;
    private FoodDeliveryDataService dataService;


    public FoodDelivery() {
        dataService = CSVService.getInstance(); // Se va schimba in functie de provenienta datelor cu clase care implementeaza FoodDeliveryDataService
        conturi = dataService.readConturi();
        comenzi = dataService.readComenzi();

        try {
            FileWriter fwLog = new FileWriter(filenameLog);

            log = new BufferedWriter(fwLog);
        } catch (IOException e) {
            e.printStackTrace();
        }

        writeLog("FoodDelivery(): initializarea platformei s-a incheiat cu succes");
    }

    public boolean login(String userName, String parolaText) {
        for (Cont c : conturi) {
            if (c.getUsername().equals(userName) && c.getPassword().equals(parolaText)) {
                esteLogat = true;
                contLogat = c;
                writeLog("Login cu succes cu userul " + contLogat.getUsername());
                return true;
            }
        }
        writeLog("Login esuat cu user " + contLogat.getUsername());
        return false;
    }

    public boolean inregistreazaUtilizatorNou(String username, String password, String nume, String prenume, String nrTel, String adresa) {
        // TODO: de adaugat verificari pentru validitatea datelor
        conturi.add(new Utilizator(username, password, nume, prenume, nrTel, adresa));
        writeLog("inregistreazaUtilizatorNou");
        return true;
    }

    public boolean inregistreazaLocalNou(String username, String password, String nume, String prenume, String nrTel, String adresa) {
        // TODO: de adaugat verificari pentru validitatea datelor
        conturi.add(new Local(username, password, nume, prenume, nrTel, adresa));
        writeLog("inregistreazaLocalNou");
        return true;
    }

    public boolean inregistreazaSoferNou(String username, String password, String nume, String prenume, String nrTel, String oras, String nrInmatriculare, String vehicul) {
        // TODO: de adaugat verificari pentru validitatea datelor
        conturi.add(new Sofer(username, password, nume, prenume, nrTel, oras, nrInmatriculare, vehicul));
        writeLog("inregistreazaSoferNou");
        return true;
    }

    public void logout() {
        esteLogat = false;
        writeLog("Logout cu succes. User: " + contLogat.getUsername());
        contLogat = null;
    }

    public boolean esteLogat() {
        return esteLogat;
    }

    public boolean utilizatorPlaseazaComanda(Map<String, Integer> produse, int idLocal) {
        if (contLogat instanceof Utilizator) {
            // TODO: de adaugat validare date
            comenzi.add(new Comanda(contLogat.getId(), idLocal, produse));
            writeLog("Comanda plasata cu succes");
            return true;
        } else {
            System.err.println("Doar conturile de utilizator pot plasa comenzi, nu si celelalte tipuri de conturi!");
            writeLog("Comanda esuata");
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
            writeLog("Utilizatorul a vizionat istoricul comenzilor");
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
                    writeLog("Soferul a preluat o comanda cu succes");
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

    public void terminate() {
        dataService.writeComenzi(comenzi);
        dataService.writeConturi(conturi);
        writeLog("Aplicatia se inchide");
        System.exit(0);
    }

    public void writeLog(String content) {
        try {
            log.write(content + "," + System.currentTimeMillis() + "\n");
            log.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
