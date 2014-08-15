//require(['jquery', 'shared_scripts', 'tipsy', 'cycle_all',
//    'async!https://maps.googleapis.com/maps/api/js?sensor=false'
//], function ($) {
//
//    function startMap() {
//        var coordinates = new google.maps.LatLng(52.774509, 20.698843);	//home
//        //    var coordinates = new google.maps.LatLng(52.874509,20.608843);	//cieachanow
//        var mapOptions = {
//            zoom: 8,
//            center: coordinates,
//            mapTypeId: google.maps.MapTypeId.ROADMAP
//        };
//        var officeMap = new google.maps.Map($('#contactMap'), mapOptions);
//        var markerOptions = {
//            position: coordinates,
//            map: officeMap,
//            title: 'CWSFE'
//        };
//        var marker = new google.maps.Marker(markerOptions);
//    }
//
//    $(document).ready(function () {
//        google.maps.event.addDomListener(window, 'load', startMap);
//    });
//
//});