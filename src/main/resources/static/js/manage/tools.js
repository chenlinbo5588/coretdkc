
$(function(){

    $("#toolsButton").click(function () {
        $("#toolsButtonBox").toggle();
    })
    $("#tuceng").click(function () {
        $("#slideBox").hide();
        $("#tucengSelectBox").toggle();
    })
    $(".toolsItemButton").click(function () {

        $("#toolsButtonBox").hide();
        var id = $(this).attr('id');
        if(id== "cj"){
            createcj();
        }else if(id=="cm"){
            createcm();
        }else if(id=="fx"){
            openBigMap();
        }else if(id=="import"){
            inputshp();
        }else if(id=="outputshp"){
            dowloadOutputShp(projectId);
        }else if(id="outputdwg"){
            dowloadOutputDwg(projectId);
        }
    })

    function createcj() {
        drawTool.view = view;
        var action = drawTool.create("polyline", {mode: "click"});
        action.on("vertex-add", function (evt) {
            dist(view, evt);
        });
        action.on("vertex-remove", function (evt) {
            dist(view, evt);
        });
        action.on("cursor-update", function (evt) {
            dist(view, evt);
        });
        action.on("draw-complete", function (evt) {
            dist(view, evt);
        });

    }
    function createcm() {
        drawTool.view = view;
        var action = drawTool.create("polygon", {mode: "click"});
        action.on("vertex-add", function (evt) {
            lateralArea(view, evt);
        });
        action.on("vertex-remove", function (evt) {
            lateralArea(view, evt);
        });
        action.on("cursor-update", function (evt) {
            lateralArea(view, evt);
        });
        action.on("draw-complete", function (evt) {
            lateralArea(view, evt);
        });

    }
    function inputshp() {
        var file =$("#shpFile");
        file.click();
    }
    $("#shpFile").unbind();
    $("#shpFile").change(function () {
        loadshp("shpFile",view,true);
    })
    $(".toolsmask").click(function () {
        $(".viewDivMapBox").hide();
        $(".toolsmask").hide();
        $("#searchBox").hide();
        view.graphics.removeAll();
        view.container = "projectMapBox";

    })
    $(".viewDivMapTitle img").click(function () {
        $(".viewDivMapBox").hide();
        $(".mask").hide();
        $("#searchBox").hide();
        view.graphics.removeAll();
        view.container = "projectMapBox";
    })

    function openBigMap() {

        openCkMap();
        view.graphics.removeAll();
        drawTool.view = view;
        var drawAction = drawTool.create("polygon", {mode: "click"});
        drawAction.on("vertex-add", function (evt) {
            analyseM(view,evt)
        });
        drawAction.on("vertex-remove" , function (evt) {
            analyseM(view,evt)
        });
        drawAction.on("cursor-update",function (evt) {
            analyseM(view,evt)
        });
        drawAction.on("draw-complete", function (evt) {
            analyseM(view,evt)
        });

    }
    $("[name=fxCloseButton]").click(function () {
        $(".toolsmask").click();
    })


})

