package br.com.sizer.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import java.util.List;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;

import br.com.sizer.model.User;

@Service

public interface UserService {

    public Optional<User> findOne(String email);

    public List<String> findAll();

    // public Page<User> findAll(Pageable pageable);

    public User create(User user);

    public User update(Long id, User userDetails);

    public void delete(Long id);

}
