package io.dev.concertreservationsystem.domain.data_platform;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataPlatformService {

    private final DataPlatformRepository dataPlatformRepository;

    public void sendData(String message) {
        dataPlatformRepository.sendData(message) ;
    }
}
