var Brewer = Brewer || {};

Brewer.UploadFoto = (function() {

	class UploadFoto {
		constructor() {
			this.inputNomeFoto = $('input[name=foto]');
			this.inputContentType = $('input[name=contentType]');
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
				complete: onUploadSuccess.bind(this)
			}

			UIkit.uploadSelect($('#upload-select'), settings);
			UIkit.uploadDrop(this.inputDrop, settings);

		}
	}


	function onUploadSuccess(resposta) {
		this.inputNomeFoto.val(resposta.nome);
		this.inputContentType.val(resposta.contentType);

		this.inputDrop.addClass('hidden');
		this.htmlFotoCerveja = this.template({ nomeFoto: resposta.nome });
		this.containerFotoCerveja.append(this.htmlFotoCerveja);

		$('.js-remove-foto').on('click', onRemoveFoto.bind(this));

	}

	function onRemoveFoto() {
		$('.js-foto-cerveja').remove();
		this.inputDrop.removeClass('hidden');
		this.inputNomeFoto.val('');
		this.inputContentType.val('');
	}

	return UploadFoto;

})();

$(function() {
	var uploadFoto = new Brewer.UploadFoto();
	uploadFoto.enable();
}) 