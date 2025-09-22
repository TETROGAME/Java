//13. Найти максимальное из чисел, встречающихся в заданной матрице более одного раза.

import java.util.HashMap;
import java.util.Map;
public class Task13 {
    int solution(int[][] matrix) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int[] row : matrix) {
            for (int elem : row) {
                map.put(elem, map.getOrDefault(elem, 0) + 1);
            }
        }

        int max = Integer.MIN_VALUE;
        boolean found = false;

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > 1) {
                if (entry.getKey() > max) {
                    max = entry.getKey();
                    found = true;
                }
            }
        }

        return found ? max : -1;
    }
}