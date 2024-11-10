# Calcolo dei Set di FIRST e FOLLOW

## Grammatica d'Esempio

Consideriamo la seguente grammatica:

```
S -> A B C 
A -> a | ε 
B -> b | ε 
C -> c | D 
D -> d | ε
```

---

## Calcolo dei Set di FIRST (Approccio Bottom-Up)

Per FIRST, seguiamo un approccio **bottom-up**, iniziando dalle variabili più "basse" o indipendenti (quelle senza dipendenze da altre variabili). Risalendo verso il simbolo di partenza `S`, calcoliamo i set di FIRST passo passo.

### Regole per FIRST

1. **Se la produzione inizia con un terminale**, quel terminale va direttamente nel FIRST della variabile.
2. **Se la produzione inizia con una variabile**, aggiungiamo tutto il FIRST di quella variabile al FIRST della variabile in esame.
    - Se il FIRST di quella variabile contiene `ε`, passiamo al simbolo successivo nella produzione.
3. **Se tutti i simboli di una produzione possono derivare in `ε`**, aggiungiamo `ε` al FIRST della variabile in esame.

### Procedura Bottom-Up per Calcolare FIRST

1. **FIRST(D)**
    - Produzioni per `D`: `D -> d | ε`
    - La prima produzione inizia con il terminale `d`, quindi aggiungiamo `d` a `FIRST(D)`.
    - La seconda produzione è solo `ε`, quindi aggiungiamo `ε` a `FIRST(D)`.
    - **Risultato**: `FIRST(D) = { d, ε }`

2. **FIRST(C)**
    - Produzioni per `C`: `C -> c | D`
    - La prima produzione inizia con il terminale `c`, quindi aggiungiamo `c` a `FIRST(C)`.
    - La seconda produzione inizia con la variabile `D`, quindi aggiungiamo `FIRST(D)` a `FIRST(C)`.
        - `FIRST(D) = { d, ε }`, quindi aggiungiamo `{ d }` a `FIRST(C)`.
        - Poiché `FIRST(D)` contiene `ε`, aggiungiamo anche `ε` a `FIRST(C)`.
    - **Risultato**: `FIRST(C) = { c, d, ε }`

3. **FIRST(B)**
    - Produzioni per `B`: `B -> b | ε`
    - La prima produzione inizia con il terminale `b`, quindi aggiungiamo `b` a `FIRST(B)`.
    - La seconda produzione è solo `ε`, quindi aggiungiamo `ε` a `FIRST(B)`.
    - **Risultato**: `FIRST(B) = { b, ε }`

4. **FIRST(A)**
    - Produzioni per `A`: `A -> a | ε`
    - La prima produzione inizia con il terminale `a`, quindi aggiungiamo `a` a `FIRST(A)`.
    - La seconda produzione è solo `ε`, quindi aggiungiamo `ε` a `FIRST(A)`.
    - **Risultato**: `FIRST(A) = { a, ε }`

5. **FIRST(S)**
    - Produzione per `S`: `S -> A B C`
    - La produzione inizia con `A`, quindi aggiungiamo `FIRST(A)` a `FIRST(S)`.
        - `FIRST(A) = { a, ε }`, quindi aggiungiamo `{ a }` a `FIRST(S)`.
        - Poiché `FIRST(A)` contiene `ε`, continuiamo con `B`.
    - Ora guardiamo `B`, quindi aggiungiamo `FIRST(B)` a `FIRST(S)`.
        - `FIRST(B) = { b, ε }`, quindi aggiungiamo `{ b }` a `FIRST(S)`.
        - `FIRST(B)` contiene `ε`, quindi continuiamo con `C`.
    - Ora guardiamo `C`, quindi aggiungiamo `FIRST(C)` a `FIRST(S)`.
        - `FIRST(C) = { c, d, ε }`, quindi aggiungiamo `{ c, d }` a `FIRST(S)`.
        - `FIRST(C)` contiene `ε`, quindi aggiungiamo anche `ε` a `FIRST(S)`.
    - **Risultato**: `FIRST(S) = { a, b, c, d, ε }`

### Riassunto dei Set di FIRST

