/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.parser.rulehandler;

import com.creativewidgetworks.goldparser.engine.ParserException;
import com.creativewidgetworks.goldparser.engine.Reduction;
import com.creativewidgetworks.goldparser.parser.GOLDParser;
import com.creativewidgetworks.goldparser.parser.ProcessRule;
import com.creativewidgetworks.goldparser.parser.Variable;

@ProcessRule( rule="<Variable> ::= '@' Id" )

public class VariableRuleHandler extends Reduction
{
    public VariableRuleHandler(GOLDParser parser)
    {
        Reduction reduction = parser.getCurrentReduction();
        if (reduction != null) {
            if (reduction.size() == 2) {
                setValue( new Variable( new Variable( reduction.get( 1 ).asString() ) ) );
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
//        System.out.println("executing an atom!");
    }
}
