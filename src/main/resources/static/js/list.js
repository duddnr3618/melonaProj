// document.addEventListener('DOMContentLoaded', function() {
//     document.getElementById('btnradio1').addEventListener('change', function() {
//         if(this.checked) {
//             window.location.href = 'http://localhost:7777/news/list2?text=주식'; // Replace 'page1URL' with the URL for Radio 1
//         }
//     });
//
//     document.getElementById('btnradio2').addEventListener('change', function() {
//         if(this.checked) {
//             window.location.href = 'http://localhost:7777/news/list2?text=코인'; // Replace 'page2URL' with the URL for Radio 2
//         }
//     });
// });

function switchContent(type) {
    const frame = document.getElementById('content-frame');
    const list = type === '주식' ? `list3` : `list4`;
    const data = $("#" + list).html();
    frame.innerHTML = data;
}