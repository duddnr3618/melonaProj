$(function () {

})

const id_ResultTable = $('#ResultTable');

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

/**게시판 관리*/
id_DetailBoardFilter.find('li').click(function () {
    const categoryData = $(this).parents('ul').data('board-category');
    const filterData = $(this).data('board-filter');

    const params = new URLSearchParams();
    params.append('category', categoryData);
    params.append('filter', filterData);

    console.log(params.toString());
    axios.get(`management/board_filter_page?${params.toString()}`)
        .then()
        .catch();
})

/**유저 제재 관리*/
id_DetailUserFilter.find('li').click(function () {
    const filterData = $(this).data('user-filter');
    const params = new URLSearchParams();
    params.append('filter', filterData);

    axios.get(`management/member_filter_page?${params.toString()}`)
        .then(function (memberData) {
            //TODO 패스워드 넘어오지 않게 쿼리 수정할것
            const memberPaging = memberData.data.content;
            let tableHTML = '<table>';

            tableHTML += '<tr>';
            tableHTML += '<th>Email</th>';
            tableHTML += '<th>Name</th>';
            tableHTML += '<th>Nickname</th>';
            tableHTML += '<th>Role</th>';
            tableHTML += '<th>LimitState</th>'
            tableHTML += '</tr>';

            for (const index in memberPaging) {
                tableHTML += '<tr>';
                tableHTML += `<td>${memberPaging[index].id}</td>`;
                tableHTML += `<td>${memberPaging[index].memberEmail}</td>`;
                tableHTML += `<td>${memberPaging[index].memberName}</td>`;
                tableHTML += `<td>${memberPaging[index].memberNickname}</td>`;
                tableHTML += `<td>${memberPaging[index].memberLimitState}</td>`;
                tableHTML += `</tr>`;
            }
            tableHTML += '</table>';

            id_ResultTable.html(tableHTML);
            console.log(tableHTML);
        })
        .catch();
})

/**유저 권한 관리*/
id_DetailRoleFilter.find('li').click(function () {
    const filterData = $(this).data('user-filter');
    const params = new URLSearchParams();
    params.append('filter', filterData);

    axios.get(`management/member_role_filter_page?${params.toString()}`)
        .then()
        .catch();
})