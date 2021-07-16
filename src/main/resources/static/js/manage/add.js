

$(function(){
    initDatepicker();
    function initDatepicker(){
        $.datepicker.regional["zh-CN"] = { closeText: "关闭", prevText: "&#x3c;上月", nextText: "下月&#x3e;", currentText: "今天", monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"], monthNamesShort: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"], dayNames: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"], dayNamesShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"], dayNamesMin: ["日", "一", "二", "三", "四", "五", "六"], weekHeader: "周", dateFormat: "yy-mm-dd", firstDay: 1, isRTL: !1, showMonthAfterYear: !0, yearSuffix: "年" }
        $.datepicker.setDefaults($.datepicker.regional["zh-CN"]);
    }


    $( ".datepicker" ).datepicker({
        showOtherMonths: true,
        selectOtherMonths: true,
    });
    $("#baocun").click(function () {
        var flag = true;
        var index =0;
        if(id !=null){
            var url = BASE_URL +"manage/edit?id="+id;
            index++;
        }else{
            var url = BASE_URL +"manage/add?";
        }
        $("input").each(function () {
            if($(this).attr("name") !=undefined){
                if($(this).val()!=""){
                    if(index ==0){
                        index++;
                        url = url + $(this).attr("name")+ "=" + $(this).val();
                    }else{
                        index++;
                        url = url + "&"+$(this).attr("name")+ "=" + $(this).val();
                    }
                }else{
                    flag =false;
                    if($("#tc").find("#tcBox").find("#xmxxTc").length >0){
                        $("#tc").find("#tcBox").find("#xmxxTc").show();
                    }else{
                        $("#tc").find("#tcBox").empty();
                        $("#tc").find("#tcBox").append($("#xmxxTc"));
                        $("#tc").find("#tcBox").find("#xmxxTc").show();
                    }
                    $("#tc").show();
                    return false;
                }
            }
        })
        if(flag){
            $("textarea").each(function () {
                if($(this).attr("name") !=undefined){
                    if($(this).val()!=""){
                        if(index == 0){
                            index++;
                            url = url + $(this).attr("name")+ "=" + $(this).val();
                        }else{
                            index++;
                            url = url + "&"+$(this).attr("name")+ "=" + $(this).val();
                        }
                    }
                }
            })
            url =url +"&type=add";
            $.get(url,function(resp){
                $("#viewDiv").html(resp);
            })
        }
    })
    initXcList();
    function initXcList(){
        $.get(BASE_URL +"manage/xcList?projectId="+id,function(resp){
            $("#xcContentBox").html(resp);
        })
    }
    $("#quedin").click(function () {
        $("#tc").hide();
    })

})