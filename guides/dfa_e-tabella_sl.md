# Costruzione del DFA e della Tabella SL(1) per la Grammatica

## Grammatica d'Esempio

Consideriamo la seguente grammatica:

```
S' -> S 
S -> A B c 
A -> a A | b 
B -> b
```

Questa grammatica ha:
- `S'` come produzione iniziale aggiunta.
- `S`, `A`, `B` come variabili.
- `a`, `b`, `c` come terminali.

---

## Calcolo dei Set di FIRST e FOLLOW

### Set FIRST

- **FIRST(S') = FIRST(S) = { a, b }**
- **FIRST(S) = FIRST(A B c) = FIRST(A) = { a, b }**
- **FIRST(A) = { a, b }** (perché `A -> a A | b`)
- **FIRST(B) = { b }** (perché `B -> b`)

### Set FOLLOW

Per calcolare i set FOLLOW, consideriamo le posizioni in cui appaiono le variabili nella grammatica.

- **FOLLOW(S') = { $ }** (dato che `S'` è l'inizio)
- **FOLLOW(S) = FOLLOW(S') = { $ }** (poiché `S' -> S`)
- **FOLLOW(A)**:
    - `A` appare in `S -> A B c`
    - Dopo `A`, c'è `B c`
    - **FIRST(B c) = FIRST(B) = { b }** (poiché `B` non può derivare ε)
    - Quindi, **FOLLOW(A) = FIRST(B c) = { b }**
- **FOLLOW(B)**:
    - `B` appare in `S -> A B c`
    - Dopo `B`, c'è `c`
    - **FIRST(c) = { c }**
    - Quindi, **FOLLOW(B) = FIRST(c) = { c }**

---

## Costruzione del DFA (Automa a Stati Finiti Deterministico)

### Obiettivo

Costruire un DFA che rappresenti le sequenze di produzione della grammatica utilizzando elementi LR(0), dove ogni elemento è una produzione con un pallino (`•`) che indica la posizione corrente.

### Regole per Costruire il DFA

1. **Stato Iniziale (I₀)**:
    - Inizia con l'elemento iniziale `S' -> • S`.
    - Applica la **chiusura** su questo elemento.

2. **Chiusura**:
    - Per ogni elemento che ha un non terminale **dopo** il pallino, aggiungi tutte le produzioni di quel non terminale con il pallino all'inizio.
    - Ripeti finché non ci sono più nuovi elementi da aggiungere.

3. **Transizioni (GOTO)**:
    - Per ogni simbolo (terminale o non terminale) che segue un pallino negli elementi dello stato corrente, crea una transizione spostando il pallino oltre quel simbolo.
    - Il nuovo stato include tutti gli elementi ottenuti dopo lo spostamento del pallino, più la loro chiusura se necessario.

4. **Stati di Riduzione**:
    - Se un elemento ha il pallino alla fine (es. `A -> α •`), lo stato corrispondente è uno stato di riduzione per quella produzione.

---

## Procedura Dettagliata per Costruire il DFA

### Stato `I₀`

Elementi iniziali:
- `S' -> • S`

Applichiamo la **chiusura**:
- Il pallino è davanti a `S`, un non terminale.
- Aggiungiamo le produzioni di `S` con il pallino all'inizio:
    - `S -> • A B c`
- Il pallino è ora davanti a `A`, quindi applichiamo la chiusura per `A`:
    - `A -> • a A`
    - `A -> • b`

Elementi finali di `I₀`:
- `S' -> • S`
- `S -> • A B c`
- `A -> • a A`
- `A -> • b`

### Costruzione delle Transizioni e degli Stati Successivi

**Transizioni da `I₀`:**

1. **Con `S`**:
    - Elemento: `S' -> • S` diventa `S' -> S •`
    - Stato `I₁`:
        - `S' -> S •` (stato di accettazione perché `S'` è l'inizio)

2. **Con `A`**:
    - Elemento: `S -> • A B c` diventa `S -> A • B c`
    - Applichiamo la chiusura per `B` (pallino davanti a `B`):
        - `B -> • b`
    - Stato `I₂`:
        - `S -> A • B c`
        - `B -> • b`

3. **Con `a`**:
    - Elemento: `A -> • a A` diventa `A -> a • A`
    - Applichiamo la chiusura per `A` (pallino davanti a `A`):
        - `A -> • a A`
        - `A -> • b`
    - Stato `I₃`:
        - `A -> a • A`
        - `A -> • a A`
        - `A -> • b`

4. **Con `b`**:
    - Elementi:
        - `A -> • b` diventa `A -> b •`
        - `B -> • b` (da `I₂`) diventa `B -> b •`
    - Stati:
        - Stato `I₄`:
            - `A -> b •` (riduzione per `A -> b`)
        - Stato `I₆`:
            - `B -> b •` (riduzione per `B -> b`)

**Transizioni successive:**

- **Da `I₂` con `B`**:
    - `S -> A • B c` diventa `S -> A B • c`
    - Stato `I₅`:
        - `S -> A B • c`

- **Da `I₅` con `c`**:
    - `S -> A B • c` diventa `S -> A B c •`
    - Stato `I₇`:
        - `S -> A B c •` (riduzione per `S -> A B c`)

- **Da `I₃` con `A`**:
    - `A -> a • A` diventa `A -> a A •`
    - Stato `I₈`:
        - `A -> a A •` (riduzione per `A -> a A`)

---

## Riassunto degli Stati del DFA

| Stato | Elementi                                                                 |
|-------|--------------------------------------------------------------------------|
| `I₀`  | `S' -> • S`<br>`S -> • A B c`<br>`A -> • a A`<br>`A -> • b`              |
| `I₁`  | `S' -> S •`                                                              |
| `I₂`  | `S -> A • B c`<br>`B -> • b`                                             |
| `I₃`  | `A -> a • A`<br>`A -> • a A`<br>`A -> • b`                               |
| `I₄`  | `A -> b •`                                                               |
| `I₅`  | `S -> A B • c`                                                           |
| `I₆`  | `B -> b •`                                                               |
| `I₇`  | `S -> A B c •`                                                           |
| `I₈`  | `A -> a A •`                                                             |

---

## Tabella delle Transizioni (GOTO)

| Da Stato | Con Simbolo | A Stato |
|----------|-------------|---------|
| `I₀`     | `S`         | `I₁`    |
| `I₀`     | `A`         | `I₂`    |
| `I₀`     | `a`         | `I₃`    |
| `I₀`     | `b`         | `I₄`    |
| `I₂`     | `B`         | `I₅`    |
| `I₂`     | `b`         | `I₆`    |
| `I₅`     | `c`         | `I₇`    |
| `I₃`     | `A`         | `I₈`    |
| `I₃`     | `a`         | `I₃`    |
| `I₃`     | `b`         | `I₄`    |

---

## Costruzione della Tabella SL(1)

### Struttura della Tabella

- **Righe**: Stati del DFA (`I₀` - `I₈`).
- **Colonne**:
    - **Azioni**: terminali (`a`, `b`, `c`, `$`).
    - **GOTO**: non terminali (`S`, `A`, `B`).

### Regole per Riempire la Tabella

1. **SHIFT (sN)**: Se c'è una transizione su un terminale, inserisci "shift" con lo stato di destinazione.
2. **GOTO (N)**: Se c'è una transizione su un non terminale, inserisci lo stato di destinazione nella sezione GOTO.
3. **REDUCE (rN)**: Se lo stato contiene un elemento con il pallino alla fine, inserisci "reduce" con il numero della produzione nelle colonne dei terminali nel FOLLOW della testa della produzione.
4. **ACCEPT (acc)**: Se lo stato contiene l'elemento `S' -> S •`, inserisci "accept" nella colonna `$`.

### Numerazione delle Produzioni

1. `(1)` `S' -> S`
2. `(2)` `S -> A B c`
3. `(3)` `A -> a A`
4. `(4)` `A -> b`
5. `(5)` `B -> b`

### Tabella Completa

| Stato |   `a`   |   `b`   |   `c`   |   `$`   |   `S`   |   `A`   |   `B`   |
|-------|---------|---------|---------|---------|---------|---------|---------|
| `I₀`  |  s3     |  s4     |         |         |    1    |    2    |         |
| `I₁`  |         |         |         |  acc    |         |         |         |
| `I₂`  |         |  s6     |         |         |         |         |    5    |
| `I₃`  |  s3     |  s4     |         |         |         |    8    |         |
| `I₄`  |         | **r4**  |         |         |         |         |         |
| `I₅`  |         |         |  s7     |         |         |         |         |
| `I₆`  |         |         | **r5**  |         |         |         |         |
| `I₇`  |         |         |         | **r2**  |         |         |         |
| `I₈`  |         | **r3**  |         |         |         |         |         |

**Legenda:**
- **sN**: shift e vai allo stato `N`.
- **rN**: riduci usando la produzione numero `N`.
- **acc**: accetta l'input.

### Spiegazione delle Azioni di Riduzione

- **Stato `I₄` (`A -> b •`)**:
    - Riduci usando la produzione `(4)` `A -> b`.
    - Applica la riduzione nei terminali in `FOLLOW(A) = { b }`.
- **Stato `I₆` (`B -> b •`)**:
    - Riduci usando la produzione `(5)` `B -> b`.
    - Applica la riduzione nei terminali in `FOLLOW(B) = { c }`.
- **Stato `I₇` (`S -> A B c •`)**:
    - Riduci usando la produzione `(2)` `S -> A B c`.
    - Applica la riduzione nei terminali in `FOLLOW(S) = { $ }`.
- **Stato `I₈` (`A -> a A •`)**:
    - Riduci usando la produzione `(3)` `A -> a A`.
    - Applica la riduzione nei terminali in `FOLLOW(A) = { b }`.

---

## Conclusione

La costruzione del DFA e della tabella SL(1) richiede attenzione nel:

- Applicare la **chiusura** sulle variabili **dopo** il pallino, non prima.
- Calcolare correttamente i set di **FIRST** e **FOLLOW**.
- Gestire le azioni di riduzione solo nei terminali appropriati in base al **FOLLOW**.

Con queste correzioni, la tabella SL(1) risulta corretta e utilizzabile per l'analisi sintattica della grammatica data.
