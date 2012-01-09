package com.brweber2.unification;

import com.brweber2.kb.KnowledgeBase;
import com.brweber2.term.Term;
import com.brweber2.term.rule.Rule;

/**
 * @author brweber2
 * Copyright: 2012
 */
public interface Unify {
    UnificationResult unify( Term term1, Term term2);
//    UnificationResult unify( UnificationScope scope, Term term1, Term term2);
    UnificationResult unify(KnowledgeBase knowledgeBase, Term term1, Rule rule);
}
