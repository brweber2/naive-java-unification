/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.parser.rulehandler;

import com.creativewidgetworks.goldparser.engine.ParserException;
import com.creativewidgetworks.goldparser.engine.Reduction;
import com.creativewidgetworks.goldparser.parser.GOLDParser;
import com.creativewidgetworks.goldparser.parser.ProcessRule;

@ProcessRule( rule={"<TermList> ::= <Term> ',' <TermList>"
        ,"<TermList> ::= <Term>"} )

public class TermListRuleHandler extends Reduction
{
    public TermListRuleHandler(GOLDParser parser)
    {
        Reduction reduction = parser.getCurrentReduction();
        if (reduction != null) {
            if (reduction.size() == 1) {
                setValue( reduction.get( 0 ).asReduction().getValue() );
            } else if ( reduction.size() == 3 ) {
                setValue( reduction.get( 0 ).asReduction().getValue() );
                // todo what to do here?
                setValue( reduction.get( 2 ).asReduction().getValue() );
            }else {
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
