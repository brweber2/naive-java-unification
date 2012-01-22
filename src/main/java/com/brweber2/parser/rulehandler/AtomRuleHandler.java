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

@ProcessRule(rule="<Atom> ::= Id | {AtomLiteral}")

public class AtomRuleHandler extends Reduction
{
    public AtomRuleHandler(GOLDParser parser) {
        String atom = parser.getCurrentReduction().get(0).asString();

        // todo if the single quotes are there, remove them...
        
        setValue( new Variable( new Atom( atom ) ) );
    }

    @Override
    public void execute() throws ParserException
    {
        System.out.println("executing an atom!");
    }
}
