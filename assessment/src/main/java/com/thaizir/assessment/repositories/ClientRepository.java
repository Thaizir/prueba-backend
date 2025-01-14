package com.thaizir.assessment.repositories;

import com.thaizir.assessment.models.Client;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findByEmail(String clientEmail);
}
