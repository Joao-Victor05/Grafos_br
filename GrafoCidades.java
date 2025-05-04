import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.Normalizer;

public class GrafoCidades {

    private List<No> nos;
    private List<Aresta> arestas;
    private List<Aresta> caminhoMinimo;

    public GrafoCidades() {
        nos = new ArrayList<>();
        arestas = new ArrayList<>();
        caminhoMinimo = new ArrayList<>();
        preCarregarCidades();
    }
    
    private void preCarregarCidades() {
        // Adiciona as 27 capitais brasileiras
        addVertice("Aracaju", true);
        addVertice("Belém", true);
        addVertice("Belo Horizonte", true);
        addVertice("Boa Vista", true);
        addVertice("Brasília", true);
        addVertice("Campo Grande", true);
        addVertice("Cuiabá", true);
        addVertice("Curitiba", true);
        addVertice("Florianópolis", true);
        addVertice("Fortaleza", true);
        addVertice("Goiânia", true);
        addVertice("João Pessoa", true);
        addVertice("Macapá", true);
        addVertice("Maceió", true);
        addVertice("Manaus", true);
        addVertice("Natal", true);
        addVertice("Palmas", true);
        addVertice("Porto Alegre", true);
        addVertice("Porto Velho", true);
        addVertice("Recife", true);
        addVertice("Rio Branco", true);
        addVertice("Rio de Janeiro", true);
        addVertice("Salvador", true);
        addVertice("São Luís", true);
        addVertice("São Paulo", true);
        addVertice("Teresina", true);
        addVertice("Vitória", true);

        // Adiciona cidades vizinhas (não-capitais)
        addVertice("Campinas", false);
        addVertice("Santos", false);
        addVertice("Juiz de Fora", false);
        addVertice("Joinville", false);
        addVertice("Caxias do Sul", false);
        addVertice("Feira de Santana", false);
        addVertice("Caruaru", false);
        addVertice("Dourados", false);
        addVertice("Santarém", false);

        // Conexões entre capitais (mantidas do original)
        addAresta("São Paulo", "Rio de Janeiro", 430);
        addAresta("São Paulo", "Curitiba", 410);
        addAresta("São Paulo", "Belo Horizonte", 590);
        addAresta("São Paulo", "Brasília", 870);
        addAresta("São Paulo", "Campo Grande", 990);
        addAresta("Rio de Janeiro", "Belo Horizonte", 440);
        addAresta("Rio de Janeiro", "Vitória", 520);
        addAresta("Belo Horizonte", "Brasília", 710);
        addAresta("Belo Horizonte", "Vitória", 510);
        addAresta("Brasília", "Goiânia", 210);
        addAresta("Brasília", "Cuiabá", 1130);
        addAresta("Brasília", "Palmas", 620);
        addAresta("Goiânia", "Cuiabá", 930);
        addAresta("Goiânia", "Campo Grande", 840);
        addAresta("Cuiabá", "Campo Grande", 690);
        addAresta("Cuiabá", "Porto Velho", 1450);
        addAresta("Porto Velho", "Rio Branco", 510);
        addAresta("Porto Velho", "Manaus", 760);
        addAresta("Manaus", "Boa Vista", 660);
        addAresta("Manaus", "Belém", 1300);
        addAresta("Belém", "Macapá", 330);
        addAresta("Belém", "São Luís", 480);
        addAresta("São Luís", "Teresina", 330);
        addAresta("Teresina", "Fortaleza", 500);
        addAresta("Fortaleza", "Natal", 430);
        addAresta("Natal", "João Pessoa", 180);
        addAresta("João Pessoa", "Recife", 120);
        addAresta("Recife", "Maceió", 200);
        addAresta("Maceió", "Aracaju", 280);
        addAresta("Aracaju", "Salvador", 310);
        addAresta("Salvador", "Vitória", 840);
        addAresta("Curitiba", "Florianópolis", 300);
        addAresta("Florianópolis", "Porto Alegre", 460);

        // Conexões envolvendo cidades vizinhas (distancias aproximadas)
        addAresta("São Paulo", "Campinas", 100);
        addAresta("São Paulo", "Santos", 70);
        addAresta("Campinas", "Santos", 150);
        addAresta("Belo Horizonte", "Juiz de Fora", 260);
        addAresta("Juiz de Fora", "Rio de Janeiro", 180);
        addAresta("Curitiba", "Joinville", 130);
        addAresta("Joinville", "Florianópolis", 170);
        addAresta("Porto Alegre", "Caxias do Sul", 130);
        addAresta("Caxias do Sul", "Florianópolis", 450);
        addAresta("Salvador", "Feira de Santana", 110);
        addAresta("Feira de Santana", "Aracaju", 350);
        addAresta("Recife", "Caruaru", 130);
        addAresta("Caruaru", "Maceió", 200);
        addAresta("Campo Grande", "Dourados", 230);
        addAresta("Dourados", "Cuiabá", 650);
        addAresta("Belém", "Santarém", 710);
        addAresta("Santarém", "Manaus", 590);
    }

