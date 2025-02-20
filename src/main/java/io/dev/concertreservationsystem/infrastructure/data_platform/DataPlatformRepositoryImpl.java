package io.dev.concertreservationsystem.infrastructure.data_platform;

import io.dev.concertreservationsystem.domain.data_platform.DataPlatformRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class DataPlatformRepositoryImpl implements DataPlatformRepository {

    @Override
    public void sendData(String message) {
        log.debug("message : ", message);
    }


}
