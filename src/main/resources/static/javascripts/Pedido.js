Brewer.Pedido = (function() {
	class Pedido {
		constructor(tabelaItens) {
			this.tabelaItens = tabelaItens;
			this.valorTotalBox = $('.js-valor-total-box');
			this.valorFreteInput = $('#valorFrete');
			this.valorDescontoInput = $('#valorDesconto');

			this.valorTotalBoxContainer = $('.js-valor-total-box-container');

			this.valorDesconto = this.valorDescontoInput.data('valor');
			this.valorFrete = this.valorFreteInput.data('valor');
			this.valorTotalItens = this.tabelaItens.valorTotal();


		}

		enable() {

			this.tabelaItens.on('tabela-itens-atualizada', onTabelaItensAutalizada.bind(this));
			this.valorFreteInput.on('keyup', onValorFreteAlterado.bind(this));
			this.valorDescontoInput.on('keyup', onValorDescontoAlterado.bind(this));


			this.tabelaItens.on('tabela-itens-atualizada', onValoresAlterados.bind(this));
			this.valorFreteInput.on('keyup', onValoresAlterados.bind(this));
			this.valorDescontoInput.on('keyup', onValoresAlterados.bind(this));

			onValoresAlterados.call(this);
		}



	}

	function onTabelaItensAutalizada(evento, valorTotalItens) {
		this.valorTotalItens = valorTotalItens == null ? 0 : valorTotalItens;
	}

	function onValoresAlterados() {
		var valorTotal = numeral(this.valorTotalItens) + numeral(this.valorFrete) - numeral(this.valorDesconto);
		this.valorTotalBox.html(Brewer.formatarMoeda(valorTotal));

		this.valorTotalBoxContainer.toggleClass('negativo', valorTotal < 0);
	}

	function onValorFreteAlterado(event) {
		this.valorFrete = Brewer.recuperarValor($(event.target).val());
	}

	function onValorDescontoAlterado(event) {
		this.valorDesconto = Brewer.recuperarValor($(event.target).val());
	}

	return Pedido;

}());

$(function() {
	var autoComplete = new Brewer.AutoComplete();
	autoComplete.enalble();

	var pedidoItens = new Brewer.PedidoItens(autoComplete);
	pedidoItens.enable();

	var pedido = new Brewer.Pedido(pedidoItens)
	pedido.enable();
});