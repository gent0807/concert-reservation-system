## 콘서트 예약 시스템 DB Index 적용 보고서

### DB Index

* #### DB Index란,추가적인 쓰기 작업과 저장 공간을 활용하여 데이터베이스 테이블의 검색 속도를 향상시키기 위한 자료구조

* #### DB Indexing의 필요성: DB 인덱스는 데이터베이스의 검색 성능 향상과 효율적인 데이터 접근을 위해 필수적인 요소

  * #### 1. 빠른 데이터 검색
    * #### 전체 테이블 스캔 방지: 인덱스가 없으면, 데이터베이스는 조건에 맞는 데이터를 찾기 위해 테이블의 모든 행을 하나씩 확인해야 합니다. 특히 데이터가 많을 경우 이 과정은 매우 비효율적입니다.
    * #### 포인터 역할: 인덱스는 특정 컬럼의 값과 해당 값이 저장된 위치(포인터)를 함께 저장하여, 원하는 데이터를 즉시 찾아갈 수 있도록 도와줍니다.

  * #### 2. 쿼리 성능 최적화
    * #### 조건 검색 및 범위 검색 최적화: WHERE 절이나 JOIN, ORDER BY, GROUP BY와 같은 SQL 구문에서 인덱스는 조건에 부합하는 데이터를 빠르게 찾거나 정렬하는 데 큰 도움을 줍니다.
    * #### 복합 인덱스 활용: 여러 컬럼을 포함하는 복합 인덱스를 사용하면, 복잡한 조건의 조합에서도 검색 효율을 높일 수 있습니다.

  * #### 3. 리소스 효율성
    * #### 디스크 I/O 감소: 인덱스를 활용하면 불필요한 디스크 접근을 줄여 I/O 부하를 감소시킵니다. 이는 특히 대용량 데이터베이스에서 매우 중요한 요소입니다.
    * #### 쿼리 실행 시간 단축: 검색 시간이 줄어들면 전체적인 애플리케이션 성능이 향상되고, 사용자 경험 또한 개선됩니다.

  * #### 4. 데이터 무결성과 유니크성 보장
    * #### 유니크 인덱스: 유니크 인덱스를 통해 중복된 데이터 입력을 방지함으로써 데이터 무결성을 유지할 수 있습니다.
    * #### 비즈니스 로직 보조: 예를 들어, 이메일이나 사용자 ID처럼 고유해야 하는 값을 관리할 때 유니크 인덱스는 중요한 역할을 합니다.

  * #### 5. 부가적인 고려사항
    * #### 쓰기 작업에 대한 오버헤드: 인덱스는 데이터 삽입, 수정, 삭제 시 함께 업데이트 되어야 하기 때문에, 너무 많은 인덱스는 쓰기 작업의 성능 저하를 유발할 수 있습니다.
    * #### 공간 사용량 증가: 인덱스는 별도의 저장 공간을 사용하므로, 인덱스의 수와 크기를 적절히 관리해야 합니다.
