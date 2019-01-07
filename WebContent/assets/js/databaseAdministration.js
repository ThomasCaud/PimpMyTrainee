$(document).ready(function(){
	$('button.btn_dbadmin').on('click',function(e) {
		e.preventDefault();
		var $this = $(this)
		var script = $this.attr('data-script')
		var inProgressMessage = $this.attr('data-in-progress')
		var doneMessage = $this.attr('data-done')
		var number = $this.attr('data-dataset-number')
		var spanId = $this.attr('data-span-id')
		
		if(spanId == null) {
			spanId = '#span_'+script
		}
		
		$this.attr("disabled", "disabled");
		$(spanId).text(inProgressMessage)
		
		$.post("databaseAdministration", {action:script,datasetNumber:number}).done(function(responseJson) {
			$(spanId).text(doneMessage)
			$this.removeAttr("disabled");
		});
	})
})