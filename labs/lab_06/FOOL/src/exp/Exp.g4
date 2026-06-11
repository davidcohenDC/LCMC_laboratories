grammar Exp;

@lexer::members {
public int lexicalErrors=0;
}

// PARSER RULES
// ANTLR mi consente di usare RE perchè sono comode, quindi posso usare anche l'alternativa + (che la usa per la chisura positiva), mentre per le alternative usa la pipe.
// Quindi posso fare TIMES | DIV mettendoli allo stesso livello di priorità dando priorità a quelli nella prima produzione.
// Per evitare ambiguità devo mettere le parentesi tonde
// Possiamo usare anche il punto interrogativo, che è un operatore di comodità che dice che è opzionale ossia 'alternativa' \epsilon.
// Le grammatiche che usano le RE nel corpo delle produzione permettono di essere più sintentici e si chiamano EBNF
// (Extended Bacus Naur Form).


prog : exp EOF {System.out.println("Parsing finished!");} ;
 
exp     : exp (TIMES | DIV) exp     #expProd1 // <assoc=right>
        | exp (PLUS | MINUS) exp    #expProd2 // <assoc=right>
        | LPAR exp RPAR             #expProd3
        | MINUS? NUM                #expProd4 // prima di un numero ci può essere un meno.
        ;
          
// LEXER RULES

PLUS	: '+' ;
TIMES	: '*' ;
MINUS	: '-' ;
DIV	    : '/' ;
LPAR    : '(' ;
RPAR    : ')' ;
NUM     : '0' | ('1'..'9')('0'..'9')* ;

WHITESP	: (' '|'\t'|'\n'|'\r')+ -> channel(HIDDEN) ;
COMMENT : '/*' .*? '*/' -> channel(HIDDEN) ;

ERR 	: . {System.out.println("Invalid char: "+ getText()); lexicalErrors++;} -> channel(HIDDEN) ; 



