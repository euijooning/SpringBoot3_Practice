// 삭제 기능
const deleteButton = document.getElementById('delete-btn'); // 삭제 버튼을 가져옵니다.

if (deleteButton) { // 삭제 버튼이 존재하는 경우에만 이벤트 리스너를 추가합니다.
    deleteButton.addEventListener('click', event => { // 삭제 버튼이 클릭되었을 때 실행할 함수를 등록합니다.
        let id = document.getElementById('article-id').value; // HTML에서 article-id라는 요소의 값을 가져와 변수 id에 저장합니다.

        function success() { // 성공했을 때 실행할 함수를 정의합니다.
            alert('삭제가 완료되었습니다.'); // 삭제 완료 메시지를 표시합니다.
            location.replace('/articles'); // 현재 페이지를 '/articles'로 교체하여 이동합니다.
        }

        function fail() { // 실패했을 때 실행할 함수를 정의합니다.
            alert('삭제 실패했습니다.'); // 삭제 실패 메시지를 표시합니다.
            location.replace('/articles'); // 현재 페이지를 '/articles'로 교체하여 이동합니다.
        }

        // httpRequest 함수를 호출하여 HTTP DELETE 요청을 보냅니다.
        // 요청 URL은 '/api/articles/{id}'로 설정되며, 성공 또는 실패에 따라 success 또는 fail 함수가 호출됩니다.
        httpRequest('DELETE', `/api/articles/${id}`, null, success, fail);
    });
}

// 수정 기능
const modifyButton = document.getElementById('modify-btn'); // 수정 버튼을 가져옵니다.

if (modifyButton) { // 수정 버튼이 존재하는 경우에만 이벤트 리스너를 추가합니다.
    modifyButton.addEventListener('click', event => { // 수정 버튼이 클릭되었을 때 실행할 함수를 등록합니다.
        let params = new URLSearchParams(location.search); // 현재 페이지의 URL에서 query parameter를 가져와 params 변수에 저장합니다.
        let id = params.get('id'); // query parameter 중 'id' 값을 가져와 변수 id에 저장합니다.

        // 입력된 제목과 내용을 JSON 형식으로 생성하여 body 변수에 저장합니다.
        let body = JSON.stringify({
            title: document.getElementById('title').value,
            content: document.getElementById('content').value
        });

        function success() { // 성공했을 때 실행할 함수를 정의합니다.
            alert('수정 완료되었습니다.'); // 수정 완료 메시지를 표시합니다.
            location.replace(`/articles/${id}`); // 수정된 게시물 페이지로 이동합니다.
        }

        function fail() { // 실패했을 때 실행할 함수를 정의합니다.
            alert('수정 실패했습니다.'); // 수정 실패 메시지를 표시합니다.
            location.replace(`/articles/${id}`); // 수정된 게시물 페이지로 이동합니다.
        }

        // httpRequest 함수를 호출하여 HTTP PUT 요청을 보냅니다.
        // 요청 URL은 '/api/articles/{id}'로 설정되며, 성공 또는 실패에 따라 success 또는 fail 함수가 호출됩니다.
        httpRequest('PUT', `/api/articles/${id}`, body, success, fail);
    });
}

// 생성 기능
const createButton = document.getElementById('create-btn'); // 생성 버튼을 가져옵니다.

if (createButton) { // 생성 버튼이 존재하는 경우에만 이벤트 리스너를 추가합니다.
    createButton.addEventListener('click', event => { // 생성 버튼이 클릭되었을 때 실행할 함수를 등록합니다.

        // 입력된 제목과 내용을 JSON 형식으로 생성하여 body 변수에 저장합니다.
        let body = JSON.stringify({
            title: document.getElementById('title').value,
            content: document.getElementById('content').value
        });

        function success() { // 성공했을 때 실행할 함수를 정의합니다.
            alert('등록 완료되었습니다.'); // 등록 완료 메시지를 표시합니다.
            location.replace('/articles'); // 게시물 목록 페이지로 이동합니다.
        };

        function fail() { // 실패했을 때 실행할 함수를 정의합니다.
            alert('등록 실패했습니다.'); // 등록 실패 메시지를 표시합니다.
            location.replace('/articles'); // 게시물 목록 페이지로 이동합니다.
        };

        // httpRequest 함수를 호출하여 HTTP POST 요청을 보냅니다.
        // 요청 URL은 '/api/articles'로 설정되며, 성공 또는 실패에 따라 success 또는 fail 함수가 호출됩니다.
        httpRequest('POST', '/api/articles', body, success, fail);
    });
}

// 쿠키를 가져오는 함수
function getCookie(key) {
    var result = null;
    var cookie = document.cookie.split(';'); // 쿠키 문자열을 세미콜론을 기준으로 나눕니다.
    cookie.some(function (item) { // 배열에서 조건을 만족하는 요소를 찾기 위해 some 함수를 사용합니다.
        item = item.replace(' ', ''); // 공백을 제거합니다.

        var dic = item.split('='); // '='을 기준으로 쿠키 이름과 값을 나눕니다.

        if (key === dic[0]) { // 주어진 키와 일치하는 쿠키를 찾으면 해당 값을 result 변수에 저장하고 반복을 중단합니다.
            result = dic[1];
            return true;
        }
    });

    return result; // 찾은 쿠키의 값을 반환합니다.
}

// HTTP 요청을 보내는 함수
function httpRequest(method, url, body, success, fail) {
    fetch(url, { // fetch를 사용하여 HTTP 요청을 보냅니다.
        method: method, // HTTP 메서드를 지정합니다. (GET, POST, PUT, DELETE 등)
        headers: { // 요청 헤더를 설정합니다.
            Authorization: 'Bearer ' + localStorage.getItem('access_token'), // 액세스 토큰을 헤더에 추가합니다.
            'Content-Type': 'application/json', // 요청 본문의 데이터 형식을 JSON으로 지정합니다.
        },
        body: body, // 요청 본문을 설정합니다.
    }).then(response => { // 서버 응답을 처리합니다.
        if (response.status === 200 || response.status === 201) { // 성공적인 응답인 경우
            return success(); // success 함수를 호출합니다.
        }
        const refresh_token = getCookie('refresh_token'); // 쿠키에서 refresh_token을 가져옵니다.
        if (response.status === 401 && refresh_token) { // 인증 오류(401)가 발생하고 refresh_token이 있는 경우
            fetch('/api/token', { // 새로운 액세스 토큰을 요청하기 위해 '/api/token'으로 요청을 보냅니다.
                method: 'POST', // POST 요청을 보냅니다.
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('access_token'), // 액세스 토큰을 헤더에 추가합니다.
                    'Content-Type': 'application/json', // 요청 본문의 데이터 형식을 JSON으로 지정합니다.
                },
                body: JSON.stringify({
                    refreshToken: getCookie('refresh_token'), // refresh_token을 요청 본문에 추가합니다.
                }),
            })
                .then(res => {
                    if (res.ok) {
                        return res.json();
                    }
                })
                .then(result => { // 액세스 토큰 재발급이 성공하면
                    localStorage.setItem('access_token', result.accessToken); // 새로운 액세스 토큰으로 교체합니다.
                    httpRequest(method, url, body, success, fail); // 이전 요청을 다시 보냅니다.
                })
                .catch(error => fail()); // 재발급 실패 시 fail 함수를 호출합니다.
        } else {
            return fail(); // 다른 오류 상태 코드인 경우 fail 함수를 호출합니다.
        }
    });
}