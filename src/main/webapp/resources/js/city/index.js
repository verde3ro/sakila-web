function loadTable() {
	const tblCities = $('#tblCities');
	
	$.ajax({
		url: 'http://localhost:8080/sakila-web/city/findAll',
		type: 'GET',
		dataType: 'json',
		success: function (data) {
			if (data.estatus === 'success') {
				tblCities.bootstrapTable({data: data.datos});
			} else {
				bootbox.alert({
					message: data.mensaje,
					size: 'large'
				});
			}
		}
	});
}

function addCity() {
	const btnAddCity = $('#btnAddCity');
	
	btnAddCity.click(function () {
		console.log('Aqui llego');
	});
}

loadTable();
addCity();
