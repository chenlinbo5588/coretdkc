var view;
var map;
var shuiyuFind;
var shuiyuFindparams;
var find;
var findparams;
var drawTool;
var identifyparams;
var identifyTask;
var rightmap;
var fxdata;
var fxSlectData;

function initIndexMap() {
    require([
        "esri/Map",
        "esri/views/MapView",
        "esri/tasks/IdentifyTask",
        "esri/tasks/support/IdentifyParameters",
        "esri/layers/FeatureLayer",
        "esri/layers/GraphicsLayer",
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
        "esri/widgets/LayerList",
        "esri/views/draw/Draw",
    ], function (Map, MapView, IdentifyTask, IdentifyParameters, FeatureLayer, GraphicsLayer, Graphic, TileLayer, urlUtils, esriConfig, WebTileLayer, QueryTask, Query, MapImageLayer,
                 FindTask, FindParameters, LayerList, Draw) {


        // esriConfig.apiKey= gloablConfig.arcgisToken;
        urlUtils.addProxyRule({
            urlPrefix: gloablConfig.mapHost + "/arcgis", // specify resource location
            proxyUrl: "http://" + gloablConfig.xmHost + ":" + gloablConfig.mapServerPort + "/river/proxy" // specify location of proxy file
        });


        var tdt_token = "fac43bd612f98b93bacda49ccb3af69c";
        var tiledLayer = new WebTileLayer({
            title: "天地图",
            urlTemplate:
                "http://{subDomain}.tianditu.gov.cn/DataServer?T=img_w&x={col}&y={row}&l={level}&tk=" + tdt_token,
            subDomains: ["t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7"],
        });
        var tiledLayer2 = new WebTileLayer({
            title: "天地图",
            urlTemplate:
                "http://{subDomain}.tianditu.gov.cn/DataServer?T=img_w&x={col}&y={row}&l={level}&tk=" + tdt_token,
            subDomains: ["t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7"],
        });


        var ditu = new TileLayer({
            title: "ditu",
            url: "http://" + gloablConfig.mapHost + "/arcgis/rest/services/yx2021/yxgjc/MapServer"
        });
        var tiledLayer_poi = new WebTileLayer({
            title: "天地图标注",
            visible: false,
            urlTemplate:
                "http://{subDomain}.tianditu.gov.cn/DataServer?T=cva_w&x={col}&y={row}&l={level}&tk=" + tdt_token,
            subDomains: ["t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7"],
        });
        var tiledLayer_poi2 = new WebTileLayer({
            title: "天地图标注",
            visible: false,
            urlTemplate:
                "http://{subDomain}.tianditu.gov.cn/DataServer?T=cva_w&x={col}&y={row}&l={level}&tk=" + tdt_token,
            subDomains: ["t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7"],
        });
        river = new MapImageLayer({
            url: "http://" + gloablConfig.mapHost + "/arcgis/rest/services/" + gloablConfig.mapServerName + "/shuiyu/MapServer",
            sublayers: [{
                id: 11,
                title: "其他水域",
                visible: true,
            }, {
                title: "其他水域管理范围线",
                id: 10,
                visible: true,
                minScale: 6000,

            }, {
                title: "人工水道",
                id: 9,
                visible: true,
            }, {
                title: "人工水道管理范围线",
                id: 8,
                visible: true,
                minScale: 6000,
            }, {
                title: "山塘",
                id: 7,
                visible: true,
            }, {
                title: "山塘范围线",
                id: 6,
                visible: true,
                minScale: 6000,
            }, {
                title: "湖泊",
                id: 5,
                visible: true,
            }, {
                title: "湖泊范围管理线",
                id: 4,
                visible: true,
                minScale: 6000,
            }, {
                title: "水库",
                id: 3,
                visible: true,
            }, {
                title: "水库范围管理线",
                id: 2,
                visible: true,
                minScale: 6000,
            }, {
                title: "河道水域",
                id: 1,
                visible: true,
            }, {
                title: "河道水域范围管理线",
                id: 0,
                visible: true,
                minScale: 6000,
            }
            ]
        });

        var other = new MapImageLayer({
            url: "http://" + gloablConfig.mapHost + "/arcgis/rest/services/" + gloablConfig.mapServerName + "/xzj/MapServer",
            sublayers: [{
                id: 1,
                title: "乡镇界",
                visible: true,
            }, {
                title: "水准点",
                id: 0,
                visible: false,
            }]
        });
        var bgtx = new MapImageLayer({
            url: "http://" + gloablConfig.mapHost + "/arcgis/rest/services/" + gloablConfig.mapServerName + "/bgtx/MapServer",
            sublayers: [{
                id: 2,
                title: "其他水域变更",
                visible: true,
            }, {
                title: "水库变更",
                id: 1,
                visible: true,
            }, {
                title: "河道变更",
                id: 0,
                visible: true,
            }]
        });


        shuiyuFind = new FindTask({
            url: "http://" + gloablConfig.mapHost + "/arcgis/rest/services/" + gloablConfig.mapServerName + "/shuiyu/MapServer"
        });
        shuiyuFindparams = new FindParameters({
            layerIds: [1, 3, 5, 7, 9, 11],
            searchFields: ["identification"],
            returnGeometry: true,
        });

        find = new FindTask({
            url: "http://" + gloablConfig.mapHost + "/arcgis/rest/services/" + gloablConfig.mapServerName + "/bgtx/MapServer"
        });
        findparams = new FindParameters({
            layerIds: [0, 1, 2],
            searchFields: ["identification"],
            returnGeometry: true,
        });

        map = new Map({
            layers: [tiledLayer, tiledLayer_poi, ditu, other, river,]
        });
        rightmap = new Map({
            layers: [tiledLayer2, tiledLayer_poi2, bgtx]
        });
        view = new MapView({
            container: "viewDiv",
            map: map,
            center: [121.25962011651083, 30.17229501748913],
            zoom: 11
        });
        view.ui.remove('attribution')
        view.ui.remove("zoom");

        var layerList = new LayerList({
            view: view,

            listItemCreatedFunction: function (event) {
                const item = event.item;
                if (item.title == "Shuiyu") {
                    item.title = "水域图层"
                } else if (item.title == "Xzj") {
                    item.title = "其他图层"
                } else if (item.title == "Bgtx") {
                    item.title = "变更图形图层"
                } else if (item.title == "ditu") {
                    item.title = "自定义影像"
                }
            }
        }, "shuiyu");

        view.ui.add(document.getElementById("bottomRightBox"), {
            position: "bottom-right",
            index: 0,
        });
        view.ui.add(document.getElementById("slide"), {
            position: "bottom-right",
            index: 1,
        });
        view.ui.add(document.getElementById("topRightBox"), {
            position: "top-right",
            index: 1
        });
        $("#topRightBox").show();

        $("#bottomRightBox").show();
        //
        // var graphicsLayer = new GraphicsLayer({title:"编辑图层"});
        // map.add(graphicsLayer);
        /*叠加分析*/
        drawTool = new Draw({
            view: view
        });

        // view.on("mouse-wheel",function(evt){
        //
        //    console.log(view.scale);
        //
        // });


        view.when(function () {
            view.on("click", executeIdentifyTask);
            // Create identify task for the specified map service
            identifyTask = new IdentifyTask({url: "http://" + gloablConfig.mapHost + "/arcgis/rest/services/" + gloablConfig.mapServerName + "/shuiyu/MapServer"});
            // Set the parameters for the Identify
            identifyparams = new IdentifyParameters();
            identifyparams.tolerance = 1;
            identifyparams.returnGeometry = true;
            identifyparams.layerIds = [1, 3, 5, 7, 9, 11];
            identifyparams.layerOption = "visible";
            identifyparams.width = view.width;
            identifyparams.height = view.height;

        });

        function executeIdentifyTask(event) {
            view.graphics.removeAll();

            identifyparams.geometry = event.mapPoint;
            identifyparams.mapExtent = view.extent;
            $("#viewDiv").css("cursor", "wait");
            identifyTask
                .execute(identifyparams)
                .then(function (response) {
                    var results = response.results;
                    if (results.length > 0) {
                        var result = results[0].feature;
                        var layerId = results[0].layerId;
                        var identification = result.attributes.identification;

                        view.goTo(result.geometry.extent.expand(1)).then(function () {
                            $.get(BASE_URL + "river/water/info/ic?identification=" + identification + "&layerId=" + layerId, function (resp) {
                                $(".infoList").hide();
                                $("#close").show();
                                $("#infoDetail").show();
                                $("#infoDetail").html(resp);
                            })
                        });
                    }
                    $("#viewDiv").css("cursor", "auto");
                })
        }
    });
}

