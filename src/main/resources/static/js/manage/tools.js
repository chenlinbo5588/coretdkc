var project ;
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
            if(daochuStatus == false){
                daochuStatus =true;
                dowloadOutputShp(projectId);
            }else{
                showMessage('导出中，请勿重复点击s', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }

        }else if(id="outputdwg"){
            if(daochuStatus == false){
                daochuStatus =true;
                dowloadOutputDwg(projectId);
            }else{
                showMessage('导出中，请勿重复点击', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }

        }
    })

    function createcj() {
        drawTool.view = projectView;
        var action = drawTool.create("polyline", {mode: "click"});
        action.on("vertex-add", function (evt) {
            dist(projectView, evt);
        });
        action.on("vertex-remove", function (evt) {
            dist(projectView, evt);
        });
        action.on("cursor-update", function (evt) {
            dist(projectView, evt);
        });
        action.on("draw-complete", function (evt) {
            dist(projectView, evt);
        });

    }
    function createcm() {
        drawTool.view = projectView;
        var action = drawTool.create("polygon", {mode: "click"});
        action.on("vertex-add", function (evt) {
            lateralArea(projectView, evt);
        });
        action.on("vertex-remove", function (evt) {
            lateralArea(projectView, evt);
        });
        action.on("cursor-update", function (evt) {
            lateralArea(projectView, evt);
        });
        action.on("draw-complete", function (evt) {
            lateralArea(projectView, evt);
        });

    }
    function inputshp() {
        var file =$("#shpFile");
        file.click();
    }
    $("#shpFile").unbind();
    $("#shpFile").change(function () {
        loadshp("shpFile",projectView,true);
    })
    $(".toolsmask").click(function () {
        $(".viewDivMapBox").hide();
        $(".toolsmask").hide();
        $("#searchBox").hide();
        projectView.graphics.removeAll();
        projectView.container = "projectMapBox";

    })
    $(".viewDivMapTitle img").click(function () {
        $(".viewDivMapBox").hide();
        $(".mask").hide();
        $("#searchBox").hide();
        projectView.graphics.removeAll();
        projectView.container = "projectMapBox";
    })

    function openBigMap() {

        openCkMap();
        projectView.graphics.removeAll();
        drawTool.view = projectView;
        var drawAction = drawTool.create("polygon", {mode: "click"});
        drawAction.on("vertex-add", function (evt) {
            analyseM(projectView,evt)
        });
        drawAction.on("vertex-remove" , function (evt) {
            analyseM(projectView,evt)
        });
        drawAction.on("cursor-update",function (evt) {
            analyseM(projectView,evt)
        });
        drawAction.on("draw-complete", function (evt) {
            analyseM(projectView,evt)
        });

    }
    $("[name=fxCloseButton]").click(function () {
        $(".toolsmask").click();
    })


})

