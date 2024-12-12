import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class StringMatching {
    public static void main(String[] args) {
        BrutalForce brutalForce = new BrutalForce();
        BoyerMoore boyerMoore = new BoyerMoore();
        KMP1 kmp1 = new KMP1();
        KMP2 kmp2 = new KMP2();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nSeleccione el algoritmo que desea utilizar:");
            System.out.println("1. Fuerza Bruta (FB)");
            System.out.println("2. Knuth-Morris-Pratt (KMP) metodo 1");
            System.out.println("3. Knuth-Morris-Pratt (KMP) metodo 2");
            System.out.println("4. Boyer Moore metodo 2");
            System.out.println("5. Salir");
            System.out.print("Ingrese su opción: ");

            int opcion = scanner.nextInt();
            if (opcion == 5) {
                System.out.println("Saliendo del programa.");
                break;
            }
            System.out.print("Ingrese una cadena: ");
            String text = scanner.next();
            System.out.print("Ingrese un patrón que desea comparar: ");
            String pattern = scanner.next();

            switch (opcion) {
                case 1:
                    brutalForce.searchFB(text, pattern);
                    break;
                case 2:
                    kmp1.searchKMP(text, pattern);
                    break;
                case 3:
                    kmp2.searchKMP2(text, pattern);
                case 4:
                    boyerMoore.searchBM(text, pattern);

                default:
                    System.out.println("Opción no válida.");
            }
        }
        scanner.close();
    }
}