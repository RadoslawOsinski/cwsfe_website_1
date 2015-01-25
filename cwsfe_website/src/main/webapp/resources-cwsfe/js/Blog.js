require(['jquery', 'knockout', 'shared_scripts', 'ajaxCodeFetcher'], function ($, ko, shared_scripts, ajaxCodeFetcher) {

    function BlogViewModel() {
        var self = this;
        self.keywords = getKeywords();
        self.DEFAULT_POSTS_PER_PAGE = 5;
        self.currentPage = ko.observable($('#currentPage').val());
        self.categoryId = ko.observable($('#categoryId').val());
        self.blogI18nPairs = getBlogI18nPairs(self.DEFAULT_POSTS_PER_PAGE, self.currentPage());
        self.totalPairs = getBlogI18nPairsTotal();
        self.numberOfPages = (Math.floor(self.totalPairs / self.DEFAULT_POSTS_PER_PAGE) + (self.totalPairs % self.DEFAULT_POSTS_PER_PAGE > 0 ? 1 : 0));

        self.isPreviewLinkVisible = ko.computed(function () {
            return parseInt(self.currentPage()) > 0;
        });
        self.isNextLinkVisible = ko.computed(function () {
            return parseInt(self.currentPage()) + 1 < parseInt(self.numberOfPages);
        });
    }

    var blogViewModel = new BlogViewModel();

    $(document).ready(function () {
        ko.applyBindings(blogViewModel);

        showKeywords();

        $('#prevPageLink').click(function () {
            goToPreviousLink(parseInt(blogViewModel.currentPage()));
        });
        $('#nextPageLink').click(function () {
            goToNextLink(parseInt(blogViewModel.currentPage()), parseInt(blogViewModel.numberOfPages));
        });

        showPagingButtons();
        showPagedPosts();
        showPostAuthors();
        showPostKeywords();
        showPostComments();

        ajaxCodeFetcher.fetchCode();
    });

    function goToPreviousLink(currentPage) {
        if (currentPage > 0) {
            window.location = 'blog?categoryId=' + blogViewModel.categoryId() + '&currentPage=' + (currentPage - 1);
        }
    }

    function goToNextLink(currentPage, numberOfPages) {
        if (currentPage < numberOfPages) {
            window.location = '/blog?categoryId=' + blogViewModel.categoryId() + '&currentPage=' + (currentPage + 1);
        }
    }

    function showPagingButtons() {
        var linksToAppend = '';
        for (var i = 0; i < blogViewModel.numberOfPages; ++i) {
            if (i == blogViewModel.currentPage()) {
                linksToAppend += '<span class="current">' + (i + 1) + '</span>';
            } else {
                linksToAppend += '<a href="/blog?categoryId=' + blogViewModel.categoryId() + '&currentPage=' + i + '" class="nextprev" title="Go to page ' + (i + 1) + '">' + (i + 1) + '</a>';
            }
        }
        $('#prevPageLink').after(linksToAppend);
    }

    function getBlogI18nPairs(blogPerPage, currentPage) {
        var localeLanguage = $('#localeLanguage').val();
        var categoryId = $('#categoryId').val();
        var blogI18nPairs = null;
        $.ajax({
            type: 'GET',
            async: false,
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            url: '/../../CWSFE_CMS/rest/blogI18nPairs',
            data: 'limit=' + blogPerPage + '&offset=' + (currentPage * blogPerPage) + '&categoryId=' + categoryId + '&languageCode=' + localeLanguage,
            success: function (response) {
                blogI18nPairs = response;
                $.each(blogI18nPairs, function (index, blogI18nPair) {
                    blogI18nPair.blogPostI18nContent.postShortcut = blogI18nPair.blogPostI18nContent.postShortcut.replace('#!CURRENT_BLOG_POST_ID!#', blogI18nPair.blogPost.id);
                });
            },
            error: function (xhr) {
            }
        });
        return blogI18nPairs;
    }

    function getBlogI18nPairsTotal() {
        var localeLanguage = $('#localeLanguage').val();
        var categoryId = $('#categoryId').val();
        var blogI18nPairsTotal = null;
        $.ajax({
            type: 'GET',
            async: false,
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            url: '/../../CWSFE_CMS/rest/blogI18nPairsTotal',
            data: 'categoryId=' + categoryId + '&languageCode=' + localeLanguage,
            success: function (response) {
                blogI18nPairsTotal = parseInt(response.total);
            },
            error: function (xhr) {
                blogI18nPairsTotal = 0;
            }
        });
        return blogI18nPairsTotal;
    }

    function getSingleBlogUrl(postId, postI18nId) {
        return '/blog/singlePost/' + postId + '/' + postI18nId;
    }

    function getKeywords() {
        var localeLanguage = $('#localeLanguage').val();
        var keywords = null;
        $.ajax({
            type: 'GET',
            async: false,
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            url: '/../../CWSFE_CMS/rest/blogKeywordsList',
            data: 'languageCode=' + localeLanguage,
            success: function (response) {
                keywords = response;
            }
        });
        return keywords;
    }

    function showKeywords() {
        if (blogViewModel.keywords != null) {
            var liTags = '';
            $.each(blogViewModel.keywords, function (index, keyword) {
                liTags += '<li><a href="' + '/blog_category/' + keyword.id + '">' + keyword.keywordName + '</a></li>';
            });
            $('#keywordsList').append(liTags);
        }
    }

    /**
     * Shows posts on blog page
     */
    function showPagedPosts() {
        if (blogViewModel.blogI18nPairs == null || blogViewModel.blogI18nPairs.length == 0) {
            $('#noPostsMessage').show('');
        } else {
            var postsToAppend = '';

            $.each(blogViewModel.blogI18nPairs, function (index, blogI18nPair) {
                var sectionTag = '<section>';
                sectionTag += '<div class="blog-post" data-post="' + blogI18nPair.blogPost.id + '">';
                sectionTag += '<div class="blog-post-date">';
                var postCreation = new Date(blogI18nPair.blogPost.postCreationDate);
                sectionTag += postCreation.getDate();
                sectionTag += '<span>';
                if (postCreation.getMonth() < 9) {
                    sectionTag += '0' + (postCreation.getMonth() + 1);
                } else {
                    sectionTag += (postCreation.getMonth() + 1);
                }
                sectionTag += '.';
                sectionTag += postCreation.getFullYear();
                sectionTag += '</span>';
                sectionTag += '</div>';
                sectionTag += '<a href="/blog/singlePost/' + blogI18nPair.blogPost.id + '/' + blogI18nPair.blogPostI18nContent.id + '">';
                sectionTag += '<h3 class="blog-post-title">' + blogI18nPair.blogPostI18nContent.postTitle + '</h3>';
                sectionTag += '</a>';
                sectionTag += '<ul class="blog-post-info fixed">';
                sectionTag += '<li class="author" data-author="' + blogI18nPair.blogPost.postAuthorId + '">&nbsp;</li>';
                sectionTag += '<li class="categories" data-categories="' + blogI18nPair.blogPost.id + '">&nbsp;</li>';
                sectionTag += '<li class="comments" data-comments="' + blogI18nPair.blogPost.id + '">&nbsp;</li>';
                sectionTag += '</li>';
                sectionTag += '</ul>';
                sectionTag += blogI18nPair.blogPostI18nContent.postShortcut;
                sectionTag += '<div class="fixed">';
                sectionTag += '<div class="blog-post-readmore">';
                sectionTag += '<a href="/blog/singlePost/' + blogI18nPair.blogPost.id + '/' + blogI18nPair.blogPostI18nContent.id + '">— ' + $('#readMoreI18n').val() + '</a>';
                sectionTag += '</div>';
                sectionTag += '</div>';
                sectionTag += '<div class="hr"></div>';
                sectionTag += '</div>';
                sectionTag += '</section>';
                postsToAppend += sectionTag;
            });

            var postList = $('#postsList');
            postList.val('');
            postList.append(postsToAppend);
        }
    }

    function showPostAuthors() {
        $.each($('li.author'), function (index, authorLiTag) {
            var authorTag = $(this);
            var authorId = authorTag.data('author');
            $.ajax({
                type: 'GET',
                async: false,
                contentType: 'application/json;charset=utf-8',
                dataType: 'json',
                url: '/../../CWSFE_CMS/rest/author/' + authorId,
                success: function (response) {
                    var author = response;
                    if (author.googlePlusAuthorLink != null && author.googlePlusAuthorLink !== '') {
                        authorTag.html('<a rel="author" href="' + author.googlePlusAuthorLink + '" target="_blank">' + author.lastName + ' ' + author.firstName + '</a></li>');
                    } else {
                        authorTag.html(author.lastName + ' ' + author.firstName);
                    }
                },
                error: function (xhr) {
                }
            });
        });
    }

    function showPostKeywords() {
        var localeLanguage = $('#localeLanguage').val();
        $.each($('li.categories'), function (index, postKeywordsLiTag) {
            var postKeywordsTag = $(this);
            $.ajax({
                type: 'GET',
                async: false,
                contentType: 'application/json;charset=utf-8',
                dataType: 'json',
                url: '/../../CWSFE_CMS/rest/postKeywords',
                data: 'blogPostId=' + postKeywordsTag.data('categories') + '&languageCode=' + localeLanguage,
                success: function (response) {
                    var postKeywords = response;
                    var postKeywordsContent = '&nbsp;';
                    $.each(postKeywords, function (index, keyword) {
                        postKeywordsContent += ((index > 0) ? ', ' : '') + keyword.keywordName;
                    });
                    postKeywordsTag.html(postKeywordsContent);
                },
                error: function (xhr) {
                }
            });
        });
    }

    function showPostComments() {
        $.each($('li.comments'), function (index, commentsLiTag) {
            var commentsTag = $(this);
            $.ajax({
                type: 'GET',
                async: false,
                contentType: 'application/json;charset=utf-8',
                dataType: 'json',
                url: '/../../CWSFE_CMS/rest/comments',
                data: 'blogPostI18nContentId=' + commentsTag.data('comments'),
                success: function (response) {
                    commentsTag.html('&nbsp;' + response.count);
                }
            });
        });
    }

})
