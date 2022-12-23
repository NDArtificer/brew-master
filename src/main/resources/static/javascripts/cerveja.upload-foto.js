var Brewer = Brewer || {};

Brewer.UploadFoto = (function() {

	class UploadFoto {
		constructor() {
			this.inputNomeFoto = $('input[name=foto]');
			this.inputContentType = $('input[name=contentType]');
			this.novaFoto = $('input[name=novaFoto]');
			this.inputUrl = $('input[name=urlFoto]');

			this.htmlFotoCervejaTemplate = $('#foto-cerveja').html();
			this.template = Handlebars.compile(this.htmlFotoCervejaTemplate);

			this.containerFotoCerveja = $('.js-container-foto-cerveja');
			this.inputDrop = $('#upload-drop');
			this.imgLoading = $(".js-img-loading");

		}
		enable() {
			var settings = {
				type: 'json',
				filelimit: 1,
				allow: '*.(jpg|jpeg|png)',
				action: this.containerFotoCerveja.data('url-fotos'),
				complete: onUploadSuccess.bind(this),
				beforeSend: addCsrfToken,
				loadStart: onLoadStart.bind(this)
			}

			UIkit.uploadSelect($('#upload-select'), settings);
			UIkit.uploadDrop(this.inputDrop, settings);

			if (this.inputNomeFoto.val()) {
				renderizarFoto.call(this, {
					nome: this.inputNomeFoto.val(),
					contentType: this.inputContentType.val(),
					url: this.inputUrl.val()
				});
			}
		}
	}


	function onLoadStart() {
		this.imgLoading.removeClass('hidden');
	}

	function onUploadSuccess(resposta) {
		this.novaFoto.val('true');
		this.inputUrl.val(resposta.url);
		this.imgLoading.addClass('hidden');
		renderizarFoto.call(this, resposta);

	}


	function renderizarFoto(resposta) {
		this.inputNomeFoto.val(resposta.nome);
		this.inputContentType.val(resposta.contentType);

		this.inputDrop.addClass('hidden');
		this.htmlFotoCerveja = this.template({ url: resposta.url });
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