require(['jquery', 'shared_scripts', 'tipsy', 'cycle_all', 'prettyPhoto'], function ($) {

    $(document).ready(function () {

        $('.preview-options').css('opacity', '0');
        $('.portfolio-item-preview').hover(
            function () {
                $(this).find('.preview-options').stop().fadeTo(400, 1);
            }, function () {
                $(this).find('.preview-options').stop().fadeTo(200, 0);
            }
        );

        var $slideShow = $('#slideshow-portfolio');
        if ($slideShow.size()) {
            $slideShow.find('ul').cycle({
                timeout: 5000,	// milliseconds between slide transitions (0 to disable auto advance)
                fx: 'fade',						     		// choose your transition type, ex: fade, scrollUp, shuffle, etc...
                pager: '#slideshow-portfolio-pager', // selector for element to use as pager container
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

    });

});