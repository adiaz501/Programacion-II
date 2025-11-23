package P6;

import java.io.Serializable;

public class Pagina implements Serializable {
    private static final long serialVersionUID = 1L;
    private int numero;
    private String contenido;

    public Pagina(int numero, String contenido) {
        this.numero = numero;
        this.contenido = contenido;
    }

    public void mostrarPagina() {
        System.out.println("Página " + numero + ": " + contenido);
    }
}