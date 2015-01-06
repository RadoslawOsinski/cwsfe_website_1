require(['jquery', 'knockout', 'shared_scripts', 'ajaxCodeFetcher'], function ($, ko, shared_scripts, ajaxCodeFetcher) {

    function BlogViewModel() {
        var self = this;
        self.keywords = getKeywords();
        //self.DEFAULT_NEWS_PER_PAGE = 5;
        //self.currentPage = ko.observable($('#currentPage').val());
        //self.postsI18nPairs = getPostsI18nPairs(self.DEFAULT_NEWS_PER_PAGE, self.currentPage());
        //self.totalPairs = getPostsI18nPairsTotal();
        //self.numberOfPages = (Math.floor(self.totalPairs / self.DEFAULT_NEWS_PER_PAGE) + (self.totalPairs % self.DEFAULT_NEWS_PER_PAGE > 0 ? 1 : 0))
        //
        //self.isPreviewLinkVisible = ko.computed(function () {
        //    return parseInt(self.currentPage()) > 0;
        //});
        //self.isNextLinkVisible = ko.computed(function () {
        //    return parseInt(self.currentPage()) + 1 < parseInt(self.numberOfPages);
        //})
    }

    var blogViewModel = new BlogViewModel();

    $(function () {
        ajaxCodeFetcher.fetchCode();
    });

    $(document).ready(function () {
        ko.applyBindings(blogViewModel);

        showKeywords();
    });

    function getKeywords() {
        var localeLanguage = $('#localeLanguage').val();
        var keywords = null;
        $.ajax({
            type: 'GET',
            async: false,
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            url: '../CWSFE_CMS/rest/blogKeywordsList',
            data: 'languageCode=' + localeLanguage,
            success: function (response) {
                keywords = response;
            },
            error: function (xhr) {
            }
        });
        return keywords;
    }

    function showKeywords() {
        if (blogViewModel.keywords != null) {
            var liTags = '';
            $.each(blogViewModel.keywords, function (index, keyword) {
                liTags += '<li><a href="' + '/blog/category/' + keyword.id + '">' + keyword.keywordName + '</a></li>';
            });
            $('#keywordsList').append(liTags);
        }
    }

})
