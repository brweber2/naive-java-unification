/*
 * Copyright (C) 2011 ARIN
 */
package com.brweber2.parser.rulehandler;

import com.creativewidgetworks.goldparser.engine.Reduction;
import com.creativewidgetworks.goldparser.parser.GOLDParser;
import com.creativewidgetworks.goldparser.parser.ProcessRule;
import com.creativewidgetworks.goldparser.parser.Variable;

@ProcessRule(rule="Id            = {Letter}{AlphaNumeric}*")

public class IdRuleHandler extends Reduction
{

        private GOLDParser theParser;
        private String variableName;

        public IdRuleHandler(GOLDParser parser) {
            theParser = parser;
            Reduction reduction = parser.getCurrentReduction();
            if (reduction != null) {
                if (reduction.size() == 1) {
                    variableName = reduction.get(0).asString();
                } else {
                    parser.raiseParserException("boom");
                }
            } else {
                parser.raiseParserException("bang");
            }
        }

        public String getVariableName() {
            return variableName;
        }

        @Override
        public Variable getValue() {
            Variable var = theParser.getProgramVariable(variableName);
            return var == null ? new Variable("") : var;
        }

        @Override
        public String toString() {
            return variableName + "=" + getValue();
        }


}
