function initTableCheckbox() {
    var $thr = $('table thead tr');
    var $checkAllTh = $('#checkAll');
    /* “全选/反选”复选框 */
    var $checkAll = $thr.find('input');
    $checkAll.click(function(event) {
        /* 将所有行的选中状态设成全选框的选中状态 */
        $tbr.find('input').prop('checked', $(this).prop('checked'));
        /* 并调整所有选中行的CSS样式 */
        if ($(this).prop('checked')) {
            $tbr.find('input').parent().parent().addClass('warning');
        } else {
            $tbr.find('input').parent().parent().removeClass('warning');
        }
        /* 阻止向上冒泡，以防再次触发点击操作 */
        event.stopPropagation();
    });
    /* 点击全选框所在单元格时也触发全选框的点击操作 */
    $checkAllTh.click(function() {
        $(this).find('input').click();
    });
    var $tbr = $('table tbody tr');
    /* 点击每一行的选中复选框时 */
    $tbr.find('input').click(function(event) {
        /* 调整选中行的CSS样式 */
        $(this).parent().parent().toggleClass('warning');
        /* 如果已经被选中行的行数等于表格的数据行数，将全选框设为选中状态，否则设为未选中状态 */
        $checkAll.prop('checked', $tbr.find('input:checked').length == $tbr.length ? true : false);
        /* 阻止向上冒泡，以防再次触发点击操作 */
        event.stopPropagation();
    });
    /* 点击每一行时也触发该行的选中操作 */
    $tbr.click(function() {
        $(this).find('input').click();
    });
}

$('#reload').click(function() {
    $.ajax({
        url : 'getAllMessages.do',
        dataType : 'json',
        success : function(result) {
            if (result.success) {
                var messages = result.messages;
                var innerHTML = '';
                for (var i = 0; i < messages.length; i++) {
                    var id = messages[i].id;
                    var checkboxId = `cb${id}`;
                    var gmtCreate = moment(messages[i].gmtCreate).format('YYYY-MM-DD HH:mm:ss');
                    var gmtModified = moment(messages[i].gmtModified).format('YYYY-MM-DD HH:mm:ss');
                    var title = messages[i].title;
                    var msg = messages[i].msg;
                    if (typeof (msg) == 'undefined') {
                        msg = '';
                    } else {
                        msg = msg.replace(/\r\n/g, '<br>');
                    }
                    var receiver = messages[i].receiver;
                    var bSent = messages[i].bSent;

                    var tdInput = `<td><input type="checkbox" id="${checkboxId}"></td>`;
                    var tdId = `<td name="id">${id}</td>`;
                    var tdGmtCreate = `<td>${gmtCreate}</td>`;
                    var tdGmtModified = `<td>${gmtModified}</td>`;
                    var tdTitle = `<td>${title}</td>`;
                    var tdMsg = `<td>${msg}</td>`;
                    var tdReceiver = `<td>${receiver}</td>`;
                    var tdBSent = `<td>${bSent}</td>`;
                    var tr = `<tr>${tdInput}${tdId}${tdGmtCreate}${tdGmtModified}${tdTitle}${tdMsg}${tdReceiver}${tdBSent}</tr>`;

                    innerHTML += tr;
                }
                $('#tbody').html(innerHTML);

                initTableCheckbox();
            } else {
                alert("reload error");
            }
        },
        error : function(json) {
            alert("reload error");
        }
    });
});

$('#resend').click(function() {
    var ids = [];
    $(':checkbox:checked', '#table').each(function() {
        console.log($(this));
        var tablerow = $(this).parent().parent();
        console.log(tablerow);
        var id = parseInt(tablerow.find('td[name="id"]').text());
        ids.push(id);
    });
    console.log(ids);

    $.ajax({
        url : 'resendMessage.do',
        data : {
            'ids' : JSON.stringify(ids)
        },
        success : function(ret) {
            var result = JSON.parse(ret);
            console.log(result);
            if (result.success) {
                var messages = result.messages;
                var retIds = [];
                for (var i in messages) {
                    retIds.push(messages[i].id);
                }
                alert(`resend id=${retIds} success`);
            } else {
                alert("resendMessage error");
            }
        },
        error : function(json) {
            alert("resendMessages error");
        }
    });
});
