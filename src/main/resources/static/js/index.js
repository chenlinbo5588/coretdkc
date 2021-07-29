
var baobiaoSelect = false;
var xmgl = false;
$(function () {

    var animate = false;
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
        if (!xmgl) {
            $("#searchBox").hide();
            $(this).animate({width: "300px"}, function () {
            });
        } else {
            $(this).animate({width: "300px"}, function () {
            });
        }

    });
    $("#leftBox").mouseleave(function () {
        if (!xmgl) {
            $(this).animate({width: "60px"}, function () {
                $("#searchBox").show();
            });
        } else {
            $(this).animate({width: "60px"}, function () {
            });
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
        $(".infoList").empty();
        $(".infoList").hide();

    })
    $("#searchButton").click(function () {
        var value = $("#searchInput").val();
        if (value == "") {
            $(".infoList").empty();
            $(".infoList").hide();
        } else {
            $.get(BASE_URL + "river/indexSearch?value=" + value, function (resp) {
                $(".infoList").hide();
                $("#infoList").show();
                $("#infoList").html(resp);
            })
        }
    })
    $("#baobiao").click(function () {
        if (baobiaoSelect == false) {
            $.get(BASE_URL + "river/baobiao?type=" + "河道", function (resp) {
                $("#baobiaoBox").html(resp);
                $("#baobiaoBox").show();
            })
        }
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
        var file =$("#shpFile");
        file.click();
    })
    $("#shpFile").change(function () {
        var flag = loadshp("shpFile",view,false);
        $("#tc").hide();
        if(flag){
            showMessage('加载成功',3000,true,'bounceInUp-hastrans','bounceOutDown-hastrans');
        }else{
            showMessage('加载失败,请检查文件正确性',3000,true,'bounceInUp-hastrans','bounceOutDown-hastrans');
        }

    })
    //根据文字添加图形
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
        if(value == ""){
            $(".fxbuttonbox").toast({
                content:'请先输入坐标内容再点击确认',
                duration:1500,
                animateIn:'bounceInUp-hastrans',
                animateOut:'bounceOutDown-hastrans',
            });
        }else{
            var val=$('input:radio:checked').val();
            var ring = getPoints(value,val);
            if(ring.length >0){
                $("#txtInput").val("");
                var gr = creatrPolgnByRing(ring,view);
                view.graphics.add(gr);
                view.goTo(gr.geometry.extent.expand(1));
                $("#tc").hide();
                showMessage('加载成功',3000,true,'bounceInUp-hastrans','bounceOutDown-hastrans');
            }else{
                $(".fxbuttonbox").toast({
                    content:'内容有误,无法解析，请确认选择类型或者文本内容是否错误！',
                    duration:3000,
                    animateIn:'bounceInUp-hastrans',
                    animateOut:'bounceOutDown-hastrans',
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
    $("#tuceng").click(function () {
        $("#slideBox").hide();
        $("#tucengSelectBox").toggle();
    })
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
    $( "#fxTishi" ).draggable();
    $("#fxTishiClose").click(function () {
        $(".fxTishi").hide();
    })


    $("#close").click(function () {
        $(".infoList").hide();
    })


})

