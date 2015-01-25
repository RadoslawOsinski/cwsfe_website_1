require(['jquery', 'knockout', 'shared_scripts', 'tipsy', 'cycle_all', 'prettyPhoto'], function ($, ko) {

    function SingleProductsEntryViewModel() {
        var self = this;
        self.DEFAULT_NEWS_PER_PAGE = 6;
        self.newsI18n = getNewsI18nPair();
    }

    var singleProductsEntryViewModel = new SingleProductsEntryViewModel();

    $(document).ready(function () {
        ko.applyBindings(singleProductsEntryViewModel);

        var $slideShow = $('#slideshow-products');
        if ($slideShow.size()) {
            $slideShow.find('ul').cycle({
                timeout: 5000,	// milliseconds between slide transitions (0 to disable auto advance)
                fx: 'fade',						     		// choose your transition type, ex: fade, scrollUp, shuffle, etc...
                pager: '#slideshow-products-pager', // selector for element to use as pager container
                delay: 0, 						     		// additional delay (in ms) for first transition (hint: can be negative)
                speed: 1000,  					     	// speed of the transition (any valid fx speed value)
                pause: true,									// true to enable "pause on hover"
                cleartypeNoBg: true,			    // set to true to disable extra cleartype fixing (leave false to force background color setting on slides)
                pauseOnPagerHover: 0 			    // true to pause when hovering over pager link
            });
        }

    });

    function getNewsI18nPair() {
        var cmsNewsI18nContentsId = $('#cmsNewsI18nContentsId').val();
        var newsI18n = null;
        $.ajax({
            type: 'GET',
            async: false,
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            url: '/../../CWSFE_CMS/rest/singleNewsI18nContent',
            data: 'id=' + cmsNewsI18nContentsId,
            success: function (response) {
                newsI18n = response;
                $('#page-header-title').html(newsI18n.newsTitle);
                $('#newsTitle').html(newsI18n.newsTitle);
                $('#newsDescription').html(newsI18n.newsDescription);
            },
            error: function (xhr) {
            }
        });
        return newsI18n;
    }

});
