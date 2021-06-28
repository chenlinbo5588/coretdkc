
var SCALE_MIN = 100;
var SCALE_MAX = 3000000;
var view;
var map;
var featureLayer;
var qTask ;
var params ;
require([
    "esri/Map",
    "esri/views/MapView",
    "esri/layers/FeatureLayer",
    "esri/geometry/support/webMercatorUtils",
    "esri/Graphic",
    "esri/widgets/Sketch",
    "esri/layers/GraphicsLayer",
    "esri/widgets/Search",
    "esri/widgets/Editor",
    "esri/layers/TileLayer",
    "esri/core/urlUtils",
    "esri/views/SceneView",
    "esri/widgets/BasemapToggle",
    "esri/config",
    "esri/layers/WebTileLayer",
    "esri/tasks/QueryTask",
    "esri/tasks/support/Query",
    "esri/layers/MapImageLayer",
], function (Map, MapView, FeatureLayer, webMercatorUtils, Graphic, Sketch, GraphicsLayer, Search, Editor, TileLayer, urlUtils,SceneView,BasemapToggle,esriConfig,WebTileLayer,QueryTask,Query,MapImageLayer) {

    urlUtils.addProxyRule({
        urlPrefix: "localhost/arcgis", // specify resource location
        proxyUrl: "http://localhost:8080/river/proxy" // specify location of proxy file
    });
    var tdt_token = "fac43bd612f98b93bacda49ccb3af69c";
    var tiledLayer = new WebTileLayer({
        urlTemplate:
            "http://{subDomain}.tianditu.gov.cn/DataServer?T=img_w&x={col}&y={row}&l={level}&tk="+tdt_token,
        subDomains: ["t0", "t1", "t2", "t3","t4", "t5", "t6", "t7"],
    });

    var tiledLayer_poi = new WebTileLayer({
        urlTemplate:
            "http://{subDomain}.tianditu.gov.cn/DataServer?T=img_w&x={col}&y={row}&l={level}&tk="+tdt_token,
        subDomains: ["t0", "t1", "t2", "t3","t4", "t5", "t6", "t7"],
    });
    var template = {
        title: "{NAME}",
        content: [
            {
                type: "fields",
                fieldInfos: [
                    {
                        fieldName: "NAME",
                        label: "河道",
                        format: {
                                digitSeparator: true,
                                places: 0
                        }
                    },
                    {
                        fieldName: "LENGTH",
                        label: "河道长度",
                        format: {
                            digitSeparator: true,
                            places: 0
                        }
                    }
                ]
            }
        ]
    };

    river = new MapImageLayer({
        url: "http://localhost/arcgis/rest/services/river/shuiyu/MapServer",
        sublayers: [{
                id: 11,
                visible: true,
            },{
                id: 10,
                visible: true,
                minScale:6000,

            },{
                id: 9,
                visible: true,
            },{
                id: 8,
                visible: true,
                minScale:6000,
            },{
                id: 7,
                visible: true,
            },{
                id: 6,
                visible: true,
                minScale:6000,
            },{
                id: 5,
                visible: true,
            },{
                id: 4,
                visible: true,
                minScale:6000,
            },{
                id: 3,
                visible: true,
            },{
                id: 2,
                visible: true,
                minScale:6000,
            },{
                id: 1,
                visible: true,
                popupTemplate: template,
            },{
                id: 0,
                visible: true,
                minScale:6000,
            }
        ]
    });

    // river_1 = new FeatureLayer({
    //     url: "http://localhost/arcgis/rest/services/river/shuiyu/FeatureServer/1",
    //     popupTemplate: template
    // });
    // var LineFeatureLayer = new FeatureLayer({
    //     url: "http://localhost/arcgis/rest/services/yaosu/rval/FeatureServer/0",
    // });
    // var graphicsLayer = new GraphicsLayer();
    //搜索
    qTask = new QueryTask({
        url: "http://localhost/arcgis/rest/services/river/shuiyu/FeatureServer/1"
    });
    params = new Query({
        returnGeometry: true,
        outFields: ["*"]
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

    view.on("click", function (ev) {
        view.graphics.removeAll();

        // var mp = webMercatorUtils.webMercatorToGeographic(ev.mapPoint);
        // view.hitTest(ev).then(function (response) {
        //     if (response.results[0].graphic) {
        //         var query = new Query({
        //             objectIds: [
        //                 response.results[0].graphic.attributes.OBJECTID
        //             ],
        //             outFields: ["*"]
        //         });
        //         featureLayer.queryFeatures(query).then(function (result) {
        //             showInfo(result.features[0].attributes);
        //         });
        //     }
        // });
    });
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

function findObjectId(id){
    console.log(id);
    params.where = "OBJECTID="+id;
    qTask.execute(params).then(ShowFindResult);
}

function ShowFindResult(response){
    var results = response.features[0];
    view.graphics.removeAll();
    console.log(results);
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