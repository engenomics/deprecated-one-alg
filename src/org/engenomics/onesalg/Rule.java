package org.engenomics.onesalg;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public final class Rule implements Comparable<Rule> {
    private final int start;
    private final int end;
    private final int step;
    private final boolean positive;
    private final List<Rule> basicRulesContained;

    public Rule(int start, int end, int step) {
        check(start, end, step);

        this.start = start;
        this.end = end;
        this.step = step;
        this.basicRulesContained = new LinkedList<>();
        this.positive = true;
    }

    public Rule(int start, int end, int step, List<Rule> basicRulesContained) {
        check(start, end, step);

        this.start = start;
        this.end = end;
        this.step = step;
        this.basicRulesContained = basicRulesContained;
        this.positive = true;
    }

    public Rule(int start, int end, int step, List<Rule> basicRulesContained, boolean positive) {
        check(start, end, step);

        this.start = start;
        this.end = end;
        this.step = step;
        this.basicRulesContained = basicRulesContained;
        this.positive = positive;
    }

    @Contract(pure = true)
    @Override
    public String toString() {
        return (positive ? "" : "!") + "Every " + step + "" + Utils.getSuffix(step) + ", [" + start + " to " + end + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rule rule = (Rule) o;

        if (start != rule.start) return false;
        if (end != rule.end) return false;
        if (step != rule.step) return false;
        return positive == rule.positive;

    }

    @Override
    public int hashCode() {
        int result = start;
        result = 31 * result + end;
        result = 31 * result + step;
        result = 31 * result + (positive ? 1 : 0);
        return result;
    }

    public boolean contains(int n) {
        return (n - start) % step == 0 && n <= end && n >= start;
    }

    public boolean isPositive() {
        return positive;

    }

    public List<Rule> getBasicRulesContained() {
        return basicRulesContained;
    }

    /**
     * Makes sure that the start, end, and step parameters make sense in relation to each other.
     *
     * @param start - the start parameter
     * @param end   - the end parameter
     * @param step  - the step parameter
     * @throws IllegalArgumentException - if repeatedly adding the step to the start does not eventually reach the end
     */
    private void check(int start, int end, int step) {
        if ((end - start) % step != 0) {
            throw new IllegalArgumentException("The step does does not match the start and end parameters.");
        }
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getStep() {
        return step;
    }

    /**
     * Gets the number of elements covered by the rule.
     * @return - the number of elements covered by the rule
     */
    private int getCovered() {
        return (this.end - this.start) / this.step;
    }

    @Override
    public int compareTo(@NotNull Rule o) {
        // Sorts by elements covered, in a descending fashion
        if (o.getCovered() != this.getCovered()) {
            return o.getCovered() - this.getCovered();
        }
        return this.getStart() - o.getStart();
    }
}