function loadshp(ysId) {
    require([
        'esri/Color',
        "esri/geometry/Point",
        "esri/geometry/Polyline",
        "esri/geometry/Polygon",
        "esri/layers/GraphicsLayer",
        'esri/Graphic',
        "esri/symbols/SimpleMarkerSymbol",
        "esri/symbols/SimpleFillSymbol",
        'esri/symbols/SimpleLineSymbol',
    ], function (
        Color,
        Point,
        Polyline,
        Polygon,
        GraphicsLayer,
        Graphic,
        SimpleMarkerSymbol,
        SimpleFillSymbol,
        SimpleLineSymbol,
    ) {
        var lineSymbol = new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([255, 99, 71]), 3); //线样式
        var pointSymbol = new SimpleMarkerSymbol() //点样式

        //加载方法

        let input = document.getElementById(ysId).files[0]; //获取导入的文件
        let reader = new FileReader();
        reader.readAsArrayBuffer(input) //读取文件为 ArrayBuffer
        reader.onload = function (evt) {
            let fileData = evt.target.result //fileData就是读取到的文件ArrayBuffer
            shapefile.open(fileData).then(source => source.read().then(function log(result) { //将ArrayBuffer转换
                if (result.done) return
                let type = result.value.geometry.type;
                //判断导入shp文件的类型 根据不同类型绘制点线面
                if (type == "Polygon") {
                    let polygonPath = result.value.geometry.coordinates[0];
                    var fillSymbol = {
                        type: "simple-fill",
                        outline: {
                            color: "red",
                            width: 2
                        }
                    };
                    var polygon = new Polygon({
                        rings: polygonPath,
                        spatialReference: view.spatialReference
                    });
                    let gr = new Graphic({
                        geometry: polygon,
                        symbol: fillSymbol
                    });
                    view.graphics.add(gr);
                }
                if (type == "MultiLineString" || type == "LineString") {
                    let polylinePath = result.value.geometry.coordinates;
                    let polyline = new Polyline({
                        paths: polylinePath,
                        spatialReference: view.spatialReference
                    });
                    let gr = new Graphic(polyline, lineSymbol);
                    view.graphics.add(gr);
                }
                if (type == "Point") {
                    let lon = result.value.geometry.coordinates[0];
                    let lat = result.value.geometry.coordinates[1];
                    let point = new Point({
                        x:lon,
                        y:lat,
                        spatialReference: view.spatialReference
                    });
                    let gr = new Graphic(point, pointSymbol);
                    view.graphics.add(gr);
                }
            }))
                .catch(error => console.error(error.stack))
        }
    });
}

