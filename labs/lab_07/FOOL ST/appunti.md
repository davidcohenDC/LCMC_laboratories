# Significato di let e in.

Usiamo let, in invece di {} per semplificare la vita, in modo che le dichiarazioni fossero tutte nel blocco.

Dichiarazioni come nel linguaggio funzionale ML (Meta Language)

-------------

```
{
int x = 5;
int y = 6;

Codice senza dichiarazioni
}
```

------------------

```

let

int x = 5;
int y = 6;

in

Codice senza dichiarazioni

;
```

- Dopo l'in metto il codice principale ritornando un risultato.
- Invece di fare int Y, facciamo var:int e poi mettiamo l'espressione.
- Abbiamo un concetto di immutabilità, non abbiamo assegnamento.