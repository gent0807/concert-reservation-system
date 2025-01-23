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
  1. #### 낙관적 락
      #### 대부분의 트랜잭션은 충돌이 발생하지 않는다고 낙관적으로 가정하는 방법

      #### 낙관적 락은 비관적락과 다르게 데이터베이스가 제공하는 락이 아닌 애플리케이션 레벨에서 락을 구현하게 된다. 
      #### JPA에서는 버전 관리 기능(@Version)을 통해 구현
    
      #### 낙관적 락은 애플리케이션에서 충돌을 관리하기에 트랜잭션을 커밋하기 전까지는 충돌을 알 수 없으며 
      #### 락은 충돌 발생 시점에만 데이터에 대한 동시성 제어를 수행하기 때문에, 성능상 이점이 있다.
   
     ![Optimistic_Lock](https://github.com/user-attachments/assets/6471f4b6-3b8a-4396-add2-2f2ca473fe20)

      #### 결과적으로, 낙관적락은 실제로 데이터 충돌이 자주 일어나지 않을것이라고 예상되는 시나리오에서 좋다.                    
                                                                                
      #### 하지만 추가적인 오류 처리가 필요하고 동시 접근이 많이 발생하면 오류 처리를 위해 더 많은 리소스가 소모되는 상황이 만들어질 수 있다.
  2. #### 비관적 락
     1. #### s-lock(공유락)
        #### 공유 락이 걸린 데이터에 대해서는 읽기 연산(SELECT)만 실행 O, 쓰기 연산(WRITE)은 실행 X. 
        #### 즉, 공유 락이 걸린 데이터는 다른 트랜잭션도 똑같이 공유 락을 획득할 수 있으나, 배타 락은 획득할 수 없다. 
        #### 공유 락이 걸려도 읽기 작업은 가능하다는 뜻이다.
        #### 공유 락을 사용하면, 조회한 데이터가 트랜잭션 내내 변경되지 않음을 보장한다.
     2. #### x-lock(배타락)
        #### 배타 락은 쓰기 락(Write Lock)이라고도 불린다. 
        #### 데이터에 대해 배타 락을 획득한 트랜잭션은, 읽기 연산과 쓰기 연산을 모두 실행할 수 있다. 
        #### 다른 트랜잭션은 배타 락이 걸린 데이터에 대해 읽기 작업도, 쓰기 작업도 수행할 수 없다. 
        #### 즉, 배타 락이 걸려있다면 다른 트랜잭션은 공유 락, 배타 락 둘 다 획득 할 수 없다. 
        #### 배타 락을 획득한 트랜잭션은 해당 데이터에 대한 독점권을 갖는 것이다.
 
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
          #### 두 트랜잭션이 특정 자원들의 x-lock 해제될 때까지 대기하여, 두 트랜잭션이 모두 무한 대기에 빠질 수 있다.
 
          ![images_gojung_post_b8c0a12b-30e9-4aaf-97ec-765226db0164_image](https://github.com/user-attachments/assets/9ab4d57a-5f2b-4011-9378-067abed25442)



      5. 비관적 락은 동시에 데이터에 접근하는 트랜잭션과 수정 작업이 많거나, 데이터의 정합성이 중요한 시나리오에 적합하다.                           
      6. 다만, 대기 시간 증가와 교착 상태 발생 가능성, 불필요한 락으로 인한 성능 저하, 동시성 처리 불리 이슈가 발생한다.                             

      
        
       

        
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

* Case 1  : Transaction A (특정 유저의 포인트 충전) 동시에 여러 개 발생
* Case 2  : Transaction B (특정 콘서트 실제 공연 좌석 예약) 동시에 여러 개 발생
* Case 3  : Transaction C (특정 결제 정보의 결제 처리) 동시에 여러개 발생
* Case 4  : Transaction A (특정 유저 포인트 충전), Transaction C (결제 시 특정 유저 포인트 차감) 동시에 발생
* Csae 5  : Transaction B (특정 콘서트 실제 공연 좌석 예약 상태 변경), Transaction C(결제 시 특정 콘서트 실제 공연 좌석 에약 상태 변경) 동시에 발생생
  
