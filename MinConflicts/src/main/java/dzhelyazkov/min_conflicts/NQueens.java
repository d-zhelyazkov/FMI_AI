/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dzhelyazkov.min_conflicts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author grade
 */
public class NQueens {

    private static int N = 0;
    
    private static List<Integer> queenCols;
    
    private static Set<Integer> queensInConflict;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try(Scanner sc = new Scanner(System.in)){
            N = sc.nextInt();
        }
        
        queenCols = new Random().ints(N, 0, N).boxed().collect(Collectors.toList());
        queensInConflict = new HashSet<>(IntStream.range(0, N).boxed().collect(Collectors.toList()));
        
        Collection<Integer> queensInConflict = getQueensInConflict();
        while(!queensInConflict.isEmpty()) {
            
            int queen = getRandomElement(queensInConflict);
            
            int conflicts = N;
            Collection<QueenTest> tests = Collections.emptyList();
            for(int i = 0; i < N; i ++) {
                Collection<Integer> conflictedQueens = getQueensInConflict(queen, i);
                if(conflictedQueens.size() == conflicts) {
                    tests.add( new QueenTest(i, conflictedQueens));
                } else if(conflictedQueens.size() < conflicts) {
                    conflicts = conflictedQueens.size();
                    tests = new ArrayList<>(N);
                    tests.add(new QueenTest(i, conflictedQueens));
                }
            }
            
            QueenTest randomTest = getRandomElement(tests);
            queenCols.set(queen, randomTest.col);
            if(randomTest.queensInConflict.isEmpty()) {
                queensInConflict.remove(queen);
            } else {
                queensInConflict.addAll(randomTest.queensInConflict);
            }


            queensInConflict = getQueensInConflict();
        }
        
        printResult();
        
    }
    
    private static <T> T getRandomElement(Collection<T> collection) {
        return new ArrayList<>(collection).get(new Random().nextInt(collection.size()));
    }
    

    private static Collection<Integer> getQueensInConflict() {
        
        Set<Integer> conflictedQueens = new HashSet<>(queensInConflict.size());
        for(Integer queen : queensInConflict) {
//        for(int queen = 0; queen < N; queen ++) {
            if(conflictedQueens.contains(queen)) 
                continue;
            
            Collection<Integer> queensInConflict = getQueensInConflict(queen, queenCols.get(queen));
            if(queensInConflict.isEmpty()) {
                continue;
            }
            
            
            conflictedQueens.add(queen);
            conflictedQueens.addAll(queensInConflict);
        }
        
        queensInConflict = conflictedQueens;
        return conflictedQueens;
    }

    private static Collection<Integer> getQueensInConflict(Integer queen, int queenCol) {
        Collection<Integer> result = new ArrayList<>(N-1);
        for(int i = 0; i < N; i ++) {
            if(i == queen) 
                continue;
            
            int col = queenCols.get(i);
            if((queenCol == col)
//                    || (Math.abs(queenCol - col) == Math.abs(queen - i))) {
                    || (queen - queenCol) == (i - col)
                    || (queen + queenCol) == (i + col)) {
                result.add(i);
            }
        }
        
        return result;
    }

    
    static class QueenTest {
        int col;
        Collection<Integer> queensInConflict;

        QueenTest(int col, Collection<Integer> queensInConflict) {
            this.col = col;
            this.queensInConflict = queensInConflict;
        }
        
        
    }
    
    private static void printResult() {
        char[] line = new char[N];
        for (Integer col : queenCols) {
            Arrays.fill(line,0, col, '.');
            line[col] = '*';
            Arrays.fill(line,col + 1, N, '.');
            System.out.println(line);
        }
    }
}
