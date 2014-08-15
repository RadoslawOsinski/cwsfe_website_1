require(['jquery', 'shared_scripts', 'ajaxCodeFetcher'], function ($, shared_scripts, ajaxCodeFetcher) {

    $(function () {
        ajaxCodeFetcher.fetchCode();
    });

});