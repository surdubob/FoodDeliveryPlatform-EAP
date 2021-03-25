package ro.unibuc.cti;

import java.util.List;

public interface FoodDeliveryDataService {

    List<Cont> readConturi();
    List<Comanda> readComenzi();

}
