function loadTable() {
	const tblCities = $('#tblCities');
	tblCities.bootstrapTable();
	
	$.ajax({
		url: '/sakila-web/city/findAll',
		type: 'GET',
		dataType: 'json',
		success: function (data) {
			if (data.estatus === 'success') {
				tblCities.bootstrapTable('destroy').bootstrapTable({data: data.datos});
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
		$.ajax({
			type: 'GET',
			url: '/sakila-web/city/city.html',
			contentType: 'application/html; charset=utf-8',
			success: function(html) {
				bootbox.dialog({
					title: 'Agregar Ciudad',
					onEscape: false,
					animate: true,
					message: html,
					buttons: {
						cancel: {
							label: 'Cancelar',
							className:'btn-secondary'
						},
						save: {
							label: 'Agregar',
							className: 'btn-success',
							callback: function() {
								if ($('#txtCity').val() !== '' && $('#cmbPais').val().trim() !== '') {
									save();
								} else {
									bootbox.alert('Debes introducir los valores');
								}

								return false;
							}
						}
					}
				});
			}
		});
	});
}

function save() {
	const frmCity = $('#frmCity');
	
	$.ajax({
		url: '/sakila-web/city/save',
		type: 'POST',
		data: frmCity.serialize(),
		dataType: 'json',
		success: function(data) {
			if(data.estatus === 'success') {
				loadTable();
				$('.modal').modal('hide');
				bootbox.alert('Se agrego la ciudad <b>' + data.datos.city + '</b> con el ID <b>' + data.datos.cityId + '</b>');
			} else {
				bootbox.alert(data.mensaje);
			}
		}
	});
}

function getExcel() {
	console.log('botón excel');
	const btnExcel = $('#btnExcel');
	
	btnExcel.click(function() {
		$.ajax({
		url: '/sakila-web/city/excel',
		type: 'GET',
		dataType: 'json',
		success: function (data) {
			if (data.estatus === 'success') {
				const datos = data.datos;
				const download = document.createElement('a');
				
				download.href = 'data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64, ' + datos;
				download.download = 'ciudades.xlsx';
				download.click();
			} else {
				bootbox.alert({
					message: data.mensaje,
					size: 'large'
				});
			}
		}
	});
	});
}

loadTable();
addCity();
getExcel();
