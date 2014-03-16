jQuery(document).ready(function () {

    jQuery('#newsletterMailsList').dataTable({
        'iTabIndex': -1,
        'bAutoWidth': true,
        'sPaginationType': 'full_numbers',
        'bProcessing': true,
        'bServerSide': true,
        'bFilter': false,
        'aLengthMenu': [10, 20, 30],
        'bPaginate': true,
        'bLengthChange': true,
        'sAjaxSource': 'newsletterMailsList',
        'fnServerParams': function (aoData) {
            aoData.push(
                { 'name': "searchName", 'value': $('#searchName').val() }
            );
            aoData.push(
                { 'name': "searchRecipientGroupId", 'value': $('#searchRecipientGroupId').val() }
            );
        },
        aoColumns: [
            { 'bSortable': false, mData: '#' },
            { 'bSortable': false, mData: 'recipientGroupName' },
            { 'bSortable': false, mData: 'newsletterMailName' },
            { 'bSortable': false, mData: 'newsletterMailSubject' },
            { 'bSortable': false, mData: 'newsletterMailStatus' ,
                'fnRender': function (o) {
                    if (o.aData['newsletterMailStatus'] === 'N') {
                        return '<span class="highlight yellow">New</span>';
                    } else if (o.aData['newsletterMailStatus'] === 'P') {
                        return '<span class="highlight green">Preparing</span>';
                    } else if (o.aData['newsletterMailStatus'] === 'S') {
                        return '<span class="highlight blue">Sended</span>';
                    } else if (o.aData['newsletterMailStatus'] === 'D') {
                        return '<span class="highlight red">Deleted</span>';
                    }
                    return '<span class="highlight red">Deleted</span>';
                }
            },
            { 'bSortable': false, mData: 'id',
                "fnRender": function (o) {
                    return '' +
                        '<form method="GET" action="newsletterMails/' + o.aData['id'] + '">' +
                        '<button class="button green tiny" type="submit" tabindex="-1">Select</button>' +
                        '<button class="button red tiny" onclick="removeNewsletterMail(' + o.aData['id'] + ');return false;" tabindex="-1">Delete</button>' +
                        '</form>'
                        ;
                }
            }
        ]
    });

    $('#searchRecipientGroup').autocomplete({
        source: function (request, response) {
            $.ajax({
                url: 'newsletterMailGroupsDropList',
                data: {
                    limit: 5,
                    term: request.term
                },
                success: function (data) {
                    response($.map(data.data, function (item) {
                        return {
                            value: item.newsletterMailGroupName,
                            id: item.id
                        }
                    }));
                }
            });
        },
        minLength: 0,
        select: function (event, ui) {
            $('#searchRecipientGroupId').val(ui.item.id);
        }
    }).focus(function () {
        $(this).autocomplete("search", "");
    });

    $('#recipientGroup').autocomplete({
        source: function (request, response) {
            $.ajax({
                url: 'newsletterMailGroupsDropList',
                data: {
                    limit: 5,
                    term: request.term
                },
                success: function (data) {
                    response($.map(data.data, function (item) {
                        return {
                            value: item.newsletterMailGroupName,
                            id: item.id
                        }
                    }));
                }
            });
        },
        minLength: 0,
        select: function (event, ui) {
            $('#recipientGroupId').val(ui.item.id);
        }
    }).focus(function () {
        $(this).autocomplete("search", "");
    });

});

function searchNewsletterMail() {
    $("#newsletterMailsList").dataTable().fnDraw();
}

function addNewsletterMail() {
    var newsletterMailName = $('#newsletterMailName').val();
    var recipientGroupId = $('#recipientGroupId').val();
    var newsletterMailSubject = $('#newsletterMailSubject').val();
    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: 'addNewsletterMail',
        data: "name=" + newsletterMailName + "&recipientGroupId=" + recipientGroupId + "&subject=" + newsletterMailSubject,
        success: function (response) {
            if (response.status == 'SUCCESS') {
                $("#newsletterMailsList").dataTable().fnDraw();
                $("#addNewNewsletterMailForm").trigger('reset');
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

function removeNewsletterMail(idValue) {
    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: 'deleteNewsletterMail',
        data: "id=" + idValue,
        success: function (response) {
            if (response.status == 'SUCCESS') {
                $("#newsletterMailsList").dataTable().fnDraw();
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
