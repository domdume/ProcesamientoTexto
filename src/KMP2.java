import java.util.ArrayList;
import java.util.List;

public class KMP2 {
    public static int comparaciones = 0;
    public static int desplazamientos = 0;
    public static int fallos = 0;
    public static int coincidencias = 0;
        /**
         * Método de búsqueda KMP que muestra métricas y resultados
         *
         * @param texto Cadena de texto donde se busca el patrón
         * @param patron Patrón a buscar en el texto
         */
        public void searchKMP2(String texto, String patron) {
            // Validaciones iniciales
            if (texto == null || patron == null) {
                System.out.println("Error: Texto o patrón nulo.");
                return;
            }

            if (patron.isEmpty()) {
                System.out.println("Error: El patrón no puede estar vacío.");
                return;
            }

            // Calcula la tabla de prefijos (b[])
            int[] tablaPrefijos = calcularTablaPrefijos(patron);

            // Variables para métricas
            int i = 0; // Índice para recorrer el texto
            int j = 0; // Índice para recorrer el patrón

            // Lista para almacenar índices de coincidencias
            List<Integer> indicesCoincidencias = new ArrayList<>();

            while (i < texto.length()) {
                comparaciones++; // Incrementa comparaciones

                // Si los caracteres coinciden, avanzamos en ambos
                if (texto.charAt(i) == patron.charAt(j)) {
                    i++;
                    j++;

                    // Si encontramos todo el patrón
                    if (j == patron.length()) {
                        coincidencias++; // Incrementa coincidencias
                        indicesCoincidencias.add(i - j); // Guarda índice de la coincidencia

                        // Resetear j para buscar más coincidencias
                        j = tablaPrefijos[j - 1] + 1;
                    }
                } else {
                    // Si no coinciden, usamos la tabla de prefijos para determinar el salto
                    fallos++; // Incrementa fallos

                    if (j > 0) {
                        // Desplazamiento basado en la tabla de prefijos
                        int antiguoJ = j;
                        j = tablaPrefijos[j - 1] + 1;
                        desplazamientos++; // Incrementa desplazamientos
                    } else {
                        // Si j es 0, simplemente movemos el índice del texto
                        i++;
                        desplazamientos++; // Incrementa desplazamientos
                    }
                }
            }

            // Mostrar resultados
            System.out.println("\n--- Resultados Búsqueda KMP2 ---");
            System.out.println("Texto: " + texto);
            System.out.println("Patrón: " + patron);

            if (coincidencias > 0) {
                System.out.println("Coincidencias encontradas en índices: " + indicesCoincidencias);
            } else {
                System.out.println("Patrón NO encontrado en el texto.");
            }

            // Mostrar métricas
            imprimirMetrica();
        }

    private static void imprimirMetrica() {
        System.out.println("\n--- Métricas ---");
        System.out.println("Comparaciones totales: " + comparaciones);
        System.out.println("Fallos: " + fallos);
        System.out.println("Desplazamientos: " + desplazamientos);
        System.out.println("Número de coincidencias: " + coincidencias);
    }

    /**
     * Calcula la tabla de prefijos para el algoritmo KMP
     *
     * @param patron Patrón para calcular la tabla de prefijos
     * @return Array con los valores de la tabla de prefijos
     */
    private int[] calcularTablaPrefijos(String patron) {
        int[] b = new int[patron.length()];
        b[0] = -1;

        int i = 1;
        int j = 0;

        while (i < patron.length()) {
            if (patron.charAt(i) == patron.charAt(j)) {
                b[i] = j;
                i++;
                j++;
            } else {
                if (j > 0) {
                    j = b[j - 1] + 1;
                } else {
                    b[i] = -1;
                    i++;
                }
            }
        }

        return b;
    }
}

