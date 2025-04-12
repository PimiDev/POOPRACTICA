import java.util.ArrayList;

public class Mano {
    private ArrayList<Carta> cartas;
    private String nombre;

    public Mano(Mazo mazo) {
        this.nombre = nombre;
        this.cartas = new ArrayList<>();
        // Robar cartas iniciales del mazo
        for (int i = 0; i < 7; i++) {
            this.cartas.add(mazo.robarCarta());
        }
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public ArrayList<Carta> getMano() {
        return cartas;
    }

    public void imprimirMano() {
        final int[] index = {0};
        cartas.forEach(carta -> {
            System.out.print(index[0] + ": ");
            carta.imprimirCartaEnTerminal();
            index[0]++;
        });
    }


    public int darNumeroDeCartas() {
        return cartas.size();
    }

    public void eliminarCarta(int index) {
        cartas.remove(index);
    }

    // Método para robar una carta del mazo
    public Carta robarCarta(Mazo mazo) {
        Carta cartaRobada = mazo.robarCarta();
        this.cartas.add(cartaRobada);
        return cartaRobada;
    }
}
