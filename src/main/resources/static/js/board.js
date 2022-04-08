let index = {
        init: function(){
            $("#btn-save").on("click", () => {
                this.save();
            });
             $("#btn-delete").on("click", () => {
                this.deleteById();
             });
            $("#btn-update").on("click", () => {
                this.update();
             });
             $("#btn-reply-save").on("click", () => {
                 this.replySave();
              });
        },

        save: function(){
            let data = {
                title: $("#title").val(),
                content: $("#content").val(),
            };

            $.ajax({
                type:"POST",
                url :"/api/board",
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            }).done(function(resp){
                alert("글쓰기가 완료되었습니다");
                location.href = "/";
            }).fail(function(error){
                alert(JSON.stringify(error));
            });
        },

           deleteById: function(){
                    let id = $("#boardId").text();

                    $.ajax({
                        type:"DELETE",
                        url :"/api/board/" + id,
                        dataType: "json"
                    }).done(function(resp){
                        alert("삭제가 완료되었습니다");
                        location.href = "/";
                    }).fail(function(error){
                        alert(JSON.stringify(error));
                    });

                },

                 update: function(){
                           let boardId = $("#boardId").val();
                            let data = {
                                title: $("#title").val(),
                                content: $("#content").val()
                            };

                            $.ajax({
                                type:"PUT",
                                url :"/api/board/"+boardId,
                                data: JSON.stringify(data),
                                contentType: "application/json; charset=utf-8",
                                dataType: "json"
                            }).done(function(resp){
                                alert("업데이트가 완료되었습니다");
                                location.href = "/";
                            }).fail(function(error){
                                alert(JSON.stringify(error));
                            });
                        },

                        replySave: function(){
                                    let data = {
                                        content: $("#reply-content").val(),
                                    };
                                    let boardId = $("#boardId").text()

                                    $.ajax({
                                        type:"POST",
                                        url :"/api/board/" +boardId + "/reply",
                                        data: JSON.stringify(data),
                                        contentType: "application/json; charset=utf-8",
                                        dataType: "json"
                                    }).done(function(resp){
                                        alert("댓글 작성이 완료되었습니다");
                                        location.href = "/board/" + boardId;
                                    }).fail(function(error){
                                        alert(JSON.stringify(error));
                                    });
                                },

                                replyDelete: function(boardId, replyId){
                                    $.ajax({
                                        type:"DELETE",
                                        url :"/api/board/" +boardId + "/reply/" +replyId,
                                        dataType: "json"
                                    }).done(function(resp){
                                        alert("댓글삭제 성공");
                                        location.href = "/board/" + boardId;
                                    }).fail(function(error){
                                        alert(JSON.stringify(error));
                                    });
                            }
}
index.init();