let chart3; // global

async function requestData3() {
    const result = await fetch('https://api.bithumb.com/public/ticker/ALL_KRW');
    if (result.ok) {
        const data = await result.json();
        if (data.status === "0000" && data.data && data.data.XRP) {
            const date = new Date().getTime(); // current time
            const value = parseFloat(data.data.XRP.closing_price);
            const point = [date, value];
            const series = chart3.series[0];
            const shift = series.data.length > 20; // shift if the series is longer than 20

            // add the point
            chart3.series[0].addPoint(point, true, shift);

            // call it again after one second
            setTimeout(requestData3, 1000);
        }
    }
}

window.addEventListener('load', function () {
    chart3 = new Highcharts.Chart({
        chart: {
            renderTo: 'container3',
            defaultSeriesType: 'spline',
            events: {
                load: requestData3
            }
        },
        title: {
            text: 'Bithumb Ripple Real-time Price'
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
            name: 'Ripple Price',
            data: []
        }]
    });
});
