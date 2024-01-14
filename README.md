# 1. Introducere

    Proiectul presupune implementarea unei applicatii "Bank System" si demonstrarea aplicarii modelelor de proiectare in dezvoltarea de software.

## 1.1 Putin despre business-ul aplicatiei
    Sa implementat unui sistem bancar in care folosind endoint-uri (doar implementare back-end, nu front-end) se poate efectua operatiuni CRUD (Creare, Read, Update, Delete) asupra unui cont bancar care descrie numarul contului (accountNumber) , soldul (balance) si starea (state) acestuia. Pe langa aceasta, se poate efectua tranzactii de depunere (deposit) si retragere (withdrawal) asupra contului respectiv. Tranzactiile se pot efectua doar in cazul in care contul bancar este activ (state=ACTIVE).

# 2. Stack-ul de Tehnologii

    - Java 17
    - Spring Boot Framework
    - Maven
    - MySQL DataBase
    - Postman

    - Am ales limbajul Java 17 pentru ca este usor de implemntat si versiunea sa este Long-Term-Suport (LTS), adica aceasta versiune primeste asistenta si actualizari pe termen lung din partea comunitatii Java. Conceptul de LTS este deosebit de important pentru intreprinderi si dezvoltatori care construiesc si intretin aplicatii care necesita stabilitate si suport pe termen lung.
    - Spring Boot Framework are urmatoarele avantaje:
        a. ofera posibilitatea de a construi o aplicatie pe layere: Controller, Service si Repository fiecare strat avand rolul sau;
        b. elimina nevoia de configurare extensiva, acest lucru facand ca dezvoltarea sa fie mai rapida si mai usoara;
        c. faciliteaza gestionarea dependentelor prin intermediul Maven si Gradle;
        d. se integreaza usor cu alte module Spring cum ar fi (Spring Data, Security, Cloud) oferind solutie comprehensiva pentru diverse nevoie de dezvoltare
    - MySQL: este un sistem de gestionare a bazelor de date relationale (RDBMS - Relational Database Management System) open-source, care ofera o platforma eficienta si fiabila pentru stocarea si gestionarea datelor.
    - Postman: cu acest tool pot efectua operatiuni de request si analiza a raspunsului.

# 3. Modele de proiectare

    3.1. Command
    Modelul de desing "Command" este un model de proiectare comportamentala care transforma o solicitare intr-un obiect, permitand clientilor sa parametrizeze clientii cu diferite solicitari, solicitari in coada si sa suporte operatiuni anulabile. Separa expeditorul si destinatarul unei cereri, incapsuland o cerere ca obiect. Elementele principale ale modelului de comanda sunt: 
    Command Interface (abstract class): Aceasta interfata are de obicei o referinta la obiectul asupra caruia ar trebui efectuata actiunea.
    ```java
    public abstract class Transaction {

        protected Account account;

        protected Double amount;

        protected LocalDateTime dateTimeTransaction;

        abstract void execute();
    }
    ```
    Concrete Command: Implementeaza interfata de comanda si specifica legatura dintre o comanda si obiectul care executa comanda.
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
    
    Receiver: Stie cum sa efectueze operatia asociata comenzii. Este tinta comenzii si contine implementarea efectiva a actiunii.
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
    Invoker: Solicita comenzii sa execute cererea. Contine o referinta la obiectul de comanda si declanseaza executia comenzii.
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
    