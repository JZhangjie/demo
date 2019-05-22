require([
	"esri/widgets/Sketch",
	"esri/Map",
	"esri/layers/GraphicsLayer",
	"esri/views/MapView",
	"components/Demo/Widget"
], function(Sketch, Map,Demo) {
	var d = new Demo();
	d.placeAt("viewDiv","first");
});