package com.brweber2.term;

/**
 * @author brweber2
 * Copyright: 2012
 */
public class Numeric implements Term {
    private final String number;

    public Numeric(String number) {
        this.number = number;
    }

    @Override
    public String prettyPrint() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Numeric numeric = (Numeric) o;

        if (!number.equals(numeric.number)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    @Override
    public String toString() {
        return "Numeric{" +
                "number='" + number + '\'' +
                '}';
    }
}
