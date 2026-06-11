package exp;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;


public class TestMinus {
    public static void main(String[] args) throws Exception {

        String fileName = "prova.txt";
     
        CharStream chars = CharStreams.fromFileName(fileName);
        SimpleExpTwoLexer lexer = new SimpleExpTwoLexer(chars);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SimpleExpTwoParser parser = new SimpleExpTwoParser(tokens);
        
        ParseTree prog = parser.prog();

        System.out.println("You had: "+lexer.lexicalErrors+ " lexical errors and "+
                           parser.getNumberOfSyntaxErrors()+" syntax errors.");

        if (lexer.lexicalErrors+parser.getNumberOfSyntaxErrors() > 0)
            System.exit(1);

        System.out.println("Calculating expression");

        SimpleCalcSTVisitorMinus visitor = new SimpleCalcSTVisitorMinus();
        System.out.println("The result is: " + visitor.visit(prog));
    }
}



