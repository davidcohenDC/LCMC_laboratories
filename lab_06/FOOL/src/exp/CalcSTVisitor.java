package exp;

import org.antlr.v4.runtime.tree.*;
import exp.ExpParser.*;
/*
Sistemiamo CalcVisitor, quando si trova exp per cui è stata applicata expProd1 hanno matchato avrà tre figli; exp times exp oppure exp div exp.
Adesso dovremo fare un if

*/

public class CalcSTVisitor extends ExpBaseVisitor<Integer> {

	String indent;
	
    @Override
	public Integer visit(ParseTree x) {
        String temp=indent;
        indent=(indent==null)?"":indent+"  ";
        int result = super.visit(x);
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
		// chiamando visit(ctx.exp(0)) e visit(ctx.exp(1)) si visita il sotto albero di ctx.exp(0) e ctx.exp(1) e si calcolano i valori
		boolean times= ctx.TIMES( ) != null;
		System.out.println(indent+"exp: prod1 with "+(times?"TIMES":"DIV"));
		if (times)
			return visit( ctx.exp(0) ) * visit( ctx.exp(1) );
		else  // deve essere ctx.DIV()!=null
			return visit( ctx.exp(0) ) / visit( ctx.exp(1) );
	}

	@Override
	public Integer visitExpProd2(ExpProd2Context ctx) {
		// chiamando visit(ctx.exp(0)) e visit(ctx.exp(1)) si visita il sotto albero di ctx.exp(0) e ctx.exp(1) e si calcolano i valori
		boolean plus= ctx.PLUS( ) != null;
		System.out.println(indent+"exp: prod2 with "+(plus?"PLUS":"MINUS"));
		if (plus)
			return visit( ctx.exp(0) ) + visit( ctx.exp(1) );
		else  // deve essere ctx.MINUS()!=null
			return visit( ctx.exp(0) ) - visit( ctx.exp(1) );
	}

	@Override
	public Integer visitExpProd3(ExpProd3Context ctx) {
		System.out.println(indent+"exp: prod3 with LPAR RPAR");
		return visit(ctx.exp());
	}

	@Override
	public Integer visitExpProd4(ExpProd4Context ctx) {
		boolean minus= ctx.MINUS( ) != null;
		int res= Integer.parseInt(ctx.NUM().getText());
		System.out.println(indent+"exp: prod4 with "+(minus?"MINUS ":"")+"NUM "+res);
		return minus?-res:res;
	}
}

