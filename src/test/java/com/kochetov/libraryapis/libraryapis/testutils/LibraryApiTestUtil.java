package com.kochetov.libraryapis.libraryapis.testutils;

import com.kochetov.libraryapis.libraryapis.author.Author;
import com.kochetov.libraryapis.libraryapis.author.AuthorEntity;
import com.kochetov.libraryapis.libraryapis.book.*;
import com.kochetov.libraryapis.libraryapis.model.comon.Gender;
import com.kochetov.libraryapis.libraryapis.publisher.Publisher;
import com.kochetov.libraryapis.libraryapis.publisher.PublisherEntity;
import com.kochetov.libraryapis.libraryapis.user.User;
import com.kochetov.libraryapis.libraryapis.user.UserEntity;

import java.time.LocalDate;
import java.util.Optional;

public class LibraryApiTestUtil {

    private static int userCtr;
    private static int bookCtr;

    public static Publisher createPublisher() {
        return new Publisher(null, TestConstants.TEST_PUBLISHER_NAME,
                TestConstants.TEST_PUBLISHER_EMAIL,
                TestConstants.TEST_PUBLISHER_PHONE);
    }

    public static PublisherEntity createPublisherEntity() {
        return new PublisherEntity(TestConstants.TEST_PUBLISHER_NAME,
                TestConstants.TEST_PUBLISHER_EMAIL,
                TestConstants.TEST_PUBLISHER_PHONE);
    }

    public static Optional<PublisherEntity> createPublisherEntityOptional() {
        return Optional.of(createPublisherEntity());
    }

    public static Author createAuthor() {
        return new Author(null, TestConstants.TEST_AUTHOR_FIRST_NAME,
                TestConstants.TEST_AUTHOR_LAST_NAME, LocalDate.now().minusYears(30), Gender.Female);
    }

    public static AuthorEntity createAuthorEntity() {
        return new AuthorEntity(TestConstants.TEST_AUTHOR_FIRST_NAME,
                TestConstants.TEST_AUTHOR_LAST_NAME, LocalDate.now().minusYears(30), Gender.Female);
    }

    public static Optional<AuthorEntity> createAuthorEntityOptional() {
        return Optional.of(createAuthorEntity());
    }

    public static User createUser(String username) {


        return new User(username + userCtr++, TestConstants.TEST_USER_FIRST_NAME,
                TestConstants.TEST_USER_LAST_NAME, LocalDate.now().minusYears(30), Gender.Female, TestConstants.TEST_USER_PHONE,
                username + userCtr + "@email.com");

    }

    public static UserEntity createUserEntity(String username) {
        UserEntity be = new UserEntity(username, TestConstants.TEST_USER_PASSWORD, TestConstants.TEST_USER_FIRST_NAME,
                TestConstants.TEST_USER_LAST_NAME, LocalDate.now().minusYears(20), TestConstants.TEST_USER_GENDER,
                TestConstants.TEST_USER_PHONE, TestConstants.TEST_USER_EMAIL, "USER");
        return be;
    }

    public static Optional<UserEntity> createUserEntityOptional(String username) {
        return Optional.of(createUserEntity(username));
    }

    public static BookEntity createBookEntity() {
        BookEntity be = new BookEntity(TestConstants.TEST_BOOK_ISBN, TestConstants.TEST_BOOK_TITLE,
                TestConstants.TEST_BOOK_YEAR_PUBLISHED, TestConstants.TEST_BOOK_EDITION);
        be.setPublisher(createPublisherEntity());
        be.setBookStatus(createBookStatusEntity(0));
        return be;
    }

    public static BookStatusEntity createBookStatusEntity(int bookId) {
        return new BookStatusEntity(bookId, BookStatusState.Active, 3, 0);
    }

    public static Book createBook(int publisherId) {

        bookCtr++;

        // Create a new Book object
        return new Book(TestConstants.TEST_BOOK_ISBN + bookCtr,
                TestConstants.TEST_BOOK_TITLE + "-" + bookCtr, publisherId,
                TestConstants.TEST_BOOK_YEAR_PUBLISHED, TestConstants.TEST_BOOK_EDITION, createBookStatus());

    }

    public static BookStatus createBookStatus() {
        return new BookStatus(BookStatusState.Active, 3, 0);
    }

    public static Optional<BookEntity> createBookEntityOptional() {
        return Optional.of(createBookEntity());
    }
}
