require(['jquery', 'shared_scripts', 'tipsy', 'cycle_all', 'jqueryAccordion'], function ($) {

    $(document).ready(function () {

        $('#accordion-1').accordion();

        // -------------------------------------------------------------------------------------------------------
        //  Make entire service overviews clickable
        // -------------------------------------------------------------------------------------------------------
        $(".service-overview li, .service-overview-index li").click(function () {
            window.location = $(this).find("a").attr("href");
            return false;
        });
        /*
         if($('#slideshow-clients').size()){

         $('#slideshow-clients ul').cycle({
         timeout: 4000,// milliseconds between slide transitions (0 to disable auto advance)
         fx: 'fade',// choose your transition type, ex: fade, scrollUp, shuffle, etc...
         delay: 0, // additional delay (in ms) for first transition (hint: can be negative)
         speed: 1000,  // speed of the transition (any valid fx speed value)
         pause: true,// true to enable "pause on hover"
         cleartypeNoBg: true,// set to true to disable extra cleartype fixing (leave false to force background color setting on slides)
         pauseOnPagerHover: 0 // true to pause when hovering over pager link
         });

         }
         */
        var localeLanguage = $('#localeLanguage').val();

        $.ajax({
            type: 'GET',
            contentType: 'application/json',
            dataType: 'json',
            url: CMS_ADDRESS + '/rest/newsI18nContentByNews',
            data: 'languageCode=' + localeLanguage + '&newsType=Services&folderName=Services&newsCode=Services',
            success: function (response) {
                $('#newsTitle').html(response.newsTitle);
                $('#newsDescription').html(response.newsDescription);
            },
            error: function (xhr) {
            }
        });
    });

});