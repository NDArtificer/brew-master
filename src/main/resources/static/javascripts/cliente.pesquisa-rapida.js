Brewer = Brewer || {};

Brewer.PesquisaRapidaClientes = (function() {
	class PesquisaRapidaClientes {
		constructor() {
			this.pesquisaRapidaClientesModal = $('#pesquisaRapidaClientes');
			this.nomeInput = $('#nomeClienteModal');
			this.pesquisaBtn = $('.js-btn-pesquisa');
			this.containerTabelaPesquisa = $('#containerTabelaPesquisaRapidaClientes');
			this.htmlTabelaPesquisa = $('#Tabela-Pesquisa-Rapida-Cliente').html();
			this.template = Handlebars.compile(this.htmlTabelaPesquisa);
			this.mensagemError = $('.js-mensagem-error-modal');

		}

		enable() {
			this.pesquisaBtn.on('click', onPesquisaBtnClick.bind(this));
			this.pesquisaRapidaClientesModal.on('show.bs.modal', onModalShow.bind(this, this.nomeInput));
			this.pesquisaRapidaClientesModal.on('hide.bs.modal', onModalClose.bind(this));
		}
	}

	function onModalShow(nomeInput) {
		nomeInput.focus();
	}

	function onModalClose() {
		this.nomeInput.val('');
		this.mensagemError.addClass('hidden');
		var html = this.template('{}');
		this.containerTabelaPesquisa.html(html);
	}

	function onPesquisaBtnClick(event) {
		event.preventDefault();
		$.ajax({
			url: this.pesquisaRapidaClientesModal.find('form').attr('action'),
			method: 'GET',
			contentType: 'application/json',
			data: {
				nome: this.nomeInput.val()
			},
			beforeSend: addCsrfToken,
			success: onPesquisaConcluida.bind(this),
			error: onErrorPesquisa.bind(this)
		});

	}

	function onErrorPesquisa() {
		this.mensagemError.removeClass('hidden');
	}

	function onPesquisaConcluida(resultado) {
		this.mensagemError.addClass('hidden');

		var html = this.template(resultado);
		this.containerTabelaPesquisa.html(html);

		var tabelaClientePesquisaRapida = new Brewer.TabelaClientesPesquisaRapida(this.pesquisaRapidaClientesModal);
		tabelaClientePesquisaRapida.enable();

	}

	function addCsrfToken(xhr) {
		var token = $('input[name=_csrf]').val();
		var header = $('input[name=_csrf_header]').val();
		xhr.setRequestHeader(header, token);
	}

	return PesquisaRapidaClientes;
}());

Brewer.TabelaClientesPesquisaRapida = (function() {
	class TabelaClientesPesquisaRapida {
		constructor(modal) {
			this.modalCliente = modal;
			this.cliente = $('.js-cliente-pesquisa-rapida');
		}
		enable() {
			this.cliente.on('click', onClienteSelecionado.bind(this));
		}


	}

	function onClienteSelecionado(event) {
		this.modalCliente.modal('hide');
		var clienteSelected = $(event.currentTarget);
		$('#nomeCliente').val(clienteSelected.data('nome'));
		$('#idCliente').val(clienteSelected.data('id'));
	}
	return TabelaClientesPesquisaRapida;


}());


$(function() {
	var pesquisaRapidaClientes = new Brewer.PesquisaRapidaClientes();
	pesquisaRapidaClientes.enable();
});