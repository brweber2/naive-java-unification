package com.brweber2.unification;

import com.brweber2.term.Term;
import com.brweber2.term.Variable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class UnificationScope {
    private UnificationScope parent;
    private Map<Variable,Term> scope = new LinkedHashMap<Variable, Term>();

    public UnificationScope() {
    }

    public UnificationScope( UnificationScope parentScope )
    {
        this.parent = parentScope;
    }

    public boolean set(Variable term1, Term value)
    {
        System.out.println("trying to set " + term1 + " to " + value);
        if ( has(term1) )
        {
            Term existingValue = get(term1);
            if ( existingValue instanceof Variable && value instanceof Variable)
            {
                Variable existingVariable = (Variable)existingValue;
                Variable valueVariable = (Variable)value;
                if ( has(existingVariable) && has(valueVariable))
                {
                    return resolve(existingVariable).equals(resolve(valueVariable));
                }
                else if ( has(existingVariable))
                {
                    return resolve(existingVariable).equals(value);
                }
                else if ( has(valueVariable))
                {
                    return resolve(valueVariable).equals(existingValue);
                }
                else
                {
                    // be sure to prevent a loop!!!
                }
            }   
            else if ( existingValue instanceof Variable )
            {
                Variable existingVariable = (Variable) existingValue;
                if ( has(existingVariable) )
                {
                    return value.equals(resolve(existingVariable));
                }
                else
                {
                    scope.put(existingVariable,value);
                }
            }
            else if ( value instanceof Variable )
            {
                Variable valueVariable = (Variable)value;
                if ( has(valueVariable) )
                {
                    return existingValue.equals(resolve(valueVariable));
                }
                else
                {
                    if ( existingValue == null )
                    {
                        scope.put(valueVariable,term1);
                    }
                    else
                    {
                        scope.put(valueVariable, existingValue);
                    }
                }
            }
            else
            {
                if ( existingValue == null )
                {
                    scope.put(term1,value);
                }
                else
                {
                    return existingValue.equals(value);
                }
            }
            return true;
        }
        else
        {
            if ( term1.equals(value) )
            {
                scope.put(term1,null); // unbound still
            }
            else
            {
                scope.put(term1,value);
            }
            return true;
        }
    }
    
    public Set<Variable> keys()
    {
        Set<Variable> s = scope.keySet();
        if ( parent != null )
        {
            s.addAll(parent.keys());
        }
        return s;
    }
    
    public boolean has(Variable variable )
    {
        return scope.containsKey(variable) || (parent != null && parent.has(variable));
    }

    public Term get(Variable variable)
    {
        return resolve(variable);
    }
    
    public Term resolve(Variable variable)
    {
        if ( !scope.containsKey(variable) )
        {
            if ( parent == null )
            {
                throw new RuntimeException("No such variable " + variable + " found in scope.");
            }
            return parent.resolve(variable);
        }
        System.out.println("trying to resolve " + variable + " in " + scope);
        Term term = scope.get(variable);
        if ( term instanceof Variable )
        {
            Variable termVariable = (Variable) term;
            return resolve(termVariable);
        }
        return term;
    }

    public boolean isEmpty()
    {
        return scope.isEmpty() && (parent == null || parent.isEmpty());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnificationScope that = (UnificationScope) o;

        if (parent != null ? !parent.equals(that.parent) : that.parent != null) return false;
        if (!scope.equals(that.scope)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = parent != null ? parent.hashCode() : 0;
        result = 31 * result + scope.hashCode();
        return result;
    }
}
