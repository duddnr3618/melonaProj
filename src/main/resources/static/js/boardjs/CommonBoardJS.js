const id_BoardSearchForm = $('#BoardSearchForm');
const id_BoardSearchFromButton = $('#BoardSearchFormButton');

/**검색 버튼 Axios*/
id_BoardSearchFromButton.on('click', function (event) {
    event.preventDefault();
    const form_searchForm = new FormData(id_BoardSearchForm[0]);
    sendSearchData(form_searchForm);
    $(this).off('click');
})

const cls_boardTableValue = $('.boardTableValue');
cls_boardTableValue.on('click', function () {
    const data_boardid = $(this).data('boardid');
    movePage(data_boardid);
    $(this).off('click');
})

/**키워드 검색 데이터 전송*/
function sendSearchData(formData) {
    const params = new URLSearchParams();
    formData.forEach((value, key) => {params.append(key, value);})
    axios.get(`/normalboard/search?${params.toString()}`)
}

/**상세 페이지 이동*/
function movePage(boardId) {
    window.location.href = "/normalboard/viewDetail/"+boardId;
}

/**새로운 보드 생성 데이터 전송*/
function sendNewBoardData() {
    
}

/**기존 보드 수정 데이터 전송*/
function sendModifyBoardData() {
    
}