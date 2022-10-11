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
	
	
	return MaskPhoneNumber ;
}());


$(function() {
	var maskMoney = new Brewer.MaskMoney();
	var maskPhone = new Brewer.MaskPhoneNumber()
	maskMoney.enable();
	maskPhone.enable();

});