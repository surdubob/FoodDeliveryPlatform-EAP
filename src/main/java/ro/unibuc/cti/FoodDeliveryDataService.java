package ro.unibuc.cti;

import java.util.List;

public interface FoodDeliveryDataService {

    List<Cont> readConturi();
    List<Comanda> readComenzi();

    void writeConturi(List<Cont> listaConturi);
    void writeComenzi(List<Comanda> listaComenzi);

}
