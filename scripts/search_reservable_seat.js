import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    stages: [
        { duration: '5s', target: 700 },
        { duration: '30s', target: 300 },
        { duration: '5s', target: 1000 },
        { duration: '35s', target: 200 },
    ],
};

export default function () {
    let concertDetailId = 1;

    let params = {
        headers: {
            'Authorization' : 1242,
            // user로 등록되기만 했다면 같은 userId로 중복해서 접속하는 것을 허용했기 때문에 여러 유저 접속을 하나의 userId로 대체하기로 한다.
            'X-Custom-UserId': '00365f9d-c6a7-4720-a035-168f15559564',
        },
    };

    // GET 요청
    let getResponse = http.get(`http://localhost:8088/seats/${concertDetailId}/reservable`, params);

    check(getResponse, {
        'GET 요청이 200을 반환해야 함': (res) => res.status === 200,
    });

    sleep(1); // 각 요청 후 1초 대기
}