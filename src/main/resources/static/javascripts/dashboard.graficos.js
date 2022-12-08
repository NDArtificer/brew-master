var Brewer = Brewer || {};

Brewer.GraficosVendaMes = (function() {

	class GraficosVendaMes {
		constructor() {
			this.ctx = $('#graficoVendaMes')[0].getContext('2d');
		}

		enable() {

			$.ajax({
				url: 'pedidos/totalPorMes',
				method: 'GET',
				success: onDadosRecebidos.bind(this)
			});

		}
	}

	function onDadosRecebidos(vendaMeses) {
		var meses = [];
		var valores = [];
		vendaMeses.forEach(function(venda) {
			meses.unshift(venda.mes);
			valores.unshift(venda.total);
		})

		var vendasPorMes = new Chart(this.ctx, {
			type: 'line',
			data: {
				labels: meses,
				datasets: [{
					label: 'Vendas por mês',
					backgroundColor: "rgba(26,179,148,0.5)",
					pointBorderColor: "rgba(26,179,148,1)",
					pintbackgroundColor: "#fff",
					data: valores
				}]
			},
		});
	}

	return GraficosVendaMes;
}());



Brewer.GraficoVendaPorOrigem = (function() {
	class GraficoVendaPorOrigem {
		constructor() {
			this.ctx = $('#graficoVendasPorOrigem')[0].getContext('2d');
		}
		enable() {
			$.ajax({
				url: 'pedidos/porOrigem',
				method: 'GET',
				success: onDadosRecebidos.bind(this)
			});
		}
	}

	function onDadosRecebidos(vendaOrigem) {
		var meses = [];
		var cervejasNacionais = [];
		var cervejasInternacionais = [];

		vendaOrigem.forEach(function(obj) {
			meses.unshift(obj.mes);
			cervejasNacionais.unshift(obj.totalNacional);
			cervejasInternacionais.unshift(obj.totalInternacional)
		});

		var graficoVendasPorOrigem = new Chart(this.ctx, {
			type: 'bar',
			data: {
				labels: meses,
				datasets: [{
					label: 'Nacional',
					backgroundColor: "rgba(220,220,220,0.5)",
					data: cervejasNacionais
				},
				{
					label: 'Internacional',
					backgroundColor: "rgba(26,179,148,0.5)",
					data: cervejasInternacionais
				}]
			},
		});
	}

	return GraficoVendaPorOrigem;

}());
$(function() {
	var graficosVendaMes = new Brewer.GraficosVendaMes();
	var graficoVendaPorOrigem = new Brewer.GraficoVendaPorOrigem();
	graficosVendaMes.enable();
	graficoVendaPorOrigem.enable();

});