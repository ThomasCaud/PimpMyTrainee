$(document).ready(function(){
	$('#btn_dbadmin_drop_all_tables').on('click',function(e) {
		e.preventDefault();
		var $this = $(this)
		
		$this.attr("disabled", "disabled");
		$('#span_dbadmin_drop_all_tables').text('Dropping all tables...')
		
		$.post("databaseAdministration", {action:'drop_all_tables'}).done(function(responseJson) {
			$('#span_dbadmin_drop_all_tables').text('Done !')
			$this.removeAttr("disabled");
		});
	})
})