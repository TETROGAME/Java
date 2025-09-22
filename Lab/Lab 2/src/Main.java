import java.io.PrintStream;
import java.util.Scanner;
public class Main {
    static Scanner in = new Scanner(System.in);
    static PrintStream out = System.out;
    public static void printMatrix(int[][] matrix){
        for (int[] row : matrix){
            for (int col : row){
                out.print(col + " ");
            }
            out.println();
        }
    }
    public static int[][] getMatrix(){
        out.print("Введите n и k: ");
        int n, k;
        n = in.nextInt();
        k = in.nextInt();
        int[][] matrix = new int[n][k];
        out.println("1 - Автоматическое заполнение");
        out.println("2 - Ручное заполнение");
        out.print(": ");
        switch (in.nextInt()){
            case 1:
                for (int i = 0; i < n; i++){
                    for (int j = 0; j < k; j++){
                        matrix[i][j] = (int)(Math.random() * 10);
                    }
                }
                break;
            case 2:
                for (int i = 0; i < n; i++){
                    for (int j = 0; j < k; j++){
                        matrix[i][j] = in.nextInt();
                    }
                }
                break;
        }
        out.println("Матрица:");
        printMatrix(matrix);
        return matrix;
    }
    public static void main(String[] args) {
        out.print("Введите задание для решения (13, 27, 41): ");
        int answer = in.nextInt();
        int[][] matrix;
        switch (answer) {
            case 13:
                Task13 task13 = new Task13();
                matrix = getMatrix();
                out.print("Максимальный из элементов, встречающийся больше 1 раза: ");
                out.println(task13.solution(matrix));
                break;

            case 27:
                Task27 task27 = new Task27();
                matrix = getMatrix();
                out.print("Количество строк в максимальном множестве попарно непохожих строк матрицы: ");
                out.println(task27.solution(matrix));
                break;

            case 41:
                Task41 task41 = new Task41();
                matrix = getMatrix();
                int[][] result = task41.solution(matrix);
                out.println("Итоговая матрица:");
                printMatrix(result);
                break;
        }

    }
}