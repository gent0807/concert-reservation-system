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