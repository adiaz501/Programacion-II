package algebravectorial;

public class AlgebraVectorial {
    
    private double x, y, z;

    public AlgebraVectorial() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public AlgebraVectorial(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double productoPunto(AlgebraVectorial v) {
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }

    public double magnitud() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public AlgebraVectorial productoCruz(AlgebraVectorial v) {
        double cx = this.y * v.z - this.z * v.y;
        double cy = this.z * v.x - this.x * v.z;
        double cz = this.x * v.y - this.y * v.x;
        return new AlgebraVectorial(cx, cy, cz);
    }

    public AlgebraVectorial sumar(AlgebraVectorial v) {
        return new AlgebraVectorial(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    public AlgebraVectorial restar(AlgebraVectorial v) {
        return new AlgebraVectorial(this.x - v.x, this.y - v.y, this.z - v.z);
    }

    private boolean esCero(double valor) {
        return Math.abs(valor) < 1e-9; // tolerancia
    }

    // ==== PERPENDICULARIDAD ====
    public boolean perpendicular(AlgebraVectorial v) {
        return perpendicular(v, 3);
    }

    public boolean perpendicular(AlgebraVectorial v, int metodo) {
        switch (metodo) {
            case 1: // a) |a+b| = |a-b|
                double sumaMagnitud = this.sumar(v).magnitud();
                double restaMagnitud = this.restar(v).magnitud();
                return esCero(sumaMagnitud - restaMagnitud);
                
            case 2: // b) |a-b| = |b-a|
                double abMagnitud = this.restar(v).magnitud();
                double baMagnitud = v.restar(this).magnitud();
                return esCero(abMagnitud - baMagnitud);
                
            case 3: // c) a · b = 0
                return esCero(this.productoPunto(v));
                
            case 4: // d) |a+b|² = |a|² + |b|²
                double lhs = Math.pow(this.sumar(v).magnitud(), 2);
                double rhs = Math.pow(this.magnitud(), 2) + Math.pow(v.magnitud(), 2);
                return esCero(lhs - rhs);
                
            default:
                return false;
        }
    }

    // ==== PARALELISMO ====
    public boolean paralela(AlgebraVectorial v) {
        // e) a = r·b
        if (esCero(this.magnitud()) && esCero(v.magnitud())) {
            return true;
        }
        if (esCero(this.magnitud()) || esCero(v.magnitud())) {
            return false;
        }
        
        Double r = null;
        
        if (!esCero(v.x)) {
            r = this.x / v.x;
        } else if (!esCero(v.y)) {
            r = this.y / v.y;
        } else if (!esCero(v.z)) {
            r = this.z / v.z;
        }
        
        if (r != null) {
            boolean cond1 = esCero(this.x - r * v.x);
            boolean cond2 = esCero(this.y - r * v.y);
            boolean cond3 = esCero(this.z - r * v.z);
            return cond1 && cond2 && cond3;
        }
        
        return false;
    }

    public boolean paralela(AlgebraVectorial v, int metodo) {
        if (metodo == 2) {
            // f) a × b = 0
            AlgebraVectorial cruz = this.productoCruz(v);
            return esCero(cruz.x) && esCero(cruz.y) && esCero(cruz.z);
        }
        return paralela(v);
    }

    // ==== PROYECCIÓN ====
    public AlgebraVectorial proyeccion(AlgebraVectorial v) {
        // g) Proyección de a sobre b: (a·b/|b|²) * b
        if (esCero(v.magnitud())) {
            return new AlgebraVectorial(); // Vector cero si b es cero
        }
        double factor = this.productoPunto(v) / Math.pow(v.magnitud(), 2);
        return new AlgebraVectorial(factor * v.x, factor * v.y, factor * v.z);
    }

    // ==== COMPONENTE ====
    public double componente(AlgebraVectorial v) {
        // h) Componente de a en la dirección de b: (a·b)/|b|
        if (esCero(v.magnitud())) {
        }
        return this.productoPunto(v) / v.magnitud();
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}