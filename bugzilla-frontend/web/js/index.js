(function($) {
	Task = Backbone.Model.extend({
		// Create a model to hold friend attribute
		taskId : null,
		summary : null
	});

	AppView = Backbone.View.extend({
		el : $("body"),
		initialize : function() {
			console.log('Initialize  View');
		},
		events : {
			"click #get-tasks" : "getTasks",
		},
		getTasks : function() {
			var data = '';
			// call server
			$.get('tasksjs', data, function(response) {
				console.log("get response");
				var returnedObjects = eval(response);
				// create an array of bugs
			//	window.AppView.prototype.addTasksLi(null);

			});
		},
		addTasksLi : function(model) {
			// The parameter passed is a reference to the model that was added
			var i = 0;
			for (i = 0; i < bugs.length; i++) {
				$("#tasks-list").append(
						"<li>" + model[i] + ":" + model[i][2] + "</li>");
			}
			// Use .get to receive attributes of the model
		}
	});

	var appview = new AppView;

})(jQuery);

