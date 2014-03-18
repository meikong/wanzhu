/* Plugin directory
What you can find in this file

	0.) window.log
	1.) Yeonope - yepnope.com - yepnopejs.com/
	2.) Styled Selects (lightweight) - code.google.com/p/lnet/wiki/jQueryStyledSelectOverview
	3.) jQuery elastic for textarea - 
	4.) jQuery tipsy - 
	5.) jQuery DropKick - style Select
	6.) flexcroll - 
	-3.) jQuery Easing Plugin - gsgd.co.uk/sandbox/jquery/easing/
	8.) jQuery Color Plugin

*/

$(function(){
    /*if($('.w-s').height() < $(window).height()){
        $('.w-s').css('height',$(window).height())
    }*/
    /*if($('#main').height() < $(window).height()){
        $('#main').css('height': $(window).height())
    }*/
})
/**
* 0.) window.log
* by html5boilerplate 1.0
*/
window.log = function(){
  log.history = log.history || [];
  log.history.push(arguments);
  arguments.callee = arguments.callee.caller;  
  //if(this.console) //console.log( Array.prototype.slice.call(arguments) );
};
(function(b){function c(){}for(var d="assert,count,debug,dir,dirxml,error,exception,group,groupCollapsed,groupEnd,info,log,markTimeline,profile,profileEnd,time,timeEnd,trace,warn".split(","),a;a=d.pop();)b[a]=b[a]||c})(window.console=window.console||{});


/**
* 1.) Yepnope
* version: 1.0.2
*/
(function(a,b,c){function H(){var a=z;a.loader={load:G,i:0};return a}function G(a,b,c){var e=b=="c"?r:q;i=0,b=b||"j",u(a)?F(e,a,b,this.i++,d,c):(h.splice(this.i++,0,a),h.length==1&&E());return this}function F(a,c,d,g,j,l){function q(){!o&&A(n.readyState)&&(p.r=o=1,!i&&B(),n.onload=n.onreadystatechange=null,e(function(){m.removeChild(n)},0))}var n=b.createElement(a),o=0,p={t:d,s:c,e:l};n.src=n.data=c,!k&&(n.style.display="none"),n.width=n.height="0",a!="object"&&(n.type=d),n.onload=n.onreadystatechange=q,a=="img"?n.onerror=q:a=="script"&&(n.onerror=function(){p.e=p.r=1,E()}),h.splice(g,0,p),m.insertBefore(n,k?null:f),e(function(){o||(m.removeChild(n),p.r=p.e=o=1,B())},z.errorTimeout)}function E(){var a=h.shift();i=1,a?a.t?e(function(){a.t=="c"?D(a):C(a)},0):(a(),B()):i=0}function D(a){var c=b.createElement("link"),d;c.href=a.s,c.rel="stylesheet",c.type="text/css";if(!a.e&&(o||j)){var g=function(a){e(function(){if(!d)try{a.sheet.cssRules.length?(d=1,B()):g(a)}catch(b){b.code==1e3||b.message=="security"||b.message=="denied"?(d=1,e(function(){B()},0)):g(a)}},0)};g(c)}else c.onload=function(){d||(d=1,e(function(){B()},0))},a.e&&c.onload();e(function(){d||(d=1,B())},z.errorTimeout),!a.e&&f.parentNode.insertBefore(c,f)}function C(a){var c=b.createElement("script"),d;c.src=a.s,c.onreadystatechange=c.onload=function(){!d&&A(c.readyState)&&(d=1,B(),c.onload=c.onreadystatechange=null)},e(function(){d||(d=1,B())},z.errorTimeout),a.e?c.onload():f.parentNode.insertBefore(c,f)}function B(){var a=1,b=-1;while(h.length- ++b)if(h[b].s&&!(a=h[b].r))break;a&&E()}function A(a){return!a||a=="loaded"||a=="complete"}var d=b.documentElement,e=a.setTimeout,f=b.getElementsByTagName("script")[0],g={}.toString,h=[],i=0,j="MozAppearance"in d.style,k=j&&!!b.createRange().compareNode,l=j&&!k,m=k?d:f.parentNode,n=a.opera&&g.call(a.opera)=="[object Opera]",o="webkitAppearance"in d.style,p=o&&"async"in b.createElement("script"),q=j?"object":n||p?"img":"script",r=o?"img":q,s=Array.isArray||function(a){return g.call(a)=="[object Array]"},t=function(a){return Object(a)===a},u=function(a){return typeof a=="string"},v=function(a){return g.call(a)=="[object Function]"},w=[],x={},y,z;z=function(a){function h(a,b){function i(a){if(u(a))g(a,f,b,0,c);else if(t(a))for(h in a)a.hasOwnProperty(h)&&g(a[h],f,b,h,c)}var c=!!a.test,d=c?a.yep:a.nope,e=a.load||a.both,f=a.callback,h;i(d),i(e),a.complete&&b.load(a.complete)}function g(a,b,d,e,g){var h=f(a),i=h.autoCallback;if(!h.bypass){b&&(b=v(b)?b:b[a]||b[e]||b[a.split("/").pop().split("?")[0]]);if(h.instead)return h.instead(a,b,d,e,g);d.load(h.url,h.forceCSS||!h.forceJS&&/css$/.test(h.url)?"c":c,h.noexec),(v(b)||v(i))&&d.load(function(){H(),b&&b(h.origUrl,g,e),i&&i(h.origUrl,g,e)})}}function f(a){var b=a.split("!"),c=w.length,d=b.pop(),e=b.length,f={url:d,origUrl:d,prefixes:b},g,h;for(h=0;h<e;h++)g=x[b[h]],g&&(f=g(f));for(h=0;h<c;h++)f=w[h](f);return f}var b,d,e=this.yepnope.loader;if(u(a))g(a,0,e,0);else if(s(a))for(b=0;b<a.length;b++)d=a[b],u(d)?g(d,0,e,0):s(d)?z(d):t(d)&&h(d,e);else t(a)&&h(a,e)},z.addPrefix=function(a,b){x[a]=b},z.addFilter=function(a){w.push(a)},z.errorTimeout=1e4,b.readyState==null&&b.addEventListener&&(b.readyState="loading",b.addEventListener("DOMContentLoaded",y=function(){b.removeEventListener("DOMContentLoaded",y,0),b.readyState="complete"},0)),a.yepnope=H()})(this,this.document)


/*
 * 2.) jQuery Styled Select Boxes
 * version: 1.1 (2009/03/24)
 * @requires jQuery v1.2.6 or later
 *
 * Examples and documentation at: http://code.google.com/p/lnet/wiki/jQueryStyledSelectOverview
 *
 * Copyright (c) 2008 Lasar Liepins, liepins.org, liepins@gmail.com
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */
jQuery.fn.styledSelect=function(settings){settings=jQuery.extend({selectClass:'styledSelect',openSelectClass:'open',optionClass:'option',selectedOptionClass:'selected',closedOptionClass:'closed',firstOptionClass:'first',lastOptionClass:'last',zIndexApply:false,zIndexStart:250,deactiveOnBackgroundClick:true},settings);var currentZIndex=settings.zIndexStart;this.each(function(){var s=jQuery(this);var cs=jQuery('<div></div>').attr('class',settings.selectClass);if(settings.zIndexApply){cs.css('z-index',currentZIndex-2);};var csl=jQuery('<ul></li>');if(settings.zIndexApply){csl.css('z-index',currentZIndex-1);};cs.append(csl);s.hide(0).after(cs);cs=s.next();jQuery('option',s).each(function(){if(jQuery(this).attr('value')==undefined){jQuery(this).attr('value',jQuery(this).text());}});var closedSelect=function(){jQuery('ul',cs).html('');addOption(s.val(),jQuery(':selected',s).text(),clickSelect);cs.removeClass(settings.openSelectClass);jQuery('ul li',cs).removeClass(settings.selectedOptionClass).removeClass(settings.optionClass).addClass(settings.closedOptionClass);if(settings.deactiveOnBackgroundClick){$(document).unbind('mousedown',closedSelect);cs.unbind('mousedown');}};var clickSelect=function(){jQuery('ul',cs).empty();jQuery('option',s).each(function(i){addOption(jQuery(this).val(),jQuery(this).text(),clickOption);});cs.addClass(settings.openSelectClass);jQuery('ul li:first-child',cs).addClass(settings.firstOptionClass);jQuery('ul li:last-child',cs).addClass(settings.lastOptionClass);if(settings.deactiveOnBackgroundClick){$(document).bind('mousedown',closedSelect);cs.bind('mousedown',function(){return false;});}};var clickOption=function(){var val=jQuery(this).attr('rel');s.val(val);s.change();closedSelect();};var addOption=function(optVal,optName,callBack){var cso=jQuery('<li></li>').attr('rel',optVal).text(optName).click(callBack).addClass(settings.optionClass);if(settings.zIndexApply){cso.css('z-index',currentZIndex);};if(s.val()==optVal){cso.addClass(settings.selectedOptionClass);};jQuery('ul',cs).append(cso);};closedSelect();s.change(closedSelect);currentZIndex-=3;});return this;};


/**
*   @name                           Elastic
*   @descripton                     Elastic is jQuery plugin that grow and shrink your textareas automatically
*   @version                        1.6.11
*   @requires                       jQuery 1.2.6+
*
*   @author                         Jan Jarfalk
*   @author-email                   jan.jarfalk@unwrongest.com
*   @author-website                 http://www.unwrongest.com
*
*   @licence                        MIT License - http://www.opensource.org/licenses/mit-license.php
*/

(function($){ 
    jQuery.fn.extend({  
        elastic: function() {
        
            //  We will create a div clone of the textarea
            //  by copying these attributes from the textarea to the div.
            var mimics = [
                'paddingTop',
                'paddingRight',
                'paddingBottom',
                'paddingLeft',
                'fontSize',
                'lineHeight',
                'fontFamily',
                'width',
                'fontWeight',
                'border-top-width',
                'border-right-width',
                'border-bottom-width',
                'border-left-width',
                'borderTopStyle',
                'borderTopColor',
                'borderRightStyle',
                'borderRightColor',
                'borderBottomStyle',
                'borderBottomColor',
                'borderLeftStyle',
                'borderLeftColor'
                ];
            
            return this.each( function() {

                // Elastic only works on textareas
                if ( this.type !== 'textarea' ) {
                    return false;
                }
                    
            var $textarea   = jQuery(this),
                $twin       = jQuery('<div />').css({
                    'position'      : 'absolute',
                    'display'       : 'none',
                    'word-wrap'     : 'break-word',
                    'white-space'   :'pre-wrap'
                }),
                lineHeight  = parseInt($textarea.css('line-height'),10) || parseInt($textarea.css('font-size'),'10'),
                minheight   = parseInt($textarea.css('height'),10) || lineHeight*3,
                maxheight   = parseInt($textarea.css('max-height'),10) || Number.MAX_VALUE,
                goalheight  = 0;
                
                // Opera returns max-height of -1 if not set
                if (maxheight < 0) { maxheight = Number.MAX_VALUE; }
                    
                // Append the twin to the DOM
                // We are going to meassure the height of this, not the textarea.
                $twin.appendTo($textarea.parent());
                
                // Copy the essential styles (mimics) from the textarea to the twin
                var i = mimics.length;
                while(i--){
                    $twin.css(mimics[i].toString(),$textarea.css(mimics[i].toString()));
                }
                
                // Updates the width of the twin. (solution for textareas with widths in percent)
                function setTwinWidth(){
                    var curatedWidth = Math.floor(parseInt($textarea.width(),10));
                    if($twin.width() !== curatedWidth){
                        $twin.css({'width': curatedWidth + 'px'});
                        
                        // Update height of textarea
                        update(true);
                    }
                }
                
                // Sets a given height and overflow state on the textarea
                function setHeightAndOverflow(height, overflow){
                
                    var curratedHeight = Math.floor(parseInt(height,10));
                    if($textarea.height() !== curratedHeight){
                        $textarea.css({'height': curratedHeight + 'px','overflow':overflow});
                    }
                }
                
                // This function will update the height of the textarea if necessary 
                function update(forced) {
                    
                    // Get curated content from the textarea.
                    var textareaContent = $textarea.val().replace(/&/g,'&amp;').replace(/ {2}/g, '&nbsp;').replace(/<|>/g, '&gt;').replace(/\n/g, '<br />');
                    
                    // Compare curated content with curated twin.
                    var twinContent = $twin.html().replace(/<br>/ig,'<br />');
                    
                    if(forced || textareaContent+'&nbsp;' !== twinContent){
                    
                        // Add an extra white space so new rows are added when you are at the end of a row.
                        $twin.html(textareaContent+'&nbsp;');
                        
                        // Change textarea height if twin plus the height of one line differs more than 3 pixel from textarea height
                        if(Math.abs($twin.height() + lineHeight - $textarea.height()) > 3){
                            
                            var goalheight = $twin.height();
                            if(goalheight >= maxheight) {
                                setHeightAndOverflow(maxheight,'auto');
                            } else if(goalheight <= minheight) {
                                setHeightAndOverflow(minheight,'hidden');
                            } else {
                                setHeightAndOverflow(goalheight,'hidden');
                            }
                            
                        }
                        
                    }
                    
                }
                
                // Hide scrollbars
                $textarea.css({'overflow':'hidden'});
                
                // Update textarea size on keyup, change, cut and paste
                $textarea.bind('keyup change cut paste', function(){
                    update(); 
                });
                
                // Update width of twin if browser or textarea is resized (solution for textareas with widths in percent)
                $(window).bind('resize', setTwinWidth);
                $textarea.bind('resize', setTwinWidth);
                $textarea.bind('update', update);
                
                // Compact textarea on blur
                $textarea.bind('blur',function(){
                    if($twin.height() < maxheight){
                        if($twin.height() > minheight) {
                            $textarea.height($twin.height());
                        } else {
                            $textarea.height(minheight);
                        }
                    }
                });
                
                // And this line is to catch the browser paste event
                $textarea.bind('input paste',function(e){ setTimeout( update, 250); });             
                
                // Run update once when elastic is initialized
                update();
                
            });
            
        } 
    }); 
})(jQuery);

