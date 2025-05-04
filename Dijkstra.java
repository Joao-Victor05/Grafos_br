import java.util.*;

class Dijkstra {
    private List<No> nos;
    private List<Aresta> arestas;
    private Map<No, Integer> distancias;
    private Map<No, No> predecessores;
    private Map<No, Aresta> arestasUsadas;

    public Dijkstra(List<No> nos, List<Aresta> arestas) {
        this.nos = nos;
        this.arestas = arestas;
    }

    public List<Aresta> dijkstra(No origem, No destino) {
        distancias = new HashMap<>();
        predecessores = new HashMap<>();
        arestasUsadas = new HashMap<>();
        PriorityQueue<No> fila = new PriorityQueue<>(Comparator.comparingInt(distancias::get));

        for (No no : nos) {
            distancias.put(no, Integer.MAX_VALUE);
        }
        distancias.put(origem, 0);
        fila.add(origem);

        while (!fila.isEmpty()) {
            No atual = fila.poll();
            for (Aresta aresta : arestas) {
                No vizinho = null;
                if (aresta.getNo1().equals(atual)) {
                    vizinho = aresta.getNo2();
                } else if (aresta.getNo2().equals(atual)) {
                    vizinho = aresta.getNo1();
                }
                if (vizinho != null) {
                    int novaDistancia = distancias.get(atual) + aresta.getPeso();
                    if (novaDistancia < distancias.get(vizinho)) {
                        distancias.put(vizinho, novaDistancia);
                        predecessores.put(vizinho, atual);
                        arestasUsadas.put(vizinho, aresta);
                        fila.remove(vizinho);
                        fila.add(vizinho);
                    }
                }
            }
        }

        List<Aresta> caminho = new ArrayList<>();
        No atual = destino;
        while (predecessores.containsKey(atual)) {
            caminho.add(arestasUsadas.get(atual));
            atual = predecessores.get(atual);
        }
        Collections.reverse(caminho);
        return distancias.get(destino) == Integer.MAX_VALUE ? new ArrayList<>() : caminho;
    }
}