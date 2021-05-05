package ro.unibuc.cti;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

import java.io.*;
import java.util.*;

public class CSVService implements FoodDeliveryDataService {

    private static CSVService INSTANCE;

    private final static String filenameSoferi = "src/main/csv/soferi.csv";
    private final static String filenameUtilizatori = "src/main/csv/utilizatori.csv";
    private final static String filenameLocaluri = "src/main/csv/localuri.csv";
    private final static String filenameComenzi = "src/main/csv/comenzi.csv";

    private CSVService() {
    }

    public static CSVService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new CSVService();
        }
        return INSTANCE;
    }

    @Override
    public List<Cont> readConturi() {
        List<Cont> conturi = new ArrayList<>();
        conturi.addAll(read(Utilizator.class));
        conturi.addAll(read(Sofer.class));
        conturi.addAll(read(Local.class));
        return conturi;
    }

    private <T extends Cont> List<T> read(Class<T> clazz) {
        InputStream csvStream;
        List<T> list;
        if (clazz == Utilizator.class) {
            csvStream = getFileFromResourceAsStream(filenameUtilizatori);
            InputStreamReader isr = new InputStreamReader(csvStream);
            BufferedReader reader = new BufferedReader(isr);
            list = new ArrayList<>();
            try {
                while (reader.ready()) {
                    String line = reader.readLine();
                    String[] tokens = line.split(";");
                    Utilizator u = new Utilizator(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6]);
                    list.add((T) u);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (clazz == Sofer.class) {
            csvStream = getFileFromResourceAsStream(filenameSoferi);
            InputStreamReader isr = new InputStreamReader(csvStream);
            BufferedReader reader = new BufferedReader(isr);
            list = new ArrayList<>();
            try {
                while (reader.ready()) {
                    String line = reader.readLine();
                    String[] tokens = line.split(";");
                    Sofer s = new Sofer(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], tokens[7], tokens[7]);
                    list.add((T) s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {    // Local
            csvStream = getFileFromResourceAsStream(filenameLocaluri);
            InputStreamReader isr = new InputStreamReader(csvStream);
            BufferedReader reader = new BufferedReader(isr);

            list = new ArrayList<>();

            try {
                String line = reader.readLine();
                outerwhile: while(reader.ready()) {
                    String[] tokens = line.split(";");

                    Local l = new Local(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6]);

                    while (true){
                        line = reader.readLine();
                        if(line == null) {
                            list.add((T) l);
//                            System.out.println(l);
                            break;
                        }
                        tokens = line.split(";");
                        if (tokens.length == 2) {
                            l.adaugaProdusInMeniu(tokens[0], Integer.parseInt(tokens[1]));
                        } else {
                            list.add((T) l);
//                            System.out.println(l);
                            continue outerwhile;
                        }
                    }
                    line = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        return list;
    }


    @Override
    public List<Comanda> readComenzi() {
//        System.out.println("\n\n");

        InputStream csvStream = getFileFromResourceAsStream(filenameComenzi);
        InputStreamReader isr = new InputStreamReader(csvStream);
        BufferedReader reader = new BufferedReader(isr);

        List<Comanda> list = new ArrayList<>();

        try {
            String lastId = reader.readLine();
            Comanda.setLastId(Integer.parseInt(lastId));

            String line = reader.readLine();
            outerwhile: while(reader.ready()) {
                String[] tokens = line.split(";");
                Comanda c;
                if (tokens.length != 2) {
                    c = new Comanda(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Comanda.StatusComanda.valueOf(tokens[3]));
                } else {
                    c = new Comanda(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
                }
                while (true){
                    line = reader.readLine();
                    if(line == null) {
                        list.add(c);
                        break;
                    }
                    tokens = Arrays.stream(line.split(";")).map(String::strip).toArray(String[]::new);
                    if (tokens.length == 2) {
                        if (tokens[0].matches("[0-9]+") && tokens[1].matches("[0-9]+")) {
                            list.add(c);
                            c = new Comanda(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
                        } else {
                            c.adaugaProdus(tokens[0], Integer.parseInt(tokens[1]));
                        }
                    } else {
                        list.add(c);
                        continue outerwhile;
                    }
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

//        for (Comanda ob : list) {
//            System.out.println(ob.toString());
//        }

        return list;
    }

    @Override
    public void writeConturi(List<Cont> listaConturi) {
        try {
            FileWriter fwUtilizatori = new FileWriter(filenameUtilizatori);
            FileWriter fwSoferi = new FileWriter(filenameSoferi);
            FileWriter fwLocaluri = new FileWriter(filenameLocaluri);

            BufferedWriter csvUtilizatori = new BufferedWriter(fwUtilizatori);
            BufferedWriter csvSoferi = new BufferedWriter(fwSoferi);
            BufferedWriter csvLocaluri = new BufferedWriter(fwLocaluri);

            for (Cont c : listaConturi) {
                if (c instanceof Utilizator) {
//                    id,username,password,nume,prenume,nrTel,adresa
                    csvUtilizatori.write(c.getId() + ";" + c.getUsername() + ";" +
                            c.getPassword() + ";" + c.getNume() + ";" + c.getPrenume() + ";" + c.getNrTel() + ";" + ((Utilizator) c).getAdresa() + "\n");
                } else if (c instanceof Sofer) {
                    // id,username,password,nume,prenume,nrTel,nrInmatriculare,vehicul,oras
                    csvSoferi.write(c.getId() + ";" + c.getUsername() + ";" +
                            c.getPassword() + ";" + c.getNume() + ";" + c.getPrenume() + ";" 
                            + c.getNrTel() + ";" + ((Sofer) c).getNrInmatriculare() + ";" + ((Sofer) c).getVehicul() + ";" + ((Sofer) c).getOras() + "\n");
                } else if (c instanceof Local){ // local
                    // 3;lamicutu;shaormalamicutu123;La;Micutu;0792746312;Strada zambilelor 15, Bucuresti
                    csvLocaluri.write(c.getId() + ";" + c.getUsername() + ";" +
                            c.getPassword() + ";" + c.getNume() + ";" + c.getPrenume() + ";"
                            + c.getNrTel() + ";" + ((Local) c).getAdresa() + "\n");
                    for (String m : ((Local) c).getMeniu().keySet()) {
                        csvLocaluri.write(m + ";" + ((Local) c).getMeniu().get(m) + "\n");
                    }
                }
            }
            csvLocaluri.close();
            csvSoferi.close();
            csvUtilizatori.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeComenzi(List<Comanda> listaComenzi) {
        try {
            FileWriter fwComenzi = new FileWriter(filenameComenzi);
            BufferedWriter csvComenzi = new BufferedWriter(fwComenzi);
            
            csvComenzi.write(Comanda.getLastId() + "\n");
            for (Comanda comanda : listaComenzi) {
                csvComenzi.write(comanda.getIdUser() + ";" + comanda.getIdLocal() + ";" + comanda.getIdSofer() + ";" + comanda.getStatusComanda().toString() + "\n");
                for (String s : comanda.getProduseComandate().keySet()) {
                    csvComenzi.write(s + ";" + comanda.getProduseComandate().get(s) + "\n");
                }
            }
            csvComenzi.close();
            fwComenzi.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private InputStream getFileFromResourceAsStream(String fileName) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (inputStream == null) {
            throw new IllegalArgumentException("File not found! " + fileName);
        } else {
            return inputStream;
        }
    }

}