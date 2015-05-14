require(['jquery', 'knockout', 'shared_scripts', 'tipsy', 'cycle_all', 'prettyPhoto'], function ($, ko) {

    function ProductsViewModel() {
        var self = this;
        self.DEFAULT_NEWS_PER_PAGE = 6;
        self.currentPage = ko.observable($('#currentPage').val());
        self.newsI18nPairs = getNewsI18nPairs(self.DEFAULT_NEWS_PER_PAGE, self.currentPage());
        self.totalPairs = getNewsI18nPairsTotal();
        self.numberOfPages = (Math.floor(self.totalPairs / self.DEFAULT_NEWS_PER_PAGE) + (self.totalPairs % self.DEFAULT_NEWS_PER_PAGE > 0 ? 1 : 0))

        self.isPreviewLinkVisible = ko.computed(function () {
            return parseInt(self.currentPage()) > 0;
        });
        self.isNextLinkVisible = ko.computed(function () {
            return parseInt(self.currentPage()) + 1 < parseInt(self.numberOfPages);
        });
    }

    var productsViewModel = new ProductsViewModel();

    $(document).ready(function () {
        ko.applyBindings(productsViewModel);

        $('.preview-options').css('opacity', '0');
        $('.products-item-preview').hover(
            function () {
                $(this).find('.preview-options').stop().fadeTo(400, 1);
            }, function () {
                $(this).find('.preview-options').stop().fadeTo(200, 0);
            }
        );

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

        $("a[data-gal^='prettyPhoto']").prettyPhoto({
            opacity: 0.80,					// Value between 0 and 1
            autoplay_slideshow: true,
            slideshow: 5000,
            show_title: false,
            default_width: 500,
            default_height: 500,
            theme: 'light_rounded',	// light_rounded / dark_rounded / light_square / dark_square / facebook
            hideflash: false,				// Hides all the flash object on a page, set to TRUE if flash appears over prettyPhoto
            modal: false,						// If set to true, only the close button will close the window
            overlay_gallery: false	//disable thumbnails
        });

        listProductsFolders();

        $('#prevProductsPageLink').click(function () {
            goToPreviousProductsLink(parseInt(productsViewModel.currentPage()));
    });
        $('#nextProductsPageLink').click(function () {
            goToNextProductsLink(parseInt(productsViewModel.currentPage()), parseInt(productsViewModel.numberOfPages));
        });

        showPagingButtons();
        showNewsThumbnails();
    });

    function goToPreviousProductsLink(currentPage) {
        if (currentPage > 0) {
            window.location = '/products?newsFolderId=' + $('#newsFolder').val() + '&currentPage=' + (currentPage - 1);
        }
    }

    function goToNextProductsLink(currentPage, numberOfPages) {
        if (currentPage < numberOfPages) {
            window.location = '/products?newsFolderId=' + $('#newsFolder').val() + '&currentPage=' + (currentPage + 1);
        }
    }

    function showPagingButtons() {
        var linksToAppend = '';
        for (var i = 0; i < productsViewModel.numberOfPages; ++i) {
            if (i == productsViewModel.currentPage()) {
                linksToAppend += '<span class="current">' + (i + 1) + '</span>';
            } else {
                linksToAppend += '<a href="/products?newsFolderId=' + $('#newsFolder').val() + '&currentPage=' + i + '" class="nextprev" title="Go to page ' + (i + 1) + '">' + (i + 1) + '</a>';
            }
        }
        $('#prevProductsPageLink').after(linksToAppend);
    }

    function getNewsI18nPairs(newsPerPage, currentPage) {
        var localeLanguage = $('#localeLanguage').val();
        var folderName = $('#newsFolder').val();
        if (folderName === '') {
            folderName = 'AllProducts';
        }
        var newsI18nPairs = null;
        $.ajax({
            type: 'GET',
            async: false,
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            url: '/../../CWSFE_CMS/rest/newsI18nPairs',
            data: 'newsPerPage=' + newsPerPage + '&offset=' + (currentPage * newsPerPage) + '&folderName=' + folderName + '&languageCode=' + localeLanguage + '&newsType=Products',
            success: function (response) {
                newsI18nPairs = response;
            },
            error: function (xhr) {
            }
        });
        return newsI18nPairs;
    }

    function getNewsI18nPairsTotal() {
        var localeLanguage = $('#localeLanguage').val();
        var folderName = $('#newsFolder').val();
        if (folderName === '') {
            folderName = 'AllProducts';
        }
        var newsI18nPairsTotal = null;
        $.ajax({
            type: 'GET',
            async: false,
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            url: '/../../CWSFE_CMS/rest/newsI18nPairsTotal',
            data: 'folderName=' + folderName + '&languageCode=' + localeLanguage + '&newsType=Products',
            success: function (response) {
                newsI18nPairsTotal = parseInt(response.total);
            },
            error: function (xhr) {
                newsI18nPairsTotal = 0;
            }
        });
        return newsI18nPairsTotal;
    }

    function getSingleNewsUrl(cmsNewsId, cmsNewsI18nContentsId) {
        return '/products/singleNews/' + cmsNewsId + '/' + cmsNewsI18nContentsId;
    }

    function appendProductsItemPreview(newsI18nPair) {
        var images = getNewsImages(newsI18nPair.cmsNews.id);
        var thumbnailImageTag = '';
        if (images.thumbnailImage != null) {
            thumbnailImageTag = '<img src="/CWSFE_CMS/newsImages/' + o.aData.fileName + '"' +
            ' height="' + images.thumbnailImage.height + '"' +
            ' width="' + images.thumbnailImage.width + '"' +
            ' alt="' + images.thumbnailImage.title + ' image"/>';
        }
        var previewOptionsTag = '';
        if (images.newsImages != null) {
            previewOptionsTag += '<div class="preview-options">';
            previewOptionsTag += '<a href="/newsImages/?imageId=' + images.thumbnailImage.id + '" class="lightbox tip2" title="" data-gal="prettyPhoto[gallery_' + newsI18nPair.cmsNews.id + ']"><spring:message code="ViewLargeVersion"/></a>';
            previewOptionsTag += '<div style="display: none;">';
            $.each(images.newsImages, function (index, largeImageInfo) {
                previewOptionsTag += '<a href="/CWSFE_CMS/newsImages/' + largeImageInfo.id + '" class="lightbox tip2" title="" data-gal="prettyPhoto[gallery_' + newsI18nPair.cmsNews.id + ']"><spring:message code="ViewLargeVersion"/></a>';
            });
            previewOptionsTag += '</div>';
            previewOptionsTag += '<a href="' + getSingleNewsUrl(newsI18nPair.cmsNews.id, newsI18nPair.cmsNewsI18nContent.id) + '" class="view">&#8212; ' + newsI18nPair.cmsNewsI18nContent.newsTitle + '</a>';
            previewOptionsTag += '</div>';
        }
        return '<div class="products-item-preview">' + thumbnailImageTag + previewOptionsTag + '</div>';
    }

    function getNewsImages(newsId) {
        var images = null;
        $.ajax({
            type: 'GET',
            async: false,
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            url: '/../../CWSFE_CMS/rest/newsImages',
            data: 'newsId=' + newsId,
            success: function (response) {
                images = response;
            },
            error: function (xhr) {
            }
        });
        return images;
    }

    function appendThumbnailTitle(singleNewsUrl, newsTitle) {
        return '<h5><a href="' + singleNewsUrl + '">' + newsTitle + '</a></h5>';
    }

    function appendThumbnailShortcut(newsShortcut) {
        return '<p class = "products_paragraph">' + newsShortcut + '</p>';
    }

    function showNewsThumbnails() {
        if (productsViewModel.newsI18nPairs == null || productsViewModel.newsI18nPairs.length == 0) {
            $('#noProductsMessage').show('');
        } else {
            var thumbnailsToAppend = '';

            $.each(productsViewModel.newsI18nPairs, function (index, newsI18nPair) {
                var liContent = '';
                liContent += appendProductsItemPreview(newsI18nPair);
                liContent += appendThumbnailTitle(getSingleNewsUrl(newsI18nPair.cmsNews.id, newsI18nPair.cmsNewsI18nContent.id), newsI18nPair.cmsNewsI18nContent.newsTitle);
                liContent += appendThumbnailShortcut(newsI18nPair.cmsNewsI18nContent.newsShortcut);
                var lastClass = '';
                if (index % 3 == 2) {
                    lastClass = 'last';
                }
                var liTag = '<li class="' + lastClass + '">' + liContent + '</li>';
                thumbnailsToAppend += liTag;
            });

            $('#newsThumbnails').val('');
            $('#newsThumbnails').append(thumbnailsToAppend);
        }
    }

    /**
     * Displays folder names in products
     */
    function listProductsFolders() {
        var localeLanguage = $('#localeLanguage').val();
        var newsFolder = $('#newsFolder').val();

        $('#products-filter').html('');
        $.each(['AllProducts'], function (index, value) {
            var firstClass = '';
            if (index == 0) {
                firstClass = 'first'
            }
            var currentClass = '';
            if ((newsFolder == null && index == 0) || (newsFolder != null && newsFolder == value)) {
                currentClass = 'current';
            }
            var folderNameI18n = '';
            $.ajax({
                type: 'GET',
                async: false,
                contentType: 'application/json;charset=utf-8',
                dataType: 'text',
                url: '/../../CWSFE_CMS/rest/getTranslation',
                data: 'languageCode=' + localeLanguage + '&category=Folders&key=' + value,
                success: function (response) {
                    folderNameI18n = response;
                    $('#products-filter').append('<li class="' + firstClass + ' ' + currentClass + '"><a href="/products?currentPage=0&newsFolder' + value + '">' + folderNameI18n + '</a></li>');
                },
                error: function (xhr) {
                }
            });
        });
    }

});