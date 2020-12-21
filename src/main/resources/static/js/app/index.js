// function onLoad() {
//     gapi.load('client:auth2', function() {
//         gapi.auth2.init({
//             client_id:'850417348860-6avt5dtq96r8290i7g30d66b882d4vf9.apps.googleusercontent.com'
//         });
//     });
// }

function out() {
    // var auth2 = gapi.auth2.getAuthInstance();
    // auth2.signOut().then(function () {
    //     console.log('User signed out.');
    // });
    auth2.disconnect();
    alert("로그아웃");
}

let main = {
    init : function () {
        let _this = this;

        $('#btn-logout').on('click', () => {
            _this.logout();
        });

        $('#btn-save').on('click', () => {
           _this.save();
        });

        /* btn-update란 id를 가진 HTML 엘리먼트에 click 이벤트가 발생할 때 update 함수가 실행하도록
        이벤트 등록 */
        $('#btn-update').on('click', () => {
            _this.update();
        });

        $('#btn-delete').on('click', () => {
            _this.delete();
        });
    },

    logout : () => {
        out();
    },

    save : function () {
        let data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
           type: 'POST',
           url: '/api/v1/posts',
           dataType: 'json',
           contentType:'application/json; charset=utf-8',
           data: JSON.stringify(data)
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href = '/'; // 글 등록에 성공하면 메인페이지로 이동
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    // 신규로 추가될 update 함수
    update : () => {
        let data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        let id = $('#id').val();

        $.ajax({
            /*HTTP Method중 PUT 메소드를 선택
            api controller에서 이미 put 어노테이션을 선언 하였기 때문에
            Put을 사용해야 함.*/
            type: 'PUT',
            url: '/api/v1/posts/'+id, // 게시글 타겟을 위해 id 추가
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(() => {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(error => {
            alert(JSON.stringify(error));
        });
    },

    delete : () => {
        let id = $('#id').val();

        $.ajax({
           type: 'DELETE',
           url: '/api/v1/posts/'+id,
           dataType: 'json',
           contentType: 'application/json; charset=utf-8'
        }).done(() => {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(error => {
            alert(JSON.stringify(error));
        });
    }
};

main.init();