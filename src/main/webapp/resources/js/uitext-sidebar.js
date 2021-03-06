/**
 * @author Mats L
 */

var uitext = uitext || {};
   		
uitext.sidebar = (function($) {
	var MENU_CONTAINER = '.menu-container',
	    PROFILE_BUTTON = '#profile-button',
	    SELECTED_MENU_ITEM = 'active-menuitem',
	    
	    $menuContainer = null,
	    $menuButton = null,
	    $profileButton = null,
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
	},
	
	profileButtonHandler = function(e) {
        var profileMenu = $(this).next('ul');
        if(profileMenu.is(':visible')) {
            profileMenu.slideUp();
        }
        else {
            profileMenu.slideDown();
        }
        
        e.preventDefault();
    };

	return {
   		
   		/**
   		 * Initializerar modulen. Anropas lämpligen från en $(document).ready()-funktion.
   		 */
   		init : function() {
   			$menuContainer = $(MENU_CONTAINER);
   			$profileButton = $(PROFILE_BUTTON);
   			$wrapper = $('#wrapperId');
   			
   			resolveMenuSelection();

   			// Register eventhandling
   			$menuContainer.off('click').on('click', menuClickHandler);
   			$profileButton.off('click').on('click', profileButtonHandler);
   		}
	};
}(jQuery));
