//Тимошенко Никита Михайлович 2 курс 5 группа
//Задание: посчитать приближение косинуса через ряд Тейлора
//для заданного x с точностью 10^(-k)
//Тесты:
//1.
//Введите x(double): 0
//Введите k(int): 124
//Результат встроенной функции: 1
//Результат разработанной функции: 1

//2.
//Введите x(double): 0.5
//java.util.InputMismatchException

//3.
//Введите x(double): 0,5
//Введите k(int): 10
//Результат встроенной функции: 0,878
//Результат разработанной функции: 0,878

//4.
//Введите x(double): -1251251
//Введите k(int): 10
//Результат встроенной функции: 0,198
//Результат разработанной функции: 0,198

import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.Scanner;
public class Main {
    public static double pow(double base, int exponent) {
        double result = 1;
        for (int i = 1; i <= Math.abs(exponent); i++) {
            result *= base;
        }
        if (exponent < 0) {
            return 1 / result;
        }
        return result;
    }
    public static double factorial(int n){
        if(n < 0){
            System.err.println("Expected positive integer");
            return -1;
        }
        double result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }
    public static double taylor(double x, int k){
        x = x % (2 * Math.PI); // Отбрасываем период 2 пи
        double precision = pow(10, -k);
        double member, result = 0;
        int n = 0;
        while(true){
            member = pow(-1, n) *
                    (pow(x, 2 * n) / factorial(n * 2));
            if(Math.abs(member) < precision){
                return result;
            }
            result += member;
            n++;
        }
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintStream out = System.out;
        double x;
        int k;
        try {
            out.print("Введите x(double): ");
            x = in.nextDouble();
            out.print("Введите k(int): ");
            k = in.nextInt();
        }
        catch(Exception error) {
            out.println(error);
            return;
        }
        //Форматирование вывода до 3 знаков после запятой
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMaximumFractionDigits(3);

        out.print("Результат встроенной функции: ");
        out.println(formatter.format(Math.cos(x)));
        out.print("Результат разработанной функции: ");
        out.print(formatter.format(taylor(x, k)));
    }
}