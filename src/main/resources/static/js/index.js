var baobiaoSelect = false;
var xmgl = false;
var animate =true
$(document).bind("ajaxSend", function () {
    $("#loadingMask").show();
}).bind("ajaxComplete", function () {
    $("#loadingMask").hide();
});


$(function () {

    init();

    function init() {
        $("#manage").addClass("cblclick");
        $("#riverChange").removeClass("cblclick");
    }

    $("#manage").click(function () {
        $("#riverChange").removeClass("cblclick");
        $(this).addClass("cblclick");
    });
    $("#leftBox").mouseenter(function () {
        console.log(animate);
        if(animate){
            animate =false;
            if (!xmgl) {
                $("#searchBox").hide();
                $(this).animate({width: "300px"},150, function () {
                    animate =true;
                });
            } else {
                $(this).animate({width: "300px"},150, function () {
                    animate =true;
                });
            }
        }


    });
    $("#leftBox").mouseleave(function () {
        console.log(animate);
        if(animate) {
            animate = false;
            if (!xmgl) {
                $(this).animate({width: "60px"},150, function () {
                    $("#searchBox").show();
                    animate = true;

                });
            } else {
                $(this).animate({width: "60px"},150, function () {
                    animate = true;
                });
            }
        }
    });
    $("#searchInput").bind("input propertychange", function () {
        var value = $(this).val();
        if (value != "") {
            $("#qinkong").show();
        } else {
            $("#qinkong").hide();
        }
    });
    $("#qinkong").click(function () {
        $("#searchInput").val("");
        $("#qinkong").hide();
        $(".infoList").each(function (){
            var id = $(this).attr("id");
            if(id =='back' || id =='close' ){
            }else{
                $(this).empty();
            }
        });
        $(".infoList").hide();

    })
    $("#searchButton").click(autoInput());
    $("#baobiao").click(function () {
        if (baobiaoSelect == false) {
            $.get(BASE_URL + "river/baobiao?type=" + "??????", function (resp) {
                $("#baobiaoBox").html(resp);
                $("#baobiaoBox").show();
            })
        }
    })
    $("#tools").click(function () {
        $(".indexToolsBox").toggle("fast");
        closeCj();
    });



    $("#xcList").mouseenter(function () {
        $("#xcListbox").show();
    })
    $("#xcListbox").mouseleave(function () {
        $("#xcListbox").hide();
    })
    $("[name=xcButton]").click(function () {
        var id = $(this).data("id");

        $("#leftBox").hide();
        $("#fxTishi").hide();
        $.get(BASE_URL + "manage/select?id=" + id + "&type=index", function (resp) {
            xmgl = true;
            $("#viewDiv").empty();
            $("#viewDiv").removeClass();
            $("#searchBox").hide();
            $("#sy").removeClass("indexButtonClick");
            $("#xmgl").addClass("indexButtonClick");
            $("#viewDiv").html(resp);
        })
    })
    $("#xmgl").click(function () {
        $("#leftBox").hide();
        $("#fxTishi").hide();

        $.get(BASE_URL + "manage/index", function (resp) {
            xmgl = true;
            $("#viewDiv").empty();
            $("#viewDiv").removeClass();
            $("#searchBox").hide();
            $("#sy").removeClass("indexButtonClick");
            $("#xmgl").addClass("indexButtonClick");
            $("#viewDiv").html(resp);
        })
    })
    $("#addShp").click(function () {
        var file = $("#shpFile");
        file.click();
    })
    $("#shpFile").change(function () {
        var flag = loadshp("shpFile", view, false);
        $("#tc").hide();
    })
    //????????????????????????
    $("#addText").click(function () {
        $(".fxbuttonbox").hide();
        $("#txtInputBox").show();
    })
    $("#cancel").click(function () {
        $(".fxbuttonbox").show();
        $("#txtInputBox").hide();
    })
    $("#queding").click(function () {
        var value = $("#txtInput").val();
        if (value == "") {
            $(".fxbuttonbox").toast({
                content: '???????????????????????????????????????',
                duration: 1500,
                animateIn: 'bounceInUp-hastrans',
                animateOut: 'bounceOutDown-hastrans',
            });
        } else {
            var val = $('input:radio:checked').val();
            var ring = getPoints(value, val);
            if (ring.length > 0) {
                $("#txtInput").val("");
                var gr = creatrPolgnByRing(ring, view);
                view.graphics.add(gr);
                view.goTo(gr.geometry.extent.expand(1));
                $("#tc").hide();
                showMessage('????????????', 3000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            } else {
                $(".fxbuttonbox").toast({
                    content: '????????????,?????????????????????????????????????????????????????????????????????',
                    duration: 3000,
                    animateIn: 'bounceInUp-hastrans',
                    animateOut: 'bounceOutDown-hastrans',
                });
            }
        }
    })


    /////
    $('input[type="checkbox"]').click(function () {
        var name = $(this).data("name");
        view.map.layers.forEach(function (layer) {
            if (layer.title == "Shuiyu") {
                for (var i = 0; i < layer.allSublayers.items.length; i++) {
                    if (layer.allSublayers.items[i].title == name) {
                        layer.allSublayers.items[i].visible = !layer.allSublayers.items[i].visible;
                    }
                }
            }
        })
    })

    // $("#tuceng").click(function () {
    //     $("#slideBox").hide();
    //     $("#tucengSelectBox").toggle();
    // })
    $("#tuceng").mouseenter(function () {
        $("#slideBox").hide();
        $("#tucengSelectBox").show();
    })
    $("#tucengSelectBox").mouseleave(function () {
        $("#slideBox").hide();
        $("#tucengSelectBox").hide();
    });
    $("#riverChange").click(function () {
        $("#manage").removeClass("cblclick");
        $(this).addClass("cblclick");
        $("#fxTishi").hide();
        $.get(BASE_URL + "river/changeSelect", function (resp) {
            $(".infoList").hide();
            $("#viewDiv").removeClass();
            $("#viewDiv").html(resp);
        })

    })
    $("#fxButoon").click(function () {
        if ($("#tc").find("#tcBox").find("#fxTc").length > 0) {
            $("#tc").find("#tcBox").find("#fxTc").show();
        } else {
            $("#tc").find("#tcBox").empty();
            $("#tc").find("#tcBox").append($("#fxTc"));
            $("#tc").find("#tcBox").find("#fxTc").show();

        }
        $("#tc").show();
        $(".fxbuttonbox").show();
        $("#txtInputBox").hide();

        $("#mask").click(function () {
            $(".fxbuttonbox").show();
            $("#txtInputBox").hide();
            $("#tc").hide();
        })
        $("#draw").click(function () {
            drawing = true;
            view.graphics.removeAll();
            var drawAction = drawTool.create("polygon", {mode: "click"});
            drawAction.on("vertex-add", showPolygon);
            drawAction.on("vertex-remove", showPolygon);
            drawAction.on("cursor-update", showPolygon);
            drawAction.on("draw-complete", fxPolygon);
            $(".fxTishi").show();
            $("#tc").hide();
        })
    })
    $("#fxTishi").draggable();
    $("#fxTishiClose").click(function () {
        $(".fxTishi").hide();
    })


    $("#close").click(function () {
        $(".infoList").hide();
    })


})

