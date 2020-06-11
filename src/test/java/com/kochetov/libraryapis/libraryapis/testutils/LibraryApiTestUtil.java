package com.kochetov.libraryapis.libraryapis.testutils;

import com.kochetov.libraryapis.libraryapis.publisher.Publisher;
import com.kochetov.libraryapis.libraryapis.publisher.PublisherEntity;

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
}
