$(document).ready(function(){

    if ($('#slideshow-index').size()){

        $('#slideshow-index ul').cycle({
            timeout: 5000,	// milliseconds between slide transitions (0 to disable auto advance)
            fx: 'fade',						// choose your transition type, ex: fade, scrollUp, shuffle, etc...
            prev: '#text-slideshow-prev',// selector for element to use as click trigger for next slide
            next: '#text-slideshow-next',// selector for element to use as click trigger for previous slide
            pager: '#index-slideshow-pager',// selector for element to use as pager container
            delay: 0, 						// additional delay (in ms) for first transition (hint: can be negative)
            speed: 1000,  					// speed of the transition (any valid fx speed value)
            pause: true,						// true to enable "pause on hover"
            cleartypeNoBg: true,			// set to true to disable extra cleartype fixing (leave false to force background color setting on slides)
            pauseOnPagerHover: 0 			// true to pause when hovering over pager link
        });

    }
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
});
