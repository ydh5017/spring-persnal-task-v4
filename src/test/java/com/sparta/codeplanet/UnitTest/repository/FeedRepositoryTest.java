package com.sparta.codeplanet.UnitTest.repository;

import com.sparta.codeplanet.config.TestConfig;
import com.sparta.codeplanet.product.dto.PageDTO;
import com.sparta.codeplanet.product.entity.Feed;
import com.sparta.codeplanet.product.entity.User;
import com.sparta.codeplanet.product.repository.UserRepository;
import com.sparta.codeplanet.product.repository.feed.FeedRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
public class FeedRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void getLikeFeeds() {
        // given
        User user = userRepository.findById(1L).orElse(null);
        PageDTO pageDTO = PageDTO.builder()
                .page(1)
                .size(5)
                .build();

        // when
        Page<Feed> feeds = feedRepository.getLikeFeeds(user, pageDTO.toPageable());

        // then
        assertEquals(feeds.getSize(), 5);
    }
}
