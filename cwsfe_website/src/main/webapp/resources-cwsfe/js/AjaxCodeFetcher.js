define(['ajaxCodeFetcher', 'jquery'], function () {

    var fetchCode = function () {
        $("code.palio_code_for_replace").each(function () {
            var codeTag = $(this);
            var codeContentForReplace = codeTag.attr('id');
            var indexOfComma = codeContentForReplace.indexOf(',');
            var postId = codeContentForReplace.substring(0, indexOfComma);
            var codeId = codeContentForReplace.substring(indexOfComma + 1);
            var codeToReplace = '...';
            $.ajax({
                url: '/blogPostCode/' + postId + '/' + codeId,
                dataType: 'json',
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