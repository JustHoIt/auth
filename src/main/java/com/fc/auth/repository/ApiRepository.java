package com.fc.auth.repository;

import com.fc.auth.model.entity.Api;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiRepository extends JpaRepository<Api, Long> {
    Api findByMethodAndPath(String method, String path);
}