    private void encontrarMenorRota(No origem, No destino) {
        caminhoMinimo.clear();
        Dijkstra dijkstra = new Dijkstra(nos, arestas);
        caminhoMinimo = dijkstra.dijkstra(origem, destino);
    }

    private void mostrarResultado(No origem, No destino) {
        if (caminhoMinimo.isEmpty()) {
            System.out.println("Não foi possível encontrar um caminho entre " + origem.getNome() + " e " + destino.getNome() + ".");
            return;
        }

        StringBuilder resultado = new StringBuilder();
        StringBuilder resultadoDetalhado = new StringBuilder("Rota detalhada:\n");
        int totalDistancia = 0;
        No pontoAtual = origem;
        
        resultado.append("Rota: ").append(pontoAtual.getNome());
        
        for (Aresta aresta : caminhoMinimo) {
            No proximo;
            if (aresta.getNo1().equals(pontoAtual)) {
                proximo = aresta.getNo2();
            } else {
                proximo = aresta.getNo1();
            }
            
            resultado.append(" -> ").append(proximo.getNome());
            resultadoDetalhado.append(pontoAtual.getNome())
                    .append(" -> ").append(proximo.getNome())
                    .append(" (").append(aresta.getPeso()).append(" km)\n");
            
            totalDistancia += aresta.getPeso();
            pontoAtual = proximo;
        }
        
        resultado.append("\nDistância total: ").append(totalDistancia).append(" km");
        resultado.append("\n\n").append(resultadoDetalhado);
        
        System.out.println(resultado.toString());
    }

    public void addVertice(String nome, boolean capital) {
        No novoPonto = new No(nome, capital);
        nos.add(novoPonto);
    }

    public void addAresta(String nomeNo1, String nomeNo2, int peso) {
        No no1 = encontrarNoPorNome(nomeNo1);
        No no2 = encontrarNoPorNome(nomeNo2);
        if (no1 != null && no2 != null) {
            Aresta novaAresta = new Aresta(no1, no2, peso);
            arestas.add(novaAresta);
        }
    }

    private No encontrarNoPorNome(String nome) {
        // Normaliza a entrada para remover acentos
        String nomeNormalizado = Normalizer.normalize(nome, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "").toLowerCase();
        
        for (No no : nos) {
            String nomeNoNormalizado = Normalizer.normalize(no.getNome(), Normalizer.Form.NFD)
                    .replaceAll("[^\\p{ASCII}]", "").toLowerCase();
            if (nomeNoNormalizado.equals(nomeNormalizado)) {
                return no;
            }
        }
        return null;
    }

    private void listarCidades() {
        System.out.println("Cidades disponíveis:");
        for (int i = 0; i < nos.size(); i++) {
            System.out.println((i + 1) + ". " + nos.get(i).getNome() + (nos.get(i).isCapital() ? " (Capital)" : ""));
        }
    }

    public static void main(String[] args) {
        GrafoCidades grafo = new GrafoCidades();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Cálculo de Rotas entre Cidades Brasileiras ===");
            grafo.listarCidades();
            
            System.out.print("\nDigite o nome da cidade de origem (ou 'sair' para encerrar): ");
            String origemNome = scanner.nextLine().trim();
            if (origemNome.equalsIgnoreCase("sair")) {
                break;
            }

            No origem = grafo.encontrarNoPorNome(origemNome);
            if (origem == null) {
                System.out.println("Cidade de origem inválida. Tente novamente.");
                continue;
            }

            System.out.print("Digite o nome da cidade de destino: ");
            String destinoNome = scanner.nextLine().trim();
            No destino = grafo.encontrarNoPorNome(destinoNome);
            if (destino == null) {
                System.out.println("Cidade de destino inválida. Tente novamente.");
                continue;
            }

            grafo.encontrarMenorRota(origem, destino);
            grafo.mostrarResultado(origem, destino);

            // Pergunta se o usuário deseja calcular outra rota
            while (true) {
                System.out.print("\nDeseja calcular outra rota? (s/n): ");
                String resposta = scanner.nextLine().trim().toLowerCase();
                if (resposta.equals("s")) {
                    break; // Volta ao início do loop para nova entrada
                } else if (resposta.equals("n")) {
                    scanner.close();
                    System.out.println("Programa encerrado.");
                    return; // Encerra o programa
                } else {
                    System.out.println("Entrada inválida. Digite 's' para sim ou 'n' para não.");
                }
            }
        }

        scanner.close();
        System.out.println("Programa encerrado.");
    }
}