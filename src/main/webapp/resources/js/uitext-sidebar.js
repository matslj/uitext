/**
 * @author Mats L
 */

var uitext = uitext || {};
   		
uitext.sidebar = (function($) {
	var MENU_CONTAINER = '.menu-container',
	    SELECTED_MENU_ITEM = 'active-menuitem',
	    
	    $menuContainer = null,
	    $wrapper = null,
	
	/**
	 * Markerar vald menuitem i menyträdet och expanderar
	 * alla ovanliggande menyer i trädet.
	 */
	selectAndExpandLiParents = function($element) {
		var $liNode = $element.parents('li'),
		    $parentUl = $liNode.parent('ul');
		if ($liNode.length) {
			if ($parentUl.length) {
				$parentUl.show(0);
			}
			$liNode.addClass(SELECTED_MENU_ITEM);
			selectAndExpandLiParents($liNode);
		}
	},
		
	resolveMenuSelection = function() {
		var url = window.location.href,
		    $selectedAnchor = null;

		// Trailing hashmark disturbs the indexOf below, so I remove them.
		// This will break if there is something after the hash. In my use
		// case there isn't but a more stable solution would be to remove
		// the hash and everything after (perhaps by changing the replace regex).
		url = url.replace("#", '');
		$(MENU_CONTAINER + " li a").each(function() {
			var href = $(this).attr("href");
			if (url.indexOf(href) >= 0) {
				$selectedAnchor = $(this);
		    }
		});
		selectAndExpandLiParents($selectedAnchor);
	},
	
	/**
	 * Sköter all menyhantering i samband med ett klick på en menypost.
	 */
	menuClickHandler = function(e) {
		var $target = $(e.target),
		    $parentLi = $target.closest('li'),
		    $oldLi = null;
		if ($parentLi.hasClass(SELECTED_MENU_ITEM)) {
			// Menyposten är sedan tidigare vald och ska nu deselectas. 
			// Stäng eventuell undermeny och avmarkera klickad menypost.
			$parentLi.find('>ul').hide(500);
			$parentLi.removeClass(SELECTED_MENU_ITEM);
		} else {
			$oldLi = $parentLi.parents('li.' + SELECTED_MENU_ITEM);
			if ($oldLi.length == 0) {
				// Klicket har skett på en ny gren i menyträdet. Stäng alla andra öppna menygrenar
				// och avmarkera alla förekomster i dessa grenar.
				$oldLi = $menuContainer.find('li.' + SELECTED_MENU_ITEM);
				if ($oldLi.length) {
					$oldLi.find('>ul').hide(500);
					$oldLi.removeClass(SELECTED_MENU_ITEM);
				}
			} else {
				// Den klickade menyposten är ett barn till en redan vald menypost. Stäng alla ul-barn (submenyer)
				// och avmarkera alla underposter (så att de ej längre är markerade som valda).
				$parentLi.find('ul').hide(0);
				$parentLi.find('li.' + SELECTED_MENU_ITEM).removeClass(SELECTED_MENU_ITEM);
			}
			// Markera klickad menypost och öppna eventuell undermeny.
			$parentLi.addClass(SELECTED_MENU_ITEM);
			$parentLi.find('>ul').show(500);
		}
	};
	
	return {
		/**
		 * Dölj/visa sidomenyn baserat på nuvarande tillstånd för sidomenyn.
		 * 
		 * @param currValue boolean - om true så betyder det att nuvarande tillstånd är att menyn visas,
		 *                  och således ska den nu döljas av den här metoden. Om false så betyder det att
		 *                  nuvarande tillstånd är att menyn är dold, och då ska den här metoden göra att
		 *                  den visas igen.
		 */
		showHideSidebar : function(currValue) {
   			if (currValue) {
   				$wrapper.attr('class', 'wrapper sidebar-inactive-l');
			} else {
				$wrapper.attr('class', 'wrapper sidebar-active-m');
			}
   		},
   		
   		/**
   		 * Initializerar modulen. Anropas lämpligen från en $(document).ready()-funktion.
   		 */
   		init : function() {
   			$menuContainer = $(MENU_CONTAINER);
   			$wrapper = $('#wrapperId');
   			
   			resolveMenuSelection();
   			
   			// Register events
   			$menuContainer.click(menuClickHandler);
   		}
	};
}(jQuery));

/*!
 * jQuery Cookie Plugin v1.4.1
 * https://github.com/carhartl/jquery-cookie
 *
 * Copyright 2006, 2014 Klaus Hartl
 * Released under the MIT license
 */
(function (factory) {
	if (typeof define === 'function' && define.amd) {
		// AMD (Register as an anonymous module)
		define(['jquery'], factory);
	} else if (typeof exports === 'object') {
		// Node/CommonJS
		module.exports = factory(require('jquery'));
	} else {
		// Browser globals
		factory(jQuery);
	}
}(function ($) {

	var pluses = /\+/g;

	function encode(s) {
		return config.raw ? s : encodeURIComponent(s);
	}

	function decode(s) {
		return config.raw ? s : decodeURIComponent(s);
	}

	function stringifyCookieValue(value) {
		return encode(config.json ? JSON.stringify(value) : String(value));
	}

	function parseCookieValue(s) {
		if (s.indexOf('"') === 0) {
			// This is a quoted cookie as according to RFC2068, unescape...
			s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');
		}

		try {
			// Replace server-side written pluses with spaces.
			// If we can't decode the cookie, ignore it, it's unusable.
			// If we can't parse the cookie, ignore it, it's unusable.
			s = decodeURIComponent(s.replace(pluses, ' '));
			return config.json ? JSON.parse(s) : s;
		} catch(e) {}
	}

	function read(s, converter) {
		var value = config.raw ? s : parseCookieValue(s);
		return $.isFunction(converter) ? converter(value) : value;
	}

	var config = $.cookie = function (key, value, options) {

		// Write

		if (arguments.length > 1 && !$.isFunction(value)) {
			options = $.extend({}, config.defaults, options);

			if (typeof options.expires === 'number') {
				var days = options.expires, t = options.expires = new Date();
				t.setMilliseconds(t.getMilliseconds() + days * 864e+5);
			}

			return (document.cookie = [
				encode(key), '=', stringifyCookieValue(value),
				options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
				options.path    ? '; path=' + options.path : '',
				options.domain  ? '; domain=' + options.domain : '',
				options.secure  ? '; secure' : ''
			].join(''));
		}

		// Read

		var result = key ? undefined : {},
			// To prevent the for loop in the first place assign an empty array
			// in case there are no cookies at all. Also prevents odd result when
			// calling $.cookie().
			cookies = document.cookie ? document.cookie.split('; ') : [],
			i = 0,
			l = cookies.length;

		for (; i < l; i++) {
			var parts = cookies[i].split('='),
				name = decode(parts.shift()),
				cookie = parts.join('=');

			if (key === name) {
				// If second argument (value) is a function it's a converter...
				result = read(cookie, value);
				break;
			}

			// Prevent storing a cookie that we couldn't decode.
			if (!key && (cookie = read(cookie)) !== undefined) {
				result[name] = cookie;
			}
		}

		return result;
	};

	config.defaults = {};

	$.removeCookie = function (key, options) {
		// Must not alter options, thus extending a fresh object...
		$.cookie(key, '', $.extend({}, options, { expires: -1 }));
		return !$.cookie(key);
	};

}));