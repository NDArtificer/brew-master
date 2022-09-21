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


$(function() {
	var maskMoney = new Brewer.MaskMoney();
	maskMoney.enable();

});