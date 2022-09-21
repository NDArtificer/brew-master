var Brewer = Brewer || {};

Brewer.MaskMoney = (function() {

	function MaskMoney() {
		this.decimal = $('.js-decimal');

	}
	MaskMoney.prototype.enable = function() {
		this.decimal.maskMoney({ decimal: ',', thousands: '.' });
	}

	return MaskMoney;

}());


$(function() {
	var maskMoney = new Brewer.MaskMoney();
	maskMoney.enable();

});