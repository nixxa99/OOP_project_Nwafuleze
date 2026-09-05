# Sistema Simulato POS Ristorante
ciaoo
### Progetto finale di Nwafuleze Diandra per il corso di Epicode OOP

## Panoramica dell'Applicazione e Funzionalità
Questo progetto è un'applicazione Java SE che implementa un sistema POS per un ristorante. Il sistema funziona tramite un'interfaccia a riga di comando (CLI) e permette agli utenti di gestire il menù di un ristorante e simulare gli ordini dei clienti.

Le funzionalità principali includono:
- Visualizzazione dell'intera gerarchia del menù (categorie e singoli piatti).
- Filtraggio del menù per visualizzare solo le opzioni vegetariane.
- Aggiunta dinamica di nuovi piatti al menù.
- Salvataggio dello stato attuale del menù in un file di archiviazione persistente.
- Simulazione di un ordine cliente con generazione automatica dello scontrino.
- Attivazione/disattivazione della modalità "Happy Hour" che applica sconti dinamici agli ordini.

## Tecnologie e Pattern Utilizzati

### Pattern di Progettazione utilizzati
- **Factory Pattern (`MenuItemFactory`)**: Utilizzato per incapsulare la logica di istanziazione degli oggetti `MenuItem`. Centralizza la creazione degli oggetti, rendendo più semplice l'aggiunta di nuovi piatti in futuro senza modificare la logica principale di business.

- **Composite Pattern (`MenuComponent`, `MenuCategory`, `MenuItem`)**: Utilizzato per rappresentare la struttura gerarchica del menù. Permette al sistema di trattare i singoli elementi (foglie) e le categorie di elementi (compositi) in modo uniforme, semplificando operazioni come la stampa dell'intero albero del menù.

- **Iterator Pattern (`DietaryIterator`, `MenuIterator`)**: Utilizzato per attraversare la struttura del menù. Astrae la logica di attraversamento dalla struttura dati sottostante (l'albero Composite), consentendo iterazioni specializzate come il filtraggio dei soli piatti vegetariani (`DietaryIterator`) senza esporre le implementazioni interne.

- **Exception Shielding (`DataStorageException`, `SystemException`)**: Vengono utilizzate eccezioni personalizzate per incapsulare le eccezioni di sistema o di I/O sottostanti. Impedisce che i dettagli a basso livello (come gli errori relativi a percorsi di file specifici o problemi di serializzazione) si propaghino al livello di presentazione, mantenendo una netta separazione delle responsabilità e impedendo che stack trace sensibili raggiungano l'utente finale.

- **Strategy Pattern (`DiscountStrategy`, `HappyHourStrategy`, `NoDiscountStrategy`)**: Utilizzato per gestire la logica di sconto per gli ordini. Consente di selezionare e scambiare l'algoritmo di sconto a runtime senza alterare la classe `Order`.

- **Singleton Pattern (`RestaurantManager`)**: Utilizzato per gestire lo stato globale del ristorante, come l'istanza del menù principale e lo stato di attivazione dell'Happy Hour. Assicura che ci sia un unico punto di controllo coordinato per lo stato principale del ristorante per tutto il ciclo di vita dell'applicazione.

### Tecnologie impiegate
- **Collections Framework**: Utilizzati (es. `ArrayList` in `MenuCategory` e `Order`) per memorizzare e gestire gruppi dinamici di oggetti.

- **Generics**: Utilizzati all'interno delle classi di utilità (es. `MenuStorageUtil.<T>load()`) per garantire la sicurezza dei tipi (type safety) durante i processi di serializzazione e deserializzazione.

- **Java I/O**: Implementato in `MenuStorageUtil` utilizzando la serializzazione degli oggetti (`ObjectOutputStream`, `ObjectInputStream`) per persistere lo stato del menù in un file binario (`menu_data.dat`).

- **Logging**: Configurato tramite `LoggerManager` (utilizzando `java.util.logging`) per tracciare eventi dell'applicazione, errori e transizioni di stato del sistema, indirizzando l'output su `application.log`.

- **JUnit Testing**: È fornita una suite di test utilizzando JUnit 5 (vedi `RestaurantTest.java`) per verificare il corretto comportamento dei componenti principali come i pattern Factory e Composite.

- **Stream API e Espressioni Lambda**: Utilizzate (es. `Order`, `MenuCategory`) per elaborare le collezioni di dati in modo funzionale e compatto, migliorando la leggibilità durante i calcoli (es. il totale dell'ordine) o per verifiche logiche come il controllo della flag vegetariana per gli elementi di una categoria. Le lambda sono usate anche per rendere più snelli i test JUnit tramite `assertThrows`.

### Pratiche di Programmazione Sicura
- **Sanitizzazione dell'Input (Input Sanitization)**: Implementata tramite `InputValidator` per garantire che gli input utente dalla CLI vengano processati in modo sicuro (es. intercettando `NumberFormatException` per i numeri interi), prevenendo crash dell'applicazione a causa di input non validi.
- **Propagazione Controllata delle Eccezioni**: Come indicato in Exception Shielding, tutti gli errori critici vengono intercettati in modo sicuro, presentando alla CLI solo messaggi comprensibili per l'utente.
- **Nessun Segreto Hardcoded**: I percorsi dei file e le configurazioni sono gestiti tramite costanti, evitando dati sensibili hardcoded.

## Istruzioni di Setup ed Esecuzione

### Prerequisiti
- Java Development Kit (JDK) 11 o superiore.
- Maven (o un IDE con supporto Maven integrato come IntelliJ IDEA).

### Esecuzione tramite IDE
1. Aprire la cartella del progetto (`oop_nwafuleze`) nel proprio IDE di scelta.
2. Consentire all'IDE di risolvere le dipendenze tramite il file `pom.xml`.
3. Individuare la classe `Main` in `src/main/java/com/restaurantpos/Main.java`.
4. Eseguire il metodo `main`.

### Esecuzione dei Test
I test possono essere eseguiti direttamente tramite il test runner integrato dell'IDE scelto o tramite CLI Maven:
```bash
mvn clean test
```

## Diagrammi UML



## Limitazioni Note e Possbili Sviluppi Futuri
- **Limitazioni**: L'attuale meccanismo di archiviazione utilizza la Serializzazione Java, che è fragile rispetto alle modifiche della struttura delle classi.
- L'interfaccia CLI è basilare e sincrona.
- L'applicazione è progettata per un uso singolo. In un vero ristorante, più camerieri potrebbero voler inserire ordini contemporaneamente. Allo stato attuale, il sistema non gestisce accessi simultanei o il multithreading, il che causerebbe problemi di sovrascrittura o eccezioni.
- **Possibili Sviluppi Futuri**: Interfaccia Grafica (GUI). Si potrebbe sostituire o affiancare la CLI con un'interfaccia grafica moderna utilizzando JavaFX, rendendo l'utilizzo molto più intuitivo
- Aggiunta di un inventory per il tracciamento degli ingredienti disponibili. Ogni volta che si ordina un piatto, il sistema scala automaticamente le quantità dal magazzino virtuale per segnalare quando un piatto è "Esaurito".
- Espansione del sistema di ordinazione per gestire la disposizione di più tavoli (in questo progetto viene simulato un `Tavolo 1` fittizio), i conti separati per cliente e calcoli fiscali complessi.