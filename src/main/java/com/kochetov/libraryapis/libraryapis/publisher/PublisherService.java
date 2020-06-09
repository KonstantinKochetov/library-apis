package com.kochetov.libraryapis.libraryapis.publisher;

import com.kochetov.libraryapis.libraryapis.publisher.exception.LibraryResourceAlreadyExistException;
import com.kochetov.libraryapis.libraryapis.publisher.exception.LibraryResourceNotFoundException;
import com.kochetov.libraryapis.libraryapis.util.LibraryApiUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublisherService {

    private PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public void addPublisher(Publisher publisherToBeAdded) throws LibraryResourceAlreadyExistException {

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
    }

    public Publisher getPublisher(Integer publisherId) throws LibraryResourceNotFoundException {
        Optional<PublisherEntity> publisherEntity = publisherRepository.findById(publisherId);
        Publisher publisher = null;

        if (publisherEntity.isPresent()) {
            PublisherEntity pe = publisherEntity.get();
            publisher = createPublisherFromEntity(pe);
        } else {
            throw new LibraryResourceNotFoundException("Publisher Id: " + publisherId + "Not Found");
        }

        return publisher;
    }

    public void updatePublisher(Publisher publisherToBeUpdated) throws LibraryResourceNotFoundException {
        Optional<PublisherEntity> publisherEntity = publisherRepository.findById(publisherToBeUpdated.getPublisherId());
        Publisher publisher = null;

        if (publisherEntity.isPresent()) {
            PublisherEntity pe = publisherEntity.get();
            if (LibraryApiUtils.doesStringValueExist(publisherToBeUpdated.getEmailId())) {
                pe.setEmailId(publisherToBeUpdated.getEmailId());
            }
            if (LibraryApiUtils.doesStringValueExist(publisherToBeUpdated.getPhoneNumber())) {
                pe.setPhoneNumber(publisherToBeUpdated.getPhoneNumber());
            }
            publisherRepository.save(pe);
        } else {
            throw new LibraryResourceNotFoundException("Publisher Id: " + publisherToBeUpdated.getPublisherId() + "Not Found");
        }

    }

    public void deletePublisher(Integer publisherId) throws LibraryResourceNotFoundException {
        try {
            publisherRepository.deleteById(publisherId);
        } catch (EmptyResultDataAccessException e) {
            throw new LibraryResourceNotFoundException("Publisher Id: " + publisherId + "Not Found");
        }
    }

    public List<Publisher> searchPublisher(String name) {
        List<PublisherEntity> publisherEntities = null;
        if (LibraryApiUtils.doesStringValueExist(name)) {
            publisherEntities = publisherRepository.findByNameContaining(name);
        }
        if (publisherEntities != null && publisherEntities.size() > 0) {
            return createPublishersForSearchResponse(publisherEntities);
        } else {
            return Collections.emptyList();
        }
    }

    private List<Publisher> createPublishersForSearchResponse(List<PublisherEntity> publisherEntities) {
        return publisherEntities.stream()
                .map(this::createPublisherFromEntity)
                .collect(Collectors.toList());
    }

    private Publisher createPublisherFromEntity(PublisherEntity pe) {
        return new Publisher(pe.getPublisherId(), pe.getName(), pe.getEmailId(), pe.getPhoneNumber());
    }
}
