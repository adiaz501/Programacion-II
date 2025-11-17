package segundoParcial;

public class Prueba {
    public static void main(String[] args) {

        MiTeleferico mt = new MiTeleferico();

        Linea rojo = new Linea("Rojo");
        Linea verde = new Linea("Verde");
        Linea amarillo = new Linea("Amarillo");

        mt.agregarLinea(rojo);
        mt.agregarLinea(verde);
        mt.agregarLinea(amarillo);

        
        rojo.agregarCabina(1);
        rojo.agregarCabina(2);
        
        verde.agregarCabina(1);
        amarillo.agregarCabina(1);

        
        rojo.agregarPersona(new Persona("Alison", 20, 60));    
        rojo.agregarPersona(new Persona("Luis", 30, 80)); 

        verde.agregarPersona(new Persona("Jose", 40, 90));  
        verde.agregarPersona(new Persona("Beto", 15, 55)); 

        amarillo.agregarPersona(new Persona("Carla", 28, 65));

        // a
        mt.agregarPrimeraPersonaFila("Rojo", 1);
        mt.agregarPrimeraPersonaFila("Rojo", 1);
        mt.agregarPrimeraPersonaFila("Verde", 1);
        mt.agregarPrimeraPersonaFila("Amarillo", 1);

        // b
        mt.verificarReglas();

        // c
        float ingresos = mt.calcularIngresos();
        System.out.println("Ingreso total: " + ingresos + " Bs");

        //d
        String mayor = mt.lineaMayorIngresoRegular();
        System.out.println("La línea con mayor ingreso regular es: " + mayor);
    }
}


