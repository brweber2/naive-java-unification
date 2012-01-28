# Naive Java Unification
A super naive unification library written in Java.  This is based on Chapters 1-4 from "Learn Prolog Now!".

## Syntax Differences with Prolog
* Atoms and complex terms can start with either an uppercase or lowercase letter.
* Variables are prefixed with '@' (i.e. @foo).

## In progress work (i.e. incomplete)
* Loading terms and rules from files is not functioning currently.  They must be loaded via the repl.

## Work that hasn't started yet
* There is no support for lists.
* There is no support for 'is'.

## Work that will likely not be supported
* There is not short circuiting of rules that will never terminate.
* There is no support for operators.
* There is no (and there is no plan to) support for input/output (Just use Java...).
* There is no (and there is no plan to) support for cut.

## How do I build it?

### To build via maven

#### First build my fork of goldengine parser

* cd *dir*
* git clone https://github.com/brweber2/goldengine.git
* cd goldengine
* mvn clean install

#### Then build naive java unification

* cd *dir*
* git clone https://github.com/brweber2/naive-java-unification.git
* cd naive-java-unification
* mvn clean install

## How do I run it?

### To run programmatically via Java

* Construct a knowledge base and use a ProofSearch to query it
* A tutorial will be added at a later date, see the unit tests for examples today.

### To run via the repl

* java -jar target/unification-1.0.jar

## What can I do in the REPL?

### mode().

* This toggles you back and forth between assert mode and query mode.
* Assert mode has '!-' as its prompt and query mode uses '?-' as its prompt.

### exit, quit or halt

* Exits the REPL

