grammar SimpleExpTwo;

//dichiarazione lexicalErrors
@lexer::members {
int lexicalErrors=0;
}

prog: exp EOF;

exp: <assoc=right> exp TIMES exp  #expProd1
   | <assoc=right> exp MINUS exp  #expProd2 //<assoc=right> exp PLUS exp
   | <assoc=right> LPAR exp RPAR  #expProd3
   | NUM            #expProd4
   ;


MINUS   : '-';
TIMES   : '*';
LPAR    : '(';
RPAR    : ')';
NUM     : '0' | ('1'..'9') ('0'..'9')* ;

WHITESP : (' ' | '\t' | '\n' | '\r')+ -> channel(HIDDEN); // matchato non lo diamo al parser

ERR     : . { System.out.println("Invalid char: "+ this.getText()); this.lexicalErrors++;  } -> channel(HIDDEN); // . = qualsiasi carattere, matchato non lo diamo al parser


COMMENT: '/*' .*? '*/' -> channel(HIDDEN);
