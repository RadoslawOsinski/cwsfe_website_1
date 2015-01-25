require(['jquery', 'knockout', 'shared_scripts', 'ajaxCodeFetcher'], function ($, ko, shared_scripts, ajaxCodeFetcher) {

    function BlogViewModel() {
        var self = this;
    }

    var blogViewModel = new BlogViewModel();

    $(document).ready(function () {
        ko.applyBindings(blogViewModel);

        showBlogKeywords();
        showPost();

        $('#addCommentButton').click(function () {
            addComment();
        });

        ajaxCodeFetcher.fetchCode();
    });

    function getBlogI18nPair() {
        var blogI18nPair = null;
        $.ajax({
            type: 'GET',
            async: false,
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            url: '../../../CWSFE_CMS/rest/blog/singlePost/' + $('#blogPostId').val() + '/' + $('#blogPostI18nContentId').val(),
            success: function (response) {
                blogI18nPair = response;
            },
            error: function (xhr) {
            }
        });
        return blogI18nPair;
    }

    function showPost() {
        showPostCategories();
        var blogI18nPair = getBlogI18nPair();
        if (blogI18nPair != null) {
            showPostAuthor(blogI18nPair.blogPost.postAuthorId);
            $('#postTitle').html(blogI18nPair.blogPostI18nContent.postTitle);
            $('#postDescription').html(blogI18nPair.blogPostI18nContent.postDescription);
            var postCreation = new Date(blogI18nPair.blogPost.postCreationDate);

            $('#postCreationDateDay').html(postCreation.getDate());
            var monthText = '';
            if (postCreation.getMonth() < 9) {
                monthText += '0' + (postCreation.getMonth() + 1);
            } else {
                monthText += (postCreation.getMonth() + 1);
            }
            $('#postCreationDateMonthAndYear').html(monthText + '.' + postCreation.getFullYear());
        }
        showPostCommentsCount();

    }

    function showBlogKeywords() {
        var localeLanguage = $('#localeLanguage').val();
        $.ajax({
            type: 'GET',
            async: true,
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            url: '../../../CWSFE_CMS/rest/blogKeywordsList',
            data: 'languageCode=' + localeLanguage,
            success: function (response) {
                showKeywords(response);
            }
        });
    }

    function showKeywords(keywords) {
        if (keywords != null) {
            var liTags = '';
            $.each(keywords, function (index, keyword) {
                liTags += '<li><a href="' + '/blog_category/' + keyword.id + '">' + keyword.keywordName + '</a></li>';
            });
            $('#keywordsList').append(liTags);
        }
    }

    function showPostAuthor(postAuthorId) {
        $.ajax({
            type: 'GET',
            async: true,
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            url: '../../../CWSFE_CMS/rest/author/' + postAuthorId,
            success: function (response) {
                var author = response;
                var authorText = '';
                if (author.googlePlusAuthorLink != null) {
                    authorText += '<a rel="author" target="_blank" href="' + author.googlePlusAuthorLink + '">' + author.lastName + ' ' + author.firstName + '</a>';
                } else {
                    authorText += author.lastName + ' ' + author.firstName;
                }
                $('#postAuthorText').html(authorText);
            },
            error: function (xhr) {
            }
        });
    }

    function showPostCategories() {
        var localeLanguage = $('#localeLanguage').val();
        var postCategories = null;
        $.ajax({
            type: 'GET',
            async: false,
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            url: '../../../CWSFE_CMS/rest/postKeywords',
            data: 'blogPostId=' + $('#blogPostId').val() + '&languageCode=' + localeLanguage,
            success: function (response) {
                postCategories = response;
                var postKeywordsContent = '&nbsp;';
                if (postCategories != null) {
                    $.each(postCategories, function (index, keyword) {
                        postKeywordsContent += ((index > 0) ? ', ' : '') + keyword.keywordName;
                    });
                }
                $('#postCategories').html(postKeywordsContent);
            },
            error: function (xhr) {
            }
        });
        return postCategories;
    }

    function showPostCommentsCount() {
        $.ajax({
            type: 'GET',
            async: true,
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            url: '../../../CWSFE_CMS/rest/comments',
            data: 'blogPostI18nContentId=' + $('#blogPostI18nContentId').val(),
            success: function (response) {
                var commentsCount = response.count;
                $('#commentsCount1').html(commentsCount);
                $('#commentsCount2').html(commentsCount);
            }
        });
    }

    function addComment() {
        $.ajax({
            type: 'POST',
            async: true,
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            url: '../../../CWSFE_CMS/rest/comments',
            data: $('#addCommentForm').serialize(),
            success: function (response) {
                $('#addCommentInfoMessage').show();
                $('#addCommentErrorMessage').hide();
                $('#addCommentForm').reset();
            },
            error: function (xhr) {
                $('#addCommentInfoMessage').hide();
                $('#addCommentErrorMessage').show();
            }
        });
    }

})
