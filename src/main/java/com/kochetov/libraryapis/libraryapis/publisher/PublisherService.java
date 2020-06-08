package com.kochetov.libraryapis.libraryapis.publisher;

import com.kochetov.libraryapis.libraryapis.publisher.exception.LibraryResourceAlreadyExistException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

    private PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Publisher addPublisher(Publisher publisherToBeAdded) throws LibraryResourceAlreadyExistException {

        PublisherEntity publisherEntity = new PublisherEntity(
                publisherToBeAdded.getName(),
                publisherToBeAdded.getEmailId(),
                publisherToBeAdded.getPhoneNumber()
        );

        PublisherEntity addedPublisher = null;

        try {
            addedPublisher = publisherRepository.save(publisherEntity);
        } catch (DataIntegrityViolationException e) {
            throw new LibraryResourceAlreadyExistException("Publisher already exists!");
        }

        publisherToBeAdded.setPublisherId((addedPublisher.getPublisherId()));

        return publisherToBeAdded;

    }
}
