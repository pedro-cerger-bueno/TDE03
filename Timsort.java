import java.util.Random;

public class Timsort {
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
    static final int min_run = 24;

    public static void insert_sort(int[] array, int esquerda, int direita, Contadores contadores) {
        for (int i = esquerda + 1; i <= direita; i++) {
            int temp = array[i];
            int j = i - 1;
            while (j >= esquerda && array[j] > temp) {
                contadores.iteracoes++;  
                array[j + 1] = array[j];
                j--;
                contadores.trocas++;  
            }
            array[j + 1] = temp;
            contadores.trocas++;  
        }
    }

    public static void merge(int[] array, int esquerda, int meio, int direita, Contadores contadores) {
        int n1 = meio - esquerda + 1;
        int n2 = direita - meio;

        int[] E = new int[n1];
        int[] D = new int[n2];
        for (int i = 0; i < n1; i++) {
            E[i] = array[esquerda + i];
        }
        for (int j = 0; j < n2; j++) {
            D[j] = array[meio + 1 + j];
        }
        int i = 0, j = 0, k = esquerda;
        while (i < n1 && j < n2) {
            contadores.iteracoes++;  
            if (E[i] <= D[j]) {
                array[k++] = E[i++];
            } else {
                array[k++] = D[j++];
            }
            contadores.trocas++;  
        }

        while (i < n1) {
            array[k++] = E[i++];
            contadores.trocas++;
        }

        while (j < n2) {
            array[k++] = D[j++];
            contadores.trocas++;
        }
    }

    public static void timsort(int[] array, int n, Contadores contadores) {
        for (int i = 0; i < n; i += min_run) {
            int fim = (i + min_run - 1 < n - 1) ? i + min_run - 1 : n - 1;
            insert_sort(array, i, fim, contadores);
        }
        for (int tamanho = min_run; tamanho < n; tamanho = 2 * tamanho) {
            for (int esquerda = 0; esquerda < n; esquerda += 2 * tamanho) {
                int meio = esquerda + tamanho - 1;
                int direita = (esquerda + 2 * tamanho - 1 < n - 1) ? esquerda + 2 * tamanho - 1 : n - 1;
                if (meio < direita) {
                    merge(array, esquerda, meio, direita, contadores);
                }
            }
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

    public static Resultado resultado_tim(int[] array) {
        Contadores contador = new Contadores();
        long tempo_inicial = System.currentTimeMillis();
        timsort(array, array.length, contador);
        long tempo_final = System.currentTimeMillis();
        return new Resultado(tempo_final - tempo_inicial, contador.trocas, contador.iteracoes);
    }

    public static void main(String[] args) {
        int[] tamanho_vetores = {1000, 10000, 100000, 500000, 1000000};
        long seed = 60; 
         for (int tamanho : tamanho_vetores) {
            long tempo_total = 0, trocas_total = 0, iteracoes_total = 0;
            System.out.println("Tamanho do conjunto: " + tamanho);

            for (int rodada = 1; rodada <= 5; rodada++) {
                int[] array = gerar_array(tamanho, seed + rodada);  
                Resultado resultado = resultado_tim(array);
                tempo_total += resultado.tempo_total;
                trocas_total += resultado.trocas;
                iteracoes_total += resultado.iteracoes;
                System.out.println("Rodada " + rodada + ": " + resultado);
            }
            System.out.printf("Média - Tempo: %d ms, Trocas: %d, Iterações: %d%n", tempo_total / 5, trocas_total / 5, iteracoes_total / 5);
            System.out.println("-------------------------------");
        }
    }
}

