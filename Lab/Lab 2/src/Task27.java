//27. Две строки матрицы назовем похожими, если совпадают множества чисел,
//встречающихся в этих строках. Найти количество строк в максимальном множестве
//попарно непохожих строк заданной матрицы.

import java.util.HashSet;
public class Task27 {
    int solution(int[][] matrix){
        HashSet<HashSet<Integer>> vault = new HashSet<>();
        for (int[] ints : matrix) {
            HashSet<Integer> rowSet = new HashSet<>();
            for (int col : ints) {
                rowSet.add(col);
            }
            vault.add(rowSet);
        }
        return vault.size();
    }
}
