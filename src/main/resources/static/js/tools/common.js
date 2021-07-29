var lastLineLength;


function getPoints(txt, format) {
    ///console.log(txt);
    var mt, i = 0, rings = [], targetRing = [], regAr, regNum = /(\d+(\.\d+)?)/ig;

    if ('CAD List' == format) {
        regAr = new RegExp('(X=.*Z=)', 'img');
    } else if ('X,Y' == format || 'Y,X' == format) {
        regAr = /^(.*)$/igm;
    }
    mt = txt.match(regAr);
    //console.log(mt);
    if (mt && mt.length != 0) {
        if (format == 'CAD List') {
            for (i = 0; i < mt.length; i++) {
                var temp = mt[i].replace(/X=/g, '').replace(/Y=/g, ',').replace(/Z=/g, '').replace(/ /g, '').split(',');
                rings.push(temp);
            }
        } else if ('X,Y' == format || 'Y,X' == format) {
            for (i = 0; i < mt.length; i++) {
                var xyTemp = mt[i].match(regNum);
                // console.log(mt[i]);
                if (mt[i].indexOf("°") > 0) {
                    if (xyTemp && xyTemp.length >= 6) {
                        var x = parseFloat(xyTemp[0]) + parseFloat(xyTemp[1] / 60) + parseFloat(xyTemp[2] / 3600);
                        var y = parseFloat(xyTemp[3]) + parseFloat(xyTemp[4] / 60) + parseFloat(xyTemp[5] / 3600);
                        if ('Y,X' == format) {
                            rings.push(latLng2WebMercator2(y, x));
                        } else {
                            rings.push(latLng2WebMercator2(x, y));
                        }
                    }
                } else {
                    if (xyTemp && xyTemp.length >= 2) {
                        if ('Y,X' == format) {
                            // rings.push([xyTemp[1],xyTemp[0]]);
                            rings.push(latLng2WebMercator2(parseFloat(xyTemp[1]), parseFloat(xyTemp[0])));
                        } else {
                            // rings.push([xyTemp[0],xyTemp[1]]);
                            rings.push(latLng2WebMercator2(parseFloat(xyTemp[0]), parseFloat(xyTemp[1])));
                        }
                    }
                }
            }
        }

        for (i = 0; i < rings.length; i++) {
            targetRing.push([parseFloat(rings[i][0]), parseFloat(rings[i][1])]);
        }
    }
    return targetRing;
}

function latLng2WebMercator2(lng, lat) {
    var x = lng * 20037508.34 / 180;
    var y = Math.log(Math.tan((90 + lat) * Math.PI / 360)) / (Math.PI / 180);
    y = y * 20037508.34 / 180;
    return [x, y];
}

function creatrPolgnByRing(ring, view) {
    var gr;
    var polygon
    require([
        "esri/geometry/Polygon",
        'esri/Graphic',
    ], function (
        Polygon,
        Graphic
    ) {
        // console.log(view.spatialReference);
        polygon = new Polygon({
            rings: ring,
            spatialReference: view.spatialReference
        });
        gr = new Graphic({
            geometry: polygon,
            symbol: selectionSymbolR
        });

        fxByPolygon(polygon, view);
    });

    return gr;
}


function lateralArea(view, evt) {
    require([
        "esri/geometry/Polyline",
        "esri/geometry/Point",
        "esri/Graphic",
        "esri/geometry/geometryEngine",
        "esri/geometry/Polygon",
    ], function (Polyline, Point, Graphic, geometryEngine, Polygon) {
        view.graphics.removeAll();
        var polygon = new Polygon({
            rings: evt.vertices,
            spatialReference: view.spatialReference
        });
        var graphic = new Graphic({
            geometry: polygon,
            symbol: {
                type: "simple-line", // autocasts as SimpleLineSymbol
                color: [4, 90, 141],
                width: 3,
                cap: "round",
                join: "round"
            }
        });
        if (evt.type == "draw-complete") {
            if (evt.vertices.length >= 2) {
                var point = new Point({
                    x: graphic.geometry.centroid.x,
                    y: graphic.geometry.centroid.y,
                    spatialReference: view.spatialReference
                });
                var textSymbol = {
                    type: "text",  // autocasts as new TextSymbol()
                    color: "white",
                    haloColor: "black",
                    haloSize: "1px",
                    text: (geometryEngine.geodesicArea(graphic.geometry) / 1000000).toFixed(2) + "平方千米",
                    xoffset: 3,
                    yoffset: 3,
                    font: {  // autocast as new Font()
                        size: 12,
                        family: "sans-serif",
                        weight: "bold"
                    }
                };
                view.graphics.add(graphic);
                view.graphics.add(new Graphic(point, textSymbol));
            }
        } else {
            view.graphics.add(graphic);
        }
    });
}

