const id_BoardSearchForm = $('#BoardSearchForm');
const id_BoardSearchFromButton = $('#BoardSearchFormButton');

id_BoardSearchFromButton.on('click', function (event) {
    event.preventDefault();
    const form_searchForm = new FormData(id_BoardSearchForm[0]);
    sendSearchData(form_searchForm);
})


function sendSearchData(formData) {
    const params = new URLSearchParams();
    formData.forEach((value, key) => {params.append(key, value);})
    axios.get(`/normalboard/search?${params.toString()}`)
}

function sendNewBoardData() {
    
}

function sendModifyBoardData() {
    
}