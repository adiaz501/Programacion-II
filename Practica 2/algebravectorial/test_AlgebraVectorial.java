package algebravectorial;

public class test_AlgebraVectorial {

    public static void main(String[] args) {
        System.out.println("=== PRUEBAS ===");

        AlgebraVectorial v1 = new AlgebraVectorial(1, 0, 0);   // eje X
        AlgebraVectorial v2 = new AlgebraVectorial(0, 1, 0);   // eje Y
        AlgebraVectorial v3 = new AlgebraVectorial(2, 0, 0);   // paralelo a v1
        AlgebraVectorial v4 = new AlgebraVectorial(1, 1, 0);   // diagonal XY
        AlgebraVectorial v5 = new AlgebraVectorial(0, 0, 1);   // eje Z
        AlgebraVectorial v0 = new AlgebraVectorial(0, 0, 0);   // vector cero

        // ==== PERPENDICULARIDAD ====
        System.out.println(">> PERPENDICULARIDAD v1 y v2 (esperado: true)");
        System.out.println("Método 1 (|a+b|=|a-b|): " + v1.perpendicular(v2, 1));
        System.out.println("Método 2 (|a-b|=|b-a|): " + v1.perpendicular(v2, 2));
        System.out.println("Método 3 (a·b=0): " + v1.perpendicular(v2, 3));
        System.out.println("Método 4 (|a+b|²=|a|²+|b|²): " + v1.perpendicular(v2, 4));
        System.out.println("Método por defecto: " + v1.perpendicular(v2));

        System.out.println("\n>> PERPENDICULARIDAD v1 y v3 (esperado: false)");
        System.out.println("Método 1: " + v1.perpendicular(v3, 1));
        System.out.println("Método 2: " + v1.perpendicular(v3, 2));
        System.out.println("Método 3: " + v1.perpendicular(v3, 3));
        System.out.println("Método 4: " + v1.perpendicular(v3, 4));

        // ==== PARALELISMO ====
        System.out.println("\n>> PARALELISMO v1 y v3 (esperado: true)");
        System.out.println("Método 1 (a=rb): " + v1.paralela(v3));
        System.out.println("Método 2 (a×b=0): " + v1.paralela(v3, 2));

        System.out.println("\n>> PARALELISMO v1 y v2 (esperado: false)");
        System.out.println("Método 1: " + v1.paralela(v2));
        System.out.println("Método 2: " + v1.paralela(v2, 2));

        System.out.println("\n>> PARALELISMO v2 y v5 (esperado: false, son ortogonales)");
        System.out.println("Método 1: " + v2.paralela(v5));
        System.out.println("Método 2: " + v2.paralela(v5, 2));

        System.out.println("\n>> PARALELISMO CON VECTOR CERO");
        System.out.println("v1 y vector cero (esperado: false): " + v1.paralela(v0));
        System.out.println("Vector cero y v1 (esperado: false): " + v0.paralela(v1));
        System.out.println("Vector cero y vector cero (esperado: true): " + v0.paralela(v0));

        // ==== PROYECCIÓN Y COMPONENTE ====
        AlgebraVectorial a = new AlgebraVectorial(3, 4, 0);
        AlgebraVectorial b = new AlgebraVectorial(1, 0, 0);

        System.out.println("\n>> PROYECCIÓN Y COMPONENTE");
        System.out.println("a = " + a + ", b = " + b);
        System.out.println("Proyección de a sobre b = " + a.proyeccion(b));
        System.out.println("Componente de a en dirección de b = " + a.componente(b));

        // Proyección con vector cero
        System.out.println("\n>> PROYECCIÓN CON VECTOR CERO");
        System.out.println("Proyección de a sobre vector cero = " + a.proyeccion(v0));
        System.out.println("Componente de a en dirección de vector cero = " + a.componente(v0));
    }
}