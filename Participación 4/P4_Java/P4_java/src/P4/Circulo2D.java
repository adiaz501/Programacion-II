package P4;

public class Circulo2D {
    private double x;
    private double y;
    private double radio;

    public Circulo2D() {
        this.x=0;
        this.y=0;
        this.radio=1;
    }

    public Circulo2D(double x, double y, double radio) {
        this.x = x;
        this.y = y;
        this.radio = radio;
    }

    public double getX() { 
    	return x; 
    }
    public double getY() { 
    	return y; 
    }
    public double getRadio() { 
    	return radio; 
    }

    
    public double getArea() {
        return Math.PI * radio * radio;
    }

    public double getPerimetro() {
        return 2 * Math.PI * radio;
    }

    //contiene para un punto
    public boolean contiene(double x, double y) {
        double distancia = Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
        return distancia <= radio;
    }

    //contiene para otro círculo
    public boolean contiene(Circulo2D c) {
        double distancia = Math.sqrt(Math.pow(this.x - c.x, 2) + Math.pow(this.y - c.y, 2));
        return distancia + c.radio <= this.radio;
    }

    //sobrepone
    public boolean sobrepone(Circulo2D c) {
        double distancia = Math.sqrt(Math.pow(this.x - c.x, 2) + Math.pow(this.y - c.y, 2));
        return distancia <= (this.radio + c.radio) &&
               distancia >= Math.abs(this.radio - c.radio);
    }
}

