jQuery(document).ready(function () {

    jQuery('#blogPostsList').dataTable({
        'iTabIndex': -1,
        'bAutoWidth': true,
        'sPaginationType': 'full_numbers',
        'bProcessing': true,
        'bServerSide': true,
        'bFilter': false,
        'aLengthMenu': [10, 20, 30],
        'bPaginate': true,
        'bLengthChange': true,
        'sAjaxSource': 'blogPostsList',
        'fnServerParams': function (aoData) {
            aoData.push(
                { 'name': "searchAuthorId", 'value': $('#searchAuthorId').val() },
                { 'name': "searchPostTextCode", 'value': $('#searchPostTextCode').val() }
            );
        },
        aoColumns: [
            { 'bSortable': false, mData: '#' },
            { 'bSortable': false, mData: 'author' },
            { 'bSortable': false, mData: 'postTextCode' },
            { 'bSortable': false, mData: 'postCreationDate' },
            { 'bSortable': false, mData: 'id',
                "fnRender": function (o) {
                    return '' +
                        '<form method="GET" action="blogPosts/' + o.aData['id'] + '">' +
                        '<button class="button green tiny" type="submit" tabindex="-1">Select</button>' +
                        '<button class="button red tiny" onclick="removeBlogPost(' + o.aData['id'] + ');return false;" tabindex="-1">Delete</button>' +
                        '</form>'
                        ;
                }
            }
        ]
    });

    $('#searchAuthor').autocomplete({
        source: function (request, response) {
            $.ajax({
                url: 'authorsDropList',
                data: {
                    limit: 5,
                    term: request.term
                },
                success: function (data) {
                    response($.map(data.data, function (item) {
                        return {
                            value: item.lastName + " " + item.firstName,
                            id: item.id
                        }
                    }));
                }
            });
        },
        minLength: 0,
        select: function (event, ui) {
            $('#searchAuthorId').val(ui.item.id);
        }
    }).focus(function () {
            $(this).autocomplete("search", "");
        });

    $('#author').autocomplete({
        source: function (request, response) {
            $.ajax({
                url: 'authorsDropList',
                data: {
                    limit: 5,
                    term: request.term
                },
                success: function (data) {
                    response($.map(data.data, function (item) {
                        return {
                            value: item.lastName + " " + item.firstName,
                            id: item.id
                        }
                    }));
                }
            });
        },
        minLength: 0,
        select: function (event, ui) {
            $('#authorId').val(ui.item.id);
        }
    }).focus(function () {
            $(this).autocomplete("search", "");
        });

});

function searchBlogPosts() {
    $("#blogPostsList").dataTable().fnDraw();
}

function addBlogPost() {
    var authorId = $('#authorId').val();
    var postTextCode = $('#postTextCode').val();
    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: 'addBlogPost',
        data: "postAuthorId=" + authorId + "&postTextCode=" + postTextCode,
        success: function (response) {
            if (response.status == 'SUCCESS') {
                $("#blogPostsList").dataTable().fnDraw();
                $("#addNewBlogPostForm").trigger('reset');
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

function removeBlogPost(idValue) {
    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: 'deleteBlogPost',
        data: "id=" + idValue,
        success: function (response) {
            if (response.status == 'SUCCESS') {
                $("#blogPostsList").dataTable().fnDraw();
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
