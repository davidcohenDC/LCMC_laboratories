package exp;

import exp.SimpleExpParser.*;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Classe visitor per valutare espressioni aritmetiche semplici.
 * Estende la classe base generata da ANTLR e implementa metodi per
 * calcolare il risultato delle espressioni parsate.
 * Include anche la logica per l'indentazione, utile per stampare l'albero
 * sintattico con una formattazione leggibile durante la traversata.
 */
public class SimpleCalcSTVisitor extends SimpleExpBaseVisitor<Integer> {

	// Variabile per gestire l'indentazione nelle stampe
	String indent;

	@Override
	public Integer visit(ParseTree tree) {
		// Aggiorna l'indentazione per la stampa dell'albero sintattico
		String temp = indent;
		indent = (indent == null) ? "" : indent + "  ";
		int result = super.visit(tree);
		indent = temp;
		return result;
	}

	@Override
	public Integer visitProg(ProgContext ctx) {
		System.out.println(indent + "prog");
		// Visita l'espressione contenuta nel programma
		return visit(ctx.exp());
	}

	@Override
	public Integer visitExpProd1(ExpProd1Context ctx) {
		System.out.println(indent + "exp: prod1 with TIMES");
		// Calcola il prodotto delle due espressioni figlie
		return visit(ctx.exp(0)) * visit(ctx.exp(1));
	}

	@Override
	public Integer visitExpProd2(ExpProd2Context ctx) {
		System.out.println(indent + "exp: prod2 with PLUS");
		// Calcola la somma delle due espressioni figlie
		return visit(ctx.exp(0)) + visit(ctx.exp(1));
	}

	@Override
	public Integer visitExpProd3(ExpProd3Context ctx) {
		System.out.println(indent + "exp: prod3 with LPAR RPAR");
		// Le parentesi non alterano il risultato, restituisce il valore dell'espressione interna
		return visit(ctx.exp());
	}

	@Override
	public Integer visitExpProd4(ExpProd4Context ctx) {
		Integer res = Integer.parseInt(ctx.NUM().getText());
		System.out.println(indent + "exp: prod4 with NUM " + res);
		// Restituisce il valore numerico del token NUM
		return res;
	}
}
