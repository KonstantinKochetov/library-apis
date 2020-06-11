package com.kochetov.libraryapis.libraryapis.publisher;

import com.kochetov.libraryapis.libraryapis.exception.LibraryResourceAlreadyExistException;
import com.kochetov.libraryapis.libraryapis.exception.LibraryResourceBadRequestException;
import com.kochetov.libraryapis.libraryapis.exception.LibraryResourceNotFoundException;
import com.kochetov.libraryapis.libraryapis.util.LibraryApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/publishers")
public class PublisherController {

    private static Logger logger = LoggerFactory.getLogger(PublisherController.class);

    private PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping(path = "/{publisherId}")
    public ResponseEntity<?> getPublisher(@PathVariable Integer publisherId,
                                          @RequestHeader(value = "Trace-Id", defaultValue = "") String traceId) throws LibraryResourceNotFoundException {

        if(!LibraryApiUtils.doesStringValueExist(traceId)) {
            traceId = UUID.randomUUID().toString();
        }

        return new ResponseEntity<>(publisherService.getPublisher(publisherId, traceId), HttpStatus.OK);
    }

    @PutMapping(path = "/{publisherId}")
    public ResponseEntity<?> updatePublisher(@PathVariable Integer publisherId, @Valid @RequestBody Publisher publisher,
                                             @RequestHeader(value = "Trace-Id", defaultValue = "") String traceId)
            throws LibraryResourceNotFoundException {

        if(!LibraryApiUtils.doesStringValueExist(traceId)) {
            traceId = UUID.randomUUID().toString();
        }

        logger.debug("Added TraceId: {}", traceId);

        publisher.setPublisherId(publisherId);
        publisherService.updatePublisher(publisher, traceId);

        logger.debug("Returning response for TraceId: {}", traceId);
        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addPublisher(@Valid @RequestBody Publisher publisher,
                                          @RequestHeader(value = "Trace-Id", defaultValue = "") String traceId) {

        logger.debug("Request to add Publisher: {}", publisher);
        if (!LibraryApiUtils.doesStringValueExist(traceId)) {
            traceId = UUID.randomUUID().toString();
        }
        try {
            publisherService.addPublisher(publisher, traceId);
        } catch (LibraryResourceAlreadyExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        logger.debug("Added TraceId: {}", traceId);

        logger.debug("Returning response for TraceId: {}", traceId);
        return new ResponseEntity<>(publisher, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{publisherId}")
    public ResponseEntity<?> deletePublisher(@PathVariable Integer publisherId,
                                             @RequestHeader(value = "Trace-Id", defaultValue = "") String traceId) throws LibraryResourceNotFoundException {
        if(!LibraryApiUtils.doesStringValueExist(traceId)) {
            traceId = UUID.randomUUID().toString();
        }

        logger.debug("Added TraceId: {}", traceId);

        publisherService.deletePublisher(publisherId, traceId);
        logger.debug("Returning response for TraceId: {}", traceId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<?> searchPublisher(@RequestParam String name,
                                             @RequestHeader(value = "Trace-Id", defaultValue = "") String traceId) throws LibraryResourceBadRequestException {
        if(!LibraryApiUtils.doesStringValueExist(traceId)) {
            traceId = UUID.randomUUID().toString();
        }

        if(!LibraryApiUtils.doesStringValueExist(name)) {
            logger.error("TraceId: {}, Please enter a name to search Publisher!!", traceId);
            throw new LibraryResourceBadRequestException(traceId, "Please enter a name to search Publisher.");
        }
        logger.debug("Returning response for TraceId: {}", traceId);
        return new ResponseEntity<>(publisherService.searchPublisher(name, traceId), HttpStatus.OK);
    }
}
