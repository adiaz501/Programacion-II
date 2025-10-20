package EJ2;
public class Cuadrado extends Figura implements Coloreado {
    private double lado;
    public Cuadrado(String color, double lado) {
        super(color);
        this.lado = lado;
    }
    public double getLado() {
        return lado;
    }
    public void setLado(double lado) {
        this.lado = lado;
    }
    
    @Override
    public double area() {
        return lado * lado;
    }
    
    @Override
    public double perimetro() {
        return 4 * lado;
    }
    
    @Override
    public String comoColorear() {
        return "Colorear los cuatro lados";
    }
    
    @Override
    public String toString() {
        return "Cuadrado{color='" + color + "', lado=" + lado + "}";
    }
}