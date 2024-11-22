package compiler;

import compiler.AST.*;
import compiler.exc.IncomplException;
import compiler.exc.TypeException;
import compiler.lib.BaseEASTVisitor;
import compiler.lib.FOOLlib;
import compiler.lib.Node;
import compiler.lib.TypeNode;

import static compiler.lib.FOOLlib.isSubtype;

//visit(n) fa il type checking di un Node n e ritorna: 
//per una espressione, il suo tipo (oggetto BoolTypeNode o IntTypeNode)
//per una dichiarazione, "null"
public class TypeCheckEASTVisitor extends BaseEASTVisitor<TypeNode,TypeException> {

	TypeCheckEASTVisitor() { super(true); } // enables incomplete tree exceptions
	TypeCheckEASTVisitor(boolean debug) { super(true,debug); } // enables print for debugging

	@Override
	public TypeNode visitNode(ProgLetInNode n) throws TypeException {
		if (print) printNode(n);
		for (Node dec : n.declist) 
			try {
				visit(dec);		
			} catch (TypeException e) {
				System.out.println("Type checking error in a declaration: "+e.text);
			} catch (IncomplException e) {
			}
		return visit(n.exp);
	}

	@Override
	public TypeNode visitNode(ProgNode n) throws TypeException {
		if (print) printNode(n);
		return visit(n.exp);
	}

	@Override
	public TypeNode visitNode(FunNode n) throws TypeException {
		if (print) printNode(n,n.id);
		for (Node dec : n.declist)
			try {
				visit(dec);
			} catch (TypeException e) {
				System.out.println("Type checking error in a declaration: "+e.text);
			} catch (IncomplException e) {
			}
		visit(n.retType);
		if ( !(isSubtype(visit(n.exp),n.retType)) )
			throw new TypeException("Wrong return type for function " + n.id,n.getLine());
		return null;
	}

	@Override
	public TypeNode visitNode(VarNode n) throws TypeException {
		if (print) printNode(n,n.id);
		visit(n.type);
		if ( !(isSubtype(visit(n.exp),n.type)) )
			throw new TypeException("Incompatible value for variable " + n.id,n.getLine());
		return null;
	}

	@Override
	public TypeNode visitNode(PrintNode n) throws TypeException {
		if (print) printNode(n);
		return visit(n.exp);
	}


	@Override
	public TypeNode visitNode(IfNode n) throws TypeException {
		if (print) printNode(n);
		if ( !(isSubtype(visit(n.cond),new BoolTypeNode())) )
			throw new TypeException("Non boolean condition in if",n.getLine());
		TypeNode t=visit(n.th);
		TypeNode e=visit(n.el);
		if ( isSubtype(t,e) ) return e;
		if ( isSubtype(e,t) ) return t;
		throw new TypeException("Incompatible types in then-else branches",n.getLine());
	}

	@Override
	public TypeNode visitNode(EqualNode n) throws TypeException {
		if (print) printNode(n);
		TypeNode l=visit(n.left);
		TypeNode r=visit(n.right);
		if ( !(isSubtype(l,r) || isSubtype(r,l)) )
			throw new TypeException("Incompatible types in equal",n.getLine());
		return new BoolTypeNode();
	}

	@Override
	public TypeNode visitNode(TimesNode n) throws TypeException {
		if (print) printNode(n);
		if ( !( isSubtype(visit(n.left), new IntTypeNode()) &&
		        isSubtype(visit(n.right), new IntTypeNode()) )  ) 
			throw new TypeException("Non integers in multiplication",n.getLine());
		return new IntTypeNode();
	}

	@Override
	public TypeNode visitNode(PlusNode n) throws TypeException {
		if (print) printNode(n);
		if ( !( isSubtype(visit(n.left), new IntTypeNode()) &&
		        isSubtype(visit(n.right), new IntTypeNode()) )  ) 
			throw new TypeException("Non integers in sum",n.getLine());
		return new IntTypeNode();
	}

	@Override
	public TypeNode visitNode(CallNode n) throws TypeException {
		if (print) printNode(n,n.id);
		TypeNode t = visit(n.entry); //recupero tipo

		if (t instanceof ArrowTypeNode arrowType) {
			// Check numero di parametri
			if (arrowType.parlist.size() != n.arglist.size()) {
				throw new TypeException("Wrong number of parameters in the invocation of " + n.id, n.getLine());
			}

			// Check tipo degli argomenti
			for (int i = 0; i < n.arglist.size(); i++) {
				TypeNode argType = visit(n.arglist.get(i));
				TypeNode parType = arrowType.parlist.get(i);
				if (!FOOLlib.isSubtype(argType, parType)) {
					throw new TypeException("Wrong type for " + (i + 1) + "-th parameter in the invocation of " + n.id, n.getLine());
				}
			}
		} else {
			throw new TypeException("Invocation of a non-function " + n.id, n.getLine());
		}

		for (Node arg : n.arglist) {
			visit(arg);
		}

		return arrowType.ret;
	}

	@Override
	public TypeNode visitNode(IdNode n) throws TypeException {
		if (print) printNode(n,n.id);
		TypeNode t = visit(n.entry);
		if (t instanceof ArrowTypeNode)
			throw new TypeException("Wrong usage of function identifier "+n.id,n.getLine());			
		return t;
	}

	@Override
	public TypeNode visitNode(BoolNode n) {
		if (print) printNode(n,n.val.toString());
		return new BoolTypeNode();
	}

	@Override
	public TypeNode visitNode(IntNode n) {
		if (print) printNode(n,n.val.toString());
		return new IntTypeNode();
	}

// gestione tipi incompleti	(se lo sono lancia eccezione)
	@Override
	public TypeNode visitNode(ArrowTypeNode n) throws TypeException {
		if (print) printNode(n);
		for (Node par: n.parlist) visit(par);
		visit(n.ret,"->"); //marks return type
		return null;
	}

	@Override
	public TypeNode visitNode(BoolTypeNode n) {
		if (print) printNode(n);
		return null;
	}

	@Override
	public TypeNode visitNode(IntTypeNode n) {
		if (print) printNode(n);
		return null;
	}

// STentry (ritorna campo type)
	@Override
	public TypeNode visitSTentry(STentry entry) throws TypeException {
		if (print) printSTentry("type");
		visit(entry.type);
		return entry.type;
	}


}