* #### 복합 Index
  * #### 1. 기본 개념
    * #### 여러 컬럼 결합
      * #### 복합 인덱스는 예를 들어 (A, B, C)와 같이 여러 컬럼을 결합하여 하나의 인덱스로 구성합니다.
      * #### 이 때 컬럼의 순서가 매우 중요하며, 보통 쿼리에서 자주 사용되거나 카디널리티가 높은 컬럼을 앞쪽에 배치합니다.
    * #### 왼쪽 접두어 원칙 (Leftmost Prefix Principle)
      * #### 복합 인덱스는 인덱스에 지정된 왼쪽부터 연속된 컬럼 조합에 대해 활용할 수 있습니다.
      * #### 예를 들어, 인덱스 (A, B, C)는 WHERE A = ?, WHERE A = ? AND B = ?, 또는 WHERE A = ? AND B = ? AND C = ? 등의 조건에서 효과적입니다.
      * #### 하지만 WHERE B = ? 만으로는 인덱스의 활용도가 떨어질 수 있습니다.
  * #### 2. 활용 및 장점 
    * #### 다중 조건 최적화
      * #### 하나의 인덱스가 여러 컬럼에 걸친 검색을 최적화할 수 있으므로, 복합 조건의 쿼리 실행 속도가 향상됩니다. 
    * #### 커버링 인덱스(Covering Index)
      * #### 만약 복합 인덱스에 쿼리에서 사용되는 모든 컬럼(검색 컬럼과 반환 컬럼)이 포함된다면, 실제 테이블 데이터에 접근하지 않고 인덱스만으로 결과를 얻을 수 있습니다.
    * #### 쿼리 비용 절감
      * #### 복합 인덱스를 잘 설계하면 디스크 I/O가 줄어들어 쿼리 비용이 낮아지며, 전체 애플리케이션의 응답 속도를 향상시킬 수 있습니다.
  * #### 3. 설계 시 고려사항
    * #### 컬럼 순서 결정
      * #### 쿼리의 조건에 가장 많이 사용되는 컬럼이나 선택도가 높은 컬럼을 인덱스의 첫 번째 위치에 두어야 합니다.
      * #### 데이터의 분포와 쿼리 패턴을 분석하여, 최적의 컬럼 순서를 결정하는 것이 중요합니다.
    * #### 쿼리 패턴 분석
      * #### 주로 실행되는 쿼리에서 어떤 컬럼들이 필터 조건이나 정렬, 그룹핑 등에 사용되는지 분석합니다. 이를 기반으로 복합 인덱스를 구성하면, 인덱스 효율성을 극대화할 수 있습니다.
    * #### 인덱스 크기 및 유지 비용
      * #### 많은 컬럼을 포함하면 인덱스의 크기가 커지고, 데이터 변경 시 인덱스 갱신 비용이 증가할 수 있습니다. 필요한 컬럼만 선택적으로 포함하는 것이 좋습니다.
    * #### 동시성 및 병렬 처리 고려
      * #### 복합 인덱스는 여러 조건을 동시에 고려하기 때문에, 인덱스 스캔 시 효율성을 높일 수 있지만, 너무 많은 인덱스가 존재하면 쓰기 작업에 오버헤드가 발생할 수 있습니다.

