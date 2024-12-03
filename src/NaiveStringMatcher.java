import java.util.Scanner;

public class NaiveStringMatcher {

    public static void search(String text, String pattern) {
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
                //comparaciones ++;
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    fallos++;
                    comparaciones += j;
                    break;
                }
            }
            if (j == m) {
                coincidencias ++;
                comparaciones += m;
                //System.out.println("Patrón encontrado en el índice " + i);
            }
        }
        System.out.println("Número de comparaciones:" + comparaciones);
        System.out.println("Número de desplazamientos:" + desplazamientos);
        System.out.println("Número de fallos:" + fallos);
        System.out.println("Número de coincidencias:" + coincidencias);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese una cadena: ");
        String text = scanner.next();
        System.out.print("Ingrese un patrón que desea comparar: ");
        String pattern = scanner.next();
        search(text, pattern);
    }
}