function showPolygon(event) {

    require([
        "esri/geometry/Polygon",
        "esri/Graphic",
    ], function (Polygon, Graphic) {
        var fillSymbol = {
            type: "simple-fill",
            outline: {
                color: "red",
                width: 2
            }
        };
        var polygon = new Polygon({
            rings: event.vertices,
            spatialReference: view.spatialReference
        });
        var polygonGraphic = new Graphic({
            geometry: polygon,
            symbol: fillSymbol
        });
        view.graphics.removeAll();
        view.graphics.add(polygonGraphic);
    });

}

function fxPolygon(event) {

    require([
        "esri/geometry/Polygon",
        "esri/geometry/geometryEngine",
    ], function (Polygon, geometryEngine) {
        var fillSymbol = {
            type: "simple-fill",
            outline: {
                color: "red",
                width: 2
            }
        };
        var polygon = new Polygon({
            rings: event.vertices,
            spatialReference: view.spatialReference
        });
        setidentifyparamsLayerIds();
        identifyparams.geometry = polygon;
        identifyparams.mapExtent = view.extent;
        var data = [];
        fxdata = [];
        identifyTask
            .execute(identifyparams)
            .then(function (response) {
                var results = response.results;
                if (results.length > 0) {
                    for (var i = 0; i < results.length; i++) {

                        var result = results[i].feature;
                        var intersect = geometryEngine.intersect(result.geometry, polygon);
                        var area = parseFloat(geometryEngine.planarArea(intersect, "square-meters"));

                        var item = {};
                        item.layerId = results[i].layerId;
                        item.identification = result.attributes.identification;
                        item.area = area;
                        data.push(item);
                        var selectionSymbol = {
                            type: "simple-fill",
                            size: 10,
                            outline: {
                                color: "yellow",
                                width: 2
                            }
                        };
                        result.symbol = selectionSymbol;
                        result.geometry = intersect;
                        fxdata.push(result);
                        view.graphics.add(result);
                    }
                    showfxPolygon(data);
                }
            })
    });

}

function showfxPolygon(data) {
    $("#fxTishi").hide();
    var param = {
        fxdata: JSON.stringify(data)
    };
    $.post(BASE_URL + "/river/fxSelect", param, function (result) {
        $(".infoList").hide();

        $("#fxList").html(result);
        $("#fxList").show();
    });

}

function setidentifyparamsLayerIds() {

    view.map.layers.forEach(function (layer) {
        if (layer.title == "Shuiyu") {
            identifyparams.layerIds = [];
            for (i = 0; i < layer.allSublayers.items.length; i = i + 2) {
                if (layer.allSublayers.items[i].visible) {
                    identifyparams.layerIds.push(layer.allSublayers.items[i].id);
                }
            }
        }
    })

}

