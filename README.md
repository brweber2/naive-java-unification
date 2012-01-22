# Naive Java Unification
A super naive unification library written in Java.  This is based on Chapters 1-4 from "Learn Prolog Now!".

## Syntax Differences with Prolog
* Atoms and complex terms can start with either an uppercase or lowercase letter.
* Variables are prefixed with '@' (i.e. @foo).

## In progress work (i.e. incomplete)
* The parser is incomplete.  The grammar is written and the rule handlers are in place, but the terms are not generated yet. Hopefully this will be fixed shortly.

## Work that hasn't started yet
* There is no support for lists.
* There is no support for 'is'.

## Work that will likely not be supported
* There is not short circuiting of rules that will never terminate.
* There is no (and there is no plan to) support for input/output (Just use Java...).
* There is no (and there is no plan to) support for cut.

