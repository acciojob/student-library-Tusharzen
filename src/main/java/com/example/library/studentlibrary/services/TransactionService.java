package com.example.library.studentlibrary.services;

import com.example.library.studentlibrary.models.*;
import com.example.library.studentlibrary.repositories.BookRepository;
import com.example.library.studentlibrary.repositories.CardRepository;
import com.example.library.studentlibrary.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    BookRepository bookRepository5;

    @Autowired
    CardRepository cardRepository5;

    @Autowired
    TransactionRepository transactionRepository5;

    @Value("${books.max_allowed}")
    int max_allowed_books;

    @Value("${books.max_allowed_days}")
    int getMax_allowed_days;

    @Value("${books.fine.per_day}")
    int fine_per_day;

    public String issueBook(int cardId, int bookId) throws Exception {
        //check whether bookId and cardId already exist
        //conditions required for successful transaction of issue book:
        //1. book is present and available
        // If it fails: throw new Exception("Book is either unavailable or not present");
        //2. card is present and activated
        // If it fails: throw new Exception("Card is invalid");
        //3. number of books issued against the card is strictly less than max_allowed_books
        // If it fails: throw new Exception("Book limit has reached for this card");
        //If the transaction is successful, save the transaction to the list of transactions and return the id

        //Note that the error message should match exactly in all cases

        Transaction transaction = new Transaction() ;
        if(cardRepository5.existsById(cardId) && bookRepository5.existsById(bookId))
        {
            Book book = bookRepository5.findById(bookId).get();
            Card card = cardRepository5.findById(cardId).get();
            if(book.isAvailable())
            {
                if(card.getCardStatus() == CardStatus.ACTIVATED)
                {
                    List<Book> booklist = card.getBooks();
                    if(booklist.size()< max_allowed_books)
                    {
                        booklist.add(book) ;
                        card.setBooks(booklist);
                        book.setAvailable(false);
                        transaction.setBook(book);
                        transaction.setCard(card);
                        transaction.setIssueOperation(true);
                        transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
                        transactionRepository5.save(transaction) ;
                    }
                    else
                    {throw new Exception("Book limit has reached for this card");}
                }
                else
                {throw new Exception("Card is invalid");}
            }
            else{throw new Exception("Book is either unavailable or not present");}
        }

       return transaction; //return transactionId instead
    }

    public Transaction returnBook(int cardId, int bookId) throws Exception{

        List<Transaction> transactions = transactionRepository5.find(cardId, bookId,TransactionStatus.SUCCESSFUL, true);
        Transaction transaction = transactions.get(transactions.size() - 1);

        Book book = bookRepository5.findById(bookId).get();
        Card card = cardRepository5.findById(cardId).get() ;

        Transaction returnTransaction = new Transaction() ;
        returnTransaction.setBook(book);
        returnTransaction.setCard(card);
        returnTransaction.setIssueOperation(false);
        returnTransaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        book.setAvailable(true);

        List<Book> booklist = card.getBooks();
        for(Book b:booklist)
        {
            if(b.getId()== bookId)
            booklist.remove(b) ;
            break ;
        }

        transactionRepository5.save(returnTransaction);




        //for the given transaction calculate the fine amount considering the book has been returned exactly when this function is called
        //make the book available for other users
        //make a new transaction for return book which contains the fine amount as well

        Transaction returnBookTransaction  = returnTransaction;
        return returnBookTransaction; //return the transaction after updating all details
    }
}