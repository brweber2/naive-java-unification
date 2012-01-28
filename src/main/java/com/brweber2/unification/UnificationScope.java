package com.brweber2.unification;

import com.brweber2.term.Term;
import com.brweber2.term.Variable;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class UnificationScope {
    
    private static Logger log = Logger.getLogger(UnificationScope.class.getName());
    
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
        log.finer("trying to set " + term1 + " to " + value);
        if ( has(term1) )
        {
            Term existingValue = get(term1);
            if ( existingValue instanceof Variable && value instanceof Variable)
            {
                Variable existingVariable = (Variable)existingValue;
                Variable valueVariable = (Variable)value;
                if ( has(existingVariable) && has(valueVariable))
                {
                    return get( existingVariable ).equals(get( valueVariable ));
                }
                else if ( has(existingVariable))
                {
                    return get( existingVariable ).equals(value);
                }
                else if ( has(valueVariable))
                {
                    return get( valueVariable ).equals(existingValue);
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
                    return value.equals(get( existingVariable ));
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
                    return existingValue.equals(get( valueVariable ));
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
        Set<Variable> s = new HashSet<Variable>(scope.keySet());
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
    
    public Term grab(Variable variable)
    {
        return grabResolve( variable, this );
    }

    private Term grabResolve(Variable variable, UnificationScope startingScope)
    {
        if ( !scope.containsKey(variable) )
        {
            if ( parent == null )
            {
                return variable;
            }
            return parent.resolve( variable, startingScope );
        }
        log.finer("trying to resolve " + variable + " in " + scope);
        Term term = scope.get(variable);
        if ( term instanceof Variable )
        {
            Variable termVariable = (Variable) term;
            return startingScope.grab( termVariable );
        }
        return term;
    }

    public Term get(Variable variable)
    {
        return resolve(variable,this);
    }
    
    public Term resolve(Variable variable, UnificationScope startingScope)
    {
        if ( !scope.containsKey(variable) )
        {
            if ( parent == null )
            {
                throw new RuntimeException("No such variable " + variable + " found in scope.");
            }
            return parent.resolve( variable, startingScope );
        }
        log.finer("trying to resolve " + variable + " in " + scope);
        Term term = scope.get(variable);
        if ( term instanceof Variable )
        {
            Variable termVariable = (Variable) term;
            return startingScope.get( termVariable );
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

    @Override
    public String toString() {
        return "UnificationScope{" +
                "parent=" + parent +
                ", scope=" + scope +
                '}';
    }
}
