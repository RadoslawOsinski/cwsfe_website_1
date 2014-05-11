jQuery(window).load(function() {

	jQuery('#loading-block').fadeOut(function() {
        var arrowSelector = jQuery('.arrow-up, .arrow-down, .arrow-right');
        if (arrowSelector.length) {
			arrowSelector.each(function() {
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

});
