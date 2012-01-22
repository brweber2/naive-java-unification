/*
 * Copyright (C) 2011 ARIN
 */
package com.brweber2.parser.rulehandler;

import com.creativewidgetworks.goldparser.engine.ParserException;
import com.creativewidgetworks.goldparser.engine.Reduction;
import com.creativewidgetworks.goldparser.parser.ProcessRule;

@ProcessRule(rule="<Statements> ::= Atom | ")

public class StatementsRuleHandler extends Reduction
{
    @Override
    public void execute() throws ParserException
    {
        System.out.println("executing an atom!");
    }
}