### 현재 시나리오 상의 Index 적용 분석
#### 1. 콘서트 좌석 관련 쿼리
```java
public class SeatRepositoryImpl implements SeatRepository {

    private final SeatJPARepository seatJPARepository;

  /**
   * 특정 콘서트 실제 공연의 예약 가능한 상태의 좌석들 조회
   * @param concertDetailId
   * @param seatStatus
   * @return Optional<List<Seat>>
   */
    @Override
    public Optional<List<Seat>> findReservableSeatsByConcertDetailIdAndSeatStatusType(Long concertDetailId, SeatStatusType seatStatus){
        return seatJPARepository.findSeatsByConcertDetailIdAndSeatStatus(concertDetailId, seatStatus);
    }

    @Override
    public Optional<Seat> findSeatBySeatIdWithLock(Long seatId){
        return seatJPARepository.findSeatBySeatIdForShareWithPessimisticLock(seatId);
    }

    @Override
    public void save(Seat seat){
        seatJPARepository.saveAndFlush(seat);
    }

    @Override
    public List<Seat> findSeatsByConcertDetailId(Long concertDetailId){
        return seatJPARepository.findSeatsByConcertDetailId(concertDetailId);
    }

    @Override
    public Optional<List<Seat>> findSeatsBySeatStatusWithLock(SeatStatusType seatStatusType){
        return seatJPARepository.findSeatsBySeatStatusForShareWithPessimisticLock(SeatStatusType.OCCUPIED);
    }
}
```
```java
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "seat")
@Slf4j
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long seatId;

    @Column(name = "concert_detail_id", nullable = false, columnDefinition = "bigint unsigned")
    @Positive
    @Min(0)
    private Long concertDetailId;

    @Version
    private Integer version;

    @Column(name = "seat_number", nullable = false, columnDefinition = "int unsigned")
    @Min(1)
    @Max(50)
    private Integer seatNumber;

    @Column(name = "price", nullable = false, columnDefinition = "int unsigned default 50000")
    @Positive
    @Min(30000)
    private Long price;

    @Column(name = "seat_status", nullable = false)
    private SeatStatusType seatStatus;
}
```
```java
public enum SeatStatusType {
    RESERVED,
    RESERVABLE,
    FORBIDDEN,
    OCCUPIED
}
```
* #### Index 적용 근거 : 콘서트 실제 공연 좌석 테이블의 경우, 여러 유저가 공통적으로 조회를 위해 접근하는 테이블 정보이고, 좌석 예약 시에도 조회와 갱신이 필요한 테이블 정보이므로, 조회가 굉장히 빈번하게 붙을 것이다. 따라서 Index를 적극 활용하는 것이 좋을 것이라 판단했다.
* #### Index 컬럼 : 콘서트 실제 공연 좌석 아이디(seat_id) 컬럼을 기본키로 하고, 콘서트 스케줄 아이디(concert_detail_id) 컬럼을 첫 번째 Index로, 콘서트 실제 공연 좌석의 상태(seat_status)를 두 번째 Index로, 좌석 가격(price)를 세 번째 인덱스로 하는 복합 인덱스를 생성할 것이다.
* #### Index 컬럼 선정 고민 포인트와 예측
  * #### 카디널리티가 높은 순으로 컬럼을 선정 : 콘서트 스케줄 아이디(concert_detail_id)가 기본키를 제외하고는 카디널리티가 높은 편에 속할 것이고, 좌석 가격(price)도 카디널리티가 높은 편에 속할 것이다.
  * #### 좌석 가격은 현재 쿼리에서 조건에 사용되지 않지만 카디널리티가 높은 편이고 차후 검색 조건에 추가될 여지가 충분하기 때문에, 인덱스에 추가시키기로 하였다.
  * #### 콘서트 실제 공연 좌석의 상태(seat_status) 컬럼은 카디널리티가 높지 않고, 갱신이 빈번하게 일어나기 때문에 인덱스에서 제외를 시킬까 고민하였다.
  * #### 하지만, 특정 콘서트 실제 공연의 예약 가능한 상태의 좌석들을 조회하는 쿼리가 아주 빈번하게 실행될 것이기 때문에 갱신 시 쓰기 부하 이슈와 trade off하는 차원에서 인덱스에 추가하기로 하였다.
  * #### 좌석 가격의 경우 범위 조건으로 사용될 가능성이 크기 때문에, 카디널리티가 상대적으로 낮더라도, 인덱스의 성능을 고려한 바, 좌석 상태 컬럼을 좌석 가격 컬럼보다 인덱스 상에 선순위로 두기로 하였다.
  * #### 성능비교
    * #### 좌석 1000만 건 임의 생성 프로시저
      ```sql
        
            CREATE PROCEDURE insert_random_seats()
            BEGIN
            DECLARE i INT DEFAULT 0;
            DECLARE v_seat_number INT;
            DECLARE v_seat_status TINYINT;
            DECLARE v_version INT;
            DECLARE v_concert_detail_id BIGINT;
            DECLARE v_price INT;
    
            -- (옵션) 트랜잭션을 수동으로 관리하도록 autocommit 해제
            SET autocommit = 0;
        
            WHILE i < 10000000 DO
            -- seat_number: 1 ~ 50 사이의 임의의 값
            SET v_seat_number = FLOOR(RAND() * 50) + 1;
            -- seat_status: 0, 1, 2, 3 중 하나
            SET v_seat_status = FLOOR(RAND() * 4);
            -- version: 0 ~ 9 사이의 임의 정수
            SET v_version = FLOOR(RAND() * 10);
            -- concert_detail_id: 1 ~ 2000 사이의 임의의 값
            SET v_concert_detail_id = FLOOR(RAND() * 2000) + 1;
            -- price: 30000 ~ 100000 사이의 임의의 값
            SET v_price = FLOOR(RAND() * 70000) + 30000;
            
            INSERT INTO seat (seat_number, seat_status, version, concert_detail_id, price)
            VALUES (v_seat_number, v_seat_status, v_version, v_concert_detail_id, v_price);
            
            SET i = i + 1;
            
            -- 1만 건마다 COMMIT 하여 트랜잭션 크기를 줄임
            IF (i MOD 10000 = 0) THEN
                COMMIT;
            END IF;
            END WHILE;
        
            -- 남은 데이터 커밋
            COMMIT;
        
             -- autocommit 복원 (원하는 경우)
            SET autocommit = 1;
            END$$

      ```
      ```sql
        select count(*) from seat
      ```
      ```sql
        +----------+
        | count(*) |
        +----------+
        | 10000000 |
        +----------+
      ```
    * #### 인덱스 적용 전 조회
      ```sql
        explain analyze select * from seat where concert_detail_id = 1000 and seat_status = 2;
      ```
      ```sql
        +---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
        | EXPLAIN
        |
        +---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
        | -> Filter: ((seat.seat_status = 2) and (seat.concert_detail_id = 1000))  (cost=1e+6 rows=97326) (actual time=6.67..3350 rows=1232 loops=1)
        -> Table scan on seat  (cost=1e+6 rows=9.73e+6) (actual time=1.94..2955 rows=10e+6 loops=1)
        |
        +---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
        1 row in set (3.35 sec)
      ```
      ```sql
        explain analyze select * from seat where concert_detail_id = 1000 and seat_status = 2 and price between 45000 and 52000;
      ```
      ```sql
        +------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
        | EXPLAIN

        |
        +------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
        | -> Filter: ((seat.seat_status = 2) and (seat.concert_detail_id = 1000) and (seat.price between 45000 and 52000))  (cost=1e+6 rows=10813) (actual time=16.2..3284 rows=144 loops=1)
        -> Table scan on seat  (cost=1e+6 rows=9.73e+6) (actual time=2.2..2899 rows=10e+6 loops=1)
        |
        +------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
        1 row in set (3.29 sec)
      ```
    * #### 인덱스 생성
    ```sql
      CREATE INDEX idx_concert_detail_id_seat_status_price ON seat (concert_detail_id, seat_status, price);
    ```
    * #### 인덱스 적용 후 조회(속도가 비약적으로 빨라졌다)
    ```sql
       explain analyze select * from seat where concert_detail_id = 1000 and seat_status = 2;
    ```
    ```sql
       +-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
       | EXPLAIN
       |
       +-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
       | -> Index lookup on seat using idx_concert_detail_id_seat_status_price (concert_detail_id = 1000, seat_status = 2)  (cost=1355 rows=1232) (actual time=31.4..232 rows=1232 loops=1)
       |
       +-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
       1 row in set (0.23 sec)
    ```
    ```sql
       explain analyze select * from seat where concert_detail_id = 1000 and seat_status = 2 and price between 45000 and 52000;
    ```
    ```sql
       +----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
       | EXPLAIN

                                                                                                           |
       +----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
       | -> Index range scan on seat using idx_concert_detail_id_seat_status_price over (concert_detail_id = 1000 AND seat_status = 2 AND 45000 <= price <= 52000), with index condition: ((seat.seat_status = 2) and (seat.concert_detail_id = 1000) and (seat.price between 45000 and 52000))  (cost=170 rows=144) (actual time=0.367..0.529 rows=144 loops=1)
       |
       +----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
       1 row in set (0.00 sec)
    ```
    ```sql
      mysql> show profiles;
      +----------+------------+-------------------------------------------------------------------------------------------------------------------------+
      | Query_ID | Duration   | Query
      |
      +----------+------------+-------------------------------------------------------------------------------------------------------------------------+
      |        1 | 0.00092225 | explain analyze select * from seat where concert_detail_id = 1000 and seat_status = 2 and price between 45000 and 52000 |
      +----------+------------+-------------------------------------------------------------------------------------------------------------------------+
    ```
