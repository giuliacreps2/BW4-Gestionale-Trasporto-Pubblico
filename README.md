
```

        // ****** DAO
                
        // distingui tipi di utente diversi
        // un tipo di utente può essere amministratore o utente
        // 
        // PRIVILEGI USER:
        //      amministratore
        //      utente
        
        // --------------------------------------------------------
        //  FUNZIONALITA       |   UTENTE    |   AMMINISTRATORE
        // --------------------------------------------------------
        //  crea un biglietto        SI             NO
        // -----------------------------------------------------------
        //  gestire biglietto        NO             SI 
        // -----------------------------------------------------------
        //  creare abbonamento       SI             NO
        // -----------------------------------------------------------
        //  gestire abbonamento      NO             SI
        // -----------------------------------------------------------
        //  creare punto di          NO             SI 
        //        emissione
        // -----------------------------------------------------------
        //  creare tessera           NO             SI
        // -----------------------------------------------------------
        //  statistiche 
        //      biglietti            NO             SI
        // -----------------------------------------------------------
        // statistiche 
        //      abbonamenti          NO             SI  
        // -----------------------------------------------------------
        // statistiche 
        //    punti vendita          NO             SI
        // -----------------------------------------------------------
        //  statistiche              NO             SI
        //      mezzo                       
        // -----------------------------------------------------------
        //  ottieni tempo
        //       percorrenza         NO              SI
        // -----------------------------------------------------------
        //  ottieni zona arrivo      SI              SI
        //         /partenza
        // -----------------------------------------------------------
        //  verifica se un mezzo    SI                SI 
        //      è in servizio
        // -----------------------------------------------------------
        //  verifica se un mezzo    NO                SI 
        //      è in manutenzione
        // -----------------------------------------------------------
        //  verificare se 
        //      distributore        SI                SI 
        //      è in servizio
        // -----------------------------------------------------------
        //  trova i mezzi           NO                SI 
        //     dell'azienda   
        // -----------------------------------------------------------


        
        // SIGNIFICA:
        //      gestire biglietto = update, cambiare attributi, delete
        //      gestire abbonamento = update, cambiare attributi, delete
        //      statistiche mezzo = dato un mezzo, calcola il tempo medio effettivo di percorrenza
        //      trova i mezzi dell'azienda = trova tutti i mezzi dell'azienda (quali, non quanti, quindi una lista)
                        
        
```