$(function () {

})

const PUTINWORK = "관리할 대상이 존재하지 않습니다. 한가하시겠군요.";

const id_ResultTable = $('#ResultTable');
const id_ResultPageLinkButton = $('#ResultPageLinkButton');

const id_LayoutBoard = $('#LayoutBoard');
const id_DetailBoardFilter = $('#DetailBoardFilter');

const id_LayoutUser = $('#LayoutUser');
const id_DetailUserFilter = $('#DetailUserFilter');

const id_LayoutRole = $('#LayoutRole');
const id_DetailRoleFilter = $('#DetailRoleFilter');

let managementCategoryMap = new Map([
    [id_LayoutBoard, id_DetailBoardFilter]
    , [id_LayoutUser, id_DetailUserFilter]
    , [id_LayoutRole, id_DetailRoleFilter]
]);

/**게시판 관리*/
id_DetailBoardFilter.find('li').click(function () {
    const categoryData = $(this).parents('ul').data('board-category');
    const filterData = $(this).data('board-filter');

    const params = new URLSearchParams();
    params.append('category', categoryData);
    params.append('filter', filterData);

    let titleArray = ["ID", "Title", "Writer"];

    axios.get(`management/board_filter_page?${params.toString()}`)
        .then(function (boardData) {
            console.log("클릭감지");
            console.log(boardData);

            let buttonTypeSelect;
            switch (filterData) {
                case "waring" :
                    buttonTypeSelect = 0;
                    break;
                case "block" :
                    buttonTypeSelect = 1;
                    break;
                default :
                    buttonTypeSelect = null;
            }

            const boardPaging = boardData.data.content;
            const boardKey = ["id", "boardTitle", "boardWriter"];
            TableHtmlHandler(titleArray, boardPaging, boardKey, "id", BoardDisabledAxios, categoryData);
        })
        .catch(function () {
            TableErrorHandler(titleArray);
        });
});


/**유저 제재 관리*/
id_DetailUserFilter.find('li').click(function () {
    const filterData = $(this).data('user-filter');
    const params = new URLSearchParams();
    params.append('filter', filterData);

    let titleArray = ["Name", "Nickname", "Role", "LimitState"];
    axios.get(`management/member_filter_page?${params.toString()}`)
        .then(function (memberData) {

            const memberPaging = memberData.data.content;
            const memberKey = ["memberName", "memberNickname", "memberRole", "memberLimitState"];

            TableHtmlHandler(titleArray, memberPaging, memberKey, "id");

            /**TODO 하단 페이지 이동 버튼 생성*/
        })
        .catch(function () {
            TableErrorHandler(titleArray);
        });
})

/**유저 권한 관리*/
id_DetailRoleFilter.find('li').click(function () {
    const filterData = $(this).data('role-filter');
    const params = new URLSearchParams();
    params.append('filter', filterData);

    let titleArray = ["Name", "Nickname", "Role", "LimitState"];
    axios.get(`management/member_role_filter_page?${params.toString()}`)
        .then(function (roleData) {
            const memberPaging = roleData.data.content;
            const memberKey = ["memberName", "memberNickname", "memberRole", "memberLimitState"];

            TableHtmlHandler(titleArray, memberPaging, memberKey, null);
        })
        .catch(function () {
            TableErrorHandler(titleArray);
        });
})


/**각 리스트 클릭 이벤트 처리*/
managementCategoryMap.forEach((managementList, clickCategory) => {
    clickCategory.click(function () {

        const thisHidden = managementList.is(':hidden');
        if (thisHidden) {
            managementList.show();
        }

        /**요소를 클릭시 다른 요소가 자동적으로 닫히도록 하는 each문*/
        managementCategoryMap.forEach((otherManagementList, otherViewCategory) => {
            if (otherViewCategory !== clickCategory) {
                otherManagementList.hide();
            }
        });
    });
})


/**
 * 테이블을 타이틀, 타겟키값으로 자동으로 html 파싱까지 완료하는 함수
 * @param {Array} titleArray 테이블 타이틀로 지정할 이름의 배열을 전달할것.
 * @param {DataView} targetPaging axios 데이터로 넘겨받은 데이터를 전달할것.
 * @param {Array} targetPagingKey axios 데이터로 넘겨받은 데이터중 추출할 키값의 배열을 전달할것.
 * @param targetPagingIdKey axios 데이터로 넘겨받은 데이터의 PK 혹은 그의 준하는 키값을 넘길것.
 * @param {location} 버튼 클릭시 이동할 페이지 값
 */