* #### Entity 적용
  ```java
  @Getter
  @Setter
  @Entity
  @Builder
  @AllArgsConstructor
  @EntityListeners(AuditingEntityListener.class)
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @Table(name = "seat", indexes = @Index(name = "idx_concert_detail_id_seat_status", columnList = "concert_detail_id, seat_status, price"))
  @Slf4j
  public class Seat {
  
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      @Column(name = "seat_id")
      private Long seatId;
  
      @Column(name = "concert_detail_id", nullable = false, columnDefinition = "bigint unsigned")
      @Positive
      @Min(0)
      private Long concertDetailId;
  
      @Version
      private Integer version;
  
      @Column(name = "seat_number", nullable = false, columnDefinition = "int unsigned")
      @Min(1)
      @Max(50)
      private Integer seatNumber;
  
      @Column(name = "price", nullable = false, columnDefinition = "int unsigned default 50000")
      @Positive
      @Min(30000)
      private Long price;
  
      @Column(name = "seat_status", nullable = false)
      private SeatStatusType seatStatus;
  }
  ```

#### 2. 콘서트 스케줄 관련 쿼리

```java
public class ConcertDetailRepositoryImpl implements ConcertDetailRepository {
    private final ConcertDetailJPARepository concertDetailJPARepository;

    /**
     * 어떤 특정 콘서트의 예약 가능한 스케줄 목록(특정 콘서트의 실제 공연 목록)을 기간에 따라 조회
     * @param concertBasicId
     * @param concertDetailStatus
     * @return Optional<List<ConcertDetail>>
     */
    @Override
    public Optional<List<ConcertDetail>> findConcertDetailsByConcertBasicIdStartTimeAndEndTimeAndConcertDetailStatus(Long concertBasicId, ConcertDetailStatusType concertDetailStatus){
        return concertDetailJPARepository.findConcertDetailsByConcertBasicIdAndStartTimeAndEndTimeConcertDetailStatus(concertBasicId, concertDetailStatus);
    }

    /**
     * 어떤 특정 콘서트의 예약 가능한 스케줄 상의 좌석 예약 시, 해당 좌석이 종속된 콘서트 스케줄(특정 콘서트 실제 공연)의 예약 가능 상태 여부를 데이터베이스 특정 행 잠금을 통해 확인하는 용도
     * @param concertDetailId
     * @return Optional<ConcertDetail>
     */
    @Override
    public Optional<ConcertDetail> findConcertDetailByConcertDetailIdWithLock(Long concertDetailId){
        return concertDetailJPARepository.findConcertDetailByConcertDetailIdForShareWithPessimisticLock(concertDetailId);
    }

    /**
     * 어떤 특정 콘서트의 예약 가능한 스케줄 내 좌석 예약 시, 특정 콘서트 실제 공연의 예약 가능 상태를 update
     * @param concertDetail
     * @return ConcertDetail
     */
    @Override
    public ConcertDetail save(ConcertDetail concertDetail){
        return concertDetailJPARepository.saveAndFlush(concertDetail);
    }

}
```
```java
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "concert_detail")
@Slf4j
public class ConcertDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_detail_id")
    private Long concertDetailId;

    @Column(name = "concert_basic_id", nullable = false)
    private Long concertBasicId;

    @Version
    private Integer version;

    @Column(name = "concert_detail_status", nullable = false)
    private ConcertDetailStatusType concertDetailStatus;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @CreatedDate
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;
}
```
```java

public enum ConcertDetailStatusType {
    COMPLETED,  // 매진
    RESERVABLE, // 예약 가능 좌석 있음
    CANCELLED   // 공연 취소됨
}

```

