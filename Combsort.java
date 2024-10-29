import java.util.Random;

public class Combsort {
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

    public static void combsort(int[] array, Contadores contadores) {
        int n = array.length;
        int gap = n;
        boolean swapped = true;

        while (gap > 1 || swapped) {
            gap = (int) (gap / 1.3);
            if (gap < 1) {
                gap = 1;
            } 
            swapped = false;
        
        

            for (int i = 0; i + gap < n; i++) {
                contadores.iteracoes++;
                if (array[i] > array[i + gap]) {
                    int temp = array[i];
                    array[i] = array[i + gap];
                    array[i + gap] = temp;
                    contadores.trocas++;
                    swapped = true;
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

    public static Resultado resultado_comb(int[] array) {
        Contadores contadores = new Contadores();
        long tempo_inicial = System.currentTimeMillis();
        combsort(array, contadores);
        long tempo_final = System.currentTimeMillis();
        return new Resultado(tempo_final - tempo_inicial, contadores.trocas, contadores.iteracoes);
    }

    public static void main(String[] args) {
        int[] tamanho_vetores = {1000, 10000, 100000, 500000, 1000000};
        long seed = 88; 

        for (int tamanho : tamanho_vetores) {
            long tempo_total = 0, trocas_total = 0, iteracoes_total = 0;
            System.out.println("Tamanho do conjunto: " + tamanho);

            for (int rodada = 1; rodada <= 5; rodada++) {
                int[] array = gerar_array(tamanho, seed + rodada); 
                Resultado resultado = resultado_comb(array);
                tempo_total += resultado.tempo_total;
                trocas_total += resultado.trocas;
                iteracoes_total += resultado.iteracoes;
                System.out.println("Rodada " + rodada + ": " + resultado);
            }
            System.out.printf("Média - Tempo: %d ms, Trocas: %d, Iterações: %d%n",tempo_total / 5, trocas_total / 5,iteracoes_total / 5);
            System.out.println("-----------------------------");
        }
    }
}
