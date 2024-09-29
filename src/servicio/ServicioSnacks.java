package servicio;

import dominio.Snack;
import java.util.ArrayList;
import java.util.List;

public class ServicioSnacks implements IServicioSnacks{

    private static final List<Snack> listaSnacks;

    //Agregando algunos productos a la lista con un bloque estatico inicializador
    static {
        listaSnacks = new ArrayList<>();
        listaSnacks.add(new Snack("Agua",15));
        listaSnacks.add(new Snack("Refresco",30));
        listaSnacks.add(new Snack("Papas",18.50));
    }

    public void agregarSnack(Snack snack){
        listaSnacks.add(snack);
    }

    public void mostrarSnacks(){
        System.out.println("--servicio.Snacks disponibles--");
        for(Snack s: listaSnacks){
            System.out.println(s);
        }
    }

    public List<Snack> getSnacks(){
        return listaSnacks;
    }





}
