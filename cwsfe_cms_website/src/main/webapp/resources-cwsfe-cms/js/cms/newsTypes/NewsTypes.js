jQuery(document).ready(function () {

    jQuery('#newsTypesList').dataTable({
        'iTabIndex': -1,
        'sPaginationType': 'full_numbers',
        'bProcessing': true,
        'bServerSide': true,
        'bFilter': false,
        'aLengthMenu': [10, 20, 30],
        'bPaginate': true,
        'bLengthChange': true,
        'sAjaxSource': 'newsTypesList',
        aoColumns: [
            { 'bSortable': false, mData: '#' },
            { 'bSortable': false, mData: 'type' },
            { 'bSortable': false, mData: 'id',
                "fnRender": function (o) {
                    return '<button class="button red tiny" onclick="removeNewsType(' + o.aData['id'] + ')" tabindex="-1">Delete</button>';
                }
            }
        ]
    });

});

function addNewsType() {
    var type = $('#type').val();
    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: 'addNewsType',
        data: "type=" + type,
        success: function (response) {
            if (response.status == 'SUCCESS') {
                $("#newsTypesList").dataTable().fnDraw();
                $('#type').val('');
            } else {
                errorInfo = "";
                for (i = 0; i < response.result.length; i++) {
                    errorInfo += "<br>" + (i + 1) + ". " + response.result[i].error;
                }
                $('#formValidation').html("<p>Please correct following errors: " + errorInfo + "</p>").show('slow');
            }
        },
        error: function (response) {
            console.log('BUG: ' + response);
        }
    });
}

function removeNewsType(idValue) {
    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: 'deleteNewsType',
        data: "id=" + idValue,
        success: function (response) {
            if (response.status == 'SUCCESS') {
                $("#newsTypesList").dataTable().fnDraw();
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