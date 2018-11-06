function getSearchParameters() {
      var prmstr = window.location.search.substr(1);
      return prmstr != null && prmstr != "" ? transformToAssocArray(prmstr) : {};
}

function transformToAssocArray( prmstr ) {
    var params = {};
    var prmarr = prmstr.split("&");
    for ( var i = 0; i < prmarr.length; i++) {
        var tmparr = prmarr[i].split("=");
        params[tmparr[0]] = tmparr[1];
    }
    return params;
}

function colorPossibleAnswers() {
	$("input[type='radio']:checked").closest(".possibleAnswerRow").addClass('possibleAnswerRow-correct');
	$("input[type='radio']:checked").closest(".possibleAnswerRow").removeClass('possibleAnswerRow-incorrect');
	$("input[type='radio']:not(:checked)").closest(".possibleAnswerRow").addClass('possibleAnswerRow-incorrect');
	$("input[type='radio']:not(:checked)").closest(".possibleAnswerRow").removeClass('possibleAnswerRow-correct');
}

$(document).ready(function(){
	colorPossibleAnswers();
	
	$("input[type='radio']").click(function(){
		colorPossibleAnswers();
	})
	
	$("#btnNewQuestion").click(function(e){
		e.preventDefault();
	});
	
	$('[data-toggle="tooltip"]').tooltip()
	
	$('select#nbItemsPerPage').on("change",function(){
		var value = $('select#nbItemsPerPage option:selected').text();
		var url = window.location.href;
		var search = window.location.search.substr(1);
		url = url.replace(search,"")
		
		var params = getSearchParameters();
		
		if(search == "")
			url += "?"
		params.n = value
				
		for (var param in params) {
		    if (params.hasOwnProperty(param)) {
		        url += param
		        url += "="
		        url += params[param]
		        url += "&"
		    }
		}

		url = url.substring(0,url.length-1)
			
		window.location.href = url;
	});
});