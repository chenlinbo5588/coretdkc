
$(function() {

    $("img[name=rvOpen]").click(function () {
        $("img[name=rvOpen]").show();
        $(this).hide();
        $("#rvContentBox").toggle();
    })

    $("img[name=rsOpen]").click(function () {
        $("img[name=rsOpen]").show();
        $(this).hide();
        $("#rsContentBox").toggle();
    })
    $("img[name=lkOpen]").click(function () {
        $("img[name=lkOpen]").show();
        $(this).hide();
        $("#lkContentBox").toggle();
    })
    $("img[name=hpOpen]").click(function () {
        $("img[name=hpOpen]").show();
        $(this).hide();
        $("#hpContentBox").toggle();
    })
    $("img[name=acOpen]").click(function () {
        $("img[name=acOpen]").show();
        $(this).hide();
        $("#acContentBox").toggle();
    })
    $("img[name=owOpen]").click(function () {
        $("img[name=owOpen]").show();
        $(this).hide();
        $("#owContentBox").toggle();
    })

    $(".fxclick").click(function (e) {
        e.stopPropagation();
        var id = $(this).data("id");
        var layerId = $(this).data("layerid");
        $.get(BASE_URL + "river/waterInfo?id="+id+"&layerId="+layerId,function(resp){
            $(".infoList").hide();
            $("#back").show();
            $("#infoDetail").show();
            $("#infoDetail").html(resp);
        })
    })
    initBack();
    function  initBack() {
        $("#back").unbind();
        $("#back").click(function () {
            $(".infoList").hide();
            $("#fxList").show();
        })
    }

    $("[name=itemClick]").click(function () {
        $("[name=itemClick]").removeClass("itemClick");
        $(this).addClass("itemClick");

        var identification = $(this).data("identification");
        //view.graphics.removeAll();
        for(var i=0;i<fxdata.length;i++){
            if(identification == fxdata[i].attributes.identification){
                if(fxSlectData != undefined){
                    view.graphics.remove(fxSlectData);
                }
                view.goTo(fxdata[i].geometry.extent.expand(1)).then(function() {
                    var selectionSymbol={
                        type:"simple-fill",
                        size:10,
                        outline:{
                            color:"red",
                            width:2
                        }
                    };
                    fxdata[i].symbol= selectionSymbol;
                    fxSlectData = fxdata[i];
                    view.graphics.add(fxSlectData);
                });
                break;
            }
        }
    })



})

