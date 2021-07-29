initDatepicker();
function initDatepicker(){
    $.datepicker.regional["zh-CN"] = { closeText: "关闭", prevText: "&#x3c;上月", nextText: "下月&#x3e;", currentText: "今天", monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"], monthNamesShort: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"], dayNames: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"], dayNamesShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"], dayNamesMin: ["日", "一", "二", "三", "四", "五", "六"], weekHeader: "周", dateFormat: "yy-mm-dd", firstDay: 1, isRTL: !1, showMonthAfterYear: !0, yearSuffix: "年" ,changeYear:true}
    $.datepicker.setDefaults($.datepicker.regional["zh-CN"]);
}
$(function(){

    initMap();
    function initMap(){
        projectId =  id;
        view =initProjectView(view,"projectMapBox",id);
        $.get(BASE_URL +"manage/tools",function(resp){
            $("#tools").html(resp).promise().done(function(){
                initTools(view,'toolsBox','top-right');
                initLayerList(view,'shuiyu','bottomRightBox','bottom-right');
            });
        })
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
        $("#xinxibiao input").each(function () {
            if($(this).attr("name") !=undefined){
                if($(this).val()!=""){
                    if($(this).attr("name") =="mj" || $(this).attr("name") =="ztwfzl"){
                        if(!isNumber($(this).val())){
                            flag =false;
                            if($(this).attr("name") =="mj"){
                                openxmxxTc("用地面积必须为数字,请修改");
                            }else if ($(this).attr("name") =="ztwfzl"){
                                openxmxxTc("渣土挖方总量必须为数字,请修改");
                            }
                        }
                    }
                    if(index ==0){
                        index++;
                        url = url + $(this).attr("name")+ "=" + $(this).val();
                    }else{
                        index++;
                        url = url + "&"+$(this).attr("name")+ "=" + $(this).val();
                    }
                }else{
                    flag =false;
                    openxmxxTc("请将项目信息填写完整再点击保存");
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
            url =url +"&type=add&page="+page;

            //todo
            var request = $.get(url,function(resp){ })
            if(request ){
                showMessage('保存成功',2000,true,'bounceInUp-hastrans','bounceOutDown-hastrans');
            }
        }
    })
    initlist();
    function initlist(){
        $.get(BASE_URL +"manage/xcList?glxmId="+id,function(resp){
            $("#xcContentBox").html(resp);
        })
        $.get(BASE_URL +"manage/file/list?glxmId="+id,function(resp){
            $("#right").html(resp);
        })
    }
    $("#quedin").click(function () {
        $("#tc").hide();
    })
    $("#backIndex").click(function () {
        var url = BASE_URL + "manage/index?page="+page;
        $.get(url,function(resp){
            $("#viewDiv").html(resp);
        })
    })
    $("#outputDt").click(function () {
        outputMap(projectId);
    })
    $("#outputData").click(function () {
        $("#xinxibiao").jqprint({
            debug: false,
            importCSS: true,
            printContainer: true,
            operaSupport: false
        });
    })
    
})
function openUploadFile() {
    $(".mask").unbind();
    if($("#tc").find("#tcBox").find("#uploadTc").length >0){
        $("#tc").find("#tcBox").find("#uploadTc").show();
    }else{
        $("#tc").find("#tcBox").empty();
        $("#tc").find("#tcBox").append($("#uploadTc"));
        $("#tc").find("#tcBox").find("#uploadTc").show();
    }
    $("#tc").show();
}

function openxmxxTc(text){
    $(".mask").unbind();
    $("#xmxxTc").find(".contain").html(text);
    if($("#tc").find("#tcBox").find("#xmxxTc").length >0){
        $("#tc").find("#tcBox").find("#xmxxTc").show();
    }else{
        $("#tc").find("#tcBox").empty();
        $("#tc").find("#tcBox").append($("#xmxxTc"));
        $("#tc").find("#tcBox").find("#xmxxTc").show();
    }
    $("#tc").show();
}
function openxmglTc(text) {
    $(".mask").unbind();
    $("#xmglTc").find(".contain").html(text);
    if($("#tc").find("#tcBox").find("#xmglTc").length >0){
        $("#tc").find("#tcBox").find("#xmglTc").show();
    }else{
        $("#tc").find("#tcBox").empty();
        $("#tc").find("#tcBox").append($("#xmglTc"));
        $("#tc").find("#tcBox").find("#xmglTc").show();
    }
    $("#xmglTcDelet").unbind();
    $("#xmglTcDelet").html("确认");
    $("#xmglTcCancel").show();

    $("#tc").show();
}