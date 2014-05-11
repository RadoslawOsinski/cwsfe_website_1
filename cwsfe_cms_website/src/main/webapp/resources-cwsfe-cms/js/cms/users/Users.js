jQuery(document).ready(function () {

    jQuery('#usersList').dataTable({
        'iTabIndex': -1,
        'sPaginationType': 'full_numbers',
        'bProcessing': true,
        'bServerSide': true,
        'bFilter': false,
        'aLengthMenu': [10, 20, 30],
        'bPaginate': true,
        'bLengthChange': true,
        'sAjaxSource': 'usersList',
        aoColumns: [
            { 'bSortable': false, mData: '#' },
            { 'bSortable': false, mData: 'userName' },
            { 'bSortable': false, mData: 'status' ,
                'fnRender': function (o) {
                    if (o.aData['status'] === 'N') {
                        return '<span class="highlight green">New</span>';
                    } else if (o.aData['status'] === 'L') {
                        return '<span class="highlight blue">Locked</span>';
                    } else if (o.aData['status'] === 'D') {
                        return '<span class="highlight red">Deleted</span>';
                    }
                    return '<span class="highlight red">Deleted</span>';
                }
            },
            { 'bSortable': false, mData: 'id',
                "fnRender": function (o) {
                    return '' +
                    '<form method="GET" action="users/' + o.aData['id'] + '">' +
                    '<button class="button green tiny" type="submit" tabindex="-1">Select</button>' +
                    '<a class="icon awesome thumbs-up tipsy-trigger" title="Unlock" tabindex="-1" onclick="unlockUser(' + o.aData['id'] + ');"></a>' +
                    '<a class="icon awesome thumbs-down tipsy-trigger" title="Lock" tabindex="-1" onclick="lockUser(' + o.aData['id'] + ');"></a>' +
                    '<a class="icon awesome icon-remove-sign tipsy-trigger" title="Delete" tabindex="-1" onclick="removeUser(' + o.aData['id'] + ');"></a>' +
                    '</form>';
                }
            }
        ]
    });

});

function addUser() {
    var username = $('#username').val();
    var passwordHash = $('#passwordHash').val();
    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: 'addUser',
        data: "username=" + username + "&passwordHash=" + passwordHash,
        success: function (response) {
            if (response.status == 'SUCCESS') {
                $("#usersList").dataTable().fnDraw();
                $('#userName').val('');
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

function removeUser(idValue) {
    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: 'deleteUser',
        data: "id=" + idValue,
        success: function (response) {
            if (response.status == 'SUCCESS') {
                $("#usersList").dataTable().fnDraw();
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

function lockUser(idValue) {
    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: 'lockUser',
        data: "id=" + idValue,
        success: function (response) {
            if (response.status == 'SUCCESS') {
                $("#usersList").dataTable().fnDraw();
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

function unlockUser(idValue) {
    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: 'unlockUser',
        data: "id=" + idValue,
        success: function (response) {
            if (response.status == 'SUCCESS') {
                $("#usersList").dataTable().fnDraw();
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
