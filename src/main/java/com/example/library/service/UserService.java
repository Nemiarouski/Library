package com.example.library.service;

import com.example.library.exception.NotFoundException;
import com.example.library.model.Reader;
import com.example.library.repository.ReaderRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);
    private final ReaderRepository readerRepository;

    public String getUserLogin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public Long getUserId() {
        Optional<Reader> reader = readerRepository.findByLogin(getUserLogin());
        if (reader.isPresent()) {
            return reader.get().getId();
        } else {
            logger.info("Unknown user.");
            throw new NotFoundException();
        }
    }
}