import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UNO {
    private ArrayList<Mano> jugadores;
    private Mazo mazo;
    private ArrayList<Carta> cartasTablero;
    private Scanner scanner;

    //INTERFAZ
    private JPanel panelInicio;
    private JFrame frame;
    private JLabel labelCartaTablero;
    private int indexCartaGUI;
    private JButton btnIzquierda;
    private JButton btnDerecha;
    private JLabel labelCartaJugador;
    private int indexJugador = 0;

    public UNO(int numJugadores) {
        scanner = new Scanner(System.in);
        mazo = new Mazo();
        cartasTablero = new ArrayList<>();
        jugadores = new ArrayList<>();
        inicializarJugadores(numJugadores);
        indexJugador = 0;
        indexCartaGUI = 0;

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setTitle("UNO");

        panelInicio = new JPanel();
        panelInicio.setLayout(new BorderLayout());
        panelInicio.setBackground(Color.WHITE);
        frame.add(panelInicio, BorderLayout.CENTER);

        btnIzquierda = new JButton("Izquierda");
        btnDerecha = new JButton("Derecha");
        panelInicio.add(btnIzquierda, BorderLayout.WEST);
        panelInicio.add(btnDerecha, BorderLayout.EAST);

        btnIzquierda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indexCartaGUI > 0) {
                    indexCartaGUI--;
                    mostrarCartaActual();
                }
            }
        });

        btnDerecha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indexCartaGUI < jugadores.get(indexJugador).getMano().size() - 1) {
                    indexCartaGUI++;
                    mostrarCartaActual();
                }
            }
        });


        labelCartaTablero = new JLabel();
        labelCartaTablero.setHorizontalAlignment(SwingConstants.CENTER);
        labelCartaTablero.setVerticalAlignment(SwingConstants.CENTER);

        labelCartaJugador = new JLabel();
        labelCartaJugador.setHorizontalAlignment(SwingConstants.CENTER);
        labelCartaJugador.setVerticalAlignment(SwingConstants.CENTER);

        panelInicio.add(labelCartaTablero, BorderLayout.SOUTH);
        panelInicio.add(labelCartaJugador, BorderLayout.NORTH);
        frame.setVisible(true);

        primeraCartaEnJuego();
        mostrarCartaActual();



        do {
            if (!verificarSiHayCartaValida(indexJugador)) {
                System.out.println("El jugador no tiene cartas validas. Tomando cartas hasta que tenga una válida...");

                while (!verificarSiHayCartaValida(indexJugador)) {

                    anadirCartaDelMazo(indexJugador);
                    Carta cartaAñadida = jugadores.get(indexJugador).getMano().get(jugadores.get(indexJugador).getMano().size() - 1);
                    System.out.println("Se anadio la siguiente carta a la mano de " + jugadores.get(indexJugador).getNombre() + ": ");
                    cartaAñadida.imprimirCartaEnTerminal();

                }
                imprimirUltimaCarta();
                continue;
            }

            boolean come2boolean=false;
            boolean come4boolean=false;
            boolean cambiarColorCarta=false;
            int colorNuevo=-1;
            elegirCartaParaJugar(jugadores.get(indexJugador));
            if(decirSiEsEspecial(devolverUltimaCartaDelTablero())) {
                int valorEspecial = detectarCartaEspecial(devolverUltimaCartaDelTablero());

                if (valorEspecial == 10) {
                    System.out.println("Bloqueando turno jijjiji");
                    indexJugador = (indexJugador + 1) % numJugadores;
                }
                if(valorEspecial == 11) {
                System.out.println("Cambiando rumbo:");
                indexJugador = (indexJugador + 1) % numJugadores;
            }
                if(valorEspecial == 12) {
                    System.out.println("El siguiente jugador come 2 cartas jajaja");
                    come2boolean = true;
                }
                    if(valorEspecial == 13) {
                        System.out.println("ELIGE UN NUEVO COLOR");
                        System.out.println("Elige el color nuevo: 0Azul, 1Rojo, 2Verde, 3Amarillo");
                        colorNuevo = scanner.nextInt();
                        cambiarColorCarta = true;
                    }
                if(valorEspecial == 14) {
                    System.out.println("El siguiente jugador come 4 cartas jajaja");
                    System.out.println("Elige el color nuevo: 0Azul, 1Rojo, 2Verde, 3Amarillo");
                   colorNuevo = scanner.nextInt();
                    come4boolean = true;
                }
                }
            else{
                System.out.println("Carta especial no detectada");
            }





            if (verificarSiAlgunoNoTieneCartas()) {
                System.out.println(jugadores.get(indexJugador).getNombre() + " ha ganado!");
            } else {




                if (cambiarColorCarta || come4boolean){
                    cambiarColorEspecial(colorNuevo);
                }
                indexJugador = (indexJugador + 1) % numJugadores;

               if(come2boolean) {
                   comer2(jugadores.get(indexJugador));
               }
                if(come4boolean) {
                    comer4(jugadores.get(indexJugador));
                    cambiarColorEspecial(colorNuevo);
                }
                imprimirUltimaCarta();
            }

            /*if (mazo.getCartas().isEmpty()) {
                Mazo mazoNuevo = new Mazo();
                mazo = mazoNuevo;
            }*/
        } while (!verificarSiAlgunoNoTieneCartas());


        //  scanner.close();
    }
