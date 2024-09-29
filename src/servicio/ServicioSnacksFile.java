package servicio;

import dominio.Snack;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ServicioSnacksFile implements IServicioSnacks{
    private final String NOMBRE_ARCHIVO = "snacks.txt";
    //Crea la lista de snacks
    private List<Snack> listaSnacks = new ArrayList<>();

    //Constructor de la clase.
    public ServicioSnacksFile(){
        //Creamos el archivo si no existe
        var archivo = new File(NOMBRE_ARCHIVO);
        var existe = false;
        try{
            existe = archivo.exists();
            if(existe){
                this.listaSnacks = obtenerSnacksFile();
            }else {
                var salida = new PrintWriter(new FileWriter(archivo));
                salida.close();
                System.out.println("Se ha creado el archivo");
            }
        }catch (Exception e){
            System.out.println("Error al crear el archivo: "+e.getMessage());
            e.printStackTrace();
        }
        //Si el archivo no existia, se cargan algunos productos iniciales
        if(!existe) {
            cargarSnacksDefault();

        }
    }

    private void cargarSnacksDefault(){
        this.agregarSnack(new Snack("Agua",15));
        this.agregarSnack(new Snack("Refresco",25));
        this.agregarSnack(new Snack("Papas",27));

    }

    private List<Snack> obtenerSnacksFile(){
        List<Snack> snacks = new ArrayList<>();
        try{

            List<String> lineas = Files.readAllLines(Paths.get(NOMBRE_ARCHIVO));
            for(String linea : lineas){
                //Separa individualmente los datos separados por comas.
                String[] lineaSnack = linea.split(",");
                var idSnack = lineaSnack[0];
                var nombre = lineaSnack[1];
                var precio = Double.parseDouble(lineaSnack[2]);

                var snack = new Snack(nombre,precio);
                snacks.add(snack);
            }

        }catch (Exception e){
            System.out.println("Error al leer el arcivo de Snacks: "+e.getMessage());
            e.printStackTrace();
        }
        return snacks;

    }

    @Override
    public void agregarSnack(Snack snack) {
        this.listaSnacks.add(snack);
        //Guardamos el snack en el archivo
        this.agregarSnackFile(snack);
    }

    public void agregarSnackFile(Snack snack){
        boolean anexar = false;
        var archivo = new File(NOMBRE_ARCHIVO);
        try{
            anexar = archivo.exists();
            var salida = new PrintWriter(new FileWriter(archivo,anexar));
            salida.println(snack.escribirSnack());
            salida.close();
        } catch (Exception e) {
            System.out.println("Error al agregar el snack: "+e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void mostrarSnacks() {
        System.out.println("*** LISTA DE PRODUCTOS ***");
        String inventarioSnacks = "";
        for(Snack snack : this.listaSnacks){
            inventarioSnacks += snack.toString()+"\n";
        }
        System.out.println(inventarioSnacks);

    }

    @Override
    public List<Snack> getSnacks() {
        return this.listaSnacks;
    }
}
