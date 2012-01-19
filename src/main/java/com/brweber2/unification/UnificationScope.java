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
    private Map<Variable,Term> scope = new LinkedHashMap<Variable, Term>();

    public UnificationScope() {
    }

    private UnificationScope(Map<Variable, Term> scope) {
        this.scope = scope;
    }

    public UnificationScope merge( UnificationScope unificationScope )
    {
        Map<Variable,Term> map = new HashMap<Variable,Term>(scope);
        map.putAll(unificationScope.scope);
        return new UnificationScope(map);
    }

    public boolean set(Variable term1, Term value)
    {
        System.out.println("trying to set " + term1 + " to " + value);
        if ( scope.containsKey( term1 ) )
        {
            Term existingValue = get(term1);
            if ( existingValue instanceof Variable && value instanceof Variable)
            {
                Variable existingVariable = (Variable)existingValue;
                Variable valueVariable = (Variable)value;
                if ( scope.containsKey(existingVariable) && scope.containsKey(valueVariable))
                {
                    return resolve(existingVariable).equals(resolve(valueVariable));
                }
                else if ( scope.containsKey(existingVariable))
                {
                    return resolve(existingVariable).equals(value);
                }
                else if ( scope.containsKey(valueVariable))
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
                if ( scope.containsKey(existingVariable) )
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
                if ( scope.containsKey(valueVariable) )
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
        return scope.keySet();
    }
    
    public boolean has(Variable variable )
    {
        return scope.containsKey(variable);
    }

    public Term get(Variable variable)
    {
        return resolve(variable);
    }
    
    public Term resolve(Variable variable)
    {
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
        return scope.isEmpty();
    }
}
