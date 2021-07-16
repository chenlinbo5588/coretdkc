
var leftView;
var rightView;



$(function() {

    leftView =initLeftView(leftView,"xinView");
    rightView =initRightView(rightView,"jiuView");

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
    ], function (LayerList) {
        var layerList = new LayerList({
            view: leftView,
            listItemCreatedFunction: function(event) {
                const item = event.item;
                if(item.title == "Shuiyu"){
                    item.title ="水域图层"
                }else if(item.title == "Xzj"){
                    item.title ="其他图层"
                }else if(item.title == "Bgtx"){
                    item.title ="变更图形图层"
                }else if(item.title == "ditu"){
                    item.title ="自定义影像"
                }
            }
        },"shuiyu");
        rightView.ui.add(document.getElementById("tucengSelectBox"), "bottom-right");
    });
    $("#bottomRightBox").show();
    $("#leftViewBox").show();
    $("#rightViewBox").show();

    init();
    function init(){
        $(".searchBox").hide();
        $("#bgSearchBox").show();
    }

    $("#tucengButton").click(function () {
        $("#tucengSelectBox").toggle();
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


