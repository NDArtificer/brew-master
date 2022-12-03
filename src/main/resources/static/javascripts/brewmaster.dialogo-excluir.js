var Brewer = Brewer || {};

Brewer.DialogoExcluir = (function() {

	class DialogoExcluir {
		constructor() {
			this.excluirBtn = $('.js-exclusao');
			this.totalElementos = $('#numeroElementosPagina').val();
			this.paginaAtual = $('#paginaAtual');
			this.ultimaPagina = $('#ultimaPagina').val();
		}
		enalble() {
			this.excluirBtn.on('click', onExcluirClick.bind(this));
			if (window.location.search.indexOf('excluido') > -1) {
				swal('Pronto!', 'Excluido com Sucesso!', 'success');
			}
		}


	}

	function onExcluirClick(event) {
		event.preventDefault();
		var botaoClicado = $(event.currentTarget);
		var url = botaoClicado.data('url');
		var objeto = botaoClicado.data('objeto');

		swal({
			title: 'Tem Certeza?',
			text: 'Excluir "' + objeto + '"? Você não poderá recuperar depois!',
			showCancelButton: true,
			confirmButtonColor: '#DD6B55',
			confirmButtonText: 'Sim, Excluir',
			closeOnConfirm: false
		}, onConfirmExclusao.bind(this, url));
	}

	function onConfirmExclusao(url) {
		$.ajax({
			url: url,
			method: 'DELETE',
			success: onExcluidoSucesso.bind(this),
			error: onExcluidoError.bind(this)

		})
	}

	function onExcluidoSucesso() {
		/*	var urlAtual = window.location.href;
			var separator = urlAtual.indexOf('?') > -1 ? '&' : '?';
			var novaUrl = urlAtual.indexOf('excluido') > -1 ? urlAtual : urlAtual + separator + 'excluido';
	
			window.location = novaUrl;
		*/

		var urlAtual = window.location.href;
		var separador = urlAtual.indexOf('?') > -1 ? '&' : '?';
		var novaUrl = urlAtual;

		if (this.ultimaPagina == 'true' && this.totalElementos == '1' && this.paginaAtual != '0') {
			var novaPagina = 'page=' + (this.paginaAtual - 1);
			var paginaAtual = urlAtual.match('page=\\d');
			novaUrl = urlAtual.replace(paginaAtual, novaPagina);
		}
		window.location = urlAtual.indexOf('excluido') > -1 ? novaUrl : novaUrl + separador + 'excluido';

	}


	function onExcluidoError(e) {
		var mensagemErro = e.responseText;
		console.log('ahahahah', mensagemErro);
		swal('Oops!', mensagemErro, 'error');
	}


	return DialogoExcluir;
}());

$(function() {

	var dialogoExcluir = new Brewer.DialogoExcluir();
	dialogoExcluir.enalble();
});