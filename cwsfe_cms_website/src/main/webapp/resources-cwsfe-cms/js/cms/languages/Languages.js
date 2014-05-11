jQuery(document).ready(function () {

    jQuery('#LanguagesList').dataTable({
        'iTabIndex': -1,
        'sPaginationType': 'full_numbers',
        'bProcessing': true,
        'bServerSide': true,
        'bFilter': false,
        'aLengthMenu': [10, 20, 30],
        'bPaginate': true,
        'bLengthChange': true,
        'sAjaxSource': 'languagesList',
        aoColumns: [
            { 'bSortable': false, mData: '#' },
            { 'bSortable': false, mData: 'code' },
            { 'bSortable': false, mData: 'name' },
            { 'bSortable': false, mData: 'id',
                "fnRender": function (o) {
                    return '<button class="button red tiny" onclick="removeLanguage(' + o.aData['id'] + ')" tabindex="-1">Delete</button>';
                }
            }
        ]
    });

});

function addLanguage() {
    var code = $('#code').val();
    var name = $('#name').val();
    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: 'addLanguage',
        data: "code=" + code + "&name=" + name,
        success: function (response) {
            if (response.status == 'SUCCESS') {
                $("#LanguagesList").dataTable().fnDraw();
                $('#code').val('');
                $('#name').val('');
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

function removeLanguage(idValue) {
    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: 'deleteLanguage',
        data: "id=" + idValue,
        success: function (response) {
            if (response.status == 'SUCCESS') {
                $("#LanguagesList").dataTable().fnDraw();
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