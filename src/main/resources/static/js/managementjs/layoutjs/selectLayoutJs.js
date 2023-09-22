$(function () {

})

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