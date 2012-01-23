package com.brweber2.term;

/**
 * @author brweber2
 * Copyright: 2012
 */
public class Atom implements Term {
    
    private final String atom;

    public Atom(String atom) {
        this.atom = atom;
    }

    public String prettyPrint() {
        return atom;
    }
    
    public String getRawString()
    {
        return atom;
    }

    @Override
    public String toString() {
        return "Atom{" +
                "atom='" + atom + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Atom atom1 = (Atom) o;

        if (!atom.equals(atom1.atom)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return atom.hashCode();
    }
}
