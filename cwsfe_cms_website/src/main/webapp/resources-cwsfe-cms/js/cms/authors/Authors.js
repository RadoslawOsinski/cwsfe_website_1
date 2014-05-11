jQuery(document).ready(function () {

    jQuery('#authorsList').dataTable({
        'iTabIndex': -1,
        'sPaginationType': 'full_numbers',
        'bProcessing': true,
        'bServerSide': true,
        'bFilter': false,
        'aLengthMenu': [10, 20, 30],
        'bPaginate': true,
        'bLengthChange': true,
        'sAjaxSource': 'authorsList',
        aoColumns: [
            { 'bSortable': false, mData: '#' },
            { 'bSortable': false, mData: 'lastName' },
            { 'bSortable': false, mData: 'firstName' },
            { 'bSortable': false, mData: 'googlePlusAuthorLink' },
            { 'bSortable': false, mData: 'id',
                "fnRender": function (o) {
                    return '<button class="button red tiny" onclick="removeAuthor(' + o.aData['id'] + ')" tabindex="-1">Delete</button>';
                }
            }
        ]
    });

});

function addAuthor() {
    var firstName = $('#firstName').val();
    var lastName = $('#lastName').val();
    var googlePlusAuthorLink = $('#googlePlusAuthorLink').val();
    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: 'addAuthor',
        data: "firstName=" + firstName + "&lastName=" + lastName + "&googlePlusAuthorLink=" + googlePlusAuthorLink,
        success: function (response) {
            if (response.status == 'SUCCESS') {
                $("#authorsList").dataTable().fnDraw();
                $('#firstName').val('');
                $('#googlePlusAuthorLink').val('');
                $('#lastName').val('');
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

function removeAuthor(idValue) {
    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: 'deleteAuthor',
        data: "id=" + idValue,
        success: function (response) {
            if (response.status == 'SUCCESS') {
                $("#authorsList").dataTable().fnDraw();
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