(function($) {

	//cache management
	var webappCache = window.applicationCache;
	 
	webappCache.addEventListener("updateready", updateCache, false);
	//webappCache.update();
	 
	function updateCache() {
	webappCache.swapCache();
	alert("Une nouvelle version est disponible.\nVeuillez rafraîchir la page pour mettre à jour.");
	}
	
	
	$("#draggable").draggable();
	Task = Backbone.Model.extend({
		// Create a model to hold task attribute
		taskId : null,
		severity : null,
		assignedTo : null,
		priority : null,
		status : null,
		summary : null
	});

	$("button").button();
	var tasks = new Backbone.Collection;

	var AppView = Backbone.View.extend({

		el : $("body"),
		initialize : function() {
			console.log('Initialize  View');
			tasks.bind('add', this.addTasksLi, this);
		},
		events : {
			"click #get-tasks" : "getTasks",
		},
		getTasks : function() {
			var data = '';

			$.get('tasksjs', data, function(response, textStatus, jqXHR) {
				console.log("Got response");
				var contentArr = CSVToArray(response);

				contentArr.forEach(function(element, index, array) {
					if (array[index][0] == "bug_id") {
						console.log("skip header");
					} else {
						// console.log(index + '/' + element);
						var mTask = new Task();

						mTask.set({
							taskId : array[index][0]
						});
						mTask.set({
							severity : array[index][1]
						});
						mTask.set({
							assignedTo : array[index][4]
						});
						mTask.set({
							priority : array[index][2]
						});
						mTask.set({
							status : array[index][5]
						});
						mTask.set({
							summary : array[index][7]
						});
						// console.log(JSON.stringify(mTask));

						tasks.add(mTask);
					}
				});

			}).success(function() {

			}).error(function(response, msg) {
				alert("error:" + response);
			}).complete(function() {

			});
			;

		},
		addTasksLi : function(task) {
			// The parameter passed is a reference to the model that was added
			var listToAdd = "#listTODO";
			if (task.get("taskId") == "") {
				return;
			}
			if (task.get("status") == "ASSIGNED") {
				listToAdd = "#listDOING";
			} else if (task.get("status") == "RESOLVED") {
				listToAdd = "#listDONE";
			} else if (task.get("status") == "CLOSED") {
				listToAdd = "#listDONE";
			}
			$(listToAdd).append(Mustache.to_html($('#task-template').html(), task.toJSON()));
			var descriptId = "#desc" + task.get("taskId");
	
			$( descriptId ).dialog({
				autoOpen: false,
				height: 300,
				width: 350,
				modal: true});
			var mId = "#"+task.get("taskId");
			$(mId).click(function() {
			
					$(descriptId).dialog("open");
			});

		}

	});

	var appview = new AppView;

	$("#listTODO").sortable({
		connectWith : ".connectedSortable",
		items : "li:not(.placeholder)",
		over : function(event, ui) {
			$("#droppableTODO").addClass("ui-state-highlight");
		},
		out : function(event, ui) {
			$("#droppableTODO").removeClass("ui-state-highlight");
		}
	}).disableSelection();
	$("#listDOING").sortable({
		connectWith : ".connectedSortable",
		items : "li:not(.placeholder)",
		over : function(event, ui) {
			$("#droppableDOING").addClass("ui-state-highlight");
		},
		out : function(event, ui) {
			$("#droppableDOING").removeClass("ui-state-highlight");
		}
	}).disableSelection();
	$("#listDONE").sortable({
		connectWith : ".connectedSortable",
		items : "li:not(.placeholder)",
		over : function(event, ui) {
			$("#droppableDONE").addClass("ui-state-highlight");
		},
		out : function(event, ui) {
			$("#droppableDONE").removeClass("ui-state-highlight");
		}
	}).disableSelection();

})(jQuery);

// This will parse a delimited string into an array of
// arrays. The default delimiter is the comma, but this
// can be overriden in the second argument.
function CSVToArray(strData, strDelimiter) {
	// Check to see if the delimiter is defined. If not,
	// then default to comma.
	strDelimiter = (strDelimiter || ",");

	// Create a regular expression to parse the CSV values.
	var objPattern = new RegExp((
	// Delimiters.
	"(\\" + strDelimiter + "|\\r?\\n|\\r|^)" +

	// Quoted fields.
	"(?:\"([^\"]*(?:\"\"[^\"]*)*)\"|" +

	// Standard fields.
	"([^\"\\" + strDelimiter + "\\r\\n]*))"), "gi");

	// Create an array to hold our data. Give the array
	// a default empty first row.
	var arrData = [ [] ];

	// Create an array to hold our individual pattern
	// matching groups.
	var arrMatches = null;

	// Keep looping over the regular expression matches
	// until we can no longer find a match.
	while (arrMatches = objPattern.exec(strData)) {

		// Get the delimiter that was found.
		var strMatchedDelimiter = arrMatches[1];

		// Check to see if the given delimiter has a length
		// (is not the start of string) and if it matches
		// field delimiter. If id does not, then we know
		// that this delimiter is a row delimiter.
		if (strMatchedDelimiter.length && (strMatchedDelimiter != strDelimiter)) {

			// Since we have reached a new row of data,
			// add an empty row to our data array.
			arrData.push([]);

		}

		// Now that we have our delimiter out of the way,
		// let's check to see which kind of value we
		// captured (quoted or unquoted).
		if (arrMatches[2]) {

			// We found a quoted value. When we capture
			// this value, unescape any double quotes.
			var strMatchedValue = arrMatches[2].replace(new RegExp("\"\"", "g"), "\"");

		} else {

			// We found a non-quoted value.
			var strMatchedValue = arrMatches[3];

		}

		// Now that we have our value string, let's add
		// it to the data array.
		arrData[arrData.length - 1].push(strMatchedValue);
	}

	// Return the parsed data.
	return (arrData);
}
