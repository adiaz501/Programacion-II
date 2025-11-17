package segundoParcial;
import java.util.*;

class Persona {
    private String nombre;
    private int edad;
    private float pesoPersona;

    public Persona(String nombre, int edad, float peso) {
        this.nombre = nombre;
        this.edad = edad;
        this.pesoPersona = peso;
    }

    public int getEdad() { return edad; }
    public float getPeso() { return pesoPersona; }
}


class Cabina {
    private int nroCabina;
    private ArrayList<Persona> personasAbordo;

    public Cabina(int nroCabina) {
        this.nroCabina = nroCabina;
        this.personasAbordo = new ArrayList<>();
    }

    public boolean agregarPersona(Persona p) {
        if (personasAbordo.size() >= 10) return false;
        if (getPesoTotal() + p.getPeso() > 850) return false;

        personasAbordo.add(p);
        return true;
    }

    public float getPesoTotal() {
        float total = 0;
        for (Persona p : personasAbordo)
            total += p.getPeso();
        return total;
    }

    public int getCantidadPersonas() {
        return personasAbordo.size();
    }

    public ArrayList<Persona> getPersonas() {
        return personasAbordo;
    }

    public int getNroCabina() { return nroCabina; }
}


class Linea {
    private String color;
    private ArrayList<Persona> filaPersonas;
    private ArrayList<Cabina> cabinas;
    private int cantidadCabinas;

    public Linea(String color) {
        this.color = color;
        this.filaPersonas = new ArrayList<>();
        this.cabinas = new ArrayList<>();
    }

    public void agregarPersona(Persona p) {
        filaPersonas.add(p);
    }

    public void agregarCabina(int nroCab) {
        cabinas.add(new Cabina(nroCab));
        cantidadCabinas = cabinas.size();
    }

    public ArrayList<Persona> getFila() {
        return filaPersonas;
    }

    public ArrayList<Cabina> getCabinas() {
        return cabinas;
    }

    public String getColor() { return color; }
}

class MiTeleferico {
    private ArrayList<Linea> lineas;
    private float cantidadIngresos;

    public MiTeleferico() {
        this.lineas = new ArrayList<>();
        this.cantidadIngresos = 0;
    }

    public void agregarLinea(Linea linea) {
        lineas.add(linea);
    }

    // a
    
    public boolean agregarPrimeraPersonaFila(String lineaColor, int nroCabina) {
        for (Linea l : lineas) {
            if (l.getColor().equalsIgnoreCase(lineaColor)) {

                if (l.getFila().isEmpty()) return false;

                Persona p = l.getFila().get(0);

                for (Cabina c : l.getCabinas()) {
                    if (c.getNroCabina() == nroCabina) {

                        boolean agregado = c.agregarPersona(p);

                        if (agregado) {
                            l.getFila().remove(0);
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }

    // b
    public void verificarReglas() {
        for (Linea l : lineas) {
            for (Cabina c : l.getCabinas()) {

                boolean Personas = c.getCantidadPersonas() <= 10;
                boolean Peso = c.getPesoTotal() <= 850;

                System.out.println("Línea " + l.getColor() + 
                    " - Cabina " + c.getNroCabina() +
                    " Personas: " + c.getCantidadPersonas() +
                    " Peso total: " + c.getPesoTotal() +
                    " " + (Personas && Peso ? "CUMPLE" : "INCUMPLE"));
            }
        }
    }

    // c
    public float calcularIngresos() {
        cantidadIngresos = 0;

        for (Linea l : lineas) {
            for (Cabina c : l.getCabinas()) {

                for (Persona p : c.getPersonas()) {
                    int edad = p.getEdad();

                    if (edad < 25 || edad > 60)
                        cantidadIngresos += 1.5f;
                    else
                        cantidadIngresos += 3f;
                }
            }
        }
        return cantidadIngresos;
    }

    // d
    
    public String lineaMayorIngresoRegular() {

        float mayor = -1;
        String lineaMayor = "";

        for (Linea l : lineas) {

            float ingreso = 0;

            for (Cabina c : l.getCabinas()) {
                for (Persona p : c.getPersonas()) {

                    int edad = p.getEdad();

                    if (edad >= 25 && edad <= 60) {
                        ingreso += 3f;
                    }
                }
            }

            if (ingreso > mayor) {
                mayor = ingreso;
                lineaMayor = l.getColor();
            }
        }

        return lineaMayor;
    }
}
