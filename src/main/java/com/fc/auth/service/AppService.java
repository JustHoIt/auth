package com.fc.auth.service;

import com.fc.auth.model.entity.App;
import com.fc.auth.repository.AppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppService {

    private final AppRepository appRepository;

    public List<App> listApps() {
        return appRepository.findAll();
    }
}
