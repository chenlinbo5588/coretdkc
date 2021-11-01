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
                            rings.push([xyTemp[1], xyTemp[0]]);
                            // rings.push(latLng2WebMercator2(parseFloat(xyTemp[1]), parseFloat(xyTemp[0])));
                        } else {
                            rings.push([xyTemp[0], xyTemp[1]]);
                            // rings.push(latLng2WebMercator2(parseFloat(xyTemp[0]), parseFloat(xyTemp[1])));
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


function timestampToTime(timestamp) {

    if (timestamp == 0) {
        return "无数据";
    } else {
        var date = new Date(timestamp * 1000);
        Y = date.getFullYear() + '-';
        M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
        D = date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate();
        h = date.getHours() + ':';
        m = date.getMinutes() + ':';
        s = date.getSeconds();
        return Y + M + D;
    }


}

function creatrPolgnByRing(ring, view) {
    var gr;
    var polygon
    require([
        "esri/geometry/Polygon",
        'esri/Graphic',
    ], (
        Polygon,
        Graphic
    ) => {
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
    ], (Polyline, Point, Graphic, geometryEngine, Polygon) => {
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
                    text: Math.abs((geometryEngine.geodesicArea(graphic.geometry)).toFixed(2)) + "平方米",
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
    ], (Polyline, Point, Graphic, geometryEngine) => {
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
                    text: (geometryEngine.geodesicLength(graphic.geometry)).toFixed(2) + "米",
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
    ], (Polygon, Graphic, geometryEngine) => {
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
    ], (LayerList) => {
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


    $("#fxList").hide();

    if (container == "viewDivMap") {

    } else {
        $(".viewDivMapBox").show();
        $(".toolsmask").show();
        $("#searchBox").css("position", "static");
        $("#searchBox").show();
        projectView = changeProjectView(projectView, "viewDivMap");
        initTools(projectView, 'searchBox', 'top-left');
        initTools(projectView, 'toolsBox', 'top-right');
        initLayerList(projectView, 'shuiyu', 'bottomRightBox', 'bottom-right');
    }

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
        "esri/shpwrite",
        "esri/arcgis-to-geojson",
        "esri/rest/support/Query",
    ], (shpwrite, arcgis2geojson, Query) => {
        var options = {
            folder: 'output',//文件夹名字
            types: {
                point: 'hxpoint', //点图层的文件名，下同
                line: "hxline",
                polygon: 'hxpolygon'
            }
        };
        var query = new Query();
        query.where = "projectId = '" + id + "'";
        query.returnGeometry = true;
        hxaa.queryFeatures(query).then(function (results) {
            console.log(results);
            if (results.features.length > 0) {
                var geojsons = arcgis2geojson.arcgisToGeoJSON(results);//转化数据为GeoJSON
                shpwrite.download(geojsons, options) //下载数据
            } else {
                showMessage('未导入红线,请先导入红线', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
            daochuStatus = false;

        });
    });
}

function outputMap(id) {
    require([
        "esri/rest/support/Query",
        'esri/Graphic',
    ], (Query, Graphic) => {
        if (daochuStatus == false) {
            daochuStatus = true;
            const query = new Query();
            query.where = "projectId ='" + id +"'";
            query.returnGeometry = true;
            var polygon;
            hxaa.queryFeatures(query).then(function (results) {
                var polygons = results.features;
                if (polygons.length > 0) {
                    polygon = polygons[0].geometry;
                    sj = Date.parse(new Date()) + "" + Math.round(Math.random() * 100);
                    var tempfeature = new Graphic({
                        geometry: polygon,
                        attributes: {
                            flag: sj
                        }
                    });
                    console.log(tempfeature);
                    addFeature(temppolygon, tempfeature);
                    dowloadOutputfxDwg(sj);
                    showMessage('导出中,导出后会自动下载', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                } else {
                    daochuStatus = false;
                    showMessage('未导入红线,无法导出图形', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                }
            });
        } else {
            showMessage('正在导出文件中,请耐心等待', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
        }
    })
}

function dowloadOutputfxDwg(sj) {
    require([
        "esri/tasks/Geoprocessor",
        "esri/rest/support/FeatureSet",
    ], (Geoprocessor, FeatureSet) => {


        var outputGpurl = gloablConfig.outputallDwgUrl;
        var downloadurl = gloablConfig.featureToCadDownloadUrl;
        var featureToCadurl = gloablConfig.featureToCadurl;

        var flag = 0;
        var featureSet = new FeatureSet();
        featureSets = [new FeatureSet(), new FeatureSet(), new FeatureSet(), new FeatureSet(), new FeatureSet(), new FeatureSet()];

        var gp = new Geoprocessor(outputGpurl);
        var featuregp = new Geoprocessor(featureToCadurl);
        var params = {
            bm: sj,
            // outputFile: "分析图形",
            f: "JSON",
        };
        console.log(params);

        var outputString = ["outputrv", "outputac", "outputhp", "outputlk", "outputow", "outputrs"];
        showMessage('导出中,需要大概几分钟时间,请耐心等待', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
        gp.submitJob(params).then(function (gpjobInfo) {
            const jobid = gpjobInfo.jobId;
            const gpoptions = {
                interval: 1500,
                statusCallback: (j) => {
                    console.log("Job Status: ", j.jobStatus);
                }
            };
            gpjobInfo.waitForJobCompletion(gpoptions).then((request) => {
                daochuStatus = false;
                if (request.jobStatus == "job-succeeded") {
                    for (var n = 0; n < outputString.length; n++) {
                        var p = n;
                        gpjobInfo.fetchResultData(outputString[n]).then(function (results) {
                            flag++;
                            for (var i = 0; i < results.value.features.length; i++) {
                                featureSet.features.push(results.value.features[i]);
                            }
                            if (flag == 6) {
                                var params2 = {
                                    inputFeature: JSON.stringify(featureSet),
                                    daochuUrl: "D:/project/sdk/4.20/4.20/download/" + gpjobInfo.jobId,
                                    f: "JSON",
                                };

                                featuregp.submitJob(params2).then(function (featuregpjobInfo) {
                                    const options2 = {
                                        interval: 1500,
                                        statusCallback: (j) => {
                                            console.log("featuregp  Status: ", j.jobStatus);
                                        }
                                    };
                                    featuregpjobInfo.waitForJobCompletion(options2).then((repo) => {
                                        if (repo.jobStatus == "job-succeeded") {
                                            var url = downloadurl + gpjobInfo.jobId + ".DWG";
                                            console.log(url);
                                            var a = document.createElement("a");
                                            a.setAttribute("id", "download");
                                            document.body.appendChild(a);
                                            var triggerDownload = $("#download").attr("href", url).attr("download", "分析图形.dwg");
                                            triggerDownload[0].click();
                                            document.body.removeChild(a);

                                        } else if (repo.jobStatus == "job-failed") {
                                            showMessage('导出失败,请联系管理员', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                                        }
                                    });
                                })
                            }
                        });
                    }
                } else if (request.jobStatus == "job-failed") {
                    showMessage('导出失败,请联系管理员', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                }

            });


        });

    });


}


function dowloadOutputDwg(id) {
    require([
        "esri/tasks/Geoprocessor",
        "esri/rest/support/Query",
    ], (Geoprocessor, Query) => {
        var outputGpurl = gloablConfig.outputDwgUrl;
        var downloadurl = gloablConfig.outputDwgDownloadUrl;

        //http://192.168.5.120/arcgis/rest/directories/arcgisjobs/outputCAD_gpserver/j48042049f1dd4cc8a5aa3cf5d1038a3e/scratch/ExportCAD.DWG

        var gp = new Geoprocessor(outputGpurl);
        var params = {
            projectId: id,
            daochuUrl: "D:/project/sdk/4.20/4.20/download/红线"+id,
        };
        var query = new Query();
        query.where = "projectId = '" + id + "'";
        query.returnGeometry = true;
        hxaa.queryFeatures(query).then(function (results) {
            if (results.features.length > 0) {
                showMessage('导出中,请耐心等待', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                gp.submitJob(params).then(function (gpjobInfo) {
                    const gpoptions = {
                        interval: 1500,
                        statusCallback: (j) => {
                            console.log("Job Status: ", j.jobStatus);
                        }
                    };
                    gpjobInfo.waitForJobCompletion(gpoptions).then((request) => {
                        daochuStatus = false;
                        if (request.jobStatus == "job-succeeded") {
                            downloadurl = downloadurl + "红线"+id+".dwg";
                            var a = document.createElement("a");
                            a.setAttribute("id", "download");
                            document.body.appendChild(a);
                            var triggerDownload = $("#download").attr("href", downloadurl).attr("download", "红线cad.dwg");
                            triggerDownload[0].click();
                            document.body.removeChild(a);
                        }
                    });
                });
            } else {
                daochuStatus = false;
                showMessage('未导入红线,请先导入红线', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
        });
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
    ], (PrintTask, PrintTemplate, PrintParameters, MapImageLayer, Task, request, geometryEngine, Point) => {

        var river = new MapImageLayer({
            url: gloablConfig.riverQzjUrl,
        });
        var point = new Point({
            x: x,
            y: y,
            spatialReference: {
                wkid: 4326,
            }
        });
        var result = geometryEngine.geodesicBuffer(point, 2000, "meters");
        var param = river.createExportImageParameters(result.extent, 1700, 1000);

        //url需修改
        var url = "http://gis2.sy.gov:8080/river/proxy?http://gis2.sy.gov/arcgis/rest/services/SY/shuiyu2/MapServer/export?bbox=" + param.bbox + "&bboxSR=" + param.bboxSR + "&dpi=96&f=image&gdbVersion=" + param.gdbVersion + "&imageSR=" + param.imageSR + "&size=" + param.size + "&transparent=" + param.transparent;
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

