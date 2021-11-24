function initEditing (event) {
    var featureLayerInfos = arrayUtils.map(event.layers, function (layer) {
      return {
        "featureLayer": layer.layer
      };
    });
    
    var settings = {
      map: map,
      layerInfos: featureLayerInfos,
      toolbarVisible: true,
      showAttachments: true,
      //enableUndoRedo:true,
      //undoManager:undoManager,
      createOptions: {
          polygonDrawTools: [ 
            esri.dijit.editing.Editor.CREATE_TOOL_FREEHAND_POLYGON,
            esri.dijit.editing.Editor.CREATE_TOOL_AUTOCOMPLETE
          ]
        },
      toolbarOptions: {
       reshapeVisible: true,
       cutVisible: true,
       mergeVisible: true
      }
    };
    var params = {
      settings: settings
    };
    var editorWidget = new esri.toolbars.Editor(params, 'editorDiv');
    editorWidget.startup();

    //snapping defaults to Cmd key in Mac & Ctrl in PC.
    //specify "snapKey" option only if you want a different key combination for snapping
    map.enableSnapping();
}


function activateTool(event) {
  if($(this).html() == '清空'){
    
	map.getLayer("panWork").clear();
	map.graphics.clear();
	
    return ;
  } 
  var tool = $(this).attr("data-sharp").toUpperCase().replace(/ /g, "_");
  toolbar.activate(esri.toolbars.Draw[tool]);
  map.hideZoomSlider();
}

function createToolbar(themap) {
  // 工具
  toolbar = new esri.toolbars.Draw(map, { showTooltips : true } );
  toolbar.on("draw-end", addToMap);
  
  //编辑
  editToolbar = new esri.toolbars.Edit(map);
  

  //map.graphics.on("click", function(evt) {
  map.getLayer("panWork").on("click", function(evt) {
	myevent.stop(evt);
    //console.log(evt.graphic);
    activateToolbar(evt.graphic);
  });
  
  //deactivate the toolbar when you click outside a graphic
  map.on("click", function(evt){
    //console.log(evt.mapPoint);
    editToolbar.deactivate();
  });
  
}

function addToMap(evt) {
  console.log(evt);
  var symbol;
  toolbar.deactivate();
  map.showZoomSlider();
  
  switch (evt.geometry.type) {
    case "point":
    case "multipoint":
    	symbol = new esri.symbol.SimpleMarkerSymbol();
      	break;
    case "polyline":
    	symbol = new esri.symbol.SimpleLineSymbol();
    	break;
    case 'polygon':
    	symbol = new esri.symbol.SimpleFillSymbol();
    	break;
    default:
    	symbol = new esri.symbol.SimpleFillSymbol();
      	break;
  }
  var graphic = new esri.Graphic(evt.geometry, symbol);

  //map.graphics.add(graphic);
  map.getLayer("panWork").add(graphic);
  console.log(graphic);
  //map.graphics.add(graphic);
}

function activateToolbar(graphic) {
  var tool = 0;
  //tool = Edit.MOVE | Edit.EDIT_VERTICES | Edit.SCALE | Edit.ROTATE; 
  tool = esri.toolbars.Edit.MOVE | esri.toolbars.Edit.EDIT_VERTICES | esri.toolbars.Edit.SCALE | esri.toolbars.Edit.ROTATE; 
  
  // enable text editing if a graphic uses a text symbol
  if ( graphic.symbol.declaredClass === "esri.symbol.TextSymbol" ) {
    tool = tool | esri.toolbars.Edit.EDIT_TEXT;
  }

  var options = {
    allowAddVertices : true,
    allowDeleteVertices : true,
    uniformScaling : true
  }
  
  editToolbar.activate(tool, graphic, options);
}