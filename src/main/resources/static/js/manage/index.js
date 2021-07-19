

$(function(){

    var id ="";
    var name;
    var flag =false;
    $("#xmglTcCancel").unbind();
    $("#xmglTcDelet").unbind();
    $(".titleClick").click(function () {
        var paixu =$(this).data("paixu");
        if($(this).hasClass("paixushang")){
            paixuTj = paixu + ",desc";
            changpaixu(paixuTj);
            $(this).removeClass("paixushang");
            $(this).addClass("paixuxia");
        }else if($(this).hasClass("paixuxia")){
            paixuTj = paixu + ",asc";
            changpaixu(paixuTj);
            $(this).removeClass("paixuxia");
            $(this).addClass("paixushang");
        }else{
            paixuTj = paixu + ",asc";
            changpaixu(paixuTj);
            $(this).addClass("paixushang");
        }
    })
    $("#first").click(function () {
        if(page !=minPage){
            changePage(minPage);
        }
    })
    $("#last").click(function () {
        if(page !=maxPage){
            changePage(maxPage);
        }
    })
    $("#next").click(function () {
        if(page <maxPage){
            page = page+1
        }else{
            page =1;
        }
        changePage(page);
    })
    $("#previous").click(function () {
        if(page >minPage){
            page = page-1
        }else{
            page =maxPage;
        }
        changePage(page);
    })
    $(".pageItem").click(function () {
        var index = $(this).data("page");
        if(page != index){
            changePage(index);
        }
    })
    function changePage(index) {
        var value = $("#xmhlSearch").val();
        page = index;
        if(paixuTj ==null){
            if (value !=""){
                var url = BASE_URL + "manage/index?page="+index+"&value="+ value;
            }else{
                var url = BASE_URL + "manage/index?page="+index;
            }
            $.get(url,function(resp){
                $("#viewDiv").html(resp);
            })
        }else{
            changpaixu(paixuTj);
        }
    }

    function changpaixu(paixuValue){
        var value = $("#xmhlSearch").val();
        if (value !=""){
            var url = BASE_URL + "manage/index?page="+page+"&value="+ value+"&paixu="+paixuValue;
        }else{
            var url = BASE_URL + "manage/index?page="+page+"&paixu="+paixuValue;
        }
        $.get(url,function(resp){
            $("#viewDiv").html(resp);
        })
    }
    function deleteByid(id){
        var url = BASE_URL + "manage/delete?id="+id;
        $.get(url,function(){
            changePage(page);
        })
    }
    function deleteByidList(id){
        var url = BASE_URL + "manage/deleteByList?idList="+id;
        $.get(url,function(){
            changePage(page);
        })
    }

    $("[name=pname]").click(function () {
        var id = $(this).data("id");
        $.get(BASE_URL + "manage/edit?id="+id+"&page="+page,function(resp){
            $("#viewDiv").html(resp);
        })
    })
    $("[name=xmAdd]").click(function () {
        $.get(BASE_URL + "manage/add?page"+page,function(resp){
            $("#viewDiv").html(resp);
        })
    })

    $("#xmglSearch").click(function () {
        changePage(1);
    })
    $("[name=delete]").click(function () {
        name = $(this).data("name");
        id = $(this).data("id");
        $("#xmglTc").find(".contain").html("是否删除"+name+"项目?");
        if($("#tc").find("#tcBox").find("#xmglTc").length >0){
            $("#tc").find("#tcBox").find("#xmglTc").show();
        }else{
            $("#tc").find("#tcBox").empty();
            $("#tc").find("#tcBox").append($("#xmglTc"));
            $("#tc").find("#tcBox").find("#xmglTc").show();
        }
        $("#xmglTcDelet").unbind();
        $("#xmglTcDelet").html("删除");
        $("#xmglTcCancel").show();
        $("#xmglTcDelet").click(function () {
            $("#tc").hide();
            deleteByid(id);
        })
        $("#tc").show();
    })
    $("#xmglTcCancel").click(function () {
        $("#tc").hide();
    })

    $("[name=alldelete]").click(function () {

        id="";
        $('input[type="checkbox"]:checked').each(function () {
            id=id+","+$(this).val();
        })
        if(id == ""){
            $("#xmglTc").find(".contain").html("请选择至少一条项目");
            if($("#tc").find("#tcBox").find("#xmglTc").length >0){
                $("#tc").find("#tcBox").find("#xmglTc").show();

            }else{
                $("#tc").find("#tcBox").empty();
                $("#tc").find("#tcBox").append($("#xmglTc"));
                $("#tc").find("#tcBox").find("#xmglTc").show();
            }
            $("#tc").show();
            $("#xmglTcDelet").unbind();
            $("#xmglTcDelet").html("确定");
            $("#xmglTcCancel").hide();
            $("#xmglTcDelet").click(function () {
                    $("#xmglTcDelet").html("删除");
                    $("#xmglTcCancel").show();
                    $("#tc").hide();
            })
        }else{
            $("#xmglTc").find(".contain").html("是否删除选中项目?");
            if($("#tc").find("#tcBox").find("#xmglTc").length >0){
                $("#tc").find("#tcBox").find("#xmglTc").show();
            }else{
                $("#tc").find("#tcBox").empty();
                $("#tc").find("#tcBox").append($("#xmglTc"));
                $("#tc").find("#tcBox").find("#xmglTc").show();

            }
            $("#tc").show();
            $("#xmglTcDelet").unbind();
            $("#xmglTcDelet").html("删除");
            $("#xmglTcCancel").show();
            $("#xmglTcDelet").click(function () {
                if(id !=""){
                    $("#tc").hide();
                    deleteByidList(id);
                }
            })
        }



    })



    $(".allselect").click(function () {
        if(!flag){
            flag=true;
            $('input[type="checkbox"]').prop("checked",true);
        }else{
            flag=false;
            $('input[type="checkbox"]').prop("checked",false);
        }
    })


})