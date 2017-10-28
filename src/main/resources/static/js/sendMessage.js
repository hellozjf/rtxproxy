$('#submit').click(function() {
    $.ajax({
        type : 'post',
        url : 'sendMessage.do',
        data : {
            'title' : $('#title').val(),
            'msg' : $('#msg').val(),
            'receiver' : $('#receiver').val()
        },
        dataType : 'json',
        success : function(result) {
            console.log(result);
            if (result.success) {
                var message = result.message;
                console.log(message);
                $('#dialogId').text(message.id);
                $('#dialogGmtCreate').text(message.gmtCreate);
                $('#dialogGmtModified').text(message.gmtModified);
                $('#dialogTitle').text(message.title);
                $('#dialogMsg').text(message.msg);
                $('#dialogReceiver').text(message.receiver);
                $('#dialogBSent').text(message.bSent);
                $('#myModal').modal('show');
            } else {
                alert("sendMessage error");
            }
        },
        error: function(json){  
            alert("sendMessage error");  
        } 
    });
});
