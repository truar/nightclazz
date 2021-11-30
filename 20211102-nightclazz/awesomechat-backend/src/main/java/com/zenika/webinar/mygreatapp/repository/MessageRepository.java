package com.zenika.webinar.mygreatapp.repository;

import com.zenika.webinar.mygreatapp.model.Message;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;

/*
 * TODO-04 : Make this class an instant Repository
 */
public interface MessageRepository extends DatastoreRepository<Message, Long> {

    Iterable<Message> findAllByOrderByCreationDateDesc();
}
