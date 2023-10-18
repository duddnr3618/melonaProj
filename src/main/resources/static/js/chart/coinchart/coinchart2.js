let chart2; // global

async function requestData2() {
    const result = await fetch('https://api.bithumb.com/public/ticker/ALL_KRW');
    if (result.ok) {
        const data = await result.json();
        if (data.status === "0000" && data.data && data.data.BTC) {
            const date = new Date().getTime(); // current time
            const value = parseFloat(data.data.BTC.closing_price);
            const point = [date, value];
            const series = chart2.series[0];
            const shift = series.data.length > 20; // shift if the series is longer than 20

            // add the point
            chart2.series[0].addPoint(point, true, shift);

            // call it again after one second
            setTimeout(requestData2, 1000);
        }
    }
}

window.addEventListener('load', function () {
    chart2 = new Highcharts.Chart({
        chart: {
            renderTo: 'container2',
            defaultSeriesType: 'spline',
            events: {
                load: requestData2
            }
        },
        title: {
            text: 'Bithumb Bitcoin Real-time Price'
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 150,
            maxZoom: 20 * 1000
        },
        yAxis: {
            minPadding: 0.2,
            maxPadding: 0.2,
            title: {
                text: 'Price (KRW)',
                margin: 80
            }
        },
        series: [{
            name: '비트코인 Price',
            data: []
        }]
    });
});
