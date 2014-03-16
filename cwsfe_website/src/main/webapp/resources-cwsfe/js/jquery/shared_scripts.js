$(document).ready(function(){

    $("ul#dropdown-menu li").hover(function () {
        $(this).addClass("hover");
        $('ul:first', this).css({visibility: "visible",display: "none"}).slideDown(200);
    }, function () {
        $(this).removeClass("hover");
        $('ul:first', this).css({visibility: "hidden"});
    });

    $("ul#dropdown-menu li ul li:has(ul)").find("a:first").addClass("arrow");

});
