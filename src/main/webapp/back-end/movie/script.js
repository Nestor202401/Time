// Get the modal
var modal = document.getElementById("modal");
var iframe = document.getElementById("modalIframe");

// Get the button that opens the modal
var btn = document.getElementById("modalBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal
btn.onclick = function() {
  modal.style.display = "block";
  resizeIframe();
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
  modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}

// Function to resize the iframe
function resizeIframe() {
  // Set the iframe height to the content height
  iframe.style.height = iframe.contentWindow.document.body.scrollHeight + 'px';
}

//這裡是iframe網頁的處理======================================================



// 監聽新增按鈕點擊事件
$('#modalBtn').on('click', function(){
  // 重新載入iframe內容
  $('#modalIframe').attr('src', './addMovie.jsp');
  // 淡入效果打開模態框
  $('#modal').fadeIn(300);
});



// 監聽模態框關閉事件，清空iframe的src屬性
$('#modal').on('hidden.bs.modal', function (e) {
  $('#modalIframe').attr('src', '');
});

// =============================模態框底=============================================
// =============================表格開始=============================================

$(document).ready(function() {
  // 初始化 DataTable
  var table = $('#example').DataTable({
      // 表格整體可以顯示的高度
      scrollY:        "500px",
      scrollX:        true,
      scrollCollapse: true,
      // 開/關 頁面切頁功能
      paging:         true,
      // 一頁顯示幾筆資料
      pageLength:     10, 
      // 開/關 頁面顯示幾筆
      lengthChange:   false,
      fixedColumns:   {
          leftColumns: 1,
          rightColumns: 0
      },
      // 移除搜索框和顯示信息
      searching: false,
      info: false,
      // 取消排序功能
      ordering: true,
      // 设置默认排序列
      order: [[1, 'asc']]
  });
});


// =============================表格底=============================================
// =============================日期選擇器開始======================================

 // 初始化日期選擇器
 $( "#datepicker" ).datepicker({
   showAnim: "slideDown", // 設置動畫為滑下
   dateFormat: "yy-mm-dd", // 設置日期格式為年-月-日
   onSelect: function(selectedDate) {
     // 在選擇開始日期後，更新結束日期選擇器的最小日期
     $( "#datepicker2" ).datepicker( "option", "minDate", selectedDate );
   }
 });
 
 $( "#datepicker2" ).datepicker({
   showAnim: "slideDown", // 設置動畫為滑下
   dateFormat: "yy-mm-dd", // 設置日期格式為年-月-日
   onSelect: function(selectedDate) {
     // 在選擇結束日期後，更新開始日期選擇器的最大日期
     $( "#datepicker" ).datepicker( "option", "maxDate", selectedDate );
   }
 });
 
 // 清除開始日期時，重置結束日期選擇器的最小日期
 $("#datepicker").on("input", function() {
   if ($(this).val() === "") {
     $( "#datepicker2" ).datepicker( "option", "minDate", null );
   }
 });
 
 // 清除結束日期時，重置開始日期選擇器的最大日期
 $("#datepicker2").on("input", function() {
   if ($(this).val() === "") {
     $( "#datepicker" ).datepicker( "option", "maxDate", null );
   }
 });
 
 // 監聽表單提交事件
 $("#dateForm").submit(function(event) {
   // 保存日期選擇器和搜索輸入框的值到本地存儲
   localStorage.setItem("start_date", $("#datepicker").val());
   localStorage.setItem("end_date", $("#datepicker2").val());
   localStorage.setItem("search", $("#search").val());
 });


// =============================日期選擇器底======================================