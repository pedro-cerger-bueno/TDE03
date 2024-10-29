import java.util.Random;

public class Merge {
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
    public static void Merge_sort(int[] array, int esquerda, int direita, Contadores contadores) {
        if (esquerda < direita) {
            int meio = (esquerda + direita) / 2;
            Merge_sort(array, esquerda, meio, contadores);
            Merge_sort(array, meio + 1, direita, contadores);
            merge(array, esquerda, meio, direita, contadores);
        }
    }
    public static void merge(int[] array, int esquerda, int meio, int direita, Contadores contadores) {
        int n1 = meio - esquerda + 1;
        int n2 = direita - meio;
        int[] E = new int[n1];
        int[] D = new int[n2];

      
        for (int i = 0; i < n1; i++) E[i] = array[esquerda + i];
        for (int j = 0; j < n2; j++) D[j] = array[meio + 1 + j];

        int i = 0, j = 0;
        int k = esquerda;
        while (i < n1 && j < n2) {
            contadores.iteracoes++;  
            if (E[i] <= D[j]) {
                array[k] = E[i];
                i++;
            } else {
                array[k] = D[j];
                j++;
            }
            contadores.trocas++; 
            k++;
        }
        while (i < n1) {
            array[k] = E[i];
            i++;
            k++;
            contadores.trocas++;
        }
        while (j < n2) {
            array[k] = D[j];
            j++;
            k++;
            contadores.trocas++;
        }
    }

    public static class Resultado {
        long tempo_total;
        long trocas;
        long iteracoes;

        public Resultado(long tempo_total, long trocas, long iteracoes) {
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

    public static Resultado resultado_merge(int[] array) {
        Contadores contadores = new Contadores();
        long tempo_inicial = System.currentTimeMillis();
        Merge_sort(array, 0, array.length - 1, contadores);
        long tempo_final = System.currentTimeMillis();
        return new Resultado(tempo_final - tempo_inicial, contadores.trocas, contadores.iteracoes);
    }

    public static void main(String[] args) {
        int[] tamanho_vetores = {1000, 10000, 100000, 500000, 1000000};
        long seed = 55; 
    
        for (int tamanho : tamanho_vetores) {
            long soma_tempo = 0, soma_trocas = 0, soma_iteracoes = 0;
    
            System.out.println("Tamanho do conjunto: " + tamanho);
    
            for (int rodada = 1; rodada <= 5; rodada++) {
                int[] array = gerar_array(tamanho, seed + rodada);  
                Resultado resultado = resultado_merge(array);
    
                soma_tempo += resultado.tempo_total;
                soma_trocas += resultado.trocas;
                soma_iteracoes += resultado.iteracoes;
    
                System.out.println("Rodada " + rodada + ": " + resultado);
            }
    
            System.out.printf("Média - Tempo: %d ms, Trocas: %d, Iterações: %d%n",
                    soma_tempo / 5, soma_trocas / 5, soma_iteracoes / 5);
            System.out.println("---------------------");
        }
    }
}    