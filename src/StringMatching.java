import java.util.Scanner;

public class StringMatching {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int result;
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
                    System.out.print("FUERZA BRUTA \n");
                    searchFB(text, pattern);
                    System.out.print("KMP \n");
                    searchKMP(text, pattern);
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }
    public static void searchFB(String text, String pattern) {
        long totTime=0;
        long startTime=System.currentTimeMillis();
        int n = text.length();
        int m = pattern.length();
        int comparaciones = 0;
        int desplazamientos = 0;
        int fallos = 0;
        int coincidencias = 0;
        for (int i=0; i<= n-m; i++) {
            int k=0;
            while(k<m && text.charAt(i+k) == pattern.charAt(k))
                k++;
            comparaciones=comparaciones+k;
            if(k==m){
                coincidencias++;
            fallos=i;
            desplazamientos=fallos;
                System.out.println("Patrón encontrado en el índice " + i);
        }}
        System.out.println("Número de comparaciones: " + comparaciones);
        System.out.println("Número de desplazamientos: " + desplazamientos);
        System.out.println("Número de fallos: " + (fallos));
        System.out.println("Número de coincidencias: " + coincidencias);
        totTime+=(System.currentTimeMillis()-startTime);
        System.out.println("Tiempo de computo: "+totTime/1e6 + "[ms]");
    }

    public static void searchKMP(String text, String pattern) {
        long totTime=0;
        long startTime=System.currentTimeMillis();
        int n = text.length();
        int m = pattern.length();
        if (m > n) {
            System.out.println("El patrón no puede ser mayor que el texto.");
            return;
        }
        int comparaciones = 0;
        int fallos = 0;
        int desplazamientos = 0;
        int coincidencias = 0;
        char[] x = pattern.toCharArray();
        char[] y = text.toCharArray();
        int[] mpNext = computeLPSArray(x);
        int i = 0; // Índice para text
        int j = 0; // Índice para pattern
        while (j < n) {
            comparaciones++; // Cada comparación cuenta
            while (i > -1 && x[i] != y[j]) {
                fallos++; // Fallo cuando hay un desajuste
                i = mpNext[i];
                desplazamientos++; // Desplazamiento cuando el patrón retrocede
            }
            i++;
            j++;
            if (i >= m) {
                coincidencias++; // Se encontró una coincidencia completa
                System.out.println("Coincidencia encontrada en el índice: " + (j - i));
                i = mpNext[i];
            }
        }
        System.out.println("Número de comparaciones: " + comparaciones);
        System.out.println("Número de desplazamientos: " + desplazamientos);
        System.out.println("Número de fallos: " + fallos);
        System.out.println("Número de coincidencias: " + coincidencias);
        totTime+=(System.currentTimeMillis()-startTime);
        System.out.println("Tiempo de computo: "+totTime/1e6 + "[ms]");
    }
    private static int[] computeLPSArray(char[] pattern) {
        int m = pattern.length;
        int[] mpNext = new int[m + 1];
        int i = 0, j = -1;
        mpNext[0] = -1;
        while (i < m) {
            while (j > -1 && pattern[i] != pattern[j]) {
                j = mpNext[j];
            }
            i++;
            j++;
            mpNext[i] = j;
        }
        return mpNext;
    }
}

