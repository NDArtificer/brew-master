var Brewer = Brewer || {};

Brewer.PrecisionZero = (function() {

	class PrecisionZero {
		constructor() {
			this.plain = $('.js-plain');

		}
		enable() {
			this.plain.maskMoney({ precision: 0, thousands: '.' });
		}
	}
	return PrecisionZero;

}());


$(function() {
	var precisionZero = new Brewer.PrecisionZero();
	precisionZero.enable();

});