function initLeftView(view, contain) {
    var identifyTask;
    var identifyparams;
    require([
        "esri/Map",
        "esri/views/MapView",
        "esri/tasks/IdentifyTask",
        "esri/tasks/support/IdentifyParameters",
    ], function (Map, MapView) {

        view = new MapView({
            container: contain,
            map: map,
            center: [121.25962011651083, 30.17229501748913],
            zoom: 11
        });
        view.ui.remove('attribution')
        view.ui.remove("zoom");

        view.when(function () {
            view.on("click", executeIdentifyTask);

            // Create identify task for the specified map service
            // identifyTask = new IdentifyTask({ url: "http://"+gloablConfig.mapHost+"/arcgis/rest/services/"+gloablConfig.mapServerName+"/shuiyu/MapServer"});
            // // Set the parameters for the Identify
            // identifyparams = new IdentifyParameters();
            // identifyparams.tolerance = 1;
            // identifyparams.returnGeometry =true;
            // identifyparams.layerOption = "visible";
            // identifyparams.width = view.width;
            // identifyparams.height = view.height;

        });

        // function executeIdentifyTask(event) {
        //     view.graphics.removeAll();
        //
        //     identifyparams.geometry = event.mapPoint;
        //     identifyparams.mapExtent = view.extent;
        //     $("#viewDiv").css("cursor","wait");
        //     identifyTask
        //         .execute(identifyparams)
        //         .then(function(response) {
        //             var results = response.results;
        //             if (results.length>0){
        //                 var result = results[0].feature;
        //                 var layerId = results[0].layerId;
        //
        //                 view.goTo(result.geometry.extent.expand(1)).then(function() {
        //                     var selectionSymbol={
        //                         type:"simple-fill",
        //                         size:10,
        //                         outline:{
        //                             color:"red",
        //                             width:2
        //                         }
        //                     };
        //                     result.symbol= selectionSymbol;
        //                     view.graphics.add(result);
        //                     view.popup.open({
        //                         title:result.attributes.selectName,
        //                         location: event.mapPoint
        //                     });
        //                 });
        //             }
        //             $("#viewDiv").css("cursor","auto");
        //         })
        // }
    });
    return view;
}

function initRightView(view, contain) {

    require([
        "esri/Map",
        "esri/views/MapView",
        "esri/tasks/IdentifyTask",
        "esri/tasks/support/IdentifyParameters",
    ], function (Map, MapView) {

        view = new MapView({
            container: contain,
            map: rightmap,
            center: [121.25962011651083, 30.17229501748913],
            zoom: 11
        });
        view.ui.remove('attribution')
        view.ui.remove("zoom");
    });
    return view;
}

$(function () {
    initIndexMap();
})

function findByIdentification(code) {
    shuiyuFindparams.searchText = code;
    shuiyuFind.execute(shuiyuFindparams)
        .then(function (response) {
            var results = response.results;
            if (results.length > 0) {
                var result = results[0].feature;
                view.goTo(result.geometry.extent.expand(1)).then(function () {
                    var selectionSymbol = {
                        type: "simple-fill",
                        size: 10,
                        outline: {
                            color: "red",
                            width: 2
                        }
                    };
                    result.symbol = selectionSymbol;
                    view.graphics.add(result);
                });

            }
            $("#viewDiv").css("cursor", "auto");
        })
}

function changeFindByCode(code, view1, view2) {

    view1.graphics.removeAll();
    view2.graphics.removeAll();
    findparams.searchText = code;

    $("#viewDiv").css("cursor", "wait");
    find.execute(findparams)
        .then(function (response) {
            var results = response.results;
            if (results.length > 0) {
                var result = results[0].feature;

                view1.goTo(result.geometry.extent.expand(1)).then(function () {

                });
                view2.goTo(result.geometry.extent.expand(1)).then(function () {
                    var selectionSymbol = {
                        type: "simple-fill",
                        size: 10,
                        outline: {
                            color: "red",
                            width: 2
                        }
                    };
                    result.symbol = selectionSymbol;
                    view2.graphics.add(result);
                });

            }
            $("#viewDiv").css("cursor", "auto");
        })
}

function ShowFindResult(response) {
    var results = response.features[0];
    view.graphics.removeAll();
    view.goTo(results.geometry.extent.expand(1)).then(function () {
        var selectionSymbol = {
            type: "simple-fill",
            size: 10,
            outline: {
                color: "red",
                width: 2
            }
        };
        results.symbol = selectionSymbol;
        view.graphics.add(results);
        view.popup.open({
            title: results.attributes.NAME,
            location: results.geometry.centroid
        });
    });

}