/**
 * 
 */
$('#load').click(function() {
    $.ajax({
        url : 'getConfig.do',
        success : function(ret) {
            var result = JSON.parse(ret);
            console.log(result);
            if (result.success) {
                var config = result.config;
                $('#serverIp').val(config.serverIp);
                $('#serverPort').val(config.serverPort);
                $('#sendUrl').val(config.sendUrl);
                $('#sendInterval').val(config.sendInterval);
            } else {
                alert("getConfig error");
            }
        },
        error : function(json) {
            alert("getConfig error");
        }
    });
});

$('#save').click(function() {
    $.ajax({
        url : 'setConfig.do',
        data : {
            'serverIp' : $('#serverIp').val(),
            'serverPort' : $('#serverPort').val(),
            'sendUrl' : $('#sendUrl').val(),
            'sendInterval' : $('#sendInterval').val()
        },
        success : function(ret) {
            var result = JSON.parse(ret);
            console.log(result);
            if (result.success) {
                alert('更新配置成功')
            } else {
                alert('更新配置失败');
            }
        },
        error : function(ret) {
            alert('setConfig error');
        }
    });
});

$('#download').click(function() {
    window.location.href = 'downloadConfig.do';
});

$(document).ready(function() {
    $('#load').click();
});