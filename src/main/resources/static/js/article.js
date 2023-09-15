// 1. 삭제 기능

// 'delete-btn' 아이디를 가진 버튼 요소를 찾아서 deleteButton 변수에 할당
const deleteButton = document.getElementById('delete-btn');

// 만약 deleteButton 요소가 존재한다면 아래의 이벤트 리스너를 등록
if (deleteButton) {
    // 'click' 이벤트가 발생했을 때 아래 함수 실행
    deleteButton.addEventListener('click', event => {
        // 숨겨진 입력 필드에서 블로그 글의 ID 값을 가져옴
        let id = document.getElementById('article-id').value;

        // 서버에 HTTP DELETE 요청을 보내서 블로그 글 삭제
        fetch(`/api/articles/${id}`, {
            method: 'DELETE'
        })
            // 서버 응답을 받으면 다음 작업 수행
            .then(() => {
                // 삭제 완료 메시지를 경고창으로 표시
                alert('삭제가 완료되었습니다.');

                // 현재 페이지를 '/articles' 경로로 다시 로드 (페이지 새로고침)
                location.replace('/articles');
            });
    });
}
