/**
 * @author Mats L
 */

var uitext = uitext || {};
   		
uitext.topbar = (function($) {
	var TOPBAR_ICONS = '#topbar-icons',
	    ACTIVE = 'active',
	    TOPBAR_SUBMENU_HOLDER = 'topbar-menu',
	    
    $topbarIcons = null,
    menuClick = false,
    
    topbarIconsSubmenuClickhandler = function(e) {
		var $target = $(e.target),
		    $parentA = $target.nodeName != 'a' ? $target.parent('a') : $target,
		    $parentDiv = null;
		    
		if ($parentA.hasClass(TOPBAR_SUBMENU_HOLDER)) {
			menuClick = true;
			$parentDiv = $parentA.parent('div');
			$parentDiv.siblings('div').removeClass(ACTIVE);
			$parentDiv.toggleClass(ACTIVE);
			
			e.preventDefault();
		}
	},
	
	registerEvents = function() {
		
		// Register topbar-icons eventhandling
		$topbarIcons.off('click').on('click', topbarIconsSubmenuClickhandler);
		
		// Ta bort öppna menyer som inte ska vara öppna
		$(document.body).off('click').on('click', function() {
			if (!menuClick) {
				$topbarIcons.find('div').removeClass(ACTIVE);
			}
			menuClick = false;
		});
	};
	
	return {
   		
   		/**
   		 * Initializerar modulen. Anropas lämpligen från en $(document).ready()-funktion.
   		 */
   		init : function() {
   			$topbarIcons = $(TOPBAR_ICONS);
   			
   			// Register events
   			registerEvents();
   		}
	};
}(jQuery));