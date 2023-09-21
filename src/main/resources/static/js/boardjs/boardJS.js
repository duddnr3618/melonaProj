/**동적 스크립트 처리로 인한 함수 첫할당*/
$(function () {
    handleBoardTableRowClick();
    handlePageLinkClick();
})

const id_BoardSearchForm = $('#BoardSearchForm');
const id_BoardSearchFromButton = $('#BoardSearchFormButton');
let formData_SearchForm =
    function searchFormConversionToFormData(page) {
        let form_searchForm = new FormData(id_BoardSearchForm[0]);
        if (page === undefined) {
            page = 0;
        }
        form_searchForm.set("searchPageValue", page);
        return form_searchForm;
    }

/**검색 버튼 이벤트*/
id_BoardSearchFromButton.click(function (event) {
    event.preventDefault();
    sendSearchData(formData_SearchForm());
})


/**각 게시물 클릭 시 상세 페이지로 이동하는 이벤트 처리 함수*/
function handleBoardTableRowClick() {
    const cls_boardTableValue = $('.boardTableValue');
    cls_boardTableValue.off('click');
    cls_boardTableValue.click(function () {
        const data_boardid = $(this).data('boardid');
        movePage(data_boardid);
    })
}

/**하단 페이지 이동 함수*/
function handlePageLinkClick() {
    const cls_pageLink = $('.page-link');
    cls_pageLink.off('click');
    cls_pageLink.click(function (event) {
        event.preventDefault();
        let pageValue = $(this).data('page');
        sendSearchData(formData_SearchForm(pageValue));
    })
}

/**키워드 검색 데이터 전송*/
function sendSearchData(formData) {
    const id_BoardTableBox = $('#BoardTableBox');
    const params = new URLSearchParams();
    formData.forEach((value, key) => {
        params.append(key, value);
    })
    axios.get(`/normalboard/search/?${params.toString()}`)
        .then(function (response) {
            id_BoardTableBox.html(response.data);
            handleBoardTableRowClick();
            handlePageLinkClick();
        })
        .catch(function (error) {
            /*SUDO 에러 페이지처리 */
        });

}


/**SUDO 링크로 다이렉트 이동 수정할것*/
/**상세 페이지 이동*/
function movePage(boardId) {
    window.location.href = "/normalboard/viewDetail/" + boardId;
}


/**새로운 보드 생성 데이터 전송*/
function sendNewBoardData() {

}


/**기존 보드 수정 데이터 전송*/
function sendModifyBoardData() {

}