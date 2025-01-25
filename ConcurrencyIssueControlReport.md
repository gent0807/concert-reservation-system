# 동시성 이슈 제어 보고서 

## 1. 동시성 제어 방식
### 1.1. Race Condition

#### Race Condition : 두 개 이상의 프로세스(트랜잭션, 프로세스)가 공유 자원(메모리, 데이터베이스, 파일)을 병행적으로 읽거나 쓰는 상황으로, 공유 자원에 동시 접근할 시, 실행 순서에 따라 결과값이 달라질 수 있는 현상  

* #### 데이터베이스 Race Condition
    * #### 분실 갱신(Lost Update) : 여러 트랜잭션이 동시에 데이터를 수정하면서 하나의 트랜잭션의 작업 결과가 분실되는 문제
      ![LOST_UPDATE (2)](https://github.com/user-attachments/assets/1e06fb6c-d217-450e-b961-5384c31d9f0d)
    * #### 커밋되지 않은 의존(Uncommited Dependency) : 트랜잭션 실행 중 문제가 발생해 롤백시키면서 결과적으로는 반영되지 않아야 할 데이터를 읽어 생기는 문제
      ![Uncommited_Dependency](https://github.com/user-attachments/assets/52778a3b-a719-4bbd-bb2f-f338a663b247)
    * #### 모순 감지(Inconsistency Analysis) : 트랜잭션이 실행 중에 다른 트랜잭션이 데이터 수정에 개입해 데이터 일관성이 깨지는 문제 
      ![Inconsistency_Analysis](https://github.com/user-attachments/assets/c703fca8-31b9-401e-8726-1c3c518975ea)

* #### Isolation Level(트랜잭션 격리 수준)
  #### 트랜잭션 격리 수준은 서로 다른 트랙잭션 간에 적용된다. 동일 트랜잭션 안에서는 커밋되지 않은 변경 사항을 READ
  * READ UNCOMMITTED LEVEL  : 커밋되지 않은 변경 사항을 다른 트랜잭션에서 READ 가능
  * READ COMMITTED LEVEL    : 커밋된 변경 사항만 다른 트랜잭션에서 READ 가능
  * REPEATABLE REDD LEVEL (mysql default 격리 수준)  : 커밋 여부와 상관없이 다른 트랜잭션에서는 해당 트랜잭션의 시작 시점 스냅샷 READ, *MVCC 이용 
  * SERIALIZABLE LEVEL      : 서로 다른 트랜잭션들 간의 완전한 직렬화 수준

* #### Isolation Level(트랜잭션 격리 수준)에 따른 문제 현상
   * Dirty read : 다른 트랜잭션 안의 커밋 이전인 변경 사항 데이터를 읽고 난 후 해당 다른 트랙잭션이 비정상 종료되어 Rollback 시 데이터의 일관성이 깨지는 문제 (READ UNCOMMITTED LEVEL애서 발생)

     ![dirty_read](https://github.com/user-attachments/assets/bd55988a-e12b-4baf-adc4-23b24bb8e7e0)

   * Non-repeatable read : 트랜잭션 진행 중에 다른 트랜잭션에서 변경, 커밋되기 이전의 데이터를 읽고난 후 해당 데이터가 변경,커밋된 이후에 또다시 Read할 시, 데이터 일관성이 깨지는 문제(READ UNCOMMITTED LEVEL, READ COMMITTED LEVEL에서 발생)

     ![Non-repeatable_read](https://github.com/user-attachments/assets/8d305ce8-22b9-46e9-ada7-e2210226689b)
   * Phantom read : 트랜잭션 진행 중에 다른 트랜잭션에서 추가 생성, 커밋되기 이전의 데이터를 읽고난 후, 신규 데이터가 생성, 커밋된 이후에 또다시 Read할 시, 데이터 일관성이 깨지는 문제(READ UNCOMMITTED LEVEL, READ COMMITTED LEVEL, REPEATABLE REDD LEVEL에서 발생)

     ![phantom_read](https://github.com/user-attachments/assets/1236bc53-7bb0-480a-aa16-1b3307770185)

     ![isolation_level](https://github.com/user-attachments/assets/f795250f-8095-4c0d-9bae-31d0b71784e1)

  

### 1.2 데이터베이스 Lock  
#### 위와 같은 데이터베이스 Race Condition을 해결하기 위한 방안을 알아보자.
* #### DB Lock
  1. #### 낙관적 lock
      #### 대부분의 트랜잭션은 충돌이 발생하지 않는다고 낙관적으로 가정하는 방법

      #### 낙관적 lock은 비관적 lock과 다르게 데이터베이스가 제공하는 lock이 아닌 애플리케이션 레벨에서 lock을 구현하게 된다. 
      #### JPA에서는 버전 관리 기능(@Version)을 통해 구현
    
      #### 낙관적 lock은 애플리케이션에서 충돌을 관리하기에 트랜잭션을 커밋하기 전까지는 충돌을 알 수 없으며 
      #### lock은 충돌 발생 시점에만 데이터에 대한 동시성 제어를 수행하기 때문에, 성능상 이점이 있다.
   
     ![Optimistic_Lock](https://github.com/user-attachments/assets/6471f4b6-3b8a-4396-add2-2f2ca473fe20)

      #### 결과적으로, 낙관적lock은 실제로 데이터 충돌이 자주 일어나지 않을것이라고 예상되는 시나리오에서 좋다.                    
                                                                                
      #### 하지만 추가적인 오류 처리가 필요하고 동시 접근이 많이 발생하면 오류 처리를 위해 더 많은 리소스가 소모되는 상황이 만들어질 수 있다.
  2. #### 비관적 lock
     1. #### s-lock(공유lock)
        #### 공유 lock이 걸린 데이터에 대해서는 읽기 연산(SELECT)만 실행 O, 쓰기 연산(WRITE)은 실행 X. 
        #### 즉, 공유 lock이 걸린 데이터는 다른 트랜잭션도 똑같이 공유 lock을 획득할 수 있으나, 배타 lock은 획득할 수 없다. 
        #### 공유 lock이 걸려도 읽기 작업은 가능하다는 뜻이다.
        #### 공유 lock을 사용하면, 조회한 데이터가 트랜잭션 내내 변경되지 않음을 보장한다.
     2. #### x-lock(배타lock)
        #### 배타 lock은 쓰기 lock(Write Lock)이라고도 불린다. 
        #### 데이터에 대해 배타 lock을 획득한 트랜잭션은, 읽기 연산과 쓰기 연산을 모두 실행할 수 있다. 
        #### 다른 트랜잭션은 배타 lock이 걸린 데이터에 대해 읽기 작업도, 쓰기 작업도 수행할 수 없다. 
        #### 즉, 배타 lock이 걸려있다면 다른 트랜잭션은 공유 lock, 배타 lock 둘 다 획득 할 수 없다. 
        #### 배타 lock을 획득한 트랜잭션은 해당 데이터에 대한 독점권을 갖는 것이다.
 
         <img width="623" alt="image (1)" src="https://github.com/user-attachments/assets/9421b4f2-f844-4c5c-827b-d3ce2ca5f01d" />
     3. DB Lock  호환성
         
         ![db_lock](https://github.com/user-attachments/assets/64c61c1c-b25f-48a1-96f3-93c6fe68383b)

     4. #### Dead Lock 발생 문제 
        * #### s-lock and x-lock
          #### 두 트랜잭션이 모두 동일한 자원에 대해 s-lock 얻은 상태에서,
          #### 두 트랜잭션 모두 커밋(s-lock 해제)되기 전에 UPDATE 쿼리 실행하여 x-lock 얻으려 할 때,
          #### 두 트랜잭션이 서로의 s-lock 해제될 때까지 대기함으로써 두 트랜잭션 모두 무한 대기에 빠질 수 있다.
          * #### x-lock and x-lock
            #### 두 트랜잭션이 각 트랜잭션 내에서 모두 동일한 자원들에 대해 x-lock 얻는다고 할때,
            #### 두 트랜잭션의 x-lock 자원들의 잠금 순서가 서로 다르면,
            #### 두 트랜잭션이 서로 특정 자원들의 x-lock 해제될 때까지 대기하여, 두 트랜잭션이 모두 무한 대기에 빠질 수 있다.
 
            ![images_gojung_post_b8c0a12b-30e9-4aaf-97ec-765226db0164_image](https://github.com/user-attachments/assets/9ab4d57a-5f2b-4011-9378-067abed25442)
     5. 비관적 lock은 동시에 데이터에 접근하는 트랜잭션과 수정 작업이 많거나, 데이터의 정합성이 중요한 시나리오에 적합하다.
     6. 다만, 대기 시간 증가와 교착 상태 발생 가능성, 불필요한 lock으로 인한 성능 저하, 동시성 처리 불리 이슈가 발생한다.                             

      
        
       

        
* #### 분산 Lock 
            
            

##  2. 콘서트 좌석 예약 시스템 동시성 이슈 제어
### 2-1. 현재 기능 상 발생 가능한 동시성 이슈
                                     
* #### 포인트 충전 시의 경합 발생 
* #### 좌석 예약 (좌석 점유 처리) 시의 경합 발생
* #### 결제 정보 결제 처리 시의 경합 발생

### 2-2. 현재 동시성 이슈의 Race Condition 분석
              
* ### Transaction A (포인트 충전) 
  ```
  유저 정보 조회 - 유저 정보 수정 저장 - 포인트 충전 내역 저장 - 포인트 충전/차감 내역 목록 조회
  ``` 
    ```java 
       
        @Service
        public class PointHistoryService{  
        @Validated(CreatePointHistory.class)
        @Transactional
        public List<PointHistoryDTOResult> insertChargeUserPointHistory(@Valid PointHistoryDTOParam pointHistoryDTOParam) {
           
               // 유저 포인트 동시 충전에 대한 동시성 제어 위해 데이터베에스 테이블 특정 유저 lock: 각 트랜잭션마다 적용
               User user = userRepository.findUserByUserIdWithLock(pointHistoryDTOParam.userId())
                           .orElseThrow(()->{
                               throw new ServiceDataNotFoundException(ErrorCode.USER_NOT_FOUND, "POINT_HISTORY SERVICE", "insertUserPointHistory");
               });
           
               // 포인트 수정, jpa 영속 객체 dirty checking 이용한 업데이트
               user.chargePoint(pointHistoryDTOParam.amount());
                   
               // 도메인 모델 내 정적 팩토리 메소드로 생성
               PointHistory pointHistory = PointHistory.createPointHistory(pointHistoryDTOParam.userId(), pointHistoryDTOParam.type(), pointHistoryDTOParam.amount(), user.getPoint());
           
               // 포인트 충전 내역 저장, 비영속 객체이므로 save 필요
               pointHistoryRepository.savePointHistory(pointHistory);
           
                   
           
               // 유저의 포인트 충전/차감 내역 목록 반환
               return pointHistoryRepository.findPointHistoriesByUserId(pointHistory.getUserId())
                                                         .orElseThrow(()->{
                                                             throw new ServiceDataNotFoundException(ErrorCode.POINT_HISTORY_SAVE_FAILED, "POINT_HISTORY SERVICE", "insertUserPointHistory");
                                                         }).stream().map(PointHistory::convertToPointHistoryDTOResult).collect(Collectors.toList());
           
           }
      
        }
    ```                  
  
* ### Transaction B (좌석 예약, 좌석 점유) 
  ```
  실제 공연의 좌석 정보 조회 - 콘서트 실제 공연 정보 조회 - 결제 정보 발행 - 좌석 임시 예약 정보 신규 등록 - 현재 유저의 좌석 예약 정보 목록 조회 - 예약 좌석 정보 (점유 상태로) 수정 저장 - 콘서트 실제 공연 정보 수정 저장(sold out or not sold out) 
  ```    
  
  ```java
      // 3. 좌석 예약 주문서 발행, 좌석 임시 점유(occupied)        
      @Validated(CreateReservations.class)
      @Transactional
      public List<ConcertReserveAdminDTOResult> insertReservations(List<@Valid ConcertReserveAdminDTOParam> concertReserveAdminDTOParamList) {

          // 콘서트 실제 공연 좌석들의 예약 상태/예약 가능 여부 확인, 예약 불가(상태가 reservable 아닌 경우)이면 exception 발생
          seatService.checkReservableOfConcertDetailAndSeat(ConcertReserveAdminDTOParam.convertToSeatDTOParamList(concertReserveAdminDTOParamList));

          // 미결제 상태의 결제 정보 신규 저장(주문서 발행)
          PaymentDTOResult paymentDTOResult = paymentService.publishNewPayment(concertReserveAdminDTOParamList);

          // 좌석들 임시 예약 정보 신규 등록, 현재 유저 좌석 예약 정보 조회
          List<ReservationDTOResult> reservationDTOResultList = reservationService.insertReservations(ConcertReserveAdminDTOParam.convertToReservationDTOParamList(concertReserveAdminDTOParamList, paymentDTOResult));

          // 좌석들의 예약 상태/예약 가능 여부를 점유 상태로 수정
          seatService.updateStatusOfConcertDetailAndSeats(reservationDTOResultList.stream().map(reservationDTOResult -> {
           return SeatDTOParam.builder().seatId(reservationDTOResult.seatId()).build();
          }).collect(Collectors.toList()), SeatStatusType.OCCUPIED);
          //
          return ReservationDTOResult.convertToConcertReserveAdminDTOResultList(reservationDTOResultList);

  }
  ```           
  

* ### Transaction C (결제)
  ```
  실제 공연의 좌석 정보 조회 - 좌석 예약 정보 조회 - 결제 정보 조회 - 유저 정보(포인트) 조회 - 유저 정보(포인트) 수정 저장 - 좌석 예약 정보(예약 상태 COMPLETED) 수정 저장 - 결제 정보(결제 상태 PAID) 수정 저장 - 결제 정보 조회 - 콘서트 좌석 정보(예약 상태 RESERVED) 수정 저장
  ```           
  ```java
  // 4. 주문 금액 결제, 좌석 완전 예약
  @Validated(ProcessPayment.class)
  @Transactional
  public ConcertReserveAdminDTOResult payAndReserveConcertSeats(@Valid ConcertReserveAdminDTOParam concertReserveAdminDTOParam) {

          // 결제하려는 예약 좌석들의 상태가 점유 상태인지 확인
          seatService.checkSeatsOccupied(reservationService.convertReservationDTOParamToSeatDTOParamList(concertReserveAdminDTOParam.convertToReservationDTOParam()));

          // 좌석 예약 정보들의 상태가 임시 상태인지 확인
          reservationService.checkReservationsTemp(concertReserveAdminDTOParam.convertToReservationDTOParam());

          // 결제 정보의 상태가 미결제 상태인지 확인
          paymentService.checkPaymentPublished(concertReserveAdminDTOParam.convertToPaymentDTOParam());

          // 유저의 포인트 차감
          pointHistoryService.useUserPoint(concertReserveAdminDTOParam.convertToPointHistoryDTOParam());

          // 좌석 예약 정보들 예약 상태를 예약 완료 상태로 변경
          reservationService.updateStatusOfReservations(concertReserveAdminDTOParam.convertToReservationDTOParam(), ReservationStatusType.CONFIRMED);

          // 결제 정보 결제 완료 상태로 변경
          PaymentDTOResult paymentDTOResult = paymentService.updateStatusOfPayment(concertReserveAdminDTOParam.convertToPaymentDTOParam(), PaymentStatusType.PAID);

          // 콘서트 실제 공연 좌석들 예약 상태를 reserved 상태로 변경
          seatService.updateStatusOfConcertDetailAndSeats(reservationService.convertReservationDTOParamToSeatDTOParamList(concertReserveAdminDTOParam.convertToReservationDTOParam()), SeatStatusType.RESERVED);

          return paymentDTOResult.convertToConcertReserveAdminDTOResult();

  }
  ```         
* Transaction A, Transaction B, Transaction C의 경합 발생 가능 Resource
  *  User 테이블의 row 
  *  ConcertDetail 테이블의 row
  *  Seat 테이블의 row
  *  Reservation 테이블의 row
  *  Payment 테이블의 row


#### 
  
* Case 1  : Transaction A (특정 유저의 포인트 충전) 동시에 여러 개 발생
  * User 테이블의 특정 User Row 대한 포인트 수정 동시성 처리
    * 낙관적 lock 
       #### 특정 유저의 포인트 충전 요청은 현실을 고려할 시, User 테이블의 row에 대해 상대적으로 충돌이 덜 발생하고 
       #### 특정 유저의 포인트 충전 요청이 동시에 여러 개 발생하는 것은 비정상적인 요청으로 간주 가능하며,
       #### 불필요한 잠금으로 인한 성능 저하없이 동시성 처리를 하기엔 낙관적 lock이 유리할 수 있다.
       #### 포인트 충전 시 낙관적 lock 사용을 위한 유저 도메인 엔티티에 version 컬럼 선언 
        ```java
          @Getter
          @Setter
          @Entity
          @Builder
          @AllArgsConstructor
          @NoArgsConstructor(access = AccessLevel.PROTECTED)
          @Slf4j
          public class User {
    
          @Id
          private String userId;
    
          @Column(name = "user_name", nullable = false)
          private String userName;
    
          @Version
          private Integer version;
    
          @Column(name = "age", nullable = false)
          @Positive
          @Min(0)
          private int age;
    
          @Column(name = "gender", nullable = false)
          private UserGenderType gender;
    
          @Column(name = "point", nullable = false, columnDefinition = "BIGINT UNSIGNED DEFAULT 0")
          private long point;
    
          @CreatedDate
          @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
          private LocalDateTime createdAt;
    
          @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
          private LocalDateTime updatedAt;
    
          @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
          private LocalDateTime deletedAt;
        }
      ```
      #### 낙관적 lock을 위한 UserJPARepository 인터페이스 OptimisticUserRepository
       ```java
            public interface OptimisticUserRepository extends UserJPARepository{

            @Override
            @Lock(LockModeType.NONE)
            @Query("SELECT u FROM User u WHERE u.userId = :userId")
              User findUserByUserIdForUpdate(String userId);
            }
            
        ```
        #### LockModeType.NONE vs LockModeType.OPTIMISTIC
        #### 낙관적 lock을 적용할 도메인 엔티티에 version 컬럼을 선언하고 
        #### @Version을 적용한 상황에서 해당 엔티티를 조회하는 쿼리의 Lock 모드가 NONE으로 되어있는 경우,
        #### 트랜잭션들이 데이터를 조회하는 시점의 무결성을 보장해주지 않는다. 
        #### 하지만 엔티티를 조회하는 쿼리의 Lock 모드가 OPTIMISTIC으로 되어있는 경우엔,
        #### 트랜잭션들이 데이터를 조회하는 시점의 무결성을 보장해줄 수 있다. Lock 모드가 OPTIMISTIC 이면, 
        #### 데이터를 조회하는 시점부터 낙관적 Lock을 이용해 쓰기 작업에 의한 충돌을 감지하기 때문이다.
        #### 이로 인해, LockMode가 NONE인 경우의 낙관적 lock이 LockMode가 OPTIMISTIC 경우의 낙관적 lock보다 
        #### 성능면에서는 우수할 수 있지만, 충돌이 상대적으로 많은 상황에선 데이터 정합성이 부족할 수 있다.
        #### 특정 유저의 포인트의 충전/차감의 경우엔 현실적으로 충돌이 많지 않고, 충돌 발생 시 재시도 하지 않으므로  
        #### LockMode를 NONE을 선택하였다.
    
       #### 낙관적 lock을 위한 UserRepository 구현체 OptimisticUserRepositoryImpl
        ```java
                @Repository
                @Profile("optimistic-lock")
                @RequiredArgsConstructor
                public class OptimisticUserRepositoryImpl implements UserRepository {
                private final OptimisticUserRepository optimisticUserRepository;
                
                    @Override
                    public User findUserByUserIdWithLock(String userId) {
                        return optimisticUserRepository.findUserByUserIdForUpdate(userId);
                    }
                
                
                    @Override
                    public void createUser(User user) {
                        optimisticUserRepository.save(user);
                    }
                
                
                    @Override
                    public Optional<User> findUserByUserId(String userId){
                        return optimisticUserRepository.findUserByUserId(userId);
                    }
                
                    @Override
                    public User saveUser(User user){
                        return optimisticUserRepository.save(user);
                    }
                }

        ```

      * 배타 lock
        #### 특정 유저의 포인트 충전 요청은 금액과 관련되어 데이터 정합성이 매우 중요할 수 있기 때문에
        #### 안전하게 READ/UPDATE 위한 배타 lock이 유리할 수 있다.
      
        #### 비관적 lock을 위한 UserJPARepository 인터페이스 PessimisticUserJPARepository
          ```java
            public interface PessimisticUserJPARepository extends UserJPARepository {

                  @Lock(LockModeType.PESSIMISTIC_WRITE)
                  @Query("SELECT u FROM User u WHERE u.userId = :userId")
                  User findUserByUserIdForUpdate(String userId);

            }
           ```
        #### 비관적 lock을 위한 UserRepository 구현체 PessimisticUserRepositoryImpl
          ```java
                @Repository
                @Profile("pessimistic-lock")
                @RequiredArgsConstructor
                public class PessimisticUserRepositoryImpl implements UserRepository {

                private final PessimisticUserJPARepository pessimisticUserJPARepository;

                @Override
                public User findUserByUserIdWithLock(String userId) {
                        return pessimisticUserJPARepository.findUserByUserIdForUpdate(userId);
                }


                @Override
                public void createUser(User user) {
                    pessimisticUserJPARepository.save(user);
                }
            
            
                @Override
                public Optional<User> findUserByUserId(String userId){
                    return pessimisticUserJPARepository.findUserByUserId(userId);
                }
            
                @Override
                public User saveUser(User user){
                    return pessimisticUserJPARepository.save(user);
                }
            
            }
         ```
      * Test 
    
        ```java
            @SpringBootTest
            @Testcontainers
            @ActiveProfiles("optimistic-lock")
            @Slf4j
            public class PointHistoryConcurrencyTest {
            
            @Autowired
            private UserRepository userRepository;
            
            @Autowired
            private PointHistoryService pointHistoryService;
            
            private static final String TEST_USER_ID = UUID.randomUUID().toString(); // 테스트용 유저 ID
            
            private static final long USER_INIT_POINT = 10000L;
            
            User saveUser;
            
            @BeforeEach
            void setUp(){
            
                User user = User.builder()
                        .userId(TEST_USER_ID)
                        .userName("tester")
                        .gender(UserGenderType.MALE)
                        .age(31)
                        .point(USER_INIT_POINT)
                        .build();
            
                log.info("user id : {}", user.getUserId());
            
                userRepository.saveUser(user);
            
                saveUser = userRepository.findUserByUserId(user.getUserId()).orElseThrow(()->{
                    log.info("find user fail in id({})", user.getUserId() );
                    return null;
                });
            
                log.info("save user id : {}", saveUser.getUserId());
            
            }
            
            @Test
            public void 동일한_userId로_여러_개의_포인트_충전_요청이_동시에_들어오는_경우_정확한_값으로_계산되어야_한다() throws InterruptedException {
            
                log.info("save user id : {}", saveUser.getUserId());
            
                long startTime;
            
                long endTime;
            
                // 쓰레드 설정
                int threadCount = 5;
                ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
                CountDownLatch latch = new CountDownLatch(threadCount);
            
            
                // 각 쓰레드에서 충전할 포인트
                int chargePointPerThread = 10000;
            
                // 포인트 충전 DTO 생성
                PointHistoryDTOParam pointHistoryDTOParam = PointHistoryDTOParam.builder()
                        .userId(saveUser.getUserId())
                        .type(PointTransactionType.CHARGE)
                        .amount(chargePointPerThread)
                        .build();
            
                log.info("pointHistoryDTOParam user id : {}", pointHistoryDTOParam.userId());
            
                // 동시 실행 결과를 저장할 리스트
                List<Future<Boolean>> results = new ArrayList<>();
            
                for(int i = 0; i < threadCount; i++){
                    results.add(executorService.submit(()->{
                        try{
                            latch.countDown();
                            latch.await();
                            // 서비스에서 포인트 충전
                            pointHistoryService.insertChargeUserPointHistory(pointHistoryDTOParam);
                            log.info("충전 성공");
            
                            return true;
                        }catch (ServiceDataNotFoundException e){
                            log.info("ServiceDataNotFoundException error : {}, {}, {}", e.getMessage(), e.getCause(), e.getStackTrace() );
            
                            return false;
                        }catch (DomainModelParamInvalidException e){
                            log.info("DomainModelParamInvalidException error : {}, {}, {}", e.getMessage(), e.getCause(), e.getStackTrace() );
            
                            return false;
                        }
            
                    }));
                }
            
                startTime = System.currentTimeMillis();
            
                executorService.shutdown();
                executorService.awaitTermination(1, TimeUnit.MINUTES);
            
                endTime = System.currentTimeMillis();
            
                // 결과 확인
                long successCount = results.stream().filter(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        return false;
                    }
                }).count();
            
                long resultUserPoint = USER_INIT_POINT + (successCount * chargePointPerThread);
            
                User user = userRepository.findUserByUserId(saveUser.getUserId()).orElseThrow();
            
                log.info("유저 포인트 : {}", user.getPoint() );
            
                log.info("포인트 충전 내역 결과 포인트: {}", resultUserPoint);
            
                Assertions.assertThat(user.getPoint()).isEqualTo(resultUserPoint);
            
                log.info("실행 시간 : {} ms", endTime - startTime);
             }
            }
        ```
* Case 2 : Transaction B (특정 콘서트 실제 공연 좌석 예약) 동시에 여러 개 발생
  * Seat 테이블의 특정 Seat Row와 ConcertDetail 테이블의 특정 ConcertDetail Row에 대한 상태 수정 동시성 처리 필요
    * 낙관적 lock 
      #### 좌석 예약의 경우, 특정 하나의 좌석에 대해서 경합이 실제로 많이 일어날 것이기 때문에 낙관적 lock을 사용할 시, 
      #### 비관적 lock을 사용할 때보다 상대적으로 데이터의 무결성과 정합성을 보장하기는 어렵지만, 
      #### 데이터베이스 자원의 잠금없이 좌석을 한 자리 먼저 차지하면, 다른 요청들은 전부 예외를 발생시키기 때문에 동시성 처리의 성능면에서는 낙관적 lock이 유리할 수 있다. 
    
      #### 좌석 예약 시 낙관적 lock을 사용하기 위한 좌석 도메인 엔티티 Seat와 콘서트 실제 공연 도메인 엔티티 ConcertDetail version 컬럼 선언
      ```java
          @Getter
          @Setter
          @Entity
          @Builder
          @AllArgsConstructor
          @NoArgsConstructor(access = AccessLevel.PROTECTED)
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
              private int seatNumber;
          }
      ```
      ```java
          @Getter
          @Setter
          @Entity
          @Builder
          @AllArgsConstructor
          @NoArgsConstructor(access = AccessLevel.PROTECTED)
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
      }

      ```
      #### 낙관적 lock을 위한 SeatJPARepository 인터페이스 OptimisticSeatJPARepository
       ```java
          public interface OptimisticSeatJPARepository extends SeatJPARepository {
              @Lock(LockModeType.OPTIMISTIC)
              @Query(value = "SELECT s from Seat s WHERE s.seatStatus = :seatStatus")
              Optional<List<Seat>> findSeatsBySeatStatusForUpdate(@Param("seatStatus") SeatStatusType seatStatus);
            
              @Lock(LockModeType.OPTIMISTIC)
              @Query(value = "SELECT s from Seat s WHERE s.seatId = :seatId")
              Optional<Seat> findSeatBySeatIdForUpdate(@Param("seatId") Long seatId);
          }
       ```
      #### 낙관적 lock을 위한 ConcertDetailJPARepository 인터페이스 OptimisticConcertDetailJPARepository
       ```java
          public interface OptimisticConcertDetailJPARepository extends ConcertDetailJPARepository{
               @Lock(LockModeType.OPTIMISTIC)
               @Query(value = "SELECT cd from ConcertDetail cd WHERE cd.concertDetailId = :concertDetailId")
               Optional<ConcertDetail> findConcertDetailByConcertDetailIdForUpdate(@Param("concertDetailId") Long concertDetailId);
          }
       ```
    
      #### 포인트 충전 차감과 달리 좌석 예약의 경우엔 충돌 발생이 많을 것이므로, 낙과적 lock을 사용하는 경우에도
      #### LockMode를 OPTIMISTIC으로 하여, 조회 시점부터 낙관적 lock을 적용한 충돌 감지를 하도록 하였다
      #### 좌석 예약의 경우, 낙관적 lock 충돌이 발생해도 재시도하지 않을 것이므로 데이터 무결성을 
      #### 포기하고, LockMode를 NONE으로 하여 빠르게 처리를 하는 것이 낫지 않나 싶었지만, 
      #### 그것보다 충돌이 많이 발생하는 것에 대한 제어가 중요하다 판단해, 낙관적 lock의 LockMode를 OPTIMISTIC으로 하였다.
    
      #### 낙관적 lock을 위한 SeatRepository 구현체 OptimisticSeatRepositoryImpl
      ```java
           @Repository
           @RequiredArgsConstructor
           @Profile(value = "optimistic-lock")
           public class OptimisticSeatRepositoryImpl implements SeatRepository {
                  private final OptimisticSeatJPARepository optimisticSeatJPARepository;

                  @Override
                  public Optional<List<Seat>> findReservableSeatsByConcertDetailIdAndSeatStatusType(Long concertDetailId, SeatStatusType seatStatus){
                      return optimisticSeatJPARepository.findSeatsByConcertDetailIdAndSeatStatus(concertDetailId, seatStatus);
                  }
            
                  @Override
                  public Optional<Seat> findSeatBySeatIdWithLock(Long seatId){
                      return optimisticSeatJPARepository.findSeatBySeatIdForUpdate(seatId);
                  }
            
                  @Override
                  public void save(Seat seat){
                      optimisticSeatJPARepository.save(seat);
                  }
            
                  @Override
                  public List<Seat> findSeatsByConcertDetailId(Long concertDetailId){
                      return optimisticSeatJPARepository.findSeatsByConcertDetailId(concertDetailId);
                  }
            
                  @Override
                  public Optional<List<Seat>> findSeatsBySeatStatusWithLock(SeatStatusType seatStatusType){
                      return optimisticSeatJPARepository.findSeatsBySeatStatusForUpdate(SeatStatusType.OCCUPIED);
                  }
           }
      ```
      #### 낙관적 lock을 위한 ConcertDetailRepository 구현체 OptimisticConcertDetailRepositoryImpl
      ```java
           @Repository
           @RequiredArgsConstructor
           @Profile(value = "optimistic-lock")
           public class OptimisticConcertDetailRepositoryImpl implements ConcertDetailRepository {
                 private final OptimisticConcertDetailJPARepository optimisticConcertDetailJPARepository;

                 @Override
                 public Optional<List<ConcertDetail>> findConcertDetailsByConcertBasicIdAndConcertDetailStatus(Long concertBasicId, ConcertDetailStatusType concertDetailStatus){
                      return optimisticConcertDetailJPARepository.findConcertDetailsByConcertBasicIdAndConcertDetailStatus(concertBasicId, concertDetailStatus);
                 }

                  @Override
                  public Optional<ConcertDetail> findConcertDetailByConcertDetailIdWithLock(Long concertDetailId){
                      return optimisticConcertDetailJPARepository.findConcertDetailByConcertDetailIdForUpdate(concertDetailId);
                  }

                  @Override
                  public ConcertDetail save(ConcertDetail concertDetail){
                          return optimisticConcertDetailJPARepository.save(concertDetail);
                  }
          }
      ```
      * 배타 lock
        #### 좌석 예약의 경우, 특정 하나의 좌석에 대해서 경합이 실제로 많이 일어날 것이기 때문에 비관적 lock을 사용할 시,
        #### 데이터베이스 자원을 잠금으로 인한, 성능 저하 이슈가 발생할 수 있지만, 확실한 데이터 정합성을 보장해줄 수 있다.
      
        #### 배타 lock을 위한 SeatJPARepository 인터페이스 PessimisticSeatRepository
        ```java
            public interface PessimisticSeatJPARepository extends SeatJPARepository {

            @Lock(LockModeType.PESSIMISTIC_WRITE)
            @Query(value = "SELECT s from Seat s WHERE s.seatStatus = :seatStatus")
            Optional<List<Seat>> findSeatsBySeatStatusForUpdate(@Param("seatStatus") SeatStatusType seatStatus);
        
            @Lock(LockModeType.PESSIMISTIC_WRITE)
            @Query(value = "SELECT s from Seat s WHERE s.seatId = :seatId")
            Optional<Seat> findSeatBySeatIdForUpdate(@Param("seatId") Long seatId);
            }
        ```
        #### 배타 lock을 위한 ConcertDetailJPARepository 인터페이스 PessimisticConcertDetailRepository
        ```java
           public interface PessimisticConcertDetailJPARepository extends ConcertDetailJPARepository {
                @Lock(LockModeType.PESSIMISTIC_WRITE)
                @Query(value = "SELECT cd from ConcertDetail cd WHERE cd.concertDetailId = :concertDetailId")
                Optional<ConcertDetail> findConcertDetailByConcertDetailIdForUpdate(@Param("concertDetailId") Long concertDetailId);
           }
        ```
        #### 배타 lock을 위한 SeatRepository 구현체 PessimisticSeatRepositoryImpl
        ```java
                @Repository
                @RequiredArgsConstructor
                @Profile(value = "pessimistic-lock")
                public class PessimisticSeatRepositoryImpl implements SeatRepository {
                        private final PessimisticSeatJPARepository pessimisticSeatJPARepository;

                        @Override
                        public Optional<List<Seat>> findReservableSeatsByConcertDetailIdAndSeatStatusType(Long concertDetailId, SeatStatusType seatStatus){
                            return pessimisticSeatJPARepository.findSeatsByConcertDetailIdAndSeatStatus(concertDetailId, seatStatus);
                        }
                    
                        @Override
                        public Optional<Seat> findSeatBySeatIdWithLock(Long seatId){
                            return pessimisticSeatJPARepository.findSeatBySeatIdForUpdate(seatId);
                        }
                    
                        @Override
                        public void save(Seat seat){
                            pessimisticSeatJPARepository.save(seat);
                        }
                    
                        @Override
                        public List<Seat> findSeatsByConcertDetailId(Long concertDetailId){
                            return pessimisticSeatJPARepository.findSeatsByConcertDetailId(concertDetailId);
                        }
                    
                        @Override
                        public Optional<List<Seat>> findSeatsBySeatStatusWithLock(SeatStatusType seatStatusType){
                            return pessimisticSeatJPARepository.findSeatsBySeatStatusForUpdate(SeatStatusType.OCCUPIED);
                        }
                }
        ```
        #### 배타 lock을 위한 ConcertDetailRepository 구현체 PessimisticConcertDetailRepositoryImpl
        ```java
                @Repository
                @RequiredArgsConstructor
                @Profile(value = "pessimistic-lock")
                public class PessimisticConcertDetailRepositoryImpl implements ConcertDetailRepository {
                private final PessimisticConcertDetailJPARepository pessimisticConcertDetailJPARepository;
                
                    @Override
                    public Optional<List<ConcertDetail>> findConcertDetailsByConcertBasicIdAndConcertDetailStatus(Long concertBasicId, ConcertDetailStatusType concertDetailStatus){
                        return pessimisticConcertDetailJPARepository.findConcertDetailsByConcertBasicIdAndConcertDetailStatus(concertBasicId, concertDetailStatus);
                    }
                
                    @Override
                    public Optional<ConcertDetail> findConcertDetailByConcertDetailIdWithLock(Long concertDetailId){
                        return pessimisticConcertDetailJPARepository.findConcertDetailByConcertDetailIdForUpdate(concertDetailId);
                    }
                
                    @Override
                    public ConcertDetail save(ConcertDetail concertDetail){
                        return pessimisticConcertDetailJPARepository.save(concertDetail);
                    }
                }
        ```        
        #### lock 발생하는 SeatService 메소드 
         ```java
                @Validated(CreateReservations.class)
                public void checkReservableOfConcertDetailAndSeat(List<@Valid SeatDTOParam> seatDTOParamList) {
                    seatDTOParamList.stream().forEach(seatDTOParam -> {
    
                        Seat seat = seatRepository.findSeatBySeatIdWithLock(seatDTOParam.seatId()).orElseThrow(()->{
                            throw new ServiceDataNotFoundException(ErrorCode.SEAT_NOT_FOUND_BY_SEAT_ID, "CONCERT DETAIL SERVICE", "checkReservableOfConcertDetail");
                        });
            
                        ConcertDetail concertDetail = concertDetailRepository.findConcertDetailByConcertDetailIdWithLock(seat.getConcertDetailId())
                                .orElseThrow(()->{
                                    throw new ServiceDataNotFoundException(ErrorCode.CONCERT_DETAIL_NOT_FOUND, "CONCERT DETAIL SERVICE", "checkReservableOfConcertDetail");
                                });
            
                        concertDetail.checkReservable();
            
                        seat.checkReservable();
            
                    });
                }
         ```
         ```java
                @Validated({CreateReservations.class, ProcessPayment.class})
                public void updateStatusOfConcertDetailAndSeats(List<@Valid SeatDTOParam> seatDTOParamList, SeatStatusType seatStatus) {
                seatDTOParamList.stream().forEach(
                seatDTOParam -> {
            
                                Seat seat = seatRepository.findSeatBySeatIdWithLock(seatDTOParam.seatId()).orElseThrow(
                                        ()->{
                                            throw new ServiceDataNotFoundException(ErrorCode.SEAT_NOT_FOUND_BY_SEAT_ID, "SEAT SERVICE", "updateStatusOfSeats");
                                        }
                                );
            
                                seat.updateSeatStatus(seatStatus);
            
                                seat.updateExpiredAt(LocalDateTime.now().plusMinutes(5));
            
                                seatRepository.save(seat);
            
                                ConcertDetail concertDetail = concertDetailRepository.findConcertDetailByConcertDetailIdWithLock(seat.getConcertDetailId()).orElseThrow(
                                        ()->{
                                            throw new ServiceDataNotFoundException(ErrorCode.CONCERT_DETAIL_NOT_FOUND, "SEAT SERVICE", "updateStatusOfSeats");
                                        }
                                );
            
                                concertDetail.setConcertDetailStatus(ConcertDetailStatusType.COMPLETED);
            
                                List<Seat> seatList = seatRepository.findSeatsByConcertDetailId(concertDetail.getConcertDetailId());
            
                                seatList.stream().forEach(s->{
                                    if(s.getSeatStatus() == SeatStatusType.RESERVABLE){
                                        concertDetail.setConcertDetailStatus(ConcertDetailStatusType.RESERVABLE);
                                    }
                                });
            
                                concertDetailRepository.save(concertDetail);
            
                            }
                    );
                }
         ```
    * Test
        ```java
        @SpringBootTest
        @Testcontainers
        @ActiveProfiles("pessimistic-lock")
        @Slf4j
        public class ConcertReservationConcurrencyTest {
           @Autowired
           ConcertReserveAdminFacade concertReserveAdminFacade;

            @Autowired
            UserRepository userRepository;

            @Autowired
            SeatRepository seatRepository;

            @Autowired
            ConcertDetailRepository concertDetailRepository;

            @Autowired
            ReservationRepository reservationRepository;

            @Autowired
            PaymentRepository paymentRepository;

            private static final String TEST_USER_ID = UUID.randomUUID().toString();

            private static final long TEST_CONCERT_BASIC_ID = 1L;

            private long TEST_SEAT_ID;

            private long TEST_RESERVATION_ID;

            private long TEST_PAYMENT_ID;

            User saveUser;

            ConcertDetail saveConcertDetail;

            Seat saveSeat;

            Reservation saveReservation;

            Payment savePayment;
        
            @BeforeEach
            void setUp(){
        
                // ConcertDetail 저장
                ConcertDetail concertDetail = ConcertDetail.builder()
                        .concertBasicId(TEST_CONCERT_BASIC_ID)
                        .concertDetailStatus(ConcertDetailStatusType.RESERVABLE)
                        .startTime(LocalDateTime.of(2025, 10, 1, 10, 0))
                        .endTime(LocalDateTime.of(2025, 10, 1, 12, 0))
                        .build();
        
                concertDetailRepository.save(concertDetail);
        
                long TEST_CONCERT_DETAIL_ID = concertDetailRepository.findConcertDetailsByConcertBasicIdAndConcertDetailStatus(TEST_CONCERT_BASIC_ID, ConcertDetailStatusType.RESERVABLE).orElseThrow().getFirst().getConcertDetailId();
        
                // Seat 저장
                Seat seat = Seat.builder()
                                .concertDetailId(TEST_CONCERT_DETAIL_ID)
                                .seatStatus(SeatStatusType.RESERVABLE)
                                .seatNumber(1)
                                .price(50000)
                                .build();
        
                seatRepository.save(seat);
        
                TEST_SEAT_ID = seatRepository.findReservableSeatsByConcertDetailIdAndSeatStatusType(TEST_CONCERT_DETAIL_ID, SeatStatusType.RESERVABLE).orElseThrow().getFirst().getSeatId();
            }
        
            @Test
            @DisplayName("동일한 좌석에 대한 예약 요청 동시에 발생하는 경우, 비관적 lock이면 DomainModelParamInvalidException, 낙관적 lock이면 OptimisticLockException")
            public void 동일한_좌석에_예약_요청이_동시에_발생하는_경우_비관적_lock이면_DomainModelParamInvalidException_낙관적_lock이면_OptimisticLockException() throws InterruptedException {
        
        
                // 쓰레드 설정
                int threadCount = 5;
                ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
                CountDownLatch latch = new CountDownLatch(threadCount);
        
        
                // 좌석 예약 DTOList 생성
                List<ConcertReserveAdminDTOParam> concertReserveAdminDTOParamList = new ArrayList<>();
        
                concertReserveAdminDTOParamList.add(ConcertReserveAdminDTOParam.builder()
                        .userId(TEST_USER_ID)
                        .seatId(TEST_SEAT_ID)
                        .build());
        
                // 동시 실행 결과를 저장할 리스트
                List<Future<Boolean>> results = new ArrayList<>();
        
                for(int i = 0; i < threadCount; i++){
                    results.add(executorService.submit(()->{
                        latch.countDown();
                        latch.await();
        
                        try{
                            concertReserveAdminFacade.insertReservations(concertReserveAdminDTOParamList);
                            return true;
                        }catch (DomainModelParamInvalidException e){
                            return false;
                        }catch(ServiceDataNotFoundException e){
                            return false;
                        }catch (OptimisticLockException e){
                            return false;
                        }
                    }));
                }
        
                executorService.shutdown();
                executorService.awaitTermination(1, TimeUnit.MINUTES);
        
                // 결과 확인
                long successCount = results.stream().filter(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        return false;
                    }
                }).count();
        
                long failureCount = results.stream().filter(future -> {
                    try {
                        return !future.get();
                    } catch (Exception e) {
                        return false;
                    }
                }).count();
        
                // 동시성 테스트 결과 검증
                assertThat(successCount).isEqualTo(1); // 한 요청만 성공해야 함
                assertThat(failureCount).isEqualTo(threadCount - 1); // 나머지 요청은 실패해야 함
            }
        
        
        }
        ```
* Case 3  : Transaction C (특정 결제 정보의 결제 처리) 동시에 여러 개 발생
    * User 테이블의 row에 대한 포인트 수정과, Seat, ConcertDetail, Reservation, Payment 테이블의 row에 대한 상태 수정 동시성 처리 필요
      * 낙관적 lock
      * 비관적 lock
* Case 4  : Transaction A (특정 유저 포인트 충전), Transaction C (결제 시 특정 유저 포인트 차감) 동시에 발생
* Case 5  : Transaction B (특정 콘서트 실제 공연 좌석 예약 상태 변경), Transaction C(결제 시 특정 콘서트 실제 공연 좌석 에약 상태 변경) 동시에 발생
  
