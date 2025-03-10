package com.fc.auth.repository;

import com.fc.auth.model.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {

    AppRole findByAppIdAndApiId(Long appId, Long apiId);
}
