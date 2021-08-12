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
var layerItemList = [];
var slideName;
var rslideName;
var lslideName;
var selectionSymbolR;
var selectionSymbolY;
var projectFind;
var projectFindparams;
var hxaa;
var projectId;
var temppolygon;
var sj;
var daochuStatus = false;
var container = "";


function defineActions(event) {
    var item = event.item;
    if (item.title == "天地图" || item.title == "天地图标注" || item.title == "Other" || item.title == "Shuiyu" || item.title == "Bgtx" || item.title == "ditu") {
        layerItemList.push(item.layer);
        item.actionsSections = [
            [
                {
                    title: "透明度设置",
                    className: "esri-icon-settings2",
                    id: "opacity",
                }
            ]
        ];

        if (item.title == "Other") {
            item.title = "其他图层";
        } else if (item.title == "Shuiyu") {
            item.title = "水域图层";
        } else if (item.title == "Bgtx") {
            item.title = "变更图形";
        } else if (item.title == "ditu") {
            item.title = "其他影像图";
        }
    }
}

function initIndexMap() {
    require([
        "esri/Map",
        "esri/views/MapView",
        "esri/tasks/IdentifyTask",
        "esri/rest/support/IdentifyParameters",
        "esri/layers/FeatureLayer",
        "esri/layers/GraphicsLayer",
        "esri/Graphic",
        "esri/layers/TileLayer",
        "esri/core/urlUtils",
        "esri/config",
        "esri/layers/WebTileLayer",
        "esri/tasks/QueryTask",
        "esri/rest/support/Query",
        "esri/layers/MapImageLayer",
        "esri/tasks/FindTask",
        "esri/rest/support/FindParameters",
        "esri/widgets/LayerList",
        "esri/views/draw/Draw",
        "esri/geometry/SpatialReference",
        "esri/geometry/support/webMercatorUtils",
        'esri/layers/support/TileInfo',
        "esri/geometry/Point",
    ], (Map, MapView, IdentifyTask, IdentifyParameters, FeatureLayer, GraphicsLayer, Graphic, TileLayer, urlUtils, esriConfig, WebTileLayer, QueryTask, Query, MapImageLayer,
                 FindTask, FindParameters, LayerList, Draw, SpatialReference, webMercatorUtils, TileInfo, Point) => {
        // esriConfig.apiKey= gloablConfig.arcgisToken;
        urlUtils.addProxyRule({
            urlPrefix: gloablConfig.mapHost + "/arcgis", // specify resource location
            proxyUrl: "http://" + gloablConfig.xmHost + ":" + gloablConfig.mapServerPort + "/river/proxy" // specify location of proxy file
        });
        urlUtils.addProxyRule({
            urlPrefix: "www.myarcgis.com", // specify resource location
            proxyUrl: "http://" + gloablConfig.xmHost + ":" + gloablConfig.mapServerPort + "/river/proxy" // specify location of proxy file
        });
        var tdt_token = "fac43bd612f98b93bacda49ccb3af69c";

        var tileInfo = new TileInfo({
            dpi: 90.71428571427429,
            rows: 256,
            cols: 256,
            compressionQuality: 0,//压缩像素值
            origin: {
                x: -180,
                y: 90
            },
            spatialReference: {
                wkid: 4326
            },
            lods: [
                {level: 2, levelValue: 2, resolution: 0.3515625, scale: 147748796.52937502},
                {level: 3, levelValue: 3, resolution: 0.17578125, scale: 73874398.264687508},
                {level: 4, levelValue: 4, resolution: 0.087890625, scale: 36937199.132343754},
                {level: 5, levelValue: 5, resolution: 0.0439453125, scale: 18468599.566171877},
                {level: 6, levelValue: 6, resolution: 0.02197265625, scale: 9234299.7830859385},
                {level: 7, levelValue: 7, resolution: 0.010986328125, scale: 4617149.8915429693},
                {level: 8, levelValue: 8, resolution: 0.0054931640625, scale: 2308574.9457714846},
                {level: 9, levelValue: 9, resolution: 0.00274658203125, scale: 1154287.4728857423},
                {level: 10, levelValue: 10, resolution: 0.001373291015625, scale: 577143.73644287116},
                {level: 11, levelValue: 11, resolution: 0.0006866455078125, scale: 288571.86822143558},
                {level: 12, levelValue: 12, resolution: 0.00034332275390625, scale: 144285.93411071779},
                {level: 13, levelValue: 13, resolution: 0.000171661376953125, scale: 72142.967055358895},
                {level: 14, levelValue: 14, resolution: 8.58306884765625e-005, scale: 36071.483527679447},
                {level: 15, levelValue: 15, resolution: 4.291534423828125e-005, scale: 18035.741763839724},
                {level: 16, levelValue: 16, resolution: 2.1457672119140625e-005, scale: 9017.8708819198619},
                {level: 17, levelValue: 17, resolution: 1.0728836059570313e-005, scale: 4508.9354409599309},
                {level: 18, levelValue: 18, resolution: 5.3644180297851563e-006, scale: 2254.4677204799655},
                {level: 19, levelValue: 19, resolution: 2.68220901489257815e-006, scale: 1127.23386023998275},
                {level: 20, levelValue: 20, resolution: 1.341104507446289075e-006, scale: 563.616930119991375}
            ]
        });

        var tiledLayer = new WebTileLayer("http://{subDomain}.tianditu.com/DataServer?T=img_c&X={col}&Y={row}&L={level}&tk=" + tdt_token, {
            title: "天地图",
            subDomains: ["t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7"],
            tileInfo: tileInfo,
            spatialReference: {
                wkid: 4326
            },
        });

        var tiledLayer2 = new WebTileLayer("http://{subDomain}.tianditu.com/DataServer?T=img_c&X={col}&Y={row}&L={level}&tk=" + tdt_token, {
            title: "天地图",
            subDomains: ["t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7"],
            tileInfo: tileInfo,
            spatialReference: {
                wkid: 4326
            },
        });

        var ditu = new TileLayer({
            title: "ditu",
            url: "http://" + gloablConfig.mapHost + "/arcgis/rest/services/yx2021/yxgjc/MapServer",
            tileInfo: tileInfo,
            spatialReference: {
                wkid: 4326
            },
        });
        var tiledLayer_poi = new WebTileLayer("http://{subDomain}.tianditu.com/DataServer?T=cva_c&X={col}&Y={row}&L={level}&tk=" + tdt_token, {
            title: "天地图标注",
            subDomains: ["t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7"],
            tileInfo: tileInfo,
            spatialReference: {
                wkid: 4326
            },
            visible: false,
        });

        var tiledLayer_poi2 = new WebTileLayer("http://{subDomain}.tianditu.com/DataServer?T=cva_c&X={col}&Y={row}&L={level}&tk=" + tdt_token, {
            title: "天地图标注",
            subDomains: ["t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7"],
            tileInfo: tileInfo,
            visible: false,
            spatialReference: {
                wkid: 4326
            },
        });

        temppolygon = new FeatureLayer({
            url: gloablConfig.temppolygonUrl,
            title: "水准点",
        });

        river = new MapImageLayer({

            url: gloablConfig.riverUrl,
            sublayers: [{
                id: 13,
                title: "其他沟渠",
                visible: true,
                minScale: 6000,
            }, {
                id: 12,
                title: "暗河",
                visible: true,
                minScale: 6000,
            }, {
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

            url: gloablConfig.otherUrl,
            sublayers: [{
                id: 11,
                title: "乡镇界",
                visible: true,
            }, {
                title: "河区",
                id: 10,
                visible: false,
            }, {
                id: 9,
                title: "村界",
                visible: false,
            }, {
                title: "船闸",
                id: 8,
                visible: false,
            }, {
                id: 7,
                title: "泵站",
                visible: false,
            }, {
                title: "码头",
                id: 6,
                visible: false,
            }, {
                id: 5,
                title: "水闸",
                visible: false,
            }, {
                title: "拦水坝",
                id: 4,
                visible: false,
            }, {
                id: 3,
                title: "其他工程",
                visible: false,
            }, {
                title: "堤防",
                id: 2,
                visible: false,
            }, {
                title: "桥梁",
                id: 1,
                visible: false,
            }, {
                title: "水准点",
                id: 2,
                visible: false,
            }]
        });


        var bgtx = new MapImageLayer({
            url: gloablConfig.bgtxUrl,
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
            url: gloablConfig.riverUrl
        });
        shuiyuFindparams = new FindParameters({
            layerIds: [1, 3, 5, 7, 9, 11],
            searchFields: ["identification"],
            returnGeometry: true,
        });
        selectionSymbolR = {
            type: "simple-fill",
            size: 10,
            outline: {
                color: "red",
                width: 2
            }
        };
        selectionSymbolY = {
            type: "simple-fill",
            size: 10,
            outline: {
                color: "yellow",
                width: 2
            }
        };
        find = new FindTask({
            url: gloablConfig.bgtxUrl
        });
        findparams = new FindParameters({
            layerIds: [0, 1, 2],
            searchFields: ["identification"],
            returnGeometry: true,
        });


        map = new Map({
            layers: [tiledLayer, tiledLayer_poi, other, river, ditu],
            spatialReference: {
                wkid: 4326
            },
        });
        rightmap = new Map({
            layers: [tiledLayer2, tiledLayer_poi2, bgtx],
            spatialReference: {
                wkid: 4326
            },
        });
        view = new MapView({
            container: "viewDiv",
            map: map,
            scale: 120000,
            // zoom:13,
            center: [121.29207073988186, 30.245057849724848],
            spatialReference: {
                wkid: 4326
            },
        });


        view.ui.remove('attribution')
        view.ui.remove("zoom");


        view.ui.add(document.getElementById("topRightBox"), {
            position: "top-right",
            index: 1
        });
        $("#topRightBox").show();

        $("#bottomRightBox").show();
        //
        // var graphicsLayer = new GraphicsLayer({title:"编辑图层"});
        // map.add(graphicsLayer);
        /*画图工具声明*/
        drawTool = new Draw({
            view: view
        });

        // view.on("mouse-wheel",function(evt){
        //
        //    console.log(view.scale);
        //
        // });
        view.on("click", function (e) {
            // geom = webMercatorUtils.xyToLngLat(e.mapPoint.x, e.mapPoint.y);
            // console.log(geom[0], geom[1]);
            console.log(e.mapPoint.x + "," + e.mapPoint.y);
            // getOpenImg(e.mapPoint.x,+e.mapPoint.y);
        });

        view.when(function () {
            view.on("click", executeIdentifyTask);
            // Create identify task for the specified map service

            identifyTask = new IdentifyTask({url: gloablConfig.riverUrl});

            // Set the parameters for the Identify
            identifyparams = new IdentifyParameters();
            identifyparams.tolerance = 1;
            identifyparams.returnGeometry = true;
            identifyparams.layerIds = [1, 3, 5, 7, 9, 11];
            identifyparams.layerOption = "visible";
            identifyparams.width = view.width;
            identifyparams.height = view.height;
            //加载layerlist
            var layerList = new LayerList({
                view: view,
                listItemCreatedFunction: defineActions,
            }, "shuiyu");

            layerList.on("trigger-action", function (event) {
                var id = event.action.id;
                if (slideName == event.item.title) {
                    $("#slideBox").hide();
                    slideName = null;
                } else {
                    slideName = event.item.title;
                    $("#slideBox").show();
                    if (id === "opacity") {
                        if (event.item.layer.opacity == undefined) {
                            $("#slideIndex").html(event.item.layer.layer.opacity * 100 + "%");
                            $("#slide").slider({
                                value: event.item.layer.layer.opacity * 100,
                                step: 10,
                                slide: function (event2, ui) {
                                    $("#slideIndex").html(ui.value + "%");
                                    event.item.layer.layer.opacity = ui.value / 100;
                                }
                            });
                        } else {
                            $("#slideIndex").html(event.item.layer.opacity * 100 + "%");
                            $("#slide").slider({
                                value: event.item.layer.opacity * 100,
                                step: 10,
                                slide: function (event2, ui) {
                                    $("#slideIndex").html(ui.value + "%");
                                    event.item.layer.opacity = ui.value / 100;
                                }
                            });
                        }
                    }
                }

            });
            view.ui.add(document.getElementById("bottomRightBox"), {
                position: "bottom-right",
                index: 0,
            });
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

function loadshp(ysId, view, save) {

    require([
        'esri/Color',
        "esri/geometry/Point",
        "esri/geometry/Polyline",
        "esri/geometry/Polygon",
        "esri/layers/GraphicsLayer",
        'esri/Graphic',
        "esri/tasks/GeometryService",
        "esri/rest/support/ProjectParameters"
    ], ( Color,Point,Polyline,Polygon, GraphicsLayer,Graphic,GeometryService,ProjectParameters) =>{
        //加载方法
        console.log(view.extent);
        let input = document.getElementById(ysId).files[0]; //获取导入的文件
        let reader = new FileReader();
        reader.readAsArrayBuffer(input) //读取文件为 ArrayBuffer
        reader.onload = function (evt) {
            let fileData = evt.target.result //fileData就是读取到的文件ArrayBuffer

            shapefile.open(fileData).then(source => source.read().then(function log(result) { //将ArrayBuffer转换
                if (result.done) return
                let type = result.value.geometry.type
                //判断导入shp文件的类型 根据不同类型绘制点线面
                if (type == "Polygon" || type == "MultiPolygon") {
                    view.graphics.removeAll();
                    var polygonPath = result.value.geometry.coordinates[0];
                    var str = polygonPath[0][0];

                    if(str.toString().substring(0,2) =="40"){
                        var polygon = new Polygon({
                            rings: polygonPath,
                            spatialReference:{
                                wkid: 4528
                            }
                        });
                        //project
                        var geomSer = new GeometryService(gloablConfig.geometryServerUrl);
                        var params = new ProjectParameters({
                            geometries: [polygon],
                            outSpatialReference: view.spatialReference,
                        });
                        geomSer.project(params).then(function (request) {
                            var gr = new Graphic({
                                geometry: request[0],
                                symbol: selectionSymbolR
                            });

                            if (save) {
                                findHxaaAndDelete(id);
                                var addfeatrue = new Graphic({
                                    geometry: request[0],
                                    symbol: selectionSymbolR,
                                    attributes: {
                                        projectId: projectId
                                    }
                                });
                                addFeature(hxaa, addfeatrue);
                                openCkMap();
                                projectView.graphics.add(gr);
                                projectView.goTo(request[0].extent.expand(1));
                                fxByPolygon(request[0], projectView);
                            }else{
                                fxByPolygon(request[0], view);
                                view.graphics.add(gr);
                                view.goTo(request[0].extent.expand(1));
                            }
                            showMessage('加载成功', 3000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                        });

                    }else if(str.toString().substring(0,2) =="12"){
                        var polygon = new Polygon({
                            rings: polygonPath,
                            spatialReference:view.spatialReference,
                        });
                        var gr = new Graphic({
                            geometry: polygon,
                            symbol: selectionSymbolR,
                            spatialReference:view.spatialReference,
                        });
                        if (save) {
                            findHxaaAndDelete(id);
                            var addfeatrue = new Graphic({
                                geometry: polygon,
                                symbol: selectionSymbolR,
                                attributes: {
                                    projectId: projectId
                                }
                            });
                            addFeature(hxaa, addfeatrue);
                            openCkMap();
                            projectView.graphics.add(gr);
                            projectView.goTo(polygon.extent.expand(1));


                            fxByPolygon(polygon, projectView);
                        }else{
                            fxByPolygon(polygon, view);
                            view.graphics.add(gr);
                            view.goTo(gr.geometry.extent.expand(1));
                            showMessage('加载成功', 3000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                        }
                    }else{
                        showMessage('该shp坐标系暂不支持', 3000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                    }
                } else {
                    showMessage('加载失败,请检查文件正确性', 3000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                }
                // if (type == "MultiLineString" || type == "LineString") {
                //     let polylinePath = result.value.geometry.coordinates;
                //     let polyline = new Polyline({
                //         paths: polylinePath,
                //         spatialReference: view.spatialReference
                //     });
                //     let gr = new Graphic(polyline, lineSymbol);
                //     view.graphics.add(gr);
                // }
                // if (type == "Point") {
                //     let lon = result.value.geometry.coordinates[0];
                //     let lat = result.value.geometry.coordinates[1];
                //     let point = new Point({
                //         x:lon,
                //         y:lat,
                //         spatialReference: view.spatialReference
                //     });
                //     let gr = new Graphic(point, pointSymbol);
                //     view.graphics.add(gr);
                // }
                // return source.read().then(log)
                return true;
            }))
                .catch(error => console.error(error.stack))
        }
    });

}

function showPolygon(event) {
    require([
        "esri/geometry/Polygon",
        "esri/Graphic",
    ], (Polygon, Graphic) =>{

        var polygon = new Polygon({
            rings: event.vertices,
            spatialReference: view.spatialReference
        });
        var polygonGraphic = new Graphic({
            geometry: polygon,
            symbol: selectionSymbolR
        });
        view.graphics.removeAll();
        view.graphics.add(polygonGraphic);
    });

}

function fxByPolygon(polygon, fxview) {
    require([
        "esri/geometry/geometryEngine",
        "esri/Graphic",
        "esri/geometry/Extent",
        "esri/geometry/SpatialReference",
    ],(geometryEngine, Graphic,Extent,SpatialReference)=>{
        identifyparams.geometry = polygon;
        sj = Date.parse(new Date()) + "" + Math.round(Math.random() * 100);
        var tempfeature = new Graphic({
            geometry: polygon,
            attributes: {
                flag: sj
            }
        });
        addFeature(temppolygon, tempfeature);
        identifyparams.mapExtent =  new Extent(180, 90, -180, -90,
            new SpatialReference({ wkid:4326 }) );
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
                        if (intersect != null) {
                            var area = parseFloat(geometryEngine.geodesicArea(intersect, "square-meters"));
                            var item = {};
                            item.layerId = results[i].layerId;
                            item.identification = result.attributes.identification;
                            item.area = area;
                            data.push(item);
                            result.symbol = selectionSymbolY;
                            result.geometry = intersect;
                            fxdata.push(result);
                            fxview.graphics.add(result);
                        }
                    }
                    showfxPolygon(data, fxview);
                }
            })
    })
}

function fxPolygon(event) {

    require([
        "esri/geometry/Polygon",
        "esri/geometry/geometryEngine",
        "esri/Graphic",
    ],(Polygon, geometryEngine, Graphic)=>{
        var polygon = new Polygon({
            rings: event.vertices,
            spatialReference: view.spatialReference,
        });
        sj = Date.parse(new Date()) + "" + Math.round(Math.random() * 100);
        var tempfeature = new Graphic({
            geometry: polygon,
            attributes: {
                flag: sj
            }
        });
        addFeature(temppolygon, tempfeature);

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
                        var area = parseFloat(geometryEngine.geodesicArea(intersect, "square-meters"));
                        var item = {};
                        item.layerId = results[i].layerId;
                        item.identification = result.attributes.identification;
                        item.area = area;
                        data.push(item);
                        result.symbol = selectionSymbolY;
                        result.geometry = intersect;
                        fxdata.push(result);
                        view.graphics.add(result);
                    }
                    showfxPolygon(data, view);
                }
            })
    });

}

function showfxPolygon(data, view) {
    $("#fxTishi").hide();
    var param = {
        fxdata: JSON.stringify(data)
    };
    $.post(BASE_URL + "/river/fxSelect", param, function (result) {
        $(".infoList").hide();
        $("#fxList").html(result).promise().done(function () {
            $("[name=itemClick]").click(function () {
                $("[name=itemClick]").removeClass("itemClick");
                $(this).addClass("itemClick");
                var identification = $(this).data("identification");
                //view.graphics.removeAll();
                for (var i = 0; i < fxdata.length; i++) {
                    if (identification == fxdata[i].attributes.identification) {
                        if (fxSlectData != undefined) {
                            view.graphics.remove(fxSlectData);
                        }
                        console.log(fxdata[i]);
                        view.goTo(fxdata[i].geometry.extent.expand(1)).then(function () {
                            var selectionSymbol = {
                                type: "simple-fill",
                                size: 10,
                                outline: {
                                    color: "red",
                                    width: 2
                                }
                            };
                            fxdata[i].symbol = selectionSymbol;
                            fxSlectData = fxdata[i];
                            view.graphics.add(fxSlectData);
                        });
                        break;
                    }
                }
            })
        });
        ;
        $("#fxList").show();
    });

}


function initLeftView(view, contain) {
    require([
        "esri/views/MapView",
    ],(MapView)=> {
        view = new MapView({
            container: contain,
            map: map,
            scale: 120000,
            center: [121.29207073988186, 30.245057849724848],
            spatialReference: {
                wkid: 4326
            },
        });
        view.ui.remove('attribution')
        view.ui.remove("zoom");


    });

    return view;


}

function initRightView(view, contain) {

    require([
        "esri/views/MapView",
    ],(MapView)=> {
        view = new MapView({
            container: contain,
            map: rightmap,
            scale: 120000,
            center: [121.29207073988186, 30.245057849724848],
            spatialReference: {
                wkid: 4326
            },
        });
        view.ui.remove('attribution')
        view.ui.remove("zoom");
    });
    return view;
}
function changeProjectView(view,contain){
    require([
        "esri/Map",
        "esri/views/MapView",
    ], function (Map, MapView) {
        view = new MapView({
            container: contain,
            map: map,
            scale: 120000,
            center: [121.29207073988186, 30.245057849724848],
            spatialReference: {
                wkid: 4326
            },
        });
        view.ui.remove('attribution')
        view.ui.remove("zoom");
    });
    return view;
}

function initProjectView(contain,id) {

    require([
        "esri/Map",
        "esri/views/MapView",
        "esri/widgets/LayerList",
        "esri/layers/FeatureLayer",
        "esri/tasks/FindTask",
        "esri/rest/support/FindParameters",
        "esri/tasks/GeometryService",
        "esri/rest/support/ProjectParameters",
    ], function (Map, MapView,LayerList,FeatureLayer,FindTask,FindParameters,GeometryService,ProjectParameters) {
        projectId = id;
        hxaa = new FeatureLayer({
            url: gloablConfig.hxUrl,
            title: "红线图层",
        });

        projectFind = new FindTask({
            url: gloablConfig.hxSearchUrl
        });
        projectFindparams = new FindParameters({
            layerIds: [0],
            searchFields: ["projectId"],
            returnGeometry: true,
        });
        view = new MapView({
            container: contain,
            map: map,
            scale: 120000,
            center: [121.29207073988186, 30.245057849724848],
            spatialReference: {
                wkid: 4326
            },
        });

        view.ui.remove('attribution')
        view.ui.remove("zoom");
        view.when(function () {
            projectFindparams.searchText = id;
            projectFind.execute(projectFindparams)
                .then(function (response) {
                    var results = response.results;
                    if (results.length > 0) {
                        var result = results[0].feature;

                        var geomSer = new GeometryService(gloablConfig.geometryServerUrl);
                        var params = new ProjectParameters({
                            geometries: [result.geometry],
                            outSpatialReference: view.spatialReference,
                        });
                        geomSer.project(params).then(function (request) {
                            view.goTo(request[0].extent.expand(1)).then(function () {
                                result.symbol = selectionSymbolR;
                                result.geometry = request[0];
                                view.graphics.add(result);
                                container =contain;

                            });
                        });
                    }
                })
        })


    });

}

$(function () {
    initIndexMap();
})
//todo
function findHxaaAndDelete(id) {
    projectFindparams.searchText = id;
    projectFind.execute(projectFindparams)
        .then(function (response) {
            var results = response.results;
            for (var i = 0; i < results.length; i++) {
                var result = results[i].feature;
                deleteFeature(hxaa, result);
            }
        })
}
function findByIdentification(code) {
    require([
        "esri/tasks/GeometryService",
        "esri/rest/support/ProjectParameters"
    ],(GeometryService, ProjectParameters)=>{
        shuiyuFindparams.searchText = code;
        shuiyuFind.execute(shuiyuFindparams)
            .then(function (response) {
                var results = response.results;
                if (results.length > 0) {
                    var result = results[0].feature;
                    var geomSer = new GeometryService(gloablConfig.geometryServerUrl);
                    var params = new ProjectParameters({
                        geometries: [result.geometry],
                        outSpatialReference: view.spatialReference,
                    });
                    geomSer.project(params).then(function (request) {
                        view.goTo(request[0].extent.expand(1)).then(function () {
                            result.symbol = selectionSymbolR;
                            result.geometry = request[0];
                            view.graphics.add(result);
                        });
                    });
                }
                $("#viewDiv").css("cursor", "auto");
            })
    });

}

function changeFindByCode(code, view1, view2) {
    require([
        "esri/tasks/GeometryService",
        "esri/rest/support/ProjectParameters"
    ], (GeometryService, ProjectParameters)=> {
        view1.graphics.removeAll();
        view2.graphics.removeAll();
        findparams.searchText = code;
        $("#viewDiv").css("cursor", "wait");
        find.execute(findparams)
            .then(function (response) {
                var results = response.results;
                if (results.length > 0) {
                    var result = results[0].feature;
                    var geomSer = new GeometryService(gloablConfig.geometryServerUrl);
                    var params = new ProjectParameters({
                        geometries: [result.geometry],
                        outSpatialReference: view.spatialReference,
                    });
                    geomSer.project(params).then(function (request) {
                        result.symbol = selectionSymbolR;
                        result.geometry = request[0];
                        view1.goTo(request[0].extent.expand(1)).then(function () {
                        });
                        view2.goTo(request[0].extent.expand(1)).then(function () {
                            result.symbol = selectionSymbolR;
                            view2.graphics.add(result);
                        });

                    });
                }
                $("#viewDiv").css("cursor", "auto");
            })

    });
}

function ShowFindResult(response) {
    var results = response.features[0];
    view.graphics.removeAll();
    view.goTo(results.geometry.extent.expand(1)).then(function () {
        results.symbol = selectionSymbolR;
        view.graphics.add(results);
        view.popup.open({
            title: results.attributes.NAME,
            location: results.geometry.centroid
        });
    });

}