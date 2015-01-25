define(['ajaxCodeFetcher', 'jquery'], function () {

    var fetchCode = function () {
        $("code.palio_code_for_replace").each(function () {
            var codeTag = $(this);
            var codeContentForReplace = codeTag.attr('id');
            var indexOfComma = codeContentForReplace.indexOf(',');
            var postId = codeContentForReplace.substring(0, indexOfComma);
            console.log('postId: ' + postId);
            var codeId = codeContentForReplace.substring(indexOfComma + 1);
            console.log('codeId: ' + codeId);
            var codeToReplace = '...';
            $.ajax({
                type: 'GET',
                async: true,
                contentType: 'application/json;charset=utf-8',
                dataType: 'json',
                url: '/../../CWSFE_CMS/rest/blogPostCode/' + postId + '/' + codeId,
                success: function (data) {
                    codeToReplace = data.code.trim();
                }
            }).done(function () {
                codeTag.replaceWith("<code>" + codeToReplace + "</code>");
            });
        });
    };

    return {
        fetchCode: fetchCode
    };

});