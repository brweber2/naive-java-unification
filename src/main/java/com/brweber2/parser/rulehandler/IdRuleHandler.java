/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.parser.rulehandler;

import com.brweber2.term.Atom;
import com.creativewidgetworks.goldparser.engine.ParserException;
import com.creativewidgetworks.goldparser.engine.Reduction;
import com.creativewidgetworks.goldparser.parser.GOLDParser;
import com.creativewidgetworks.goldparser.parser.ProcessRule;
import com.creativewidgetworks.goldparser.parser.Variable;

@ProcessRule(rule="Id            = {Letter}{AlphaNumeric}*")

public class IdRuleHandler extends Reduction
{
    private Reduction id;
    
        public IdRuleHandler(GOLDParser parser) {
            Reduction reduction = parser.getCurrentReduction();
            if (reduction != null) {
                if (reduction.size() == 1) {
                    id = reduction.get( 0 ).asReduction();
                } else {
                    parser.raiseParserException("boom");
                }
            } else {
                parser.raiseParserException("bang");
            }
        }

    @Override
    public void execute() throws ParserException
    {
        System.out.println("executing an id!");
        id.execute();
        setValue( new Variable( new Atom( id.getValue().asString() ) ) );
    }
}
