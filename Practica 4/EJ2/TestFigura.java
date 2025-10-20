package EJ2;

import java.util.Random;

public class TestFigura {
    public static void main(String[] args) {
        Random random = new Random();
        Figura[] figuras = new Figura[5];
        String[] colores = {"Rojo", "Rosa", "Verde", "Amarillo", "Naranja"};
        
        for (int i = 0; i < 5; i++) {
            int tipoFigura = random.nextInt(2) + 1;
            String color = colores[random.nextInt(colores.length)];
            
            if (tipoFigura == 1) {
                double lado = 1 + random.nextDouble() * 9;
                figuras[i] = new Cuadrado(color, lado);
            } else {
                double radio = 1 + random.nextDouble() * 9;
                figuras[i] = new Circulo(color, radio);
            }
        }
        
        for (int i = 0; i < figuras.length; i++) {
            System.out.println("\n=== Figura " + (i + 1) + " ===");
            System.out.println("Tipo: " + figuras[i].getClass().getSimpleName());
            System.out.println("Color: " + figuras[i].getColor());
            System.out.println("Área: " + String.format("%.2f", figuras[i].area()));
            System.out.println("Perímetro: " + String.format("%.2f", figuras[i].perimetro()));
            
            if (figuras[i] instanceof Coloreado) {
                Coloreado coloreado = (Coloreado) figuras[i];
                System.out.println("Método colorear: " + coloreado.comoColorear());
            } else {
                System.out.println("Esta figura no implementa la interfaz Coloreado");
            }
        }
    }
}