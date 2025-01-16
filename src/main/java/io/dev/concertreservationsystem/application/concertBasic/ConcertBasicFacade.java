package io.dev.concertreservationsystem.application.concertBasic;

import io.dev.concertreservationsystem.domain.concert_basic.ConcertBasicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConcertBasicFacade {

    private final ConcertBasicService concertBasicService;

    public void findAllConcertBasics() {
       /*
            List<ConcertBasicDTOResult> concertBasicDTOResultList = concertBasicService.findAllConcertBasics();

            return ConcertBasicDTOResult.convertToConcertAdminDTOResultList(concertBasicDTOResultList);
        */
    }

    public void findConcertBasicsByStartDateAndEndDate(ConcertBasicFacadeDTOParam concertBasicFacadeDTOParam) {
    /*

        List<ConcertBasicDTOResult> concertBasicDTOResultList = concertBasicService.findConcertBasicsByStartDateAndEndDate(concertBasicFacadeDTOParam.convertToConcertBasicDTOParam());

        return ConcertBasicDTOResult.convertToConcertAdminDTOResultList(concertBasicDTOResultList);
     */
    }
}