/*
* 4. jQuery tipsy - facebook style tooltips for jquery
* version: 1.0.0a
* (c) 2008-2010 jason frame [jason@onehackoranother.com]
* released under the MIT license
*/
(function($) {
    
    function maybeCall(thing, ctx) {
        return (typeof thing == 'function') ? (thing.call(ctx)) : thing;
    };
    
    function Tipsy(element, options) {
        this.$element = $(element);
        this.options = options;
        this.enabled = true;
        this.fixTitle();
    };
    
    Tipsy.prototype = {
        show: function() {
            var title = this.getTitle();
            if (title && this.enabled) {
                var $tip = this.tip();
                
                $tip.find('.tipsy-inner')[this.options.html ? 'html' : 'text'](title);
                $tip[0].className = 'tipsy'; // reset classname in case of dynamic gravity
                $tip.remove().css({top: 0, left: 0, visibility: 'hidden', display: 'block'}).prependTo(document.body);
                
                var pos = $.extend({}, this.$element.offset(), {
                    width: this.$element[0].offsetWidth,
                    height: this.$element[0].offsetHeight
                });
                
                var actualWidth = $tip[0].offsetWidth,
                    actualHeight = $tip[0].offsetHeight,
                    gravity = maybeCall(this.options.gravity, this.$element[0]);
                
                var tp;
                switch (gravity.charAt(0)) {
                    case 'n':
                        tp = {top: pos.top + pos.height + this.options.offset, left: pos.left + pos.width / 2 - actualWidth / 2};
                        break;
                    case 's':
                        tp = {top: pos.top - actualHeight - this.options.offset, left: pos.left + pos.width / 2 - actualWidth / 2};
                        break;
                    case 'e':
                        tp = {top: pos.top + pos.height / 2 - actualHeight / 2, left: pos.left - actualWidth - this.options.offset};
                        break;
                    case 'w':
                        tp = {top: pos.top + pos.height / 2 - actualHeight / 2, left: pos.left + pos.width + this.options.offset};
                        break;
                }
                
                if (gravity.length == 2) {
                    if (gravity.charAt(1) == 'w') {
                        tp.left = pos.left + pos.width / 2 - 15;
                    } else {
                        tp.left = pos.left + pos.width / 2 - actualWidth + 15;
                    }
                }
                
                $tip.css(tp).addClass('tipsy-' + gravity);
                $tip.find('.tipsy-arrow')[0].className = 'tipsy-arrow tipsy-arrow-' + gravity.charAt(0);
                if (this.options.className) {
                    $tip.addClass(maybeCall(this.options.className, this.$element[0]));
                }
                
                if (this.options.fade) {
                    $tip.stop().css({opacity: 0, display: 'block', visibility: 'visible'}).animate({opacity: this.options.opacity});
                } else {
                    $tip.css({visibility: 'visible', opacity: this.options.opacity});
                }
            }
        },
        
        hide: function() {
            if (this.options.fade) {
                this.tip().stop().fadeOut(function() { $(this).remove(); });
            } else {
                this.tip().remove();
            }
        },
        
        fixTitle: function() {
            var $e = this.$element;
            if ($e.attr('title') || typeof($e.attr('original-title')) != 'string') {
                $e.attr('original-title', $e.attr('title') || '').removeAttr('title');
            }
        },
        
        getTitle: function() {
            var title, $e = this.$element, o = this.options;
            this.fixTitle();
            var title, o = this.options;
            if (typeof o.title == 'string') {
                title = $e.attr(o.title == 'title' ? 'original-title' : o.title);
            } else if (typeof o.title == 'function') {
                title = o.title.call($e[0]);
            }
            title = ('' + title).replace(/(^\s*|\s*$)/, "");
            return title || o.fallback;
        },
        
        tip: function() {
            if (!this.$tip) {
                this.$tip = $('<div class="tipsy"></div>').html('<div class="tipsy-arrow"></div><div class="tipsy-inner"></div>');
            }
            return this.$tip;
        },
        
        validate: function() {
            if (!this.$element[0].parentNode) {
                this.hide();
                this.$element = null;
                this.options = null;
            }
        },
        
        enable: function() { this.enabled = true; },
        disable: function() { this.enabled = false; },
        toggleEnabled: function() { this.enabled = !this.enabled; }
    };
    
    $.fn.tipsy = function(options) {
        
        if (options === true) {
            return this.data('tipsy');
        } else if (typeof options == 'string') {
            var tipsy = this.data('tipsy');
            if (tipsy) tipsy[options]();
            return this;
        }
        
        options = $.extend({}, $.fn.tipsy.defaults, options);
        
        function get(ele) {
            var tipsy = $.data(ele, 'tipsy');
            if (!tipsy) {
                tipsy = new Tipsy(ele, $.fn.tipsy.elementOptions(ele, options));
                $.data(ele, 'tipsy', tipsy);
            }
            return tipsy;
        }
        
        function enter() {
            var tipsy = get(this);
            tipsy.hoverState = 'in';
            if (options.delayIn == 0) {
                tipsy.show();
            } else {
                tipsy.fixTitle();
                setTimeout(function() { if (tipsy.hoverState == 'in') tipsy.show(); }, options.delayIn);
            }
        };
        
        function leave() {
            var tipsy = get(this);
            tipsy.hoverState = 'out';
            if (options.delayOut == 0) {
                tipsy.hide();
            } else {
                setTimeout(function() { if (tipsy.hoverState == 'out') tipsy.hide(); }, options.delayOut);
            }
        };
        
        if (!options.live) this.each(function() { get(this); });
        
        if (options.trigger != 'manual') {
            var binder   = options.live ? 'live' : 'bind',
                eventIn  = options.trigger == 'hover' ? 'mouseenter' : 'focus',
                eventOut = options.trigger == 'hover' ? 'mouseleave' : 'blur';
            this[binder](eventIn, enter)[binder](eventOut, leave);
        }
        
        return this;
        
    };
    
    $.fn.tipsy.defaults = {
        className: null,
        delayIn: 0,
        delayOut: 0,
        fade: false,
        fallback: '',
        gravity: 'n',
        html: false,
        live: false,
        offset: 0,
        opacity: 0.8,
        title: 'title',
        trigger: 'hover'
    };
    
    // Overwrite this method to provide options on a per-element basis.
    // For example, you could store the gravity in a 'tipsy-gravity' attribute:
    // return $.extend({}, options, {gravity: $(ele).attr('tipsy-gravity') || 'n' });
    // (remember - do not modify 'options' in place!)
    $.fn.tipsy.elementOptions = function(ele, options) {
        return $.metadata ? $.extend({}, options, $(ele).metadata()) : options;
    };
    
    $.fn.tipsy.autoNS = function() {
        return $(this).offset().top > ($(document).scrollTop() + $(window).height() / 2) ? 's' : 'n';
    };
    
    $.fn.tipsy.autoWE = function() {
        return $(this).offset().left > ($(document).scrollLeft() + $(window).width() / 2) ? 'e' : 'w';
    };
    
    /**
     * yields a closure of the supplied parameters, producing a function that takes
     * no arguments and is suitable for use as an autogravity function like so:
     *
     * @param margin (int) - distance from the viewable region edge that an
     *        element should be before setting its tooltip's gravity to be away
     *        from that edge.
     * @param prefer (string, e.g. 'n', 'sw', 'w') - the direction to prefer
     *        if there are no viewable region edges effecting the tooltip's
     *        gravity. It will try to vary from this minimally, for example,
     *        if 'sw' is preferred and an element is near the right viewable 
     *        region edge, but not the top edge, it will set the gravity for
     *        that element's tooltip to be 'se', preserving the southern
     *        component.
     */
     $.fn.tipsy.autoBounds = function(margin, prefer) {
		return function() {
			var dir = {ns: prefer[0], ew: (prefer.length > 1 ? prefer[1] : false)},
			    boundTop = $(document).scrollTop() + margin,
			    boundLeft = $(document).scrollLeft() + margin,
			    $this = $(this);

			if ($this.offset().top < boundTop) dir.ns = 'n';
			if ($this.offset().left < boundLeft) dir.ew = 'w';
			if ($(window).width() + $(document).scrollLeft() - $this.offset().left < margin) dir.ew = 'e';
			if ($(window).height() + $(document).scrollTop() - $this.offset().top < margin) dir.ns = 's';

			return dir.ns + (dir.ew ? dir.ew : '');
		}
	};
    
})(jQuery);

/**
 * 5.) DropKick
 * version: 1.0.0
 * Highly customizable <select> lists
 * https://github.com/JamieLottering/DropKick
 *
 * &copy; 2011 Jamie Lottering <http://github.com/JamieLottering>
 *                        <http://twitter.com/JamieLottering>
 * 
 */