private void comer2(Mano manoJugador){

        for(int i = 1; i <= 2; i++) {
         manoJugador.robarCarta(mazo).imprimirCartaEnTerminal();

        }
        System.out.println("El jugador actual comio dos cartas");

}
    private void comer4(Mano manoJugador){

        for(int i = 1; i <= 4; i++) {
            manoJugador.robarCarta(mazo).imprimirCartaEnTerminal();

        }
        System.out.println("El jugador actual comio 4 cartas");

    }
    private void inicializarJugadores(int numJugadores) {
        for (int i = 0; i < numJugadores; i++) {
            System.out.print("Ingrese el nombre del jugador " + (i + 1) + ": ");
            String nombre = scanner.nextLine();

            Mano manoJugador = new Mano(mazo);
            manoJugador.setNombre(nombre);
            jugadores.add(manoJugador);

            // Imprimir la mano del jugador
            System.out.println("\nMano de " + manoJugador.getNombre() + ":");
            manoJugador.imprimirMano();
            System.out.println();
        }
    }

    private void primeraCartaEnJuego() {
        Carta primeraCarta;

        // Seguir sacando cartas hasta que el valor sea 10 o menor
        do {
            primeraCarta = mazo.getCartas().remove(0);
            mazo.getCartas().add(primeraCarta); // Devolver la carta al final si no es válida
        } while (primeraCarta.getValor() >= 10);

        cartasTablero.add(primeraCarta);
        System.out.println("Primera carta en juego: ");
        primeraCarta.imprimirCartaEnTerminal();
        mostrarCartaDelTablero(primeraCarta);
    }


    private void elegirCartaParaJugar(Mano manoJugador) {
        System.out.println("Turno de " + manoJugador.getNombre() + ":\n Su mano es:\n");
        manoJugador.imprimirMano();
        boolean cartaValida = false;

        while (!cartaValida) {
            System.out.println("que carta desea poner? Ingresa el indice de la carta)");

            int cartaElegidaIndex = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            if (cartaElegidaIndex < 0 || cartaElegidaIndex >= manoJugador.getMano().size()) {
                System.out.println("indice fuera de rango. Intenta de nuevo.");
                continue;
            }

            Carta ultimaCartaTablero = cartasTablero.get(cartasTablero.size() - 1);

            if (ultimaCartaTablero.compararValorCarta(manoJugador.getMano().get(cartaElegidaIndex)) ||
                    ultimaCartaTablero.compararColorCarta(manoJugador.getMano().get(cartaElegidaIndex))) {

                Carta cartaJugada = manoJugador.getMano().remove(cartaElegidaIndex);
                cartasTablero.add(cartaJugada);
                mostrarCartaDelTablero(cartaJugada);
                cartaValida = true;

            } else {
                System.out.println("La carta no coincide. Intenta de nuevo.\n");
            }
        }
    }

    private boolean jugadorSinCartas(Mano manoJugador) {
        return manoJugador.getMano().isEmpty();
    }

    private void imprimirUltimaCarta() {
        System.out.println("Última carta en el tablero:");
        cartasTablero.get(cartasTablero.size() - 1).imprimirCartaEnTerminal();
    }

    private boolean verificarSiAlgunoNoTieneCartas() {
        int numeroCartas;
        for (int i = 0; i < jugadores.size(); i++) {
            numeroCartas = jugadores.get(i).darNumeroDeCartas();
            if (numeroCartas == 0) {
                return true;
            }
        }
        return false;
    }

    private boolean verificarSiHayCartaValida(int index) {
        Carta ultimaCartaTablero = cartasTablero.get(cartasTablero.size() - 1);
        for (int i = 0; i < jugadores.get(index).darNumeroDeCartas(); i++) {
            Carta cartaJugador = jugadores.get(index).getMano().get(i);
            if (cartaJugador.getValor() >=13 && cartaJugador.getColorIndex() == 4) {
                return true;
            }
            if (cartaJugador.compararValorCarta(ultimaCartaTablero) ||
                    cartaJugador.compararColorCarta(ultimaCartaTablero)) {
                return true;
            }
        }
        return false;
    }

    private void anadirCartaDelMazo(int index) {
        Carta carta = mazo.getCartas().get(0);
        jugadores.get(index).getMano().add(carta);
        mazo.getCartas().remove(0);
    }


    private boolean decirSiEsEspecial(Carta carta){
        if (carta.getValor() >= 10){
            return true;
        }
        else
            return false;
    }
    private Carta devolverUltimaCartaDelTablero() {
        return cartasTablero.get(cartasTablero.size() - 1);
    }
    private int detectarCartaEspecial(Carta carta) {
        int valor = carta.getValor();

        switch (valor) {
            case 10:
                System.out.println("CARTA ESPECIAL BLOQUEAR TURNOS");
                return 10;

            case 11:
                System.out.println("CARTA ESPECIAL CAMBIAR RUMBO");
                return 11;

            case 12:
                System.out.println("CARTA ESPECIAL COME 2");

                return 12;

            case 13:
               System.out.println("CARTA ESPECIAL CAMBIAR COLOR");
                return 13;
            case 14:
                System.out.println("CARTA ESPECIAL COME4");
                return 14;
        }
            return 0;

    }
    private void utilizarCartaEspecial(int valor){
        switch (valor) {

            case 10:
                System.out.println("Accionando bloquear turno");
                break;
            case 11:
                System.out.println("Accionando cambiar rumbo");
                break;
            case 12:
                System.out.println("Accionando come 2");
                break;
            case 13:
                System.out.println("Accionando cambiar color");
                break;
            case 14:
                System.out.println("Accionando come 4");
                break;
        }
    }
    private void cambiarColorEspecial(int valor){
        devolverUltimaCartaDelTablero().setColor(valor);
    }
    private ImageIcon asignarImagenACarta(Carta carta){
        int valor = carta.getValor();
        int color = carta.getColorIndex();
        String ruta = String.format("cartasJPG/%d%d.png", valor, color);
        ImageIcon original = new ImageIcon(ruta);

        // Escalar imagen (ajusta tamaño aquí)
        Image imagenEscalada = original.getImage().getScaledInstance(240, 360, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }
    private void mostrarCartaDelTablero(Carta carta) {
        ImageIcon imagenCarta = asignarImagenACarta(carta);
            labelCartaTablero.setIcon(imagenCarta);
            labelCartaTablero.setText("");
    }
    private void mostrarCartaActual() {
        Carta carta = jugadores.get(indexJugador).getMano().get(indexCartaGUI);
        ImageIcon imagenCarta = asignarImagenACarta(carta);
        if (imagenCarta != null) {
            labelCartaJugador.setIcon(imagenCarta);
            labelCartaJugador.setText("");
        } else {
            labelCartaJugador.setIcon(null);
            labelCartaJugador.setText("Imagen no encontrada");
        }
    }

}





