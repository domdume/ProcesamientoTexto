import java.util.ArrayList;
import java.util.List;

public class KMP1 {
    public static int comparaciones = 0;
    public static int desplazamientos = 0;
    public static int fallos = 0;
    public static int coincidencias = 0;
    public static void searchKMP(String texto, String patrón) {
        long tiempoInicio = System.currentTimeMillis(); // Tiempo de inicio de la ejecución
        int n = texto.length(); // Longitud del texto
        int m = patrón.length(); // Longitud del patrón
        List<Integer> posiciones = new ArrayList<>(); // Lista para almacenar las posiciones de coincidencia
        // Pre-procesar el patrón para construir el arreglo LPS
        int[] lps = determinarTablaLPS(patrón);
        int i = 0; // Índice del texto
        int j = 0; // Índice del patrón

        // Realizar la búsqueda del patrón en el texto
        while (i < n) {
            // Comparar caracteres del patrón y el texto
            if (patrón.charAt(j) == texto.charAt(i)) {
                comparaciones++; // Incrementar el contador de comparaciones
                coincidencias++; // Incrementar el contador de coincidencias
                i++;
                j++;
            }

            // Sí se encontró una coincidencia completa
            if (j == m) {
                coincidencias++; // Incrementar el contador de apariciones
                System.out.println("Coincidencia encontrada en el índice: " + (i - j));
                j = lps[j - 1]; // Ajustar índice del patrón utilizando el arreglo LPS
            } else if (i < n && patrón.charAt(j) != texto.charAt(i)) {
                // Sí hay un desajuste entre el patrón y el texto
                comparaciones++; // Incrementar el contador de comparaciones (fallida)
                fallos++; // Incrementar el contador de fallos
                desplazamientos++; // Incrementar el contador de desplazamientos

                // Ajustar índice del patrón utilizando el arreglo LPS
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        double tiempoFin = (System.currentTimeMillis() - tiempoInicio) / 1e6; // Tiempo de fin de la ejecución
        imprimirMetrica();
        System.out.println("Tiempo de computo: " + tiempoFin + "[ms]");
    }

    private static void imprimirMetrica() {
        System.out.println("\n--- Métricas ---");
        System.out.println("Número de comparaciones: " + comparaciones);
        System.out.println("Número de desplazamientos: " + desplazamientos);
        System.out.println("Número de fallos: " + fallos);
        System.out.println("Número de coincidencias: " + coincidencias);
    }

    /**
     * Calcula el arreglo LPS (Longest Prefix Suffix) para el patrón.
     * Este arreglo permite evitar comparaciones redundantes durante la búsqueda.
     *
     * @param patrón El patrón para el cual se calculará el arreglo LPS.
     * @return Un arreglo LPS donde cada elemento indica el prefijo más largo que también es sufijo.
     */
    private static int[] determinarTablaLPS(String patrón) {
        int m = patrón.length();
        int[] lps = new int[m]; // Inicializar el arreglo LPS
        int len = 0; // Longitud del prefijo más largo que también es sufijo
        int i = 1;
        // Calcular el arreglo LPS
        while (i < m) {
            if (patrón.charAt(i) == patrón.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1]; // Ajustar longitud utilizando valores previos en LPS
                } else {
                    lps[i] = 0; // No hay prefijo que también sea sufijo
                    i++;
                }
            }
        }

        return lps; // Retornar el arreglo LPS calculado
    }
}
