package ro.unibuc.cti;

import javax.sound.midi.MidiChannel;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.util.*;

import static ro.unibuc.cti.ColorConstants.*;

public class Main {

    private static boolean running = true;

    private static FoodDelivery platform;
    private static Scanner sc;

    public static void main(String[] args) {
        platform = new FoodDelivery();

        sc = new Scanner(System.in);

        System.out.println(BOLD + ANSI_RED + "Bun venit la BobFoodDelivery!" + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_BLACK + "Va rugam sa va logati pentru a putea accesa functiile platformei. " +
                "Daca nu aveti deja un cont, va puteti crea unul scriind \"nou\" in locul numelui de utilizator.");

        System.out.print("Username: ");
        String username = sc.next();
        if(username.equals("nou")) {
            createNewUser();
        } else {
            System.out.print("Parola:  ");
            String password = sc.next();

            while (!platform.login(username, password)) {
                System.out.println(ANSI_RED + "Login nereusit. Username sau parola incorecte." + ANSI_RESET);
                System.out.println();
                System.out.println(ANSI_PURPLE + "Daca doriti sa creati un cont nou scrieti \"nou\" in locul numelui de utilizator." + ANSI_RESET);

                System.out.print(ANSI_BRIGHT_BLACK + "Username: " + ANSI_RESET);
                username = sc.next();

                if(username.equals("nou")) {
                    createNewUser();
                    break;
                }

                System.out.print(ANSI_BRIGHT_BLACK + "Parola:  " + ANSI_RESET);
                password = sc.next();
            }

            while (running) {
                System.out.println(ANSI_RESET + "Bine ati venit!");
                if (platform.getContLogat() instanceof Utilizator) {
                    System.out.println("1. Plaseaza o comanda");
                    System.out.println("2. Vezi status / istoric comenzi");
                    System.out.println("3. Logout");
                    System.out.print("Introdu numarul optiunii dorite: ");

                    int optiune = sc.nextInt();
                    switch (optiune) {
                        case 1: {
                            System.out.println(ANSI_YELLOW + "Alegeti un local de la care doriti sa comandati: " + ANSI_RESET);
                            System.out.println("0. Inapoi la meniul principal");
                            List<Local> localuri = platform.getListaLocaluri();
                            for (int i = 0; i < localuri.size(); i++) {
                                System.out.print(i + 1);
                                System.out.println(". " + localuri.get(i).getNume() + " " + localuri.get(i).getPrenume());
                            }
                            System.out.print("Introduceti numarul localului: ");
                            int indexLocal = sc.nextInt();
                            if(indexLocal == 0) {
                                continue;
                            }
                            System.out.println();
                            if(indexLocal > 0 && indexLocal <= localuri.size()) {
                                Local localSelectat = localuri.get(indexLocal - 1);
                                System.out.println(ANSI_GREEN + "Acesta este meniul localului " + ANSI_RED + localSelectat.getNume()
                                        + " " + localSelectat.getPrenume() + ANSI_GREEN + ":" + ANSI_RESET);

                                int i = 1;
                                System.out.println("------------------------------------------------");
                                System.out.println("|" + BOLD + ANSI_GREEN + "NR. CRT. " + ANSI_RESET + "|" + BOLD + ANSI_GREEN +
                                        "       DENUMIRE PRODUS       " + ANSI_RESET + "|" + BOLD + ANSI_GREEN + " PRET " + ANSI_RESET + "|");
                                System.out.println("------------------------------------------------");
                                for (String prod : localSelectat.getMeniu().keySet()) {
                                    String price = String.valueOf(localSelectat.getMeniu().get(prod));
                                    System.out.println("| " + padRight(String.valueOf(i++), 7) + " | " +
                                            padRight(padLeft(prod, 13 + prod.length() / 2), 27) + " | " +
                                            padRight(padLeft(price, 2 + price.length() / 2), 4) + " |");
                                }
                                System.out.println("------------------------------------------------");
                                System.out.println("Scrieti numarul produsului dorit sau \"0\" pentru a iesi:");

                                Map<String, Integer> cmd = new HashMap<>();

                                int indexulProdusului = sc.nextInt();

                                while (indexulProdusului < 0 || indexulProdusului > localSelectat.getMeniu().size()) {
                                    System.out.println("Index incorect! Introduceti \"0\" pentru a iesi.");
                                    indexulProdusului = sc.nextInt();
                                }

                                if (indexulProdusului != 0) {
                                    while(indexulProdusului != 0) {
                                        i = 1;
                                        String denumire = "";
                                        for (String prod : localSelectat.getMeniu().keySet()) { // cautarea denumirii produsului
                                            if(i == indexulProdusului) {
                                                denumire = prod;
                                                break;
                                            }
                                            i++;
                                        }
                                        System.out.print("Introduceti cantitatea dorita: ");
                                        int cant = sc.nextInt();
                                        if (indexulProdusului > localSelectat.getMeniu().size()){
                                            System.out.println("Index incorect!");
                                        } else {
                                            cmd.put(denumire, cant);
                                        }

                                        System.out.println("Scrieti numarul produsului dorit sau \"0\" pentru a iesi:");
                                        indexulProdusului = sc.nextInt();
                                    }

                                    platform.utilizatorPlaseazaComanda(cmd, localSelectat.getId());
                                }
                            }
                            break;
                        }
                        case 2: {
                            List<Comanda> comenzi = platform.utilizatorVeziComenzi();

                            System.out.println("----------------------------------------------------------------------------------------------------------------------------");
                            System.out.println("|" + BOLD + ANSI_GREEN + " NUMAR " + ANSI_RESET + "|" + BOLD + ANSI_GREEN +
                                    "       LOCAL       " + ANSI_RESET + "|" + BOLD + ANSI_GREEN + "        SOFER / NR_INMATRICULARE        " + ANSI_RESET + "|"
                                    + BOLD + ANSI_GREEN + "               PRODUSE                " + ANSI_RESET + "|"
                                    + BOLD + ANSI_GREEN + "    STATUS    " + ANSI_RESET + "|");
                            System.out.println("----------------------------------------------------------------------------------------------------------------------------");
                            for (Comanda c : comenzi) {
                                Local l = platform.cautaLocalDupaId(c.getIdLocal());
                                Sofer s = platform.cautaSoferDupaId(c.getIdSofer());

                                String idComanda = String.valueOf(c.getIdComanda());
                                String numeLocal = l.getNume() + " " + l.getPrenume();
                                String numeSofer = s.getNume() + " " + s.getPrenume() + " / " + s.getNrInmatriculare();
                                String statusComanda = c.getStatusComanda().toString();
                                String primulProdus = (String)c.getProduseComandate().keySet().toArray()[0];
                                primulProdus += " x" + c.getProduseComandate().get(primulProdus);


                                System.out.println("| " + padRight(idComanda, 5) + " | " +
                                        padRight(padLeft(numeLocal, 8 + numeLocal.length() / 2), 17) + " | " +
                                        padRight(padLeft(numeSofer, 19 + numeSofer.length() / 2), 38) + " | " +
                                        padRight(padLeft(primulProdus,
                                                18 + primulProdus.length() / 2), 36) + " | " +
                                        padRight(padLeft(statusComanda, 6 + statusComanda.length() / 2), 12) + " |");
                                boolean first = true;
                                for (String denProd : c.getProduseComandate().keySet()) {
                                    if (!first) {
                                        String prd = denProd + " x" + c.getProduseComandate().get(denProd);
                                        System.out.println(
                                                "|       |                   |                                        | " +
                                                        padRight(padLeft(prd, 18 + prd.length() / 2), 36)
                                                        + " |              |"
                                        );
                                    } else {
                                        first = false;
                                    }
                                }
                                System.out.println("----------------------------------------------------------------------------------------------------------------------------");
                            }

                            break;
                        }
                        case 3: {

                            break;
                        }
                        default: {
                            System.out.println("Optiune invalida");
                        }
                    }
                } else if (platform.getContLogat() instanceof Sofer) {

                } else if (platform.getContLogat() instanceof Local) {

                } else {
                    System.err.println("A aparut o eroare foarte grava si aplicatia trebuie sa se opreasca! O zi buna!");
                    System.exit(0);
                }
            }
        }

    }

