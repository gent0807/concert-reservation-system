
https://www.notion.so/Docs-16c4f425c08980a58d04c0cdf413f23c?pvs=4

| 요구사항ID | 요구사항명  | 기능ID | 기능명 | 요청 인자 제약 조건 | 필수 데이터 | 선택 데이터 | 동시성 고려 |
|--------| --- | --- | --- | --- | --- | --- | --- |
| MAIN01 | 콘서트_기본_정보_등록 | MAIN01_CONCERT_BASIC_CREATE_01 | 콘서트 기본 정보를 등록한다. | - 콘서트 장르 아이디(not null, not 공백 문자열, 콘서트 장르 아이디 테이블에 존재하는 아이디) - 콘서트 이름(not null, not 공백 문자열, 20자 이하의 문자열) | 콘서트 장르 아이디, 콘서트 이름 |  |  |
| MAIN01 | 콘서트_기본_정보_목록_조회 | MAIN01_CONCERT_BASIC_LIST_FIND_01 | 콘서트 기본 정보 목록을 조건에 맞게 조회한다. |  |  | 콘서트 장르 아이디, 콘서트 이름 |  |
| MAIN01 | 콘서트_기본_정보_상세_조회 | MAIN01_CONCERT_BASIC_FIND_01 | 콘서트 기본 정보를 상세 조회한다. | - 콘서트 기본 정보 아이디(not nul, not 공백 문자열, 1 이상의 자연수) | 콘서트 기본 정보 아이디 |  |  |
| MAIN01 | 콘서트_기본_정보_수정 | MAIN01_CONCERT_BASIC_PUT_UPDATE_01 | 콘서트 기본 정보를 선택 수정한다. | - 콘서트 기본 정보 아이디(not nul, not 공백 문자열, 1 이상의 자연수)- 콘서트 장르 아이디(not null, not 공백 문자열, 콘서트 장르 아이디 테이블에 존재하는 아이디)- 콘서트이름(not null, not 공백 문자열, 20자 이하의 문자열) | 콘서트 기본 정보 아이디, 콘서트 장르 아이디, 콘서트 이름  |  |  |
| MAIN01 | 콘서트_기본_정보_삭제 | MAIN01_CONCERT_BASIC_PATCH_DELETE_01 | 콘서트 기본 정보를 선택 삭제한다. | - 콘서트 기본 정보 아이디(not nul, not 공백 문자열, 1 이상의 자연수)- 콘서트 기본 정보 삭제일/ 삭제 시각(not null, not 공백 문자열, 오늘 날짜) | 콘서트 기본 정보 아이디, 콘서트 기본 정보 삭제일/ 삭제 시각 |  |  |
| MAIN02 | 콘서트_실제_공연_정보_등록 | MAIN02_CONCERT_DETAIL_CREATE_01 | 콘서트 실제 공연 정보를 등록한다. | - 콘서트 기본 정보 아이디(not nul, not 공백 문자열, 1 이상의 자연수)- 실제 공연 시작일/시작 시간(not null, not 공백 문자열, 오늘 날짜 이후로 3주 뒤부터 1년 뒤, 요청에 담긴 실제 공연 종료일/ 종료 시각보다 앞서야함)- 실제 공연 종료일/ 종료 시각(not null, not 공백 문자열, 요청에 담긴 실제 공연 시작일/시작 시각보다 뒤에 있어야함)- 콘서트 기본 정보 아이디, 콘서트 실제 공연 시작일/시작 시각, 콘서트 실제 공연 종료일/종료 시각 조합은 UNIQUE | 콘서트 기본 정보 아이디, 공연 시작일/시작 시각, 공연 종료일/종료 시각, 공연 정보 등록자 아이디 |  |  |
| MAIN02 | 콘서트_실제_공연_정보_목록_조회 | MAIN02_CONCERT_DETAIL_LIST_FIND_01 | 콘서트 실제 공연 정보 목록을 조건에 맞게 조회한다. |  |  | 콘서트 실제 공연 시작일/시작 시각과 콘서트 실제 공연 종료일/종료 시각, 콘서트 기본 정보 아이디 |  |
| MAIN02 | 콘서트_실제_공연_정보_상세_조회 | MAIN02_CONCERT_DETAIL_FIND_01 | 콘서트 실제 공연 정보를 선택 상세 조회한다. | - 콘서트 실제 공연 정보 아이디(not nul, not 공백 문자열, 1이상의 자연수)| 콘서트 실제 공연 정보 아이디 |  |  |
| MAIN02 | 콘서트_실제_공연_정보_수정 | MAIN02_CONCERT_DETAIL_PUT_UPDATE_01 | 콘서트_실제_공연_정보를 선택 수정한다. | - 콘서트 실제 공연 정보 아이디(not nul, not 공백 문자열, 1이상의 자연수)- 콘서트 기본 정보 아이디(not nul, not 공백 문자열, 1이상의 자연수)- 실제 공연 시작일/시작 시간(not null, not 공백 문자열, 오늘 날짜 이후로 3주 뒤부터 1년 뒤, 요청에 담긴 실제 공연 종료일/ 종료 시각보다 앞서야함)- 실제 공연 종료일/ 종료 시각(not null, not 공백 문자열, 요청에 담긴 실제 공연 시작일/시작 시각보다 뒤에 있어야함)- 콘서트 실제 공연 정보 등록자 아이디(콘서트 실제 공연 정보 등록자 아이디 테이블에 존재하는 값)- 콘서트 실제 공연 예약 가능 상태 아이디(콘서트 실제 공연 예약 가능 상태 테이블에 존재하는 아이디)- 콘서트 실제 공연 정보 생성일/생성 시각(not null, not 공백)- 콘서트 실제 공연 정보 수정일/수정 시각(not null, not 공백 문자열, 오늘 날짜, 생성일/생성 시각이 지난 날짜/시각)- 콘서트 기본 정보 아이디, 콘서트 실제 공연 시작일/시작 시각, 콘서트 실제 공연 종료일/종료 시각 조합은 UNIQUE | 콘서트 실제 공연 정보 아이디,콘서트 실제 공연 시작일/시작 시각과 콘서트 실제 공연 종료일/종료 시각, 콘서트 기본 정보 아이디, 콘서트 실제 공연 정보 등록자 아이디, 콘서트 실제 공연 예약 가능 상태 아이디, 콘서트 실제 공연 정보 생성일/생성 시각, 콘서트 실제 공연 정보 수정일/수정 시각 |  |  |
| MAIN02 | 콘서트_실제_공연_정보_삭제 | MAIN02_CONCERT_DETAIL_PATCH_DELETE_01 | 콘서트_실제_공연_정보를 선택 삭제한다. | - 콘서트 실제 공연 정보 아이디(not nul, not 공백 문자열, 1 이상의 자연수),- 콘서트 실제 공연 예약 가능 상태 아이디(콘서트 실제 공연 예약 가능 상태 테이블에 존재하면서 삭제 상태에 해당하는 아이디)- 콘서트 실제 공연 정보 삭제일/삭제 시각(not null, not 공백 문자열, 오늘 날짜) | 콘서트 실제 공연 정보 아이디,콘서트 실제 공연 예약 가능 상태 아이디,  콘서트 실제 공연 정보 삭제일/삭제 시각 |  |  |
| MAIN03 | 콘서트_실제_공연_좌석_정보_등록 | MAIN02_CONCERT_DETAIL_SEAT_CREATE_01 | 콘서트 실제 공연 좌석 정보를 등록한다. | - 콘서트 실제 공연 정보 아이디(not nul, not 공백 문자열, 1이상의 자연수)- 좌석 번호(not null, not 공백 문자열, 1~50에 해당하는 자연수)- 좌석 가겨(not null, not 공백 문자열, 10000이상의 자연수)- 콘서트 실제 공연 정보 아이디, 콘서트 실제 공연 좌석 번호 아이디 조합은 UNIQUE | 콘서트 실제 공연 정보 아이디,좌석 번호, 좌석 가격 |  |  |
| MAIN03 | 콘서트_실제_공연_좌석_정보_목록_조회 | MAIN03_CONCERT_DETAIL_SEAT_LIST_FIND_01 | 콘서트 실제 공연 좌석 정보 목록을 조건에 맞게 조회한다. |  |  | 콘서트 실제 공연 정보 아이디, 좌석 번호, 좌석 가격 |  |
| MAIN03 | 콘서트_실제_공연_좌석_정보_상세_조회 | MAIN03_CONCERT_DETAIL_SEAT_FIND_01 | 콘서트 실제 공연 좌석 정보를 선택 상세 조회한다. | 콘서트 실제 공연 좌석 정보 아이디(not nul, not 공백 문자열, 1 이상의 자연수) |  | 콘서트 실제 공연 좌석 정보 아이디 |  |
| MAIN03 | 콘서트_실제_공연_좌석_정보_수정 | MAIN03_CONCERT_DETAIL_SEAT_PUT_UPDATE_01 | 콘서트 실제 공연 좌석 정보를 선택 수정한다. | - 콘서트 실제 공연 좌석 정보 아이디(not nul, not 공백 문자열, 1이상의 자연수)- 콘서트 실제 공연 정보 아이디(not nul, not 공백 문자열, 1이상의 자연수)- 콘서트 실제 공연 좌석 번호(not nul, not 공백 문자열, 1 ~50의 자연수)- 콘서트 실제 공연 좌석 예약 가능 상태 아이디(콘서트 실제 공연 좌석 예약 가능 상태 테이블에 존재하는 아이디)- 콘서트 실제 공연 좌석 가격(not nul, not 공백 문자열, 10000 이상의 자연수)- 콘서트 실제 좌석 공연 정보 생성일/생성 시각(not null, not 공백 문자열,)- 콘서트 실제 좌석 공연 정보 수정일/수정 시각(not null, not 공백 문자열, 오늘 날짜,  생성일/생성 시각이 지난 날짜/시각)- 콘서트 실제 공연 정보 아이디, 콘서트 실제 공연 좌석 번호 아이디 조합은 UNIQUE | 콘서트_실제_공연_좌석_정보_아이디, 콘서트 실제 공연 정보 아이디,  콘서트 실제 공연 좌석 번호, 콘서트실제 공연 좌석 예약 가능 상태 아이디, 콘서트 실제 공연 좌석 가격, 실제 공연 좌석 정보 생성일/생성 시각, 실제 공연 좌석 정보 수정일/수정 시각 |  |  |
| MAIN03 | 콘서트_실제_공연_좌석_정보_삭제 | MAIN03_CONCERT_DETAIL_SEAT_PATCH_DELETE_01 | 콘서트 실제 공연 좌석 정보를 선택 삭제한다. | - 콘서트 실제 공연 좌석 정보 아이디(not nul, not 공백 문자열, 1 이상의 자연수),- 콘서트 실제 공연 좌석 예약 가능 상태 아이디(콘서트 실제 공연 좌석 예약 가능 상태 테이블에 존재하면서 삭제 상태에 해당하는 아이디)- 콘서트 실제 공연 좌석 정보 삭제일/삭제 시각(not null, not 공백 문자열, 오늘 날짜) | 콘서트 실제 공연 좌석 정보 아이디,콘서트 실제 공연 좌석 예약 가능 상태 아이디,  콘서트 실제 공연 좌석 정보 삭제일/삭제 시각 |  |  |
| MAIN04 | 콘서트_관리자_계정_정보_등록 | MAIN04_CONCERT_DETAIL_MANAGER_CREATE_01 | 콘서트_실제_공연_정보를 등록할 권한이 있는 관리자 계정을 등록한다. |  |  |  |  |
| MAIN04 | 콘서트_관리자_계정_목록_조회 | MAIN04_CONCERT_DETAIL_MANAGER_LIST_FIND_01 | 콘서트_실제_공연_정보를 등록할 권한이 있는 관리자 계정 목록을 조건에 맞게 조회한다. |  |  |  |  |
| MAIN04 | 콘서트_관리자_계정_상세_조회 | MAIN04_CONCERT_DETAIL_MANAGER_FIND_01 | 콘서트_실제_공연_정보를 등록할 권한이 있는 관리자 계정의 상세 정보를 선택 상세 조회한다. |  |  |  |  |
| MAIN04 | 콘서트_관리자_계정_정보_수정 | MAIN04_CONCERT_DETAIL_MANAGER_PUT_UPDATE_01 | 콘서트_실제_공연_정보를 등록할 권한이 있는 관리자 계정의 상세 정보를 선택 수정한다. |  |  |  |  |
| MAIN04 | 콘서트_관리자_계정_정보_삭제 | MAIN04_CONCERT_DETAIL_MANAGER_PATCH_DELETE_01 | 콘서트_실제_공연_정보를 등록할 권한이 있는 관리자 계정의 정보를 선택 삭제한다. |  |  |  |  |
| MAIN05 | 회원_계정_정보_등록 | MAIN05_MEMBER_CREATE_01 | 콘서트 예약, 콘서트 관람, 콘서트 후기 작성, 콘서트 평가 권한이 있는 회원 계정 정보를 등록한다. | 회원 계정 정보 아이디(UUID) | 회원 계정 정보 아이디(UUID) |  |  |
| MAIN05 | 회원_계정_목록_조회 | MAIN05_MEMBER_LIST_FIND_01 | 콘서트 예약, 콘서트 관람, 콘서트 후기 작성, 콘서트 평가 권한이 있는 회원 계정 목록을 조건에 맞게 조회한다. |  |  | 회원 나이, 회원 성별, 회원의 이름 |  |
| MAIN05 | 회원_계정_상세_조회 | MAIN05_MEMBER_FIND_01 | 콘서트 예약, 콘서트 관람, 콘서트 후기 작성, 콘서트 평가 권한이 있는 선택된 회원 계정 정보를 선택 상세 조회한다. | 회원 계정 정보 아이디(UUID) | 회원 계정 정보 아이디 |  |  |
| MAIN05 | 회원_계정_정보_수정 | MAIN05_MEMBER_PUT_UPDATE_01 | 콘서트 예약, 콘서트 관람, 콘서트 후기 작성, 콘서트 평가 권한이 있는 선택된  회원 계정 정보를 선택 수정한다. | • 회원 계정 정보를 수정하기 위해선, 회원 계정 정보 아이디, 회원의 나이, 회원의 이름, 회원의 잔고, 회원 계정 정보 생성일/ 생성 시각, 회원 계정 정보 수정일/수정 시각이 반드시 필요하다.• 회원의 나이가 null이거나, 공백 문자열이거나, 14 이상의 자연수가 아니면  IllegalArgumentException• 회원의 잔고가 0이상의 자연수가 아니면 IllegalArgumentException• 회원의 이름이 null이거나 공백 문자열이거나 길이가 30자 이상이면  IllegalArgumentException• 회원 계정 정보 아이디가 유효한 UUID가 아니면, IllegalArgumentException• 회원 정보 생성일/생성 시각이 null이거나, 공백 문자(열)이면 IllegalArgumentException(Custom)• 회원 계정 정보 수정일/수정 시각이 null이거나, 공백 문자(열)이거나  오늘 날짜가 아니거나 생성일/ 생성 시각 이전의 날짜/시각이면 IllegalArgumentException(Custom) | 회원 계정 정보 아이디, 회원의 나이, 회원의 이름, 회원의 잔고, 회원 계정 정보 생성일/ 생성 시각, 회원 계정 정보 수정일/수정 시각 |  |  |
| MAIN05 | 회원_계정_정보_삭제  | MAIN05_MEMBER_PATCH_DELETE_01 | 콘서트 예약, 콘서트 관람, 콘서트 후기 작성, 콘서트 평가 권한이 있는 선택된 회원 계정 정보를 선택 삭제한다. | • 회원 계정 정보를 삭제 수정하기 위해선, 회원 계정 정보 아이디, 회원 계정 정보 삭제일/삭제 시각이 반드시 필요하다.• 회원 계정 정보 아이디가 유효한 UUID가 아니면, IllegalArgumentException• 회원 계정 정보 삭제일/삭제 시각이 null이거나, 공백 문자(열)이거나  오늘 날짜가 아니면 IllegalArgumentException(Custom) | 회원 계정 정보 아이디, 회원 계정 정보 삭제일/삭제 시각 |  |  |
| MAIN06 | 좌석_예약_서비스_이용_회원_대기_토큰_정보_등록 | MAIN06_RESERVATION_SERVICE_WATING_TOKEN_CREATE_01 | 좌석 예약 서비스를 이용하기 위한 회원 대기 토큰 정보를 등록한다. | • 좌석_예약_서비스_이용_회원_대기_토큰_정보를 등록하기 위해선, 회원 계정 정보 아이디가 반드시 필요하다.• 회원 계정 정보 아이디가 UUID로써 유효하지 않으면 IllegalArgumentException |  회원 계정 정보 아이디 |  |  |
| MAIN06 | 좌석_예약_서비스_이용_회원_대기_토큰_정보_목록_조회 | MAIN06_RESERVATION_SERVICE_WATING_TOKEN_LIST_FIND_01 | 좌석 예약 서비스를 이용하기 위한 회원 대기 토큰 정보 목록을 조건에 맞게 조회한다. | • 좌석_예약_서비스_이용_회원_대기_토큰_정보_목록을 조회하기 위한 요청에는 회원 계정 정보 아이디,  대기 토큰 상태 아이디, 대기 토큰 생성일/생성 시각, 대기 토큰 만료일/만료 시각에 대한 인자값들이 담길 수 있다.• 위의 요청 인자 값들이 null이거나, 공백 문장(열)이거나, 1 이상의 자연수가 아닌 경우거나, 대기 토큰 상태 테이블 존재하지 않는 대기 토큰 상태 아이디인 경우거나 회원 계정 정보 아이디가 유효하지 않은 UUID인 경우엔 각각의 값들이 요청에 담겨있지 않은 것으로 하며, 예외를 발생시키진 않는다. |  | 회원 계정 정보 아이디,  대기 토큰 상태 아이디, 대기 토큰 생성일/생성 시각, 대기 토큰 만료일/만료 시각 |  |
| MAIN06 | 좌석_예약_서비스_이용_회원_대기_토큰 _정보_상세_조회 | MAIN06_RESERVATION_SERVICE_WATING_TOKEN_FIND_01 | 좌석 예약 서비스를 이용하기 위한 회원 대기 토큰 정보를 선택 상세 조회한다. | • 좌석_예약_서비스_이용_회원_대기_토큰_정보를 상세 조회하기 위해선, 회원_대기_토큰_정보 아이디가 반드시 필요하다.• 요청에 담긴 대기 토큰 정보 아이디가  null이거나, 공백 문자(열)이거나 1 이상의 자연수가 아니면,  IllegalArgumentException | 회원_대기_토큰_정보 아이디 |  |  |
| MAIN06 | 좌석_예약_서비스_이용_회원_대기_토큰_정보_수정 | MAIN06_RESERVATION_SERVICE_WATING_TOKEN_PUT_UPDATE_01 | 좌석 예약 서비스를 이용하기 위한 회원 대기 토큰 정보를 선택 수정한다. | • 좌석_예약_서비스_이용_회원_대기_토큰_정보를 수정하기 위해선, 회원_대기_토큰_정보 아이디, 대기 회원 정보 아이디, 대기 토큰 상태 아이디,  회원 대기 토큰 생성일/생성 시각, 회원 대기 토큰 만료일/만료 시각, 회원 대기 토큰 수정일/수정 시각이 반드시 필요하다.• 요청에 담긴 회원 대기 토큰 정보 아이디가 null이거나, 공백 문자(열)이거나 1 이상의 자연수가 아니면,IllegalArgumentException(Custom)• 대기 회원 계정 정보 아이디가 UUID로써 유효하지 않으면 IllegalArgumentException• 대기 토큰 상태 아이디(token_status_id)는 대기 토큰 상태 테이블에 고유하게 존재하는 값이어야 한다. 아니면,IllegalArgumentException(Custom)• 회원 대기 토큰 생성일/생성 시각이 null이거나, 공백 문자(열)이면 IllegalArgumentException(Custom)• 회원 대기 토큰 만료일/만료 시각이 null이거나, 공백 문자(열)이거나,  생성일/ 생성 시각이전의 날짜/시각이면 IllegalArgumentException(Custom), 회원 대기 토큰 만료일/ 만료시각은 회원 대기 토큰 생성일/ 생성 시각 기준 5분 후이다.• 회원 대기 토큰 수정일/수정 시각이 null이거나, 공백 문자(열)이거나  오늘 날짜가 아니거나 생성일/ 생성 시각 이전의 날짜/시각이면 IllegalArgumentException(Custom)• 회원 계정 정보 아이디와 대기 토큰 생성일/ 생성 시각 조합은 UNIQUE하다. | 회원_대기_토큰_정보 아이디, 대기 회원 정보 아이디, 대기 토큰 상태 아이디,  회원 대기 토큰 생성일/생성 시각, 회원 대기 토큰 만료일/만료 시각, 회원 대기 토큰 수정일/수정 시각 |  |  |
| MAIN06 | 좌석_예약_서비스_이용_회원_대기_토큰_정보_삭제 | MAIN06_RESERVATION_SERVICE_WATING_TOKEN_PATCH_DELETE_01 | 좌석 예약 서비스를 이용하기 위한 회원 대기 토큰 정보를 선택 삭제한다. | • 좌석_예약_서비스_이용_회원_대기_토큰_정보를 삭제 수정하기 위해선, 대기 토큰 정보 아이디와 대기 토큰 정보 삭제일/삭제 시각이 반드시 필요하다.• 좌석_예약_서비스_이용_회원_대기_토큰_정보 삭제일/삭제 시각이 null이거나, 공백 문자(열)이거나  오늘 날짜가 아니면 IllegalArgumentException(Custom) | 대기 토큰 정보 아이디와 대기 토큰 정보 삭제일/삭제 시각 |  |  |
| MAIN07 | 콘서트_실제_공연_좌석_예약_정보_등록 | MAIN07_CONCERT_SEAT_RESERVATION_CREATE_01 | 콘서트 좌석 예약 정보를 등록한다. | • 콘서트_실제_공연_좌석_예약_정보를 등록하기 위해선, 회원 계정 정보 아이디와 콘서트 실제 공연 좌석 정보 아이디가  반드시 필요하다.• 회원 계정 정보 아이디가 UUID로써 유효하지 않거나, 회원 계정 정보 테이블에 고유하게 존재하는 회원 계정 정보 아이디가 아니면 IllegalArgumentException• 콘서트 실제 공연 좌석 정보 아이디가 null이거나 공백 문자열이거나 유효한 범위(1~50의 자연수)가 아니거나 콘서트 실제 공연 좌석 정보 테이블에 고유하게 존재않으면  IllegalArgumentException | 회원 계정 정보 아이디와 콘서트 실제 공연 좌석 정보 아이디 |  |  |
| MAIN07 | 콘서트_실제_공연_좌석_예약_정보_목록_조회 | MAIN07_CONCERT_SEAT_RESERVATION_LIST_FIND_01 | 콘서트 좌석 예약 정보 목록을 조건에 맞게 조회한다. | • 콘서트 실제_공연_좌석 예약 정보 목록을 조회하기 위한 요청에는 회원 계정 정보 아이디와 콘서트 실제 공연 좌석 정보 아이디, 콘서트 실제 공연 좌석 예약 정보의 상태 아이디, 콘서트 실제 공연 좌석 예약 정보 생성일/ 생성 시각에 대한 인자값들이 담길 수 있다.• 위의 요청 인자값들이 유효하지 않다면 요청에 담기지 않은 것으로 하며, 예외를 발생 시키진 않는다. |  | 회원 계정 정보 아이디와 콘서트 실제 공연 좌석 정보 아이디, 콘서트 실제 공연 좌석 예약 상태 아이디, 콘서트 실제 공연 좌석 예약 정보 생성일/ 생성 시각, 공연 좌석 예약 정보 수정일/ 수정 시각 |  |
| MAIN07 | 콘서트_실제_공연_좌석_예약_정보_상세_조회 | MAIN07_CONCERT_SEAT_RESERVATION_FIND_01 | 콘서트 좌석 예약 정보를 선택 상세 조회한다. | • 콘서트 실제 공연 좌석 예약 정보를 상세 조회하기 위해선 공연 좌석 예약 정보 아이디가 필요하다.• 공연 좌석 예약 정보 아이디가 null이거나, 공백 문자(열)이거나 1 이상의 자연수가 아니면,  IllegalArgumentException | 공연 좌석 예약 정보 아이디 |  |  |
| MAIN07 | 콘서트_실제_공연_좌석_예약_정보_수정 | MAIN07_CONCERT_SEAT_RESERVATION_PUT_UPDATE_01 | 콘서트 좌석 예약 정보를 선택 수정한다. | •  콘서트_실제_공연_좌석_예약_정보를 수정하기 위해선, 콘서트_실제_공연_좌석_예약_정보 아이디, 콘서트_실제_공연_좌석_정보 아이디, 회원 계정 정보 아이디, 실제 공연 좌석 예약 정보의 상태 아이디, 예약 정보 생성일/생성 시각, 예약 정보 수정일/ 수정 시각에 대한 인자값이 반드시 필요하다.• 위의 요청 인자값들에 대한 유효성 검사 시 예외 처리: IllegalArgumentException• 회원 계정 정보 아이디와 실제 공연 좌석 정보 아이디 조합은 UNIQUE | 콘서트_실제_공연_좌석_예약_정보 아이디, 콘서트_실제_공연_좌석_정보 아이디, 회원 계정 정보 아이디, 실제 공연 좌석 예약 상태 아이디, 예약 정보 생성일/생성 시각, 예약 정보 수정일/ 수정 시각 |  |  |
| MAIN07 | 콘서트_실제_공연_좌석_예약_정보_삭제 | MAIN07_CONCERT_SEAT_RESERVATION_PATCH_DELETE_01 | 콘서트 좌석 예약 정보를 선택 삭제한다. | • 콘서트_실제_공연_좌석_예약_정보를 삭제 수정하기 위해선, 콘서트_실제_공연_좌석_예약_정보아이디와 콘서트_실제_공연_좌석_예약_정보 삭제일/삭제 시각이 반드시 필요하다• 콘서트_실제_공연_좌석_예약_정보 삭제일/삭제 시각이 null이거나, 공백 문자(열)이거나  오늘 날짜가 아니면 IllegalArgumentException(Custom) |  콘서트_실제_공연_좌석_예약_정보아이디와 콘서트_실제_공연_좌석_예약_정보 삭제일/삭제 시각 |  |  |
| MAIN08 | 슈퍼_관리자_계정_정보_등록 | MAIN08_SUPER_MANAGER_CREATE_01 | 콘서트 기본 정보를 등록할 권한이 있는 슈퍼 관리자 계정을 등록한다. |  |  |  |  |
| MAIN08 | 슈퍼_관리자_계정_정보_목록_조회 | MAIN08_SUPER_MANAGER_LIST_FIND_01 | 슈퍼 관리자 목록을 조건에 맞게 조회한다. |  |  |  |  |
| MAIN08 | 슈퍼_관리자_계정_정보_상세_조회 | MAIN08_SUPER_MANAGER_FIND_01 | 슈퍼 관리자 정보를 선택 상세 조회한다. |  |  |  |  |
| MAIN08 | 슈퍼_관리자_계정_정보_수정 | MAIN08_SUPER_MANAGER_PUT_UPDATE_01 | 슈퍼 관리자 정보를 선택 수정한다. |  |  |  |  |
| MAIN08 | 슈퍼_관리자_계정_정보_삭제 | MAIN08_SUPER_MANAGER_PATCH_DELETE_01 | 슈퍼 관리자 정보를 선택 삭제한다. |  |  |  |  |

