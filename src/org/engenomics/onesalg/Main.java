package org.engenomics.onesalg;

import javax.naming.OperationNotSupportedException;
import java.util.LinkedList;
import java.util.List;

public class Main {
    // private final static int[] ones = {1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1};
    private final static int[] ones = {1, 1, 0, 1, 1, 0, 1, 1, 0};

    public static void main(String[] args) throws OperationNotSupportedException, RuleNotSubsetOfListException {
        new Main().run();
    }

    private void run() throws OperationNotSupportedException, RuleNotSubsetOfListException {
        // Memoization method

        List<Rule> optimalRules = Utils.getOptimalRules(ones);

        System.out.println("Optimal rules:");
        optimalRules.forEach(System.out::println);
    }


}

/*
1110111011111

111110111011 - interesnoe razbienie
0, 2, 4, 6, 8, 10
3, 7, 11
1

010110111011110 - 0 2 5 9 14
4, 6, 8, 10, 12
1, 7, 13
3, 11

*/