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