Brewer.PedidoItens = (function() {
	class PedidoItens {
		constructor(autoComplete) {
			this.autoComplete = autoComplete;
			this.tabelaCervejasContainer = $('.js-tabela-cervejas-container');
			this.uuid = $('#uuid').val();
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
				id: item.id,
				uuid: this.uuid
			},
			beforeSend: addCsrfToken
		});

		resposta.done(onItemAtualizadoServidor.bind(this));
	}

	function onItemAtualizadoServidor(html) {
		this.tabelaCervejasContainer.html(html);
		$('.js-tabela-cerveja-quantidade-item').on('change', onQuantidadeAlterado.bind(this));
		$('.js-tabela-item').on('dblclick', onDoubleClick);
		$('.js-remover-iten-btn').on('click', onRemocaoItemClick.bind(this));
	}

	function onDoubleClick(event) {
		$(this).toggleClass('solicitando-exclusao')
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
					quantidade: quantidade,
					uuid: this.uuid
				},
				beforeSend: addCsrfToken
			});

		resposta.done(onItemAtualizadoServidor.bind(this));
	}


	function onRemocaoItemClick(event) {
		var id = $(event.target).data('id-cerveja');
		var resposta = $.ajax({
			url: 'item/' + this.uuid + '/' + id,
			method: 'DELETE',
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
