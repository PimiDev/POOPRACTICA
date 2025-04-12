import java.util.ArrayList;
import java.util.Collections;

public class Mazo {
    private ArrayList<Carta> mazo;

    public Mazo() {
        mazo = new ArrayList<>();
        generarMazo();
        barajarMazo();
    }

    // Generar el mazo de cartas
    public void generarMazo() {
        for (int colorIndex = 0; colorIndex < 4; colorIndex++) {
            for (int valor = 0; valor <= 14; valor++) {
                if (valor >= 13) {
                    mazo.add(new Carta(valor, 4)); // Cartas especiales (Cambio de color, +4)
                } else {
                    mazo.add(new Carta(valor, colorIndex)); // Cartas numéricas
                    if (valor != 0) {
                        mazo.add(new Carta(valor, colorIndex)); // Repetir cartas numéricas
                    }
                }
            }
        }
    }

    // Barajar el mazo
    public void barajarMazo() {
        Collections.shuffle(mazo);
    }

    // Método para robar una carta del mazo
    public Carta robarCarta() {
        return mazo.isEmpty() ? null : mazo.remove(0); // Roba la primera carta y la elimina
    }

    // Imprimir todas las cartas del mazo (para depuración)
    public void imprimirMazo() {
        for (int i = 0; i < mazo.size(); i++) {
            mazo.get(i).imprimirCartaEnTerminal();
        }
    }

    // Getter para obtener el mazo completo
    public ArrayList<Carta> getCartas() {
        return mazo;
    }
}
