import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class BoyerMoore {
    private static int comparaciones;
    private static int fallos;
    private static int desplazamientos;
    private static int coincidencias;
    private static List<Integer> indicesCoincidencias;

    /**
     * Método  para realizar la búsqueda de patrones utilizando el algoritmo de Boyer-Moore
     *
     * @param text Texto donde se busca el patrón
     * @param pattern Patrón a buscar
     * @return void (imprime las métricas directamente)
     */
    public  void searchBM(String text, String pattern) {
        long totTime = 0;
        long startTime = System.currentTimeMillis();
        // Reiniciar métricas
        comparaciones = 0;
        fallos = 0;
        desplazamientos = 0;
        coincidencias = 0;
        indicesCoincidencias = new ArrayList<>();

        // Longitudes del texto y del patrón
        int n = text.length();
        int m = pattern.length();

        // Caso especial: patrón más largo que texto
        if (m > n) {
            imprimirMetricas();
            return;
        }

        // Preprocesamiento de heurística de mal carácter
        Map<Character, Integer> badCharacter = preprocessBadCharacter(pattern);

        // Preprocesamiento de heurística de sufijo bueno
        int[] goodSuffix = preprocessGoodSuffix(pattern);

        // Índice para recorrer el texto
        int i = 0;
        while (i <= n - m) {
            // Comparación de derecha a izquierda
            int j = m - 1;

            // Mientras los caracteres coincidan de derecha a izquierda
            while (j >= 0 && pattern.charAt(j) == text.charAt(i + j)) {
                comparaciones++;
                j--;
            }

            // Si se encontró un patrón completo
            if (j < 0) {
                coincidencias++;
                indicesCoincidencias.add(i);
                // Desplazamiento según el sufijo bueno
                i += goodSuffix[0];
            } else {
                // Incrementar fallos
                fallos++;

                // Cálculo del desplazamiento máximo
                int badCharShift = j - badCharacter.getOrDefault(text.charAt(i + j), -1);
                int goodSuffixShift = goodSuffix[j + 1];

                // Desplazamiento máximo
                int shift = Math.max(badCharShift, goodSuffixShift);
                i += shift;
            }

            // Incrementar desplazamientos
            desplazamientos++;
        }
        imprimirMetricas();
        totTime += (System.currentTimeMillis() - startTime);
        System.out.println("Tiempo de computo: " + totTime / 1e6 + "[ms]");
    }

    /**
     * Método para imprimir las métricas de búsqueda
     */
    private static void imprimirMetricas() {
        // Mostrar métricas
        System.out.println("\n--- Métricas ---");
        System.out.println("Fallos: " + fallos);
        System.out.println("Desplazamientos: " + (desplazamientos-1));
        System.out.println("Número de coincidencias: " + coincidencias);

        if (coincidencias > 0) {
            System.out.println("Coincidencias encontradas en índices: " + indicesCoincidencias);
        } else {
            System.out.println("Patrón NO encontrado en el texto.");
        }
    }

    /**
     * Preprocesamiento de la heurística de mal carácter
     */
    private static Map<Character, Integer> preprocessBadCharacter(String pattern) {
        Map<Character, Integer> badCharacter = new HashMap<>();

        for (int i = 0; i < pattern.length(); i++) {
            badCharacter.put(pattern.charAt(i), i);
        }

        return badCharacter;
    }
    /**
     * Preprocesamiento de la heurística de sufijo bueno
     */
    private static int[] preprocessGoodSuffix(String pattern) {
        int m = pattern.length();
        int[] s = new int[m + 1];
        int[] f = new int[m + 1];

        // Caso 1: Preparación para sufijos
        f[m] = m + 1;
        int i = m, j = m + 1;

        while (i > 0) {
            while (j <= m && pattern.charAt(i - 1) != pattern.charAt(j - 1)) {
                if (s[j] == 0) {
                    s[j] = j - i;
                }
                j = f[j];
            }
            i--;
            j--;
            f[i] = j;
        }

        // Caso 2: Preparación para bordes del patrón
        j = f[0];
        for (i = 0; i <= m; i++) {
            if (s[i] == 0) {
                s[i] = j;
            }
            if (i == j) {
                j = f[j];
            }
        }

        return s;
    }
}
