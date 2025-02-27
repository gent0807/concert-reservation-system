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

    // POST 요청
    let payload = JSON.stringify({
        userId: '00365f9d-c6a7-4720-a035-168f15559564',
        type: 0,
        amount: 3,
    });

    let params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    let postResponse = http.post('http://localhost:8088/pointHistories/new', payload, params);

    check(postResponse, {
        'POST 요청이 201을 반환해야 함': (res) => res.status === 201,
    });

    sleep(1); // 각 요청 후 1초 대기
}