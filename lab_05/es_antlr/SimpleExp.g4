grammar SimpleExp;

// Dichiarazione della variabile 'lexicalErrors' nel lexer
@lexer::members {
    int lexicalErrors = 0;
}

// Regole del PARSER

/*
    Grammatica per espressioni aritmetiche semplici:
    E -> E * E
       | E + E
       | (E)
       | n

    Definiamo esplicitamente le regole di precedenza e associatività per gli operatori
    per assicurare che il parser generi un unico albero sintattico per ogni stringa valida,
    senza necessità di introdurre non-terminali aggiuntivi come T e F.

    ANTLR permette di definire grammatiche ambigue e specificare come risolvere
    le ambiguità utilizzando regole di precedenza e associatività.

    Nella nostra grammatica:
    - Assegniamo maggiore precedenza all'operatore '*' rispetto a '+'
    - Utilizziamo l'associatività a sinistra per entrambi gli operatori (default in ANTLR)
*/

// Simbolo iniziale
prog: exp EOF;

// Regole per le espressioni con nomi per le produzioni
exp
    : exp TIMES exp  #expProd1   // Moltiplicazione
    | exp PLUS exp   #expProd2   // Addizione
    | LPAR exp RPAR  #expProd3   // Parentesi
    | NUM            #expProd4   // Numero
    ;

// Regole del LEXER

// Token per operatori e parentesi
PLUS    : '+';
TIMES   : '*';
LPAR    : '(';
RPAR    : ')';
NUM     : '0' | [1-9][0-9]* ;

// Spazi bianchi (ignorati dal parser)
WHITESP : [ \t\n\r]+ -> channel(HIDDEN);

// Token per catturare caratteri non validi
ERR     : . { System.out.println("Invalid char: " + getText()); lexicalErrors++; } -> channel(HIDDEN);

// Gestione dei commenti utilizzando la stella di Kleene non greedy per evitare il maximal match
COMMENT : '/*' .*? '*/' -> channel(HIDDEN);

/*
    Spiegazione:

    - Il blocco '@lexer::members' dichiara la variabile 'lexicalErrors',
      che tiene traccia del numero di errori lessicali incontrati.

    - La regola 'ERR' cattura qualsiasi singolo carattere ('.') e incrementa il contatore
      'lexicalErrors' quando viene rilevato un carattere non valido.

    - La regola 'COMMENT' utilizza '.*?' per effettuare un match non greedy,
      assicurando che i commenti vengano gestiti correttamente senza consumare
      parti indesiderate dell'input.

    - Aggiungendo 'EOF' alla regola 'prog', garantiamo che l'intero input sia
      consumato durante il parsing.

    - Le produzioni nominate (ad esempio, '#expProd1') ci permettono di identificare
      specifiche produzioni nei nostri metodi visitor.

    - Affidandoci all'associatività a sinistra di default in ANTLR per gli operatori,
      la precedenza è determinata dall'ordine delle produzioni: le regole definite
      prima hanno maggiore precedenza.
*/
