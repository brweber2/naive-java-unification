/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.parser.rulehandler;

import com.creativewidgetworks.goldparser.engine.Reduction;
import com.creativewidgetworks.goldparser.parser.ProcessRule;

@ProcessRule( rule={"<RuleBody> ::= <RuleAnd>"
        ,"<RuleBody> ::= <RuleOr>"
        ,"<RuleBody> ::= <Term>"} )

public class RuleBodyRuleHandler extends Reduction
{
}
