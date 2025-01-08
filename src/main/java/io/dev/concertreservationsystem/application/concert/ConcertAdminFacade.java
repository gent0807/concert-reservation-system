package io.dev.concertreservationsystem.application.concert;

import io.dev.concertreservationsystem.domain.concert_basic.ConcertBasicDTOParam;
import io.dev.concertreservationsystem.domain.concert_basic.ConcertBasicDTOResult;
import io.dev.concertreservationsystem.domain.concert_basic.ConcertBasicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConcertAdminFacade {

    private final ConcertBasicService concertBasicService;

    public void findAllConcertBasics() {
       /*
            List<ConcertBasicDTOResult> concertBasicDTOResultList = concertBasicService.findAllConcertBasics();

            return ConcertBasicDTOResult.convertToConcertAdminDTOResultList(concertBasicDTOResultList);
        */
    }

    public void findConcertBasicsByStartDateAndEndDate(ConcertAdminDTOParam concertAdminDTOParam) {
    /*

        List<ConcertBasicDTOResult> concertBasicDTOResultList = concertBasicService.findConcertBasicsByStartDateAndEndDate(concertAdminDTOParam.convertToConcertBasicDTOParam());

        return ConcertBasicDTOResult.convertToConcertAdminDTOResultList(concertBasicDTOResultList);
     */
    }
}
