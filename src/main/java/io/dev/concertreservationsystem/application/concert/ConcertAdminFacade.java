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

            List<ConcertAdminDTOResult> concertAdminDTOResultList = ConcertBasicDTOResult.convertToConcertAdminDTOResultList(concertBasicDTOResultList);

            return concertAdminDTOResultList;
        */
    }

    public void findConcertBasicsByStartDateAndEndDate(ConcertAdminDTOParam concertAdminDTOParam) {
    /*
        // concertAdminDTOParam을 concertBasicDTOParam으로 변환
        ConcertBasicDTOParam concertBasicDTOParam = concertAdminDTOParam.convertToConcertBasicDTOParam();

        List<ConcertBasicDTOResult> concertBasicDTOResultList = concertBasicService.findConcertBasicsByStartDateAndEndDate(concertBasicDTOParam);

        List<ConcertAdminDTOResult> concertAdminDTOResultList = ConcertBasicDTOResult.convertToConcertAdminDTOResultList(concertBasicDTOResultList);

        return concertAdminDTOResultList;
     */
    }
}