function
TableHtmlHandler(
    titleArray, targetPaging, targetPagingKey, targetPagingIdKey, axios, filterData) {
    let tableHTML = '<table>';
    tableHTML += '<tr>';
    titleArray.forEach((index) => {
        tableHTML += `<th>${index}</th>`;
    });
    tableHTML += '</tr>';
    const dataLength = targetPaging.length;
    if (dataLength === 0) {
        tableHTML += '<tr>';
        tableHTML += `<td colspan="${titleArray.length}">${PUTINWORK}</td>`
        tableHTML += '</tr>';
    }

    for (const data of targetPaging) {
        tableHTML += `<tr class="result-Table-tr" data-key-id="${data[targetPagingIdKey]}">`;
        for (const key of targetPagingKey) {
            tableHTML += `<td>${data[key]}</td>`;
        }

        tableHTML +=
            '<td>' +
            '<button class="disabledButton">비활성화</button>' +
            '</td>';
        tableHTML += '</tr>';
    }

    tableHTML += '</table>';
    id_ResultTable.html(tableHTML);

    const cls_DisabledButton = $('.disabledButton');
    cls_DisabledButton.click(function (event) {
        event.preventDefault();
        let sendData = $(this).parents("tr").data('key-id');
        console.log(sendData);
        axios(sendData, filterData);
    })
}

/**테이블 반환하는 Axios에서 에러시 반환하는 테이블값*/
function TableErrorHandler(titleArray) {
    let tableHTML = '<table>';
    tableHTML += '<tr>';
    if (titleArray === undefined) {
        tableHTML += '<td>오류!!</td>';
        tableHTML += '</tr>';
        tableHTML += `<td>오류! 기술자에게 연락하세요!</td>`;
    } else {
        titleArray.forEach((index) => {
            tableHTML += `<th>${index}</th>`;
        });
        tableHTML += '</tr>';
        tableHTML += `<td colspan="${titleArray.length}">오류! 기술자에게 연락하세요!</td>`;
    }
    tableHTML += '</table>';
    id_ResultTable.html(tableHTML);
}

/*TODO 하단 페이지 구현할것*/
/**@param {JSON} resultData*/
function PageLinkButtonHandler(resultData) {
    const allData = resultData.data;
    const pageNumber = allData.pageNumber;
    const totalPages = allData.totalPages;
    const content = allData.content;
    const contentLength = content.length;

    let pageLinkHtml = '<ul>';
    if (pageNumber >= totalPages - 1) {
        pageLinkHtml += `<li class="page-item disabled">`;
    } else {
        pageLinkHtml += `<li class="page-item">`;
    }

    pageLinkHtml += '<a class="page-link" href = "javascript:void(0)" ' + `data-page="${pageNumber}" >`;
    pageLinkHtml += '<span>이전</span>';
    pageLinkHtml += '</a>';
    pageLinkHtml += '</li>';

    if (allData.totalPages <= 1) {

    }


    if (pageNumber === i) {
        pageLinkHtml += `<li class="page-item, active">`
    } else {
        pageLinkHtml += `<li class="page-item">`
    }
    pageLinkHtml += `<a class="page-link" href="javascript:void(0)" data-page="${i}">${i}+1</a>`;
    pageLinkHtml += '</li>';
    pageLinkHtml += '</ul>';


    /*pageLinkHtml += `<li th:each="page: ${#numbers.sequence(0, pagingBoard.totalPages - 1)}"
                            th:if="${page >= pagingBoard.number - 5 and page <= pagingBoard.number +5}"
                            th:classappend="${page == pagingBoard.number} ? 'active'"
                            class="page-item">`;
    pageLinkHtml += `<a th:text="${page}  +1" class="page-link" href="javascript:void(0)" th:data-page="${page}"></a>`;
    pageLinkHtml += `</li>`;
    pageLinkHtml += '</ul>';*/
}

/** 게시판 디테일 페이지 이동을 위한 메서드
 * @param {KeyType} targetPagingIdKey 해당 테이블의 ID 컬럼의 값 혹은 그의 준하는 컬럼 값을 전달할것.
 * @param {HTMLLinkElement} targetRequestLink GetAxios 요청을 보낼 링크값을 전달할것 Params로 인자 전달함.
 * */
function BoardDetailLink(targetPagingIdKey, targetRequestLink) {
    const cls_ResultTable_tr = $('.result-Table-tr');
    cls_ResultTable_tr.click(function () {
        const data_keyId = this.data('key-id');
        const params = new URLSearchParams();
        params.append("id", data_keyId);

        const requestLink = targetRequestLink + params.toString();
        axios.get(requestLink)
            .then(function () {

            })
            .catch(function () {

            });
    });
}

function BoardDisabledAxios(id, category) {
    let params = new URLSearchParams();
    params.append("category", category);
    params.append("id", id);
    axios.put(`/management/board_disabled?${params.toString()}`)
        .then(function (response) {

        })
        .catch(reason => {
            console.log(reason);
        })
}