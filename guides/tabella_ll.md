# Costruzione della Tabella LL(1)

## Grammatica d'Esempio

Consideriamo la seguente grammatica:

```
S -> A B C 
A -> a | ε 
B -> b | ε 
C -> c | D 
D -> d | ε
```


## Set di FIRST e FOLLOW

Per questa grammatica, abbiamo già calcolato i set di **FIRST** e **FOLLOW**:

- `FIRST(S) = { a, b, c, d, ε }`
- `FIRST(A) = { a, ε }`
- `FIRST(B) = { b, ε }`
- `FIRST(C) = { c, d, ε }`
- `FIRST(D) = { d, ε }`

- `FOLLOW(S) = { $ }`
- `FOLLOW(A) = { b, c, d }`
- `FOLLOW(B) = { c, d }`
- `FOLLOW(C) = { $ }`
- `FOLLOW(D) = { $ }`

## Passi per Costruire la Tabella LL(1)

La tabella LL(1) ha:
- **Righe** corrispondenti alle **produzioni della grammatica** (variabili).
- **Colonne** corrispondenti ai **simboli terminali** della grammatica, oltre a `$` per rappresentare la fine dell’input.

### Regole per Riempire la Tabella

1. **Per ogni produzione**, guarda il set di **FIRST** della variabile a sinistra.
    - **Se il FIRST contiene terminali**: inserisci la produzione nella tabella nella riga della variabile e nella colonna dei terminali corrispondenti.
    - **Se il FIRST contiene `ε`**, usa invece il **set di FOLLOW** della variabile:
        - Inserisci la produzione nella colonna dei simboli terminali presenti nel FOLLOW, **includendo anche `$` se presente**.

2. **Gestione delle Produzioni con `ε`**:
    - Le produzioni che derivano in `ε` vengono inserite nelle colonne dei terminali presenti nel FOLLOW della variabile.

3. **Gestione del Caso in cui un FIRST contenga `ε`**:
    - Se una variabile inizia con un’altra variabile e questa ha `ε` nel suo FIRST, allora segui la variabile successiva e unisci i simboli terminali in modo sequenziale, come indicato nei set di FIRST.

---

## Calcolo della Tabella LL(1)

### Produzioni e Inserimenti nella Tabella

1. **Produzione `S -> A B C`**
    - **FIRST(S)** contiene `{ a, b, c, d, ε }`.
        - Per i terminali `{ a, b, c, d }`, inseriamo `S -> A B C` nella tabella.
        - Poiché `FIRST(S)` contiene `ε`, usiamo anche `FOLLOW(S) = { $ }`.
            - Inseriamo `S -> A B C` nella colonna `$`.

2. **Produzione `A -> a`**
    - **FIRST(A)** contiene `{ a }`.
        - Inseriamo `A -> a` nella colonna `a` della tabella.

3. **Produzione `A -> ε`**
    - **FIRST(A)** contiene `ε`, quindi usiamo **FOLLOW(A) = { b, c, d }`.
        - Inseriamo `A -> ε` nelle colonne `b`, `c`, e `d`.

4. **Produzione `B -> b`**
    - **FIRST(B)** contiene `{ b }`.
        - Inseriamo `B -> b` nella colonna `b` della tabella.

5. **Produzione `B -> ε`**
    - **FIRST(B)** contiene `ε`, quindi usiamo **FOLLOW(B) = { c, d }`.
        - Inseriamo `B -> ε` nelle colonne `c` e `d`.

6. **Produzione `C -> c`**
    - **FIRST(C)** contiene `{ c }`.
        - Inseriamo `C -> c` nella colonna `c` della tabella.

7. **Produzione `C -> D`**
    - **FIRST(C)** contiene `d` (da `FIRST(D)`), quindi inseriamo `C -> D` nella colonna `d` della tabella.

8. **Produzione `D -> d`**
    - **FIRST(D)** contiene `{ d }`.
        - Inseriamo `D -> d` nella colonna `d` della tabella.

9. **Produzione `D -> ε`**
    - **FIRST(D)** contiene `ε`, quindi usiamo **FOLLOW(D) = { $ }`.
        - Inseriamo `D -> ε` nella colonna `$`.

---

## Tabella LL(1) Completa

Ecco la tabella risultante:

|        | a           | b           | c           | d           | $           |
|--------|-------------|-------------|-------------|-------------|-------------|
| **S**  | `S -> A B C` | `S -> A B C` | `S -> A B C` | `S -> A B C` | `S -> A B C` |
| **A**  | `A -> a`    | `A -> ε`    | `A -> ε`    | `A -> ε`    |             |
| **B**  |             | `B -> b`    | `B -> ε`    | `B -> ε`    |             |
| **C**  |             |             | `C -> c`    | `C -> D`    |             |
| **D**  |             |             |             | `D -> d`    | `D -> ε`    |

---

## Riassunto dei Passi

1. **Per ogni produzione**, guarda i terminali in FIRST e riempi le celle corrispondenti.
2. **Se FIRST contiene `ε`**, riempi le celle dei terminali in FOLLOW.
3. **Esegui i controlli per `ε` nei set di FIRST**, seguendo l’ordine delle variabili in modo sequenziale se necessario.

Questa tabella LL(1) permette un parsing predittivo della grammatica, associando ciascun terminale e variabile alla produzione corretta in base ai set di FIRST e FOLLOW.
