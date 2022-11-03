var Brewer = Brewer || {};


Brewer.MultiSelecao = (function() {
	class MultiSelecao {
		constructor() {
			this.statusBtn = $('.js-btn-status');
			this.selectedCheckBox = $('.js-selected');

		}
		enable() {
			this.statusBtn.on('click', onStatusBtnClick.bind(this));
		}


	}

	function onStatusBtnClick(event) {
		var btnClicked = $(event.currentTarget);
		var status = btnClicked.data('status');

		var checkBoxesSelected = this.selectedCheckBox.filter(':checked');
		var codigos = []
		$.map(checkBoxesSelected, function(c) {
			codigos.push($(c).data('codigo'));
		});

		console.log(codigos);
		console.log(status);

		if (codigos.length > 0) {
			$.ajax({
				url: '/usuarios/status',
				method: 'PUT',
				data: {
					'codigos': codigos,
					status: status
				},
				beforeSend: addCsrfToken,
				success: function() {
					window.location.reload();
				}
			});
		}
	}


	function addCsrfToken(xhr) {
		var token = $('input[name=_csrf]').val();
		var header = $('input[name=_csrf_header]').val();
		xhr.setRequestHeader(header, token);
	}

	return MultiSelecao;

}());

$(function() {
	var multiSelecao = new Brewer.MultiSelecao();
	multiSelecao.enable();

});