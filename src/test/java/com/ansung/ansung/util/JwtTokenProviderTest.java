package com.ansung.ansung.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.ansung.ansung.utils.JwtTokenProvider;

@SpringBootTest
class JwtTokenProviderTest {
    @Test
    @DisplayName("토큰 생성 테스트")
    void createAccessTokenTest() {
        // given
        String payload = "testUser";

        // when
        String token = JwtTokenProvider.createAcessToken(payload);

        // then
        assertThat(token).isNotNull();
        System.out.println("Generated Token: " + token);
    }

    @Test
    @DisplayName("토큰 검증 테스트")
    void decodeAccessTokenTest(){
        // given
        String payload = "testUser";
        String token = JwtTokenProvider.createAcessToken(payload);

        // when
        String decodedPayload = JwtTokenProvider.decodeAcessToken(token);

        // then
        assertThat(decodedPayload).isEqualTo(payload);
    }
}
