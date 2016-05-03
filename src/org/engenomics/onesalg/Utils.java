package org.engenomics.onesalg;

import com.google.common.collect.Sets;

import java.util.*;

public class Utils {
    public static String getSuffix(int n) {
        switch (n % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    public static int[] subtract(int[] list, Rule rule) throws RuleNotSubsetOfListException {
        // Get int[] from rule
        int[] ruleList = new int[list.length];

        for (int i = 0; i < list.length; i++) {
            if (rule.contains(i)) {
                ruleList[i] = 1;
            } else {
                ruleList[i] = 0;
            }
        }


        // Subtract the ruleList from the list

        int[] result = new int[list.length];

        for (int i = 0; i < list.length; i++) {
            int value = list[i] - ruleList[i];
            if (!(value == 0 || value == 1)) {
                throw new RuleNotSubsetOfListException(rule + "(" + Arrays.toString(ruleList) + ") is not a subset of " + Arrays.toString(list) + ".");
            }
            result[i] = value;
        }

        return result;
    }

    private static List<Integer> getPositionsOfOnes(int[] list) {
        List<Integer> positionsOfOnes = new LinkedList<>();

        for (int i = 0; i < list.length; i++) {
            if (list[i] == 1) {
                positionsOfOnes.add(i);
            }
        }

        return positionsOfOnes;
    }

    // Dumb, recursive solution
    public static List<Rule> getOptimalRules(int[] list) throws RuleNotSubsetOfListException {
        if (isDescribableByOneRule(list)) {
            List<Rule> ruleList = new LinkedList<>();
            ruleList.add(listToRule(list));
            return ruleList;
        }

        // Get all possible rules that describe the list
        List<Rule> rules = Utils.getRules(list);

//        System.out.println();
//        System.out.println("Rules that describe part of " + Arrays.toString(list) + " are:");
//        rules.forEach(System.out::println);
//        System.out.println();

        List<List<Rule>> possibleRuleResults = new LinkedList<>();

        // For every possible first rule
        for (Rule firstRule : rules) {
            // Get the remaining list to describe
            int[] remainingList = Utils.subtract(list, firstRule);

            List<Rule> optimalRuleToDescribeCurrentList = getOptimalRules(remainingList);

            // Add the current rule itself
            optimalRuleToDescribeCurrentList.add(firstRule);

            possibleRuleResults.add(optimalRuleToDescribeCurrentList);
        }



        List<Rule> optimalRuleSet = Utils.getOptimalRuleset(possibleRuleResults);

        return optimalRuleSet;
    }

    private static boolean isDescribableByOneRule(int[] list) {
        List<Integer> positionsOfOnes = getPositionsOfOnes(list);

        // Trivial cases: no ones, one one, two ones
        if (positionsOfOnes.size() == 0 || positionsOfOnes.size() == 1 || positionsOfOnes.size() == 2) {
            return true;
        }


        // Complex cases: three or more ones
        int difference = positionsOfOnes.get(1) - positionsOfOnes.get(0);

        for (int i = 1; i < positionsOfOnes.size() - 1; i++) {
            int currentDifference = positionsOfOnes.get(i + 1) - positionsOfOnes.get(i);

            if (currentDifference != difference) {
                return false;
            }
        }

        return true;
    }

    /**
     * Assuming that it is possible to use a rule to describe this list, gives a rule for the given list
     *
     * @return the rule that should describe this list
     */
    private static Rule listToRule(int[] list) {
        List<Integer> onesPositions = getPositionsOfOnes(list);

        // Special cases
        if (onesPositions.size() == 0) {
            return new Rule(0, 0, 1);
        } else if (onesPositions.size() == 1) {
            return new Rule(onesPositions.get(0), onesPositions.get(0), 1);
        }


        // General case
        int step = onesPositions.get(1) - onesPositions.get(0);
        return new Rule(onesPositions.get(0), onesPositions.get(onesPositions.size() - 1), step);
    }

    public static List<Rule> getRules(int[] list) {
        List<Rule> rules = new LinkedList<>();

        Set<Integer> listSet = new HashSet<>(getPositionsOfOnes(list));

        for (Set<Integer> onesPositionsSet : Sets.powerSet(listSet)) {
            if (onesPositionsSet.size() == 0) {
                continue;
            }

            List<Integer> onesPositions = new LinkedList<>();
            onesPositions.addAll(onesPositionsSet);

            int[] listUsingCurrentOnesPositions = getListFromOnesPosition(onesPositions, list.length);

            if (isDescribableByOneRule(listUsingCurrentOnesPositions)) {
                rules.add(listToRule(listUsingCurrentOnesPositions));
            }
        }

        Collections.sort(rules);

        return rules;
    }

    private static int[] getListFromOnesPosition(List<Integer> onesList, int length) {
        int[] list = new int[length];

        for (int i = 0; i < list.length; i++) {
            list[i] = onesList.contains(i) ? 1 : 0;
        }

        return list;
    }

    public static List<Rule> getOptimalRuleset(List<List<Rule>> rulesets) {
        int minSize = rulesets.get(0).size();
        int indexOfMin = 0;

        for (int i = 0; i < rulesets.size(); i++) {
            if (rulesets.get(i).size() < minSize) {
                minSize = rulesets.get(i).size();
                indexOfMin = i;
            }
        }

        return rulesets.get(indexOfMin);
    }
}
