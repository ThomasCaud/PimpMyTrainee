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
	
	$('#btn_dbadmin_create_all_tables').on('click',function(e) {
		e.preventDefault();
		var $this = $(this)
		
		$this.attr("disabled", "disabled");
		$('#span_dbadmin_create_all_tables').text('Creating all tables...')
		
		$.post("databaseAdministration", {action:'create_all_tables'}).done(function(responseJson) {
			$('#span_dbadmin_create_all_tables').text('Done !')
			$this.removeAttr("disabled");
		});
	})
	
	$('#btn_dbadmin_create_admin_account').on('click',function(e) {
		e.preventDefault();
		var $this = $(this)
		
		$this.attr("disabled", "disabled");
		$('#span_dbadmin_create_admin_account').text('Creating admin account...')
		
		$.post("databaseAdministration", {action:'create_admin_account'}).done(function(responseJson) {
			var email = responseJson.email;
			var password= responseJson.password;
			$('#span_dbadmin_create_admin_account').text('An admin has been created with the email '+email+'. The password is '+password)
			$this.removeAttr("disabled");
		});
	})
})