* #### Index 적용 근거 : 콘서트 스케줄(콘서트 실제 공연) 테이블의 경우, 여러 유저가 공통적으로 조회를 위해 접근하는 테이블 정보이고, 좌석 예약 시에도 조회와 갱신이 필요한 테이블 정보이므로, 조회가 굉장히 빈번하게 붙을 것이다. 따라서 Index를 적극 활용하는 것이 좋을 것이라 판단했다. 
* #### Index 컬럼 : 콘서트 스케줄 아이디(concert_detail_id) 컬럼을 기본키로 하고, 콘서트 아이디(concert_basic_id) 컬럼을 첫 번째 Index로, 실제 공연 시작 일자/시각(start_time) 컬럼을 두 번째 Index로, 실제 공연 종료 일자/시작(end_time) 컬럼을 세 번째 Index로 한 복합 인덱스이자, 유니크 인덱스를 생성할 것이다.
* #### Index 컬럼 선정 고민 포인트와 예측
  * #### 카디널리티가 높은 순으로 컬럼을 선정 : 콘서트 아이디(concert_basic_id)가 기본키를 제외하고는 카디널리티가 확실히 제일 높을 것이고, 콘서트 실제 공연의 시작 일자/시각과 종료 일자/시작이 그 다음으로 높은 카디널리티를 가질 것이다.
  * #### 콘서트 실제 공연의 상태(concert_detail_status) 컬럼은 index에서 전략적으로 빼는 것이 좋을 것이라 생각했다.
    * #### 콘서트 실제 공연의 상태(concert_detail_status)는 카디널리티가 절대 높지 않다.  COMPLETED, RESERVABLE, CANCELLE, 세 가지로 국한되고 다양하게 분포하지 않고 RESERVABLE이나 COMPLETED으로 치우쳐진 상황이 많을 것이다.
    * #### 인덱스에 추가한다해도, 실제 공연 시작 일자/시각, 실제 공연 종료 일자/시각보다 콘서트 실제 공연의 상태(concert_detail_status)는 조건절에서 후순위일텐데, 실제 공연 시작 일자/시각, 실제 공연 종료 일자/시각에 BETWEEN 연산을 적용하므로 인덱스를 살릴 수 없다.
    * #### 그렇다고 인덱스 순서에 어긋나게 콘서트 실제 공연의 상태(concert_detail_status)를 조건절에서 실제 공연 시작 일자/시각보다 먼저 쓰면 인덱스 성능이 떨어진다.
    * #### 콘서트 실제 공연의 상태(concert_detail_status)는 또한 상대적으로 갱신이 자주 일어날 컬럼이기 때문에 인덱스에 추가 시 쓰기 부하 이슈가 있다.
    * #### 따라서, 콘서트 스케줄 테이블에는 콘서트 아이디(concert_basic_id) 컬럼과 콘서트 실제 공연 시작 일자/시각(start_time) 컬럼, 콘서트 실제 공연 종료 일자/시각(end_time) 순으로 인덱스를 생성하기로 했다.
  * #### 성능비교 
    * #### 콘서트 스케줄 1000만 건 임의 생성 프로시저
    ```sql
          CREATE PROCEDURE insert_random_concert_details()
          BEGIN
          DECLARE i INT DEFAULT 0;
          DECLARE dt_start DATETIME(6);
          DECLARE dt_end DATETIME(6);

          WHILE i < 10000000 DO
          -- 랜덤 시작 시간: 현재 시각부터 최대 약 11.5일 후까지의 임의 시간
          SET dt_start = DATE_ADD(NOW(), INTERVAL FLOOR(RAND() * 1000000) SECOND);
          -- 랜덤 종료 시간: 시작 시간 이후 최대 2시간 내 임의 시간 (dt_start보다 항상 이후)
          SET dt_end = DATE_ADD(dt_start, INTERVAL FLOOR(RAND() * 7200) SECOND);

          INSERT INTO concert_detail (
              concert_detail_status,
              version,
              concert_basic_id,
              start_time,
              end_time
          ) VALUES (
              FLOOR(RAND() * 3),        -- 0, 1 또는 2
              FLOOR(RAND() * 10),       -- 0부터 9 사이의 임의 정수 (예시)
              FLOOR(RAND() * 1000) + 1,   -- 1부터 1000 사이의 임의 값
              dt_start,
              dt_end
          );

          SET i = i + 1;
          END WHILE;
          END$$
    ```
    * #### 인덱스 적용 전 조회
    ```sql
        explain analyze select * from concert_detail where concert_basic_id = 300 and start_time >= '2025-02-15 00:00:00' and end_time < '2025-02-17 00:00:00';
    ```
    ```sql
        +--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
        | EXPLAIN


        |
        +--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
        | -> Filter: ((concert_detail.concert_basic_id = 300) and (concert_detail.start_time >= TIMESTAMP'2025-02-15 00:00:00') and (concert_detail.end_time < TIMESTAMP'2025-02-17 00:00:00'))  (cost=251582 rows=27342) (actual time=0.517..889 rows=419 loops=1)
        -> Table scan on concert_detail  (cost=251582 rows=2.46e+6) (actual time=0.485..809 rows=2.47e+6 loops=1)
        |
        +--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
        1 row in set (0.89 sec)
    ```
    * #### 인덱스 생성
    ```sql
       create index idx_concert_basic_id_start_time_end_time on concert_detail(concert_basic_id, start_time, end_time);
    ```
    * #### 인덱스 적용 후 조회
    ```sql
        explain analyze select * from concert_detail where concert_basic_id = 300 and start_time >= '2025-02-15 00:00:00' and end_time < '2025-02-17 00:00:00';
    ```
    ```sql
       +------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
       | EXPLAIN


                                                                               |
       +------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
       | -> Index range scan (Multi-Range Read) on concert_detail using idx_concert_basic_id_start_time_end_time over (concert_basic_id = 300 AND '2025-02-15 00:00:00.000000' <= start_time), with index condition: ((concert_detail.concert_basic_id = 300) and (concert_detail.start_time >= TIMESTAMP'2025-02-15 00:00:00') and (concert_detail.end_time < TIMESTAMP'2025-02-17 00:00:00'))  (cost=2593 rows=2257) (actual time=3..117 rows=419 loops=1)
       |
       +------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
       1 row in set (0.12 sec)
    ```
    ```sql
       mysql> show profiles;
       +----------+------------+--------------------------------------------------------------------------------------------------------------------------------------------------------+
       | Query_ID | Duration   | Query
       |
       +----------+------------+--------------------------------------------------------------------------------------------------------------------------------------------------------+
       |        1 | 0.00092225 | explain analyze select * from seat where concert_detail_id = 1000 and seat_status = 2 and price between 45000 and 52000                                |
       |        2 | 0.80457500 | explain analyze select * from concert_detail where concert_basic_id = 300 and start_time >= '2025-02-15 00:00:00' and end_time < '2025-02-17 00:00:00' |
       |        3 | 0.76126350 | select * from concert_detail where concert_basic_id = 300 and start_time >= '2025-02-15 00:00:00' and end_time < '2025-02-17 00:00:00'                 |
       |        4 | 0.94426275 | explain analyze select * from concert_detail where concert_basic_id = 300 and start_time >= '2025-02-15 00:00:00' and end_time < '2025-02-17 00:00:00' |
       |        5 | 0.88977350 | explain analyze select * from concert_detail where concert_basic_id = 300 and start_time >= '2025-02-15 00:00:00' and end_time < '2025-02-17 00:00:00' |
       |        6 | 5.83165475 | create index idx_concert_basic_id_start_time_end_time on concert_detail(concert_basic_id, start_time, end_time)                                        |
       |        7 | 0.11839825 | explain analyze select * from concert_detail where concert_basic_id = 300 and start_time >= '2025-02-15 00:00:00' and end_time < '2025-02-17 00:00:00' |
       +----------+------------+--------------------------------------------------------------------------------------------------------------------------------------------------------+
    ```
* #### Entity 적용
  ```java
    @Getter
    @Setter
    @Entity
    @Builder
    @AllArgsConstructor
    @EntityListeners(AuditingEntityListener.class)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Table(name = "concert_detail", indexes = @Index(name = "idx_concert_basic_start_time_end_time", columnList = "concert_basic_id, start_time, end_time", unique = true))
    @Slf4j
    public class ConcertDetail {
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "concert_detail_id")
        private Long concertDetailId;
    
        @Column(name = "concert_basic_id", nullable = false)
        private Long concertBasicId;
    
        @Version
        private Integer version;
    
        @Column(name = "concert_detail_status", nullable = false)
        private ConcertDetailStatusType concertDetailStatus;
    
        @Column(name = "start_time", nullable = false)
        private LocalDateTime startTime;
    
        @Column(name = "end_time", nullable = false)
        private LocalDateTime endTime;
  }
  ```
