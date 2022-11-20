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
		var resposta = $.ajax({
			url: 'item',
			method: 'POST',
			data: {
				id: item.id
			},
			beforeSend: addCsrfToken
		});

		resposta.done(onItemAtualizadoServidor.bind(this));
	}

	function onItemAtualizadoServidor(html) {
		this.tabelaCervejasContainer.html(html);
		$('.js-tabela-cerveja-quantidade-item').on('change', onQuantidadeAlterado.bind(this));
	}

	function onQuantidadeAlterado(event) {
		var input = $(event.target);
		var quantidade = input.val();
		var id = input.data('id-cerveja');

		var resposta =
			$.ajax({
				url: 'item/' + id,
				method: 'PUT',
				data: {
					quantidade: quantidade
				},
				beforeSend: addCsrfToken
			});

		resposta.done(onItemAtualizadoServidor.bind(this));
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
