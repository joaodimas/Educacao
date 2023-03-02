function setupPickList() {
	jQuery("select[id='pickListAusentes']").multiSelect("select[id='pickListPresentes']", {
		trigger: "[id='moveLeft']",
	});

	jQuery("select[id='pickListPresentes']").multiSelect("select[id='pickListAusentes']", {
		trigger: "[id='moveRight']",
	});
}

function atualizarIdsAlunos() {
	var childrenPresentes = jQuery("[id='pickListPresentes']").children();
	var childrenAusentes = jQuery("[id='pickListAusentes']").children();
			
	var presentesStr = "";
	for(var i = 0; i < childrenPresentes.length; i++) {
		if(childrenPresentes[i].value != null && childrenPresentes[i].value != "") {
			presentesStr += childrenPresentes[i].value;
			if(i < childrenPresentes.length-1) {
				presentesStr += ",";
			}			
		}
	}
			
	var ausentesStr = "";
	for(var i = 0; i < childrenAusentes.length; i++) {
		if(childrenAusentes[i].value != null && childrenAusentes[i].value != "") {
			ausentesStr += childrenAusentes[i].value;
			if(i < childrenAusentes.length-1) {
				ausentesStr += ",";
			}			
		}
	}
	
	jQuery("[id='formRegistrarChamada:idsPresentes']").attr('value', presentesStr);
	jQuery("[id='formRegistrarChamada:idsAusentes']").attr('value', ausentesStr);
		
	return true;
}