# version 0.0.0 요구 사항 상세 정리

## **기능 상세, 제약 조건, 예외 상황**

- MAIN01:  콘서트 기본 정보 처리(X)
    - **콘서트_기본_정보_등록(MAIN01_CONCERT_BASIC_CREATE_01)**
    - **콘서트_기본_정보_목록_조회(MAIN01_CONCERT_BASIC_LIST_FIND_01)**
    - **콘서트_기본_정보_상세_조회(MAIN01_CONCERT_BASIC_FIND_01)**
    - **콘서트_기본_정보_수정(MAIN01_CONCERT_BASIC_PUT_UPDATE_01)**
    - **콘서트_기본_정보_ 삭제(MAIN01_CONCERT_BASIC_PATCH_DELETE_01)**
- MAIN02: 콘서트 실제 공연 정보 처리(O)
    - **콘서트_실제_공연_정보_등록(MAIN01_CONCERT_DETAIL_CREATE_01)**
        - 콘서트의 실제 공연 정보를 등록하기 위해선, 콘서트 목록에서 공연 일정 등록이 가능한 상태의 콘서트 기본 정보 아이디(concert_basics 테이블의 concert_basic_id 데이터)가 반드시 필요하다.
        - 요청에 담긴 콘서트 기본 정보 아이디가 null이거나 아니면 공백 문자(열)이거나 혹은 1 이상의 자연수가 아닌 값이라면 ****IllegalArgumentException(Custom)
        - 요청에 담긴 콘서트 기본 정보 아이디로 concert_basics 테이블에서 공연 일정 등록이 가능한 상태의 콘서트가 하나 조회되는지 확인해야 한다. ⇒ 하나도 존재하지 않으면 IllegalArgumentException(Custom), 2개 이상 존재하면 무결성 제약 조건 SQLException(Custom)
        - 요청에 담긴 콘서트 기본 정보 아이디로 콘서트 공연 일정 등록이 가능한 상태의 콘서트가 하나 조회되면 해당 콘서트 기본 정보 아이디로 실제 공연 정보를 신규 등록할 수 있다.
        - 특정 콘서트에 대한 실제 공연 일정을 신규 등록하기 위해선, 위 과정에서의 유효한 콘서트 기본 정보 아이디와 함께 콘서트 시작일/시작 시각과, 콘서트 종료일/종료시각이 요청에 반드시 담겨있어야 한다.
        - 콘서트 시작일/시작 시각과 콘서트 종료일/종료 시각이 null이거나, 공백 문자(열)이거나 유효한 날짜 범위(오늘 날짜 이후로 3주 뒤부터 1년 뒤)를 벗어난 날짜거나, 요청에 담긴 콘서트 시작일/시작 시각이 요청에 담긴 콘서트 종료일/종료 시각이 지난 날짜/시각인 경우엔 IllegalArgumentException(Custom)
        - 콘서트 실제 공연 정보 등록자 아이디 또한 요청에 담겨있어야 한다.  콘서트 실제 공연 정보 등록자 아이디는 해당 권한을 가진 등록자 계정 테이블(concert_detail_registers)에 하나 존재하는 값이어야 한다. 아니면, IllegalArgumentException(Custom)
        - 콘서트 실제 공연 정보 등록자 아이디가 null이거나, 공백 문자(열)이거나, 1 이상의 자연수가 아닌 값이라면 IllegalArgumentException
        - 콘서트 기본 정보 아이디, 콘서트 실제 공연 시작일/ 시작 시각, 콘서트 실제 공연 종료일/ 종료 시각 조합은 UNIQUE 해야한다.
    - **콘서트_실제_공연_정보_목록_조회(MAIN01_CONCERT_DETAIL_LIST_FIND_01)**
        - 콘서트 실제 공연 정보 목록을 조회하기 위한 요청에는 콘서트 실제 공연 시작일/시작 시각과 콘서트 실제 공연 종료일/종료 시각, 콘서트 기본 정보 아이디에 대한 인자값이 담길 수 있다.
        - 위의 요청 인자 값들이 null이거나 공백 문자(열)이거나, 1 이상의 자연수가 아닌 경우엔 요청에 담겨있지 않은 것으로 하며, 예외를 발생시키진 않는다.
    - **콘서트_실제_공연_정보_상세_조회(MAIN02_CONCERT_DETAIL_FIND_01)**
        - 콘서트 실제 공연 정보를 상세 조회하기 위해선, 콘서트_실제_공연_정보_아이디가 반드시 필요하다.
        - 요청에 담긴 콘서트_실제_공연_정보_아이디가 null이거나, 공백 문자(열)이거나 1이상의 자연수가 아니면,  IllegalArgumentException
    - **콘서트_실제_공연_정보_수정(MAIN02_CONCERT_DETAIL_PUT_UPDATE_01)**
        - 콘서트 실제 공연 정보를 수정하기 위해선, 콘서트_실제_공연_정보_아이디, 실제 공연 시작일/시작 시각과 실제 공연 종료일/종료 시각, 콘서트 기본 정보 아이디,  콘서트 실제 공연 정보 등록자 아이디, 콘서트 실제 공연 예약 가능 상태 아이디, 콘서트 실제 공연 정보 생성일/생성 시각, 콘서트 실제 공연 정보 수정일/수정 시각이 반드시 필요하다.
        - 요청에 담긴 콘서트_실제_공연_정보_아이디가 null이거나, 공백 문자(열)이거나 1 이상의 자연수가 아니면,  IllegalArgumentException(Custom)
        - 콘서트 시작일/시작 시각과 콘서트 종료일/종료 시각이 null이거나, 공백 문자(열)이거나 유효한 날짜 범위(오늘 날짜 이후로 3주 뒤부터 1년 뒤)를 벗어난 날짜거나, 요청에 담긴 콘서트 시작일/시작 시각이 요청에 담긴 콘서트 종료일/종료 시각이 지난 날짜/시각인 경우엔 IllegalArgumentException(Custom)
        - 콘서트 실제 공연 예약 가능 상태 아이디(concert_detail_status_id)는 실제 공연 예약 가능 상태 테이블에 고유하게 존재하는 값이어야 한다. 아니면, IllegalArgumentException(Custom)
        - 콘서트 실제 공연 정보 등록자 아이디는 콘서트 실제 공연 정보 등록자 아이디는 콘서트 실제 공연 정보 등록자 계정 테이블(concert_detail_registers)에 하나 존재하는 값이어야 한다. 아니면, IllegalArgumentException(Custom)
        - 콘서트 실제 공연 정보 생성일/생성 시각이 null이거나, 공백 문자(열)이면IllegalArgumentException(Custom)
        - 콘서트 실제 공연 정보 수정일/수정 시각이 null이거나, 공백 문자(열)이거나  오늘 날짜가 아니거나 생성일/ 생성 시각 이전의 날짜/시각이면 IllegalArgumentException(Custom)
        - 콘서트 기본 정보 아이디, 콘서트 실제 공연 시작일/ 시작 시각, 콘서트 실제 공연 종료일/ 종료 시각 조합은 UNIQUE 해야한다.
    - **콘서트_실제_공연_정보_삭제(MAIN02_CONCERT_DETAIL_PATCH_DELETE_01)**
        - 콘서트 실제 공연 정보를 삭제 수정하기 위해선, 콘서트_실제_공연_정보_아이디, 콘서트 실제 공연 예약 가능 상태 아이디,  콘서트 실제 공연 정보 삭제일/삭제 시각이 반드시 필요하다.
        - 요청에 담긴 콘서트_실제_공연_정보_아이디가 null이거나, 공백 문자(열)이거나 1 이상의 자연수가 아니면,  IllegalArgumentException
        - 콘서트 실제 공연 예약 가능 상태 아이디(concert_detail_status_id)는 실제 공연 예약 가능 상태 테이블에 존재하면서 삭제 상태에 해당하는 아이디여야 한다. 아니면, IllegalArgumentException(Custom)
        - 콘서트 실제 공연 정보 삭제일/삭제 시각이 null이거나, 공백 문자(열)이거나  오늘 날짜가 아니면 IllegalArgumentException(Custom)
