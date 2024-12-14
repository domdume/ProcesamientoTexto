import java.util.*;

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
    public List<Integer> searchBM(String text, String pattern) {
        long startTime = System.currentTimeMillis();
        comparaciones = 0;
        fallos = 0;
        desplazamientos = 0;
        coincidencias = 0;
        indicesCoincidencias = new ArrayList<>();
        int n = text.length();
        int m = pattern.length();
        if (m > n) {
            return indicesCoincidencias;
        }
        Map<Character, Integer> badCharacter = preprocessBadCharacter(pattern);
        bmInitOcc(pattern,256);
        int[] goodSuffix = preprocessGoodSuffix(pattern);
        mostrarTablaGoodSuffix(pattern, goodSuffix);
        int i = 0;
        while (i <= n - m) {
            int j = m - 1;
            while (j >= 0 && pattern.charAt(j) == text.charAt(i + j)) {
                comparaciones++;
                j--;
            }
            if (j < 0) {
                coincidencias++;
                indicesCoincidencias.add(i);
                i += goodSuffix[0];
            } else {
                fallos++;
                int badCharShift = j - badCharacter.getOrDefault(text.charAt(i + j), -1);
                int goodSuffixShift = goodSuffix[j + 1];
                int shift = Math.max(badCharShift, goodSuffixShift);
                i += shift;
            }
            desplazamientos++;
        }

        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("Tiempo de computo: " + totalTime / 1e6 + "[ms]");

        imprimirMetricas();
        return indicesCoincidencias;
    }
    private static void imprimirMetricas() {
        // Mostrar métricas
        System.out.println("\n--- Métricas ---");
        System.out.println("Número de coincidencias: " + coincidencias);

        if (coincidencias > 0) {
            System.out.println("Coincidencias encontradas en índices: " + indicesCoincidencias);
        } else {
            System.out.println("Patrón NO encontrado en el texto.");
        }
    }
    private static Map<Character, Integer> preprocessBadCharacter(String pattern) {
        Map<Character, Integer> badCharacter = new HashMap<>();

        for (int i = 0; i < pattern.length(); i++) {
            badCharacter.put(pattern.charAt(i), i);
        }

        return badCharacter;
    }
    private static int[] preprocessGoodSuffix(String pattern) {
        int m = pattern.length();
        int[] s = new int[m + 1];
        int[] f = new int[m + 1];
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
    private static void mostrarTablaGoodSuffix(String pattern, int[] goodSuffix) {
        System.out.println("\n--- Tabla de Heurística de Sufijo Bueno ---");
        System.out.println("Patrón: " + pattern);
        System.out.println("Desplazamientos por Sufijo:");
        for (int i = 0; i < goodSuffix.length; i++) {
            System.out.println("Índice " + i + ": " + goodSuffix[i]);
        }
    }
    /**
     * Método para inicializar y procesar la tabla de ocurrencias (Bad Character)
     * @param pattern Patrón a analizar
     * @param alphabetSize Tamaño del alfabeto (por ejemplo, 256 para ASCII)
     */
    private void bmInitOcc(String pattern, int alphabetSize) {
        int m = pattern.length();
        int[] occ = new int[alphabetSize];
        for (int i = 0; i < alphabetSize; i++) {
            occ[i] = m;
        }
        char[] p = pattern.toCharArray();
        for (int j = 0; j < m; j++) {
            occ[p[j]] = m - 1 - j; // Almacenar la última aparición de cada carácter
        }
        TreeMap<Character, Integer> orderedOccurrences = new TreeMap<>();
        for (int j = 0; j < m; j++) {
            orderedOccurrences.put(p[j], occ[p[j]]);
        }
        System.out.println("Tabla de Prefijo Malo (D1):");
        System.out.print("D1: ");
        orderedOccurrences.forEach((key, value) -> System.out.print(key + " "));
        System.out.println();
        System.out.print("D1: ");
        orderedOccurrences.forEach((key, value) -> System.out.print(value + " "));
        System.out.println();
        System.out.println("OTROS: " + m);
    }
}
