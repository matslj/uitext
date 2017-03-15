/**
 * 
 */

var uitext = uitext || {};
   		
uitext.menu = (function($) {
	var SIDEBAR_HIDE = 'sidebar-hide',
	    MENU_CONTAINER = '.menu-container',
	    SELECTED_MENU_ITEM = 'active-menuitem',
	    
	    resolveMenuSelectionxxx = function() {
			//$(".menu-container a.active-menuitem").removeClass("active-menuitem");
			var url = window.location.href;
			$(".menu-container>li").each(function() {
				var href = $(this).find('a').attr("href");
				if (url.indexOf(href) >= 0) {
					$(this).addClass(SELECTED_MENU_ITEM);
			    }
			});
		},
	
	selectAndExpandLiParents = function($element) {
		var $liNode = $element.parent('li');
		if ($liNode.length) {
			$liNode.addClass(SELECTED_MENU_ITEM);
			selectAndExpandLiParents($liNode);
		}
	},
		
	resolveMenuSelection = function() {
		var url = window.location.href,
		    $selectedAnchor = null;
		
		$(MENU_CONTAINER + " li a").each(function() {
			var href = $(this).attr("href");
			if (url.indexOf(href) >= 0) {
				$selectedAnchor = $(this);
		    }
		});
		selectAndExpandLiParents($selectedAnchor);
	};
	
	return {
		showHideSidebar : function(currValue) {
   			var $wrapper = $('#wrapperId');
   			if (!currValue) {
   				$wrapper.removeClass(SIDEBAR_HIDE);
			} else {
				$wrapper.addClass(SIDEBAR_HIDE);
			}
   		},
   		
   		init : function() {
   			resolveMenuSelection();
   		}
	};
}(jQuery));