let index = {
        init: function(){
            $("#btn-save").on("click", () => {
                this.save();
            });

        },

        save: function(){
            let data = {
                username: $("#username").val(), //id가 username인 태그의 value를 가져온다
                password: $("#password").val(),
                email: $("#email").val()
            };

            //ajax호출시 default가 비동기 호출
            $.ajax({
                type:"POST",
                url :"/auth/joinProc",
                data: JSON.stringify(data), //javascript 데이터를 JSON으로 변환
                contentType: "application/json; charset=utf-8",
                dataType: "json" //accept헤더에 application/json (서버로부터 json으로 응답을 받기 위함, 이를 javascript object로 변환)
            }).done(function(resp){
                alert("회원가입이 완료되었습니다");
                location.href = "/";
            }).fail(function(error){
                alert(JSON.stringify(error));
            });

        }
}
index.init();