package P6;

import java.io.Serializable;

public class Horario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String dias;
    private String horaApertura;
    private String horaCierre;

    public Horario(String dias, String horaApertura, String horaCierre) {
        this.dias = dias;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
    }

    public void mostrarHorario() {
        System.out.println("Horario de Atención: " + dias + " de " + horaApertura + " a " + horaCierre);
    }
}