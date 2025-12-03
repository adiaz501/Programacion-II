package P6;

public class Horario {
    private String dias;
    private String horaApertura;
    private String horaCierre;

    public Horario(String dias, String horaApertura, String horaCierre) {
        this.dias = dias;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
    }

    public String getDias() {
        return dias;
    }

    public String getHoraApertura() {
        return horaApertura;
    }

    public String getHoraCierre() {
        return horaCierre;
    }

    public void mostrarHorario() {
        System.out.println("Horario de Atención: " + dias + " de " + horaApertura + " a " + horaCierre);
    }
}