import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Aresta {
    private No no1;
    private No no2;
    private int peso;

    public Aresta(No no1, No no2, int peso) {
        this.no1 = no1;
        this.no2 = no2;
        this.peso = peso;
    }

    public No getNo1() {
        return no1;
    }

    public No getNo2() {
        return no2;
    }

    public int getPeso() {
        return peso;
    }
}

class Dijkstra {
    private List<No> nos;
    private List<Aresta> arestas;

    public Dijkstra(List<No> nos, List<Aresta> arestas) {
        this.nos = nos;
        this.arestas = arestas;
    }

    public List<Aresta> dijkstra(No origem, No destino) {
        Map<No, Integer> distancias = new HashMap<>();
        Map<No, No> predecessores = new HashMap<>();
        List<No> naoVisitados = new ArrayList<>();

        // Inicialização
        for (No no : nos) {
            if (no.equals(origem)) {
                distancias.put(no, 0);
            } else {
                distancias.put(no, Integer.MAX_VALUE);
            }
            naoVisitados.add(no);
        }

        while (!naoVisitados.isEmpty()) {
            // Encontrar o nó com a menor distância
            No atual = null;
            int menorDistancia = Integer.MAX_VALUE;
            for (No no : naoVisitados) {
                int distancia = distancias.get(no);
                if (distancia < menorDistancia) {
                    menorDistancia = distancia;
                    atual = no;
                }
            }

            // Se não há nó acessível ou chegamos ao destino
            if (atual == null || atual.equals(destino)) {
                break;
            }

            // Remover o nó atual da lista de não visitados
            naoVisitados.remove(atual);

            // Atualizar as distâncias dos vizinhos
            for (Aresta aresta : arestas) {
                No vizinho = null;
                if (aresta.getNo1().equals(atual)) {
                    vizinho = aresta.getNo2();
                } else if (aresta.getNo2().equals(atual)) {
                    vizinho = aresta.getNo1();
                }

                if (vizinho != null && naoVisitados.contains(vizinho)) {
                    int novaDistancia = distancias.get(atual) + aresta.getPeso();
                    if (novaDistancia < distancias.get(vizinho)) {
                        distancias.put(vizinho, novaDistancia);
                        predecessores.put(vizinho, atual);
                    }
                }
            }
        }

        // Reconstruir o caminho
        List<Aresta> caminho = new ArrayList<>();
        No atual = destino;

        while (predecessores.containsKey(atual)) {
            No predecessor = predecessores.get(atual);
            Aresta arestaEntre = encontrarAresta(predecessor, atual);
            if (arestaEntre != null) {
                caminho.add(0, arestaEntre);
            }
            atual = predecessor;
        }

        return caminho;
    }

    private Aresta encontrarAresta(No no1, No no2) {
        for (Aresta aresta : arestas) {
            if ((aresta.getNo1().equals(no1) && aresta.getNo2().equals(no2)) ||
                (aresta.getNo1().equals(no2) && aresta.getNo2().equals(no1))) {
                return aresta;
            }
        }
        return null;
    }
}