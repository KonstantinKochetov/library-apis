package com.kochetov.libraryapis.libraryapis.publisher;

import com.kochetov.libraryapis.libraryapis.publisher.exception.LibraryResourceAlreadyExistException;
import com.kochetov.libraryapis.libraryapis.publisher.exception.LibraryResourceNotFoundException;
import com.kochetov.libraryapis.libraryapis.util.LibraryApiUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/publishers")
public class PublisherController {

    private PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping(path = "/{publisherId}")
    public ResponseEntity<?> getPublisher(@PathVariable Integer publisherId,
                                          @RequestHeader(value = "Trace-Id", defaultValue = "") String traceId) {

        if (!LibraryApiUtils.doesStringValueExist(traceId)) {
            traceId = UUID.randomUUID().toString();
        }

        Publisher publisher = null;
        try {
            publisher = publisherService.getPublisher(publisherId, traceId);
        } catch (LibraryResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }

    @PutMapping(path = "/{publisherId}")
    public ResponseEntity<?> updatePublisher(@PathVariable Integer publisherId, @Valid @RequestBody Publisher publisher,
                                             @RequestHeader(value = "Trace-Id", defaultValue = "") String traceId) {
        if (!LibraryApiUtils.doesStringValueExist(traceId)) {
            traceId = UUID.randomUUID().toString();
        }

        try {
            publisher.setPublisherId(publisherId);
            publisherService.updatePublisher(publisher, traceId);
        } catch (LibraryResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addPublisher(@Valid @RequestBody Publisher publisher,
                                          @RequestHeader(value = "Trace-Id", defaultValue = "") String traceId) {

        if (!LibraryApiUtils.doesStringValueExist(traceId)) {
            traceId = UUID.randomUUID().toString();
        }

        try {
            publisherService.addPublisher(publisher, traceId);
        } catch (LibraryResourceAlreadyExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(publisher, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{publisherId}")
    public ResponseEntity<?> deletePublisher(@PathVariable Integer publisherId,
                                             @RequestHeader(value = "Trace-Id", defaultValue = "") String traceId) {
        if (!LibraryApiUtils.doesStringValueExist(traceId)) {
            traceId = UUID.randomUUID().toString();
        }

        try {
            publisherService.deletePublisher(publisherId, traceId);
        } catch (LibraryResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<?> searchPublisher(@RequestParam String name,
                                             @RequestHeader(value = "Trace-Id", defaultValue = "") String traceId) {
        if (!LibraryApiUtils.doesStringValueExist(name)) {
            return new ResponseEntity<>("Please enter a name to search Publisher", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(publisherService.searchPublisher(name), HttpStatus.OK);
    }
}
