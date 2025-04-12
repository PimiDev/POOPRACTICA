public class Carta {
    private int Valor;
    private String Color;
    private int colorIndex;

    String[] Colores = {"💙","🔴","💚","😊","black"};

    public Carta(int valor, int colorIndex){
        Valor = valor;
        this.colorIndex = colorIndex;
       Color = Colores[colorIndex];

    }
    public boolean compararValorCarta(Carta carta){
        if(carta.getValor() == Valor){
            return true;
        }
        if (carta.getValor() >=13){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean compararColorCarta(Carta carta){
        if(carta.getColor() == Color){
            return true;
        }
        else{
            return false;
        }
    }
    void imprimirCartaEnTerminal() {
        if (Valor == 10) {
            System.out.println(Valor + Color +" Saltar turno");
        } else if (Valor == 11) {
            System.out.println(Valor + Color + " Cambiar rumbo");
        } else if (Valor == 12) {
            System.out.println(Valor + Color + " +2");
        } else if (Valor == 13) {
            System.out.println(Valor + Color +" Cambiar Color");
        } else if (Valor == 14) {
            System.out.println(Valor + Color + " +4 y cambiar color");
        } else {
            System.out.println(Valor + Color);
        }
    }

    int getValor(){

        return Valor;
    }
    String getColor(){

        return Color;
    }
    public void setColor(int nuevoColor) {
        switch (nuevoColor) {
            case 0:
                this.Color = "💙";
                break;
            case 1:
                this.Color = "🔴";
                break;
            case 2:
                this.Color = "💚";
                break;
            case 3:
                this.Color = "😊";
                break;
            default:
                System.out.println("Opción inválida. No se cambió el color.");
        }
    }
    public int getColorIndex(){
        return colorIndex;
    }

}