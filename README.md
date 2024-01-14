# 1. Introducere
_________________________________________________________
Proiectul presupune implementarea unei applicatii "Bank System" si demonstrarea aplicarii modelelor de proiectare in dezvoltarea de software.

## 1.1 Putin despre business-ul aplicatiei
_________________________________________________________
Sa implementat un sistem bancar in care folosind endpoint-uri (doar implementare back-end, fara front-end) se poate efectua operatiuni CRUD (Creare, Read, Update, Delete) asupra unui cont bancar care descrie numarul contului (accountNumber) , soldul (balance) si starea (state) acestuia. Pe langa aceasta, se poate efectua tranzactii de depunere (deposit) si retragere (withdrawal) asupra contului respectiv. Tranzactiile se pot efectua doar in cazul in care contul bancar este activ (state=ACTIVE).

# 2. Stack-ul de Tehnologii
_________________________________________________________
- Java 17
- Spring Boot Framework
- Maven
- MySQL DataBase
- Postman
_________________________________________________________
- Am ales limbajul Java 17 pentru ca este usor de implemntat si versiunea sa este Long-Term-Suport (LTS), adica aceasta versiune primeste asistenta si actualizari pe termen lung din partea comunitatii Java. Conceptul de LTS este deosebit de important pentru intreprinderi si dezvoltatori care construiesc si intretin aplicatii care necesita stabilitate si suport pe termen lung.
- Spring Boot Framework are urmatoarele avantaje:
    - a. ofera posibilitatea de a construi o aplicatie pe layere: Controller, Service si Repository fiecare strat avand rolul sau;
    - b. elimina nevoia de configurare extensiva, acest lucru facand ca dezvoltarea sa fie mai rapida si mai usoara;
    - c. faciliteaza gestionarea dependentelor prin intermediul Maven si Gradle;
    - d. se integreaza usor cu alte module Spring cum ar fi (Spring Data, Security, Cloud) oferind solutie comprehensiva pentru diverse nevoie de dezvoltare
- MySQL: este un sistem de gestionare a bazelor de date relationale (RDBMS - Relational Database Management System) open-source, care ofera o platforma eficienta si fiabila pentru stocarea si gestionarea datelor.
- Postman: cu acest tool pot efectua operatiuni de request si analiza a raspunsului.

# 3. Modele de proiectare
_________________________________________________________
## 3.1. Command
_________________________________________________________
Modelul de desing **Command** este un model de proiectare comportamentala care transforma o solicitare intr-un obiect, permitand clientilor sa parametrizeze clientii cu diferite solicitari, solicitari in coada si sa suporte operatiuni anulabile. Separa expeditorul si destinatarul unei cereri, incapsuland o cerere ca obiect. Elementele principale ale modelului de comanda sunt: 

**Command Interface** (abstract class): Aceasta interfata are de obicei o referinta la obiectul asupra caruia ar trebui efectuata actiunea. 
```java
    public abstract class Transaction {

        protected Account account;

        protected Double amount;

        protected LocalDateTime dateTimeTransaction;

        abstract void execute();
    }
```

**Concrete Command**: Implementeaza interfata de comanda si specifica legatura dintre o comanda si obiectul care executa comanda.
```java
    public class DepositTransaction extends Transaction {

        @Override
        public void execute() {
            account.deposit(amount);
            dateTimeTransaction = LocalDateTime.now();
        }
    }
   
    @Service
    public class WithdrawalTransaction extends Transaction {

        @Override
        public void execute() {
            account.withdraw(amount);
            dateTimeTransaction = LocalDateTime.now();
        }
    }
```
    
**Receiver**: Stie cum sa efectueze operatia asociata comenzii. Este tinta comenzii si contine implementarea efectiva a actiunii.
```java
    public class Account {

        // other fields 

        @Enumerated(EnumType.STRING)
        private AccountState state;

        public void deposit(double amount) {
            state.deposit(this, amount);
        }

        public void withdraw(double amount) {
            state.withdraw(this, amount);
        }
    }
```
**Invoker**: Solicita comenzii sa execute cererea. Contine o referinta la obiectul de comanda si declanseaza executia comenzii.
```java
    public class TransactionService {

        private final AccountService accountService;

        @Transactional
        public void executeTransaction(TransactionRequest transactionRequest) {

            var transaction = transactionRequest.getTransactionType().getTransaction();

            // some business logic

            transaction.execute();
        }
    }
```

## 3.2 State
_________________________________________________________
**State** este un model de design comportamental care permite unui obiect să-și modifice comportamentul atunci când starea sa internă se schimbă. Pare ca și cum obiectul isi schimba clasa.

