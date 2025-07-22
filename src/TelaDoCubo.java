import javax.swing.*;
import java.awt.*;

public class TelaDoCubo extends JPanel {

    // ➤ Variáveis principais:
    private double angulo = 0;         // ângulo de rotação do cubo
    private Ponto3D[] pontos;          // array com os 8 pontos 3D do cubo

    // ➤ Construtor da classe (executado quando criamos o painel)
    public TelaDoCubo() {
        // 1. Criar o array com 8 posições
        pontos = new Ponto3D[8];

        // 2. Preencher o array com os 8 vértices do cubo
        pontos[0] = new Ponto3D(-1, -1, -1);
        pontos[1] = new Ponto3D(1, -1, -1);
        pontos[2] = new Ponto3D(1, 1, -1);
        pontos[3] = new Ponto3D(-1, 1, -1);
        pontos[4] = new Ponto3D(-1, -1, 1);
        pontos[5] = new Ponto3D(1, -1, 1);
        pontos[6] = new Ponto3D(1, 1, 1);
        pontos[7] = new Ponto3D(-1, 1, 1);

        // 3. Criar um Timer que atualiza a tela a cada 16 milissegundos (~60 FPS)
        Timer timer = new Timer(16, e -> {
            angulo += 0.02;  // aumenta o ângulo de rotação
            repaint();       // redesenha o painel
        });
        timer.start(); // inicia a animação
    }

    // método que desenha o cubo na tela
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 4. Aplicar rotação 3D em todos os pontos
        Ponto3D[] rotacionados = new Ponto3D[8];
        for (int i = 0; i < pontos.length; i++) {
            Ponto3D p = pontos[i];

            // Rotação em Y
            double x = p.x * Math.cos(angulo) - p.z * Math.sin(angulo);
            double z = p.x * Math.sin(angulo) + p.z * Math.cos(angulo);

            // Rotação em X
            double y = p.y * Math.cos(angulo) - z * Math.sin(angulo);
            z = p.y * Math.sin(angulo) + z * Math.cos(angulo);

            // Cria um novo ponto rotacionado
            rotacionados[i] = new Ponto3D(x, y, z);
        }

        // 5. Projetar os pontos 3D para 2D (transformar para coordenadas da tela)
        Point[] pontos2D = new Point[8];
        for (int i = 0; i < 8; i++) {
            pontos2D[i] = projetar(rotacionados[i]);
        }

        // 6. Desenhar os pontos como bolinhas pretas
        g.setColor(Color.BLUE);
        for (Point p : pontos2D) {
            g.fillOval(p.x - 2, p.y - 2, 4, 4);
        }

        // 7. Desenhar as linhas (arestas) que ligam os pontos — 12 no total

        // Base do cubo
        g.drawLine(pontos2D[0].x, pontos2D[0].y, pontos2D[1].x, pontos2D[1].y);
        g.drawLine(pontos2D[1].x, pontos2D[1].y, pontos2D[2].x, pontos2D[2].y);
        g.drawLine(pontos2D[2].x, pontos2D[2].y, pontos2D[3].x, pontos2D[3].y);
        g.drawLine(pontos2D[3].x, pontos2D[3].y, pontos2D[0].x, pontos2D[0].y);

        // Topo do cubo
        g.drawLine(pontos2D[4].x, pontos2D[4].y, pontos2D[5].x, pontos2D[5].y);
        g.drawLine(pontos2D[5].x, pontos2D[5].y, pontos2D[6].x, pontos2D[6].y);
        g.drawLine(pontos2D[6].x, pontos2D[6].y, pontos2D[7].x, pontos2D[7].y);
        g.drawLine(pontos2D[7].x, pontos2D[7].y, pontos2D[4].x, pontos2D[4].y);

        // Ligações entre topo e base
        g.drawLine(pontos2D[0].x, pontos2D[0].y, pontos2D[4].x, pontos2D[4].y);
        g.drawLine(pontos2D[1].x, pontos2D[1].y, pontos2D[5].x, pontos2D[5].y);
        g.drawLine(pontos2D[2].x, pontos2D[2].y, pontos2D[6].x, pontos2D[6].y);
        g.drawLine(pontos2D[3].x, pontos2D[3].y, pontos2D[7].x, pontos2D[7].y);
    }

    // ➤ Método que projeta um ponto 3D para 2D (para desenhar na tela)
    public Point projetar(Ponto3D p) {
        double distancia = 3; // distância da "câmera"
        double escala = distancia / (distancia + p.z); // mais longe = menor escala

        int tamanho = 100; // tamanho do cubo na tela
        int centroX = getWidth() / 2; // centralizar na tela
        int centroY = getHeight() / 2;

        int x2d = (int) (p.x * escala * tamanho) + centroX;
        int y2d = (int) (p.y * escala * tamanho) + centroY;

        return new Point(x2d, y2d);
    }
}