function dist(view, evt) {
    require([
        "esri/geometry/Polyline",
        "esri/geometry/Point",
        "esri/Graphic",
        "esri/geometry/geometryEngine",

    ], function (Polyline, Point, Graphic, geometryEngine) {
        view.graphics.removeAll();
        var polyline = {
            type: "polyline", // autocasts as Polyline
            paths: evt.vertices,
            spatialReference: view.spatialReference
        };
        var graphic = new Graphic({
            geometry: polyline,
            symbol: {
                type: "simple-line", // autocasts as SimpleLineSymbol
                color: [4, 90, 141],
                width: 3,
                cap: "round",
                join: "round"
            }
        });
        if (evt.type == "draw-complete") {
            if (evt.vertices.length >= 2) {
                var point = new Point({
                    x: evt.vertices[evt.vertices.length - 1][0],
                    y: evt.vertices[evt.vertices.length - 1][1],
                    spatialReference: view.spatialReference
                });
                var textSymbol = {
                    type: "text",  // autocasts as new TextSymbol()
                    color: "white",
                    haloColor: "black",
                    haloSize: "1px",
                    text: (geometryEngine.geodesicLength(graphic.geometry) / 1000).toFixed(2) + "千米",
                    xoffset: 3,
                    yoffset: 3,
                    font: {  // autocast as new Font()
                        size: 12,
                        family: "sans-serif",
                        weight: "bold"
                    }
                };
                view.graphics.add(graphic);
                view.graphics.add(new Graphic(point, textSymbol));
            }
        } else {
            view.graphics.add(graphic);
        }
    });

}

function analyseM(view, evt) {
    require([
        "esri/geometry/Polygon",
        "esri/Graphic",
        "esri/geometry/geometryEngine",
    ], function (Polygon, Graphic, geometryEngine) {
        var polygon = new Polygon({
            rings: evt.vertices,
            spatialReference: view.spatialReference
        });
        var polygonGraphic = new Graphic({
            geometry: polygon,
            symbol: selectionSymbolR
        });
        if (evt.type == "draw-complete") {

            fxByPolygon(polygon, view);


            // identifyparams.geometry = polygon;
            // identifyparams.mapExtent = view.extent;
            // var data = [];
            // fxdata = [];
            // identifyTask
            //     .execute(identifyparams)
            //     .then(function (response) {
            //         var results = response.results;
            //         if (results.length > 0) {
            //             for (var i = 0; i < results.length; i++) {
            //                 var result = results[i].feature;
            //                 var intersect = geometryEngine.intersect(result.geometry, polygon);
            //                 if (intersect != null) {
            //                     var area = parseFloat(geometryEngine.geo(intersect, "square-meters"));
            //                     var item = {};
            //                     item.layerId = results[i].layerId;
            //                     item.identification = result.attributes.identification;
            //                     item.area = area;
            //                     data.push(item);
            //                     result.symbol = selectionSymbolY;
            //                     result.geometry = intersect;
            //                     fxdata.push(result);
            //                     view.graphics.add(result);
            //                 }
            //             }
            //             selectfxResult(view, data);
            //         }
            //     })
        } else {
            view.graphics.removeAll();
            view.graphics.add(polygonGraphic);
        }
    });
}

