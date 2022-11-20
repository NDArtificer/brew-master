var Brewer = Brewer || {};

Brewer.AutoComplete = (function() {
	class AutoComplete {

		constructor() {
			this.skuNomeInput = $('.js-sku-nome-cerveja-input');
			var htmlTemplateAutoComplete = $('#template-autocomplete-cerveja').html();
			this.template = Handlebars.compile(htmlTemplateAutoComplete);
			this.emitter = $({});
			this.on = this.emitter.on.bind(this.emitter);
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
					method: createTemplate.bind(this)
				},
				list: {
					onChooseEvent: onItemSelecionado.bind(this)
				}
			};

			this.skuNomeInput.easyAutocomplete(options);

			function addCsrfToken(xhr) {
				var token = $('input[name=_csrf]').val();
				var header = $('input[name=_csrf_header]').val();
				xhr.setRequestHeader(header, token);
			}

			function onItemSelecionado() {
				this.emitter.trigger('item-selecionado', this.skuNomeInput.getSelectedItemData());
			}

			function createTemplate(nome, cerveja) {
				cerveja.valorFormatado = Brewer.formatarMoeda(cerveja.valor);
				return this.template(cerveja);
			}
		}


	}

	return AutoComplete;

}());
