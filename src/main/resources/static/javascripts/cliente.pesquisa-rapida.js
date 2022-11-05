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
		}
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
		var html = this.template(resultado);
		this.containerTabelaPesquisa.html(html);
		this.mensagemError.addClass('hidden');
	}

	function addCsrfToken(xhr) {
		var token = $('input[name=_csrf]').val();
		var header = $('input[name=_csrf_header]').val();
		xhr.setRequestHeader(header, token);
	}

	return PesquisaRapidaClientes;
}());

$(function() {
	var pesquisaRapidaClientes = new Brewer.PesquisaRapidaClientes();
	pesquisaRapidaClientes.enable();
});