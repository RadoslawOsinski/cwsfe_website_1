jQuery(window).load(function() {

    jQuery('.tipsy-trigger').tipsy({gravity: 's', fade: true});

//    var postsRenderer = function() {
//        var ret = null;
//        $.ajax({
//            async: false,
//            url: '/CWSFE_CMS/blogPostsListForChart',
//            dataType:"json",
//            success: function(data) {
//                ret = data.statistics;
//            }
//        });
//        return ret;
//    };
//
//    if (jQuery('#posts_chart_dashboard').length) {
//        var chart = jQuery('#chart_dashboard');
//        chart.width(chart.parent().width());
//        chart.height(chart.parent().height());
//        plot1 = jQuery.jqplot("posts_chart_dashboard", [], {
//            dataRenderer: postsRenderer,
//            series:[{lineWidth:4, markerOptions:{style:'square'}}],
//            seriesColors:['#bee058', '#659ebe', '#d3a1ce', '#9bd49c', '#ba7979'],
//            animate: true,
//            animateReplot: true,
//            cursor: { showTooltip: true },
//            grid: {
//                backgroundColor: 'transparent',
//                gridLineColor: '#121212',
//                borderColor: '#121212',
//                borderWidth: 0,
//                shadowAlpha: 0.03
//            },
//            seriesDefaults: {
//                lineWidth: 3.5,
//                markerOptions: {
//                    show: true,             // wether to show data point markers.
//                    style: 'filledCircle',  // circle, diamond, square, filledCircle, filledDiamond or filledSquare.
//                    lineWidth: 2,       // width of the stroke drawing the marker.
//                    size: 10,            // size (diameter, edge length, etc.) of the marker.
//                    shadow: true,       // wether to draw shadow on marker or not.
//                    shadowAngle: 45,    // angle of the shadow.  Clockwise from x axis.
//                    shadowOffset: 1,    // offset from the line of the shadow,
//                    shadowDepth: 3,     // Number of strokes to make when drawing shadow.  Each stroke offset by shadowOffset from the last.
//                    shadowAlpha: 0.1   // Opacity of the shadow
//                }
//            },
//            axesDefaults: {
//                pad: 0,
//                tickOptions: {
//                    angle: -30
//                }
//            },
//            axes: {
//                xaxis:{
////                    label:'Date',
//                    renderer:$.jqplot.DateAxisRenderer,
//                    drawMajorGridlines: false,
//                    drawMinorGridlines: true,
//                    drawMajorTickMarks: false,
//                    rendererOptions: {
//                        tickInset: 1,
//                        minorTicks: 1
//                    },
//                    tickOptions:{
//                        formatString:'%m.%Y'
//                    },
//                    tickInterval:'1 month'
//                },
//                yaxis: {
////                    label:'Number of posts',
//                    tickOptions: {
//                        formatString: "%'d"
//                    },
//                    rendererOptions: {
//                        forceTickAt0: true
//                    }
//                }
//            }
//        });
//    }

    jQuery('#blogPostCommentsList').dataTable({
        'iTabIndex': -1,
        'bAutoWidth': true,
        'sPaginationType': 'full_numbers',
        'bProcessing': true,
        'bServerSide': true,
        'bFilter': false,
        'aLengthMenu': [10, 20, 30],
        'bPaginate': true,
        'bLengthChange': true,
        'sAjaxSource': '/CWSFE_CMS/blogPostCommentsList',
        aoColumns: [
            { 'bSortable': false, mData: '#' },
            { 'bSortable': false, mData: 'username' },
            { 'bSortable': false, mData: 'comment' },
            { 'bSortable': false, mData: 'created' },
            { 'bSortable': false, mData: 'status' ,
                'fnRender': function (o) {
                    if (o.aData['status'] === 'N') {
                        return '<span class="highlight yellow">New</span>';
                    } else if (o.aData['status'] === 'P') {
                        return '<span class="highlight green">Published</span>';
                    } else if (o.aData['status'] === 'B') {
                        return '<span class="highlight red">Blocked</span>';
                    } else if (o.aData['status'] === 'D') {
                        return '<span class="highlight red">Deleted</span>';
                    }
                    return '<span class="highlight red">Deleted</span>';
                }
            },
            { 'bSortable': false, mData: 'id',
                'fnRender': function (o) {
                    return '' +
                        '<form method="GET">' +
                            '<a class="icon awesome thumbs-up tipsy-trigger" title="Publish" tabindex="-1" onclick="publishBlogComment(' + o.aData['id'] + ');"></a>' +
                            '<a class="icon awesome thumbs-down tipsy-trigger" title="Reject" tabindex="-1" onclick="blockBlogComment(' + o.aData['id'] + ');"></a>' +
                            '<a class="icon awesome icon-remove-sign tipsy-trigger" title="Delete" tabindex="-1" onclick="deleteBlogComment(' + o.aData['id'] + ');"></a>' +
                        '</form>'
                        ;
                }
            }
        ]
    });

});

function publishBlogComment(idValue) {
    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: 'publishBlogPostComment',
        data: "id=" + idValue,
        success: function (response) {
            if (response.status == 'SUCCESS') {
                $("#blogPostCommentsList").dataTable().fnDraw();
            } else {
                errorInfo = "";
                for (i = 0; i < response.result.length; i++) {
                    errorInfo += "<br>" + (i + 1) + ". " + response.result[i].error;
                }
                $('#tableValidation').html("<p>Please correct following errors: " + errorInfo + "</p>").show('slow');
            }
        },
        error: function (response) {
            console.log('BUG: ' + response);
        }
    });
}

function blockBlogComment(idValue) {
    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: 'blockBlogPostComment',
        data: "id=" + idValue,
        success: function (response) {
            if (response.status == 'SUCCESS') {
                $("#blogPostCommentsList").dataTable().fnDraw();
            } else {
                errorInfo = "";
                for (i = 0; i < response.result.length; i++) {
                    errorInfo += "<br>" + (i + 1) + ". " + response.result[i].error;
                }
                $('#tableValidation').html("<p>Please correct following errors: " + errorInfo + "</p>").show('slow');
            }
        },
        error: function (response) {
            console.log('BUG: ' + response);
        }
    });
}

function deleteBlogComment(idValue) {
    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: 'deleteBlogPostComment',
        data: "id=" + idValue,
        success: function (response) {
            if (response.status == 'SUCCESS') {
                $("#blogPostCommentsList").dataTable().fnDraw();
            } else {
                errorInfo = "";
                for (i = 0; i < response.result.length; i++) {
                    errorInfo += "<br>" + (i + 1) + ". " + response.result[i].error;
                }
                $('#tableValidation').html("<p>Please correct following errors: " + errorInfo + "</p>").show('slow');
            }
        },
        error: function (response) {
            console.log('BUG: ' + response);
        }
    });
}
