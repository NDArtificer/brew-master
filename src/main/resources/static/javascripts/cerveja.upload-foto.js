var Brewer = Brewer || {};

Brewer.UploadFoto = (function() {

	class UploadFoto {
		constructor() {
			this.inputNomeFoto = $('input[name=foto]');
			this.inputContentType = $('input[name=contentType]');
			this.novaFoto = $('input[name=novaFoto]');

			this.htmlFotoCervejaTemplate = $('#foto-cerveja').html();
			this.template = Handlebars.compile(this.htmlFotoCervejaTemplate);

			this.containerFotoCerveja = $('.js-container-foto-cerveja');
			this.inputDrop = $('#upload-drop');

		}
		enable() {
			var settings = {
				type: 'json',
				filelimit: 1,
				allow: '*.(jpg|jpeg|png)',
				action: this.containerFotoCerveja.data('url-fotos'),
				complete: onUploadSuccess.bind(this),
				beforeSend: addCsrfToken
			}

			UIkit.uploadSelect($('#upload-select'), settings);
			UIkit.uploadDrop(this.inputDrop, settings);

			if (this.inputNomeFoto.val()) {
				renderizarFoto.call(this, { nome: this.inputNomeFoto.val(), contentType: this.inputContentType.val() });
			}
		}
	}


	function onUploadSuccess(resposta) {
		this.novaFoto.val('true');
		renderizarFoto.call(this, resposta);

	}


	function renderizarFoto(resposta) {
		this.inputNomeFoto.val(resposta.nome);
		this.inputContentType.val(resposta.contentType);

		this.inputDrop.addClass('hidden');
		var fotoName = '';
		if (this.novaFoto.val() == 'true') {
			fotoName = 'temp/';
		}
		fotoName += resposta.nome
		this.htmlFotoCerveja = this.template({ foto: fotoName });
		this.containerFotoCerveja.append(this.htmlFotoCerveja);

		$('.js-remove-foto').on('click', onRemoveFoto.bind(this));
	}
	function onRemoveFoto() {
		$('.js-foto-cerveja').remove();
		this.inputDrop.removeClass('hidden');
		this.inputNomeFoto.val('');
		this.inputContentType.val('');
		this.novaFoto.val('false')
	}

	function addCsrfToken(xhr) {
		var token = $('input[name=_csrf]').val();
		var header = $('input[name=_csrf_header]').val();
		xhr.setRequestHeader(header, token);
	}

	return UploadFoto;

})();

$(function() {
	var uploadFoto = new Brewer.UploadFoto();
	uploadFoto.enable();
}) 