- MAIN03 : 콘서트 실제 공연 좌석 정보 처리(O)
    - **콘서트_실제_공연_좌석_정보_등록(MAIN02_CONCERT_DETAIL_SEAT_CREATE_01)**
        - 콘서트 실제 공연 좌석 정보를 등록하기 위해선, 콘서트 실제 공연 정보 목록에서 좌석 등록이 가능한 상태의 콘서트 실제 공연 정보 아이디(concert_details 테이블의 concert_detail_id 데이터)가 반드시 필요하다.
        - 요청에 담긴 콘서트 실제 공연 정보 아이디가 null이거나 아니면 공백 문자(열)이거나 혹은 1 이상의 자연수가 아닌 값이라면 ****IllegalArgumentException(Custom)
        - 요청에 담긴 콘서트 실제 공연 정보 아이디로 concert_details 테이블에서 좌석 등록이 가능한 상태의 콘서트 실제 공연 정보가 하나 조회되는지 확인해야 한다. ⇒ 하나도 존재하지 않으면 IllegalArgumentException(Custom), 2개 이상 존재하면 무결성 제약 조건 SQLException(Custom)
        - 요청에 담긴 콘서트 실제 공연 정보 아이디로 좌석 등록이 가능한 상태의 콘서트 실제 공연 정보가 하나 조회되면 해당 콘서트 실제 공연 정보 아이디로 콘서트 실제 공연 좌석 정보를 신규 등록할 수 있다.
        - 특정 콘서트 실제 공연 정보에 대한 좌석 정보를 신규 등록하기 위해선, 위 과정에서의 유효한 콘서트 실제 공연 정보 아이디와 함께 좌석 번호와 좌석 가격이 요청에 반드시 담겨있어야 한다.
        - 콘서트 실제 공연 좌석 번호가  null이거나, 공백 문자(열)이거나 유효한 범위(1~50 까지의 자연수)에 해당하지 않는 경우엔 IllegalArgumentException(Custom)
        - 콘서트 실제 공연 좌석 가격이 null이거나, 공백 문자(열)이거나 유효한 범위(10000이상의 자연수)에 해당하지 않는 경우엔 IllegalArgumentException(Custom)
        - 콘서트 실제 공연 정보 아이디, 콘서트 실제 공연 좌석 번호 아이디 조합은 UNIQUE 해야한다.
    - **콘서트_실제_공연_좌석_정보_목록_조회(MAIN02_CONCERT_DETAIL_SEAT_LIST_FIND_01)**
        - 콘서트 실제 공연 좌석 정보 목록을 조회하기 위한 요청에는 콘서트 실제 공연 정보 아이디,  좌석 번호, 좌석 가격에 대한 인자값들이 담길 수 있다.
        - 위의 요청 인자 값들이 null이거나 공백 문자(열)이거나, 1 이상의 자연수가 아닌 경우엔 각각의 값들이 요청에 담겨있지 않은 것으로 하며, 예외를 발생시키지 않는다.
    - **콘서트_실제_공연_좌석_정보_상세_조회(MAIN03_CONCERT_DETAIL_SEAT_FIND_01)**
        - 콘서트_실제_공연_좌석_정보를 상세 조회하기 위해선, 콘서트_실제_공연_좌석_정보_아이디가 반드시 필요하다.
        - 요청에 담긴 콘서트_실제_공연_좌석_정보_아이디가 null이거나, 공백 문자(열)이거나 1 이상의 자연수가 아니면,  IllegalArgumentException
    - **콘서트_실제_공연_좌석_정보_수정(MAIN03_CONCERT_DETAIL_SEAT_PUT_UPDATE_01)**
        - 콘서트 실제 공연 좌석 정보를 수정하기 위해선, 콘서트_실제_공연_좌석_정보_아이디, 콘서트 실제 공연 정보 아이디,  콘서트 실제 공연 좌석 번호, 콘서트  실제 공연 좌석 예약 가능 상태 아이디, 콘서트 실제 공연 좌석 가격, 콘서트 실제 공연 좌석 생성일/생성 시각, 콘서트  실제 공연 좌석 정보 수정일/수정 시각이 반드시 필요하다.
        - 요청에 담긴 콘서트_실제_공연_좌석_정보_아이디가 null이거나, 공백 문자(열)이거나 1 이상의 자연수가 아니면,  IllegalArgumentException(Custom)
        - 콘서트 실제 공연 정보 아이디가 null이거나, 공백 문자(열)이거나 1 이상의 자연수가 아니면,  IllegalArgumentException(Custom)
        - 콘서트 실제 공연 좌석 번호가 아이디가 null이거나, 공백 문자(열)이거나 유효 범위(1~50 자연수)가 아니면,  IllegalArgumentException(Custom)
        - 콘서트 실제 공연 좌석 예약 가능 상태 아이디(seat_status_id)는 콘서트 실제 공연 좌석 예약 가능 상태 테이블에 고유하게 존재하는 값이어야 한다. 아니면, IllegalArgumentException(Custom)
        - 콘서트 실제 공연 좌석 가격이 null이거나, 공백 문자(열)이거나 유효한 범위(10000이상의 자연수)에 해당하지 않는 경우엔 IllegalArgumentException(Custom)
        - 콘서트 실제 공연 좌석 정보 생성일/생성 시각이 null이거나, 공백 문자(열)이면 IllegalArgumentException(Custom)
        - 콘서트 실제 공연 좌석 정보 수정일/수정 시각이 null이거나, 공백 문자(열)이거나  오늘 날짜가 아니거나 생성일/ 생성 시각 이전의 날짜 시각이면 IllegalArgumentException(Custom)
        - 콘서트 실제 공연 정보 아이디, 콘서트 실제 공연 좌석 번호 아이디 조합은 UNIQUE 해야한다.
    - **콘서트_실제_공연_좌석_정보_삭제(MAIN03_CONCERT_DETAIL_SEAT_PATCH_DELETE_01)**
        - 콘서트 실제 공연 좌석 정보를 삭제 수정하기 위해선, 콘서트_실제_공연_좌석_정보_아이디, 콘서트 실제 공연 좌석 예약 가능 상태 아이디,  콘서트 실제 공연 좌석 정보 삭제일/삭제 시각이 반드시 필요하다.
        - 요청에 담긴 콘서트_실제_공연_좌석_정보_아이디가 null이거나, 공백 문자(열)이거나 1 이상의 자연수가 아니면,  IllegalArgumentException
        - 콘서트 실제 공연  좌석 예약 가능 상태 아이디(concert_detail_status_id)는 실제 공연 좌석 예약 가능 상태 테이블에 존재하면서 삭제 상태에 해당하는 아이디여야 한다. 아니면, IllegalArgumentException(Custom)
        - 콘서트 실제 공연 좌석 정보 삭제일/삭제 시각이 null이거나, 공백 문자(열)이거나  오늘 날짜가 아니면 IllegalArgumentException(Custom)
