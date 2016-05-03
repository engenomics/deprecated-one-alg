package org.engenomics.onesalg;

import javax.naming.OperationNotSupportedException;
import java.util.LinkedList;
import java.util.List;

public class Main {
    private final static int[] ones = {1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1};

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
