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
			initStorage.call(this);
		}


	}

	function statusBtnAction(active) {
		active ? this.statusBtn.removeClass('disabled') : this.statusBtn.addClass('disabled');
	}

	function onSelectClick() {
		var checkBoxesSelected = $(this.selectedCheckBox).filter(':checked');
		this.selectAllCheckBox.prop('checked', checkBoxesSelected.length >= this.selectedCheckBox.length);
		statusBtnAction.call(this, checkBoxesSelected.length);
		setCodigosLocalStorage.call(this, this.chaveUsuariosSelecionados);

	}

	function onSelectAll() {
		var status = this.selectAllCheckBox.prop('checked');
		this.selectedCheckBox.prop('checked', status);
		statusBtnAction.call(this, status);
		setCodigosLocalStorage.call(this, this.chaveUsuariosSelecionados);
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

	function initStorage() {
		if (!getSelecionadosLocalStorage()) {
			setSelecionadosLocalStorage({});
			return;
		}
		marcaItensSelecionados.call(this);
	}

	function getCodigosSelecionados() {
		var checkBoxSelecionados = $(this.selectedCheckBox).filter(':checked');
		return $.map(checkBoxSelecionados, function(c) {
			return $(c).data('codigo');
		});
	}

	function getCodigosLocalStorage() {
		var pagina = new URLSearchParams(window.location.search).get("page");
		return getSelecionadosLocalStorage() ? getSelecionadosLocalStorage()[pagina] : [];
	}

	function marcaItensSelecionados() {
		var codigos = getCodigosLocalStorage();
		var todosSelecionados = true;
		this.selectedCheckBox.each(function(index, item) {
			if ($.inArray($(item).data('codigo'), codigos) != -1) {
				$(item).prop('checked', true);
			}
			else {
				todosSelecionados = false;
			}
		});

		if (todosSelecionados) {
			this.selectAllCheckBox.prop('checked', true);
		}
	}

	function setCodigosLocalStorage(chave) {
		var pagina = new URLSearchParams(window.location.search).get("page");
		var selecionados = getSelecionadosLocalStorage();
		selecionados[pagina] = getCodigosSelecionados.call(this);
		return setSelecionadosLocalStorage(selecionados);
	}

	function getSelecionadosLocalStorage() {
		return JSON.parse(localStorage.getItem("usuariosSelecionados"));
	}

	function setSelecionadosLocalStorage(object) {
		return localStorage.setItem("usuariosSelecionados", JSON.stringify(object));
	}


	return MultiSelecao;

}());

$(function() {
	var multiSelecao = new Brewer.MultiSelecao();
	multiSelecao.enable();

});