- `FIRST(S) = { a, b, c, d, ε }`
- `FIRST(A) = { a, ε }`
- `FIRST(B) = { b, ε }`
- `FIRST(C) = { c, d, ε }`
- `FIRST(D) = { d, ε }`

---

## Calcolo dei Set di FOLLOW (Approccio Top-Down)

Per FOLLOW, utilizziamo un approccio **top-down**, partendo dalla variabile di partenza `S` e scendendo lungo le produzioni per calcolare i set di FOLLOW di ciascuna variabile.

### Regole per FOLLOW

1. **Se la variabile è il simbolo di partenza della grammatica**, `$` viene aggiunto al suo FOLLOW.
2. **Se trovi un terminale subito dopo una variabile** in una produzione, aggiungi quel terminale al FOLLOW della variabile in esame.
3. **Se una variabile è seguita da un’altra variabile in una produzione**, aggiungi tutto il FIRST della variabile successiva (tranne `ε`) al FOLLOW della variabile in esame.
    - Se il FIRST della variabile successiva contiene `ε`, continua con il simbolo successivo nella produzione.
4. **Se una variabile è l'ultimo simbolo di una produzione o se tutto ciò che la segue può derivare in `ε`**, aggiungi il FOLLOW della variabile capo della produzione (quella a sinistra della freccia) al FOLLOW della variabile in esame.

---

### Procedura Top-Down per Calcolare FOLLOW

1. **FOLLOW(S)**
    - `S` è il simbolo di partenza, quindi `$` viene aggiunto a `FOLLOW(S)`.
    - **Risultato**: `FOLLOW(S) = { $ }`

2. **FOLLOW(A)**
    - Produzione contenente `A`: `S -> A B C`
    - Dopo `A`, troviamo `B`, quindi aggiungiamo `FIRST(B)` a `FOLLOW(A)` (escludendo `ε`).
        - `FIRST(B) = { b, ε }`, quindi aggiungiamo `{ b }` a `FOLLOW(A)`.
        - Poiché `FIRST(B)` contiene `ε`, continuiamo con il simbolo successivo `C`.
    - Ora abbiamo `C`, quindi aggiungiamo `FIRST(C)` a `FOLLOW(A)` (escludendo `ε`).
        - `FIRST(C) = { c, d, ε }`, quindi aggiungiamo `{ c, d }` a `FOLLOW(A)`.
    - **Risultato**: `FOLLOW(A) = { b, c, d }`

3. **FOLLOW(B)**
    - Produzione contenente `B`: `S -> A B C`
    - Dopo `B`, troviamo `C`, quindi aggiungiamo `FIRST(C)` a `FOLLOW(B)` (escludendo `ε`).
        - `FIRST(C) = { c, d, ε }`, quindi aggiungiamo `{ c, d }` a `FOLLOW(B)`.
    - **Risultato**: `FOLLOW(B) = { c, d }`

4. **FOLLOW(C)**
    - Produzione contenente `C`: `S -> A B C`
    - `C` è l'ultimo simbolo della produzione, quindi eredita `FOLLOW(S)`.
        - `FOLLOW(S) = { $ }`, quindi aggiungiamo `$` a `FOLLOW(C)`.
    - **Risultato**: `FOLLOW(C) = { $ }`

5. **FOLLOW(D)**
    - Produzione contenente `D`: `C -> c | D`
    - `D` è l'ultimo simbolo nella produzione, quindi eredita `FOLLOW(C)`.
        - `FOLLOW(C) = { $ }`, quindi aggiungiamo `$` a `FOLLOW(D)`.
    - **Risultato**: `FOLLOW(D) = { $ }`

---

### Riassunto dei Set di FOLLOW

- `FOLLOW(S) = { $ }`
- `FOLLOW(A) = { b, c, d }`
- `FOLLOW(B) = { c, d }`
- `FOLLOW(C) = { $ }`
- `FOLLOW(D) = { $ }`

---

### Conclusione

Utilizzando l’approccio **bottom-up** per il calcolo di FIRST e **top-down** per il calcolo di FOLLOW, abbiamo costruito i set in modo ordinato e sequenziale:

- **FIRST**: Partendo dalle variabili più semplici e risalendo verso `S`.
- **FOLLOW**: Partendo da `S` e scendendo lungo le produzioni, rispettando le dipendenze tra variabili.


