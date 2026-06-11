package exp;

import exp.SimpleExpTwoParser.*;
import org.antlr.v4.runtime.tree.ParseTree;

public class SimpleCalcSTVisitorMinus extends SimpleExpTwoBaseVisitor<Integer> {

	String indent;

	@Override
	public Integer visit(ParseTree tree) {
		String temp=indent;
		indent=(indent==null)?"":indent+"  ";
		int result = super.visit(tree);
		indent=temp;
		return result;
	}

	@Override
	public Integer visitProg(ProgContext ctx) {
		System.out.println(indent+"prog");
		return visit( ctx.exp() );
	}

	@Override
	public Integer visitExpProd1(ExpProd1Context ctx) {
		System.out.println(indent+"exp: prod1 with TIMES");
			return visit( ctx.exp(0) ) * visit( ctx.exp(1) );
	}

	@Override
	public Integer visitExpProd2(ExpProd2Context ctx) {
		System.out.println(indent+"exp: prod1 with MINUS");
		return visit( ctx.exp(0) ) - visit( ctx.exp(1) );
	}

	@Override
	public Integer visitExpProd3(ExpProd3Context ctx) {
		System.out.println(indent+"exp: prod3 with LPAR RPAR");
		return visit( ctx.exp() );
	}

	@Override
	public Integer visitExpProd4(ExpProd4Context ctx) {
		Integer res= Integer.parseInt(ctx.NUM().getText());
		System.out.println(indent+"exp: prod3 with NUM "+res);
		return res;
	}
}
