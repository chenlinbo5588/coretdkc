
$(function(){


    $.get(BASE_URL + "/river/manage",function(resp){
        $("#leftBox").html(resp);

    })


})