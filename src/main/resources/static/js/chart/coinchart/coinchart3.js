let chart; // 글로벌 차트 변수 선언

/**
 * 서버에서 데이터를 요청하여 그래프에 추가하고 다시 요청을 예약합니다.
 */
async function requestData() {
    const options = {method: 'GET', headers: {accept: 'application/json'}};

    const result = await fetch('https://api.bithumb.com/public/ticker/ALL_KRW', options);
    if (result.ok) {
        const data = await result.json();

        if (data.status === "0000" && data.data && data.data.ETH) {
            const ethData = data.data.XRP; // 이더리움 데이터
            const date = new Date().getTime(); // 현재 시간
            const value = parseFloat(ethData.closing_price); // 종가

            const point = [date, value];
            const series = chart.series[0];
            const shift = series.data.length > 20; // 시리즈가 20보다 길면 첫 번째 포인트 제거

            // 포인트 추가
            chart.series[0].addPoint(point, true, shift);

            // 1초 후 다시 호출
            setTimeout(requestData, 1000);
        }
    } else {
        console.error("API 호출 실패:", result.status);
    }
}

window.addEventListener('load', function() {
    chart = new Highcharts.Chart({
        chart: {
            // 차트3으로 변경
            renderTo: 'container3',
            defaultSeriesType: 'spline',
            events: {
                load: requestData
            }
        },
        title: {
            text: '빗썸 리플 실시간 가격'
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
                text: '가격 (KRW)',
                margin: 80
            }
        },
        series: [{
            name: '리플 가격',
            data: []
        }]
    });
});
