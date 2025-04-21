
public class InterligarCapitais {

    public static void interligarTodasCapitais(Grafo grafo) {
        class Capital {
            String nome;
            int x, y;
            double lat, lon;

            Capital(String nome, double lat, double lon) {
                this.nome = nome;
                this.lat = lat;
                this.lon = lon;
                this.x = (int)(400 + (lon + 70) * 12);
                this.y = (int)(300 - lat * 12);
            }
        }

        Capital[] capitais = {
            new Capital("Rio Branco", -9.975, -67.824),
            new Capital("Maceió", -9.647, -35.733),
            new Capital("Macapá", 0.034, -51.066),
            new Capital("Manaus", -3.119, -60.021),
            new Capital("Salvador", -12.971, -38.501),
            new Capital("Fortaleza", -3.731, -38.526),
            new Capital("Brasília", -15.779, -47.929),
            new Capital("Vitória", -20.319, -40.338),
            new Capital("Goiânia", -16.686, -49.265),
            new Capital("São Luís", -2.531, -44.293),
            new Capital("Cuiabá", -15.601, -56.097),
            new Capital("Campo Grande", -20.443, -54.647),
            new Capital("Belo Horizonte", -19.921, -43.935),
            new Capital("Belém", -1.455, -48.490),
            new Capital("João Pessoa", -7.115, -34.863),
            new Capital("Curitiba", -25.428, -49.273),
            new Capital("Recife", -8.054, -34.881),
            new Capital("Teresina", -5.089, -42.801),
            new Capital("Rio de Janeiro", -22.907, -43.173),
            new Capital("Natal", -5.795, -35.209),
            new Capital("Porto Alegre", -30.034, -51.218),
            new Capital("Porto Velho", -8.762, -63.904),
            new Capital("Boa Vista", 2.820, -60.672),
            new Capital("Florianópolis", -27.596, -48.549),
            new Capital("São Paulo", -23.550, -46.633),
            new Capital("Aracaju", -10.947, -37.073),
            new Capital("Palmas", -10.249, -48.324)
        };

        // Adicionar todas as capitais como vértices
        for (Capital capital : capitais) {
            grafo.addVertice(capital.nome, capital.x, capital.y);
        }

        // Conectar cada capital com todas as outras
        for (int i = 0; i < capitais.length; i++) {
            for (int j = i + 1; j < capitais.length; j++) {
                Capital c1 = capitais[i];
                Capital c2 = capitais[j];

                // Calcular a distância em quilômetros entre as duas capitais
                int distancia = calcularDistanciaEmKm(c1.lat, c1.lon, c2.lat, c2.lon);

                // Adicionar a aresta com o peso calculado
                grafo.addAresta(c1.nome, c2.nome, distancia);
            }
        }
    }

    private static int calcularDistanciaEmKm(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distancia = R * c;

        return (int) Math.round(distancia);
    }

    public static void main(String[] args) {
        System.out.println("Interligação de capitais realizada diretamente na classe Grafo.");
    }
}