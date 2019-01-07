function updateBtnCreateUserLabel(nbUsers) {
	var str = "Create " + nbUsers;
	nbUsers == 1 ? str += " user" : str += " users"
	$('#btn_dbadmin_createuser').text(str)
}

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
	
	$('#btn_dbadmin_createuser').on('click',function(e) {
		e.preventDefault();
		var $this = $(this);
		var nbusers = $this.attr('data-nbusers');
		$this.attr('disabled','disabled')
		
		$('#span_dbadmin_createuser_console').text('Calling the Random User API...')

		$.ajax({
				url: 'https://randomuser.me/api/?results='+nbusers,
				dataType: 'json',
				success: function(data) {
					$('#span_dbadmin_createuser_console').text('')
					for(i = 0; i < data.results.length; i++) {
						var firstname = data.results[i].name.first
						var lastname = data.results[i].name.last
						var email = data.results[i].email
						var company = data.results[i].login.username
						
						$.post("databaseAdministration", {
							action:'create_user',
							firstname:firstname,
							lastname:lastname,
							email:email,
							company:company
						}).done(function(responseJson) {
							var str = $('#span_dbadmin_createuser_console').html()
							str += "Created "+responseJson.firstname+" "+responseJson.lastname+", with email "+responseJson.email+"<br/>"
							$('#span_dbadmin_createuser_console').html(str)
						});
					}
				}
		}).done(function(){
			$this.removeAttr("disabled");
		})
		
		
	})
	
	$('#btn_dbadmin_createuser_plus').on('click',function(e) {
		e.preventDefault();
		var nbUsers = $('#btn_dbadmin_createuser').attr('data-nbusers')
		nbUsers++;
		$('#btn_dbadmin_createuser').attr('data-nbusers',nbUsers)
		updateBtnCreateUserLabel(nbUsers)
	})
	
	$('#btn_dbadmin_createuser_minus').on('click',function(e) {
		e.preventDefault();
		var nbUsers = $('#btn_dbadmin_createuser').attr('data-nbusers')
		if(nbUsers > 1) {
			nbUsers--
			$('#btn_dbadmin_createuser').attr('data-nbusers',nbUsers)
			updateBtnCreateUserLabel(nbUsers)
		}
	})
})