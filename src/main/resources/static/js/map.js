
var view;
var map;
var find;
var findparams;
var identify;
var identifyparams;
var qTask ;
var params ;
require([
    "esri/Map",
    "esri/views/MapView",
    "esri/tasks/IdentifyTask",
    "esri/tasks/support/IdentifyParameters",
    "esri/layers/FeatureLayer",
    "esri/Graphic",
    "esri/layers/TileLayer",
    "esri/core/urlUtils",
    "esri/config",
    "esri/layers/WebTileLayer",
    "esri/tasks/QueryTask",
    "esri/tasks/support/Query",
    "esri/layers/MapImageLayer",
    "esri/tasks/FindTask",
    "esri/tasks/support/FindParameters",
    "esri/tasks/IdentifyTask",
    "esri/tasks/support/IdentifyParameters",
], function (Map,MapView, IdentifyTask, IdentifyParameters,FeatureLayer, Graphic, TileLayer, urlUtils,esriConfig,WebTileLayer,QueryTask,Query,MapImageLayer,FindTask,FindParameters) {

    urlUtils.addProxyRule({
        urlPrefix: "192.168.5.120/arcgis", // specify resource location
        proxyUrl: "http://localhost:8080/river/proxy" // specify location of proxy file
    });
    var tdt_token = "fac43bd612f98b93bacda49ccb3af69c";
    var tiledLayer = new WebTileLayer({
        title:"天地图",
        urlTemplate:
            "http://{subDomain}.tianditu.gov.cn/DataServer?T=img_w&x={col}&y={row}&l={level}&tk="+tdt_token,
        subDomains: ["t0", "t1", "t2", "t3","t4", "t5", "t6", "t7"],
    });

    var tiledLayer_poi = new WebTileLayer({
        title:"天地图标注",
        urlTemplate:
            "http://{subDomain}.tianditu.gov.cn/DataServer?T=img_w&x={col}&y={row}&l={level}&tk="+tdt_token,
        subDomains: ["t0", "t1", "t2", "t3","t4", "t5", "t6", "t7"],
    });
    // var template = {
    //     title: "{NAME}",
    //     content: [
    //         {
    //             type: "fields",
    //             fieldInfos: [
    //                 {
    //                     fieldName: "NAME",
    //                     label: "河道",
    //                     format: {
    //                             digitSeparator: true,
    //                             places: 0
    //                     }
    //                 },
    //                 {
    //                     fieldName: "LENGTH",
    //                     label: "河道长度",
    //                     format: {
    //                         digitSeparator: true,
    //                         places: 0
    //                     }
    //                 }
    //             ]
    //         }
    //     ]
    // };

    river = new MapImageLayer({
        url: "http://192.168.5.120/arcgis/rest/services/river/shuiyu/MapServer",
        sublayers: [{
                id: 11,
                title:"其他水域",
                visible: true,
            },{
                title:"其他水域管理范围线",
                id: 10,
                visible: true,
                minScale:6000,

            },{
                title:"人工水道",
                id: 9,
                visible: true,
            },{
                title:"人工水道管理范围线",
                id: 8,
                visible: true,
                minScale:6000,
            },{
                title:"山塘",
                id: 7,
                visible: true,
            },{
                title:"山塘范围线",
                id: 6,
                visible: true,
                minScale:6000,
            },{
                title:"湖泊",
                id: 5,
                visible: true,
            },{
                title:"湖泊范围管理线",
                id: 4,
                visible: true,
                minScale:6000,
            },{
                title:"水库",
                id: 3,
                visible: true,
            },{
                title:"水库范围管理线",
                id: 2,
                visible: true,
                minScale:6000,
            },{
                title:"河道水域",
                id: 1,
                visible: true,
             },{
                title:"河道水域范围管理线",
                id:0,
                visible: true,
                minScale:6000,
             }
        ]
    });


    // var graphicsLayer = new GraphicsLayer();
    //搜索
    qTask = new QueryTask({
        url: "http://192.168.5.120/arcgis/rest/services/river/shuiyu/FeatureServer/1"
    });
    params = new Query({
        returnGeometry: true,
        outFields: ["*"]
    });
    find = new FindTask({
        url:"http://192.168.5.120/arcgis/rest/services/river/shuiyu/MapServer"
    });
    findparams = new FindParameters({
        layerIds: [1],
        searchFields: ["name"]
    });

    map = new Map({
        layers: [ tiledLayer,tiledLayer_poi,river]
    });
    view = new MapView({
        container: "viewDiv",
        map: map,
        center: [121.25962011651083, 30.17229501748913],
        zoom:13
    });

    view.ui.remove('attribution')
    view.ui.remove("zoom");

    view.ui.add(document.getElementById("topRightBox"), {
        position: "top-right",
        index: 1
    });
    view.ui.add(document.getElementById("bottomRightBox"), {
        position: "bottom-right",
        index: 1
    });
    $("#bottomRightBox").show();
    $("#topRightBox").show();

    view.when(function() {
        view.on("click", executeIdentifyTask);

        // Create identify task for the specified map service
        identifyTask = new IdentifyTask({ url: "http://192.168.5.120/arcgis/rest/services/river/shuiyu/MapServer"});

        // Set the parameters for the Identify
        identifyparams = new IdentifyParameters();
        identifyparams.tolerance = 1;
        identifyparams.returnGeometry =true;
        identifyparams.layerIds = [1,3,5,7,9,11];
        identifyparams.layerOption = "visible";
        identifyparams.width = view.width;
        identifyparams.height = view.height;

    });

    function executeIdentifyTask(event) {
        view.graphics.removeAll();

        identifyparams.geometry = event.mapPoint;
        identifyparams.mapExtent = view.extent;
        $("#viewDiv").css("cursor","wait");
        identifyTask
            .execute(identifyparams)
            .then(function(response) {
                var results = response.results;
                console.log(response);
                if (results.length>0){
                    var result = results[0].feature;
                    var layerId = results[0].layerId;

                    view.goTo(result.geometry.extent.expand(1)).then(function() {
                        var selectionSymbol={
                            type:"simple-fill",
                            size:10,
                            outline:{
                                color:"red",
                                width:2
                            }
                        };
                        result.symbol= selectionSymbol;
                        view.graphics.add(result);
                        view.popup.open({
                            title:result.attributes.selectName,
                            location: event.mapPoint
                        });
                    });
                }
                $("#viewDiv").css("cursor","auto");
            })


        // Shows the results of the Identify in a popup once the promise is resolved
        function showPopup(response) {
            if (response.length > 0) {
                view.popup.open({
                    features: response,
                    location: event.mapPoint
                });
            }
            $("#viewDiv").css("cursor","auto");
        }
    }


    // var sketch = new Sketch({
    //     layer: graphicsLayer,
    //     view: view
    // });
    /*var basemapToggle = new BasemapToggle({
        view: view,
        nextBasemap: "hybrid"
    });*/
    // view.ui.add(basemapToggle, 'top-right')

    // view.ui.add(sketch, "top-right");

    // var search = new Search({
    //     view: view,
    //     allPlaceholder: "District or Senator",
    //     activeSourceIndex: 1,
    //     sources: [{
    //         layer: featureLayer,
    //         searchFields: ["NAME"],
    //         displayField: "NAME",
    //         exactMatch: false,
    //         maxResults:6,
    //         maxSuggestions: 6,
    //         name: "网格",
    //         placeholder: "输入网格名称",
    //         outFields: ["Name"],
    //         minSuggestCharacters: 0,
    //     }],
    //
    // });
    // view.ui.add(search, {
    //     position: "top-left",
    //     index: 0
    // });

    //http://www.arcgissdk.com/latest/sample-code/sandbox/index.html?sample=tasks-identify


    // function showInfo(attributes) {
    //     document.getElementById("infoDiv").innerHTML =
    //         "<div class='infoDivBox'>" +
    //         "<div> 名称:" +
    //         attributes.NAME +
    //         "</div>" +
    //         "<div> 长度: " +
    //         attributes.LENGTH +
    //         "</div>"+
    //         "<div> 编号: " +
    //         attributes.CKBH +
    //         "</div>"+
    //         "<div> 起点位置: " +
    //         attributes.SNAME +
    //         "</div>"+
    //         "<div>终点位置: " +
    //         attributes.ENAME +
    //         "</div>"+
    //         "</div>"
    // }



    // var editConfigPointLayer, editConfigPoliceLayer, editConfigLineLayer;
    // view.when(function () {
    //     view.popup.autoOpenEnabled = true; //disable popups
    //     view.map.layers.forEach(function (layer) {
    //        if (layer.title === "Shuiyu") {
    //             editConfigPoliceLayer = {
    //                 layer: layer,
    //                 fieldConfig: [
    //                     {
    //                         name: "NAME",
    //                         label: "河段名称"
    //                     },
    //                     {
    //                         name: "LENGTH",
    //                         label: "河段长度"
    //                     },
    //                     {
    //                         name:"SNAME",
    //                         label:"起点位置名称"
    //                     },
    //                     {
    //                         name:"ENAME",
    //                         label:"终点位置名称"
    //                     }
    //                 ]
    //             };
    //         }
    //     });
    //     var editor = new Editor({
    //         view: view,
    //         layerInfos: [editConfigPoliceLayer],
    //     });
    //     view.ui.add(editor, "top-right");
    // });

});


function findObjectId(rcode){
    console.log(rcode);
    params.where = "RCODE="+rcode;

    qTask.execute(params).then(ShowFindResult);
}

function ShowFindResult(response){
    console.log(response);
    var results = response.features[0];
    view.graphics.removeAll();
    view.goTo(results.geometry.extent.expand(1)).then(function() {

        var selectionSymbol={
            type:"simple-fill",
            size:10,
            outline:{
                color:"red",
                width:2
            }
        };
        results.symbol=selectionSymbol;
        view.graphics.add(results);
        view.popup.open({
            title: results.attributes.NAME,
            location: results.geometry.centroid
        });
    });

}