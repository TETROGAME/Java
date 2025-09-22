//8. Из текста удалить все слова заданной длины, начинающиеся на согласную букву.
//Тест:
//Input text:
//Арбуз, стол, яблоко, груша, дом, река, fish, apple, sky, bread, milk, yellow, green, blue, school
//Input target length k: 4
//Арбуз яблоко груша дом apple sky bread yellow green school
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static final String consonants = "бвгджзклмнпрстфхцчшщBCDFGHJKLMNPQRSTVWXYZbcdfghjklmnpqrstvwxyz";
    static Scanner in = new Scanner(System.in);
    static PrintStream out = System.out;
    public static ArrayList<String> getText(){
        out.println("Input text: ");
        String input = in.nextLine();
        String[] temp = input.split("[,\\s]+");
        return new ArrayList<>(Arrays.asList(temp));
    }
    public static int getK(){
        out.print("Input target length k: ");
        return in.nextInt();
    }
    public static ArrayList<String> deleteWords(ArrayList<String> text){
        ArrayList<String> result = new ArrayList<>();
        int k = getK();
        for(String word : text){
            if(consonants.indexOf(word.charAt(0)) != -1){
                if(word.length() != k){
                    result.add(word);
                }
            }
            else{
                result.add(word);
            }
        }
        return result;
    }
    public static void printResult(ArrayList<String> result){
        for(String word : result){
            out.print(word.concat(" "));
        }
    }
    public static void main(String[] args) {
        ArrayList<String> words = getText();
        ArrayList<String> result = deleteWords(words);
        printResult(result);
    }
}