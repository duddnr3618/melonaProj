/**동적 스크립트 처리로 인한 함수 첫할당*/
$(function () {
    handleBoardTableRowClick();
})

const id_BoardSearchForm = $('#BoardSearchForm');
const id_BoardSearchFromButton = $('#BoardSearchFormButton');


/**검색 버튼 Axios 절대로 FormData 변수 let 건드리지 말것!!!*/
id_BoardSearchFromButton.click(function (event) {
    event.preventDefault();
    let form_searchForm = new FormData(id_BoardSearchForm[0]);
    sendSearchData(form_searchForm);
})


/**각 게시물 클릭 시 상세 페이지로 이동하는 이벤트 처리 함수*/
function handleBoardTableRowClick() {
    const cls_boardTableValue = $('.boardTableValue');
    cls_boardTableValue.click(function () {
        const data_boardid = $(this).data('boardid');
        movePage(data_boardid);
    })
}



/**키워드 검색 데이터 전송*/
function sendSearchData(formData) {
    const id_BoardTableBox = $('#BoardTableBox');
    const params = new URLSearchParams();
    formData.forEach((value, key) => {params.append(key, value);})
    axios.get(`/normalboard/search/?${params.toString()}`)
        .then(function (response) {
            id_BoardTableBox.html(response.data);
        })
        .then(function () {
            handleBoardTableRowClick();
        })
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