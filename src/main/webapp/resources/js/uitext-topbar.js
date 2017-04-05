/**
 * @author Mats L
 */

var uitext = uitext || {};
   		
uitext.topbar = (function($) {
	var TOPBAR_ICONS = '#topbar-icons',
		MENU_BUTTON = '#sidebar-toggle-button',
	    ACTIVE = 'active',
	    TOPBAR_SUBMENU_HOLDER = 'topbar-submenu',
	    TOPBAR_ICON_TOGGLE_BUTTON = '#topbar-icons-toggle-button',
	    
    $topbarIcons = null,
    $topbarIconsToggleButton = null,
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
	
	topbarMenuHandler = function(e) {
		$(this).toggleClass('active');
        
        if(isDesktop()) {
            $wrapper.toggleClass('sidebar-inactive-l');
            
            if($wrapper.hasClass('sidebar-inactive-l')) {
                $wrapper.removeClass('sidebar-active-m');
            }
        } else {
            $wrapper.toggleClass('sidebar-active-m');
            
            if($wrapper.hasClass('sidebar-active-m')) {
                $wrapper.removeClass('sidebar-inactive-l');
            }
        }
        
        e.preventDefault();
	},
	
	iconsToggleButtonHandler = function(e) {
        if($topbarIcons.hasClass('topbar-icons-visible')) {
            $topbarIcons.removeClass('topbar-icons-visible');
            expandedMenuClick = false;
        } else {
            $topbarIcons.addClass('topbar-icons-visible');
            expandedMenuClick = true;
        }

        $wrapper.removeClass('sidebar-active-m sidebar-inactive-l');
        e.preventDefault();
    },
    
    isDesktop = function() {
        return window.innerWidth > 1024;
    };
	
	return {
   		
   		/**
   		 * Initializerar modulen. Anropas lämpligen från en $(document).ready()-funktion.
   		 */
   		init : function() {
   			$topbarIcons = $(TOPBAR_ICONS);
   			$menuButton = $(MENU_BUTTON);
   			$topbarIconsToggleButton = $(TOPBAR_ICON_TOGGLE_BUTTON);
   			$wrapper = $('#wrapperId');
   			

   			// Register topbar-icons eventhandling
   			$topbarIcons.off('click').on('click', topbarIconsSubmenuClickhandler);
   			$menuButton.off('click').on('click', topbarMenuHandler);
   			$topbarIconsToggleButton.off('click').on('click', iconsToggleButtonHandler);
   			
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