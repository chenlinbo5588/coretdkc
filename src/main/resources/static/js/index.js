
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
        $("#mask").click(function () {
            $("#tc").hide();
        })
        $("#draw").click(function () {
            var drawAction = drawTool.create("polygon", {mode: "click"});
            drawAction.on("vertex-add", showPolygon);
            drawAction.on("vertex-remove", showPolygon);
            drawAction.on("cursor-update", showPolygon);
            drawAction.on("draw-complete", fxPolygon);
            $("#tc").hide();
        })
    })

    $("#slide").slider({
        value:100,
        step: 10,
        slide: function(event, ui) {
            view.map.layers.items[3].opacity = ui.value/100;

        }
    });


})

