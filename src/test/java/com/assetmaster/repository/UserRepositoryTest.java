package com.assetmaster.repository;

import com.assetmaster.model.UserModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown(){
        userRepository.deleteAll();
    }
    @Test
    void shouldReturnEmptyWhenEmailNotPresent() {
        //given
        String email = "shreeja@gmail.com";
        //when
        Optional<UserModel> result = userRepository.findByEmail(email);
        //then
        assertThat(result).isEmpty();
    }
    @Test
    void shouldReturnUserWhenEmailPresent() {
        //given
        UserModel user = new UserModel();
        user.setUserID("USR123");
        user.setUserName("Test");
        user.setEmail("test@gmail.com");
        user.setPassword("Test@123");
        user.setCreatedDate(LocalDateTime.now());
        user.setLastModified(LocalDateTime.now());
        userRepository.save(user);

        //when
        Optional<UserModel> result = userRepository.findByEmail(user.getEmail());
        //then
        assertThat(result).isNotEmpty();
    }
}