**Context**: Aceasta este clasa care conține statutul. Menține o referință la obiectul de stare curent și deleagă comportamentul specific stării obiectului de stare. Clasa de context este cea al cărei comportament se modifică pe baza stării sale interne.
```java
public class Account {

    private Long id;
    private String accountNumber;
    private Double balance;
    private AccountState state;

    public void deposit(double amount) {
        state.deposit(this, amount);
    }

    public void withdraw(double amount) {
        state.withdraw(this, amount);
    }
}
```

**State**: Aceasta este interfața care definește o interfață comună pentru toate clasele de stări concrete. Declara metode care reprezinta diferitele actiuni care pot fi efectuate atunci cand obiectul se afla in acea stare.
```java
public interface State {
  void deposit(Account account, double amount);
  void withdraw(Account account, double amount);
}
```

**Concrete State**: Acestea sunt clasele (enum-urile) care implementează interfața State. Fiecare clasă (enum) de stare concretă oferă o implementare specifică a comportamentului specific stării.
```java
public enum AccountState implements State {
    ACTIVE {
        @Override
        public void deposit(Account account, double amount) {
            account.setBalance(account.getBalance() + amount);
            log.info("Deposited " + amount + "$ in account " + account.getAccountNumber() + ". Current balance: " + account.getBalance() + "$");
        }

        @Override
        public void withdraw(Account account, double amount) {
            if (amount <= account.getBalance()) {
                account.setBalance(account.getBalance() - amount);
                log.info("Withdrawn " + amount + "$ from account " + account.getAccountNumber() + ". Current balance: " + account.getBalance() + "$");
            } else {
                log.info("Can't process withdraw. Amount: " + amount + " exceeds the account balance");
            }
        }
    },
    INACTIVE {
        @Override
        public void deposit(Account account, double amount) {
            log.info("Unable to deposit because account "  + account.getAccountNumber() + " is INACTIVE!");
            throw new AccountInactiveException("Account " + account.getAccountNumber() + " is INACTIVE.");
        }

        @Override
        public void withdraw(Account account, double amount) {
            log.info("Unable to withdraw because account "  + account.getAccountNumber() + " is INACTIVE!");
            throw new AccountInactiveException("Account " + account.getAccountNumber() + " is INACTIVE.");
        }
    };
}
```

**Client**: Clientul este responsabil pentru crearea contextului și stabilirea stării sale inițiale. Clientul interacționează cu contextul pentru a declanșa un comportament specific stării.
In cazul proiectului, **clientul** este [**Concrete Command**](#concrete-command) din modelul precedent, adica **Deposit Trasaction** sau **WithdrawalTransaction**.

## 3.3 Circuit Breaker
_________________________________________________________
Un **Circuit Breaker** este un model de proiectare utilizat în dezvoltarea de software pentru a îmbunătăți fiabilitatea și toleranța la erori a unui sistem, inclusiv a aplicațiilor Spring Boot. Scopul principal al unui întrerupător este acela de a împiedica un sistem să încerce în mod continuu să execute o operațiune care este probabil să eșueze, ceea ce poate duce la defecțiuni în cascadă și la degradarea în continuare a întregului sistem. Când este implementat un întrerupător, acesta monitorizează starea unei anumite operațiuni sau serviciu. Dacă operațiunea eșuează dincolo de un anumit prag, întrerupătorul „se deschide”, izolând componenta defectă de apeluri ulterioare. Acest lucru previne ca defecțiunea să afecteze întregul sistem. În cazul aplicației mele, întrerupătorul izolează API-ul de baza de date în cazul în care se blochează sau pierde conexiunea cu aplicația.
Circuit Breaker-ul folosit este din libraria **resilience4j**.

```java
    @CircuitBreaker(name = MY_SQL_CIRCUIT_BREAKER)
    public Set<Account> getAllAccounts() {
        return accountRepository.findAllAccounts();
    }
```
Cand Circuit Breaker se deschide atunci este aruncata o exceptie **CallNotPermittedException** care poate fi prelucrata (to handle).
Prin intermediul @ExceptionHandler am setat o metoda care va returna clientului **503 SERVICE_UNAVAILABLE.** panca cand componenta bazei de date isi va reveni.

```java
@Slf4j
@ControllerAdvice
public class OnlineBankSystemExceptionHandler {
    
    @ExceptionHandler({CallNotPermittedException.class})
    public ResponseEntity<Object> handleCallNotPermittedException(final CallNotPermittedException e,
                                                                  final HttpServletRequest request) {
        log.error("Call not permitted: Exception: " + e + ". Request: " + request);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE ).build();
    }
}
```