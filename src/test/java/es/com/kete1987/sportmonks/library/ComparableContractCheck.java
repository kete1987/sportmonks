package es.com.kete1987.sportmonks.library;

import java.util.ArrayList;
import java.util.List;

/**
 * Checks the {@link Comparable} contract (reflexive, antisymmetric, transitive) and its consistency
 * with {@code equals} over every pair and triple of a list. Returns the violations found, so a failing
 * test says exactly which elements break the contract.
 */
final class ComparableContractCheck {
    private static final int MAX_REPORTED = 5;

    private ComparableContractCheck() {
    }

    static <T extends Comparable<? super T>> List<String> violations(List<T> items) {
        List<String> found = new ArrayList<>();
        for (int i = 0; i < items.size() && found.size() < MAX_REPORTED; i++) {
            if (items.get(i).compareTo(items.get(i)) != 0) {
                found.add("not reflexive at " + i);
            }
            for (int j = 0; j < items.size(); j++) {
                pair(items, i, j, found);
            }
        }
        return found;
    }

    private static <T extends Comparable<? super T>> void pair(List<T> items, int i, int j, List<String> found) {
        T a = items.get(i);
        T b = items.get(j);
        int ab = Integer.signum(a.compareTo(b));
        if (ab != -Integer.signum(b.compareTo(a))) {
            found.add("not antisymmetric between " + i + " and " + j);
        }
        if (a.equals(b) && ab != 0) {
            found.add("equal elements " + i + " and " + j + " do not compare as 0");
        }
        if (ab <= 0) {
            for (int k = 0; k < items.size(); k++) {
                if (b.compareTo(items.get(k)) <= 0 && a.compareTo(items.get(k)) > 0) {
                    found.add("not transitive: " + i + " <= " + j + " <= " + k + " but " + i + " > " + k);
                }
            }
        }
    }
}
