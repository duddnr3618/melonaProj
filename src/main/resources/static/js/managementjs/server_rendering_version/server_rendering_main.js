// noinspection JSUnresolvedReference
$(function () {
    id_DetailBoardFilter.find('li').eq(0).click();
})

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
            id_ResultTable.html(boardData.data);
            disableButtonHandler(categoryData);
        })
        .catch(function () {
        });
});

/**게시물 비활성화 버튼 맵핑*/
function disableButtonHandler(category) {
    let cls_disabledButton = $('.disabledButton');
    cls_disabledButton.click(function (button) {
        console.log($(this).attr('class'));
        let board_id = $(this).data('id');
        const params = new URLSearchParams();
        params.append('category', category);
        params.append('id', board_id);
        console.log(board_id);
        console.log(params.toString());
        axios.put(`/management/board_disabled?${params.toString()}`)
            .then(function () {
                id_DetailBoardFilter.find('li').click();
                cls_disabledButton.off('click');
            })
            .catch((reason) => {
                console.log(reason)
            });
    })
}


/**유저 제재 관리*/
id_DetailUserFilter.find('li').click(function () {
    const filterData = $(this).data('user-filter');
    const params = new URLSearchParams();
    params.append('filter', filterData);

    axios.get(`management/member_filter_page?${params.toString()}`)
        .then(function (memberData) {
            id_ResultTable.html(memberData.data);
        })
        .catch(function () {

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

        })
        .catch(function () {
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