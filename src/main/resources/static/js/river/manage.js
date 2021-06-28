

$(function(){

    console.log("manage.js");
    init();
    function init(){
        $(".riverSelectItem").each(function () {
            var flag= $(this).data('id');
            if(flag == "1"){
                $(this).addClass("click");
            }
        });
    }


    $(".riverSelectItem").click(function () {
        var flag= $(this).data('id');
        if(flag == "0"){
            $(this).data("id", "1");
            $(this).addClass("click");
        }else{
            $(this).data("id", "0");
            $(this).removeClass("click");
        }
    });

    $("#search").click(function(){

        var value = $("#searchValue").val();
        var searchTj = "";
        $(".click").each(function(){
            searchTj = searchTj + $(this).text() + ',';
        })

        $.get(BASE_URL + "/river/manage?value="+value+"&type="+searchTj,function(resp){
            $("#leftBox").html(resp);
        })

    });

    $(".searchResultItem").click(function(){

        var id = $(this).data('id');
        // $.ajax({
        //     url:BASE_URL+"queryById/"+id,
        //     data:{
        //         "mapurl" :featureLayer.parsedUrl.path,
        //         "outFields" :"*",
        //     },
        //     dataType:"json",
        //     type:"post",
        //     traditional:true,
        //     cache:false,
        //     async:false,
        //     success:function(result){
        //         console.log(result);
        //         ShowFindResult(result);
        //     }
        // });
        findObjectId(id);



    });

})