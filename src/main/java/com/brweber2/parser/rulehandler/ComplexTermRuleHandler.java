/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.parser.rulehandler;

import com.creativewidgetworks.goldparser.engine.ParserException;
import com.creativewidgetworks.goldparser.engine.Reduction;
import com.creativewidgetworks.goldparser.parser.ProcessRule;

@ProcessRule( rule={"<ComplexTerm> ::= Id '(' ')'"
        ,"<ComplexTerm> ::= Id '(' <TermList> ')'"} )

public class ComplexTermRuleHandler extends Reduction
{
    @Override
    public void execute() throws ParserException
    {
        System.out.println("executing an atom!");
    }
}
