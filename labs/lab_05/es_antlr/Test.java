package exp;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Classe principale per testare il lexer e il parser generati da ANTLR.
 * Legge un'espressione aritmetica da un file e ne calcola il risultato
 * utilizzando la classe SimpleCalcSTVisitor.
 */
public class Test {
    public static void main(String[] args) throws Exception {

        String fileName = "prova.txt";

        // Legge il contenuto del file di input in un flusso di caratteri
        CharStream chars = CharStreams.fromFileName(fileName);

        // Crea un lexer che analizza il flusso di caratteri
        SimpleExpLexer lexer = new SimpleExpLexer(chars);

        // Crea un buffer di token estratti dal lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Crea un parser che analizza il buffer di token
        SimpleExpParser parser = new SimpleExpParser(tokens);

        // Inizia il parsing dalla regola 'prog'
        ParseTree prog = parser.prog();

        // Segnala errori lessicali e sintattici
        System.out.println("You had: " + lexer.lexicalErrors + " lexical errors and " +
                parser.getNumberOfSyntaxErrors() + " syntax errors.");

        // Se ci sono errori, termina il programma
        if (lexer.lexicalErrors + parser.getNumberOfSyntaxErrors() > 0)
            System.exit(1);

        System.out.println("Calculating expression");

        // Crea un visitor e calcola il risultato
        SimpleCalcSTVisitor visitor = new SimpleCalcSTVisitor();
        System.out.println("The result is: " + visitor.visit(prog));
    }
}
