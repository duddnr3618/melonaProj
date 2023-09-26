// noinspection JSUnresolvedReference

$(function () {

})
const PUTINWORK = "관리할 대상이 존재하지 않습니다. 일거리를 더 만들어내세요.";
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
            const boardPaging = boardData.data.content;
            const boardKey = ["boardId", "boardTitle", "boardWriter"];

            TableHtmlHandler(titleArray, boardPaging, boardKey);
        })
        .catch(function () {
            TableErrorHandler(titleArray);
        });
})

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

            TableHtmlHandler(titleArray, memberPaging, memberKey);

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
            console.log(roleData);

            TableHtmlHandler(titleArray, memberPaging, memberKey);
        })
        .catch(function () {
            TableErrorHandler(titleArray);
        });
})


/**각 리스트 클릭 이벤트 처리*/
managementCategoryMap.forEach((managementList, viewCategory) => {
    viewCategory.click(function () {

        const thisHidden = managementList.is(':hidden');
        if (thisHidden) {
            managementList.show();
        }

        /**요소를 클릭시 다른 요소가 자동적으로 닫히도록 하는 each문*/
        managementCategoryMap.forEach((otherManagementList, otherViewCategory) => {
            if (otherViewCategory !== viewCategory) {
                otherManagementList.hide();
            }
        });
    });
})


/**
 * 테이블을 타이틀, 타겟키값으로 자동으로 html 파싱까지 완료하는 함수
 * @param {Array} titleArray 테이블 타이틀로 지정할 이름의 배열을 전달할것.
 * @param {targetPaging} targetPaging axios 데이터로 넘겨받은 데이터를 전달할것.
 * @param {Array} targetPagingKey axios 데이터로 넘겨받은 데이터중 추출할 키값의 배열을 전달할것.
 */
function TableHtmlHandler(titleArray, targetPaging, targetPagingKey) {
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
        tableHTML += '<tr>';
        for (const key of targetPagingKey) {
            tableHTML += `<td>${data[key]}</td>`;
        }
        tableHTML += '</tr>';
    }

    tableHTML += '</table>';
    id_ResultTable.html(tableHTML);
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