Brewer = Brewer || {};


Brewer.BotaoSubmit = (function() {

	class BotaoSubmit {
		constructor() {
			this.botaoSubmit = $('.js-btn-submit');
			this.formulario = $('.js-formulario-principal');
		}

		enable() {
			this.botaoSubmit.on('click', onSubmitClick.bind(this));
		}


	}

	function onSubmitClick(event) {
		event.preventDefault();

		var botaoCliked = $(event.target);
		var acao = botaoCliked.data('acao');

		var acaoInput = $('<input>');
		acaoInput.attr('name', acao);

		this.formulario.append(acaoInput)
		this.formulario.submit();

	}

	return BotaoSubmit;

}());

$(function() {

	var botaoSubmit = new Brewer.BotaoSubmit();
	botaoSubmit.enable();
})