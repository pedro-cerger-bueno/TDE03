import java.util.Random;

public class Quicksort {
    public static int[] gerar_array(int tamanho, long seed) {
        Random random = new Random(seed);
        int[] array = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            array[i] = random.nextInt();  
        }
        return array;
    }
    public static class Contadores {
        long trocas = 0;     
        long iteracoes = 0;  
    }
    public static void quicksort(int[] array, int menor, int maior, Contadores contadores) {
        if (menor < maior) {
            int index_pivo = divisao(array, menor, maior, contadores);  
            quicksort(array, menor, index_pivo - 1, contadores);  
            quicksort(array, index_pivo + 1, maior, contadores);  
        }
    }
    public static int divisao(int[] array, int menor, int maior, Contadores contador) {
        int pivo = array[maior];  
        int i = menor - 1;  
        for (int j = menor; j < maior; j++) {
            contador.iteracoes++; 
            if (array[j] <= pivo) {
                i++;
                troca(array, i, j, contador);
            }
        }
        troca(array, i + 1, maior, contador);  
        return i + 1;
    }
    public static void troca(int[] array, int i, int j, Contadores contadores) {
        if (i != j) {  
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
            contadores.trocas++;  
        }
    }
    public static class Resultados {
        long tempo_total;
        long trocas;
        long iteracoes;

        public Resultados(long tempo_total, long trocas, long iteracoes) {
            this.tempo_total = tempo_total;
            this.trocas = trocas;
            this.iteracoes = iteracoes;
        }
        @Override
        public String toString() {
            return String.format("Tempo: %d ms, Trocas: %d, Iterações: %d",
                    tempo_total, trocas, iteracoes);
        }
    }
    public static Resultados resultado_quick(int[] array) {
        Contadores contadores = new Contadores();
        long tempo_inicial = System.currentTimeMillis();
        quicksort(array, 0, array.length - 1, contadores);
        long tempo_final = System.currentTimeMillis();
        return new Resultados(tempo_final - tempo_inicial, contadores.trocas, contadores.iteracoes);
    }

    public static void main(String[] args) {
        int[] tamanho_vetores = {1000, 10000, 100000, 500000, 1000000};
        long seed = 42;

        for (int tamanho : tamanho_vetores) {
            long tempo_total = 0, trocas_total = 0, iteracoes_total = 0;
            System.out.println("Tamanho do conjunto: " + tamanho);
            for (int rodada = 1; rodada <= 5; rodada++) {
                int[] array = gerar_array(tamanho, seed + rodada); 
                Resultados resultado = resultado_quick(array); tempo_total += resultado.tempo_total;
                trocas_total += resultado.trocas;
                iteracoes_total += resultado.iteracoes;
                System.out.println("Rodada " + rodada + ": " + resultado);
            }

            System.out.printf("Média - Tempo: %d ms, Trocas: %d, Iterações: %d%n", tempo_total / 5, trocas_total / 5, iteracoes_total / 5);
            System.out.println("---------------------------------");
        }
    }
}
