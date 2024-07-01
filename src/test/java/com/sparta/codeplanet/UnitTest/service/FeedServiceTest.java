package com.sparta.codeplanet.UnitTest.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.sparta.codeplanet.product.repository.UserRepository;
import com.sparta.codeplanet.product.repository.feed.FeedRepository;
import com.sparta.codeplanet.product.service.FeedService;
import com.sparta.codeplanet.product.service.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Incubating;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class FeedServiceTest {

    @Incubating
    private FeedService feedService;

    @Mock
    private FeedRepository feedRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    private FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .build();
}