var Brewer = Brewer || {};

Brewer.EstiloCadastroRapido = (function() {


	class EstiloCadastroRapido {
		constructor() {

			this.modal = $('#modalStyleFastRegister');
			this.btnSalvar = this.modal.find('.js-modal-cadastro-estilo-salvar-btn');
			this.form = this.modal.find('form');
			this.url = this.form.attr('action');
			this.containerMensagemErro = $('.js-mensagem-cadastro-rapido-estilo');
			this.inputNomeEstilo = $('#nomeEstilo');
		}
		iniciar() {
			this.form.on('submit', function(event) { event.preventDefault(); });
			this.modal.on('show.bs.modal', onModalShow.bind(this));
			this.modal.on('hide.bs.modal', onModalClose.bind(this));
			this.btnSalvar.on('click', onBtnSalvarClick.bind(this));
		}
	}



	function onModalShow() {
		this.inputNomeEstilo.focus();
	}

	function onModalClose() {
		this.inputNomeEstilo.val('');
		this.containerMensagemErro.addClass('hidden');
		this.form.find('.form-group').removeClass('has-error');
	}


	function onBtnSalvarClick() {
		var nomeEstilo = this.inputNomeEstilo.val().trim();
		$.ajax({
			url: this.url,
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({ nome: nomeEstilo }),
			error: onSubmitError.bind(this),
			success: onSubmitSuccess.bind(this)

		});
	}

	function onSubmitError(object) {
		var mensagemErro = object.responseText;
		this.containerMensagemErro.removeClass('hidden');
		this.containerMensagemErro.html('<span>' + mensagemErro + '</span>');
		this.form.find('.form-group').addClass('has-error');
	}

	function onSubmitSuccess(estilo) {
		var comboEstilo = $('#estilo');
		comboEstilo.append('<option value=' + estilo.codigo + '>' + estilo.nome + '</option>');
		comboEstilo.val(estilo.codigo);
		this.modal.modal('hide');
	}

	return EstiloCadastroRapido;

}());

$(function() {

	var cadastroEstiloRapido = new Brewer.EstiloCadastroRapido();
	cadastroEstiloRapido.iniciar();

});