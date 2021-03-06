/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.parser.rulehandler;

import com.creativewidgetworks.goldparser.engine.ParserException;
import com.creativewidgetworks.goldparser.engine.Reduction;
import com.creativewidgetworks.goldparser.parser.GOLDParser;
import com.creativewidgetworks.goldparser.parser.ProcessRule;

@ProcessRule(rule={"<Statement> ::= <Term> .",
    "<Statement> ::= <Rule> ."})

public class StatementRuleHandler extends Reduction
{
    private Reduction ruleOrTerm;
    
    public StatementRuleHandler(GOLDParser parser)
    {
        Reduction reduction = parser.getCurrentReduction();
        if (reduction != null) {
            if (reduction.size() == 2) {
                ruleOrTerm = reduction.get( 0 ).asReduction();
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
//        System.out.println("executing a statement!");
        ruleOrTerm.execute();
//        System.out.println("statement value: " + ruleOrTerm.getValue());
        setValue( ruleOrTerm.getValue() );
    }
}
