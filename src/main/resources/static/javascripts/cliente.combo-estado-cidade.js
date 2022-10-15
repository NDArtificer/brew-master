var Brewer = Brewer || {};

Brewer.ComboEstado = (function() {

	class ComboEstado {
		constructor() {

			this.combo = $('#estado');
			this.emitter = $({});
			this.on = this.emitter.on.bind(this.emitter);

		}
		enable() {
			this.combo.on('change', onEstadoAlterado.bind(this));

		}
	}


	function onEstadoAlterado() {
		this.emitter.trigger('alterado', this.combo.val());
	}

	return ComboEstado;
}());


Brewer.ComboCidade = (function() {

	class ComboCidade {
		constructor(comboEstado) {

			this.comboEstado = comboEstado;
			this.comboCidade = $('#cidade');
			this.imgLoading = $('.js-img-loading');
		}
		enable() {
			reset.call(this);
			this.comboEstado.on('alterado', onEstadoAlterado.bind(this));

		}
	}

	function onEstadoAlterado(event, estadoId) {
		if (estadoId) {
			var response = $.ajax({
				url: this.comboCidade.data('url'),
				method: 'GET',
				contentType: 'application/json',
				data: { 'estado': estadoId },
				beforeSend: beforeRequest.bind(this),
				complete: afterRequest.bind(this)

			});

			response.done(onBuscarCidadesCompleted.bind(this));

		} else {
			reset.call(this)
		}

	}

	function onBuscarCidadesCompleted(cidades) {
		var options = [];
		cidades.forEach(function(cidade) {
			options.push('<option value"' + cidade.id + '">' + cidade.nome + '</option>')
		})

		this.comboCidade.html(options.join(''));
		this.comboCidade.removeAttr('disabled');
	}

	function reset() {
		this.comboCidade.html('<option value="">Selecione a Cidade</option>');
		this.comboCidade.val('');
		this.comboCidade.attr('disabled', 'disabled');
	}

	function beforeRequest() {
		reset.call(this);
		this.imgLoading.show();

	}

	function afterRequest() {
		this.imgLoading.hide();
	}

	return ComboCidade;

}());


$(function() {

	var comboEstado = new Brewer.ComboEstado();
	comboEstado.enable();

	var comboCidade = new Brewer.ComboCidade(comboEstado);
	comboCidade.enable();
});