import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Grafo extends JPanel {

    private List<No> nos;
    private List<Aresta> arestas;
    private List<Aresta> caminhoMinimo;
    private Image imagemFundo;
    private JComboBox<No> comboBoxOrigem;
    private JComboBox<No> comboBoxDestino;

    public Grafo() {
        nos = new ArrayList<>();
        arestas = new ArrayList<>();
        caminhoMinimo = new ArrayList<>();
        imagemFundo = new ImageIcon("mapa-do-brasil.jpg").getImage();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                String nome = JOptionPane.showInputDialog("Digite o nome do ponto:");
                if (nome != null && !nome.trim().isEmpty()) {
                    No novoPonto = new No(nome, x, y);
                    nos.add(novoPonto);

                    for (No pontoExistente : nos) {
                        if (pontoExistente != novoPonto) {
                            int peso = calcularDistancia(novoPonto, pontoExistente);
                            Aresta novaAresta = new Aresta(novoPonto, pontoExistente, peso);
                            arestas.add(novaAresta);
                        }
                    }

                    atualizarComboBoxes();
                    repaint();
                }
            }
        });

        setLayout(new BorderLayout());

        comboBoxOrigem = new JComboBox<>();
        comboBoxDestino = new JComboBox<>();

        atualizarComboBoxes();

        JButton button = new JButton("Buscar Menor Peso");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                No origem = (No) comboBoxOrigem.getSelectedItem();
                No destino = (No) comboBoxDestino.getSelectedItem();
                encontrarMenorPeso(origem, destino);
                mostrarResultado();
                repaint();
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Origem:"));
        panel.add(comboBoxOrigem);
        panel.add(new JLabel("Destino:"));
        panel.add(comboBoxDestino);
        panel.add(button);

        add(panel, BorderLayout.NORTH);
    }

    private int calcularDistancia(No no1, No no2) {
        int dx = no2.getX() - no1.getX();
        int dy = no2.getY() - no1.getY();
        return (int) Math.round(Math.sqrt(dx * dx + dy * dy));
    }

    private void encontrarMenorPeso(No origem, No destino) {
        caminhoMinimo.clear();

        Dijkstra dijkstra = new Dijkstra(nos, arestas);
        caminhoMinimo = dijkstra.dijkstra(origem, destino);
    }

    private void atualizarComboBoxes() {
        comboBoxOrigem.removeAllItems();
        comboBoxDestino.removeAllItems();

        for (No no : nos) {
            comboBoxOrigem.addItem(no);
            comboBoxDestino.addItem(no);
        }
    }

    private void mostrarResultado() {
        if (caminhoMinimo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum caminho encontrado.", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder resultado = new StringBuilder("Caminho Mínimo:\n");
        int totalPeso = 0;
        No ultimoNo = null;

        for (Aresta aresta : caminhoMinimo) {
            if (ultimoNo == null || aresta.getNo1().equals(ultimoNo)) {
                resultado.append(aresta.getNo1().getNome())
                        .append(" -> ")
                        .append(aresta.getPeso())
                        .append(" -> ");
                totalPeso += aresta.getPeso();
                ultimoNo = aresta.getNo2();
            } else {
                resultado.append(aresta.getNo2().getNome())
                        .append(" -> ")
                        .append(aresta.getPeso())
                        .append(" -> ");
                totalPeso += aresta.getPeso();
                ultimoNo = aresta.getNo1();
            }
        }

        if (ultimoNo != null) {
            resultado.append(ultimoNo.getNome()).append("\n");
        }

        resultado.append("Peso Total: ").append(totalPeso).append(" km");

        JOptionPane.showMessageDialog(this, resultado.toString(), "Resultado do Caminho Mínimo", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), this);

        for (Aresta aresta : arestas) {
            No no1 = aresta.getNo1();
            No no2 = aresta.getNo2();
            if (caminhoMinimo.contains(aresta)) {
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(3));
            } else {
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(1));
            }
            g2d.drawLine(no1.getX(), no1.getY(), no2.getX(), no2.getY());
            float posX = (no2.getX() + no1.getX()) / 2;
            float posY = (no2.getY() + no1.getY()) / 2;
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.drawString(String.format("%d km", aresta.getPeso()), posX + 10, posY - 10);
        }

        for (No no : nos) {
            g2d.setColor(Color.WHITE);
            g2d.fillOval(no.getX() - 8, no.getY() - 8, 16, 16);
            g2d.setColor(Color.RED);
            g2d.fillOval(no.getX() - 6, no.getY() - 6, 12, 12);
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.drawString(no.getNome(), no.getX() + 15, no.getY() - 5);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Grafo grafo = new Grafo();

        // Removido a chamada para interligarTodasCapitais
        // Agora o mapa inicia vazio e só adiciona pontos com cliques

        frame.add(grafo);
        frame.setSize(800, 600);
        frame.setVisible(true);
        grafo.repaint();
    }

    public void addVertice(String nome, int x, int y) {
        No novoPonto = new No(nome, x, y);
        nos.add(novoPonto);
        atualizarComboBoxes();
        repaint();
    }

    public void addAresta(String nomeNo1, String nomeNo2, int peso) {
        No no1 = encontrarNoPorNome(nomeNo1);
        No no2 = encontrarNoPorNome(nomeNo2);

        if (no1 != null && no2 != null) {
            Aresta novaAresta = new Aresta(no1, no2, peso);
            arestas.add(novaAresta);
            repaint();
        }
    }

    private No encontrarNoPorNome(String nome) {
        for (No no : nos) {
            if (no.getNome().equals(nome)) {
                return no;
            }
        }
        return null;
    }
}