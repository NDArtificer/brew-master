$(function(){
	var decimal = $('.js-decimal');
	decimal.maskMoney();
	var plain = $('.js-plain');
	plain.maskMoney('unmasked')[0];
	
});