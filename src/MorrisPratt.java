import java.util.Scanner;

public class MorrisPratt {
    private static Scanner dato;

    /* Maria P�rez
     * Algoritmo de Morris Pratt
     * @param x Patron
     * @param m Largo de la cadena x
     * @param y Cadena de caracteres
     * @param n Largo de la cadena y
     */
    public static void morrisPratt(char[] x, int m, char[] y, int n){
        int i=0,j=0;
        int mpNext[] = new int[x.length+1];
        /*Preproceso*/
        preprocessMorrisPlatt(x,m,mpNext);
        while(j<n){
            while (i>-1 && x[i] !=y[j]){
                i= mpNext[i];
            }
            i++;
            j++;
            if(i>=m){
                System.out.println(j-i);
                i=mpNext[i];
            }
        }
    }

    //public void morrisPratFailFun(){
    /* @param x
     * @param m
     * @param mpNext
     */
    private static void preprocessMorrisPlatt(char[] x, int m, int[]
            mpNext){
        int i=0,j;
        j = mpNext [0] = -1;
        while(i<m){
            while(j>-1 && x[i] !=x[j]){
                j= mpNext[j];
            }
            mpNext[++i]=++j;
        }}


    public static void main( String [] alx)
    {
        dato = new Scanner(System.in);
        //calculo del coste computacional
        long ti = System.nanoTime();
        System.out.print("Longitud del patron : ");
        int patr = dato.nextInt();
        System.out.print("Escriba el Patron : ");
        String patron=dato.next();
        if(patr==patron.length()){
            char [] b = patron.toCharArray();
            System.out.print("Escriba el Texto : ");
            String texto=dato.next();
            char [] a = texto.toCharArray();
            if (patron.length()<texto.length()) {

                int a1, b1;
                a1=a.length;
                b1=b.length;
                morrisPratt(a, a1,  b, b1);
                long tf= System.nanoTime();
                long Ttol= tf-ti;
                System.out.print("el tiempo total es : "+Ttol/1000000000+ "  nns");
            }
        }else {
            System.out.print("Tamano incorrecto de patron");
        }
    }
}

//Encontrar la posicion del patron en la cadema,
// Decir cuantas veces aparece el patron en la cadena
// Hacer una tabla comparativa de las tres tecnicas: Naive, KMP y BM
// Ejemplo de prueba
// longitud del patron: 6
//Patr�n: abacab
//Texto: abacaabaccabacabaabb
