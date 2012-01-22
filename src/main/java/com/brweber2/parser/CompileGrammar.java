/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.parser;

import com.creativewidgetworks.goldparser.parser.GOLDParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;

public class CompileGrammar
{
    public static void main( String[] args ) throws IOException
    {
        File grammarFile = new File("/Users/bweber/brweber2/naive-java-unification/src/main/resources/naive_java_unification.egt");
        File sourceFile = new File( "/Users/bweber/brweber2/naive-java-unification/src/main/resources/grammar/delete_me.txt" );

        System.out.println(grammarFile.exists());
        System.out.println(sourceFile.exists());

        Reader sourceReader = new FileReader( sourceFile );
        GOLDParser parser = new GOLDParser();
        if ( !parser.setup( grammarFile ) )
        {
            throw new RuntimeException( "Unable to parse the grammar file " + grammarFile.getAbsolutePath() );
        }
        parser.loadRuleHandlers( "com.brweber2.parser.rulehandler" );
        parser.setGenerateTree( true );
        System.out.println("errors:" + parser.validateHandlersExist());
        if ( !parser.parseSourceStatements( sourceReader ) )
        {
            throw new RuntimeException( "Unable to parse the source file " + sourceFile.getAbsolutePath() );
        }
        parser.getCurrentReduction().execute();
        System.out.println(parser.getParseTree());
    }

    public GOLDParser parser()
    {
        try
        {
            return parser( new File( this.getClass().getClassLoader().getResource( "naive_java_unification.egt" ).toURI() ) );
        }
        catch ( URISyntaxException e )
        {
            throw new RuntimeException( "Unable to load the grammar file from the classpath.", e );
        }
    }
    
    public GOLDParser parser( File grammarFile )
    {
        try
        {
            GOLDParser parser = new GOLDParser();
            if ( !parser.setup( grammarFile ) )
            {
                throw new RuntimeException( "Unable to parse the grammar file " + grammarFile.getAbsolutePath() );
            }
            parser.loadRuleHandlers( "com.brweber2.parser.rulehandler" );
            parser.setGenerateTree( true );
            return parser;
        }
        catch ( IOException e )
        {
            throw new RuntimeException( "Unable to load grammar file " + grammarFile.getAbsolutePath(), e );
        }
    }
}
