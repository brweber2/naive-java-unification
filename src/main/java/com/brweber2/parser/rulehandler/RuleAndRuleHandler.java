/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.parser.rulehandler;

import com.brweber2.term.rule.RuleAnd;
import com.brweber2.term.rule.RuleBody;
import com.creativewidgetworks.goldparser.engine.ParserException;
import com.creativewidgetworks.goldparser.engine.Reduction;
import com.creativewidgetworks.goldparser.parser.GOLDParser;
import com.creativewidgetworks.goldparser.parser.ProcessRule;
import com.creativewidgetworks.goldparser.parser.Variable;

@ProcessRule( rule = "<RuleAnd> ::= <Term> ',' <RuleBody>" )

public class RuleAndRuleHandler extends Reduction
{
    public RuleAndRuleHandler(GOLDParser parser)
    {
        Reduction reduction = parser.getCurrentReduction();
        if (reduction != null) {
            if (reduction.size() == 3) {
                setValue( new Variable( new RuleAnd( (RuleBody)reduction.get( 0 ).asReduction().getValue(), (RuleBody) reduction.get( 2 ).asReduction().getValue() ) ) );
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
//        System.out.println( "executing an atom!" );
    }
}
