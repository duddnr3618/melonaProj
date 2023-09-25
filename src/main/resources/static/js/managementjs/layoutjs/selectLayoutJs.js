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

    let tableHTML = '<table>';
    tableHTML += '<tr>';
    tableHTML += '<th>Email</th>';
    tableHTML += '<th>Name</th>';
    tableHTML += '<th>Nickname</th>';
    tableHTML += '<th>Role</th>';
    tableHTML += '<th>LimitState</th>'
    tableHTML += '</tr>';

    axios.get(`management/member_filter_page?${params.toString()}`)
        .then(function (memberData) {

            const dataLength = memberData.data.content.length;
            if (dataLength === 0) {
                tableHTML += '<tr>';
                tableHTML += `<td>관리할 대상이 존재하지 않습니다. 한가하시겠군요?</td>`
                tableHTML += '</tr>';
            }

            const memberPaging = memberData.data.content;
            for (const index in memberPaging) {
                let member = memberPaging[index];

                tableHTML += '<tr>';
                tableHTML += `<td>${member.memberEmail}</td>`;
                tableHTML += `<td>${member.memberName}</td>`;
                tableHTML += `<td>${member.memberNickname}</td>`;
                tableHTML += `<td>${member.memberRole}</td>`
                tableHTML += `<td>${member.memberLimitState}</td>`;
                tableHTML += `</tr>`;
            }
            tableHTML += '</table>';

            id_ResultTable.html(tableHTML);
        })
        .catch(function () {
            tableHTML += '<tr>';
            tableHTML += `<td>오류! 기술자에게 연락하세요!</td>`;
        });
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