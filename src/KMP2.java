import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KMP2 {

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

// Comienza la medición del tiempo en nanosegundos
long startTime = System.nanoTime();

// Calcula la tabla de prefijos (b[])
int[] tablaPrefijos = calcularTablaPrefijos(patron);

// Variables para métricas
int i = 0; // Índice para recorrer el texto
int j = 0; // Índice para recorrer el patrón
int comparaciones = 0;
int coincidencias = 0;
int fallos = 0;
int desplazamientos = 0;

// Lista para almacenar índices de coincidencias
List indicesCoincidencias = new ArrayList<>();

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
j = tablaPrefijos[j - 1] + 1;
desplazamientos++; // Incrementa desplazamientos
} else {
// Si j es 0, simplemente movemos el índice del texto
i++;
desplazamientos++; // Incrementa desplazamientos
}
}
}

// Finaliza la medición del tiempo en nanosegundos
long endTime = System.nanoTime();
long timeElapsed = endTime - startTime; // Tiempo transcurrido en nanosegundos

// Mostrar resultados
System.out.println("\n--- Resultados Búsqueda KMP2 ---");
System.out.println("Texto: " + texto);
System.out.println("Patrón: " + patron);

if (coincidencias > 0) {
System.out.println("Coincidencias encontradas en índices: " + indicesCoincidencias);
} else {
System.out.println("Patrón NO encontrado en el texto.");
}

char[] patronArr = patron.toCharArray();

System.out.println("\n--- Métricas ---");
System.out.println("Comparaciones totales: " + comparaciones);
System.out.println("Fallos: " + fallos);
System.out.println("Desplazamientos: " + desplazamientos);
System.out.println("Número de coincidencias: " + coincidencias);
System.out.println("Tabla LPS: ");
System.out.println(Arrays.toString(patronArr));
System.out.println(Arrays.toString(tablaPrefijos));

// Mostrar el tiempo transcurrido
System.out.println("Tiempo de ejecución: " + timeElapsed + " nanosegundos");
}

/**
* Calcula la tabla de prefijos para el algoritmo KMP
*
* @param patron Patrón para calcular la tabla de prefijos
* @return Array con los valores de la tabla de prefijos
*/
private int[] calcularTablaPrefijos(String patron) {
int[] b = new int[patron.length()];
b[0] = -1; // Inicia con -1 según tu requerimiento

int j = -1;
for (int i = 1; i < patron.length(); i++) {
while (j >= 0 && patron.charAt(j + 1) != patron.charAt(i)) {
j = b[j];
}
if (patron.charAt(j + 1) == patron.charAt(i)) {
j++;
}
b[i] = j;
}

// Ajuste para que LPS refleje el patrón esperado
for (int i = 0; i < b.length; i++) {
b[i] = (b[i] == -1) ? 0 : b[i] + 1;
}
b[0] = -1; // Reestablecer el primer valor como -1

return b;
}

// // Main para probar el algoritmo
// public static void main(String[] args) {
// KMP2 kmp = new KMP2();
// String texto = "ABABDABACDABABCABAB";
// String patron = "ABABC";

// kmp.searchKMP2(texto, patron);
// }
}