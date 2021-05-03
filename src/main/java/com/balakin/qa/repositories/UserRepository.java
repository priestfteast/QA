package com.balakin.qa.repositories;


import com.balakin.qa.domain.User;
import org.springframework.data.repository.CrudRepository;


import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

        Optional<User> findByLogin(String login);

}
