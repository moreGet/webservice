var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
           _this.save();
        });

        /* btn-update란 id를 가진 HTML 엘리먼트에 click 이벤트가 발생할 때 update 함수가 실행하도록
        이벤트 등록 */
        $('#btn-update').on('click', () => {
            _this.update();
        })
    },

    save : function () {
        var data = {
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
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

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
    }
};

main.init();