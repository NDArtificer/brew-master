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
			var tipoPessoaSelecionada = this.radioTipoPessoa.filter(':checked')[0];
			if (tipoPessoaSelecionada) {
				applyMask.call(this, $(tipoPessoaSelecionada));
			}

		}
	}

	function onTipoPessoaAlterar(event) {
		var tipoPessoaSelecionada = $(event.currentTarget);
		applyMask.call(this, tipoPessoaSelecionada)
		this.inputCpfCnpj.val('');
	}

	function applyMask(tipoPessoaSelecionada) {
		this.labelDocumento.text(tipoPessoaSelecionada.data('documento'));
		this.inputCpfCnpj.mask(tipoPessoaSelecionada.data('mascara'));
		this.inputCpfCnpj.removeAttr('disabled');
	}

	return MaskCpfCnpj;

}());

$(function() {

	var maskCpfCnpj = new Brewer.MaskCpfCnpj();
	maskCpfCnpj.enable();
});