- MAIN04: 콘서트 관리자 계정 정보 처리(X)
- MAIN05: 회원 계정 정보 처리(O)
    - **회원_계정_정보_등록(MAIN05_MEMBER_CREATE_01)**
        - 회원 계정 정보를 등록하기 위해선 UUID로 발급된 회원 계정 정보 아이디가 반드시 필요하다.
        - 회원 계정 정보 아이디가 UUID로써 유효하지 않거나, 회원 계정 정보 테이블에 고유하게 존재하는 회원 계정 정보 아이디가 아니면 IllegalArgumentException
    - **회원_계정_정보_목록 조회(MAIN05_MEMBER_LIST_FIND_01)**
        - 회원 계정 정보 목록을 조회하기 위한 요청에는 회원의 나이, 회원의 이름, 회원의 성별에 대한  인자값들이 담길 수 있다.
        - 위의 요청 인자값들이 null이거나, 공백 문자열이거나, 1 이상의 자연수 아닌 경우엔 요청에 담겨있지 않은 것으로 하며, 예외를 발생시키진 않는다.
    - **회원_계정_정보_상세_조회(MAIN05_MEMBER_FIND_01)**
        - 회원 계정 정보를 상세 조회하기 위해선 UUID 형식의 회원 계정 정보 아이디가 반드시 필요하다.
        - 회원 계정 정보 아이디가 UUID로써 유효하지 않으면 IllegalArgumentException
    - **회원_계정_정보_수정(MAIN05_MEMBER_PUT_UPDATE_01)**
        - 회원 계정 정보를 수정하기 위해선, 회원 계정 정보 아이디, 회원의 나이, 회원의 이름, 회원의 잔고, 회원 계정 정보 생성일/ 생성 시각, 회원 계정 정보 수정일/수정 시각이 반드시 필요하다.
        - 회원의 나이가 null이거나, 공백 문자열이거나, 14 이상의 자연수가 아니면  IllegalArgumentException
        - 회원의 잔고가 0이상의 자연수가 아니면 IllegalArgumentException
        - 회원의 이름이 null이거나 공백 문자열이거나 길이가 30자 이상이면  IllegalArgumentException
        - 회원 계정 정보 아이디가 유효한 UUID가 아니면, IllegalArgumentException
        - 회원 정보 생성일/생성 시각이 null이거나, 공백 문자(열)이면 IllegalArgumentException(Custom)
        - 회원 계정 정보 수정일/수정 시각이 null이거나, 공백 문자(열)이거나  오늘 날짜가 아니거나 생성일/ 생성 시각 이전의 날짜/시각이면 IllegalArgumentException(Custom)
    - **회원_계정_정보_삭제(MAIN05_MEMBER_PATCH_DELETE_01)**
        - 회원 계정 정보를 삭제 수정하기 위해선, 회원 계정 정보 아이디, 회원 계정 정보 삭제일/삭제 시각이 반드시 필요하다.
        - 회원 계정 정보 아이디가 유효한 UUID가 아니면, IllegalArgumentException
        - 회원 계정 정보 삭제일/삭제 시각이 null이거나, 공백 문자(열)이거나  오늘 날짜가 아니면 IllegalArgumentException(Custom)
