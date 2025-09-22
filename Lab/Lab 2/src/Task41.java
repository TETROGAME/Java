//41. Характеристикой строки целочисленной матрицы назовем сумму ее положительных четных элементов.
//Переставляя строки заданной матрицы, расположить их в соответствии с ростом характеристик.

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class Task41 {
    static class RowCharacteristic {
        int[] row;
        int index;
        int sum;

        RowCharacteristic(int[] row, int index, int sum) {
            this.row = row;
            this.index = index;
            this.sum = sum;
        }
    }

    int[][] solution(int[][] matrix){
        ArrayList<RowCharacteristic> rows = new ArrayList<>();
        for(int i = 0; i < matrix.length; i++){
            int sum = 0;
            for(int col : matrix[i]){
                if(col > 0 && col % 2 == 0) sum += col;
            }
            rows.add(new RowCharacteristic(matrix[i], i, sum));

        }
        rows.sort(Comparator.comparingInt(r -> r.sum));

        int[][] result = new int[matrix.length][matrix[0].length];
        for(int i = 0; i < result.length; i++){
            result[i] = rows.get(i).row;
        }
        return result;
    }
}
