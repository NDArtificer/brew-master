Brewer.PedidoItens = (function() {
	class PedidoItens {
		constructor(autoComplete) {
			this.autoComplete = autoComplete;
			this.tabelaCervejasContainer = $('.js-tabela-cervejas-container');
		}

		enable() {
			this.autoComplete.on('item-selecionado', onItemSelecionado.bind(this));
		}
	}

	function onItemSelecionado(event, item) {
		var reposta = $.ajax({
			url: 'item',
			method: 'POST',
			data: {
				id: item.id
			},
			beforeSend: addCsrfToken
		});

		reposta.done(onItemAdicionadoServidor.bind(this));
	}

	function onItemAdicionadoServidor(html) {
		this.tabelaCervejasContainer.html(html);
	}

	function addCsrfToken(xhr) {
		var token = $('input[name=_csrf]').val();
		var header = $('input[name=_csrf_header]').val();
		xhr.setRequestHeader(header, token);
	}

	return PedidoItens;

}());

$(function() {
	var autoComplete = new Brewer.AutoComplete();
	autoComplete.enalble();

	var pedidoItens = new Brewer.PedidoItens(autoComplete);
	pedidoItens.enable();
});