- MAIN06: 좌석 예약 서비스 이용 회원 대기 토큰 정보 처리(O)
    - **좌석_예약_서비스_이용_회원_대기_토큰_정보_등록(MAIN06_RESERVATION_SERVICE_WATING_TOKEN_CREATE_01)**
        - 좌석_예약_서비스_이용_회원_대기_토큰_정보를 등록하기 위해선, 회원 계정 정보 아이디가 반드시 필요하다.
        - 회원 계정 정보 아이디가 UUID로써 유효하지 않거나, 회원 계정 정보 테이블에 고유하게 존재하는 회원 계정 정보 아이디가 아니면 IllegalArgumentException
    - **좌석_예약_서비스_이용_회원_대기_토큰_정보_목록_조회~~(~~MAIN06_RESERVATION_SERVICE_WATING_TOKEN_LIST_FIND_01)**
        - 좌석_예약_서비스_이용_회원_대기_토큰_정보_목록을 조회하기 위한 요청에는 회원 계정 정보 아이디,  대기 토큰 상태 아이디, 대기 토큰 생성일/생성 시각, 대기 토큰 만료일/만료 시각에 대한 인자값들이 담길 수 있다.
        - 위의 요청 인자 값들이 null이거나, 공백 문장(열)이거나, 1 이상의 자연수가 아닌 경우거나, 대기 토큰 상태 테이블 존재하지 않는 대기 토큰 상태 아이디인 경우거나 회원 계정 정보 아이디가 회원 계정 정보 테이블에 고유하게 존재하지 않거나 유효하지 않은 UUID인 경우엔 각각의 값들이 요청에 담겨있지 않은 것으로 하며, 예외를 발생시키진 않는다.
    - **좌석_예약_서비스_이용_회원_대기_토큰_정보_상세_조회(MAIN06_RESERVATION_SERVICE_WATING_TOKEN_FIND_01)**
        - 좌석_예약_서비스_이용_회원_대기_토큰_정보를 상세 조회하기 위해선, 회원_대기_토큰_정보 아이디가 반드시 필요하다.
        - 요청에 담긴 대기 토큰 정보 아이디가  null이거나, 공백 문자(열)이거나 1 이상의 자연수가 아니면,  IllegalArgumentException
    - **좌석_예약_서비스_이용_회원_대기_토큰_정보_수정(MAIN06_RESERVATION_SERVICE_WATING_PUT_UPDATE_01)**
        - 좌석_예약_서비스_이용_회원_대기_토큰_정보를 수정하기 위해선, 회원_대기_토큰_정보 아이디, 대기 회원 정보 아이디, 대기 토큰 상태 아이디,  회원 대기 토큰 생성일/생성 시각, 회원 대기 토큰 만료일/만료 시각, 회원 대기 토큰 수정일/수정 시각이 반드시 필요하다.
        - 요청에 담긴 회원 대기 토큰 정보 아이디가 null이거나, 공백 문자(열)이거나 1 이상의 자연수가 아니면,  IllegalArgumentException(Custom)
        - 대기 회원 계정 정보 아이디가 UUID로써 유효하지 않으면 IllegalArgumentException
        - 대기 토큰 상태 아이디(token_status_id)는 대기 토큰 상태 테이블에 고유하게 존재하는 값이어야 한다. 아니면, IllegalArgumentException(Custom)
        - 회원 대기 토큰 생성일/생성 시각이 null이거나, 공백 문자(열)이면 IllegalArgumentException(Custom)
        - 회원 대기 토큰 만료일/만료 시각이 null이거나, 공백 문자(열)이거나,  생성일/ 생성 시각이전의 날짜/시각이면 IllegalArgumentException(Custom), 회원 대기 토큰 만료일/ 만료시각은 회원 대기 토큰 생성일/ 생성 시각 기준 5분 후이다.
        - 회원 대기 토큰 수정일/수정 시각이 null이거나, 공백 문자(열)이거나  오늘 날짜가 아니거나 생성일/ 생성 시각 이전의 날짜/시각이면 IllegalArgumentException(Custom)
        - 회원 계정 정보 아이디와 대기 토큰 생성일/ 생성 시각 조합은 UNIQUE하다.
    - **좌석_예약_서비스_이용_회원_대기_토큰_정보_삭제(MAIN06_RESERVATION_SERVICE_WATING_PATCH_DELETE_01)**
        - 좌석_예약_서비스_이용_회원_대기_토큰_정보를 삭제 수정하기 위해선, 대기 토큰 정보 아이디와 대기 토큰 정보 삭제일/삭제 시각이 반드시 필요하다.
        - 좌석_예약_서비스_이용_회원_대기_토큰_정보 삭제일/삭제 시각이 null이거나, 공백 문자(열)이거나  오늘 날짜가 아니면 IllegalArgumentException(Custom)
