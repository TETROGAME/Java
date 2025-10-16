/*
8) Рациональная (несократимая) дробь представляется парой целых чисел (a,b),
где a - числитель, b - знаменатель. Создать класс Rational для работы с рациональными
дробями. Необходимо реализовать 4 арифметические операции: +-/*.
Класс должен реализовать:
-интерфейсы Comparable и Comparator с возможностью выбора одного из полей
для сравнения.
-интерфейс Iterable - индексатор по всем полям объекта.
-метод для сохранения значений всех полей в строке текста (переопределить toString())
-конструктор или метод для инициализации объекта из строки текста, соответствующий реализации метода toString()
Создать консольное приложение, демонстрирующее использование класса.
Создать небольшой массив объектов и напечатать отсортированными по выбранному полю.
*/

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner in = new Scanner(System.in);
    static PrintStream out = System.out;
    public static void main(String[] args) {
        ArrayList<Rational> list = new ArrayList<>();
        out.println("You'll be offered to input Rational fractions (pattern: numerator/denominator)");
        while(true){
            boolean exit = false;
            out.print("Input fraction (type q to exit): ");
            String line = in.nextLine();
            if (line.equals("q")) {
                out.println("Ending input sequence...");
                exit = true;
            } else {
                try {
                    Rational temp = Rational.parse(line);
                    if(temp != null){
                        list.add(temp);
                    }
                }
                catch(IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }
            }
            if(exit){ break; }
        }

        list.sort(new Rational());
        for(Rational r : list){
            out.println(r);
        }
    }
}