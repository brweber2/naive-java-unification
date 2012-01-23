/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.parser.rulehandler;

import com.creativewidgetworks.goldparser.engine.Reduction;
import com.creativewidgetworks.goldparser.parser.GOLDParser;
import com.creativewidgetworks.goldparser.parser.ProcessRule;

@ProcessRule( rule={"<RuleBody> ::= <RuleAnd>"
        ,"<RuleBody> ::= <RuleOr>"
        ,"<RuleBody> ::= <Term>"} )

public class RuleBodyRuleHandler extends Reduction
{
    public RuleBodyRuleHandler(GOLDParser parser)
    {
        Reduction reduction = parser.getCurrentReduction();
        if (reduction != null) {
            if (reduction.size() == 1) {
                setValue( reduction.get( 0 ).asReduction().getValue() );
            } else {
                parser.raiseParserException("wrong number of args");
            }
        } else {
            parser.raiseParserException("no reduction");
        }
    }
}