- MAIN07: 콘서트_실제_공연_좌석 예약 정보 처리(O)
    - **콘서트_실제_공연_좌석_예약_정보_등록(MAIN07_CONCERT_SEAT_RESERVATION_CREATE_01)**
        - 콘서트_실제_공연_좌석_예약_정보를 등록하기 위해선, 회원 계정 정보 아이디와 콘서트 실제 공연 좌석 정보 아이디가  반드시 필요하다.
        - 회원 계정 정보 아이디가 UUID로써 유효하지 않거나, 회원 계정 정보 테이블에 고유하게 존재하는 회원 계정 정보 아이디가 아니면 IllegalArgumentException
        - 콘서트 실제 공연 좌석 정보 아이디가 null이거나 공백 문자열이거나 유효한 범위(1~50의 자연수)가 아니거나 콘서트 실제 공연 좌석 정보 테이블에 고유하게 존재않으면  IllegalArgumentException
    - **콘서트_실제_공연_좌석_예약_정보_목록_조회(MAIN07_CONCERT_SEAT_RESERVATION_LIST_FIND_01)**
        - 콘서트 실제_공연_좌석 예약 정보 목록을 조회하기 위한 요청에는 회원 계정 정보 아이디와 콘서트 실제 공연 좌석 정보 아이디, 콘서트 실제 공연 좌석 예약 정보의 상태 아이디, 콘서트 실제 공연 좌석 예약 정보 생성일/ 생성 시각에 대한 인자값들이 담길 수 있다.
        - 위의 요청 인자값들이 유효하지 않다면 요청에 담기지 않은 것으로 하며, 예외를 발생 시키진 않는다.
    - **콘서트_실제_공연_좌석_예약_정보_상세_조회(MAIN07_CONCERT_SEAT_RESERVATION_FIND_01)**
        - 콘서트 실제 공연 좌석 예약 정보를 상세 조회하기 위해선 공연 좌석 예약 정보 아이디가 필요하다.
        - 공연 좌석 예약 정보 아이디가 null이거나, 공백 문자(열)이거나 1 이상의 자연수가 아니면,  IllegalArgumentException
    - **콘서트_실제_공연_좌석_예약_정보_수정(MAIN07_CONCERT_SEAT_RESERVATION_PUT_UPDATE_01)**
        - 콘서트_실제_공연_좌석_예약_정보를 수정하기 위해선, 콘서트_실제_공연_좌석_예약_정보 아이디, 콘서트_실제_공연_좌석_정보 아이디, 회원 계정 정보 아이디, 실제 공연 좌석 예약 정보의 상태 아이디, 예약 정보 생성일/생성 시각, 예약 정보 수정일/ 수정 시각에 대한 인자값이 반드시 필요하다.
        - 위의 요청 인자값들에 대한 유효성 검사 시 예외 처리: IllegalArgumentException
        - 회원 계정 정보 아이디와 실제 공연 좌석 정보 아이디 조합은 UNIQUE
    - **콘서트_실제_공연_좌석_예약_정보_삭제(MAIN07_CONCERT_SEAT_RESERVATION_PATCH_DELETE_01)**
        - 콘서트_실제_공연_좌석_예약_정보를 삭제 수정하기 위해선, 콘서트_실제_공연_좌석_예약_정보아이디와 콘서트_실제_공연_좌석_예약_정보 삭제일/삭제 시각이 반드시 필요하다
        - 콘서트_실제_공연_좌석_예약_정보 삭제일/삭제 시각이 null이거나, 공백 문자(열)이거나  오늘 날짜가 아니면 IllegalArgumentException(Custom)
