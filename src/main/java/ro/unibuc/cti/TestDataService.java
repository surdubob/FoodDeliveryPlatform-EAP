package ro.unibuc.cti;

import java.util.*;

public class TestDataService implements FoodDeliveryDataService {

    @Override
    public List<Cont> readConturi() {
        List<Cont> conturi = new ArrayList<>();
        conturi.add(new Utilizator(1, "bobica", "asdfghj", "Bobster", "Alecsila",
                "0758118787", "Str drumu ghindelor 144B"));

        conturi.add(new Sofer(2, "shoferubos", "shoferiepetichie", "Iosupila", "Fulgerica",
                "0758123464", "B39SEB", "Pejo 406 carbonizat", "Bucuresti"));

        SortedMap<String, Integer> meniuLaMicutu = new TreeMap<>();
        meniuLaMicutu.put("Shaorma cu de toate mica", 12);
        meniuLaMicutu.put("Shaorma cu de toate mare", 16);
        meniuLaMicutu.put("Pepsi", 4);
        meniuLaMicutu.put("Sprite", 4);
        meniuLaMicutu.put("Limonada", 7);
        meniuLaMicutu.put("Limonada cu menta", 8);

        conturi.add(new Local(3, "lamicutu", "shaormalamicutu123", "La", "Micutu",
                "0792746312", "Strada zambilelor 15, Bucuresti", meniuLaMicutu));

        return conturi;
    }

    @Override
    public List<Comanda> readComenzi() {
        List<Comanda> comenzi = new ArrayList<>();
        comenzi.add(new Comanda(1, 3, 2));
        comenzi.get(0).adaugaProdus("Shaorma cu de toate mica", 2);
        comenzi.get(0).adaugaProdus("Pepsi", 1);

        comenzi.add(new Comanda(1, 3, 2));
        comenzi.get(1).adaugaProdus("Limonada cu menta", 2);
        comenzi.get(1).adaugaProdus("Shaorma cu de toate mare", 1);
        comenzi.get(1).adaugaProdus("Shaorma cu de toate mica", 3);
        return comenzi;
    }
}
