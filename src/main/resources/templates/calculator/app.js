function calcNow() {
    // --- 현재 보유 --- //
    // 1. 현재 평단을 입력한다.
    const nowPrice = parseInt(document.querySelector('.now__price').value);
    console.log(nowPrice);
    // 2. 현재 수량을 입력한다.
    const nowQuantity = parseInt(document.querySelector('.now__quantity').value);
    console.log(nowQuantity);
    // 3. 총 금액 계산
    const nowResult = nowPrice * nowQuantity;
    console.log(nowResult);
    // 4. 결과값이 보여진다.
    const nowTotal = document.querySelector('.now__total');
    nowTotal.value = nowResult;
    if (isNaN(nowTotal.value) == true) {
        nowTotal.value = '';
    }

    // --- 추가 매수 --- //
    const addPrice = parseInt(document.querySelector('.add__price').value);
    const addQunatity = parseInt(document.querySelector('.add__quantity').value);
    const addResult = addPrice * addQunatity;
    console.log(addResult);
    const addTotal = document.querySelector('.add__total');
    addTotal.value = addResult;
    if (isNaN(addTotal.value) == true) {
        addTotal.value = '';
    }

    // --- 최종 보유 --- //
    // ---- 최종 평단 ---- //
    const priceAverage = (nowResult + addResult) / (nowQuantity + addQunatity);
    console.log(priceAverage);
    const resultPrice = document.querySelector('.result__price');
    resultPrice.value = Math.floor(priceAverage);
    if (isNaN(resultPrice.value) == true) {
        resultPrice.value = '';
    }
    // ---- 최종 수량 ---- //
    quantityAverage = nowQuantity + addQunatity;
    console.log(quantityAverage);
    const resultQuantity = document.querySelector('.result__quantity');
    resultQuantity.value = quantityAverage;
    if (isNaN(resultQuantity.value) == true) {
        resultQuantity.value = '';
    }
    // ---- 최종 매수 총액 ---- //
    const totalAverage = nowResult + addResult;
    console.log(totalAverage);
    const resultTotal = document.querySelector('.result__total');
    resultTotal.value = totalAverage;
    if (isNaN(resultTotal.value) == true) {
        resultTotal.value = '';
    }
}

function numberWithCommas(value) {
    return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}