- MAIN08: 슈퍼 관리자 계정 정보 처리(X)
    - **슈퍼_관리자_계정_정보_등록(MAIN08_SUPER_MANAGER_CREATE_01)**
    - **슈퍼_관리자_계정_정보_목록_조회(MAIN08_SUPER_MANAGER_LIST_FIND_01)**
    - **슈퍼_관리자_계정_정보_상세_조회(MAIN08_SUPER_MANAGER_FIND_01)**
    - **슈퍼_관리자_계정_정보_수정(MAIN08_SUPER_MANAGER_PUT_UPDATE_01)**
    - **슈퍼_관리자_계정_정보_삭제(MAIN08_SUPER_MANAGER_PATCH_DELETE_01)**

# 동시성 고려 사항

- 예약 가능 상태의 특정 좌석에 대한 예약 요청이 동시에 여러 개가 이루어지는 경우
    - 예약 가능 상태의 특정 좌석에 대한 예약이 동시에 2개 이상 요청되는 경우,
      좌석을 예약하는 하나의  트랜잭션( 1. 대기 토큰 정보 확인, 2. 해당 좌석의 예약 가능 상태 확인, 3.좌석 예약 정보 등록,  4. 좌석 예약 가능 상태 수정, 5. 해당 콘서트 실제 공연 예약 가능 상태 수정)에 대해 2개 이상의 각각의 요청들을 동기화 해주지 않으면, 하나의 좌석에 대해 예약에 성공한 사람이 여러 명인 경우가 생겨날 수 있다.
    - 따라서 예약 가능 상태의 특정 좌석에 대한 예약 요청에 대한 처리는 반드시 특정 좌석 별로 동기화가 필요하다.
- 특정한 회원의 잔고에 대한 충전 혹은 차감 요청이 동시에 여러 개가 이루어지는 경우
    - 여러 좌석에 대한 임시 예약에 성공한 특정 회원이 있다고 할 때, 해당 여러 임시 예약들에 대한 금액 결제 요청을 완전히 동시에 했다고 해보자
    - 이 때, 결제 트랜잭션(1. 대기 토큰 정보 확인 2. 임시 예약 정보를 확인 3. 임시 예약한 콘서트 좌석의 금액 확인 4. 현재 자신의 잔고 확인. 5. 예약 정보 상태를 임시에서 구매 상태로 수정 6. 잔고 차감)에 대해 결제 요청들을 동기화 해주지 않으면, 모든 결제가 끝나고 나서 회원의 잔고는 여러개가 아닌  임시 예약 하나에 대한 금액 결제만 한 것과 같은 결과일 수 있다.
    - 또한, 회원 잔고 충전 요청이 이 시점에 완전히 동시에 이루어진다고 하면, 이 때의 회원 잔고 결과값도 현실과는 완전 달라질 수 있다.
    - 따라서 회원의 잔고를 충전 혹은 차감하는 요청에 대한 처리는 반드시 특정 회원별로 동기화가 필요하다.