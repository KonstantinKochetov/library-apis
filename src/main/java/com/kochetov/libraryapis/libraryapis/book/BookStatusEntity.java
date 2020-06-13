package com.kochetov.libraryapis.libraryapis.book;

import javax.persistence.*;

@Entity
@Table(name = "BOOK_STATUS")
public class BookStatusEntity {

//    We don't generate it (no annotation) because it is basically a foreign key in book table (look at sql script)
    @Column(name = "Book_Id")
    @Id
    private int bookId;

    @Column(name = "State")
    @Enumerated(EnumType.STRING)
    private BookStatusState state;

    @Column(name = "Total_Number_Of_Copies")
    private int totalNumberOfCopies;

    @Column(name = "Number_Of_Copies_Issued")
    private int numberOfCopiesIssued;

    // Handling two tables
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Book_Id", nullable = false)
    private BookEntity bookEntity;

    public BookStatusEntity() {
    }

    public BookStatusEntity(int bookId) {
        this.bookId = bookId;
    }

    public BookStatusEntity(int bookId, BookStatusState state, int totalNumberOfCopies, int numberOfCopiesIssued) {
        this.bookId = bookId;
        this.state = state;
        this.totalNumberOfCopies = totalNumberOfCopies;
        this.numberOfCopiesIssued = numberOfCopiesIssued;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public BookStatusState getState() {
        return state;
    }

    public void setState(BookStatusState state) {
        this.state = state;
    }

    public int getTotalNumberOfCopies() {
        return totalNumberOfCopies;
    }

    public void setTotalNumberOfCopies(int totalNumberOfCopies) {
        this.totalNumberOfCopies = totalNumberOfCopies;
    }

    public int getNumberOfCopiesIssued() {
        return numberOfCopiesIssued;
    }

    public void setNumberOfCopiesIssued(int numberOfCopiesIssued) {
        this.numberOfCopiesIssued = numberOfCopiesIssued;
    }

    public BookEntity getBookEntity() {
        return bookEntity;
    }

    public void setBookEntity(BookEntity bookEntity) {
        this.bookEntity = bookEntity;
    }
}