(function ($, window, document) {

  var ie6 = false;
  
  var
    // Public methods exposed to $.fn.dropkick()
    methods = {},

    // Cache every <select> element that gets dropkicked
    lists   = [],

    // Convenience keys for keyboard navigation
    keyMap = {
      'left'  : 37,
      'up'    : 38,
      'right' : 39,
      'down'  : 40,
      'enter' : 13
    },

    // HTML template for the dropdowns
    dropdownTemplate = [
      '<div class="dk_container" id="dk_container_{{ id }}" tabindex="{{ tabindex }}">',
        '<a class="dk_toggle">',
          '<span class="dk_label">{{ label }}</span>',
        '</a>',
        '<div class="dk_options">',
          '<ul class="dk_options_inner">',
          '</ul>',
        '</div>',
      '</div>'
    ].join(''),

    // HTML template for dropdown options
    optionTemplate = '<li class="{{ current }}"><a data-dk-dropdown-value="{{ value }}">{{ text }}</a></li>',

    // Some nice default values
    defaults = {
      startSpeed : 1000,  // I recommend a high value here, I feel it makes the changes less noticeable to the user
      theme  : false,
      change : false
    },

    // Make sure we only bind keydown on the document once
    keysBound = false
  ;

  // Called by using $('foo').dropkick();
  methods.init = function (settings) {
	// Help prevent flashes of unstyled content
	  if ($.browser.msie && $.browser.version.substr(0, 1) < 7) {
	    ie6 = true;
	  } else {
	    document.documentElement.className = document.documentElement.className + ' dk_fouc';
	  }
    settings = $.extend({}, defaults, settings);

    return this.each(function () {
      var
        // The current <select> element
        $select = $(this),

        // Store a reference to the originally selected <option> element
        $original = $select.find(':selected').first(),

        // Save all of the <option> elements
        $options = $select.find('option'),

        // We store lots of great stuff using jQuery data
        data = $select.data('dropkick') || {},

        // This gets applied to the 'dk_container' element
        id = $select.attr('id') || $select.attr('name'),

        // This gets updated to be equal to the longest <option> element
        width  = settings.width || $select.outerWidth(),

        // Check if we have a tabindex set or not
        tabindex  = $select.attr('tabindex') ? $select.attr('tabindex') : '',

        // The completed dk_container element
        $dk = false,

        theme
      ;

      // Dont do anything if we've already setup dropkick on this element
      if (data.id) {
        return $select;
      } else {
        data.settings  = settings;
        data.tabindex  = tabindex;
        data.id        = id;
        data.$original = $original;
        data.$select   = $select;
        data.value     = _notBlank($select.val()) || _notBlank($original.attr('value'));
        data.label     = $original.text();
        data.options   = $options;
      }

      // Build the dropdown HTML
      $dk = _build(dropdownTemplate, data);

      // Make the dropdown fixed width if desired
      $dk.find('.dk_toggle').css({
		//'width' : width + 'px'
        'width' : 128 + 'px'
      });

      // Hide the <select> list and place our new one in front of it
      $select.before($dk);

      // Update the reference to $dk
      $dk = $('#dk_container_' + id).fadeIn(settings.startSpeed);

      // Save the current theme
      theme = settings.theme ? settings.theme : 'default';
      $dk.addClass('dk_theme_' + theme);
      data.theme = theme;

      // Save the updated $dk reference into our data object
      data.$dk = $dk;

      // Save the dropkick data onto the <select> element
      $select.data('dropkick', data);

      // Do the same for the dropdown, but add a few helpers
      $dk.data('dropkick', data);

      lists[lists.length] = $select;

      // Focus events
      $dk.bind('focus.dropkick', function (e) {
        $dk.addClass('dk_focus');
      }).bind('blur.dropkick', function (e) {
        $dk.removeClass('dk_open dk_focus');
      });

      setTimeout(function () {
        $select.hide();
      }, 0);
    });
  };

  // Allows dynamic theme changes
  methods.theme = function (newTheme) {
    var
      $select   = $(this),
      list      = $select.data('dropkick'),
      $dk       = list.$dk,
      oldtheme  = 'dk_theme_' + list.theme
    ;

    $dk.removeClass(oldtheme).addClass('dk_theme_' + newTheme);

    list.theme = newTheme;
  };

  // Reset all <selects and dropdowns in our lists array
  methods.reset = function () {
    for (var i = 0, l = lists.length; i < l; i++) {
      var
        listData  = lists[i].data('dropkick'),
        $dk       = listData.$dk,
        $current  = $dk.find('li').first()
      ;

      $dk.find('.dk_label').text(listData.label);
      $dk.find('.dk_options_inner').animate({ scrollTop: 0 }, 0);

      _setCurrent($current, $dk);
      _updateFields($current, $dk, true);
    }
  };

  // Expose the plugin
  $.fn.dropkick = function (method) {
    if (!ie6) {
      if (methods[method]) {
        return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
      } else if (typeof method === 'object' || ! method) {
        return methods.init.apply(this, arguments);
      }
    }
  };

  // private
  function _handleKeyBoardNav(e, $dk) {
    var
      code     = e.keyCode,
      data     = $dk.data('dropkick'),
      options  = $dk.find('.dk_options'),
      open     = $dk.hasClass('dk_open'),
      current  = $dk.find('.dk_option_current'),
      first    = options.find('li').first(),
      last     = options.find('li').last(),
      next,
      prev
    ;

    switch (code) {
      case keyMap.enter:
        if (open) {
          _updateFields(current.find('a'), $dk);
          _closeDropdown($dk);
        } else {
          _openDropdown($dk);
        }
        e.preventDefault();
      break;

      case keyMap.up:
        prev = current.prev('li');
        if (open) {
          if (prev.length) {
            _setCurrent(prev, $dk);
          } else {
            _setCurrent(last, $dk);
          }
        } else {
          _openDropdown($dk);
        }
        e.preventDefault();
      break;

      case keyMap.down:
        if (open) {
          next = current.next('li').first();
          if (next.length) {
            _setCurrent(next, $dk);
          } else {
            _setCurrent(first, $dk);
          }
        } else {
          _openDropdown($dk);
        }
        e.preventDefault();
      break;
      default:
      break;
    }
  }

  // Update the <select> value, and the dropdown label
  function _updateFields(option, $dk, reset) {
    var value, label, data;

    value = option.attr('data-dk-dropdown-value');
    label = option.text();
    data  = $dk.data('dropkick');

    $select = data.$select;
    $select.val(value);

    $dk.find('.dk_label').text(label);

    reset = reset || false;

    if (data.settings.change && !reset) {
      data.settings.change.call($select, value, label);
    }
  }

  // Set the currently selected option
  function _setCurrent($current, $dk) {
    $dk.find('.dk_option_current').removeClass('dk_option_current');
    $current.addClass('dk_option_current');

    _setScrollPos($dk, $current);
  }

  function _setScrollPos($dk, anchor) {
    var height = anchor.prevAll('li').outerHeight() * anchor.prevAll('li').length;
    $dk.find('.dk_options_inner').animate({ scrollTop: height + 'px' }, 0);
  }

  // Close a dropdown
  function _closeDropdown($dk) {
    $dk.removeClass('dk_open');
  }

  // Open a dropdown
  function _openDropdown($dk) {
    var data = $dk.data('dropkick');
    $dk.find('.dk_options').css({ top : $dk.find('.dk_toggle').outerHeight() - 1 });
    $dk.toggleClass('dk_open');

  }

  /**
   * Turn the dropdownTemplate into a jQuery object and fill in the variables.
   */
  function _build (tpl, view) {
    var
      // Template for the dropdown
      template  = tpl,
      // Holder of the dropdowns options
      options   = [],
      $dk
    ;

    template = template.replace('{{ id }}', view.id);
    template = template.replace('{{ label }}', view.label);
    template = template.replace('{{ tabindex }}', view.tabindex);

    if (view.options && view.options.length) {
      for (var i = 0, l = view.options.length; i < l; i++) {
        var
          $option   = $(view.options[i]),
          current   = 'dk_option_current',
          oTemplate = optionTemplate
        ;

        oTemplate = oTemplate.replace('{{ value }}', $option.val());
        oTemplate = oTemplate.replace('{{ current }}', (_notBlank($option.val()) === view.value) ? current : '');
        oTemplate = oTemplate.replace('{{ text }}', $option.text());

        options[options.length] = oTemplate;
      }
    }

    $dk = $(template);
    $dk.find('.dk_options_inner').html(options.join(''));

    return $dk;
  }

  function _notBlank(text) {
    return ($.trim(text).length > 0) ? text : false;
  }

  $(function () {

    // Handle click events on the dropdown toggler
    $('.dk_toggle').live('click', function (e) {
      var $dk  = $(this).parents('.dk_container').first();

      _openDropdown($dk);
      if ("ontouchstart" in window) {
        $dk.addClass('dk_touch');
        $dk.find('.dk_options_inner').addClass('scrollable vertical');
      }
      e.preventDefault();
      return false;
    });

    // Handle click events on individual dropdown options
    $('.dk_options a').live(($.browser.msie ? 'mousedown' : 'click'), function (e) {
      var
        $option = $(this),
        $dk     = $option.parents('.dk_container').first(),
        data    = $dk.data('dropkick')
      ;
    
      _closeDropdown($dk);
      _updateFields($option, $dk);
      _setCurrent($option.parent(), $dk);
    
      e.preventDefault();
      return false;
    });

    // Setup keyboard nav
    $(document).bind('keydown.dk_nav', function (e) {
      var
        // Look for an open dropdown...
        $open    = $('.dk_container.dk_open'),

        // Look for a focused dropdown
        $focused = $('.dk_container.dk_focus'),

        // Will be either $open, $focused, or null
        $dk = null
      ;

      // If we have an open dropdown, key events should get sent to that one
      if ($open.length) {
        $dk = $open;
      } else if ($focused.length && !$open.length) {
        // But if we have no open dropdowns, use the focused dropdown instead
        $dk = $focused;
      }

      if ($dk) {
        _handleKeyBoardNav(e, $dk);
      }
    });
  });
})(jQuery, window, document);


