/* Place in ippex\tokamak\

   In toka_dev.html:
     <script src="./pixi.draggable.releaseAll.js"></script>

   In play.js (when disruptions >= maxDisruptions):
     dragAndDropManager.releaseAll();
*/
PIXI.DragAndDropManager.prototype.releaseAll = function()
{
	for (var i = 0; i < this.draggableItems.length; i++) {
		this.draggableItems[i].__isDragging = false;
	}
};