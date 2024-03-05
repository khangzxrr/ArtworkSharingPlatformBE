package com.github.khangzxrr.repository;

import com.github.khangzxrr.domain.RequestChat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestChatRepository extends JpaRepository<RequestChat, Long> {
    List<RequestChat> findAllByRequestId(long requestId);
    List<RequestChat> findAllByIdGreaterThanAndRequestId(long id, long requestId);
}
