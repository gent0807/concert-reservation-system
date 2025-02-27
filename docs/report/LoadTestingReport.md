## 부하 테스트 보고서 

### 1. 테스트 환경
#### CPU : AMD Ryzen 9 5950X 16-Core
#### ![img_25.png](img_25.png)
#### Memory : 32GB
#### ![img_26.png](img_26.png)
#### DB : MySQL
* #### 유저 테이블 1000건 Random Insert
  ![img_31.png](img_31.png)
  ![img_37.png](img_37.png)
* #### 콘서트 스케줄 테이블 1000건 Random Insert
  ![img_33.png](img_33.png)
  ![img_32.png](img_32.png)
* #### 콘서트 좌석 테이블 50000건 Random Insert
  ![img_36.png](img_36.png)
  ![img_35.png](img_35.png)
* #### Index 
  * #### 콘서트 스케줄
    ![img_29.png](img_29.png)
  * #### 콘서트 좌석
    ![img_28.png](img_28.png)

### 2. 테스트 필요 대상 
  * #### 대기열 토큰 발행 api
    * #### 목적 : 서버 운영 환경에서, 최초 접속(로그인)이 동시 다발적으로 일어나 토큰 발행에 대한 부하가 순간적으로 많이 가해지는 상황이 발생할 것이라 예상되므로 이를 확인하고자 한다. 
    * #### 시나리오: 30초 동안 최대 1000명의 동시 사용자가 토큰 발생을 위해 접속이 몰리는 상황을 설정한다.  
    * #### 테스트 스크립트  
        ```javascript
           import http from 'k6/http';
           import { check, sleep } from 'k6';
        
           export let options = {
               stages: [
                    { duration: '30s', target: 1000 },
               ],
           };
            
           export default function () {
            
                let params = {
                   headers: {
                     // user로 등록되기만 했다면 같은 userId로 중복해서 접속하는 것을 허용했기 때문에 여러 유저 접속을 하나의 userId로 대체하기로 한다.
                     'X-Custom-UserId': 'fdjk553l4l-jrj654gfjl-gjfl90grg-5480gklg',                      
                    },    
                };
            
                let postResponse = http.post('http://localhost:8088/tokens/new', null, params);
            
                check(postResponse, {
                     'POST 요청이 201을 반환해야 함': (res) => res.status === 201,
                });
            
                sleep(1); // 각 요청 후 1초 대기
            }
            
        ```
  * #### 유저 포인트 추가 api
    * #### 목적: 유저 테이블의 포인트 컬럼에 대한 수정이 자주 일어날 것이며, 결제와 같은 다른 비즈니스 로직 내에서 유저 테이블의 포인트 컬럼에 대한 조회가 포함되기 때문에 여러 유저의 포인트 충전과 좌석 예약에 대한 결제가 동시 다발적으로 일어나 유저 테이블을 다루는 지점에서 부하가 순간적으로 많이 가해지는 상황이 발생가능하므로 이를 확인하고자 한다.
    * #### 시나리오: 30초 동안 최대 1000명의 동시 사용자가 자신의 포인트 충전을 요청하는 상황을 설정한다.
    * #### 테스트 스크립트 
      ```javascript
          import http from 'k6/http';
          import { check, sleep } from 'k6';
        
          export let options = {
             stages: [
                 { duration: '30s', target: 1000 },
             ],
          };
            
          export default function () {
    
             // POST 요청
             let payload = JSON.stringify({
                   userId: 'fdjk553l4l-jrj654gfjl-gjfl90grg-5480gklg',
                   type: 0,
                   amount: 30000,
             });
            
            let postResponse = http.post('http://localhost:8088/pointHistories/new', payload, null);
            
            check(postResponse, {
                'POST 요청이 201을 반환해야 함': (res) => res.status === 201,
            });
            
            sleep(1); // 각 요청 후 1초 대기
        }
            
      ```
  * #### 예약 가능 콘서트 스케줄 목록 조회 api
    * #### 목적: 실제 서비스 환경에서 가장 많이 이용될 api이며, 콘서트 스케줄 테이블에는 lock이 사용되고 있고, 콘서트 좌석 예약과 결제 비즈니스 로직 내에서 콘서트 스케줄 테이블의 상태 컬럼에 대한 조회와 수정이 포함되기 때문에 이 지점에서 부하가 순간적으로 많이 가해지는 상황이 발생가능하므로 이를 확인하고자 한다.
    * #### 시나리오: 30초 동안 최대 1000명까지 동시에 예약 가능한 콘서트 스케줄 목록 조회를 위해 접속이 몰리는 상황을 설정한다.
    * #### 테스트 스크립트  
       ```javascript
          import http from 'k6/http';
          import { check, sleep } from 'k6';
        
          export let options = {
             stages: [
                 { duration: '30s', target: 1000 }, 
             ],
          };
            
          export default function () {
              let concertBasicId = 1;
    
              let params = {
                   headers: {
                     'Authorization' : 1, 
                     // user로 등록되기만 했다면 같은 userId로 중복해서 접속하는 것을 허용했기 때문에 여러 유저 접속을 하나의 userId로 대체하기로 한다.
                     'X-Custom-UserId': 'fdjk553l4l-jrj654gfjl-gjfl90grg-5480gklg',                      
                    },    
              };
      
              // GET 요청
              let getResponse = http.get(`http://localhost:8088/concert-details/${concertBasicId}/reservable`, params);
            
              check(getResponse, {
                  'GET 요청이 200을 반환해야 함': (res) => res.status === 200,
              });
            
              sleep(1); // 각 요청 후 1초 대기
         }
        ```
  * #### 예약 가능 콘서트 좌석 목록 조회 api
    * #### 목적: 예약 가능 콘서트 스케줄 목록에서 선택되어 이용되기 때문에 예약 가능 콘서트 스케줄 목록 조회 api보단 이용률이 낮겠지만, 순간적으로 엄청난 요청이 몰릴 수 있는 api이며, 좌석 테이블에는 lock이 사용되고 있고, 예약과 결제 비즈니스 로직 내에서 좌석 테이블을 다루는 지점에서 부하가 예상되므로 이를 확인하고자 한다.
    * #### 시나리오: 30초 동안 최대 1000명까지 동시에 예약 가능한 콘서트 좌석 목록 조회를 위해 접속이 몰리는 상황을 설정한다.
    * #### 테스트 스크립트
       ```javascript
          import http from 'k6/http';
          import { check, sleep } from 'k6';
        
          export let options = {
             stages: [
                 { duration: '30s', target: 1000 }, 
             ],
          };
            
          export default function () {
              let concertDetailId = 1;
                
              let params = {
                   headers: {
                     'Authorization' : 1, 
                     // user로 등록되기만 했다면 같은 userId로 중복해서 접속하는 것을 허용했기 때문에 여러 유저 접속을 하나의 userId로 대체하기로 한다.
                     'X-Custom-UserId': 'fdjk553l4l-jrj654gfjl-gjfl90grg-5480gklg',                      
                    },    
              };
    
              // GET 요청
              let getResponse = http.get(`http://localhost:8088/seats/${concertDetailId}/reservable`, params);
            
              check(getResponse, {
                  'GET 요청이 200을 반환해야 함': (res) => res.status === 200,
              });
            
              sleep(1); // 각 요청 후 1초 대기
         }
        ```
  * #### 콘서트 좌석 예약 api
    * #### 목적 : 티켓팅 시 특정 콘서트 좌석에 대한 예약 요청이 동시에 일어나 순간적으로 부하가 치솟는 상황이 발생할 것이라 예상되므로 이를 확인하고자 한다.
    * #### 시나리오 : 30초 동안 최대 1000명까지 동시에 특정 하나의 좌석에 대한 예약 요청을 위해 접속이 몰리는 상황을 설정한다.
    * #### 테스트 스크립트 
       ```javascript
          import http from 'k6/http';
          import { check, sleep } from 'k6';
        
          export let options = {
             stages: [
                 { duration: '30s', target: 1000 }, 
             ],
          };
            
          export default function () {
      
              let params = {
                   headers: {
                     'Authorization' : 1, 
                     // user로 등록되기만 했다면 같은 userId로 중복해서 접속하는 것을 허용했기 때문에 여러 유저 접속을 하나의 userId로 대체하기로 한다.
                     'X-Custom-UserId': 'fdjk553l4l-jrj654gfjl-gjfl90grg-5480gklg',                      
                    },    
              };
      
             // POST 요청
             let payload = JSON.stringify([{                   
                   seatId: 1,
                   userId: 'fdjk553l4l-jrj654gfjl-gjfl90grg-5480gklg',
             }]);
    
              // POST 요청
              let postResponse = http.post(`http://localhost:8088/reservations`, payload, params);
            
              check(postResponse, {
                 'POST 요청이 201을 반환해야 함': (res) => res.status === 201,
              });
            
              sleep(1); // 각 요청 후 1초 대기
         }
      ```
  * #### 좌석 예약 정보 결제 api
    * #### 목적 : 결제 상태를 완료 상태로 수정하는 요청이 빈번하게 발생하므로 이를 확인하고자 한다.
    * #### 시나리오 : 30초 동안 최대 1000명까지 동시에 자신이 예약한 좌석에 대한 결제 정보를 결제 완료 상태로 수정하는 상황을 설정한다.
    * #### 테스트 스크립트
      ```javascript
          import http from 'k6/http';
          import { check, sleep } from 'k6';
        
          export let options = {
             stages: [
                 { duration: '30s', target: 1000 }, 
             ],
          };
            
          export default function () {
              
              let paymentId =  1 + __ITER;
                
              let params = {
                   headers: {
                     'Authorization' : 1, 
                     // user로 등록되기만 했다면 같은 userId로 중복해서 접속하는 것을 허용했기 때문에 여러 유저 접속을 하나의 userId로 대체하기로 한다.
                     'X-Custom-UserId': 'fdjk553l4l-jrj654gfjl-gjfl90grg-5480gklg',                      
                    },    
              };
              
              // POST 요청
              let postResponse = http.put(`http://localhost:8088/payments/${paymentId}`, null, params);
            
              check(postResponse, {
                 'POST 요청이 201을 반환해야 함': (res) => res.status === 201,
              });
            
              sleep(1); // 각 요청 후 1초 대기
         }
      ```
### 3. 테스트 결과와 분석

### 4. 장애 대응
    