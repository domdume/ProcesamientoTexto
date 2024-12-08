import java.util.Scanner;

public class StringMatching {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nSeleccione el algoritmo que desea utilizar:");
            System.out.println("1. Fuerza Bruta (FB)");
            System.out.println("2. Knuth-Morris-Pratt (KMP)");
            System.out.println("3. Salir");
            System.out.print("Ingrese su opción: ");

            int opcion = scanner.nextInt();

            if (opcion == 3) {
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
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    public static void searchFB(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();
        int comparaciones = 0;
        int desplazamientos = 0;
        int fallos = 0;
        int coincidencias = 0;

        for (int i = 0; i <= n - m; i++) {
            desplazamientos++;
            int j;

            for (j = 0; j < m; j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    fallos++;
                    comparaciones += j;
                    break;
                }
            }
            if (j == m) {
                coincidencias++;
                comparaciones += m;
                System.out.println("Patrón encontrado en el índice " + i);
            }
        }
        System.out.println("Número de comparaciones: " + comparaciones);
        System.out.println("Número de desplazamientos: " + desplazamientos);
        System.out.println("Número de fallos: " + fallos);
        System.out.println("Número de coincidencias: " + coincidencias);
    }

    public static void searchKMP(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();
        int[] lps = computeLPSArray(pattern);

        int comparaciones = 0;
        int desplazamientos = 0;
        int coincidencias = 0;

        int i = 0; // Índice para text
        int j = 0; // Índice para pattern

        while (i < n) {
            comparaciones++;

            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }

            if (j == m) {
                coincidencias++;
                System.out.println("Patrón encontrado en el índice " + (i - j));
                j = lps[j - 1];
            } else if (i < n && text.charAt(i) != pattern.charAt(j)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
                desplazamientos++;
            }
        }

        System.out.println("Número de comparaciones: " + comparaciones);
        System.out.println("Número de desplazamientos: " + desplazamientos);
        System.out.println("Número de coincidencias: " + coincidencias);
    }

    private static int[] computeLPSArray(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];

        int length = 0; // Longitud del prefijo-sufijo más largo
        int i = 1;
        lps[0] = 0; // lps[0] siempre es 0

        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }
}

