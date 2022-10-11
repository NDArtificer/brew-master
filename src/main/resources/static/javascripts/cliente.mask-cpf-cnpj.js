var Brewer = Brewer || {};

Brewer.MaskCpfCnpj = (function() {

	class MaskCpfCnpj {
		constructor() {
			this.radioTipoPessoa = $('.js-radio-tipo-pessoa');
			this.labelDocumento = $('[for=cpfOuCnpj]');
			this.inputCpfCnpj = $('#cpfCnpj');
		}
		enable() {
			this.radioTipoPessoa.on('change', onTipoPessoaAlterar.bind(this));
		}
	}

	function onTipoPessoaAlterar(event) {
		var tipoPessoaSelecionada = $(event.currentTarget);

		this.labelDocumento.text(tipoPessoaSelecionada.data('documento'));
		this.inputCpfCnpj.mask(tipoPessoaSelecionada.data('mascara'));
		this.inputCpfCnpj.val('');
		this.inputCpfCnpj.removeAttr('disabled');
	}

	return MaskCpfCnpj;

}());

$(function() {

	var maskCpfCnpj = new Brewer.MaskCpfCnpj();
	maskCpfCnpj.enable();
});