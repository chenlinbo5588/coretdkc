$(function () {
    $(".datepicker").datepicker({
        showOtherMonths: true,
        selectOtherMonths: true,
    });
    $("#xcSave").click(function () {

        if (glxmId != 0) {
            var flag = true;
            var index = 0;

            var url = BASE_URL + "manage/xc/add?glxmId=" + glxmId;
            index++;
            $("#newXcbox input").each(function () {
                if ($(this).attr("name") != undefined) {
                    if ($(this).val() != "") {
                        if (index == 0) {
                            index++;
                            url = url + $(this).attr("name") + "=" + $(this).val();
                        } else {
                            index++;
                            url = url + "&" + $(this).attr("name") + "=" + $(this).val();
                        }
                    } else {
                        flag = false;
                        openxmxxTc("请将巡查信息填写完整");
                        return false;
                    }
                }
            })
            if (flag) {
                $.get(url, function (resp) {
                    showMessage('保存成功', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                    $("#xcContentBox").html(resp);
                    $("#xcdateS").val(timestampToTime(xcdate));
                })
            }
        } else {
            openxmxxTc("请先新建项目之后再填写巡查记录信息");
        }

    })


    $("[name=delete]").click(function () {

        var name = $(this).data("name");
        var id = $(this).data("id");
        var glxmid = $(this).data("glxmid");

        openxmglTc("是否删除" + name + "的巡查记录?");

        $("#xmglTcCancel").click(function () {
            $("#tc").hide();
        })
        $("#xmglTcDelet").click(function () {
            $("#tc").hide();
            deleteXcByid(id, glxmid);
        })

    })

    function deleteXcByid(id, glxmid) {
        var url = BASE_URL + "manage/xcDelete?id=" + id + "&glxmId=" + glxmid;
        $.get(url, function (resp) {
            $("#xcContentBox").html(resp);
            $("#xcdateS").val(timestampToTime(xcdate));
        })
    }


})