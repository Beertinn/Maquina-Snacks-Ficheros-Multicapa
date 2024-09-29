package presentacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import dominio.Snack;
import servicio.IServicioSnacks;
import servicio.ServicioSnacksFile;

public class MaquinaSnacks {

    public static void main(String[] args) {
        maquinaSnacks();

    }

    public static void maquinaSnacks(){
        System.out.println("*** Maquina de Snacks ***");
        List<Snack> snacks = new ArrayList<>();
        var salir = false;
        var consola = new Scanner(System.in);
        IServicioSnacks servicioSnacks = new ServicioSnacksFile();
        servicioSnacks.mostrarSnacks();
        while(!salir){
            try{
                var opcion = mostrarMenu(consola);
                salir = ejecutarOpciones(opcion, consola, snacks, servicioSnacks);
            }catch (Exception e){
                System.out.println("Ocurrio un error: "+e.getMessage());
            }finally {
                System.out.println();
            }
        }

    }

    public static int mostrarMenu(Scanner consola){
        System.out.print("""
                Menu:
                1) Mostrar el inventario de snacks
                2) Comprar Snack
                3) Mostrar ticket
                4) Agregar nuevo Snack
                5) Salir
                
                Elige una opcion: \s""");
        return Integer.parseInt(consola.nextLine());
    }

    public static boolean ejecutarOpciones(int opcion, Scanner consola, List<Snack> snacks, IServicioSnacks servicioSnacks){
        var salida = false;
        switch(opcion){
            case 1 -> servicioSnacks.mostrarSnacks();
            case 2 ->comprarSnack(consola,snacks, servicioSnacks);
            case 3 ->mostrarTicket(snacks);
            case 4 ->agregarSnack(consola, servicioSnacks);
            case 5 ->{
                System.out.println("*Saliendo . . . *");
                salida = true;
            }
            default -> System.out.println("Opcion no reconocida");
        }
        return salida;
    }

    public static void comprarSnack(Scanner consola, List<Snack> snacks, IServicioSnacks servicioSnacks){
        System.out.print("Ingresa el snack que deseas comprar (id): ");
        var idSnack = Integer.parseInt(consola.nextLine());
        boolean snackEncontrado = false;
        for(Snack snack : servicioSnacks.getSnacks()){
            if(idSnack == snack.getIdSnack()) {
                //Se agrega el snack a la lista de compra
                snacks.add(snack);
                System.out.println("Snack agregado al Ticket de compra: " + snack);
                snackEncontrado = true;
                break; //Rompemos el ciclo ya que ya se encontró el id.
            }else{
                snackEncontrado = false;
            }
        }
        if(!snackEncontrado){
            System.out.println("Snack no encontrado (id "+idSnack+")");
        }
    }

    public static void mostrarTicket(List<Snack> snacks){
        double totalCompra = 0;
        System.out.println("-----Ticket de compra-----");
        for(Snack s : snacks){
            totalCompra += s.getPrecio();
            System.out.println(s.getNombre()+"\t$"+s.getPrecio());
        }
        System.out.println("\nTotal de compra: "+totalCompra);
        System.out.println("--------------------------");
    }

    public static void agregarSnack(Scanner consola, IServicioSnacks servicioSnacks){
        String nombreNuevo;
        double precioNuevo;
        System.out.print("Ingresa el nombre del producto a agregar: ");
        nombreNuevo = consola.nextLine();
        System.out.print("Ingresa su precio: ");
        precioNuevo = Double.parseDouble(consola.nextLine());
        Snack nuevoSnack = new Snack(nombreNuevo,precioNuevo);
        servicioSnacks.agregarSnack(nuevoSnack);
        System.out.println("*Snack nuevo agregado*");
        servicioSnacks.mostrarSnacks();
    }

}
