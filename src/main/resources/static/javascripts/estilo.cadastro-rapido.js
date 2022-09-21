$(function() {

	var modal = $('#modalStyleFastRegister');
	var btnSalvar = modal.find('.js-modal-cadastro-estilo-salvar-btn');
	var form = modal.find('form')
	form.on('submit', function(event) { event.preventDefault() });
	var url = form.attr('action');
	var inputNomeEstilo = $('#nomeEstilo');
	var containerMensagemErro = $('.js-mensagem-cadastro-rapido-estilo')

	modal.on('show.bs.modal', onModalShow);
	modal.on('hide.bs.modal', onModalClose);
	btnSalvar.on('click', onBtnSalvarClick);
	function onModalShow() {
		inputNomeEstilo.focus();
	}

	function onModalClose() {
		inputNomeEstilo.val('');
		containerMensagemErro.addClass('hidden');
		form.find('.form-group').removeClass('has-error');
	}


	function onBtnSalvarClick() {
		var nomeEstilo = inputNomeEstilo.val().trim();
		$.ajax({
			url: url,
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({ nome: nomeEstilo }),
			error: onSubmitError,
			success: onSubmitSuccess

		});
	}

	function onSubmitError(object) {
		var mensagemErro = object.responseText;
		containerMensagemErro.removeClass('hidden');
		containerMensagemErro.html('<span>' + mensagemErro + '</span>');
		form.find('.form-group').addClass('has-error');
	}

	function onSubmitSuccess(estilo) {
		var comboEstilo = $('#estilo');
		comboEstilo.append('<option value=' + estilo.codigo + '>' + estilo.nome + '</option>');
		comboEstilo.val(estilo.codigo);
		modal.modal('hide');
	}
});