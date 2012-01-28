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
#### First build goldengine parser
* cd <code>
* git clone https://github.com/brweber2/goldengine.git
* cd goldengine
* mvn clean install
* cd ..
#### Then build naive java unification
* git clone https://github.com/brweber2/naive-java-unification.git
* cd naive-java-unification
* mvn clean install

## How do I run it?
### To run programmatically via Java
* Construct a knowledge base and use a ProofSearch to query it
### To run via the repl
* java -jar target/unification-1.0.jar