    private static void createNewUser() {
        System.out.println("Introduceti numele de utilizator al noului cont: ");
        String username = sc.next();
        System.out.println("Introduceti parola noului cont: ");
        String pass1 = sc.next();
        System.out.println("Reitroduceti parola noului cont: ");
        String pass2 = sc.next();

        while (!pass1.equals(pass2)) {
            System.out.println("\nParolele trebuie sa fie identice!");
            System.out.println("Introduceti parola noului cont: ");
            pass1 = sc.next();
            System.out.println("Reitroduceti parola noului cont: ");
            pass2 = sc.next();
        }
        System.out.println();
        if (pass1.equals(pass2)) {
            System.out.println("Selectati tipul contului:");
            System.out.println("1. Utilizator");
            System.out.println("2. Sofer");
            System.out.println("3. Local");
            System.out.println("Introduceti numarul optiunii dorite: ");
            String tip = sc.next();
            switch (tip) {
                case "1": {  // Utilizator
                    System.out.println("Introduceti numele dvs:");
                    String nume = sc.next();
                    System.out.println("Introduceti prenumele dvs: ");
                    String prenume = sc.next();
                    System.out.println("Introduceti numarul dvs. de telefon: ");
                    String nrTel = sc.next();
                    System.out.println("Introduceti adresa dvs: ");
                    String adresa = sc.nextLine();

                    platform.inregistreazaUtilizatorNou(username, pass1, nume, prenume, nrTel, adresa);
                    break;
                }
                case "2": {  // Sofer
                    System.out.println("Introduceti numele dvs:");
                    String nume = sc.next();
                    System.out.println("Introduceti prenumele dvs: ");
                    String prenume = sc.next();
                    System.out.println("Introduceti numarul dvs. de telefon: ");
                    String nrTel = sc.next();
                    System.out.println("Introduceti orasul dvs: ");
                    String oras = sc.nextLine().toLowerCase();
                    oras = oras.substring(0,1).toUpperCase() + oras.substring(1).toLowerCase();
                    System.out.println("Introduceti numarul de inmatriculare al masinii dvs. (Ex: B12ASD): ");
                    String nrInmatriculare = sc.next();
                    System.out.println("Introduceti marca, numele si culoarea masinii dvs (Ex: Ford Focus negru");
                    String vehicul = sc.nextLine();

                    platform.inregistreazaSoferNou(username, pass1, nume, prenume, nrTel, oras, nrInmatriculare, vehicul);
                    break;
                }
                case "3": {  // Local
                    System.out.println("Introduceti numele dvs:");
                    String nume = sc.next();
                    System.out.println("Introduceti prenumele dvs: ");
                    String prenume = sc.next();
                    System.out.println("Introduceti numarul dvs. de telefon: ");
                    String nrTel = sc.next();
                    System.out.println("Introduceti adresa dvs: ");
                    String adresa = sc.nextLine();

                    platform.inregistreazaLocalNou(username, pass1, nume, prenume, nrTel, adresa);
                    break;
                }
                default: {
                    System.out.println("Optiune invalida");
                }
            }
        }

    }

    public static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    public static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }

}
