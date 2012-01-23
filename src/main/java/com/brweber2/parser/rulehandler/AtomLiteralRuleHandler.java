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

@ProcessRule(rule="AtomLiteral = ''   {AtomContent}* ''")

public class AtomLiteralRuleHandler extends Reduction
{
    public AtomLiteralRuleHandler(GOLDParser parser)
    {
        Reduction reduction = parser.getCurrentReduction();
        if (reduction != null) {
            if (reduction.size() == 1) {
                String s = reduction.get( 0 ).asString();
                // remove quotes...
                s = s.substring( 0, s.length() -1 );
                s = s.substring( 1, s.length() );
                setValue( new Variable( new Atom( s ) ) );
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
