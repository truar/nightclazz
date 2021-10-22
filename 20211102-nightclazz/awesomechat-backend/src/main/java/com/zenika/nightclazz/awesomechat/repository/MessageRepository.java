package com.zenika.nightclazz.awesomechat.repository;

import com.zenika.nightclazz.awesomechat.model.Message;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;

public interface MessageRepository extends DatastoreRepository<Message, Long> {

    Iterable<Message> findAllByOrderByCreationDateDesc();
}
