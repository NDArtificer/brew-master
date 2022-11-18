var Brewer = Brewer || {};

Brewer.AutoComplete = (function() {
	class AutoComplete {

		constructor() {
			this.skuNomeInput = $('.js-sku-nome-cerveja-input');
			var htmlTemplateAutoComplete = $('#template-autocomplete-cerveja').html();
			this.template = Handlebars.compile(htmlTemplateAutoComplete);
		}
		enalble() {
			var options = {
				url: function(skuOuNome) {
					return '/cervejas?skuOuNome=' + skuOuNome;
				},
				getValue: 'nome',
				minCharNumber: 3,
				addCsrfToken,
				requestDelay: 500,
				ajaxSettings: {
					contentType: 'application/json'
				},
				template: {
					type: 'custom',
					method: function(nome, cerveja) {
						cerveja.valorFormatado = Brewer.formatarMoeda(cerveja.valor);
						return this.template(cerveja);
					}.bind(this)
				}
			};

			this.skuNomeInput.easyAutocomplete(options);

			function addCsrfToken(xhr) {
				var token = $('input[name=_csrf]').val();
				var header = $('input[name=_csrf_header]').val();
				xhr.setRequestHeader(header, token);
			}
		}


	}

	return AutoComplete;

}());

$(function() {
	var autoComplete = new Brewer.AutoComplete();
	autoComplete.enalble();
});