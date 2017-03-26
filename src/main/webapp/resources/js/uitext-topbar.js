/**
 * @author Mats L
 */

var uitext = uitext || {};
   		
uitext.topbar = (function($) {
	var TOPBAR_ICONS = '#topbar-icons',
	    ACTIVE = 'active',
	    TOPBAR_SUBMENU_HOLDER = 'topbar-menu',
	    OPTIONS_MENU = '#options-menu-button',
	    
    $topbarIcons = null,
    $optionsMenuButton = null,
    $wrapper = null,
    menuClick = false,
    expandedMenuClick = false;
    
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
	
	optionsButtonHandler = function(e) {
        if($topbarIcons.hasClass('topbar-icons-visible')) {
            $topbarIcons.removeClass('topbar-icons-visible');
            expandedMenuClick = false;
        } else {
            $topbarIcons.addClass('topbar-icons-visible');
            expandedMenuClick = true;
        }

        $wrapper.removeClass('sidebar-active-m sidebar-inactive-l');
        e.preventDefault();
    };
	
	return {
   		
   		/**
   		 * Initializerar modulen. Anropas lämpligen från en $(document).ready()-funktion.
   		 */
   		init : function() {
   			$topbarIcons = $(TOPBAR_ICONS);
   			$optionsMenuButton = $(OPTIONS_MENU);
   			$wrapper = $('#wrapperId');
   			

   			// Register topbar-icons eventhandling
   			$topbarIcons.off('click').on('click', topbarIconsSubmenuClickhandler);
   			$optionsMenuButton.off('click').on('click', optionsButtonHandler);
   			
   			// Ta bort öppna menyer som inte ska vara öppna
   			$(document.body).off('click').on('click', function() {
   				if (!menuClick) {
   					$topbarIcons.find('div').removeClass(ACTIVE);
   				}
   				menuClick = false;
   				if (!expandedMenuClick) {
   					$topbarIcons.removeClass('topbar-icons-visible');
   				}
   				expandedMenuClick = false;
   			});
   		}
	};
}(jQuery));