var BASE_URL = "http://localhost:8080/";

$("#manage").click(function(){
    $.getScript("static/js/river_manage.js");
});

$("#leftBox").mouseenter(function(){
    $("#searchBox").hide();
    $(this).animate({width:"300px"},function () {
    });
});
$("#leftBox").mouseleave(function(){
    $(this).animate({width:"60px"},function () {
        $("#searchBox").show();
    });
});
$("#searchButton").click(function () {
    var value = $("#searchInput").val();
    $.get(BASE_URL + "/river/indexSearch?value="+value,function(resp){
        $("#infoList").html(resp);
    })
})