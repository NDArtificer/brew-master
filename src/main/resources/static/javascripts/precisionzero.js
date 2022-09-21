var Brewer = Brewer || {};

Brewer.PrecisionZero = (function() {

	function PrecisionZero() {
		this.plain = $('.js-plain');

	}
	PrecisionZero.prototype.enable = function() {
		this.plain.maskMoney({ precision: 0, thousands: '.' });
	}
	return PrecisionZero;

}());


$(function() {
	var precisionZero = new Brewer.PrecisionZero();
	precisionZero.enable();

});