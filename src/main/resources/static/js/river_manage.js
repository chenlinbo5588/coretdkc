
$(function(){

    console.log("river_manage.js");

    $.get(BASE_URL + "/river/manage",function(resp){
        $("#leftBox").html(resp);

    })


})