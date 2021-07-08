var BASE_URL = "http://localhost:8080/";
var baobiaoSelect =false;
var xmgl =false;
$(function(){

    var animate =false;
    init();

    function init(){

    }
    $("#manage").click(function(){
        $.getScript("static/js/river_manage.js");
    });
    $("#leftBox").mouseenter(function(){
        if(!xmgl){
            $("#searchBox").hide();
            $(this).animate({width:"300px"},function () {
            });
        }else{
            $(this).animate({width:"300px"},function () {
            });
        }

    });
    $("#leftBox").mouseleave(function(){
        if(!xmgl) {
            $(this).animate({width:"60px"},function () {
                $("#searchBox").show();
            });
        }else{
            $(this).animate({width:"60px"},function () {
            });
        }
    });
    $("#searchButton").click(function () {
        var value = $("#searchInput").val();
        if(value == null){
            $("#infoList").empty();
            $("#infoList").hide();
        }else{
            $.get(BASE_URL + "river/indexSearch?value="+value,function(resp){
                $("#infoList").show();
                $("#infoList").html(resp);
            })
        }
    })
    $("#baobiao").click(function () {
        if(baobiaoSelect == false){
            $.get(BASE_URL + "river/baobiao",function(resp){
                $("#baobiaoBox").html(resp);
                $("#baobiaoBox").show();
            })
        }
    })
    $("#xmgl").click(function () {
        $.get(BASE_URL + "manage/index",function(resp){
            xmgl =true;
            $("#viewDiv").empty();
            $("#viewDiv").removeClass();
            $("#searchBox").hide();
            $("#sy").removeClass("indexButtonClick");
            $("#xmgl").addClass("indexButtonClick");
            $("#viewDiv").html(resp);
        })
    })
    $('input[type="checkbox"]').click(function () {
        var name = $(this).data("name");
        view.map.layers.forEach(function (layer) {
            if(layer.title =="Shuiyu"){
                for(var i=0;i<layer.allSublayers.items.length;i++){
                    if(layer.allSublayers.items[i].title == name){
                        layer.allSublayers.items[i].visible =!layer.allSublayers.items[i].visible;
                    }
                }
            }
        })
    })
    $("#tuceng").click(function () {
        $("#tucengSelectBox").toggle();
    })
    $("#riverChange").click(function () {
        $.get(BASE_URL + "river/changeSelect",function(resp){
            $("#viewDiv").removeClass();
            $("#viewDiv").html(resp);
        })
    })

})
