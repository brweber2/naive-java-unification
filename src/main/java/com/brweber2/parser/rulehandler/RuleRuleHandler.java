/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.parser.rulehandler;

import com.brweber2.term.Term;
import com.brweber2.term.rule.Rule;
import com.brweber2.term.rule.RuleBody;
import com.creativewidgetworks.goldparser.engine.ParserException;
import com.creativewidgetworks.goldparser.engine.Reduction;
import com.creativewidgetworks.goldparser.parser.GOLDParser;
import com.creativewidgetworks.goldparser.parser.ProcessRule;
import com.creativewidgetworks.goldparser.parser.Variable;

@ProcessRule( rule="<Rule> ::= <RuleHead> ':-' <RuleBody>" )

public class RuleRuleHandler extends Reduction
{
    private Reduction head;
    private Reduction body;
    
    public RuleRuleHandler(GOLDParser parser)
    {
        Reduction reduction = parser.getCurrentReduction();
        if (reduction != null) {
            if (reduction.size() == 3) {
                head = reduction.get( 0 ).asReduction();
                body = reduction.get( 2 ).asReduction();
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
        System.out.println("executing a rule!");
        head.execute();
        body.execute();
        Object headValue = head.getValue().asObject();
        Object bodyValue = body.getValue().asObject();
        setValue( new Variable( new Rule( (Term) headValue, ( RuleBody ) bodyValue ) ) );
    }
}
