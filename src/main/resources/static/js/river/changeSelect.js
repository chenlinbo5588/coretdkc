
init("xinView");
init("jiuView");

function init(contain){
    var identifyTask;
    var identifyparams;
    require([
        "esri/Map",
        "esri/views/MapView",
        "esri/tasks/IdentifyTask",
        "esri/tasks/support/IdentifyParameters",
    ], function (Map,MapView, IdentifyTask, IdentifyParameters,) {

        var view = new MapView({
            container: contain,
            map: map,
            center: [121.25962011651083, 30.17229501748913],
            zoom:13
        });
        view.ui.remove('attribution')
        view.ui.remove("zoom");

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
        }

    });
}