/*
This license text has to stay intact at all times:
fleXcroll Public License Version
Cross Browser Custom Scroll Bar Script by Hesido.
Public version - Free for non-commercial uses.

This script cannot be used in any commercially built
web sites, or in sites that relates to commercial
activities. This script is not for re-distribution.
For licensing options:
Contact Emrah BASKAYA @ www.hesido.com

Derivative works are only allowed for personal uses,
and they cannot be redistributed.

FleXcroll Public Key Code: 20050907122003339
MD5 hash for this license: 9ada3be4d7496200ab2665160807745d

End of license text---
*/
//fleXcroll v2.0.0
eval(function(p,a,c,k,e,r){e=function(c){return(c<a?'':e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)r[e(c)]=k[c]||e(c);k=[function(e){return r[e]}];e=function(){return'\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p}('B F={2W:[],4F:8(){5(Y.1t){Y.5U(\'<11 57="5C/6p">.1R-5G-4T {3B: 2m !6a;}</11>\')};M.V(16,\'5m\',M.5E)},3O:8(g){B h=Y,J=16,23=6F;5(!h.1t||!h.4M)C;5(33(g)==\'3I\')g=Y.1t(g);5(g==Z||23.3b.2B(\'62\')!=-1||((23.3b.2B(\'6U\')!=-1||23.3b.2B(\'7d\')!=-1)&&!(33(5e)!="6x"&&5e.74))||23.7k==\'6h\'||(23.79.2B(\'7o\')!=-1&&23.3b.2B(\'70\')!=-1)){5(g!=Z)2b(g,\'1R-7g\',\'1R-5G-4T\');5(16.50)16.50(g);C};5(g.14){g.14.1G();C};5(F.5u(g))C;5(!g.1M||g.1M==\'\'){B k="6M",c=1;1B(Y.1t(k+c)!=Z){c++};g.1M=k+c}g.4I=2q 5q();g.14=2q 5q();B l=g.1M,4=g.4I,I=g.14;4.27={5Y:[\'-1s\',0],6Y:[0,\'-1s\'],6t:[\'1s\',0],7s:[0,\'1s\'],7f:[0,\'-1p\'],6e:[0,\'1p\'],7u:[0,\'-4W\'],77:[0,\'+4W\']};4.3R=["-2s","2s"];4.41=["-2s","2s"];4.1V=[[A,A],[A,A]];B m=T(\'6I\',E),H=T(\'7m\',E),G=T(\'66\',E),1l=T(\'72\',E);B o=T(\'7q\',E),1x=T(\'6B\',E),37=A;1l.D.1K=\'4P 5i 7i\';1l.2e();g.11.3B=\'2m\';1x.D.6l="7b";1x.D.1Z="53";1x.D.13="53";1x.D.1O="3h";1x.D.21="-6Q";1x.2e();B p=g.15,5y=g.1q;2u(g,1l,\'1g\',[\'1K-1b-13\',\'1K-25-13\',\'1K-1e-13\',\'1K-2g-13\']);B q=g.15,5k=g.1q,3D=5y-5k,43=p-q;B s=(g.2c)?g.2c:0,59=(g.2i)?g.2i:0;B t=Y.2Y.1f,3Q=/#([^#.]*)$/;B u=[\'5W\',\'6r\',\'6S\'];4.O=[];4.29=[];4.6c=4.U=[];4.6H=4.1I=[];4.1Q=[A,A];4.2D=A;4.2G=A;4.17=[];4.1T=[0,0];4.1v=[];4.3K=[];4.19=[];4.2o=[A,A];4.2x=[0,0];1B(g.4N){m.1a(g.4N)};m.1a(o);g.1a(H);g.1a(1l);B w=P(g,\'1O\');5(w!=\'3h\'&&w!=\'5g\'){g.11.1O=w="35"};5(w==\'5g\')g.11.1O="3h";B x=P(g,\'5C-64\');g.11.5s=\'1b\';H.D.13="52";H.D.1Z="52";H.D.1e="1g";H.D.1b="1g";2u(g,1l,"1g",[\'N-1b\',\'N-1e\',\'N-25\',\'N-2g\']);B y=g.1q,5w=g.15,48;48=H.15;H.D.6j="6O 5i 6z";5(H.15>48)37=E;H.D.6W="1g";2u(1l,g,A,[\'N-1b\',\'N-1e\',\'N-25\',\'N-2g\']);1N(H);1N(g);4.19[0]=H.1D-g.1D;4.19[2]=H.1F-g.1F;g.11.5o=P(g,"N-2g");g.11.5A=P(g,"N-25");1N(H);1N(g);4.19[1]=H.1D-g.1D;4.19[3]=H.1F-g.1F;g.11.5o=P(1l,"N-1e");g.11.5A=P(1l,"N-1b");B z=4.19[2]+4.19[3],3G=4.19[0]+4.19[1];g.11.1O=w;H.11.5s=x;2u(g,H,A,[\'N-1b\',\'N-25\',\'N-1e\',\'N-2g\']);G.D.13=g.1q+\'K\';G.D.1Z=g.15+\'K\';H.D.13=y+\'K\';H.D.1Z=5w+\'K\';G.D.1O=\'3h\';G.D.1e=\'1g\';G.D.1b=\'1g\';4.31=G.D.21;H.1a(m);g.1a(G);G.1a(1x);m.D.1O=\'35\';H.D.1O=\'35\';m.D.1e="0";m.D.13="46%";H.D.3B=\'2m\';H.D.1b="-"+4.19[2]+"K";H.D.1e="-"+4.19[0]+"K";4.4h=1x.15;4.3l=8(){B a=m.60,3p=6v=0;1n(B i=0;i<a.2y;i++){5(a[i].1q){3p=R.1X(a[i].1q,3p)}};4.U[0]=((4.17[1]&&!4.1v[1])||4.29[1])?g.1q-4.1T[0]:g.1q;4.1I[0]=3p+z;C 4.1I[0]};4.3f=8(){4.U[1]=((4.17[0]&&!4.1v[0])||4.29[0])?g.15-4.1T[1]:g.15;4.1I[1]=m.15+3G-2;C 4.1I[1]};4.4K=8(){m.D.2K=\'3T\';m.D.2K=\'6g\'};4.4d=8(){H.D.13=(37)?(4.U[0]-z-3D)+\'K\':4.U[0]+\'K\'};4.4l=8(){H.D.1Z=(37)?(4.U[1]-3G-43)+\'K\':4.U[1]+\'K\'};4.2O=8(){4.3l();4.3f();G.2U=2q 4j();B a=G.2U;2O(a,\'6K\',1);a.2S=[1j(P(a.7,\'N-1e\')),1j(P(a.7,\'N-2g\'))];a.7.D.N=\'1g\';a.7.Q=0;a.7.39=E;a.7.2w=1;m.4R=a.7;3M(a,0);4.1T[0]=(4.2o[1])?0:a.1k.1q;4.4d();G.3n=2q 4j();B b=G.3n;2O(b,\'68\',0);b.2S=[1j(P(b.7,\'N-1b\')),1j(P(b.7,\'N-25\'))];b.7.D.N=\'1g\';b.7.Q=0;b.7.39=A;b.7.2w=0;m.6D=b.7;5(J.55)b.7.D.1O=\'35\';3M(b,0);4.1T[1]=(4.2o[0])?0:b.1k.15;4.4l();G.D.1Z=g.15+\'K\';b.2I=T(\'6n\');G.1a(b.2I);b.2I.4a=8(){b.7.3j=E;4.1L=b.7;b.7.3t=E;b.7.2z=A;G.2U.7.2z=A;F.V(h,\'3Y\',1P);F.V(h,\'2M\',3r);F.V(h,\'3v\',2Q);C A}};4.1L=Z;4.2O();M.4f(o,G);5(!M.1z(g,\'4Y\',2A)||!M.1z(g,\'5c\',2A)){g.5V=2A};M.1z(g,\'4Y\',2A);M.1z(g,\'5c\',2A);M.1z(m,\'4G\',3C);M.1z(G,\'4G\',3C);g.6b(\'6G\',\'0\');M.V(g,\'6q\',8(e){5(g.2X)C;5(!e){B e=J.1m};B a=e.58;4.5D=a;I.24();5(4.27[\'1S\'+a]&&!16.55){I.1h(4.27[\'1S\'+a][0],4.27[\'1S\'+a][1],E);5(e.1u)e.1u();C A}});M.V(g,\'63\',8(e){5(g.2X)C;5(!e){B e=J.1m};B a=e.58;5(4.27[\'1S\'+a]){I.1h(4.27[\'1S\'+a][0],4.27[\'1S\'+a][1],E);5(e.1u)e.1u();C A}});M.V(g,\'6y\',8(){4.5D=A});M.V(h,\'3v\',2C);M.V(g,\'4U\',3P);8 3P(e){5(!e)e=J.1m;B a=(e.18)?e.18:(e.1C)?e.1C:A;5(!a||(a.1r&&a.1r.X(3c("\\\\6i\\\\b"))))C;4.5n=e.2f;4.5J=e.2l;34();1N(g);2C();F.V(h,\'2M\',3J);4.2J=[g.1F+10,g.1F+4.U[0]-10,g.1D+10,g.1D+4.U[1]-10]};8 3J(e){5(!e)e=J.1m;B a=e.2f,4i=e.2l,4x=a+4.4B,42=4i+4.4z;4.4D=(4x<4.2J[0]||4x>4.2J[1])?1:0;4.4E=(42<4.2J[2]||42>4.2J[3])?1:0;4.4s=a-4.5n;4.4C=4i-4.5J;4.3q=(4.4s>40)?1:(4.4s<-40)?-1:0;4.3i=(4.4C>40)?1:(4.4C<-40)?-1:0;5((4.3q!=0||4.3i!=0)&&!4.2j)4.2j=J.2R(8(){5(4.3q==0&&4.3i==0){J.2n(4.2j);4.2j=A;C};34();5(4.4D==1||4.4E==1)I.1h((4.3q*4.4D)+"s",(4.3i*4.4E)+"s",E)},45)};8 2C(){F.1H(h,\'2M\',3J);5(4.2j)J.2n(4.2j);4.2j=A;5(4.3V)J.51(4.3V);5(4.4b)J.2n(4.4b)};8 3x(a){5(4.2G){16.2n(4.2G);4.2G=A}5(!a)G.D.21=4.31};8 34(){4.4B=(J.5v)?J.5v:(h.2t&&h.2t.2i)?h.2t.2i:0;4.4z=(J.5f)?J.5f:(h.2t&&h.2t.2c)?h.2t.2c:0};I.4o=8(){1n(B j=0,3F;3F=u[j];j++){B a=g.30(3F);1n(B i=0,2r;2r=a[i];i++){5(!2r.4J){F.V(2r,\'5Z\',8(){g.2X=E});F.V(2r,\'6J\',6R=8(){g.2X=A});2r.4J=E}}}};g.6u=I.1G=8(a){5(G.W[1]()===0||G.W[0]()===0)C;m.D.N=\'4P\';B b=4.17[0],5b=4.17[1],47=G.2U,2F=G.3n,3e,3u,2T=[];G.D.13=g.1q-3D+\'K\';G.D.1Z=g.15-43+\'K\';2T[0]=4.U[0];2T[1]=4.U[1];4.17[0]=4.3l()>4.U[0];4.17[1]=4.3f()>4.U[1];B c=(b!=4.17[0]||5b!=4.17[1]||2T[0]!=4.U[0]||2T[1]!=4.U[1])?E:A;47.1k.3S(4.17[1]);2F.1k.3S(4.17[0]);3e=(4.17[1]||4.29[1]);3u=(4.17[0]||4.29[0]);4.3l();4.3f();4.4l();4.4d();5(!4.17[0]||!4.17[1]||4.1v[0]||4.1v[1])2F.2I.2e();1i 2F.2I.38();5(3e)3y(47,(3u&&!4.1v[0])?4.1T[1]:0);1i m.D.1e="0";5(3u)3y(2F,(3e&&!4.1v[1])?4.1T[0]:0);1i m.D.1b="0";5(c&&!a)I.1G(E);m.D.N=\'1g\';4.1Q[0]=4.1Q[1]=A};g.6f=I.1h=8(a,b,c,d){B e;5((a||a===0)&&4.O[0]){a=4u(a,0);e=G.3n.7;e.1c=(c)?R.28(R.1X(e.1J,e.1c-a),0):-a;e.3m()}5((b||b===0)&&4.O[1]){b=4u(b,1);e=G.2U.7;e.1c=(c)?R.28(R.1X(e.1J,e.1c-b),0):-b;e.3m()}5(!c)4.1Q[0]=4.1Q[1]=A;5(g.3z&&!d)g.3z();C 4.1V};I.4m=8(a,b){C I.1h(a,b,E)};I.3A=8(a){5(a==Z||!4X(a))C;B b=4Q(a);I.1h(b[0]+4.19[2],b[1]+4.19[0],A);I.1h(0,0,E)};2u(1l,g,\'1g\',[\'1K-1b-13\',\'1K-25-13\',\'1K-1e-13\',\'1K-2g-13\']);M.4f(1l,G);g.2c=0;g.2i=0;I.4o();M.2W[M.2W.2y]=g;2b(g,\'67\',A);I.1G();I.1h(59,s,E);5(t.X(3Q)){I.3A(h.1t(t.X(3Q)[1]))};4.7c=J.2R(8(){B n=1x.15;5(n!=4.4h){I.1G();4.4h=n}},6V);8 4u(v,i){B a=v.7j();v=6C(a);C 1j((a.X(/p$/))?v*4.U[i]*0.9:(a.X(/s$/))?v*4.U[i]*0.1:v)};8 P(a,b){C F.P(a,b)};8 2u(a,b,c,d){B e=2q 4j();1n(B i=0;i<d.2y;i++){e[i]=F.4e(d[i]);b.11[e[i]]=P(a,d[i],e[i]);5(c)a.11[e[i]]=c}};8 T(b,c,d,e){B f=(d)?d:h.4M(\'4q\');5(!d){f.1M=l+\'1S\'+b;f.1r=(c)?b:b+\' 73\'};f.W=[8(){C f.1q},8(){C f.15}];f.2N=(e)?[1P,1P]:[8(a){f.D.13=a},8(a){f.D.1Z=a}];f.5j=[8(){C P(f,"1b")},8(){C P(f,"1e")}];f.1y=(e)?[1P,1P]:[8(a){f.D.1b=a},8(a){f.D.1e=a}];f.2e=8(){f.D.2V="2m"};f.38=8(a){f.D.2V=(a)?P(a,\'2V\'):"6m"};f.D=f.11;C f};8 2O(a,b,c){B d=Y.1t(l+\'-1R-\'+b);B e=(d!=Z)?E:A;5(e){a.1k=T(A,A,d,E);4.2o[c]=E;a.2v=T(b+\'54\');a.1W=T(b+\'5z\');a.7=T(A,A,F.3X(d,\'4q\',\'1R-6N\')[0]);a.20=T(b+\'5r\');a.1E=T(b+\'5H\')}1i{a.1k=T(b+\'6Z\');a.2v=T(b+\'54\');a.1W=T(b+\'5z\');a.7=T(b+\'78\');a.20=T(b+\'5r\');a.1E=T(b+\'5H\');G.1a(a.1k);a.1k.1a(a.7);a.1k.1a(a.2v);a.1k.1a(a.1W);a.7.1a(a.20);a.7.1a(a.1E)}};8 3M(b,c){B d=b.1k,7=b.7,i=7.2w;7.1c=0;7.2E=b.2S[0];7.2Z=d;7.H=H;7.4H=m;7.1w=0;3y(b,c,E);7.3E=8(a){5(!a)7.Q=1j((7.1c*7.26)/7.1J);7.Q=(R.28(R.1X(7.Q,0),7.26));7.1y[i](7.Q+7.2E+"K");5(!7.1w)7.1w=7.1c-1j((7.Q/7.3d)*7.1J);7.1w=(7.Q==0)?0:(7.Q==7.26)?0:(!7.1w)?0:7.1w;5(a){7.1c=1j((7.Q/7.3d)*7.1J);m.1y[i](7.1c+7.1w+"K");4.1V[i]=[-7.1c-7.1w,-7.1J]}};7.3m=8(){7.Q=1j((7.1c*7.3d)/7.1J);7.Q=(R.28(R.1X(7.Q,0),7.26));m.1y[i](7.1c+"K");4.1V[i]=[-7.1c,-7.1J];7.1w=A;7.3E(A)};4.2L=P(7,\'z-5a\');7.D.21=(4.2L=="5X"||4.2L=="0"||4.2L==\'6X\')?2:4.2L;H.D.21=P(7,\'z-5a\');7.4a=8(){7.3t=E;4.1L=7;7.3j=A;7.2z=A;F.V(h,\'3Y\',1P);F.V(h,\'2M\',3r);F.V(h,\'3v\',2Q);C A};7.6s=2C;d.4a=d.7A=8(e){5(!e){B e=J.1m};5(e.18&&(e.18==b.20||e.18==b.1E||e.18==b.7))C;5(e.1C&&(e.1C==b.20||e.1C==b.1E||e.1C==b.7))C;B a,2k=[];34();I.24();1N(7);a=(7.39)?e.2l+4.4z-7.1D:e.2f+4.4B-7.1F;2k[7.2w]=(a<0)?4.41[0]:4.41[1];2k[1-7.2w]=0;I.1h(2k[0],2k[1],E);5(e.57!="7p"){2C();4.3V=J.4V(8(){4.4b=J.2R(8(){I.1h(2k[0],2k[1],E)},5M)},6d)};C A};d.3S=8(r){5(r){d.38(g);4.1v[i]=(P(d,"2V")=="2m"||4.2o[i])?E:A;5(!4.1v[i])7.38(g);1i 5(!4.2o[i])7.2e();4.O[i]=E;2b(d,"","5F")}1i{d.2e();7.2e();4.29[i]=(P(d,"2V")!="2m")?E:A;4.O[i]=A;7.Q=0;m.1y[i](\'1g\');4.1V[i]=[A,A];2b(d,"5F","")};H.1y[1-i]((4.3K[i]&&(r||4.29[i])&&!4.1v[i])?4.1T[1-i]-4.19[i*2]+"K":"-"+4.19[i*2]+"K")};d.7e=1P};8 3y(a,b,c){B d=a.1k,7=a.7,2v=a.2v,20=a.20,1W=a.1W,1E=a.1E,i=7.2w;d.2N[i](G.W[i]()-b+\'K\');d.1y[1-i](G.W[1-i]()-d.W[1-i]()+\'K\');4.3K[i]=(1j(d.5j[1-i]())===0)?E:A;a.4k=a.2S[0]+a.2S[1];a.44=1j((d.W[i]()-a.4k)*0.75);7.5p=R.28(R.1X(R.28(1j(4.U[i]/4.1I[i]*d.W[i]()),a.44),45),a.44);7.2N[i](7.5p+\'K\');7.26=d.W[i]()-7.W[i]()-a.4k;7.Q=R.28(R.1X(0,7.Q),7.26);7.1y[i](7.Q+7.2E+\'K\');7.1J=H.W[i]()-4.1I[i];7.3d=7.26;2v.2N[i](d.W[i]()-1W.W[i]()+\'K\');20.2N[i](7.W[i]()-1E.W[i]()+\'K\');1E.1y[i](7.W[i]()-1E.W[i]()+\'K\');1W.1y[i](d.W[i]()-1W.W[i]()+\'K\');5(!c)7.3m();4.4K()};I.24=8(){H.2c=0;H.2i=0;g.2c=0;g.2i=0};M.V(J,\'5m\',8(){5(g.14)I.1G()});M.V(J,\'7E\',8(){5(g.4t)J.51(g.4t);g.4t=J.4V(8(){5(g.14)I.1G()},5M)});8 1P(){C A};8 3r(e){5(!e){B e=J.1m};B a=4.1L,L,4y,7w,65;5(a==Z)C;5(!F.4O&&!e.76)2Q();4y=(a.3j)?2:1;1n(B i=0;i<4y;i++){L=(i==1)?a.4H.4R:a;5(a.3t){5(!L.2z){I.24();1N(L);1N(L.2Z);L.5x=e.2l-L.1D;L.5S=e.2f-L.1F;L.5O=L.Q;L.2z=E};L.Q=(L.39)?e.2l-L.5x-L.2Z.1D-L.2E:e.2f-L.5S-L.2Z.1F-L.2E;5(a.3j)L.Q=L.Q+(L.Q-L.5O);L.3E(E);5(g.3z)g.3z()}1i L.2z=A}};8 2Q(){5(4.1L!=Z){4.1L.3t=A;4.1L.1c+=4.1L.1w}4.1L=Z;F.1H(h,\'3Y\',1P);F.1H(h,\'2M\',3r);F.1H(h,\'3v\',2Q)};8 3C(e){5(!e)e=J.1m;5(M==G)G.D.21=4.31;5(e.2d.2y!=1||(!4.O[0]&&!4.O[1]))C A;B a=\'\',6A=(e.18&&(e.18.1f||(e.18.7t==3&&e.18.1d.1f)))?E:A;4.2x=[e.2d[0].2f,e.2d[0].2l];3x();F.1z(g,\'5h\',3L);F.1z(g,\'5K\',4c);4.5Q=(e.18&&e.18.1M&&e.18.1M.X(/1S[6k]7y[7C]e?/))?E:A;C A};8 3L(e){5(!e)e=J.1m;5(e.2d.2y!=1)C A;F.1H(g,\'4U\',3P);B a=[e.2d[0].2f,e.2d[0].2l];4.2D=E;4.12=[4.2x[0]-a[0],4.2x[1]-a[1]];5(4.5Q){4.12[0]*=-(4.1I[0]/4.U[0]);4.12[1]*=-(4.1I[1]/4.U[1])};I.4m(4.12[0],4.12[1]);4.2x[0]=a[0];4.2x[1]=a[1];1n(B i=0;i<2;i++){5(4.12[i]!==0&&4.O[i]&&(4.12[1-i]==0||!4.O[1-i])){5((4.12[i]>0&&4.1V[i][1]==4.1V[i][0])||(4.12[i]<0&&4.1V[i][0]==0))4.2D=A};5(!4.O[i]&&4.12[1-i]!==0&&R.3W(4.12[i]/4.12[1-i])>1.1)4.2D=A};5(4.2D){e.1u();G.D.21=\'7l\'}1i{G.D.21=4.31}};8 4c(e){5(!e)e=J.1m;e.1u();5(e.2d.2y>0)C A;F.1H(g,\'5h\',3L);F.1H(g,\'5K\',4c);5((4.O[0]&&R.3W(4.12[0])>6)||(4.O[1]&&R.3W(4.12[1])>6)){B a=0;3x(E);4.2G=16.2R(8(){I.4m(4p(4.12[0],0,10,a,0.3),4p(4.12[1],0,10,a,0.3));a++;5(a>10)3x()},46)}};8 2A(e){5(!e)e=J.1m;5(!M.14)C;B a=M,36,4A,1U=A,1A=0,22;I.24();4w=(e.18)?e.18:(e.1C)?e.1C:M;5(4w.1M&&4w.1M.X(/6P/))1U=E;5(e.4L)1A=-e.4L;5(e.5R)1A=e.5R;1A=(1A<0)?-1:+1;22=(1A<0)?0:1;4.1Q[1-22]=A;5((4.1Q[22]&&!1U)||(!4.O[0]&&!4.O[1]))C;5(4.O[1]&&!1U)1Y=I.1h(A,4.3R[22],E);36=!4.O[1]||1U||(4.O[1]&&((1Y[1][0]==1Y[1][1]&&1A>0)||(1Y[1][0]==0&&1A<0)));5(4.O[0]&&(!4.O[1]||1U))1Y=I.1h(4.3R[22],A,E);4A=!4.O[0]||(4.O[0]&&4.O[1]&&36&&!1U)||(4.O[0]&&((1Y[0][0]==1Y[0][1]&&1A>0)||(1Y[0][0]==0&&1A<0)));5(36&&4A&&!1U)4.1Q[22]=E;1i 4.1Q[22]=A;5(e.1u)e.1u();C A};8 4X(a){1B(a.1d){a=a.1d;5(a==g)C E}C A};8 1N(a){B b=a,2a=2h=0;5(b.32){1B(b){2a+=b.5I;2h+=b.5d;b=b.32}}1i 5(b.x){2a+=b.x;2h+=b.y};a.1F=2a;a.1D=2h};8 4Q(a){B b=a;2a=2h=0;1B(!b.15&&b.1d&&b!=m&&P(b,\'2K\')=="61"){b=b.1d};5(b.32){1B(b!=m){2a+=b.5I;2h+=b.5d;b=b.32}};C[2a,2h]};8 2b(a,b,c){F.2b(a,b,c)};8 4p(a,b,c,d,e){c=R.1X(c,1);B f=b-a,3o=a+(R.71(((1/c)*d),e)*f);C(3o>0)?R.6w(3o):R.7r(3o)}},5E:8(){5(F.3w)16.2n(F.3w);F.3H();F.3U();5(16.4Z)16.4Z()},2b:8(a,b,c){5(!a.1r)a.1r=\'\';B d=a.1r;5(b&&!d.X(3c("(^|\\\\s)"+b+"($|\\\\s)")))d=d.4n(/(\\S$)/,\'$1 \')+b;5(c)d=d.4n(3c("((^|\\\\s)+"+c+")+($|\\\\s)","g"),\'$2\').4n(/\\s$/,\'\');a.1r=d},3H:8(){B d=/#([^#.]*)$/,2H=/(.*)#.*$/,5N=/(^|\\s)1R-7h-6L-7B($|\\s)/,7x,49,i,1o,5t=Y.30("a"),2p=Y.2Y.1f;5(2p.X(2H))2p=2p.X(2H)[1];1n(i=0;1o=5t[i];i++){49=(1o.1r)?1o.1r:\'\';5(1o.1f&&!1o.3g&&1o.1f.X(d)&&((1o.1f.X(2H)&&2p===1o.1f.X(2H)[1])||49.X(5N))){1o.3g=E;F.V(1o,\'69\',8(e){5(!e)e=16.1m;B a=(e.1C)?e.1C:M;1B(!a.3g&&a.1d){a=a.1d};5(!a.3g)C;B b=Y.1t(a.1f.X(d)[1]),3a=A;5(b==Z)b=(b=Y.7D(a.1f.X(d)[1])[0])?b:Z;5(b!=Z){B c=b;1B(c.1d){c=c.1d;5(c.14){c.14.3A(b);3a=c}};5(3a){5(e.1u)e.1u();Y.2Y.1f=2p+"#"+a.1f.X(d)[1];3a.14.24();C A}}})}}},3U:8(a){F.7a=E;B b=F.3X(Y.30("7J")[0],"4q",(a)?a:\'1R\');1n(B i=0,3s;3s=b[i];i++)5(!3s.14)F.3O(3s)},7H:8(a,b){5(33(a)==\'3I\')a=Y.1t(a);5(a==Z)C A;B c=a;1B(c.1d){c=c.1d;5(c.14){5(b){Y.2Y.1f="#"+b};c.14.3A(a);c.14.24();C E}};C A},1G:8(a,b){1n(B i=0,3k;3k=F.2W[i];i++){3k.14.1G();5(b)3k.14.4o()};5(a)F.3H()},4e:8(a){B a=a.6E(\'-\'),3N=a[0],i;1n(i=1;4g=a[i];i++){3N+=4g.7v(0).7G()+4g.7I(1)};C 3N},3X:8(a,b,c){5(33(a)==\'3I\')a=Y.1t(a);5(a==Z)C A;B d=2q 3c("(^|\\\\s)"+c+"($|\\\\s)"),7K,3Z=[],4v=0;B e=a.30(b);1n(B i=0,2P;2P=e[i];i++){5(2P.1r&&2P.1r.X(d)){3Z[4v]=2P;4v++}}C 3Z},5u:8(a){5(a==Z)C E;B b;1B(a.1d){b=F.P(a,\'2K\');5(b==\'3T\')C E;a=a.1d};C A},P:8(a,b){5(16.4S)C 16.4S(a,Z).6o(b);5(a.5l)C a.5l[F.4e(b)];C A},3w:16.2R(8(){B a=Y.1t(\'1R-7n\');5(a!=Z){F.3U();16.2n(F.3w)}},46),4f:8(a,b){a.1d.7F(a);a.11.2K="3T";b.1a(a)},V:8(a,b,c){5(!F.1z(a,b,c)&&a.56){a.56(\'5L\'+b,c)}},1z:8(a,b,c){5(a.4r){a.4r(b,c,A);F.4O=E;16.4r("6T",8(){F.1H(a,b,c)},A);C E}1i C A},1H:8(a,b,c){5(!F.5B(a,b,c)&&a.5P)a.5P(\'5L\'+b,c)},5B:8(a,b,c){5(a.5T){a.5T(b,c,A);C E}1i C A}};8 7z(a){F.3O(a)};F.4F();',62,481,'||||sC|if||sBr|function||||||||||||||||||||||||||||false|var|return|sY|true|fleXenv|tDv|mDv|sfU|wD|px|movBr|this|padding|scroller|getStyle|curPos|Math||createDiv|cntRSize|addTrggr|getSize|match|document|null||style|moveDelta|width|fleXcroll|offsetHeight|window|reqS|target|paddings|appendChild|left|trgtScrll|parentNode|top|href|0px|setScrollPos|else|parseInt|sDv|pDv|event|for|anchoR||offsetWidth|className||getElementById|preventDefault|forcedHide|targetSkew|fDv|setPos|addChckTrggr|delta|while|srcElement|yPos|sSBr|xPos|updateScrollBars|remTrggr|cntSize|mxScroll|border|goScroll|id|findPos|position|retFalse|edge|flexcroll|_|barSpace|hoverH|scrollPosition|sSDv|max|scrollState|height|sFBr|zIndex|iNDx|nV|mDPosFix|right|maxPos|keyAct|min|forcedBar|curleft|classChange|scrollTop|targetTouches|fHide|clientX|bottom|curtop|scrollLeft|tSelectFunc|mV|clientY|hidden|clearInterval|externaL|urlBase|new|formItem||documentElement|copyStyles|sFDv|indx|touchPos|length|moved|mWheelProc|indexOf|intClear|touchPrevent|minPos|hBr|touchFlick|urlExt|jBox|mTBox|display|barZ|mousemove|setSize|createScrollBars|pusher|mMouseUp|setInterval|barPadding|cPSize|vrt|visibility|fleXlist|focusProtect|location|ofstParent|getElementsByTagName|tDivZ|offsetParent|typeof|pageScrolled|relative|vEdge|stdMode|fShow|vertical|eScroll|userAgent|RegExp|sRange|vUpReq|getContentHeight|fleXanchor|absolute|sYdir|scrollBoth|fleXdiv|getContentWidth|realScrollPos|hrz|stepp|maxCWidth|sXdir|mMoveBar|tgDiv|clicked|hUpReq|mouseup|catchFastInit|flickClear|updateScroll|onfleXcroll|scrollToElement|overflow|handleTouch|brdWidthLoss|doBarPos|inputName|padHeightComp|prepAnchors|string|tSelectMouse|forcedPos|handleTouchMove|prepareScroll|reT|fleXcrollMain|handleTextSelect|uReg|wheelAct|setVisibility|none|initByClass|barClickRetard|abs|getByClassName|selectstart|retArray||baseAct|mdY|brdHeightLoss|baseProp||100|vBr|mHeight|claSS|onmousedown|barClickScroll|handleTouchEnd|setWidth|camelConv|putAway|parT|zTHeight|mY|Array|padLoss|setHeight|scrollContent|replace|formUpdate|easeInOut|div|addEventListener|xAw|refreshTimeout|calcCScrollVal|key|hElem|mdX|maxx|yScrld|hEdge|xScrld|yAw|mOnXEdge|mOnYEdge|fleXcrollInit|touchstart|scrlTrgt|fleXdata|fleXprocess|fixIEDispBug|wheelDelta|createElement|firstChild|w3events|1px|findRCpos|vBar|getComputedStyle|default|mousedown|setTimeout|100p|isddvChild|mousewheel|onfleXcrollRun|onfleXcrollFail|clearTimeout|100px|1em|basebeg|opera|attachEvent|type|keyCode|oScrollX|index|reqV|DOMMouseScroll|offsetTop|HTMLElement|pageYOffset|fixed|touchmove|solid|getPos|intlWidth|currentStyle|load|inMposX|paddingTop|aSize|Object|barbeg|textAlign|anchorList|checkHidden|pageXOffset|postHeight|pointerOffsetY|brdWidth|baseend|paddingLeft|remChckTrggr|text|pkeY|globalInit|flexinactive|hide|barend|offsetLeft|inMposY|touchend|on|80|regExer|inCurPos|detachEvent|touchBar|detail|pointerOffsetX|removeEventListener|write|onmousewheel|textarea|auto|_37|focus|childNodes|inline|OmniWeb|keypress|align|yScroll|scrollwrapper|flexcrollactive|hscroller|click|important|setAttribute|containerSize|425|_34|contentScroll|block|KDE|bscrollgeneric|borderBottom|vh|fontSize|visible|scrollerjogbox|getPropertyValue|css|keydown|input|onmouseover|_39|scrollUpdate|compPad|floor|undefined|keyup|black|touchLink|zoomdetectdiv|parseFloat|hBar|split|navigator|tabIndex|contentSize|contentwrapper|blur|vscroller|page|flex__|scrollbar|2px|_hscroller|999|onblur|select|unload|AppleWebKit|2500|borderBottomWidth|normal|_38|base|MSIE|pow|copyholder|scrollgeneric|prototype||button|_35|bar|platform|initialized|12px|sizeChangeDetect|Safari|onmouseclick|_33|failed|in|blue|toString|vendor|9999|mcontentwrapper|init|Mac|dblclick|domfixdiv|ceil|_40|nodeType|_36|charAt|xScroll|matcH|scrollerba|CSBfleXcroll|ondblclick|link|rs|getElementsByName|resize|removeChild|toUpperCase|scrollTo|substr|body|clsnm'.split('|'),0,{}))
$(document).ready(function(){
	

	/* 8.)
	 * jQuery Color Animations v@VERSION
	 * http://jquery.org/
	 *
	 * Copyright 2011 John Resig
	 * Dual licensed under the MIT or GPL Version 2 licenses.
	 * http://jquery.org/license
	 *
	 * Date: @DATE
	 */

	(function( jQuery, undefined ){
		var stepHooks = "backgroundColor borderBottomColor borderLeftColor borderRightColor borderTopColor color outlineColor".split(" "),

			// plusequals test for += 100 -= 100
			rplusequals = /^([\-+])=\s*(\d+\.?\d*)/,
			// a set of RE's that can match strings and generate color tuples.
			stringParsers = [{
					re: /rgba?\(\s*(\d{1,3})\s*,\s*(\d{1,3})\s*,\s*(\d{1,3})\s*(?:,\s*(\d+(?:\.\d+)?)\s*)?\)/,
					parse: function( execResult ) {
						return [
							execResult[ 1 ],
							execResult[ 2 ],
							execResult[ 3 ],
							execResult[ 4 ]
						];
					}
				}, {
					re: /rgba?\(\s*(\d+(?:\.\d+)?)\%\s*,\s*(\d+(?:\.\d+)?)\%\s*,\s*(\d+(?:\.\d+)?)\%\s*(?:,\s*(\d+(?:\.\d+)?)\s*)?\)/,
					parse: function( execResult ) {
						return [
							2.55 * execResult[1],
							2.55 * execResult[2],
							2.55 * execResult[3],
							execResult[ 4 ]
						];
					}
				}, {
					re: /#([a-fA-F0-9]{2})([a-fA-F0-9]{2})([a-fA-F0-9]{2})/,
					parse: function( execResult ) {
						return [
							parseInt( execResult[ 1 ], 16 ),
							parseInt( execResult[ 2 ], 16 ),
							parseInt( execResult[ 3 ], 16 )
						];
					}
				}, {
					re: /#([a-fA-F0-9])([a-fA-F0-9])([a-fA-F0-9])/,
					parse: function( execResult ) {
						return [
							parseInt( execResult[ 1 ] + execResult[ 1 ], 16 ),
							parseInt( execResult[ 2 ] + execResult[ 2 ], 16 ),
							parseInt( execResult[ 3 ] + execResult[ 3 ], 16 )
						];
					}
				}, {
					re: /hsla?\(\s*(\d+(?:\.\d+)?)\s*,\s*(\d+(?:\.\d+)?)\%\s*,\s*(\d+(?:\.\d+)?)\%\s*(?:,\s*(\d+(?:\.\d+)?)\s*)?\)/,
					space: "hsla",
					parse: function( execResult ) {
						return [
							execResult[1],
							execResult[2] / 100,
							execResult[3] / 100,
							execResult[4]
						];
					}
				}],

			// jQuery.Color( )
			color = jQuery.Color = function( color, green, blue, alpha ) {
				return new jQuery.Color.fn.parse( color, green, blue, alpha );
			},
			spaces = {
				rgba: {
					cache: "_rgba",
					props: {
						red: {
							idx: 0,
							type: "byte",
							empty: true
						},
						green: {
							idx: 1,
							type: "byte",
							empty: true
						},
						blue: {
							idx: 2,
							type: "byte",
							empty: true
						},
						alpha: {
							idx: 3,
							type: "percent",
							def: 1
						}
					}
				},
				hsla: {
					cache: "_hsla",
					props: {
						hue: {
							idx: 0,
							type: "degrees",
							empty: true
						},
						saturation: {
							idx: 1,
							type: "percent",
							empty: true
						},
						lightness: {
							idx: 2,
							type: "percent",
							empty: true
						}
					}
				}
			},
			propTypes = {
				"byte": {
					floor: true,
					min: 0,
					max: 255
				},
				"percent": {
					min: 0,
					max: 1
				},
				"degrees": {
					mod: 360,
					floor: true
				}
			},
			rgbaspace = spaces.rgba.props,
			support = color.support = {},

			// colors = jQuery.Color.names
			colors,

			// local aliases of functions called often
			each = jQuery.each;

		spaces.hsla.props.alpha = rgbaspace.alpha;

		function clamp( value, prop, alwaysAllowEmpty ) {
			var type = propTypes[ prop.type ] || {},
				allowEmpty = prop.empty || alwaysAllowEmpty;

			if ( allowEmpty && value == null ) {
				return null;
			}
			if ( prop.def && value == null ) {
				return prop.def;
			}
			if ( type.floor ) {
				value = ~~value;
			} else {
				value = parseFloat( value );
			}
			if ( value == null || isNaN( value ) ) {
				return prop.def;
			}
			if ( type.mod ) {
				value = value % type.mod;
				// -10 -> 350
				return value < 0 ? type.mod + value : value;
			}

			// for now all property types without mod have min and max
			return type.min > value ? type.min : type.max < value ? type.max : value;
		}

		function stringParse( string ) {
			var inst = color(),
				rgba = inst._rgba = [];

			string = string.toLowerCase();

			each( stringParsers, function( i, parser ) {
				var match = parser.re.exec( string ),
					values = match && parser.parse( match ),
					parsed,
					spaceName = parser.space || "rgba",
					cache = spaces[ spaceName ].cache;


				if ( values ) {
					parsed = inst[ spaceName ]( values );

					// if this was an rgba parse the assignment might happen twice
					// oh well....
					inst[ cache ] = parsed[ cache ];
					rgba = inst._rgba = parsed._rgba;

					// exit each( stringParsers ) here because we matched
					return false;
				}
			});

			// Found a stringParser that handled it
			if ( rgba.length !== 0 ) {

				// if this came from a parsed string, force "transparent" when alpha is 0
				// chrome, (and maybe others) return "transparent" as rgba(0,0,0,0)
				if ( Math.max.apply( Math, rgba ) === 0 ) {
					jQuery.extend( rgba, colors.transparent );
				}
				return inst;
			}

			// named colors / default - filter back through parse function
			if ( string = colors[ string ] ) {
				return string;
			}
		}

		color.fn = color.prototype = {
			constructor: color,
			parse: function( red, green, blue, alpha ) {
				if ( red === undefined ) {
					this._rgba = [ null, null, null, null ];
					return this;
				}
				if ( red instanceof jQuery || red.nodeType ) {
					red = red instanceof jQuery ? red.css( green ) : jQuery( red ).css( green );
					green = undefined;
				}

				var inst = this,
					type = jQuery.type( red ),
					rgba = this._rgba = [],
					source;

				// more than 1 argument specified - assume ( red, green, blue, alpha )
				if ( green !== undefined ) {
					red = [ red, green, blue, alpha ];
					type = "array";
				}

				if ( type === "string" ) {
					return this.parse( stringParse( red ) || colors._default );
				}

				if ( type === "array" ) {
					each( rgbaspace, function( key, prop ) {
						rgba[ prop.idx ] = clamp( red[ prop.idx ], prop );
					});
					return this;
				}

				if ( type === "object" ) {
					if ( red instanceof color ) {
						each( spaces, function( spaceName, space ) {
							if ( red[ space.cache ] ) {
								inst[ space.cache ] = red[ space.cache ].slice();
							}
						});
					} else {
						each( spaces, function( spaceName, space ) {
							each( space.props, function( key, prop ) {
								var cache = space.cache;

								// if the cache doesn't exist, and we know how to convert
								if ( !inst[ cache ] && space.to ) {

									// if the value was null, we don't need to copy it
									// if the key was alpha, we don't need to copy it either
									if ( red[ key ] == null || key === "alpha") {
										return;
									}
									inst[ cache ] = space.to( inst._rgba );
								}

								// this is the only case where we allow nulls for ALL properties.
								// call clamp with alwaysAllowEmpty
								inst[ cache ][ prop.idx ] = clamp( red[ key ], prop, true );
							});
						});
					}
					return this;
				}
			},
			is: function( compare ) {
				var is = color( compare ),
					same = true,
					myself = this;

				each( spaces, function( _, space ) {
					var isCache = is[ space.cache ],
						localCache;
					if (isCache) {
						localCache = myself[ space.cache ] || space.to && space.to( myself._rgba ) || [];
						each( space.props, function( _, prop ) {
							if ( isCache[ prop.idx ] != null ) {
								same = ( isCache[ prop.idx ] == localCache[ prop.idx ] );
								return same;
							}
						});
					}
					return same;
				});
				return same;
			},
			_space: function() {
				var used = [],
					inst = this;
				each( spaces, function( spaceName, space ) {
					if ( inst[ space.cache ] ) {
						used.push( spaceName );
					}
				});
				return used.pop();
			},
			transition: function( other, distance ) {
				var end = color( other ),
					spaceName = end._space(),
					space = spaces[ spaceName ],
					start = this[ space.cache ] || space.to( this._rgba ),
					result = start.slice();

				end = end[ space.cache ];
				each( space.props, function( key, prop ) {
					var index = prop.idx,
						startValue = start[ index ],
						endValue = end[ index ],
						type = propTypes[ prop.type ] || {};

					// if null, don't override start value
					if ( endValue === null ) {
						return;
					}
					// if null - use end
					if ( startValue === null ) {
						result[ index ] = endValue;
					} else {
						if ( type.mod ) {
							if ( endValue - startValue > type.mod / 2 ) {
								startValue += type.mod;
							} else if ( startValue - endValue > type.mod / 2 ) {
								startValue -= type.mod;
							}
						}
						result[ prop.idx ] = clamp( ( endValue - startValue ) * distance + startValue, prop );
					}
				});
				return this[ spaceName ]( result );
			},
			blend: function( opaque ) {
				// if we are already opaque - return ourself
				if ( this._rgba[ 3 ] === 1 ) {
					return this;
				}

				var rgb = this._rgba.slice(),
					a = rgb.pop(),
					blend = color( opaque )._rgba;

				return color( jQuery.map( rgb, function( v, i ) {
					return ( 1 - a ) * blend[ i ] + a * v;
				}));
			},
			toRgbaString: function() {
				var prefix = "rgba(",
					rgba = jQuery.map( this._rgba, function( v, i ) {
						return v == null ? ( i > 2 ? 1 : 0 ) : v;
					});

				if ( rgba[ 3 ] === 1 ) {
					rgba.pop();
					prefix = "rgb(";
				}

				return prefix + rgba.join(",") + ")";
			},
			toHslaString: function() {
				var prefix = "hsla(",
					hsla = jQuery.map( this.hsla(), function( v, i ) {
						if ( v == null ) {
							v = i > 2 ? 1 : 0;
						}

						// catch 1 and 2
						if ( i && i < 3 ) {
							v = Math.round( v * 100 ) + "%";
						}
						return v;
					});

				if ( hsla[ 3 ] == 1 ) {
					hsla.pop();
					prefix = "hsl(";
				}
				return prefix + hsla.join(",") + ")";
			},
			toHexString: function( includeAlpha ) {
				var rgba = this._rgba.slice(),
					alpha = rgba.pop();

				if ( includeAlpha ) {
					rgba.push( ~~( alpha * 255 ) );
				}

				return "#" + jQuery.map( rgba, function( v, i ) {

					// default to 0 when nulls exist
					v = ( v || 0 ).toString( 16 );
					return v.length == 1 ? "0" + v : v;
				}).join("");
			},
			toString: function() {
				return this._rgba[ 3 ] === 0 ? "transparent" : this.toRgbaString();
			}
		};
		color.fn.parse.prototype = color.fn;

		// hsla conversions adapted from:
		// http://www.google.com/codesearch/p#OAMlx_jo-ck/src/third_party/WebKit/Source/WebCore/inspector/front-end/Color.js&d=7&l=193

		function hue2rgb( p, q, h ) {
			h = ( h + 1 ) % 1;
			if ( h * 6 < 1 ) {
				return p + (q - p) * 6 * h;
			}
			if ( h * 2 < 1) {
				return q;
			}
			if ( h * 3 < 2 ) {
				return p + (q - p) * ((2/3) - h) * 6;
			}
			return p;
		}

		spaces.hsla.to = function ( rgba ) {
			if ( rgba[ 0 ] == null || rgba[ 1 ] == null || rgba[ 2 ] == null ) {
				return [ null, null, null, rgba[ 3 ] ];
			}
			var r = rgba[ 0 ] / 255,
				g = rgba[ 1 ] / 255,
				b = rgba[ 2 ] / 255,
				a = rgba[ 3 ],
				max = Math.max( r, g, b ),
				min = Math.min( r, g, b ),
				diff = max - min,
				add = max + min,
				l = add * 0.5,
				h, s;

			if ( min === max ) {
				h = 0;
			} else if ( r === max ) {
				h = ( 60 * ( g - b ) / diff ) + 360;
			} else if ( g === max ) {
				h = ( 60 * ( b - r ) / diff ) + 120;
			} else {
				h = ( 60 * ( r - g ) / diff ) + 240;
			}

			if ( l === 0 || l === 1 ) {
				s = l;
			} else if ( l <= 0.5 ) {
				s = diff / add;
			} else {
				s = diff / ( 2 - add );
			}
			return [ Math.round(h) % 360, s, l, a == null ? 1 : a ];
		};

		spaces.hsla.from = function ( hsla ) {
			if ( hsla[ 0 ] == null || hsla[ 1 ] == null || hsla[ 2 ] == null ) {
				return [ null, null, null, hsla[ 3 ] ];
			}
			var h = hsla[ 0 ] / 360,
				s = hsla[ 1 ],
				l = hsla[ 2 ],
				a = hsla[ 3 ],
				q = l <= 0.5 ? l * ( 1 + s ) : l + s - l * s,
				p = 2 * l - q,
				r, g, b;

			return [
				Math.round( hue2rgb( p, q, h + ( 1 / 3 ) ) * 255 ),
				Math.round( hue2rgb( p, q, h ) * 255 ),
				Math.round( hue2rgb( p, q, h - ( 1 / 3 ) ) * 255 ),
				a
			];
		};


		each( spaces, function( spaceName, space ) {
			var props = space.props,
				cache = space.cache,
				to = space.to,
				from = space.from;

			// makes rgba() and hsla()
			color.fn[ spaceName ] = function( value ) {

				// generate a cache for this space if it doesn't exist
				if ( to && !this[ cache ] ) {
					this[ cache ] = to( this._rgba );
				}
				if ( value === undefined ) {
					return this[ cache ].slice();
				}

				var type = jQuery.type( value ),
					arr = ( type === "array" || type === "object" ) ? value : arguments,
					local = this[ cache ].slice(),
					ret;

				each( props, function( key, prop ) {
					var val = arr[ type === "object" ? key : prop.idx ];
					if ( val == null ) {
						val = local[ prop.idx ];
					}
					local[ prop.idx ] = clamp( val, prop );
				});

				if ( from ) {
					ret = color( from( local ) );
					ret[ cache ] = local;
					return ret;
				} else {
					return color( local );
				}
			};

			// makes red() green() blue() alpha() hue() saturation() lightness()
			each( props, function( key, prop ) {
				// alpha is included in more than one space
				if ( color.fn[ key ] ) {
					return;
				}
				color.fn[ key ] = function( value ) {
					var vtype = jQuery.type( value ),
						fn = ( key === 'alpha' ? ( this._hsla ? 'hsla' : 'rgba' ) : spaceName ),
						local = this[ fn ](),
						cur = local[ prop.idx ],
						match;

					if ( vtype === "undefined" ) {
						return cur;
					}

					if ( vtype === "function" ) {
						value = value.call( this, cur );
						vtype = jQuery.type( value );
					}
					if ( value == null && prop.empty ) {
						return this;
					}
					if ( vtype === "string" ) {
						match = rplusequals.exec( value );
						if ( match ) {
							value = cur + parseFloat( match[ 2 ] ) * ( match[ 1 ] === "+" ? 1 : -1 );
						}
					}
					local[ prop.idx ] = value;
					return this[ fn ]( local );
				};
			});
		});

		// add .fx.step functions
		each( stepHooks, function( i, hook ) {
			jQuery.cssHooks[ hook ] = {
				set: function( elem, value ) {
					var parsed;

					if ( jQuery.type( value ) !== 'string' || ( parsed = stringParse( value ) ) )
					{
						value = color( parsed || value );
						if ( !support.rgba && value._rgba[ 3 ] !== 1 ) {
							var backgroundColor,
								curElem = hook === "backgroundColor" ? elem.parentNode : elem;
							do {
								backgroundColor = jQuery.curCSS( curElem, "backgroundColor" );
							} while (
								( backgroundColor === "" || backgroundColor === "transparent" ) &&
								( curElem = curElem.parentNode ) &&
								curElem.style
							);

							value = value.blend( backgroundColor && backgroundColor !== "transparent" ?
								backgroundColor :
								"_default" );
						}

						value = value.toRgbaString();
					}
					elem.style[ hook ] = value;
				}
			};
			jQuery.fx.step[ hook ] = function( fx ) {
				if ( !fx.colorInit ) {
					fx.start = color( fx.elem, hook );
					fx.end = color( fx.end );
					fx.colorInit = true;
				}
				jQuery.cssHooks[ hook ].set( fx.elem, fx.start.transition( fx.end, fx.pos ) );
			};
		});

		// detect rgba support
		jQuery(function() {
			var div = document.createElement( "div" ),
				div_style = div.style;

			div_style.cssText = "background-color:rgba(1,1,1,.5)";
			support.rgba = div_style.backgroundColor.indexOf( "rgba" ) > -1;
		});

		// Some named colors to work with
		// From Interface by Stefan Petre
		// http://interface.eyecon.ro/
		colors = jQuery.Color.names = {
			aqua: "#00ffff",
			azure: "#f0ffff",
			beige: "#f5f5dc",
			black: "#000000",
			blue: "#0000ff",
			brown: "#a52a2a",
			cyan: "#00ffff",
			darkblue: "#00008b",
			darkcyan: "#008b8b",
			darkgrey: "#a9a9a9",
			darkgreen: "#006400",
			darkkhaki: "#bdb76b",
			darkmagenta: "#8b008b",
			darkolivegreen: "#556b2f",
			darkorange: "#ff8c00",
			darkorchid: "#9932cc",
			darkred: "#8b0000",
			darksalmon: "#e9967a",
			darkviolet: "#9400d3",
			fuchsia: "#ff00ff",
			gold: "#ffd700",
			green: "#008000",
			indigo: "#4b0082",
			khaki: "#f0e68c",
			lightblue: "#add8e6",
			lightcyan: "#e0ffff",
			lightgreen: "#90ee90",
			lightgrey: "#d3d3d3",
			lightpink: "#ffb6c1",
			lightyellow: "#ffffe0",
			lime: "#00ff00",
			magenta: "#ff00ff",
			maroon: "#800000",
			navy: "#000080",
			olive: "#808000",
			orange: "#ffa500",
			pink: "#ffc0cb",
			purple: "#800080",
			violet: "#800080",
			red: "#ff0000",
			silver: "#c0c0c0",
			white: "#ffffff",
			yellow: "#ffff00",
			transparent: [ null, null, null, 0 ],
			_default: "#ffffff"
		};
	})( jQuery );

	/*jshint eqnull:true */
/*!
 * jQuery Cookie Plugin v1.2
 * https://github.com/carhartl/jquery-cookie
 *
 * Copyright 2011, Klaus Hartl
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.opensource.org/licenses/GPL-2.0
 */
(function ($, document, undefined) {

    var pluses = /\+/g;

    function raw(s) {
        return s;
    }

    function decoded(s) {
        return decodeURIComponent(s.replace(pluses, ' '));
    }

    $.cookie = function (key, value, options) {

        // key and at least value given, set cookie...
        if (value !== undefined && !/Object/.test(Object.prototype.toString.call(value))) {
            options = $.extend({}, $.cookie.defaults, options);

            if (value === null) {
                options.expires = -1;
            }

            if (typeof options.expires === 'number') {
                var days = options.expires, t = options.expires = new Date();
                t.setDate(t.getDate() + days);
            }

            value = String(value);

            return (document.cookie = [
                encodeURIComponent(key), '=', options.raw ? value : encodeURIComponent(value),
                options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
                options.path    ? '; path=' + options.path : '',
                options.domain  ? '; domain=' + options.domain : '',
                options.secure  ? '; secure' : ''
            ].join(''));
        }

        // key and possibly options given, get cookie...
        options = value || $.cookie.defaults || {};
        var decode = options.raw ? raw : decoded;
        var cookies = document.cookie.split('; ');
        for (var i = 0, parts; (parts = cookies[i] && cookies[i].split('=')); i++) {
            if (decode(parts.shift()) === key) {
                return decode(parts.join('='));
            }
        }

        return null;
    };

    $.cookie.defaults = {};

    $.removeCookie = function (key, options) {
        if ($.cookie(key, options) !== null) {
            $.cookie(key, null, options);
            return true;
        }
        return false;
    };

})(jQuery, document);







    
	// @Andy Huang 
	// andyhuang@geekpark.net
	
	/*
	Regular js INTI
	*/
	
	// Remove count text when value is 0
	if($("#like-count-num").text() == 0)
		$("#like-count-num").parent().hide();
		
	// Get the video ID
	var view_id = $("#like-button").text();
	var share_to_sina = 0;
	var share_to_tencent = 0;
	
	/*
	Event functions
	*/
	
	// Like Button click function
	$("#like-button").click(function() {
		
		// init
		$(".status .text-cannel").show();
		$(".status .text-submit").hide();
		$(".share-tip-content").css('width','');
		$(".share-tip-content *").not(".text").show();
		$(".share-tip-content .sync-stats").hide();
		$("#like-count-num").parent().fadeIn(450);
		$("#share-tips").removeClass("share-tips-success share-tips-error");
		$(".share-tip-content .sync-stats").hide();
		$(".share-ok").text('确定');
		
		// Like & Share button click functions
		var like_button_stats = $("#like-button").attr("class").toString();
		
		
		if (like_button_stats == "share-to-button"){
			$('#like-button-close').hide(0);

			$('#share-tips').slideToggle('slow');
			$('#like-button-close').hide(0);
		if($("#share-tips").is(":visible"))

		$('#like-button-close').delay(1000).show(0);
			
		}else if( like_button_stats != "like-button checked" ){
			if( like_button_stats == "like-button online" ){
				$('#share-tips').slideToggle('slow', function(){
                    $('#like-button-close').delay(1000).show(0);
					//$('#like-button-close').fadeIn(2000);
				});
				$("#like-button").attr("class","share-to-button");
				$("#like-count-num").text(Number($("#like-count-num").text())+1);

			}
			else if(like_button_stats == "like-button offline" ){
				$("#like-button").attr("class","like-button checked");
				$("#like-count-num").html(Number($("#like-count-num").text())+1);
			}
			$.post("/ajax/like_entity", { guid: view_id },function(){
				},"json"
			);
		}
	});

	// Close button click function
	$("#like-button-close").click(function(){
		
		$('#like-button-close').hide();
		$('#share-tips').slideUp('slow');
		
	});


	// Share setting logic of sina
	$("span.sina").click(function(){

 	if( $("#share-to-sina .checked").is(":visible") == true ){
		if( $("#share-to-tencent .checked").is(":visible") == false )
			$(".share-ok").text('关闭');
		$("#share-to-sina").attr("title","分享到新浪微博")
	}
	else{
		$(".share-ok").text('确定');
		$("#share-to-sina").attr("title","取消分享到新浪微博")
	}
	$(".sina .checked").fadeToggle('fast');
	$("#share-to-sina .text").toggle();
	
	});
	// Share setting logic of tencent
	$("span.tencent").click(function(){
 		if($("#share-to-tencent .checked").is(":visible") == true){
			if($("#share-to-sina .checked").is(":visible") == false )
		$(".share-ok").text('关闭');
		$("#share-to-sina").attr("title","分享到腾讯微博")
		}
	else
		$(".share-ok").text('确定');
		$("#share-to-sina").attr("title","取消分享到腾讯微博")
		$(".tencent .checked").fadeToggle('fast');
		$("#share-to-tencent .text").toggle();
	
	});



	// Submit sync button click function

	$("#share-ok").click(function(){
		if($("#share-to-sina .checked").is(":visible"))
		share_to_sina = 1;
		else
		share_to_sina = 0;
		if($("#share-to-tencent .checked").is(":visible"))
		share_to_tencent = 1;
		else
		share_to_tencent = 0;
		
		if(share_to_sina+share_to_tencent == 0){
			$('#like-button-close').hide();
			setTimeout(function(){
				$(".share-tips").slideUp(350);
			}, 350);
			$(".share-tip-content div").show();
			$(".share-tip-content .sync-stats").hide();
	 }
	 else{


        $.ajax({
        type: "POST",
        dataType: "json",
        url: "/ajax/share_entity",
        data: "guid="+view_id+"&sync_items%5Bsina%5D="+share_to_sina+"&sync_items%5Btencent%5D="+share_to_tencent,
        beforeSend: function(data){
             // share-tips message slideUp
             $(".share-tip-content").slideUp(350, function(){
                $(".share-tips").addClass("share-tips-send");
                $(".share-tip-content div").hide();
                $(".share-tip-content .sync-stats").show();
                $(".share-tip-content").css('width','280px');
                $(".share-tip-content").slideDown('fast');
                $(".share-tip-content .sync-stats span").text('正在提交.');
                $(".share-tip-content .sync-stats").show();
                $('#like-button-close').hide();
             });
        },
        success: function(data){
             // +1 post
             if(share_to_sina){
                 //GP_Share.sharePlus( view_id, 'sina' );
                 $("#extra-info-sina").text(parseInt($("#extra-info-sina").text())+parseInt(share_to_sina));
             }
             if(share_to_tencent){
                 //GP_Share.sharePlus( view_id, 'tencent' );
                 $("#extra-info-tencent").text(parseInt($("#extra-info-tencent").text())+parseInt(share_to_tencent));
             }

                if(data["error"] == false){
                    $(".share-tips").removeClass("share-tips-send");
                    $(".share-tips").addClass("share-tips-success");
                    $(".share-tip-content div").hide();
                    $(".share-tip-content .sync-stats").show();
                    $(".share-tip-content").css('width','280px');
                    $(".share-tip-content .sync-stats span").text(data['message']);
                    $(".share-tip-content .sync-stats").show();
                }
                else{
                    $(".share-tips").removeClass("share-tips-send");
                    $(".share-tips").addClass("share-tips-error");
                    $(".share-tip-content div").hide();
                    $(".share-tip-content .sync-stats").show();
                    $(".share-tip-content").css('width','280px');
                    $(".share-tip-content .sync-stats span").text(data['message']);
                    $(".share-tip-content .sync-stats").show();
                }
             // end effects


        },
        complete: function(data){
                    setTimeout(function(){
                    $(".share-tips").slideUp(350,function(){
                        $(".share-tip-content div").show();
                        $(".share-tip-content .sync-stats").hide();

                    });
                }, 3000);


        }           
        });









	}
});

	// share-button-right +1 count
	$(".share-button-right div.GP_Uu").click(function(){
		var view_id = $(".share-button-right").attr('class').split(' ')[1];
		if(!$(this).hasClass("unable")){
			var item_stats = $(this).attr("class").split(" ")[0];
			GP_Share.sharePlus( view_id, item_stats );
			$(this).addClass("unable");
		}
	});

});



/*
Share Functions
*/

var GP_Share = {};

// Submit +1 count
GP_Share.sharePlus = function( view_id, item_stats){
	view_id = $.trim( view_id );
	item_stats = $.trim( item_stats );
	if( view_id == "" || item_stats === "" ){
		return false;
	}
	$.post("/ajax/share_plus", { guid: view_id, item : item_stats }, function(data) {
			},"json"
		);
}

/*
前台即可活动编辑修改 ajax
*/
$('.weibo_update').toggle(

function() {
    if ($(this).parent().find(".weibo_update").hasClass("weibo_update_yes")) {
        $(this).text("撤销");
        $(this).parent().parent().find('.main').wrapInner('<form class="format tweet" name="tweet"><textarea name="tweet" class="text"></textarea><input class="weibo_update_ok submit" type="button" value="更新" name="submit" class="submit png_bg"><div class="eventWeibo1-upload-box"><div class="eventWeibo-uploader1"><noscript><p>不支持js</p></noscript></div><span class="msg"></span></div></form>')


        var weibo_id = $(this).attr('class').split(" ")[1];
        eventWeibo1.upload1($(this).parent().parent().find('.eventWeibo-uploader1'), weibo_id);

        $(this).parent().parent().find('.weibo_update_ok').click(function(e) {
            //$(this).text("3333");
            var $objChild = $(e.target);
            var weibo_content = $(this).parent().parent().find('.text').val();
            $.post("/ajax/update_weibo", {
                id: weibo_id,
                content: weibo_content
            }, function(data) {
                if (data['error'] == true) alert('提交失败，请重试！');
                else {
                    $objChild.parent().parent().parent().find('.weibo_update').text("修改").removeClass("weibo_update_no").addClass("weibo_update_yes");
                    $objChild.parent().parent().parent().find('.main').html(weibo_content).css({
                        'background-color': '#F3F4F8',
                        'padding': '5px'
                    }).animate({
                        backgroundColor: "#ffffff"
                    }, 1500);

                }
            }, "json");
        });
        $(this).parent().find(".weibo_update").removeClass("weibo_update_yes").addClass("weibo_update_no");
    }
}, function() {
    if ($(this).parent().find(".weibo_update").hasClass("weibo_update_no")) {
        var weibo_content = $(this).parent().parent().find('.text').val();
        $(this).parent().parent().find('.main').html(weibo_content)
        $(this).parent().find(".weibo_update").text("修改").removeClass("weibo_update_no").addClass("weibo_update_yes");
    } else {

    }
});

/**
删除广播图片
**/
$('.weibo_img_del').click(function(e) {
    var $objChild = $(e.target);

    $.post('/upload/del_weibo_img', {
        imgs: $(this).attr('class').split(" ")[1]
    }, function(data) {

        if (data['success'] == true) $objChild.parent().parent().find('.weibo_img_del').html('<span style="color:#02cb2c">删除成功</span>').removeClass("weibo_img_no").addClass("weibo_img_yes").end().find('.weibo-img-thumb').delay(1000).fadeOut('slow');

        else {
            $objChild.parent().parent().find('.weibo_img_del').html('<span style="color:#f03131">' + data['message'] + '</span>').removeClass("weibo_img_yes").addClass("weibo_img_no").delay(1000).fadeOut('slow');
        }
    }, "json");

});
/**
图片点击放大
**/
$('.weibo-img-thumb img').click(function() {
    $(this).parent().hide();
    $(this).parent().parent().find('.weibo-img-orig .img-orig').html('<img src="' + $(this).parent().find('.weibo-img-orig-url').text() + '"/>');
    $(this).parent().parent().find('.weibo-img-orig').show();
});
$('.weibo-img-toggle, .img-orig').click(function() {
    $(this).parent().parent().parent().find('.weibo-img-orig').hide();
    $(this).parent().parent().parent().find('.weibo-img-thumb').show();
});

// 时间归档
var pub_time = [];
var a = []
$('.pubtime_date').each(function(i) {
    pub_time[i] = $(this).text();
});



for (var i = 0, z = 0; i < pub_time.length; i++) {

    for (var j = i + 1; j < pub_time.length; j++) {

        if (pub_time[i] == pub_time[j]) {
            a[z] = pub_time[j]
            j++;
            z++;
        } else {
            break;
        }
    }
}


for (var x = 0; x < a.length; x++) {

    $('.pubtime_date').each(function(i) {
        if ($(this).text() == a[x]) $(this).addClass('same same-' + i)
    });


}







jQuery(document).ready(function($) {
  // hide tencent tj
  $('a[title="腾讯分析"]').hide();

  // 移动版跳转链接
  $('.jump-to-mobile').live('click', function(event) {
    $.getJSON('http://www.geekpark.net/api?jsoncallback=?', 'api_key=d7d1fe3f36fdb557f56ef72922a556b0&api_sig=bb2d3cb4701e671e05d2fa3ce0c9323d&method=server.facility.browser&event_key=94finaw6qhm1t3t2xbgksvv8aytga4eu&facility=ios', function(json, textStatus) {}).complete(function() {
      location.href = "http://m.geekpark.net/";
    });
  });
});



