(function(a){function b(a){if(a.attr("title")||typeof a.attr("original-title")!="string"){a.attr("original-title",a.attr("title")||"").removeAttr("title")}}function c(c,d){this.$element=a(c);this.options=d;this.enabled=true;b(this.$element)}c.prototype={show:function(){var b=this.getTitle();if(b&&this.enabled){var c=this.tip();c.find(".tipsy-inner")[this.options.html?"html":"text"](b);c[0].className="tipsy";c.remove().css({top:0,left:0,visibility:"hidden",display:"block"}).appendTo(document.body);var d=a.extend({},this.$element.offset(),{width:this.$element[0].offsetWidth,height:this.$element[0].offsetHeight});var e=c[0].offsetWidth,f=c[0].offsetHeight;var g=typeof this.options.gravity=="function"?this.options.gravity.call(this.$element[0]):this.options.gravity;var h;switch(g.charAt(0)){case"n":h={top:d.top+d.height+this.options.offset,left:d.left+d.width/2-e/2};break;case"s":h={top:d.top-f-this.options.offset,left:d.left+d.width/2-e/2};break;case"e":h={top:d.top+d.height/2-f/2,left:d.left-e-this.options.offset};break;case"w":h={top:d.top+d.height/2-f/2,left:d.left+d.width+this.options.offset};break}if(g.length==2){if(g.charAt(1)=="w"){h.left=d.left+d.width/2-15}else{h.left=d.left+d.width/2-e+15}}c.css(h).addClass("tipsy-"+g);if(this.options.fade){c.stop().css({opacity:0,display:"block",visibility:"visible"}).animate({opacity:this.options.opacity})}else{c.css({visibility:"visible",opacity:this.options.opacity})}}},hide:function(){if(this.options.fade){this.tip().stop().fadeOut(function(){a(this).remove()})}else{this.tip().remove()}},getTitle:function(){var a,c=this.$element,d=this.options;b(c);var a,d=this.options;if(typeof d.title=="string"){a=c.attr(d.title=="title"?"original-title":d.title)}else if(typeof d.title=="function"){a=d.title.call(c[0])}a=(""+a).replace(/(^\s*|\s*$)/,"");return a||d.fallback},tip:function(){if(!this.$tip){this.$tip=a('<div class="tipsy"></div>').html('<div class="tipsy-arrow"></div><div class="tipsy-inner"/></div>')}return this.$tip},validate:function(){if(!this.$element[0].parentNode){this.hide();this.$element=null;this.options=null}},enable:function(){this.enabled=true},disable:function(){this.enabled=false},toggleEnabled:function(){this.enabled=!this.enabled}};a.fn.tipsy=function(b){function d(d){var e=a.data(d,"tipsy");if(!e){e=new c(d,a.fn.tipsy.elementOptions(d,b));a.data(d,"tipsy",e)}return e}function e(){var a=d(this);a.hoverState="in";if(b.delayIn==0){a.show()}else{setTimeout(function(){if(a.hoverState=="in")a.show()},b.delayIn)}}function f(){var a=d(this);a.hoverState="out";if(b.delayOut==0){a.hide()}else{setTimeout(function(){if(a.hoverState=="out")a.hide()},b.delayOut)}}if(b===true){return this.data("tipsy")}else if(typeof b=="string"){return this.data("tipsy")[b]()}b=a.extend({},a.fn.tipsy.defaults,b);if(!b.live)this.each(function(){d(this)});if(b.trigger!="manual"){var g=b.live?"live":"bind",h=b.trigger=="hover"?"mouseenter":"focus",i=b.trigger=="hover"?"mouseleave":"blur";this[g](h,e)[g](i,f)}return this};a.fn.tipsy.defaults={delayIn:0,delayOut:0,fade:false,fallback:"",gravity:"n",html:false,live:false,offset:0,opacity:.8,title:"title",trigger:"hover"};a.fn.tipsy.elementOptions=function(b,c){return a.metadata?a.extend({},c,a(b).metadata()):c};a.fn.tipsy.autoNS=function(){return a(this).offset().top>a(document).scrollTop()+a(window).height()/2?"s":"n"};a.fn.tipsy.autoWE=function(){return a(this).offset().left>a(document).scrollLeft()+a(window).width()/2?"e":"w"}})(jQuery);

history.navigationMode = 'compatible';

jQuery(window).load(function() {
    jQuery('#loading-block').fadeOut(function() {
        if (jQuery('.arrow-up, .arrow-down, .arrow-right').length) {
            jQuery('.arrow-up, .arrow-down, .arrow-right').each(function() {
                var t = jQuery(this);
                var e = t.find('.perc span');
                var v = parseInt(e.attr('data-value'), 10);

                e.data('counter', '');
                e.html('0');

                var $counter = e;
                e.animateNumber(v, 20);
            });
        }
    });

    if (jQuery('.tipsy-trigger').length) {
        jQuery('.tipsy-trigger').each(function() {
            jQuery(this).tipsy({
                gravity: ((jQuery(this).attr('data-tipsy-direction')) ? jQuery(this).attr('data-tipsy-direction') : 's'),
                fade: true
            });
        });
    }
    jQuery('.tipsy-header').tipsy({gravity: 'n', fade: true});

});

jQuery(document).ready(function() {
    var plus = '<span class="plus"></span>';
    jQuery('.nav li').each(function() {
        var t = jQuery(this);
        if (t.find('.submenu').length) {
            t.find('a').eq(0).append(plus);
            t.addClass('has-menu');
        }
    });

    jQuery('.nav li.has-menu > a').on('click', function(event) {
        event.preventDefault();
        var t = jQuery(this).parent('li');
        t.find('ul.submenu').slideToggle();
    });
    jQuery('.nav li.active').each(function() {
        var t = jQuery(this);
        if (t.parent().attr('class') == 'submenu') {
            t.parent().slideToggle();
        }
        else {
            t.find('a').append('<span class="arrow"></span>');
        }
    });

    jQuery('#nav_select').on('change', function() {
        var t = jQuery(this);
        var url = t.find('option:selected').val();
        if (url) {
            location.href = url;
        }
    });

});

function getLoadTime() {
    var now = new Date().getTime();
    // Get the performance object window .
    performance = window.performance || window.mozPerformance || window.msPerformance || window.webkitPerformance || { };
    var timing = performance.timing || { };
    if (timing) {
        var load_time = now - timing.navigationStart;
        $('#loadTimeValue').html(load_time);
    }
}

window.onload = function() {
    getLoadTime();
};