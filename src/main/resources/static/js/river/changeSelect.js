
var leftView;
var rightView;



$(function() {

    leftView = initLeftView(leftView,"xinView");
    rightView = initRightView(rightView,"jiuView");
    bindView();

    leftView.ui.add(document.getElementById("leftViewBox"), {
        position: "top-right",
        index: 1
    });
    rightView.ui.add(document.getElementById("rightViewBox"), {
        position: "top-right",
        index: 1
    });
     require([
        "esri/widgets/LayerList",
        "esri/widgets/TimeSlider",
        "esri/TimeExtent",
        "esri/layers/FeatureLayer",
    ],  (LayerList,TimeSlider,TimeExtent,FeatureLayer)=> {

        // var timeSlider = new TimeSlider({
        //     container: "timeSlider",
        //     view: leftView,
        //     mode: "time-window",
        //     timeVisible: false, // show the time stamps on the timeslider
        //     loop: false,
        //     layout:"compact",
        //     fullTimeExtent: {
        //         start: new Date(2011, 2, 3),
        //         end: new Date(2022, 2, 5)
        //     },
        //     timeExtent: {
        //         start: new Date(2011, 2, 1),
        //         end: new Date(2022, 2, 28)
        //     }
        // });
        // timeSlider.watch("timeExtent", (timeExtent) => {
        //     console.log("Time extent now starts at", timeExtent.start, "and finishes at:", timeExtent.end);
        // });


        var layerList = new LayerList({
            view: rightView,
            listItemCreatedFunction: defineActions
        },"rshuiyu");
        layerList.on("trigger-action", function(event) {
            var id = event.action.id;
            if(  rslideName == event.item.title){
                $("#rslideBox").hide();
                rslideName = null;
            }else{
                rslideName = event.item.title;

                $("#rslideBox").show();
                if (id === "opacity") {
                    if(event.item.layer.opacity == undefined){
                        $("#rslideIndex").html(event.item.layer.layer.opacity*100 + "%");
                        $("#rslide").slider({
                            value:event.item.layer.layer.opacity*100,
                            step: 10,
                            slide: function(event2, ui) {
                                $("#rslideIndex").html(ui.value + "%");
                                event.item.layer.layer.opacity = ui.value/100;
                            }
                        });
                    }else{
                        $("#rslideIndex").html(event.item.layer.opacity*100 + "%");
                        $("#rslide").slider({
                            value:event.item.layer.opacity*100,
                            step: 10,
                            slide: function(event2, ui) {
                                $("#rslideIndex").html(ui.value + "%");
                                event.item.layer.opacity = ui.value/100;
                            }
                        });
                    }
                }
            }

        });
        var layerList2 = new LayerList({
            view: leftView,
            listItemCreatedFunction:defineActions
        },"lshuiyu");
        layerList2.on("trigger-action", function(event) {
            var id = event.action.id;
            if(  lslideName == event.item.title){
                $("#lslideBox").hide();
                lslideName = null;
            }else{
                lslideName = event.item.title;
                $("#lslideBox").show();
                if (id === "opacity") {
                    if(event.item.layer.opacity == undefined){
                        $("#lslideIndex").html(event.item.layer.layer.opacity*100 + "%");
                        $("#lslide").slider({
                            value:event.item.layer.layer.opacity*100,
                            step: 10,
                            slide: function(event2, ui) {
                                $("#lslideIndex").html(ui.value + "%");
                                event.item.layer.layer.opacity = ui.value/100;
                            }
                        });
                    }else{
                        $("#lslideIndex").html(event.item.layer.opacity*100 + "%");
                        $("#lslide").slider({
                            value:event.item.layer.opacity*100,
                            step: 10,
                            slide: function(event2, ui) {
                                $("#lslideIndex").html(ui.value + "%");
                                event.item.layer.opacity = ui.value/100;
                            }
                        });
                    }
                }
            }

        });
    });
    $("#bottomRightBox").show();
    $("#leftViewBox").show();
    $("#rightViewBox").show();

    init();
    function init(){
        $(".searchBox").hide();
        $("#bgSearchBox").show();




    }

    $("#rightTucengButton").mouseenter(function () {
        $("#rslideBox").hide();
        $("#rightTucengSelectBox").show();
    })
    $("#rightTucengSelectBox").mouseleave(function () {
        $("#rslideBox").hide();
        $("#rightTucengSelectBox").hide();
    })

    $("#leftTucengButton").mouseenter(function () {
        $("#lslideBox").hide();
        $("#leftTucengSelectBox").show();
    })
    $("#leftTucengSelectBox").mouseleave(function () {
        $("#lslideBox").hide();
        $("#leftTucengSelectBox").hide();
    })


    $(".ssButton img").click(function () {
        $(".ssButton img").toggle();
        $(".contentBox").toggle();
        var value = $("#bgSearchInput").val();
        console.log(value);
        if(value !=""){
            $("#bgInfoList").toggle();
        }
    })
    
    $(".name").click(function () {
        var code = $(this).data("code");
        var id = $(this).data("id");
        $(".ssButton img").toggle();
        $(".contentBox").toggle();
        $("#bgList").show();

        $.get(BASE_URL + "river/changeDetail?id=" + id, function (resp) {
            $("#changeDetail").html(resp);
        })
        changeFindByCode(code,leftView,rightView);
    });

    $("#leftBox").mouseenter(function () {
        $("#bgSearchBox").hide();
    });
    $("#leftBox").mouseleave(function () {
        $("#bgSearchBox").show();
    });

    function bindView() {
        var flagL=false,flagR=false;
        leftView.watch("extent",function(){
            if(flagL){
                var Lextent=leftView.extent;
                rightView.extent= Lextent;
                flagR=false;
            }else if(!flagL){
                flagL=true;
            }
        });
        rightView.watch("extent",function(){
            if(flagR){
                var Rextent=rightView.extent;
                leftView.extent= Rextent;
                flagL=false;
            }else if(!flagR){
                flagR=true;
            }
        });
    }


    $("#bgSearchInput").bind("input propertychange", function () {
        var value = $(this).val();
        if (value != "") {
            $("#bgQinkong").show();
        } else {
            $("#bgQinkong").hide();
        }
    });
    $("#bgQinkong").click(function () {
        $("#bgSearchInput").val("");
        $("#bgQinkong").hide();
        $("#bgInfoList").empty();
        $("#bgInfoList").hide();

    })
    $("#fxbbButton").click(function () {
        $.get(BASE_URL + "river/fx/baobiao", function (resp) {
            $("#fxBaobiaoBox").html(resp);
            $("#fxBaobiaoBox").show();
        })
    })
    $("#bgSearchButton").click(function () {
        var value = $("#bgSearchInput").val();
        if (value == "") {
            $("#bgInfoList").empty();
            $("#bgInfoList").hide();
        } else {
            $.get(BASE_URL + "river/bgSearch?value=" + value, function (resp) {
                $(".ssButton img #open").hide();
                $(".ssButton img #close").show();
                $(".contentBox").hide();
                $("#bgInfoList").show();
                $("#bgInfoList").html(resp);
            })
        }
    })

})


