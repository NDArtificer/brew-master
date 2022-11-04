var Brewer = Brewer || {};


Brewer.MultiSelecao = (function() {
	class MultiSelecao {
		constructor() {
			this.statusBtn = $('.js-btn-status');
			this.selectedCheckBox = $('.js-selected');
			this.selectAllCheckBox = $('.js-select-all');

		}
		enable() {
			this.statusBtn.on('click', onStatusBtnClick.bind(this));
			this.selectAllCheckBox.on('click', onSelectAll.bind(this));
			this.selectedCheckBox.on('click', onSelectClick.bind(this));
		}


	}

	function statusBtnAction(active) {
		active ? this.statusBtn.removeClass('disable') : this.statusBtn.addClass('disable');
	}

	function onSelectClick() {
		var selectedCheckBoxes = this.selectedCheckBox.filter(':checked');
		this.selectAllCheckBox.prop('checked', selectedCheckBoxes.length >= this.selectedCheckBox.length);
		statusBtnAction.call(this, selectedCheckBoxes.length);

	}

	function onSelectAll() {
		var status = this.selectAllCheckBox.prop('checked');
		this.selectedCheckBox.prop('checked', status);
		statusBtnAction.call(this, status);
	}

	function onStatusBtnClick(event) {
		var btnClicked = $(event.currentTarget);
		var status = btnClicked.data('status');
		var url = btnClicked.data('url');

		var checkBoxesSelected = this.selectedCheckBox.filter(':checked');
		var codigos = []
		$.map(checkBoxesSelected, function(c) {
			codigos.push($(c).data('codigo'));
		});

		console.log(codigos);
		console.log(status);

		if (codigos.length > 0) {
			$.ajax({
				url: url,
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