/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.parser.rulehandler;

import com.brweber2.term.ComplexTerm;
import com.brweber2.term.Term;
import com.creativewidgetworks.goldparser.engine.ParserException;
import com.creativewidgetworks.goldparser.engine.Reduction;
import com.creativewidgetworks.goldparser.parser.GOLDParser;
import com.creativewidgetworks.goldparser.parser.ProcessRule;
import com.creativewidgetworks.goldparser.parser.Variable;

import java.util.ArrayList;
import java.util.List;

@ProcessRule( rule={"<ComplexTerm> ::= Id '(' ')'"
        ,"<ComplexTerm> ::= Id '(' <TermList> ')'"} )

public class ComplexTermRuleHandler extends Reduction
{

    public ComplexTermRuleHandler(GOLDParser parser)
    {
        Reduction reduction = parser.getCurrentReduction();
        if (reduction != null) {
            if (reduction.size() == 3) {
                setValue( new Variable( new ComplexTerm( reduction.get( 0 ).asString() ) ) );
            } else if (reduction.size() > 3 ) {
                int startAt = 2;
                int endAt = reduction.size() - 2; // -1 b/c zero indexed and -1 for ')'
                List<Term> terms = new ArrayList<Term>(  );
                for ( int i = startAt; i < endAt; i++ )
                {
                    terms.add( (Term) reduction.get( i ).asReduction().getValue() );
                }
                setValue( new Variable(  new ComplexTerm( reduction.get( 0 ).asString(), terms.toArray( new Term[terms.size()] ) ) ) );
            } else {
                parser.raiseParserException("wrong number of args");
            }
        } else {
            parser.raiseParserException("no reduction");
        }
    }

    @Override
    public void execute() throws ParserException
    {
        System.out.println("executing an atom!");
    }
}
