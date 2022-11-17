package com.aceliq.lucerne.data;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.aceliq.lucerne.model.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {

}
