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
		var $liNode = $element.parents('li'),
		    $parentUl = $liNode.parent('ul');
		if ($liNode.length) {
			if ($parentUl.length) {
				$parentUl.show(1);
			}
			$liNode.addClass(SELECTED_MENU_ITEM);
			selectAndExpandLiParents($liNode);
		}
	},
		
	resolveMenuSelection = function() {
		var url = window.location.href,
		    $selectedAnchor = null;

		url = url.replace("#", '');
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
   				//$wrapper.removeClass(SIDEBAR_HIDE);
   				$wrapper.attr('class', 'wrapper sidebar-active-m')
			} else {
				//$wrapper.addClass(SIDEBAR_HIDE);
				$wrapper.attr('class', 'wrapper sidebar-inactive-l')
			}
   		},
   		
   		init : function() {
   			var $menuContainer = $(MENU_CONTAINER);
   			
   			resolveMenuSelection();
   			
   			$menuContainer.click(function(e) {
   				var $target = $(e.target),
   				    $parentLi = $target.closest('li'),
   				    $oldLi = null;
   				if ($parentLi.hasClass(SELECTED_MENU_ITEM)) {
   					$parentLi.find('>ul').hide(500);
   					$parentLi.removeClass(SELECTED_MENU_ITEM);
   				} else {
   					$oldLi = $parentLi.parents('li.' + SELECTED_MENU_ITEM);
   					if ($oldLi.length == 0) {
		   				$oldLi = $menuContainer.find('li.' + SELECTED_MENU_ITEM);
		   				if ($oldLi.length) {
		   					$oldLi.find('>ul').hide(500);
		   					$oldLi.removeClass(SELECTED_MENU_ITEM);
		   				}
   					}
	   				$parentLi.addClass(SELECTED_MENU_ITEM);
	   				$parentLi.find('>ul').show(500);
   				}
   			});
   		}
	};
}(jQuery));