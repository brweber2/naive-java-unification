/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.parser.rulehandler;

import com.creativewidgetworks.goldparser.engine.ParserException;
import com.creativewidgetworks.goldparser.engine.Reduction;
import com.creativewidgetworks.goldparser.parser.GOLDParser;
import com.creativewidgetworks.goldparser.parser.ProcessRule;

@ProcessRule(rule={"<Term> ::= <Atom>"
    ,"<Term> ::= NumberLiteral"
    ,"<Term> ::= <Variable>"
    ,"<Term> ::= <ComplexTerm>"})

public class TermRuleHandler extends Reduction
{
    private Reduction term;
    
    public TermRuleHandler(GOLDParser parser)
    {
        Reduction reduction = parser.getCurrentReduction();
        if (reduction != null) {
            if (reduction.size() == 1) {
                term = reduction.get( 0 ).asReduction();
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
//        System.out.println("executing a term!");
        term.execute();
//        System.out.println("term value: " + term.getValue());
        setValue( term.getValue() );
    }
}
