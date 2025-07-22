import javax.swing.*;

public class Cubo3DApp {
    public static void main(String[] args) {
        javax.swing.JFrame janela = new javax.swing.JFrame("Meu cube 3D");//Cria uma janela com título
        TelaDoCubo painel = new TelaDoCubo();
        janela.setSize(600,600); //Define o tamanho dela
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Diz o que acontece ao fechar
        janela.setVisible(true); //Mostra a janela na tela, sem isso não apareça
        janela.add(painel);
    }
}
