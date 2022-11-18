var Brewer = Brewer || {};

Brewer.MaskMoney = (function() {

	class MaskMoney {
		constructor() {
			this.decimal = $('.js-decimal');

		}
		enable() {
			this.decimal.maskMoney({ decimal: ',', thousands: '.' });
		}
	}

	return MaskMoney;

}());

Brewer.MaskDate = (function() {

	class MaskDate {
		constructor() {
			this.inputDate = $('.js-date');
		}
		enable() {
			this.inputDate.mask('00/00/0000');
		}

	}
	return MaskDate;
}());


Brewer.MaskCepNumber = (function() {

	class MaskCepNumber {
		constructor() {
			this.inputCepNumber = $('.js-cep-number');
		}
		enable() {
			var maskBehavior = function(val) {
				return val.replace(/\D/g, '').length === 8 ? '00.000-000' : '00.000-000';
			};

			var options = {
				onKeyPress: function(val, e, field, options) {
					field.mask(maskBehavior.apply({}, arguments), options);
				}
			};

			this.inputCepNumber.mask(maskBehavior, options)
		}
	}

	return MaskCepNumber;

}());

Brewer.MaskPhoneNumber = (function() {

	class MaskPhoneNumber {
		constructor() {
			this.inputPhoneNumber = $('.js-phone-number');
		}
		enable() {
			var maskBehavior = function(val) {
				return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000 ' : '(00) 0000-00009';
			};

			var options = {
				onKeyPress: function(val, e, field, options) {
					field.mask(maskBehavior.apply({}, arguments), options);
				}
			};

			this.inputPhoneNumber.mask(maskBehavior, options)

		}
	}


	return MaskPhoneNumber;
}());


Brewer.formatarMoeda = function(valor) {
	numeral.language('pt-br');
	return numeral(valor).format('0,0.00');
}

$(function() {
	var maskMoney = new Brewer.MaskMoney();
	var maskPhone = new Brewer.MaskPhoneNumber();
	var maskCep = new Brewer.MaskCepNumber();
	var maskDate = new Brewer.MaskDate();
	maskMoney.enable();
	maskPhone.enable();
	maskCep.enable();
	maskDate.enable();
});