import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StringMatching {

    private static int comparaciones = 0;
    private static int desplazamientos = 0;
    private static int fallos = 0;
    private static int coincidencias = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nSeleccione el algoritmo que desea utilizar:");
            System.out.println("1. Fuerza Bruta (FB)");
            System.out.println("2. Knuth-Morris-Pratt (KMP)");
            System.out.println("3. Ambos algoritmos");
            System.out.println("4. Salir");
            System.out.print("Ingrese su opción: ");

            int opcion = scanner.nextInt();

            if (opcion == 4) {
                System.out.println("Saliendo del programa.");
                break;
            }

            System.out.print("Ingrese una cadena: ");
            String text = scanner.next();
            System.out.print("Ingrese un patrón que desea comparar: ");
            String pattern = scanner.next();

            switch (opcion) {
                case 1:
                    searchFB(text, pattern);
                    break;
                case 2:
                    searchKMP(text, pattern);
                    break;
                case 3:
                    System.out.println("Algoritmo Fuerza Bruta (FB):");
                    searchFB(text, pattern);
                    System.out.println("\nAlgoritmo Knuth-Morris-Pratt (KMP):");
                    searchKMP(text, pattern);
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
        scanner.close();
    }

    public static void searchFB(String text, String pattern) {
        long totTime = 0;
        long startTime = System.currentTimeMillis();
        int n = text.length();
        int m = pattern.length();
        int comparaciones = 0;
        int desplazamientos = 0;
        int fallos = 0;
        int coincidencias = 0;
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
        System.out.println("Número de comparaciones: " + comparaciones);
        System.out.println("Número de desplazamientos: " + desplazamientos);
        System.out.println("Número de fallos: " + (fallos));
        totTime += (System.currentTimeMillis() - startTime);
        System.out.println("Tiempo de computo: " + totTime / 1e6 + "[ms]");
    }

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
        System.out.println("Número de comparaciones: " + comparaciones);
        System.out.println("Número de desplazamientos: " + desplazamientos);
        System.out.println("Número de fallos: " + fallos);
        System.out.println("Número de coincidencias: " + coincidencias);
        System.out.println("Tiempo de computo: " + tiempoFin + "[ms]");
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