function selectfxResult(view, data) {
    $("#fxTishi").hide();
    var param = {
        fxdata: JSON.stringify(data)
    };
    $.post(BASE_URL + "/river/fxSelect", param, function (result) {
        $(".infoList").hide();
        $("#fxList").html(result).promise().done(function () {
            $("[name=itemClick]").unbind();
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
        $("#fxList").show();
    });
}

function initTools(view, contain, position) {
    view.ui.add(contain, position);
}

function initLayerList(view, contain, boxcontain, position) {
    require([
        "esri/widgets/LayerList",
    ], function (LayerList) {
        $("#" + contain).empty();
        var layerList = new LayerList({
            view: view,
            listItemCreatedFunction: defineActions,
        }, contain);
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
        initTools(view, boxcontain, position);
    })
}

function openCkMap() {
    $(".infoList").empty();
    $("#fxList").hide();
    if (view.container == "projectMapBox") {

    } else {
        $(".viewDivMapBox").show();
        $(".toolsmask").show();
        $("#searchBox").css("position", "static");
        $("#searchBox").show();
        initTools(view, 'searchBox', 'top-left');
        view.container = "viewDivMap";
    }

}

function printingMap(view) {

    require([
        "esri/widgets/LayerList",
        "esri/tasks/PrintTask",
        "esri/tasks/support/PrintTemplate",
        "esri/tasks/support/PrintParameters",
    ], function (LayerList, PrintTask, PrintTemplate, PrintParameters) {
        console.log("1");

        var printTask = new PrintTask({
            url: printToolsUrl
        });


        var template = new PrintTemplate();

        template.exportOptions = {
            width: 1200,
            height: 1200,
            dpi: 96
        };

        var params = new PrintParameters({
            view: view,
            template: template
        });
        template.format = "PDF";
        template.layout = "MAP_ONLY";
        printTask.execute(params).then(function (result) {
            console.log("2121");
            if (result != null) {
                window.open(result.url);
            }
        })

    });
}

function addFeature(feature, edits) {
    feature
        .applyEdits({
            addFeatures: [edits]
        })
        .then(function (editsResult) {
            console.log(editsResult);
        })
}

function deleteFeature(feature, edits) {
    feature
        .applyEdits({
            deleteFeatures: [edits]
        })
        .then(function (editsResult) {
            console.log(editsResult);
        })
}

function dowloadOutputShp(id) {
    require([
        "shpwrite",
        "arcgis-to-geojson",
        "esri/tasks/support/Query",
    ], function (shpwrite, arcgis2geojson, Query) {
        var options = {
            folder: 'output',//文件夹名字
            types: {
                point: 'hxpoint', //点图层的文件名，下同
                line: "hxline",
                polygon: 'hxpolygon'
            }
        };
        const query = new Query();
        query.where = "projectId =" + id;
        query.returnGeometry = true;
        hxaa.queryFeatures(query).then(function (results) {
            var geojsons = arcgis2geojson.arcgisToGeoJSON(results);//转化数据为GeoJSON
            shpwrite.download(geojsons, options) //下载数据
        });


    });
}

function outputMap(id) {
    require([
        "esri/tasks/support/Query",
        "esri/tasks/support/FeatureSet",
        "esri/geometry/geometryEngine",
    ], function ( Query,FeatureSet,geometryEngine) {
        const query = new Query();
        query.where = "projectId =" + id;
        query.returnGeometry = true;
        var polygon;
        hxaa.queryFeatures(query).then(function (results) {

            var polygons = results.features;
            if(polygons.length>0){
                var in_features = new FeatureSet();
                polygon =  polygons[0].geometry;
                identifyparams.geometry = polygon;
                identifyparams.mapExtent = view.extent;
                var data = [];
                fxdata = [];
                identifyTask
                    .execute(identifyparams)
                    .then(function (response) {
                        var items = response.results;
                        if (items.length > 0) {
                            for (var i = 0; i < items.length; i++) {
                                var item = items[i].feature;
                                var intersect = geometryEngine.intersect(item.geometry, polygon);
                                if(intersect !=null){
                                    item.symbol = selectionSymbolY;
                                    item.geometry = intersect;
                                    in_features.features.push(item);
                                }
                            }
                            geodata = JSON.stringify(in_features);
                            dowloadOutputfxDwg(geodata);
                        }else{
                            showMessage('红线中无图层,导出失败',2000,true,'bounceInUp-hastrans','bounceOutDown-hastrans');
                        }
                    })
            }else{
                showMessage('未导入红线,无法导出图形',2000,true,'bounceInUp-hastrans','bounceOutDown-hastrans');
            }





        });
    })
}

function dowloadOutputfxDwg(geodata) {
    require([
        "esri/tasks/Geoprocessor",
        //"esri/tasks/workflow/JobTask"
    ], function (Geoprocessor) {
        var outputGpurl = gloablConfig.outputallDwgUrl;
        var downloadurl = gloablConfig.outputallDwgDownloadUrl;
        //http://192.168.5.120/arcgis/rest/directories/arcgisjobs/outputCAD_gpserver/j48042049f1dd4cc8a5aa3cf5d1038a3e/scratch/ExportCAD.DWG
        var gp = new Geoprocessor(outputGpurl);
        // console.log(geodata);
        var params = {
            feature: geodata,
            outputFile: "分析图形",
            format: "JSON",
        };

        gp.submitJob(params).then(function (request) {
            // console.log(request);
            if (request.jobStatus == "job-succeeded") {
                downloadurl = downloadurl + request.jobId + "/scratch/分析图形.DWG";
                var a = document.createElement("a");
                a.setAttribute("id", "download");
                document.body.appendChild(a);
                var triggerDownload = $("#download").attr("href", downloadurl).attr("download", "分析图形.dwg");
                triggerDownload[0].click();
                document.body.removeChild(a);
            }
        });

    });


}


function dowloadOutputDwg(id) {
    require([
        "esri/tasks/Geoprocessor",
        //"esri/tasks/workflow/JobTask"
    ], function (Geoprocessor) {
        var outputGpurl = gloablConfig.outputDwgUrl;
        var downloadurl = gloablConfig.outputDwgDownloadUrl;

        //http://192.168.5.120/arcgis/rest/directories/arcgisjobs/outputCAD_gpserver/j48042049f1dd4cc8a5aa3cf5d1038a3e/scratch/ExportCAD.DWG

        var gp = new Geoprocessor(outputGpurl);
        var params = {
            projectId: id,
        };

        // gp.getResultData("j48042049f1dd4cc8a5aa3cf5d1038a3e","ExportCAD.DWG").then(function(resp){
        //     console.log(resp);
        // });

        gp.submitJob(params).then(function (request) {
            console.log(request);
            if (request.jobStatus == "job-succeeded") {
                downloadurl = downloadurl + request.jobId + "/scratch/ExportCAD.DWG";
                var a = document.createElement("a");
                a.setAttribute("id", "download");
                document.body.appendChild(a);
                var triggerDownload = $("#download").attr("href", downloadurl).attr("download", "红线cad.dwg");
                triggerDownload[0].click();
                document.body.removeChild(a);
            }
        });

        /*
        var jobTask = new JobTask("http://localhost/arcgis/rest/services/river/outputShp/GPServer/outputShp/submitJob");
        var params = {
            projectId: 48,
        };
        jobTask.getAttachments("jb1d487859f4f4627985f4027ad30eb23").then(function(resp){
            console.log(resp);
        });

        jobTask.createJobs(params).then(function(data){
            console.log(data);

            jobTask.getJob("j20fc2243dc034d8390f58440a5a07399").then(function(resp){
                console.log(resp)
            }, function(error){
                console.log(error);
            });
        });
         */

    });


}

function getOpenImg(x, y) {
    require([
        "esri/tasks/PrintTask",
        "esri/tasks/support/PrintTemplate",
        "esri/tasks/support/PrintParameters",
        "esri/layers/MapImageLayer",
        "esri/tasks/Task",
        "dojo/request",
        "esri/geometry/geometryEngine",
        "esri/geometry/Point",
    ], function (PrintTask, PrintTemplate, PrintParameters, MapImageLayer, Task, request, geometryEngine, Point) {

        var river = new MapImageLayer({
            url: "http://" + gloablConfig.mapHost + "/arcgis/rest/services/" + gloablConfig.mapServerName + "/shuiyu2/MapServer",
        });
        var point = new Point({
            x: x,
            y: y,
            spatialReference: {
                wkid: 4326,
            }
        });
        var result = geometryEngine.geodesicBuffer(point, 1500, "meters");
        var param = river.createExportImageParameters(result.extent, 1700, 1000);
        var url = "http://192.168.5.120:8080/river/proxy?http://192.168.5.120/arcgis/rest/services/river/shuiyu/MapServer/export?bbox=" + param.bbox + "&bboxSR=" + param.bboxSR + "&dpi=96&f=image&gdbVersion=" + param.gdbVersion + "&imageSR=" + param.imageSR + "&size=" + param.size + "&transparent=" + param.transparent + "&layers=show:1,3,5,7,9";
        window.location = url;

    });
}
function isNumber(value) {
    var patrn = /^(-)?\d+(\.\d+)?$/;
    if (patrn.exec(value) == null || value == "") {
        return false
    } else {
        return true
    }
}

