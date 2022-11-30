Brewer.PedidoItens = (function() {
	class PedidoItens {
		constructor(autoComplete) {
			this.autoComplete = autoComplete;
			this.tabelaCervejasContainer = $('.js-tabela-cervejas-container');
			this.uuid = $('#uuid').val();
			this.emitter = $({});
			this.on = this.emitter.on.bind(this.emitter);
		}

		enable() {
			this.autoComplete.on('item-selecionado', onItemSelecionado.bind(this));
			bindQuantidade.call(this);
			bindTabelaItem.call(this);
		}
		
		valorTotal(){
			return this.tabelaCervejasContainer.data('valor');
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

		bindQuantidade.call(this);

		var tabelaItem = bindTabelaItem.call(this);


		this.emitter.trigger('tabela-itens-atualizada', tabelaItem.data('valor-total'));
	}

	function onDoubleClick(event) {
		$(this).toggleClass('solicitando-exclusao')
	}

	function onQuantidadeAlterado(event) {
		var input = $(event.target);
		var quantidade = input.val();

		if (quantidade <= 0) {
			input.val(1);
			quantidade = 1;
		}

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

	function bindQuantidade() {
		var quantidadeInput = $('.js-tabela-cerveja-quantidade-item');
		quantidadeInput.on('change', onQuantidadeAlterado.bind(this));
		quantidadeInput.maskMoney({ precision: 0, thousands: '' });
	}

	function bindTabelaItem() {
		var tabelaItem = $('.js-tabela-item');
		tabelaItem.on('dblclick', onDoubleClick);
		$('.js-remover-iten-btn').on('click', onRemocaoItemClick.bind(this));
		return tabelaItem;
	}

	return PedidoItens;

}());


