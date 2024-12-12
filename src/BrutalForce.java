public class BrutalForce {
    public static int comparaciones = 0;
    public static int desplazamientos = 0;
    public static int fallos = 0;
    public static int coincidencias = 0;
    public static void searchFB(String text, String pattern) {
        long totTime = 0;
        long startTime = System.currentTimeMillis();
        int n = text.length();
        int m = pattern.length();
        for (int i = 0; i <= n - m; i++) {
            int k = 0;
            while (k < m && text.charAt(i + k) == pattern.charAt(k))
                k++;
            comparaciones = comparaciones + k;
            if (k == m) {
                coincidencias++;
                fallos = i;
                desplazamientos = fallos;
                System.out.println("Patrón encontrado en el índice " + i);
            }
        }
        imprimirMetrica();
        totTime += (System.currentTimeMillis() - startTime);
        System.out.println("Tiempo de computo: " + totTime / 1e6 + "[ms]");
    }

    private static void imprimirMetrica() {
        System.out.println("\n--- Métricas ---");
        System.out.println("Número de comparaciones: " + comparaciones);
        System.out.println("Número de desplazamientos: " + desplazamientos);
        System.out.println("Número de fallos: " + fallos);
    }
}
